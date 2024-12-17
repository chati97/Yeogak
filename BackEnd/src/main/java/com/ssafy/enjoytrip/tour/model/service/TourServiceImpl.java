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
import com.ssafy.enjoytrip.tour.model.ReviewDto;
import com.ssafy.enjoytrip.tour.model.SidoDto;
import com.ssafy.enjoytrip.tour.model.TourPlanDto;
import com.ssafy.enjoytrip.tour.model.mapper.GugunMapper;
import com.ssafy.enjoytrip.tour.model.mapper.InfoMapper;
import com.ssafy.enjoytrip.tour.model.mapper.SidoMapper;
import com.ssafy.enjoytrip.tour.model.mapper.TourPlanMapper;

@Service
public class TourServiceImpl implements TourService {

	@Autowired
	private SidoMapper sidoMapper;
	@Autowired
	private GugunMapper gugunMapper;
	@Autowired
	private InfoMapper infoMapper;
	@Autowired
	private TourPlanMapper tpMapper;

	@Override
	public List<SidoDto> getSidoList() throws SQLException {
		return sidoMapper.getSidoList();
	}

	@Override
	public List<GugunDto> getGugunList(int sidoCode) throws SQLException {
		return gugunMapper.getGugunList(sidoCode);
	}

	@Override
	public List<InfoDto> listPlace(InfoParameterDto infoParameterDto) throws SQLException, IOException {
		List<InfoDto> infos = infoMapper.listInfo(infoParameterDto);
		return infos;
	}

	@Override
	public InfoDto viewPlace(int contentId) throws SQLException, IOException {
		InfoDto result = infoMapper.viewPlaceInfo(contentId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("attractionId", contentId);
		result.setReviewList(infoMapper.getReviewList(map));
		return result;
	}

	@Override
	public List<ReviewDto> listReviewRecent() throws SQLException {
		return infoMapper.listReviewRecent();
	}

	@Override
	public int insertReview(Map<String, Object> map) throws SQLException {
		if (infoMapper.getReviewByUser(map) != null) {
			return 0;
		}
		return infoMapper.insertReview(map);
	}

	@Override
	public int updateReview(Map<String, Object> map) throws SQLException {
		return infoMapper.updateReview(map);
	}

	@Override
	public int deleteReview(int id) throws SQLException {
		return infoMapper.deleteReview(id);
	}

}
