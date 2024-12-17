package com.ssafy.enjoytrip.tour.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.general.model.BoardDto;
import com.ssafy.enjoytrip.general.model.BoardParameterDto;
import com.ssafy.enjoytrip.general.model.CommentDto;
import com.ssafy.enjoytrip.tour.model.GugunDto;
import com.ssafy.enjoytrip.tour.model.InfoDto;
import com.ssafy.enjoytrip.tour.model.InfoParameterDto;
import com.ssafy.enjoytrip.tour.model.SidoDto;
import com.ssafy.enjoytrip.tour.model.TourPlanDto;

public interface TourPlanService {
	public int writeTourPlan(Map<String, Object> map) throws SQLException, ParseException;

	public int writeTourPlanReview(Map<String, Object> map) throws SQLException;

	public List<TourPlanDto> listTourPlan(Map<String, Object> map) throws SQLException;

	public TourPlanDto getTourPlan(int id) throws SQLException;

	void updateHit(int id) throws Exception;

	int updateTourPlan(Map<String, Object> map) throws Exception;

	int deleteTourPlan(Map<String, Object> map) throws Exception;

	int writeComment(CommentDto comment) throws Exception;

	int deleteComment(int id) throws Exception;
}
