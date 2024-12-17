package com.ssafy.enjoytrip.tour.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.tour.model.SidoDto;

@Mapper
public interface SidoMapper {
	public List<SidoDto> getSidoList() throws SQLException;
}
