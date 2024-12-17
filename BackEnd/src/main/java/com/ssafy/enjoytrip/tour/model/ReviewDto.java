package com.ssafy.enjoytrip.tour.model;

public class ReviewDto {
	private int id;
	private String userId;
	private String nickname;
	private int userBirthYear;
	private String userSex;
	private int attractionId;
	private String attractionTitle;
	private String title;
	private String content;
	private int rating;
	private String registerTime;
	private String picture;
	private int helpfulCount;
	private double helpfulAvg;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public int getUserBirthYear() {
		return userBirthYear;
	}

	public void setUserBirthYear(int userBirthYear) {
		this.userBirthYear = userBirthYear;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public int getAttractionId() {
		return attractionId;
	}

	public void setAttractionId(int attractionId) {
		this.attractionId = attractionId;
	}
	
	public String getAttractionTitle() {
		return attractionTitle;
	}

	public void setAttractionTitle(String attractionTitle) {
		this.attractionTitle = attractionTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getHelpfulCount() {
		return helpfulCount;
	}

	public void setHelpfulCount(int helpfulCount) {
		this.helpfulCount = helpfulCount;
	}

	public double getHelpfulAvg() {
		return helpfulAvg;
	}

	public void setHelpfulAvg(double helpfulAvg) {
		this.helpfulAvg = helpfulAvg;
	}

	@Override
	public String toString() {
		return "ReviewDto [id=" + id + ", userId=" + userId + ", nickname=" + nickname + ", userBirthYear="
				+ userBirthYear + ", userSex=" + userSex + ", attractionId=" + attractionId + ", attractionTitle="
				+ attractionTitle + ", title=" + title + ", content=" + content + ", rating=" + rating
				+ ", registerTime=" + registerTime + ", picture=" + picture + ", helpfulCount=" + helpfulCount
				+ ", helpfulAvg=" + helpfulAvg + "]";
	}
}
