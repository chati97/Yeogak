package com.ssafy.enjoytrip.general.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.general.model.UserDto;

@Mapper
public interface UserMapper {

	int idCheck(String userId) throws SQLException;
	int nickCheck(String nickname) throws SQLException;
	int joinUser(UserDto userDto) throws SQLException;
	int updateUser(UserDto userDto) throws SQLException;
	int withdrawUser(String userId) throws SQLException;
	int changePassword(Map<String, Object> map) throws SQLException;
	int deleteUser(UserDto userDto) throws SQLException;
	UserDto loginUser(String userId, String userPw) throws SQLException;
	UserDto getUserInfo(String userId) throws SQLException;
	String getUserPw(String userId) throws SQLException;
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userId) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	
//	int updateMileage(String userId, int score) throws SQLException;
//	int updateGrage(String userId) throws SQLException;
}
