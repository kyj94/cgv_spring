package com.mycgv_jsp.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.service.MemberService;
import com.mycgv_jsp.service.NoticeService;
import com.mycgv_jsp.service.PageServiceImpl;
import com.mycgv_jsp.vo.AdminNoticeVo;
import com.mycgv_jsp.vo.MemberVo;

@Controller
public class AdminController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private PageServiceImpl pageService;
	
	/** admin_index - ������ �������� ������  **/
	@RequestMapping(value="/admin_index.do", method=RequestMethod.GET)
	public String admin_index() {
		return "/admin/admin_index";
	}
	
	
	/** admin_notice_list.do - ������ �������� ����Ʈ  **/
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
	
	
	/** admin_notice_list.do - ������ �������� ����Ʈ_����¡  **/
	@RequestMapping(value="/admin_notice_list.do", method=RequestMethod.GET)
	public ModelAndView admin_notice_list(String page) {
		ModelAndView model = new ModelAndView();
		Map<String, Integer> param = pageService.getPageResult(page, "notice");
		ArrayList<AdminNoticeVo> list = noticeService.getSelect(param.get("startCount"), param.get("endCount"));
		
				model.addObject("list", list);
				model.addObject("totals", param.get("dbCount"));
				model.addObject("pageSize", param.get("pageSize"));
				model.addObject("maxSize", param.get("maxSize"));
				model.addObject("page", param.get("page"));
				
				model.setViewName("/admin/notice/admin_notice_list");
		
			return model;
		}
	
	
	/** admin_notice_content.do - ������ �������� ����  **/
	@RequestMapping(value="/admin_notice_content.do", method=RequestMethod.GET)
	public ModelAndView notice_update_proc(String nid) {
		ModelAndView model = new ModelAndView();
		AdminNoticeVo adminNoticeVo = noticeService.Select(nid);
		
		// ��ȸ�� ������Ʈ-DB -> ���⼭ ����Ǹ� �ȵ�. ȸ���鸸 ī��Ʈ
		/*
		 * if(adminNoticeVo != null) { adminNoticeDao.updateHits(nid); }
		 */
		
		model.addObject("adNot", adminNoticeVo);
		model.setViewName("/admin/notice/admin_notice_content");
		return model;
	}
	
	
	/** admin_notice_write.do - ������ �������� �ۼ�  **/
	@RequestMapping(value="/admin_notice_write.do", method=RequestMethod.GET)
	public String admin_notice_write() {
		return "/admin/notice/admin_notice_write";
	}
	
	
	/** admin_write_proc.do - ������ �������� �ۼ� ó��  **/
	@RequestMapping(value="/admin_write_proc.do", method=RequestMethod.POST)
	public String admin_write_proc(AdminNoticeVo adminNoticeVo) {
		String viewName = "";
		int result = noticeService.getInsert(adminNoticeVo);
			
			if(result == 1) {
				viewName = "redirect:/admin_notice_list.do";
			}  else {
				// ���� ������ ȣ��
			}
			
		return viewName;
	}
	
	
	/** admin_notice_update.do - ������ �������� �����ϱ�  **/
	@RequestMapping(value="/admin_notice_update.do", method=RequestMethod.GET)
	public ModelAndView admin_notice_update(String nid) {
		
		ModelAndView model = new ModelAndView();
		AdminNoticeVo adminNoticeVo = noticeService.Select(nid);
		
			model.addObject("adminNoticeVo", adminNoticeVo);
			model.setViewName("/admin/notice/admin_notice_update");
		
		return model;
	}
	
	
	/** notice_update_proc.do - ������ �������� ���� ó�� **/
	@RequestMapping(value="/notice_update_proc.do", method=RequestMethod.POST)
	public String notice_update_proc(AdminNoticeVo adminNoticeVo) {
		String viewName = "";
		
		int result = noticeService.getUpdate(adminNoticeVo);
		
		if(result == 1) {
			viewName = "redirect:/admin_notice_list.do";
		}  else {
			// ���� ������ ȣ��
		}
		
		return viewName;
	}
	
	
	/** admin_notice_delete.do - ������ �������� ���� **/
	@RequestMapping(value="/admin_notice_delete.do", method=RequestMethod.GET)
	public ModelAndView admin_notice_delete(String nid) {
		ModelAndView model = new ModelAndView();
		model.addObject("nid", nid);
		model.setViewName("/admin/notice/admin_notice_delete");
		return model;
	}
	
	
	/** notice_delete_proc.do - ������ �������� ���� ó�� **/
	@RequestMapping(value="/notice_delete_proc.do", method=RequestMethod.POST)
	public String notice_delete_proc(String nid) {
		String viewName = "";
		int result = noticeService.getDelete(nid);
		
		if(result == 1) {
			viewName = "redirect:/admin_notice_list.do";
		}  else {
			// ���� ������ ȣ��
		}
		
		return viewName;
	}
	
	
	/** admin_member_list.do - ������ ȸ������ ����Ʈ  **/
	@RequestMapping(value="/admin_member_list.do", method=RequestMethod.GET)
	public ModelAndView admin_member_list(String page) {
		ModelAndView model = new ModelAndView();
		Map<String, Integer> param = pageService.getPageResult(page, "member");
		
		ArrayList<MemberVo> list = memberService.getList(param.get("startCount"), param.get("endCount"));
		
		model.addObject("list", list);
		model.addObject("totals", param.get("dbCount"));
		model.addObject("pageSize", param.get("pageSize"));
		model.addObject("maxSize", param.get("maxSize"));
		model.addObject("page", param.get("page"));
		
		model.setViewName("/admin/member/admin_member_list");
		
		return model;
	}
	
} // class
