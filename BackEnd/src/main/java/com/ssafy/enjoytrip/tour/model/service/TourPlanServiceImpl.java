package com.ssafy.enjoytrip.tour.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.general.model.BoardParameterDto;
import com.ssafy.enjoytrip.general.model.CommentDto;
import com.ssafy.enjoytrip.tour.model.GugunDto;
import com.ssafy.enjoytrip.tour.model.InfoDto;
import com.ssafy.enjoytrip.tour.model.InfoParameterDto;
import com.ssafy.enjoytrip.tour.model.SidoDto;
import com.ssafy.enjoytrip.tour.model.TourPlanDto;
import com.ssafy.enjoytrip.tour.model.mapper.GugunMapper;
import com.ssafy.enjoytrip.tour.model.mapper.InfoMapper;
import com.ssafy.enjoytrip.tour.model.mapper.SidoMapper;
import com.ssafy.enjoytrip.tour.model.mapper.TourPlanMapper;

@Service
public class TourPlanServiceImpl implements TourPlanService {

	@Autowired
	private InfoMapper infoMapper;
	@Autowired
	private TourPlanMapper tpMapper;

	@Override
	@Transactional
	public int writeTourPlan(Map<String, Object> map) throws SQLException, ParseException {
		// 주어진 startDate, endDate를 timestamp로 변경
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime = dateFormat.parse((String) map.get("startTime"));
		Date endTime = dateFormat.parse((String) map.get("endTime"));
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		int result = tpMapper.writeTourPlan(map);
		int result2 = tpMapper.registAttractions(map);
		if (result == 0) {
			return result;
		}
		if (result2 == 0) {
			return result2;
		}
		return -result2;
	}

	@Override
	public int writeTourPlanReview(Map<String, Object> map) throws SQLException {
		String userId = getTourPlan((Integer) map.get("id")).getUserId();
		if (userId.equals((String) map.get("userId"))) {
			return tpMapper.writeTourPlanReview(map);
		}
		return 0;
	}

	@Override
	@Transactional
	public TourPlanDto getTourPlan(int id) throws SQLException {
		TourPlanDto result = tpMapper.getTourPlan(id);
		result.setInfoDtoList(tpMapper.getAttractionListByPlanId(id));
		return result;
	}

	@Override
	public List<TourPlanDto> listTourPlan(Map<String, Object> map) throws SQLException {
		return tpMapper.listTourPlan(map);
	}

	@Override
	public void updateHit(int id) throws Exception {
		tpMapper.updateHit(id);
	}

	@Override
	@Transactional
	public int updateTourPlan(Map<String, Object> map) throws Exception {
		// 주어진 startDate, endDate를 timestamp로 변경
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime = dateFormat.parse((String) map.get("startTime"));
		Date endTime = dateFormat.parse((String) map.get("endTime"));
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		int result = tpMapper.updateTourPlan(map);
		if (result == 0) {
			throw new Exception();
		}
		int result2 = tpMapper.updateTourPlanReview(map);
		if (result2 == 0) {
			return result2;
		}
		return -result2;
	}

	@Override
	public int deleteTourPlan(Map<String, Object> map) throws Exception {
		return tpMapper.deleteTourPlan(map);
	}

	@Override
	public int writeComment(CommentDto comment) throws Exception {
		return tpMapper.writeComment(comment);
	}

	@Override
	public int deleteComment(int id) throws Exception {
		return tpMapper.deleteComment(id);
	}

}
