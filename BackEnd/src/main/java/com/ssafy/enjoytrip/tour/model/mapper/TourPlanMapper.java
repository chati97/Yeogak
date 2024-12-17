package com.ssafy.enjoytrip.tour.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.general.model.BoardDto;
import com.ssafy.enjoytrip.general.model.CommentDto;
import com.ssafy.enjoytrip.tour.model.InfoDto;
import com.ssafy.enjoytrip.tour.model.TourPlanDto;

@Mapper
public interface TourPlanMapper {
	public int writeTourPlan(Map<String, Object> map) throws SQLException;

	public int writeTourPlanReview(Map<String, Object> map) throws SQLException;

	public TourPlanDto getTourPlan(int id) throws SQLException;

	public List<InfoDto> getAttractionListByPlanId(int id) throws SQLException;

	public List<TourPlanDto> listTourPlan(Map<String, Object> map) throws SQLException;

	void updateHit(int id) throws Exception;

	int updateTourPlan(Map<String, Object> map) throws Exception;
	
	int updateTourPlanReview(Map<String, Object> map) throws Exception;

	int deleteTourPlan(Map<String, Object> map) throws Exception;

	int writeComment(CommentDto comment) throws Exception;

	int deleteComment(int id) throws Exception;

	int registAttractions(Map<String, Object> map) throws SQLException;
}