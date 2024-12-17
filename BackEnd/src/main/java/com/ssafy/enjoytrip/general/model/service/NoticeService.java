package com.ssafy.enjoytrip.general.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.general.model.BoardParameterDto;
import com.ssafy.enjoytrip.general.model.NoticeDto;

public interface NoticeService {
	int writeNotice(NoticeDto noticeDto) throws SQLException;
	NoticeDto getNotice(int id) throws SQLException;
	void updateNoticeHit(int id) throws Exception;
	List<NoticeDto> listNotice(BoardParameterDto bpDto) throws Exception;
	List<NoticeDto> searchNotice(Map<String, Object> map) throws Exception;
	int modifyNotice(NoticeDto noticeDto) throws Exception;
	int deleteNotice(int id) throws Exception;
}
