package com.ssafy.enjoytrip.general.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.general.model.QnADto;

@Mapper
public interface QnAMapper {

	int writeArticle(QnADto qnaDto) throws SQLException;
	List<QnADto> listArticle() throws SQLException;
	List<QnADto> listArticle(String key, String word) throws SQLException;
	int getTotalArticleCount(Map<String, Object> param) throws SQLException;
	QnADto getArticle(int id) throws SQLException;
	void updateHit(int id) throws SQLException;
	void updateLikes(int articleNo, String memberId) throws SQLException;
	int modifyArticle(QnADto qnaDto) throws SQLException;
	int writeAnswer(QnADto qnaDto) throws SQLException;
	int deleteArticle(int id) throws SQLException;
	
	List<String> getBannedWordList() throws SQLException;
	int getMaxIndex() throws SQLException;
	int getMaxHit() throws SQLException;
}
