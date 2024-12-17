package com.ssafy.enjoytrip.general.model;

import java.util.List;

public class BoardDto {

	private int id;
	private String userId;
	private String nickname;
	private String subject;
	private String content;
	private String registerTime;
	private int hit;
	private int likes;
	private List<CommentDto> commentList;

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

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public List<CommentDto> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<CommentDto> commentList) {
		this.commentList = commentList;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "BoardDto [id=" + id + ", userId=" + userId + ", subject=" + subject + ", content=" + content
				+ ", registerTime=" + registerTime + ", hit=" + hit + ", likes=" + likes + "]";
	}

}
