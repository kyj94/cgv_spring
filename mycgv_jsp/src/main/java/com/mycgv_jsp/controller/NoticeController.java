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
	
	/** notice_list.do - ������ �������� ����Ʈ ����  **/
	@RequestMapping(value="/notice_list.do", method=RequestMethod.GET)
	public ModelAndView notice_list() {
		ModelAndView model = new ModelAndView();
		AdminNoticeDao adminNoticeDao = new AdminNoticeDao();
		ArrayList<AdminNoticeVo> list = adminNoticeDao.select();
		
		model.addObject("list", list);
		model.setViewName("/notice/notice_list");
		
		return model;
	}
	
	
	/** notice_content.do - �������� ����  **/
	@RequestMapping(value="/notice_content.do", method=RequestMethod.GET)
	public ModelAndView notice_content(String nid) {
		ModelAndView model = new ModelAndView();
		AdminNoticeDao adminNoticeDao = new AdminNoticeDao();
		AdminNoticeVo adminNoticeVo = adminNoticeDao.select(nid);
		
		// ��ȸ�� ������Ʈ-DB -> ȸ���鸸 ī��Ʈ
		 if(adminNoticeVo != null) { 
			 adminNoticeDao.updateHits(nid); 
		}
		 
		
		model.addObject("notice", adminNoticeVo);
		model.setViewName("/notice/notice_content");
		return model;
	}
	
	
	

}
