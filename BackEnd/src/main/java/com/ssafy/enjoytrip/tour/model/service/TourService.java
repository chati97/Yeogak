package com.ssafy.enjoytrip.tour.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.tour.model.GugunDto;
import com.ssafy.enjoytrip.tour.model.InfoDto;
import com.ssafy.enjoytrip.tour.model.InfoParameterDto;
import com.ssafy.enjoytrip.tour.model.ReviewDto;
import com.ssafy.enjoytrip.tour.model.SidoDto;

public interface TourService {
	public List<SidoDto> getSidoList() throws SQLException;

	public List<GugunDto> getGugunList(int sidoCode) throws SQLException;

	public List<InfoDto> listPlace(InfoParameterDto infoParameterDto) throws SQLException, IOException;
	
	public InfoDto viewPlace(int contentId) throws SQLException, IOException;

	// 리뷰 관련
	public List<ReviewDto> listReviewRecent() throws SQLException;

	public int insertReview(Map<String, Object> map) throws SQLException;
	
	public int updateReview(Map<String, Object> map) throws SQLException;
	
	public int deleteReview(int id) throws SQLException;
}
