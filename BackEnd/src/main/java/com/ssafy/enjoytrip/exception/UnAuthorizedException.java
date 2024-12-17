package com.ssafy.enjoytrip.exception;

public class UnAuthorizedException extends RuntimeException {
	private static final long serialVersionUID = -2907803101486509134L;

	public UnAuthorizedException() {
		super("계정의 권한이 유효하지 않습니다.\n다시 로그인을 해주세요.");
	}
}
