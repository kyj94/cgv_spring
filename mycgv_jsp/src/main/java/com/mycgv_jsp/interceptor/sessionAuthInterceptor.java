package com.mycgv_jsp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class sessionAuthInterceptor extends HandlerInterceptorAdapter {
	/**
	 * preHandle: Controller에 접근하기 전에 수행되는 메소드
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		    throws Exception {
		// 클라이언트(브라우저)의 요청 확인 - 세션 객체 가져오기
		HttpSession session = request.getSession();
		
		// sid 확인하기
		String sid = (String)session.getAttribute("sid");
		if(sid == null) {
			// 로그인 안되어 있는 상태이므로 로그인폼으로 전송
			response.sendRedirect("/mycgv_jsp/login.do");
			return false;
		}
		
		
			return true;
		}

} // class
