package com.mycgv_jsp.service;

import java.util.ArrayList;

import com.mycgv_jsp.vo.AdminNoticeVo;

public interface NoticeService {
	
	public int getInsert(AdminNoticeVo noticeVo);
	public ArrayList<AdminNoticeVo> getSelect(int startCount, int endCount); 
	public AdminNoticeVo Select(String nid);
	public int getUpdate(AdminNoticeVo noticeVo); 
	public int getDelete(String nid);
	public int getTotalRowCount();
	public void getUdpateHits(String nid);
}
