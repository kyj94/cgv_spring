package com.mycgv_jsp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycgv_jsp.dao.MemberDao;
import com.mycgv_jsp.vo.MemberVo;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDao memberDao;
	
	@Override
	public int getLoginResult(MemberVo memberVo) {
		/* MemberDao memberDao = new MemberDao(); */
		return memberDao.loginCheck(memberVo);
	} // 로그인 체크
	
	@Override
	public String getIdCheckResult(String id) {
		/* MemberDao memberDao = new MemberDao(); */
		int result = memberDao.idCheck(id);
		return String.valueOf(result);
	} // 중복 아이디 체크
	
	@Override
	public int getJoinResult(MemberVo memberVo) {
		/* MemberDao memberDao = new MemberDao(); */
		return memberDao.insert(memberVo);
	} // 회원가입 체크
	
	@Override
	public ArrayList<MemberVo> getList(int startCount, int endCount) {
		/* MemberDao memberDao = new MemberDao(); */
		return memberDao.select(startCount, endCount);
	} // 회원관리 리스트
	
//	@Override
//	public int getTotalRowCount() {
//		MemberDao memberDao = new MemberDao();
//		return memberDao.totalRowCount();
//	} // 페이징 처리
	
		
}
