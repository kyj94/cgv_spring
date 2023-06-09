package com.mycgv_jsp.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mycgv_jsp.service.BoardService;
import com.mycgv_jsp.service.FileServiceImpl;
import com.mycgv_jsp.service.PageServiceImpl;
import com.mycgv_jsp.vo.BoardVo;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	@Autowired
	private PageServiceImpl pageService;
	@Autowired
	private FileServiceImpl fileService;
	
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
		Map<String, Integer> param = pageService.getPageResult(page, "board");
		ArrayList<BoardVo> list = boardService.getList(param.get("startCount"), param.get("endCount"));
	
		model.addObject("list", list);
		model.addObject("totals", param.get("dbCount"));
		model.addObject("pageSize", param.get("pageSize"));
		model.addObject("maxSize", param.get("maxSize"));
		model.addObject("page", param.get("page"));
		
		model.setViewName("/board/board_list");
		
		return model;
	} 
	
	
	/** board_lsit_json_data.do - ajax���� ȣ��Ǵ� �Խñ� ��ü ����Ʈ_json **/
	@RequestMapping(value="/board_list_json_data.do", method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String board_list_json_data(String page) {
		Map<String, Integer> param = pageService.getPageResult(page, "board");
		ArrayList<BoardVo> list = boardService.getList(param.get("startCount"), param.get("endCount"));
		
		// list ��ü�� �����͸� JSON ���·� ����
		JsonObject jlist = new JsonObject();
		JsonArray jarray = new JsonArray();
		
		for(BoardVo boardVo : list) {
			JsonObject jobj = new JsonObject(); //{}
			jobj.addProperty("rno", boardVo.getRno()); // {rno:1}
			jobj.addProperty("btitle", boardVo.getBtitle()); //{rno:1, btitle:~~~}
			jobj.addProperty("bhits", boardVo.getBhits());
			jobj.addProperty("id", boardVo.getId());
			jobj.addProperty("bdate", boardVo.getBdate());
			
			jarray.add(jobj);
		}
		
		jlist.add("jlist", jarray); // = model.addObject("list", list); 
		jlist.addProperty("totals", param.get("dbCount")); // = model.addObject("totals", dbCount);
		jlist.addProperty("pageSize", param.get("pageSize")); // = model.addObject("pageSize", pageSize);
		jlist.addProperty("maxSize", param.get("maxSize")); // = model.addObject("maxSize", pageCount);
		jlist.addProperty("page", param.get("page")); // = model.addObject("page", reqPage);
		
		/* System.out.println(jlist.toString()); */
		
		return new Gson().toJson(jlist);
	} 
	
	
	// header �Խ���(JSON) ȣ��Ǵ� �ּ�
	@RequestMapping(value="/board_list_json.do", method=RequestMethod.GET)
	public String board_list_json() {
		return "/board/board_list_json";
	}
	
	
	/** board_content.do - �Խñ� �� ���� **/
	@RequestMapping(value="/board_content.do", method=RequestMethod.GET)
	public ModelAndView board_content(String bid) {
		ModelAndView model = new ModelAndView();
		BoardVo boardVo = boardService.getBid(bid);
		
		// ��ȸ�� ������Ʈ-DB
		if(boardVo != null) {
			boardService.getUdpateHits(bid);
		} // ��ȸ�� ������Ʈ-DB
		
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
	public String board_write_proc(BoardVo boardVo, HttpServletRequest request) throws Exception {
		// 1. ������ �Ѿ���� ������ BoardVo�� ���
		// 2. BoardVo �����͸� Dao�� ����
		// 3. mycgv_board ���̺� insert
		String viewName = "";
		
		// bfile, bsfile ���ϸ� ����
//		// ������ ������ġ
//		String root_path = request.getSession().getServletContext().getRealPath("/");
//		String attach_path = "\\resources\\upload\\";
		
//		if(boardVo.getFile1().getOriginalFilename() != null 
//				&& ! boardVo.getFile1().getOriginalFilename().contentEquals("")) { // ������ �����ϸ�
//			
//			// BSFILE ���� �ߺ� ó��
//			UUID uuid = UUID.randomUUID();
//			String bfile = boardVo.getFile1().getOriginalFilename();
//			String bsfile = uuid + "_" + bfile;
////			System.out.println(bfile);
////			System.out.println(bsfile);
////			System.out.println(root_path+attach_path);
//			
//			boardVo.setBfile(bfile);
//			boardVo.setBsfile(bsfile);
//		} else {
////			System.out.println("���� ����");
//		}
		
//		BoardVo boardVo2 = fileService.fileCheck(boardVo);
//		int result = boardService.getWrite(boardVo);
		int result = boardService.getWrite(fileService.fileCheck(boardVo));
		
		if(result == 1) {
			// response.sendRedirect("http://localhost:9000/mycgv_jsp/board/board_list.jsp");
			// viewName = "/board/board_list";
			
//			// ������ �����ϸ� ������ ����
//			File saveFile = new File(root_path + attach_path + boardVo.getBsfile());
//			boardVo.getFile1().transferTo(saveFile);
//			if(boardVo.getBfile() != null && boardVo.getBfile().equals("")) {
			if(boardVo.getBfile() != null) {
				fileService.fileSave(boardVo, request);
			}
			
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
		BoardVo boardVo = boardService.getBid(bid);
		
		model.addObject("boardVo", boardVo);
		model.setViewName("/board/board_update");
		
		return model;
	}
	
	
	/** board_update_proc.do - �Խñ� ���� ó�� **/
	@RequestMapping(value="/board_update_proc.do", method=RequestMethod.POST)
	public String board_update_proc(BoardVo boardVo, HttpServletRequest request) throws Exception {
		String viewName = "";
		
		String oldFileName = boardVo.getBsfile(); // ���ο� ���� ������Ʈ �� �������� ����
		
//		int result = boardService.getUpdate(boardVo);
		int result = boardService.getUpdate(fileService.fileCheck(boardVo));
		
		if(result == 1) {
			if(boardVo.getBfile() != null && boardVo.getBsfile().equals("")) {
				fileService.fileSave(boardVo, request);
				// ���� ���� ����
				fileService.fileDelete(boardVo, request, oldFileName);
			}
			viewName = "redirect:/board_list.do";
		}  else {
			// ���� ������ ȣ��
		}
		
		return viewName;
	}
	
	
	/** board_delete.do - �Խñ� ���� **/
	@RequestMapping(value="/board_delete.do", method=RequestMethod.GET)
	public ModelAndView board_delete(String bid, String bsfile) {
		ModelAndView model = new ModelAndView();
		model.addObject("bid", bid);
		model.addObject("bsfile", bsfile);
		model.setViewName("/board/board_delete");
		return model;
	}
	
	
	/** board_delete_proc.do - �Խñ� ���� ó�� **/
	@RequestMapping(value="/board_delete_proc.do", method=RequestMethod.POST)
	public String board_delete_proc(String bid, String bsfile, HttpServletRequest request) throws Exception {
		String viewName = "";
		int result = boardService.getDelete(bid);
		
		if(result == 1) {
			fileService.fileDelete(request, bsfile);
			viewName = "redirect:/board_list.do";
		}  else {
			// ���� ������ ȣ��
		}
		
		return viewName;
	}
	
} // class
