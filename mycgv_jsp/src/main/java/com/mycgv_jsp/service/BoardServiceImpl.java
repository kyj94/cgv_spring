package com.mycgv_jsp.service;

import java.util.ArrayList;

import com.mycgv_jsp.dao.BoardDao;
import com.mycgv_jsp.vo.BoardVo;

public class BoardServiceImpl implements BoardService {
	
	@Override
	public int getWrite(BoardVo boardVo) {
		BoardDao boardDao = new BoardDao();
		return boardDao.insert(boardVo);
	}
	
	@Override
	public ArrayList<BoardVo> getList(int startCount, int endCount) {
		BoardDao boardDao = new BoardDao();
		return boardDao.select(startCount, endCount);
	}
	
	@Override
	public BoardVo getBid(String bid) {
		BoardDao boardDao = new BoardDao();
		return boardDao.select(bid);
	}
	
	@Override
	public int getUpdate(BoardVo boardVo) {
		BoardDao boardDao = new BoardDao();
		return boardDao.update(boardVo);
	}
	
	@Override
	public int getDelete(String bid) {
		BoardDao boardDao = new BoardDao();
		return boardDao.delete(bid);
	}
	
	@Override
	public int getTotalRowCount() {
		BoardDao boardDao = new BoardDao();
		return boardDao.totalRowCount();
	}
	
	
	
	
	
	@Override
	public int getUdpateHits(String bid) {
		BoardDao boardDao = new BoardDao();
		return boardDao.updateHits(bid);
	}

}
