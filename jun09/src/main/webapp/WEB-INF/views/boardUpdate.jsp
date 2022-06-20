<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글수정</title>
<!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<link rel="stylesheet" href="./css/index.css">
<link rel="stylesheet" href="./css/write.css">
<script type="text/javascript">
function check(){
	var title1 = $('input[name=title]').val();
	if(title1.length < 5){
		alert("제목은 5자 이상이어야 합니다.");
		$('input[name=title]').focus();
		return false;	
	}
	
	var content = $('textarea[name=content]').val();
	if(content.length < 20){
		alert("내용은 20자 이상이어야 합니다.");
		$('textarea[name=content]').focus();
		return false;	
	}
}
</script>
</head>
<body>
<div id="container">
	<div id="menubar">
		<br>
		<br>
		<c:import url="menu.jsp"/>
	</div>
	<div id="main">
	<h1>게시글수정</h1>
	<div id= "writeform">
	<form action="./boardUpdate" method="post" onsubmit="return check();">
		<input type="text" name="title" required="required" value="${dto.b_title }">
		<textarea name="content" id="summernote" required="required" >${dto.b_content }</textarea>
		<input type="hidden" name="b_no" value="${dto.b_no }">
		<button type="submit">수정</button>
	</form>
	</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		$("#summernote").summernote({
			height:400
			
		});
	});
</script>
</body>
</html>