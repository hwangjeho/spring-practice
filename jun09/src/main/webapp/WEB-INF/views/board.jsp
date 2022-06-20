<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board</title>
<link rel="stylesheet" href="./css/index.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style type="text/css">
#board {
	margin: 0 auto;
	width: 90%;
	height: 600px;
	display: block;
}

table {
	margin: 0 auto;
	width: 100%;
	max-height: 600px;
	border-collapse: collapse;
	margin-bottom: 10px;
	border-right: none;
	border-left: none;
}

th {
	background-color: #c0c0c0;
	height: 50px;
	border-bottom: 1px solid black;
	text-align: center;
}

tr {
	height: 50px;
}

td {
	min-height: 50px;
	border-bottom: 1px solid black;
	border-left: none;
	text-align: center;
	border-right: none;
}

tr:hover {
	background-color: #c1c1c1;
}

#r1 {
	width: 10%;
}

#r2 {
	width: 20%;
}

#r3 {
	width: 40%;
	text-align: left;
}

#boardTable {
	height: 600px;
}

#pagination {
	margin: 0 auto;
	width: 100%;
	height: 30px;
	/* background-color: white; */
	text-align: center;
	line-height: 30px;
	margin-top: 70px;
}

button {
	margin-top: 0px;
	float: left;
	margin-left: 70px;
}
</style>
</head>
<script type="text/javascript">
	function linkPage(pageNo) {
		location.href = "./board?b_cate=${b_cate}&pageNo=" + pageNo;
	}
</script>
<body>
	<div id="container">
		<div id="menubar">
			<br> <br>
			<c:import url="menu.jsp" />
		</div>
		<div id="main">
			<div id="board">
				<h1>${b_cate }보드</h1>
				<%
				LocalDate now = LocalDate.now();//현재날짜뽑기
				%>
				<c:set value="<%=now%>" var="now" />
				<c:choose>
					<c:when test="${fn:length(list) > 0 }">
						<div id="boardTable">
							<table border="1">
								<tr>
									<th>글번호</th>
									<th>글제목</th>
									<th>쓴날짜</th>
									<th>글쓴이</th>
									<th>조회수</th>
								</tr>
								<c:forEach items="${list }" var="l">
									<tr id="boardtr"
										onclick="location.href='./detail?b_no=${l.b_no }'">
										<td id="r1">${l.b_no }</td>
										<td id="r3">
										<c:if test="${l.fileCount gt 0 }">
										<img alt="img" src="./img/image.png">
										</c:if>
										${l.b_title }<small style="color: green;">(${l.commentcount })</small></td>
										<td id="r2"><fmt:formatDate value="${l.b_date }"
												pattern="yyyy-MM-dd" var="bdateYear" /> <c:choose>
												<c:when test="${bdateYear eq now }">
													<fmt:formatDate value="${l.b_date }" pattern="HH:mm:ss"
														var="bdateYear" />
												</c:when>
												<c:otherwise>
													<fmt:formatDate value="${l.b_date }" pattern="yyyy-MM-dd"
														var="bdateYear" />
												</c:otherwise>
											</c:choose> ${bdateYear }</td>
										<td id="r2">${l.u_id }</td>
										<td id="r1">${l.b_count }</td>
									</tr>
								</c:forEach>
							</table>
						</div>
						<div id="pagination">
							<ui:pagination paginationInfo="${paginationInfo}" type="text"
								jsFunction="linkPage" />
						</div>
					</c:when>
					<c:otherwise>
						<h1>글이 없습니다.</h1>
					</c:otherwise>
				</c:choose>

				<c:if test="${sessionScope.id ne null }">
					<button onclick="location.href='./write'"
						class="btn btn-primary btn-lg" type="button">글쓰기</button>
				</c:if>
			</div>
		</div>
	</div>

</body>
</html>