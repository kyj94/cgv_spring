package com.mycgv_jsp.service;

import java.util.ArrayList;

import com.mycgv_jsp.dao.BoardDao;
import com.mycgv_jsp.vo.BoardVo;

public class BoardServiceImpl implements BoardService {
	
	private BoardDao boardDao = new BoardDao();
	
	@Override
	public int getWrite(BoardVo boardVo) {
		return boardDao.insert(boardVo);
	}
	
	@Override
	public ArrayList<BoardVo> getList(int startCount, int endCount) {
		return boardDao.select(startCount, endCount);
	}
	
	@Override
	public BoardVo getBid(String bid) {
		return boardDao.select(bid);
	}
	
	@Override
	public int getUpdate(BoardVo boardVo) {
		return boardDao.update(boardVo);
	}
	
	@Override
	public int getDelete(String bid) {
		return boardDao.delete(bid);
	}
	
	@Override
	public int getTotalRowCount() {
		return boardDao.totalRowCount();
	}
	
	@Override
	public int getUdpateHits(String bid) {
		return boardDao.updateHits(bid);
	}

}
