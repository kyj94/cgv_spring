package com.mycgv_jsp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.dao.MemberDao;
import com.mycgv_jsp.vo.MemberVo;

@Controller
public class LoginController {
	/**
	 * login.do - �α��� ��
	 */
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String login() {
		return "/login/login";
	}
	
	/** login_proc.do - �α��� ó�� **/
	@RequestMapping(value="login_proc.do", method=RequestMethod.POST)
	public ModelAndView login_proc(MemberVo memberVo) {
		ModelAndView model = new ModelAndView();
		MemberDao memberDao = new MemberDao();
		int result = memberDao.loginCheck(memberVo);
		
		if(result == 1) {
			// index �̵�
			// viewName = "index"; viewResolver�� ȣ�� -> index.jsp
			model.addObject("login_result", "OK");
			model.setViewName("index"); // sendRedirect�� ����
		} else {
			// login_fail.jsp
			model.setViewName("redirect:/login_fail.do");
		}
		
		return model;
	}
	
	
	/** login_fail.do - �α��� ���� **/
	@RequestMapping(value="login_fail.do", method=RequestMethod.GET)
	public String login_fail() {
		return "/login/login_fail";
	}
	
	
	

} // class