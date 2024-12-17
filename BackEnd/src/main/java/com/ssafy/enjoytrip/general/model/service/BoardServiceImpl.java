package com.ssafy.enjoytrip.general.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.general.model.BoardDto;
import com.ssafy.enjoytrip.general.model.BoardParameterDto;
import com.ssafy.enjoytrip.general.model.CommentDto;
import com.ssafy.enjoytrip.general.model.UserDto;
import com.ssafy.enjoytrip.general.model.mapper.BoardMapper;
import com.ssafy.enjoytrip.general.model.mapper.CommentMapper;
import com.ssafy.enjoytrip.general.model.mapper.UserMapper;
import com.ssafy.enjoytrip.util.PageNavigation;
import com.ssafy.enjoytrip.util.SizeConstant;

@Service
public class BoardServiceImpl implements BoardService {
	
	private BoardMapper boardMapper;
	private UserMapper userMapper;
	private CommentMapper commentMapper;

	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper, UserMapper userMapper, CommentMapper commentMapper) {
		this.boardMapper = boardMapper;
		this.userMapper = userMapper;
		this.commentMapper = commentMapper;
	}

	@Override
	public int writeArticle(BoardDto boardDto) throws Exception {
		boardDto = censor(boardDto);
		return boardMapper.writeArticle(boardDto);
	}
	
	@Override
	public List<BoardDto> listArticle(BoardParameterDto bpDto) throws Exception {
		int start = bpDto.getPg() == 0 ? 0 : (bpDto.getPg() - 1) * bpDto.getSpp();
		bpDto.setStart(start);
		return boardMapper.listArticle(bpDto);
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
//		if ("userid".equals(key))
//			key = "user_id";
		param.put("key", key.isEmpty() ? "" : key);
		param.put("word", map.get("word").isEmpty() ? "" : map.get("word"));
		int totalCount = boardMapper.getTotalArticleCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}

	@Override
	public BoardDto getArticle(int id) throws Exception {
		boardMapper.updateHit(id);
		BoardDto result = boardMapper.getArticle(id);
		result.setCommentList(commentMapper.listComment(id));
		return result;
	}

	@Override
	public void updateHit(int id) throws Exception {
		boardMapper.updateHit(id);
	}

	@Override
	public int modifyArticle(BoardDto boardDto) throws Exception {
		// 1.  해당 글의 정보를 받아온다.
		// 2. 해당 글의 userId가 요청자의 userId와 일치하면 삭제시킨다.
		return boardMapper.modifyArticle(boardDto);
		// 3. 둘다 아니라면 실패를 반환한다.
	}

	@Override
	public int deleteArticle(int id) throws Exception {
		// 1. userId로 사용자의 type을 조회한다.
		// 2. type이 관리자라면 그냥 삭제 시킨다.
		return boardMapper.deleteArticle(id);			
		// 3. 아니라면 해당 글의 정보를 받아온다.
//		BoardDto boardDto = boardMapper.getArticle(id);
//		// 4. 해당 글의 userId가 요청자의 userId와 일치하면 삭제시킨다.
//		if (boardDto.getUserId().equals(id)) {
//			return boardMapper.deleteArticle(id);
//		}
//		// 5. 둘다 아니라면 실패를 반환한다.
//		return 0;
	}
	

	@Override
	public BoardDto censor(BoardDto boardDto) throws SQLException {
		char[] subject = boardDto.getSubject().toCharArray();
		char[] content = boardDto.getContent().toCharArray();
		int sl = subject.length;
		int cl = content.length;
		List<String> bannedList = boardMapper.getBannedWordList();
		for (String pattern : bannedList) {
			int pl = pattern.length();
			int[] fail = new int[pl];
			char[] p = pattern.toCharArray();
			for (int i = 1, j = 0; i < pl; i++) {
				while (j > 0 && p[i] != p[j]) {
					j = fail[j - 1];
				}
				if (p[i] == p[j]) {
					fail[i] = ++j;
				} else {
					fail[i] = 0;
				}
			}
			for (int i = 0, j = 0; i < sl; i++) {
				while(j > 0 && subject[i] != p[j]) {
					j = fail[j - 1];
				}
				if(subject[i] == p[j]) {
					if(j == pl - 1) {
						for(int k = 0; k < pl; k++) {
							subject[i - pl + k + 1] = '*';
						}
						j = 0;
					} else {
						j++;
					}
				}
			}
			for (int i = 0, j = 0; i < cl; i++) {
				while (j > 0 && content[i] != p[j]) {
					j = fail[j - 1];
				}
				if (content[i] == p[j]) {
					if (j == pl - 1) {
						// 패턴과 일치할 경우, *으로 문자 치환
						for (int k = 0; k < pl; k++) {
							content[i - pl + k + 1] = '*';
						}
						j = 0;
					} else {
						j++;
					}
				}
			}
		}
		boardDto.setSubject(String.copyValueOf(subject));
		boardDto.setContent(String.copyValueOf(content));
		return boardDto;
	}

	@Override
	public List<BoardDto> searchArticle(Map<String, Object> map) throws Exception {
		String key = (String) map.get("key");
		String word = (String) map.get("word");
		return boardMapper.listArticle(key, word);
	}

	@Override
	public int writeComment(CommentDto comment) throws Exception {
		return commentMapper.writeComment(comment);
	}

	@Override
	public int deleteComment(int id) throws Exception {
		return commentMapper.deleteComment(id);
	}

	@Override
	public int upLikes(Map<String, Object> map) throws Exception {
		return boardMapper.upLikes(map);
	}

	@Override
	public int getLikes(Map<String, Object> map) throws Exception {
		return boardMapper.getLikes(map);
	}

	@Override
	public void updateLikes(int id) throws Exception {
		boardMapper.updateLikes(id);
	}
}
