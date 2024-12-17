package com.ssafy.enjoytrip.general.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.general.model.CommentDto;

@Mapper
public interface CommentMapper {
	List<CommentDto> listComment(int id);
	int writeComment(CommentDto comment);
	int deleteComment(int id);
}
