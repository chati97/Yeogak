package com.ssafy.enjoytrip.general.model;

public class NoticeDto extends BoardDto {
	private int id;
	private String userId;
	private String nickname;
	private String subject;
	private String content;
	private int hit;
	private String registerTime;
	private boolean needToNotice;

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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public boolean isNeedToNotice() {
		return needToNotice;
	}

	public void setNeedToNotice(boolean needToNotice) {
		this.needToNotice = needToNotice;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "NoticeDto [id=" + id + ", userId=" + userId + ", subject=" + subject + ", content=" + content + ", hit="
				+ hit + ", registerTime=" + registerTime + ", needToNotice=" + needToNotice + "]";
	}
}
