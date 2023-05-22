package com.mycgv_jsp.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.dao.BoardDao;
import com.mycgv_jsp.vo.BoardVo;

@Controller
public class BoardController {
	
	/** board_list.do - �Խñ� ��ü ����Ʈ **/
	/*
	 * @RequestMapping(value="/board_list.do", method=RequestMethod.GET) public
	 * ModelAndView board_list() { ModelAndView model = new ModelAndView();
	 * 
	 * // DB���� �� Arraylist<BoardVo> BoardDao boardDao = new BoardDao();
	 * ArrayList<BoardVo> list = boardDao.select();
	 * 
	 * model.addObject("list", list); model.setViewName("/board/board_list");
	 * 
	 * return model; }
	 */
	
	
	/**
	 * board_list.do - �Խñ� ��ü ����Ʈ _ ����¡
	 * @return
	 */
	@RequestMapping(value="/board_list.do", method=RequestMethod.GET)
	public ModelAndView board_list(String page) { // ��û�ϴ� ������
		ModelAndView model = new ModelAndView();		
		BoardDao boardDao = new BoardDao();
		
		//����¡ ó�� - startCount, endCount ���ϱ�
		int startCount = 0;
		int endCount = 0;
		int pageSize = 10;	//���������� �Խù� ��
		int reqPage = 1;	//��û������	
		int pageCount = 1;	//��ü ������ ��
		int dbCount = boardDao.totalRowCount();	//DB���� ������ ��ü ���
		
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
		
		ArrayList<BoardVo> list = boardDao.select(startCount, endCount);
	
		model.addObject("list", list);
		model.addObject("totals", dbCount);
		model.addObject("pageSize", pageSize);
		model.addObject("maxSize", pageCount);
		model.addObject("page", reqPage);
		
		model.setViewName("/board/board_list");
		
		return model;
	} 
	
	
	/** board_content.do - �Խñ� �� ���� **/
	@RequestMapping(value="/board_content.do", method=RequestMethod.GET)
	public ModelAndView board_content(String bid) {
		ModelAndView model = new ModelAndView();
		
		BoardDao boardDao = new BoardDao();
		BoardVo boardVo = boardDao.select(bid);
		
		// ��ȸ�� ������Ʈ-DB
		if(boardVo != null) {
			boardDao.updateHits(bid);
		}
		
		model.addObject("bvo", boardVo);
		model.setViewName("/board/board_content");
		
		return model;
	}
	
	/** board_write.do - �Խñ۾��� **/
	@RequestMapping(value="/board_write.do", method=RequestMethod.GET)
	public String board_write() {
		return "/board/board_write";
	}
	
	
	/** board_write_proc.do - �Խñ� �۾��� ó�� **/
	@RequestMapping(value="/board_write_proc.do", method=RequestMethod.POST)
	public String board_write_proc(BoardVo boardVo) {
		// 1. ������ �Ѿ���� ������ BoardVo�� ���
		// 2. BoardVo �����͸� Dao�� ����
		// 3. mycgv_board ���̺� insert
		
		String viewName = "";
		BoardDao boardDao = new BoardDao();
		int result = boardDao.insert(boardVo);
		
		if(result == 1) {
			// response.sendRedirect("http://localhost:9000/mycgv_jsp/board/board_list.jsp");
			// viewName = "/board/board_list";
			viewName = "redirect:/board_list.do";
		}  else {
			// ���� ������ ȣ��
		}
		
		return viewName;
	}
	
	
	/** board_update.do - �Խñ� �۾��� ���� **/
	@RequestMapping(value="/board_update.do", method=RequestMethod.GET)
	public ModelAndView board_update(String bid) {
		ModelAndView model = new ModelAndView();
		BoardDao boardDao = new BoardDao();
		BoardVo boardVo = boardDao.select(bid);
		
		model.addObject("boardVo", boardVo);
		model.setViewName("/board/board_update");
		
		return model;
	}
	
	
	/** board_update_proc.do - �Խñ� ���� ó�� **/
	@RequestMapping(value="/board_update_proc.do", method=RequestMethod.POST)
	public String board_update_proc(BoardVo boardVo) {
		String viewName = "";
		
		BoardDao boardDao = new BoardDao();
		int result = boardDao.update(boardVo);
		
		if(result == 1) {
			viewName = "redirect:/board_list.do";
		}  else {
			// ���� ������ ȣ��
		}
		
		return viewName;
	}
	
	
	/** board_delete.do - �Խñ� ���� **/
	@RequestMapping(value="/board_delete.do", method=RequestMethod.GET)
	public ModelAndView board_delete(String bid) {
		ModelAndView model = new ModelAndView();
		model.addObject("bid", bid);
		model.setViewName("/board/board_delete");
		return model;
	}
	
	
	/** board_delete_proc.do - �Խñ� ���� ó�� **/
	@RequestMapping(value="/board_delete_proc.do", method=RequestMethod.POST)
	public String board_delete_proc(String bid) {
		String viewName = "";
		
		BoardDao boardDao = new BoardDao();
		int result = boardDao.delete(bid);
		
		if(result == 1) {
			viewName = "redirect:/board_list.do";
		}  else {
			// ���� ������ ȣ��
		}
		
		return viewName;
	}

} // class
