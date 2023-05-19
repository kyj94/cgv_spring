package com.mycgv_jsp.dao;

import java.util.ArrayList;

import com.mycgv_jsp.vo.AdminNoticeVo;

public class AdminNoticeDao extends DBConn {

	/** select - �������� ��ü ����Ʈ **/
	public ArrayList<AdminNoticeVo> select() {
		ArrayList<AdminNoticeVo> list = new ArrayList<AdminNoticeVo>();

		String sql = "SELECT ROWNUM RNO, NID, NTITLE, NCONTENT, NHITS, to_char(NDATE, 'yyyy-mm-dd') NDATE"
				+ " FROM (SELECT * FROM MYCGV_ADMIN_NOTICE" + " ORDER BY NDATE DESC)";
		getPreparedStatement(sql);

		try {
			rs = pstmt.executeQuery();
			while (rs.next()) {
				AdminNoticeVo adminNoticeVo = new AdminNoticeVo();
				adminNoticeVo.setRno(rs.getInt(1));
				adminNoticeVo.setNid(rs.getString(2));
				adminNoticeVo.setNtitle(rs.getString(3));
				adminNoticeVo.setNcontent(rs.getString(4));
				adminNoticeVo.setNhits(rs.getInt(5));
				adminNoticeVo.setNdate(rs.getString(6));

				list.add(adminNoticeVo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	} // ArrayList<AdminNoticeVo> select()

	/** select(bid) - �������� �󼼺��� **/
	public AdminNoticeVo select(String nid) {
		AdminNoticeVo adminNoticeVo = new AdminNoticeVo();

		String sql = "SELECT NID, NTITLE, NCONTENT, NHITS, NDATE FROM MYCGV_ADMIN_NOTICE" + " WHERE NID = ?";
		getPreparedStatement(sql);

		try {
			pstmt.setString(1, nid);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				adminNoticeVo.setNid(rs.getString(1));
				adminNoticeVo.setNtitle(rs.getString(2));
				adminNoticeVo.setNcontent(rs.getString(3));
				adminNoticeVo.setNhits(rs.getInt(4));
				adminNoticeVo.setNdate(rs.getString(5));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return adminNoticeVo;
	} // select(String nid)

	/** ������ �������� ��� **/
	public int insert(AdminNoticeVo adminNoticeVo) {
		int result = 0;

		String sql = "INSERT INTO mycgv_admin_notice(nid, NTITLE, NCONTENT, NHITS, NDATE)"
				+ " VALUES ('n_'||LTRIM(to_char(SEQU_MYCGV_ADMIN_NOTICE.nextVal, '0000')), ?, ?, 0, sysdate)";

		getPreparedStatement(sql);

		try {
			pstmt.setString(1, adminNoticeVo.getNtitle());
			pstmt.setString(2, adminNoticeVo.getNcontent());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	} // insert(AdminNoticeVo adminNoticeVo)

	/** update - �������� �����ϱ� **/
	public int update(AdminNoticeVo adminNoticeVo) {
		int result = 0;

		String sql = "update MYCGV_ADMIN_NOTICE set ntitle=?, ncontent=? where nid=?";
		getPreparedStatement(sql);

		try {
			pstmt.setString(1, adminNoticeVo.getNtitle());
			pstmt.setString(2, adminNoticeVo.getNcontent());
			pstmt.setString(3, adminNoticeVo.getNid());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	} // update(AdminNoticeVo adminNoticeVo)

	/** delete - �������� �����ϱ� **/
	public int delete(String nid) {
		int result = 0;

		String sql = "delete FROM MYCGV_ADMIN_NOTICE where nid=? ";
		getPreparedStatement(sql);

		try {
			pstmt.setString(1, nid);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	} // int delete(String nid)

	
	  // updateHits - �Խ��� ��ȸ�� - void VER 
		public void updateHits(String nid) {
		  String sql = "UPDATE MYCGV_ADMIN_NOTICE SET NHITS=NHITS+1 WHERE NID=?";
		  getPreparedStatement(sql);
		  
		  try { pstmt.setString(1, nid); pstmt.executeUpdate();
		  
		  } catch (Exception e) { e.printStackTrace(); } 
	  } 
		// void updateHits(String nid)
	

}