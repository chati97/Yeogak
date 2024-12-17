package com.ssafy.enjoytrip.general.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.general.model.QnADto;
import com.ssafy.enjoytrip.general.model.UserDto;
import com.ssafy.enjoytrip.general.model.mapper.QnAMapper;
import com.ssafy.enjoytrip.general.model.mapper.UserMapper;

@Service
public class QnAServiceImpl implements QnAService {
	private QnAMapper qnaMapper;
	private UserMapper userMapper;

	public QnAServiceImpl(QnAMapper qnaMapper, UserMapper userMapper) {
		super();
		this.qnaMapper = qnaMapper;
		this.userMapper = userMapper;
	}

	@Override
	public int writeArticle(QnADto qnaDto) throws Exception {
		qnaDto = censor(qnaDto);
		return qnaMapper.writeArticle(qnaDto);
	}

	@Override
	public List<QnADto> listArticle() throws Exception {
		return qnaMapper.listArticle();
	}

	@Override
	public QnADto getArticle(int id) throws Exception {
		qnaMapper.updateHit(id);
		return qnaMapper.getArticle(id);
	}

	@Override
	public int modifyArticle(QnADto qnaDto) throws Exception {
		return qnaMapper.modifyArticle(qnaDto);
	}

	@Override
	public int writeAnswer(QnADto qnaDto) throws Exception {
		return qnaMapper.writeAnswer(qnaDto);
	}

	@Override
	public int deleteArticle(int id, UserDto userDto) throws Exception {
		// 1. userId로 사용자의 type을 조회한다.
		UserDto user = userMapper.getUserInfo(userDto.getUserId());
		// 2. type이 관리자라면 그냥 삭제 시킨다.
		if (user.getType() == 1) {
			return qnaMapper.deleteArticle(id);
		}
		// 3. 아니라면 해당 글의 정보를 받아온다.
		QnADto qnaDto = qnaMapper.getArticle(id);
		// 4. 해당 글의 userId가 요청자의 userId와 일치하면 삭제시킨다.
		if (qnaDto.getUserId().equals(id)) {
			return qnaMapper.deleteArticle(id);
		}
		// 5. 둘다 아니라면 실패를 반환한다.
		return 0;
	}

	@Override
	public QnADto censor(QnADto qnaDto) throws SQLException {
		char[] subject = qnaDto.getSubject().toCharArray();
		char[] content = qnaDto.getContent().toCharArray();
		int sl = subject.length;
		int cl = content.length;
		List<String> bannedList = qnaMapper.getBannedWordList();
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
				while (j > 0 && subject[i] != p[j]) {
					j = fail[j - 1];
				}
				if (subject[i] == p[j]) {
					if (j == pl - 1) {
						for (int k = 0; k < pl; k++) {
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
		qnaDto.setSubject(String.copyValueOf(subject));
		qnaDto.setContent(String.copyValueOf(content));
		return qnaDto;
	}

	@Override
	public List<QnADto> searchArticle(Map<String, Object> map) throws Exception {
		String key = (String) map.get("key");
		String word = (String) map.get("word");
		return qnaMapper.listArticle(key, word);
	}

}
