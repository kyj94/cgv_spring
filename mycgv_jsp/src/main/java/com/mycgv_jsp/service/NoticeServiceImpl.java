package com.mycgv_jsp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycgv_jsp.dao.AdminNoticeDao;
import com.mycgv_jsp.vo.AdminNoticeVo;


public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private AdminNoticeDao noticeDao;
	
	@Override
	public int getInsert(AdminNoticeVo noticeVo) {
		return noticeDao.insert(noticeVo);
	}
	
	@Override
	public ArrayList<AdminNoticeVo> getSelect(int startCount, int endCount) {
		return noticeDao.select(startCount, endCount);
	}
	
	@Override
	public AdminNoticeVo Select(String nid) {
		return noticeDao.select(nid);
	}
	
	@Override
	public int getUpdate(AdminNoticeVo noticeVo) {
		return noticeDao.update(noticeVo);
	}
	
	@Override
	public int getDelete(String nid) {
		return noticeDao.delete(nid);
	}
	
	@Override
	public int getTotalRowCount() {
		return noticeDao.totalRowCount();
	}
	
	@Override
	public void getUdpateHits(String nid) {
		noticeDao.updateHits(nid);
	}

}
