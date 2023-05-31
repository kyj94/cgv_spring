package com.mycgv_jsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycgv_jsp.vo.AdminNoticeVo;

@Repository
public class AdminNoticeDao extends DBConn {
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	/** select - 공지사항 전체 리스트 **/
	public ArrayList<AdminNoticeVo> select() {
		List<AdminNoticeVo> list = sqlSession.selectList("mapper.notice.list2");
		return (ArrayList<AdminNoticeVo>) list;
		
//		ArrayList<AdminNoticeVo> list = new ArrayList<AdminNoticeVo>();
//
//		String sql = "SELECT ROWNUM RNO, NID, NTITLE, NCONTENT, NHITS, to_char(NDATE, 'yyyy-mm-dd') NDATE"
//				+ " FROM (SELECT * FROM MYCGV_ADMIN_NOTICE" + " ORDER BY NDATE DESC)";
//		getPreparedStatement(sql);
//
//		try {
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				AdminNoticeVo adminNoticeVo = new AdminNoticeVo();
//				adminNoticeVo.setRno(rs.getInt(1));
//				adminNoticeVo.setNid(rs.getString(2));
//				adminNoticeVo.setNtitle(rs.getString(3));
//				adminNoticeVo.setNcontent(rs.getString(4));
//				adminNoticeVo.setNhits(rs.getInt(5));
//				adminNoticeVo.setNdate(rs.getString(6));
//
//				list.add(adminNoticeVo);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return list;
	} // ArrayList<AdminNoticeVo> select()
	
	
	/** select - 게시글 전체 리스트 _ 페이징처리- startCount, endCount **/
	public ArrayList<AdminNoticeVo> select(int startCount, int endCount) {
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("start", startCount);
		param.put("end", endCount);
		
		List<AdminNoticeVo> list = sqlSession.selectList("mapper.notice.list", param);
		return (ArrayList<AdminNoticeVo>) list;
		
//		ArrayList<AdminNoticeVo> list = new ArrayList<AdminNoticeVo>();	
//		
//		String sql= "SELECT RNO, NID, NTITLE, NCONTENT, NHITS, NDATE" + 
//				" FROM (SELECT ROWNUM RNO, NID, NTITLE, NCONTENT, NHITS, TO_CHAR(NDATE, 'YYYY-MM-DD') NDATE" + 
//				"      FROM (SELECT * FROM MYCGV_ADMIN_NOTICE" + 
//				"            ORDER BY NDATE DESC))" + 
//				" WHERE RNO BETWEEN ? AND ?";
//		
//		getPreparedStatement(sql);
//		
//		try {
//			pstmt.setInt(1, startCount);
//			pstmt.setInt(2, endCount);
//			
//			rs = pstmt.executeQuery();
//			while(rs.next()) {
//				AdminNoticeVo adminNoticeVo = new AdminNoticeVo();
//				adminNoticeVo.setRno(rs.getInt(1));
//				adminNoticeVo.setNid(rs.getString(2));
//				adminNoticeVo.setNtitle(rs.getString(3));
//				adminNoticeVo.setNcontent(rs.getString(4));
//				adminNoticeVo.setNhits(rs.getInt(5));
//				adminNoticeVo.setNdate(rs.getString(6));
//
//				list.add(adminNoticeVo);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return list;
	} // ArrayList<BoardVo> select()
	

	/** select(bid) - 공지사항 상세보기 **/
	public AdminNoticeVo select(String nid) {
		return sqlSession.selectOne("mapper.notice.content", nid);
		
//		AdminNoticeVo adminNoticeVo = new AdminNoticeVo();
//
//		String sql = "SELECT NID, NTITLE, NCONTENT, NHITS, NDATE FROM MYCGV_ADMIN_NOTICE" + " WHERE NID = ?";
//		getPreparedStatement(sql);
//
//		try {
//			pstmt.setString(1, nid);
//
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				adminNoticeVo.setNid(rs.getString(1));
//				adminNoticeVo.setNtitle(rs.getString(2));
//				adminNoticeVo.setNcontent(rs.getString(3));
//				adminNoticeVo.setNhits(rs.getInt(4));
//				adminNoticeVo.setNdate(rs.getString(5));
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return adminNoticeVo;
	} // select(String nid)
	

	/** 관리자 공지사항 등록 **/
	public int insert(AdminNoticeVo adminNoticeVo) {
		return sqlSession.insert("mapper.notice.insert", adminNoticeVo);
		
//		int result = 0;
//
//		String sql = "INSERT INTO mycgv_admin_notice(nid, NTITLE, NCONTENT, NHITS, NDATE)"
//				+ " VALUES ('n_'||LTRIM(to_char(SEQU_MYCGV_ADMIN_NOTICE.nextVal, '0000')), ?, ?, 0, sysdate)";
//
//		getPreparedStatement(sql);
//
//		try {
//			pstmt.setString(1, adminNoticeVo.getNtitle());
//			pstmt.setString(2, adminNoticeVo.getNcontent());
//
//			result = pstmt.executeUpdate();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;

	} // insert(AdminNoticeVo adminNoticeVo)
	

	/** update - 공지사항 수정하기 **/
	public int update(AdminNoticeVo adminNoticeVo) {
		return sqlSession.update("mapper.notice.update", adminNoticeVo);
		
//		int result = 0;
//
//		String sql = "update MYCGV_ADMIN_NOTICE set ntitle=?, ncontent=? where nid=?";
//		getPreparedStatement(sql);
//
//		try {
//			pstmt.setString(1, adminNoticeVo.getNtitle());
//			pstmt.setString(2, adminNoticeVo.getNcontent());
//			pstmt.setString(3, adminNoticeVo.getNid());
//
//			result = pstmt.executeUpdate();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return result;
	} // update(AdminNoticeVo adminNoticeVo)
	

	/** delete - 공지사항 삭제하기 **/
	public int delete(String nid) {
		return sqlSession.delete("mapper.notice.delete", nid);
		
//		int result = 0;
//
//		String sql = "delete FROM MYCGV_ADMIN_NOTICE where nid=? ";
//		getPreparedStatement(sql);
//
//		try {
//			pstmt.setString(1, nid);
//			result = pstmt.executeUpdate();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return result;
	} // int delete(String nid)

	
	  // updateHits - 게시판 조회수 - void VER 
		public void updateHits(String nid) {
			sqlSession.update("mapper.notice.updateHits", nid);
			
//		  String sql = "UPDATE MYCGV_ADMIN_NOTICE SET NHITS=NHITS+1 WHERE NID=?";
//		  getPreparedStatement(sql);
//		  
//		  try { pstmt.setString(1, nid); pstmt.executeUpdate();
//		  
//		  } catch (Exception e) { e.printStackTrace(); } 
	  } 
		// void updateHits(String nid)
		
		
		/** 전체 카운트 가져오기 _ 페이징 처리 **/
		public int totalRowCount() {
				int count = 0;
				String sql = "select count(*) from mycgv_admin_notice";
				getPreparedStatement(sql);
				
				try {
					rs = pstmt.executeQuery();
					while(rs.next()) {				
						count = rs.getInt(1);
					}			
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return count;		
			} // int totalRowCount()
		
	

}
