package com.ssafy.enjoytrip.general.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.general.model.BoardDto;
import com.ssafy.enjoytrip.general.model.BoardParameterDto;
import com.ssafy.enjoytrip.general.model.CommentDto;
import com.ssafy.enjoytrip.util.PageNavigation;

public interface BoardService {

	int writeArticle(BoardDto boardDto) throws Exception;
	List<BoardDto> listArticle(BoardParameterDto bpDto) throws Exception;
	List<BoardDto> searchArticle(Map<String, Object> map) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	BoardDto getArticle(int id) throws Exception;
	void updateHit(int id) throws Exception;
	int modifyArticle(BoardDto boardDto) throws Exception;
	int deleteArticle(int id) throws Exception;
	int writeComment(CommentDto comment) throws Exception;
	int deleteComment(int id) throws Exception;
	int upLikes(Map<String, Object> map) throws Exception;
	int getLikes(Map<String, Object> map) throws Exception;
	void updateLikes(int id) throws Exception;
	BoardDto censor(BoardDto boardDto) throws SQLException;
	

}
