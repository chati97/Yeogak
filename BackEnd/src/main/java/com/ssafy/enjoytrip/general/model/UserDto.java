package com.ssafy.enjoytrip.general.model;

public class UserDto {

	private String userId;
	private String userPw;
	private String name;
	private String nickname;
	private String salt;
	private String emailId;
	private String emailDomain;
	private String registerTime;
	private int type; // 0: 기본회원,  1: 관리자
	private int sido;
	private int birthYear;
	private String sex;
	private String token;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailDomain() {
		return emailDomain;
	}

	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSido() {
		return sido;
	}

	public void setSido(int sido) {
		this.sido = sido;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", userPw=" + userPw + ", name=" + name + ", salt=" + salt + ", emailId="
				+ emailId + ", emailDomain=" + emailDomain + ", registerTime=" + registerTime + ", type=" + type
				+ ", sido=" + sido + ", birthYear=" + birthYear + ", sex=" + sex + ", token=" + token + "]";
	}

	
}
