<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MYCGV</title>
<link rel="stylesheet" href="http://localhost:9000/mycgv_jsp/css/mycgv_jsp.css">

<script src="http://localhost:9000/mycgv_jsp/js/jquery-3.6.4.min.js"></script>
<script src="http://localhost:9000/mycgv_jsp/js/mycgv_jsp_jquery.js"></script>
</head>
<body>
	<!-- header -->
	<jsp:include page="../../header.jsp"></jsp:include>
	
	<!-- content -->
	<div class="content">
		<section class="board">
			<h1 class="title">관리자 - 공지사항</h1>
			<form name="updateForm" action="notice_update_proc.do" method="post">
				<table>
					<tr>
						<th>제목</th>
						<td>
							<input type="text" name="ntitle" value="${adminNoticeVo.ntitle}">
						</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>
							<textarea name="ncontent">${adminNoticeVo.ncontent}</textarea>
							<input type="hidden" name="nid" value="${adminNoticeVo.nid}">
						</td>
					</tr>					
					<tr>
						<td colspan="2">
							<button type="button" id="btnNoticeUpdate">수정완료</button>
							<button type="reset">다시쓰기</button>
							<a href="admin_notice_content.do?nid=${adminNoticeVo.nid}">
								<button type="button">이전페이지</button></a>
							<a href="admin_notice_list.do">
								<button type="button">리스트</button></a>							
						</td>				
					</tr>
				</table>
			</form>
		</section>
	</div>
	
	<!-- footer -->
	<jsp:include page="../../footer.jsp"></jsp:include>
</body>
</html>

