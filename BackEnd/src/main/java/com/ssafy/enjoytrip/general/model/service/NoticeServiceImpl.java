package com.ssafy.enjoytrip.general.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.general.model.BoardParameterDto;
import com.ssafy.enjoytrip.general.model.NoticeDto;
import com.ssafy.enjoytrip.general.model.UserDto;
import com.ssafy.enjoytrip.general.model.mapper.NoticeMapper;
import com.ssafy.enjoytrip.general.model.mapper.UserMapper;

@Service
public class NoticeServiceImpl implements NoticeService {
	private NoticeMapper noticeMapper;
	private UserMapper userMapper;

	public NoticeServiceImpl(NoticeMapper noticeMapper, UserMapper userMapper) {
		super();
		this.noticeMapper = noticeMapper;
		this.userMapper = userMapper;
	}

	@Override
	public NoticeDto getNotice(int id) throws SQLException {
		noticeMapper.updateHit(id);
		return noticeMapper.getNotice(id);
	}

	@Override
	public List<NoticeDto> listNotice(BoardParameterDto bpDto) throws Exception {
		return noticeMapper.listNotice(bpDto);
	}

	@Override
	public int writeNotice(NoticeDto noticeDto) throws SQLException {
//		boardMapper.setAllNoticeNeed();
		// 1. 유저의 Dto를 받아온다
		// 2. 받아온 정보의 type을 확인한다
		// 3. type이 관리자라면 진행한다.
		return noticeMapper.writeNotice(noticeDto);
		// 4. 관리자가 아니라면 0을 리턴한다.
	}

	@Override
	public void updateNoticeHit(int id) throws Exception {
		noticeMapper.updateHit(id);
	}

	@Override
	public int modifyNotice(NoticeDto noticeDto) throws Exception {
		// 1. 유저의 Dto를 받아온다
		// 2. 받아온 정보의 type을 확인한다
		// 3. type이 관리자라면 진행한다.
		return noticeMapper.modifyNotice(noticeDto);
		// 4. 관리자가 아니라면 0을 리턴한다.
	}

	@Override
	public int deleteNotice(int id) throws Exception {
		// 1. userId로 사용자의 type을 조회한다.
		// 2. type이 관리자라면 그냥 삭제 시킨다.
		return noticeMapper.deleteNotice(id);
		// 3. 아니라면 실패를 반환한다.
	}

	@Override
	public List<NoticeDto> searchNotice(Map<String, Object> map) throws Exception {
		String key = (String) map.get("key");
		String word = (String) map.get("word");
		return noticeMapper.listNotice(key, word);
	}
}
