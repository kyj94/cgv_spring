package com.mycgv_jsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycgv_jsp.dao.MemberDao;
import com.mycgv_jsp.vo.MemberVo;

@Controller
public class JoinController {
	/**
	 * join.do - 회원가입 폼
	 */
	@RequestMapping(value="join.do", method=RequestMethod.GET)
	public String join() {
		return "/join/join";
	}
	
	/** join_proc.do - 회원가입 처리 **/
	@RequestMapping(value="join_proc.do", method=RequestMethod.POST)
	public String join_proc(MemberVo memverVo) {
		String viewName ="";
		MemberDao memberDao = new MemberDao();
		int result = memberDao.insert(memverVo);
		
		if(result == 1) {
			viewName = "redirect:/index.do";
		} else {
			// 회원가입 실패 - 에러페이지 호출
		}
		
		return viewName;
	}
	
	
	/** id_check.do - 아이디 중복체크 처리 **/
	@RequestMapping(value="id_check.do", method=RequestMethod.GET)
	@ResponseBody
	public String id_check(String id) {
		System.out.println(id);
		// String id = request.getParameter("id");
		MemberDao memberDao = new MemberDao();
		int result = memberDao.idCheck(id);
		
		return String.valueOf(result);
	}

}
