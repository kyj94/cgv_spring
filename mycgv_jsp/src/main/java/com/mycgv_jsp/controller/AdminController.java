package com.mycgv_jsp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.dao.AdminNoticeDao;
import com.mycgv_jsp.service.MemberService;
import com.mycgv_jsp.vo.AdminNoticeVo;
import com.mycgv_jsp.vo.MemberVo;

@Controller
public class AdminController {
	
	@Autowired
	private MemberService memberService;
	
	/** admin_index - 관리자 공지사항 페이지  **/
	@RequestMapping(value="/admin_index.do", method=RequestMethod.GET)
	public String admin_index() {
		return "/admin/admin_index";
	}
	
	
	/** admin_notice_list.do - 관리자 공지사항 리스트  **/
	/*
	 * @RequestMapping(value="/admin_notice_list.do", method=RequestMethod.GET)
	 * public ModelAndView admin_notice_list() { ModelAndView model = new
	 * ModelAndView(); AdminNoticeDao adminNoticeDao = new AdminNoticeDao();
	 * ArrayList<AdminNoticeVo> list = adminNoticeDao.select();
	 * 
	 * model.addObject("list", list);
	 * model.setViewName("/admin/notice/admin_notice_list");
	 * 
	 * return model; }
	 */
	
	
	/** admin_notice_list.do - 관리자 공지사항 리스트_페이징  **/
	@RequestMapping(value="/admin_notice_list.do", method=RequestMethod.GET)
	public ModelAndView admin_notice_list(String page) {
		ModelAndView model = new ModelAndView();
		AdminNoticeDao adminNoticeDao = new AdminNoticeDao();
		
		//페이징 처리 - startCount, endCount 구하기
				int startCount = 0;
				int endCount = 0;
				int pageSize = 5;	//한페이지당 게시물 수
				int reqPage = 1;	//요청페이지	
				int pageCount = 1;	//전체 페이지 수
				int dbCount = adminNoticeDao.totalRowCount();	//DB에서 가져온 전체 행수
				
				//총 페이지 수 계산
				if(dbCount % pageSize == 0){
					pageCount = dbCount/pageSize;
				}else{
					pageCount = dbCount/pageSize+1;
				}

				//요청 페이지 계산
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
				
				model.setViewName("/admin/notice/admin_notice_list");
		
			return model;
		}
	
	
	/** admin_notice_content.do - 관리자 공지사항 내용  **/
	@RequestMapping(value="/admin_notice_content.do", method=RequestMethod.GET)
	public ModelAndView notice_update_proc(String nid) {
		ModelAndView model = new ModelAndView();
		AdminNoticeDao adminNoticeDao = new AdminNoticeDao();
		AdminNoticeVo adminNoticeVo = adminNoticeDao.select(nid);
		
		// 조회수 업데이트-DB -> 여기서 진행되면 안됨. 회원들만 카운트
		/*
		 * if(adminNoticeVo != null) { adminNoticeDao.updateHits(nid); }
		 */
		
		model.addObject("adNot", adminNoticeVo);
		model.setViewName("/admin/notice/admin_notice_content");
		return model;
	}
	
	
	/** admin_notice_write.do - 관리자 공지사항 작성  **/
	@RequestMapping(value="/admin_notice_write.do", method=RequestMethod.GET)
	public String admin_notice_write() {
		return "/admin/notice/admin_notice_write";
	}
	
	
	/** admin_write_proc.do - 관리자 공지사항 작성 처리  **/
	@RequestMapping(value="/admin_write_proc.do", method=RequestMethod.POST)
	public String admin_write_proc(AdminNoticeVo adminNoticeVo) {
		String viewName = "";
		AdminNoticeDao adminNoticeDao = new AdminNoticeDao();
		int result = adminNoticeDao.insert(adminNoticeVo);
			
			if(result == 1) {
				viewName = "redirect:/admin_notice_list.do";
			}  else {
				// 에러 페이지 호출
			}
			
		return viewName;
	}
	
	
	/** admin_notice_update.do - 관리자 공지사항 수정하기  **/
	@RequestMapping(value="/admin_notice_update.do", method=RequestMethod.GET)
	public ModelAndView admin_notice_update(String nid) {
		
		ModelAndView model = new ModelAndView();
		AdminNoticeDao adminNoticeDao = new AdminNoticeDao();
		AdminNoticeVo adminNoticeVo = adminNoticeDao.select(nid);
		
			model.addObject("adminNoticeVo", adminNoticeVo);
			model.setViewName("/admin/notice/admin_notice_update");
		
		return model;
	}
	
	
	/** notice_update_proc.do - 관리자 공지사항 수정 처리 **/
	@RequestMapping(value="/notice_update_proc.do", method=RequestMethod.POST)
	public String notice_update_proc(AdminNoticeVo adminNoticeVo) {
		String viewName = "";
		
		AdminNoticeDao adminNoticeDao = new AdminNoticeDao();
		int result = adminNoticeDao.update(adminNoticeVo);
		
		if(result == 1) {
			viewName = "redirect:/admin_notice_list.do";
		}  else {
			// 에러 페이지 호출
		}
		
		return viewName;
	}
	
	
	/** admin_notice_delete.do - 관리자 공지사항 삭제 **/
	@RequestMapping(value="/admin_notice_delete.do", method=RequestMethod.GET)
	public ModelAndView admin_notice_delete(String nid) {
		ModelAndView model = new ModelAndView();
		model.addObject("nid", nid);
		model.setViewName("/admin/notice/admin_notice_delete");
		return model;
	}
	
	
	/** notice_delete_proc.do - 관리자 공지사항 삭제 처리 **/
	@RequestMapping(value="/notice_delete_proc.do", method=RequestMethod.POST)
	public String notice_delete_proc(String nid) {
		String viewName = "";
		
		AdminNoticeDao adminNOticeDao = new AdminNoticeDao();
		int result = adminNOticeDao.delete(nid);
		
		if(result == 1) {
			viewName = "redirect:/admin_notice_list.do";
		}  else {
			// 에러 페이지 호출
		}
		
		return viewName;
	}
	
	
	/** admin_member_list.do - 관리자 회원관리 리스트  **/
	@RequestMapping(value="/admin_member_list.do", method=RequestMethod.GET)
	public ModelAndView admin_member_list(String page) {
		ModelAndView model = new ModelAndView();
		
		//페이징 처리 - startCount, endCount 구하기
		int startCount = 0;
		int endCount = 0;
		int pageSize = 5;	//한페이지당 게시물 수
		int reqPage = 1;	//요청페이지	
		int pageCount = 1;	//전체 페이지 수
		int dbCount = memberService.getTotalRowCount();	//DB에서 가져온 전체 행수
		
		//총 페이지 수 계산
		if(dbCount % pageSize == 0){
			pageCount = dbCount/pageSize;
		}else{
			pageCount = dbCount/pageSize+1;
		}

		//요청 페이지 계산
		if(page != null){
			reqPage = Integer.parseInt(page);
			startCount = (reqPage-1) * pageSize+1; 
			endCount = reqPage * pageSize;
		}else{
			startCount = 1;
			endCount = pageSize;
		}
		
		ArrayList<MemberVo> list = memberService.getList(startCount, endCount);
		
		model.addObject("list", list);
		model.addObject("totals", dbCount);
		model.addObject("pageSize", pageSize);
		model.addObject("maxSize", pageCount);
		model.addObject("page", reqPage);
		
		model.setViewName("/admin/member/admin_member_list");
		
		return model;
	}
	
	
	
	
	
	
} // class
