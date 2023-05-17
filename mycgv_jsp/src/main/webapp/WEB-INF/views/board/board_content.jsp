<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "com.mycgv_jsp.vo.BoardVo" %>     
<%@ page import = "com.mycgv_jsp.dao.BoardDao" %>   

<%	
	String bid = request.getParameter("bid"); // pk값 갖고오기
	BoardDao boardDao = new BoardDao();
	BoardVo boardVo = boardDao.select(bid);
%>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MYCGV</title>
<link rel="stylesheet" href="http://localhost:9000/mycgv_jsp/css/mycgv_jsp.css">
</head>
<body>
	<!-- header -->
	<!-- <iframe src="http://localhost:9000/mycgv_jsp/header.jsp"
			scrolling="no" width="100%" height="149px" frameborder=0></iframe> -->
	<jsp:include page="../header.jsp"></jsp:include>
	
	<!-- content -->
	<div class="content">
		<section class="board">
			<h1 class="title">게시판</h1>
			<table class="board_content">
				<tr>
					<th>제목</th>
					<td><%= boardVo.getBtitle() %></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><%= boardVo.getBcontent() %></td>
				</tr>
				<tr>
					<th>조회수</th>
					<td><%= boardVo.getBhits() %></td>
				</tr>
				<tr>
					<th>작성자</th>
					<td><%= boardVo.getId() %></td>
				</tr>
				<tr>
					<th>작성일자</th>
					<td><%= boardVo.getBdate() %></td>
				</tr>
				<tr>
					<td colspan="2">
						<a href="board_update.jsp?bid=<%=boardVo.getBid()%>">
							<button type="button" class="btn_style">수정하기</button></a>
						<a href="board_delete.jsp?bid=<%=boardVo.getBid()%>">
							<button type="button" class="btn_style">삭제하기</button></a>
						<a href="board_list.jsp">
							<button type="button" class="btn_style">리스트</button></a>
						<a href="http://localhost:9000/mycgv_jsp/index.jsp">
						<button type="button" class="btn_style">홈으로</button></a>
					</td>
				</tr>
			</table>
		</section>
	</div>
	
	<!-- footer -->
	<!-- <iframe src="http://localhost:9000/mycgv_jsp/footer.jsp"
			scrolling="no" width="100%" height="500px" frameborder=0></iframe> -->	
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>

















