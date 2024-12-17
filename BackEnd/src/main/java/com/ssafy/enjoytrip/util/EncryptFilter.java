package com.ssafy.enjoytrip.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class EncryptFilter
 */
@WebFilter("/users")
public class EncryptFilter implements Filter {
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		HttpServletRequest req = (HttpServletRequest)request;
        EncryptWrapper encW = new EncryptWrapper(req);
        
		if("login".equals(action)) {
	        encW.getParameter("login-password");
		} else if("signup".equals(action)) {
	        encW.getParameter("signup-password");
		}
        
        chain.doFilter(encW, response);
	}
}
