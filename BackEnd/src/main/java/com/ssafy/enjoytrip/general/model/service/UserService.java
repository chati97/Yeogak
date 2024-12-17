package com.ssafy.enjoytrip.general.model.service;

import java.sql.SQLException;
import java.util.Map;

import com.ssafy.enjoytrip.general.model.UserDto;

public interface UserService {

	int idCheck(String userId) throws Exception;
	int nickCheck(String nickname) throws Exception;
	int joinUser(UserDto userDto) throws Exception;
	int updateUser(UserDto userDto) throws Exception;
	int withdrawUser(String userId) throws Exception;
	int changePassword(Map<String, Object> map) throws Exception;
	int deleteUser(UserDto userDto) throws Exception;
	UserDto loginUser(String userId, String userPw) throws Exception;
	UserDto getUserInfo(String userId) throws Exception;
	String getSalt() ; // 암호화 
	String encrypt(String userPw, String hash);
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleteRefreshToken(String userId) throws Exception;
}
