package com.ssafy.enjoytrip.general.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.general.model.BoardParameterDto;
import com.ssafy.enjoytrip.general.model.NoticeDto;

@Mapper
public interface NoticeMapper {
	int writeNotice (NoticeDto noticeDto) throws SQLException;
	List<NoticeDto> listNotice (BoardParameterDto bpDto)  throws SQLException;
	List<NoticeDto> listNotice (String key, String word)  throws SQLException;
	int modifyNotice (NoticeDto noticeDto) throws SQLException;
	int deleteNotice (int id) throws SQLException;
	NoticeDto getNotice(int id) throws SQLException;
	void updateHit (int id) throws SQLException;
}
