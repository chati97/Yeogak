package com.ssafy.enjoytrip.tour.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.tour.model.GugunDto;

@Mapper
public interface GugunMapper {
	public List<GugunDto> getGugunList(int sidoCode) throws SQLException;
}
