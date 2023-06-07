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
	<jsp:include page="../header.jsp"></jsp:include>
	
	<!-- content -->
	<div class="content">
		<section class="board">
			<h1 class="title">게시판</h1>
			<form name="deleteForm" action="board_delete_proc.do" method="post">
				<input type="text" name="bid" value="${bid}">
				<input type="text" name="bsfile" value="${bsfile}">
				<table>
					<tr>
						<td><img src="http://localhost:9000/mycgv_jsp/images/trash.jpg"></td>
					</tr>
					<tr>					
						<td>정말로 삭제 하시겠습니까?</td>
					</tr>				
					<tr>
						<td colspan="2">
							<button type="submit" class="btn_style">삭제완료</button>
							<a href="board_content.do?bid=${bid}">
								<button type="button" class="btn_style">이전페이지</button></a>
							<a href="board_list.do">
								<button type="button" class="btn_style">리스트</button></a>
							<a href="http://localhost:9000/mycgv_jsp/index.do">
								<button type="button" class="btn_style">홈으로</button></a>
						</td>				
					</tr>
				</table>
			</form>
		</section>
	</div>
	
	<!-- footer -->
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>

