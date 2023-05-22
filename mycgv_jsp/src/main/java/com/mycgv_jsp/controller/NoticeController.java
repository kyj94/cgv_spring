package com.mycgv_jsp.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.dao.AdminNoticeDao;
import com.mycgv_jsp.dao.BoardDao;
import com.mycgv_jsp.vo.AdminNoticeVo;
import com.mycgv_jsp.vo.BoardVo;

@Controller
public class NoticeController {
	
	/** notice_list.do - ������ �������� ����Ʈ ����  **/
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
	 * notice_list.do - �������� ��ü ����Ʈ _ ����¡
	 * @return
	 */
	@RequestMapping(value="/notice_list.do", method=RequestMethod.GET)
		public ModelAndView notice_list(String page) {
			ModelAndView model = new ModelAndView();		
			AdminNoticeDao adminNoticeDao = new AdminNoticeDao();
			
			//����¡ ó�� - startCount, endCount ���ϱ�
			int startCount = 0;
			int endCount = 0;
			int pageSize = 10;	//���������� �Խù� ��
			int reqPage = 1;	//��û������	
			int pageCount = 1;	//��ü ������ ��
			int dbCount = adminNoticeDao.totalRowCount();	//DB���� ������ ��ü ���
			
			//�� ������ �� ���
			if(dbCount % pageSize == 0){
				pageCount = dbCount/pageSize;
			}else{
				pageCount = dbCount/pageSize+1;
			}
	
			//��û ������ ���
			if(page != null){
				reqPage = Integer.parseInt(page);
				startCount = (reqPage-1) * pageSize+1; 
				endCount = reqPage * pageSize;
			}else{
				startCount = 1;
				endCount = pageSize;
			}
			
			ArrayList<AdminNoticeVo> list = adminNoticeDao.select(startCount, endCount);
		
			model.addObject("list", list);
			model.addObject("totals", dbCount);
			model.addObject("pageSize", pageSize);
			model.addObject("maxSize", pageCount);
			model.addObject("page", reqPage);
			
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
