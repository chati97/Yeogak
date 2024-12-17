package com.ssafy.enjoytrip.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.enjoytrip.general.model.UserDto;

@Component
public class ConfirmInterceptor implements HandlerInterceptor { 

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userInfo");
		if(userDto == null) {
			response.sendRedirect(request.getContextPath() + "/");
			return false;
		}
		return true;
	}
	
}