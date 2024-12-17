package com.ssafy.enjoytrip.tour.model.mapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.tour.model.InfoDto;
import com.ssafy.enjoytrip.tour.model.InfoParameterDto;
import com.ssafy.enjoytrip.tour.model.ReviewDto;

@Mapper
public interface InfoMapper {
	public List<InfoDto> listInfo(InfoParameterDto infoParameterDto) throws SQLException, IOException;
	
	public InfoDto viewPlaceInfo(int contentId) throws SQLException;

	public int insertReview(Map<String, Object> map) throws SQLException;

	public ReviewDto getReviewByUser(Map<String, Object> map) throws SQLException;

	public List<ReviewDto> getReviewList(Map<String, Object> map) throws SQLException;

	public List<ReviewDto> listReviewRecent() throws SQLException;
	
	public int updateReview(Map<String, Object> map) throws SQLException;

	public int deleteReview(int id) throws SQLException;
}
