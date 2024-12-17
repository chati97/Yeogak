package com.ssafy.enjoytrip.general.model;

public class QnADto {

	private int id;
	private String userId;
	private String nickname;
	private String subject;
	private String content;
	private String registerTime;
	private String answer;
	private String answerTime;

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

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(String answerTime) {
		this.answerTime = answerTime;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "QnADto [id=" + id + ", userId=" + userId + ", subject=" + subject + ", content=" + content
				+ ", registerTime=" + registerTime + ", answer=" + answer + ", answerTime=" + answerTime + "]";
	}


}
