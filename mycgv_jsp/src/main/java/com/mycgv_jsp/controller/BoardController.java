package com.mycgv_jsp.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mycgv_jsp.dao.BoardDao;
import com.mycgv_jsp.vo.BoardVo;

@Controller
public class BoardController {
	
	/** board_list.do - 게시글 전체 리스트 **/
	/*
	 * @RequestMapping(value="/board_list.do", method=RequestMethod.GET) public
	 * ModelAndView board_list() { ModelAndView model = new ModelAndView();
	 * 
	 * // DB연동 후 Arraylist<BoardVo> BoardDao boardDao = new BoardDao();
	 * ArrayList<BoardVo> list = boardDao.select();
	 * 
	 * model.addObject("list", list); model.setViewName("/board/board_list");
	 * 
	 * return model; }
	 */
	
	
	/**
	 * board_list.do - 게시글 전체 리스트 _ 페이징
	 * @return
	 */
	@RequestMapping(value="/board_list.do", method=RequestMethod.GET)
	public ModelAndView board_list(String page) { // 요청하는 페이지
		ModelAndView model = new ModelAndView();		
		BoardDao boardDao = new BoardDao();
		
		//페이징 처리 - startCount, endCount 구하기
		int startCount = 0;
		int endCount = 0;
		int pageSize = 10;	//한페이지당 게시물 수
		int reqPage = 1;	//요청페이지	
		int pageCount = 1;	//전체 페이지 수
		int dbCount = boardDao.totalRowCount();	//DB에서 가져온 전체 행수
		
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
		
		ArrayList<BoardVo> list = boardDao.select(startCount, endCount);
	
		model.addObject("list", list);
		model.addObject("totals", dbCount);
		model.addObject("pageSize", pageSize);
		model.addObject("maxSize", pageCount);
		model.addObject("page", reqPage);
		
		model.setViewName("/board/board_list");
		
		return model;
	} 
	
	
	/** board_lsit_json_data.do - ajax에서 호출되는 게시글 전체 리스트_json **/
	@RequestMapping(value="/board_list_json_data.do", method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String board_list_json_data(String page) {
		BoardDao boardDao = new BoardDao();
		
		//페이징 처리 - startCount, endCount 구하기
				int startCount = 0;
				int endCount = 0;
				int pageSize = 5;	//한페이지당 게시물 수
				int reqPage = 1;	//요청페이지	
				int pageCount = 1;	//전체 페이지 수
				int dbCount = boardDao.totalRowCount();	//DB에서 가져온 전체 행수
				
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
				
				ArrayList<BoardVo> list = boardDao.select(startCount, endCount);
				
				// list 객체의 데이터를 JSON 형태로 생성
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
				jlist.addProperty("totals", dbCount); // = model.addObject("totals", dbCount);
				jlist.addProperty("pageSize", pageSize); // = model.addObject("pageSize", pageSize);
				jlist.addProperty("maxSize", pageCount); // = model.addObject("maxSize", pageCount);
				jlist.addProperty("page", reqPage); // = model.addObject("page", reqPage);
				
				/* System.out.println(jlist.toString()); */
				
				return new Gson().toJson(jlist);
			} 
	
	
	// header 게시판(JSON) 호출되는 주소
	@RequestMapping(value="/board_list_json.do", method=RequestMethod.GET)
	public String board_list_json() {
		return "/board/board_list_json";
	}
	
	
	/** board_content.do - 게시글 상세 보기 **/
	@RequestMapping(value="/board_content.do", method=RequestMethod.GET)
	public ModelAndView board_content(String bid) {
		ModelAndView model = new ModelAndView();
		
		BoardDao boardDao = new BoardDao();
		BoardVo boardVo = boardDao.select(bid);
		
		// 조회수 업데이트-DB
		if(boardVo != null) {
			boardDao.updateHits(bid);
		} // 조회수 업데이트-DB
		
		model.addObject("bvo", boardVo);
		model.setViewName("/board/board_content");
		
		return model;
	}
	
	/** board_write.do - 게시글쓰기 **/
	@RequestMapping(value="/board_write.do", method=RequestMethod.GET)
	public String board_write() {
		return "/board/board_write";
	}
	
	
	/** board_write_proc.do - 게시글 글쓰기 처리 **/
	@RequestMapping(value="/board_write_proc.do", method=RequestMethod.POST)
	public String board_write_proc(BoardVo boardVo) {
		// 1. 폼에서 넘어오는 데이터 BoardVo에 담기
		// 2. BoardVo 데이터를 Dao에 전송
		// 3. mycgv_board 데이블에 insert
		
		String viewName = "";
		BoardDao boardDao = new BoardDao();
		int result = boardDao.insert(boardVo);
		
		if(result == 1) {
			// response.sendRedirect("http://localhost:9000/mycgv_jsp/board/board_list.jsp");
			// viewName = "/board/board_list";
			viewName = "redirect:/board_list.do";
		}  else {
			// 에러 페이지 호출
		}
		
		return viewName;
	}
	
	
	/** board_update.do - 게시글 글쓰기 수정 **/
	@RequestMapping(value="/board_update.do", method=RequestMethod.GET)
	public ModelAndView board_update(String bid) {
		ModelAndView model = new ModelAndView();
		BoardDao boardDao = new BoardDao();
		BoardVo boardVo = boardDao.select(bid);
		
		model.addObject("boardVo", boardVo);
		model.setViewName("/board/board_update");
		
		return model;
	}
	
	
	/** board_update_proc.do - 게시글 수정 처리 **/
	@RequestMapping(value="/board_update_proc.do", method=RequestMethod.POST)
	public String board_update_proc(BoardVo boardVo) {
		String viewName = "";
		
		BoardDao boardDao = new BoardDao();
		int result = boardDao.update(boardVo);
		
		if(result == 1) {
			viewName = "redirect:/board_list.do";
		}  else {
			// 에러 페이지 호출
		}
		
		return viewName;
	}
	
	
	/** board_delete.do - 게시글 삭제 **/
	@RequestMapping(value="/board_delete.do", method=RequestMethod.GET)
	public ModelAndView board_delete(String bid) {
		ModelAndView model = new ModelAndView();
		model.addObject("bid", bid);
		model.setViewName("/board/board_delete");
		return model;
	}
	
	
	/** board_delete_proc.do - 게시글 삭제 처리 **/
	@RequestMapping(value="/board_delete_proc.do", method=RequestMethod.POST)
	public String board_delete_proc(String bid) {
		String viewName = "";
		
		BoardDao boardDao = new BoardDao();
		int result = boardDao.delete(bid);
		
		if(result == 1) {
			viewName = "redirect:/board_list.do";
		}  else {
			// 에러 페이지 호출
		}
		
		return viewName;
	}
	
} // class
