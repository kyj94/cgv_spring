package com.mycgv_jsp.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.mycgv_jsp.vo.BoardVo;

@Service
public interface BoardService {
	public int getWrite(BoardVo boardVo); // getInsert客 悼老
	public ArrayList<BoardVo> getList(int startCount, int endCount); // getSelect客 悼老
	public BoardVo getBid(String bid); // getSelect客 悼老
	public int getUpdate(BoardVo boardVo); 
	public int getDelete(String bid);
//	public int getTotalRowCount();
	public void getUdpateHits(String bid);
}
