package com.ssafy.enjoytrip.general.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.general.model.QnADto;
import com.ssafy.enjoytrip.general.model.UserDto;
import com.ssafy.enjoytrip.util.PageNavigation;

public interface QnAService {

	int writeArticle(QnADto qnaDto) throws Exception;
	List<QnADto> listArticle() throws Exception;
	List<QnADto> searchArticle(Map<String, Object> map) throws Exception;
	QnADto getArticle(int id) throws Exception;
	int modifyArticle(QnADto qnaDto) throws Exception;
	int writeAnswer(QnADto qnaDto) throws Exception;
	int deleteArticle(int id, UserDto userDto) throws Exception;
	
	QnADto censor(QnADto qnaDto) throws SQLException;
	

}
