package com.mycgv_jsp.dao;

import java.util.ArrayList;

import com.mycgv_jsp.vo.BoardVo;

public class BoardDao extends DBConn {
	
	/** 게시글 등록 **/
	public int insert(BoardVo boardVo) {
		int result = 0;
		
		String sql = "INSERT INTO MYCGV_BOARD(bid, btitle, bcontent, bhits, id, bdate) "
				+ " VALUES ('b_'||LTRIM(to_char(sequ_mycgv_board.nextVal, '0000')), ?, ?, 0, ?, sysdate)";
		
		getPreparedStatement(sql);
		
		try {
			pstmt.setString(1, boardVo.getBtitle());
			pstmt.setString(2, boardVo.getBcontent());
			pstmt.setString(3, boardVo.getId());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return result;
		
	} // insert(BoardVo boardVo)
	
	/** select - 게시글 전체 리스트 **/
	public ArrayList<BoardVo> select() {
		ArrayList<BoardVo> list = new ArrayList<BoardVo>();
		
		String sql= "SELECT ROWNUM RNO, BID, BTITLE, BCONTENT, BHITS, ID, to_char(BDATE, 'yy-mm-dd') bdate" + 
				" FROM(SELECT * FROM MYCGV_BOARD" + 
				"      ORDER BY BDATE DESC)";
		getPreparedStatement(sql);
		
		try {
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardVo boardVo = new BoardVo();
				boardVo.setRno(rs.getInt(1));
				boardVo.setBid(rs.getString(2));
				boardVo.setBtitle(rs.getString(3));
				boardVo.setBcontent(rs.getString(4));
				boardVo.setBhits(rs.getInt(5));
				boardVo.setId(rs.getString(6));
				boardVo.setBdate(rs.getString(7));
				
				list.add(boardVo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return list;
	} // ArrayList<BoardVo> select()
	
	/** select(bid) - 게시글 상세보기 **/
	public BoardVo select(String bid) {
		BoardVo boardVo = new BoardVo();
		
		String sql= "SELECT BID, BTITLE, BCONTENT, BHITS, ID, BDATE" 
				+ " FROM MYCGV_BOARD" 
				+ " WHERE BID = ?";
		getPreparedStatement(sql);
		
		try {
			pstmt.setString(1, bid);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				boardVo.setBid(rs.getString(1));
				boardVo.setBtitle(rs.getString(2));
				boardVo.setBcontent(rs.getString(3));
				boardVo.setBhits(rs.getInt(4));
				boardVo.setId(rs.getString(5));
				boardVo.setBdate(rs.getString(6));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return boardVo;
	} // int select(String bid)
	
	
	/** update - 게시판 수정하기 **/
	public int update(BoardVo boardVo) {
		int result = 0;
		
		String sql = "update mycgv_board set btitle=?, bcontent=? where bid=?";
		getPreparedStatement(sql);
		
		try {
			pstmt.setString(1, boardVo.getBtitle());
			pstmt.setString(2, boardVo.getBcontent());
			pstmt.setString(3, boardVo.getBid());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return result;
	} // int update(BoardVo boardVo)
	
	
	/** delete - 게시판 삭제하기 **/
	public int delete(String bid) {
		int result = 0;
		
		String sql= "delete FROM MYCGV_BOARD where bid=? "; 
		getPreparedStatement(sql);
		
		try {
			pstmt.setString(1, bid);
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	} // int delete(String bid)

}  // class
