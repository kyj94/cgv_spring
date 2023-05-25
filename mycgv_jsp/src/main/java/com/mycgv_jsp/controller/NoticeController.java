package com.mycgv_jsp.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.service.NoticeService;
import com.mycgv_jsp.service.PageServiceImpl;
import com.mycgv_jsp.vo.AdminNoticeVo;

@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private PageServiceImpl pageService;
	
	/** notice_list.do - 관리자 공지사항 리스트 보기  **/
	/*
	 * @RequestMapping(value="/notice_list.do", method=RequestMethod.GET) public
	 * ModelAndView notice_list() { ModelAndView model = new ModelAndView();
	 * AdminNoticeDao adminNoticeDao = new AdminNoticeDao();
	 * ArrayList<AdminNoticeVo> list = adminNoticeDao.select();
	 * 
	 * model.addObject("list", list); model.setViewName("/notice/notice_list");
	 * 
	 * return model; }
	 */
	
	
	/**
	 * notice_list.do - 공지사항 전체 리스트 _ 페이징
	 * @return
	 */
	@RequestMapping(value="/notice_list.do", method=RequestMethod.GET)
		public ModelAndView notice_list(String page) {
			ModelAndView model = new ModelAndView();
			
			Map<String, Integer> param = pageService.getPageResult(page, "notice");
			ArrayList<AdminNoticeVo> list = noticeService.getSelect(param.get("startCount"), param.get("endCount"));
		
			model.addObject("list", list);
			model.addObject("totals", param.get("dbCount"));
			model.addObject("pageSize", param.get("pageSize"));
			model.addObject("maxSize", param.get("maxSize"));
			model.addObject("page", param.get("page"));
			
			model.setViewName("/notice/notice_list");
			
			return model;
		} 
	
	
	/** notice_content.do - 공지사항 내용  **/
	@RequestMapping(value="/notice_content.do", method=RequestMethod.GET)
	public ModelAndView notice_content(String nid) {
		ModelAndView model = new ModelAndView();
		AdminNoticeVo adminNoticeVo = noticeService.Select(nid);
		
		// 조회수 업데이트-DB -> 회원들만 카운트
		 if(adminNoticeVo != null) { 
			 noticeService.getUdpateHits(nid); 
		}
		 
		
		model.addObject("notice", adminNoticeVo);
		model.setViewName("/notice/notice_content");
		return model;
	}
	
	
	

}
