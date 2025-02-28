package com.ssafy.enjoytrip.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncryptWrapper extends HttpServletRequestWrapper {

	public EncryptWrapper(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String getParameter(String name) {
		String value = "";
		if(name != null && (name.equals("login-password") || name.equals("signup-password"))) {
			if(name.equals("login-password")) {
				value = super.getParameter("login-password");
			} else if (name.equals("signup-password")) {
				value = super.getParameter("signup-password");
			}
			value = getSha512(value);
		} else {
			value = super.getParameter(name);
		}
		
		return value;
	}

	private String getSha512(String password) {
		String encPwd = "";
		
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		byte[] bytes = password.getBytes(Charset.forName("UTF-8"));
		md.update(bytes);
		encPwd = Base64.getEncoder().encodeToString(md.digest());
		return encPwd;
	}

}
