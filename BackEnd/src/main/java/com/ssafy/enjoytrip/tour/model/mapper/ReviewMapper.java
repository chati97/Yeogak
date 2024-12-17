package com.ssafy.enjoytrip.tour.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.tour.model.ReviewDto;

@Mapper
public interface ReviewMapper {
	public int writeReview(ReviewDto reviewDto) throws SQLException;
	public int updateReview(ReviewDto reviewDto) throws SQLException;
	public int deleteReview(int reviewId) throws SQLException;
	public List<ReviewDto> listReview(int contentId) throws SQLException;
	public ReviewDto viewReview(int reviewId) throws SQLException;
}
