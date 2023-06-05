package com.mycgv_jsp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.service.MemberService;
import com.mycgv_jsp.vo.MemberVo;
import com.mycgv_jsp.vo.SessionVo;

@Controller
public class LoginController {
	
	@Autowired
	private MemberService memberService;
	
	/**
	 * login.do - �α��� ��
	 */
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String login() {
		return "/login/login";
	}
	
	
	/** login_proc.do - �α��� ó�� **/
	@RequestMapping(value="login_proc.do", method=RequestMethod.POST)
	public ModelAndView login_proc(MemberVo memberVo, HttpSession session) {
		ModelAndView model = new ModelAndView();
//		int result = memberService.getLoginResult(memberVo);
		SessionVo svo = memberService.getLoginResult(memberVo);
		
//		if(result() == 1) {
		if(svo.getLoginResult() == 1) {
			// index �̵�
			// viewName = "index"; viewResolver�� ȣ�� -> index.jsp
//			session.setAttribute("sid", memberVo.getId());
//			session.setAttribute("sid", memberVo.getId());
			session.setAttribute("svo", svo);
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
	
//	/** 
//	 * logout.do - �α׾ƿ�
//	 */
//	@RequestMapping(value="logout.do", method=RequestMethod.GET)
//	public String logout(HttpSession session) {
//		 String sid = (String)session.getAttribute("sid");
//	
//	if(sid != null) {
//		session.invalidate();
//	}
//	return "index";
//	}
	
	
	/** logout.do - �α׾ƿ� ó�� **/
	@RequestMapping(value="logout.do", method=RequestMethod.GET)
	public ModelAndView logout(HttpSession session) {
		ModelAndView model = new ModelAndView();
//		String sid = (String)session.getAttribute("sid");
		SessionVo svo = (SessionVo)session.getAttribute("svo");
		
//		if(sid != null) {
		if(svo != null) {
			session.invalidate();
			model.addObject("logout_result", "OK");
		}
		
		model.setViewName("index");
		
		return model;
	}

} // class
