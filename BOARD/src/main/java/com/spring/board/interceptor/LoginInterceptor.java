package com.spring.board.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter{

	LoginInterceptor(){
		System.out.println("interceptor load");
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler) throws Exception{
		if (request.getSession().getAttribute("id") == null) {
			String s = URLEncoder.encode("해당 기능은 로그인이 필요합니다.", "utf-8");
			response.sendRedirect(request.getContextPath() + "/login/login" + 
								"?msg=" + s);
			return false;
		}
		return true;
	}
}
