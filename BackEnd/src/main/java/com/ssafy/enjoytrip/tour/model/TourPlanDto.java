package com.ssafy.enjoytrip.tour.model;

import java.util.List;

public class TourPlanDto {
	private int id;
	private String userId;
	private String nickname;
	private String title;
	private String content;
	private String startTime;
	private String endTime;
	private String registerTime;
	private int hit;
	private double avg;
	private List<InfoDto> InfoDtoList;
	private String reviewContent;
	private String reviewRegisterTime;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public List<InfoDto> getInfoDtoList() {
		return InfoDtoList;
	}

	public void setInfoDtoList(List<InfoDto> infoDtoList) {
		InfoDtoList = infoDtoList;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public String getReviewRegisterTime() {
		return reviewRegisterTime;
	}

	public void setReviewRegisterTime(String reviewRegisterTime) {
		this.reviewRegisterTime = reviewRegisterTime;
	}

	@Override
	public String toString() {
		return "TourPlanDto [id=" + id + ", userId=" + userId + ", nickname=" + nickname + ", title=" + title
				+ ", content=" + content + ", startTime=" + startTime + ", endTime=" + endTime + ", registerTime="
				+ registerTime + ", hit=" + hit + ", avg=" + avg + ", InfoDtoList=" + InfoDtoList + ", reviewContent="
				+ reviewContent + ", reviewRegisterTime=" + reviewRegisterTime + "]";
	}
}