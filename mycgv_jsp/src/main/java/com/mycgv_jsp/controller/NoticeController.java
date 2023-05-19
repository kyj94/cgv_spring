package com.mycgv_jsp.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.dao.AdminNoticeDao;
import com.mycgv_jsp.vo.AdminNoticeVo;

@Controller
public class NoticeController {
	
	/** notice_list.do - 관리자 공지사항 리스트 보기  **/
	@RequestMapping(value="/notice_list.do", method=RequestMethod.GET)
	public ModelAndView notice_list() {
		ModelAndView model = new ModelAndView();
		AdminNoticeDao adminNoticeDao = new AdminNoticeDao();
		ArrayList<AdminNoticeVo> list = adminNoticeDao.select();
		
		model.addObject("list", list);
		model.setViewName("/notice/notice_list");
		
		return model;
	}
	
	
	/** notice_content.do - 공지사항 내용  **/
	@RequestMapping(value="/notice_content.do", method=RequestMethod.GET)
	public ModelAndView notice_content(String nid) {
		ModelAndView model = new ModelAndView();
		AdminNoticeDao adminNoticeDao = new AdminNoticeDao();
		AdminNoticeVo adminNoticeVo = adminNoticeDao.select(nid);
		
		// 조회수 업데이트-DB -> 회원들만 카운트
		 if(adminNoticeVo != null) { 
			 adminNoticeDao.updateHits(nid); 
		}
		 
		
		model.addObject("notice", adminNoticeVo);
		model.setViewName("/notice/notice_content");
		return model;
	}
	
	
	

}
