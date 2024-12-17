package com.ssafy.enjoytrip.general.model.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.general.model.UserDto;
import com.ssafy.enjoytrip.general.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	private UserMapper userMapper;

	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public int idCheck(String userId) throws Exception {
		return userMapper.idCheck(userId);
	}
	@Override
	public int nickCheck(String nickname) throws Exception {
		return userMapper.nickCheck(nickname);
	}

	@Override
	public int joinUser(UserDto userDto) throws Exception {
		String salt = getSalt(); // salt 생성
		userDto.setSalt(salt);
		userDto.setUserPw(encrypt(userDto.getUserPw(), salt)); // Hash
		return userMapper.joinUser(userDto);
	}

	@Override
	public UserDto loginUser(String userId, String userPw) throws Exception {
		UserDto userDto = getUserInfo(userId);
		if(userDto == null) {
			System.out.println("널입니다.");
			return null;
		}
		String hash = encrypt(userPw, userDto.getSalt());
		return userMapper.loginUser(userId, hash);
	}

	@Override
	public UserDto getUserInfo(String userId) throws Exception {
		return userMapper.getUserInfo(userId);
	}

	@Override
	public int updateUser(UserDto userDto) throws Exception {
//		changePassword(userDto);
		return userMapper.updateUser(userDto);
	}

	@Override
	public int withdrawUser(String userId) throws Exception {
		return userMapper.withdrawUser(userId);
	}

	@Override
	public int changePassword(Map<String, Object> map) throws Exception {
		// 비밀번호 변경으로 바꿀예정
		UserDto userDto = getUserInfo((String) map.get("userId"));
		String salt = userDto.getSalt(); // salt 생성
		map.put("userPw", encrypt((String) map.get("userPw"), salt));
		map.put("changePw", encrypt((String) map.get("changePw"), salt));
//		return userMapper.changePassword(userDto);
		return userMapper.changePassword(map);
	}

	public String getSalt() {
		String salt = "";
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			byte[] bytes = new byte[16];
			random.nextBytes(bytes);
			salt = new String(Base64.getEncoder().encode(bytes));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return salt;
	}

	public String encrypt(String userPw, String hash) {
		String salt = hash + userPw;
		String hex = null;
		try {
			MessageDigest msg = MessageDigest.getInstance("SHA-512");
			msg.update(salt.getBytes());
			hex = String.format("%128x", new BigInteger(1, msg.digest()));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hex;
	}

	@Override
	public int deleteUser(UserDto userDto) throws Exception {
		userDto.setUserPw(encrypt(userDto.getUserPw(), getUserInfo(userDto.getUserId()).getSalt()));
		return userMapper.deleteUser(userDto);
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		userMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return userMapper.getRefreshToken(userId);
	}

	@Override
	public void deleteRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		userMapper.saveRefreshToken(map);
	}


	
}
