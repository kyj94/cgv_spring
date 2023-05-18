<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MYCGV</title>
<link rel="stylesheet" href="http://localhost:9000/mycgv/css/mycgv.css">
</head>
<body>
	<!-- header -->
	<jsp:include page="../../header.jsp"></jsp:include>
	
	<!-- content -->
	<div class="content">
		<section class="board">
			<h1 class="title">관리자 - 공지사항</h1>			
			<table class="board_list">
				<tr>
					<td colspan="4">
						<a href="admin_notice_write.do">
							<button type="button">등록하기</button>
						</a>
					</td>
				</tr>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>조회수</th>
					<th>작성일자</th>
				</tr>
				
				<c:forEach var="adminNoticeVo" items="${list}"> 
				
				<tr>
					<td>${adminNoticeVo.rno}</td>
					<td><a href="admin_notice_content.do?nid=${adminNoticeVo.nid}"> ${adminNoticeVo.ntitle}</a></td>
					<td>${adminNoticeVo.nhits}</td>
					<td>${adminNoticeVo.ndate}</td>
				</tr>
				
				</c:forEach>
				
				<tr>
					<td colspan="4"><< 1  2  3  4  5 >></td>
				</tr>
			</table>
		</section>
	</div>
	
	<!-- footer -->
	<jsp:include page="../../footer.jsp"></jsp:include>
</body>
</html>

