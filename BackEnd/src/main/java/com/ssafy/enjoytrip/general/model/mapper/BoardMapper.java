package com.ssafy.enjoytrip.general.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.general.model.BoardDto;
import com.ssafy.enjoytrip.general.model.BoardParameterDto;

@Mapper
public interface BoardMapper {

	int writeArticle(BoardDto boardDto) throws SQLException;
	List<BoardDto> listArticle(BoardParameterDto bpDto) throws SQLException;
	List<BoardDto> listArticle(String key, String word) throws SQLException;
	int getTotalArticleCount(Map<String, Object> param) throws SQLException;
	BoardDto getArticle(int id) throws SQLException;
	void updateHit(int id) throws SQLException;
	int modifyArticle(BoardDto boardDto) throws SQLException;
	int deleteArticle(int id) throws SQLException;
	
	List<String> getBannedWordList() throws SQLException;
	int getMaxIndex() throws SQLException;
	int getMaxHit() throws SQLException;
	
	int getLikes(Map<String, Object> map) throws SQLException;
	int upLikes(Map<String, Object> map) throws SQLException;
	void updateLikes(int id) throws SQLException;
}
