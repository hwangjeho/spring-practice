<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가입하기</title>
<link href="./css/index.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
#joinBtn{
	margin-top: 10px;
}
</style>
</head>
<body>
<div id="container">
	<div id="menubar">
		<br>
		<br>
		<c:import url="menu.jsp"/>
	</div>
	<div id="main">
		<h1>가입하기</h1>
		<hr>
		<div class="mb-3">
 			 <label class="form-label">아이디</label>
 			 <input name="id" class="form-control" type="text" id="id" required>
 			 <p id="checkResult">아이디를 입력하세요.</p>
		</div>
		<div class="mb-3">
  			<label class="form-label">비밀번호</label>
  			<input name="pw1" class="form-control" type="password" id="pw1" required>
		</div>
		<div class="mb-3">
  			<label class="form-label">비밀번호 확인</label>
  			<input name="pw2" class="form-control" type="password" id="pw2" required>
		</div>
		<div class="mb-3">
  			<label class="form-label">이름</label>
  			<input name="name" class="form-control" id="name" type="text" required>
		</div>
		<div class="mb-3">
  			<label class="form-label">이메일</label>
  			<input name="email" class="form-control" id="email" type="email" required autofocus>
		</div>
		<button id="joinBtn" class="btn btn-primary btn-lg" type="submit" disabled="disabled">가입하기</button>
	</div>
</div>
<script type="text/javascript">
$(function(){
	$("#id").change(function(){ //아이디 검사용
		if( $("#id").val().length < 4 ){
			alert("아이디를 4글자 이상 입력해주세요.");
		}else{
			$.ajax({ // ajax 호출 -> id를 서버로 보내기
				url : "./checkID",
				type : "post",
				dataType : "html",
				data : {"id" : $("#id").val() },
				success : function(data){
					if(data == 0){
						$("#checkResult").css("color", "green");
						$("#checkResult").text("가입할 수 있는 ID입니다.");	
						$("#joinBtn").attr("disabled", false);
					}else{
						$("#checkResult").css("color", "red");
						$("#checkResult").text("이미 사용중인 ID입니다.");
						$("#joinBtn").attr("disabled", true);
						
					}
				},
				error : function(){
					//alert("비정상입니다");
					$("#checkResult").text("비정상입니다.");
				}
			}); 
		}
	});
	
	$("#joinBtn").on('click', function(){
		var exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
		var id = $("#id").val();
		var pw1 = $("#pw1").val();
		var pw2 = $("#pw2").val();
		var name = $("#name").val();
		var email = $("#email").val();
		
		if(id == ""){
			alert("아이디를 입력해주세요.");
			$("#id").focus();
			return false;
		}
		
		if(id.length < 4){
			alert("아이디를 4글자 이상 입력해주세요.");
			$("#id").focus();
			return false;
		}
		
		if(pw1 == ""){
			alert("비밀번호를 입력해주세요.");
			$("#pw1").focus();
			return false;
		}
		
		if(pw2 == ""){
			alert("비밀번호를 입력해주세요.");
			$("#pw2").focus();
			return false;
		}
		
		
		if(pw1 != pw2){
			alert("암호가 일치하지 않습니다.\n 다시 입력하세요.");
			$("#pw1").val("");
			$("#pw2").val("");
			return false;
		}
		
		if(name == ""){
			alert("이름를 입력해주세요.");
			$("#name").focus();
			return false;
		}
		
		if(email == ""){
			alert("이메일를 입력해주세요.");
			$("#email").focus();
			return false;
		}
		
		if(exptext.test(email)==false){		
			alert("이메일형식이 올바르지 않습니다.");
			$("#email").focus();
			return false
		}
		
		var newForm = $('<form></form');
		newForm.attr('method', 'post');
		newForm.attr('action', './join');
		newForm.appendTo('body');
		newForm.append('<input type="hidden" name="id" value="'+ id + '">');
		newForm.append('<input type="hidden" name="pw1" value="'+ pw1 + '">');
		newForm.append('<input type="hidden" name="pw2" value="'+ pw2 + '">');
		newForm.append('<input type="hidden" name="name" value="'+ name + '">');
		newForm.append('<input type="hidden" name="email" value="'+ email + '">');
		newForm.append('<button type="submit">');
		newForm.submit();
	});
});

/* $(function(){
	$("form").on("submit", function(){
		
		var id = $("#id").val();
		var pw1 = $("#pw1").val();
		var pw2 = $("#pw2").val();
		var name = $("#name").val();
		var email = $("#email").val();
		
		if(id == ""){
			alert("아이디를 입력해주세요");
			$("id")
		}
	});
}); */
</script>
</body>
</html>