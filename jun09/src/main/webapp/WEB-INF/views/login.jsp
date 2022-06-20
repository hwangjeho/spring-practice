<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>
<link rel="stylesheet" href="./css/index.css">
</head>
<body>
<div id="container">
	<div id="menubar">>
		<br>
		<br>
		<c:import url="menu.jsp"/>
	</div>
	<div id="main">
		<c:if test="${param.error ne null}">
		<script type="text/javascript">
			alert("올바른 아이디와 비밀번호를 입력하세요.");
		</script>
	</c:if>
	<div>
		<form action="./login" method="post">
			<input type="text" name="id" placeholder="아이디를 입력하세요." required="required"><br>
			<input type="password" name="pw" placeholder="비밀번호를 입력하세요." required="required"><br>
			<button type="reset">초기화</button> <button type="submit">로그인</button>
		</form>
	</div>
	</div>
</div>
	
</body>
</html>