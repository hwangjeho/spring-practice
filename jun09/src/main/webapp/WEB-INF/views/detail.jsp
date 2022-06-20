<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>detail</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet" href="./css/index.css">
<link rel="stylesheet" href="./css/comment.css">
<script type="text/javascript">
	function check(){
		var comment = document.getElementsByName("comment")[0];
		if(comment.value.length < 5){
			alert("댓글을 입력하세요.");
			comment.focus();
			return false;
		}
	}
	
	$(document).ready(function(){
		$(".delete").click(function(){
			alert("삭제를 클릭했습니다.");
			var c_no = $(this).parents("#cid").children("#c_no").text();
			location.href="./commentDelete?b_no=${detail.b_no}&c_no="+c_no;
		});
	});
	
	$(document).ready(function(){
		$(".update").click(function(){
			alert("수정을 클릭했습니다.");
			var oldComment = $(this).parents("#comment").children("#commentbody").text();
			var c_no = $(this).parents("#cid").children("#c_no").text();
			//var b_no = $(this).parents("#detail").children("#b_no").text();
			var v = "<div id='cwriteform'><form action='./commentUpdate' method='post' onsubmit='return check()'>";
			v += "<textarea name='comment' style='resize: none; width: 1030px; height: 80px;'>" + oldComment;
			v += "</textarea><input type='hidden' name='b_no' value='${detail.b_no }'>";
			v += "<input type='hidden' name='c_no' value="+c_no+">";
			v += "<button name='cUpdate' style='float:right; background-color: #5882FA; line-height: 29px; border-width: 1px; border-style: solid; font-size: 12px; font-weight: bold;'>수정하기</button>";
			v += "</button></form></div>";
			$(this).parents("#comment").html(v);
			$(".delete").remove();
			$(".update").remove();
			$("#cwritefrom").hide();
		});
	});
	
	$(document).on("keyup", "#commentText", function(){
		//댓글 글자수 제한
		$("textarea").on("keyup", function(){
			$("#textCount").html("댓글쓰기<br>(" + $(this).val().length + "/150)");
			if($(this).val().length > 150){
				$(this).val(   $(this).val().substring(0, 150)    );//넘어가는 글자 삭제하기
				$("#textCount").html("댓글쓰기<br>(150/150)");
			}
		});
	});
	
	$(document).ready(function(){
		$(".bdelete").click(function(){
			if(confirm("삭제를 하시겠습니까?")){
				location.href="./boardDelete?b_no=${detail.b_no}";	
			}
		});
	});
	
	$(document).ready(function(){
		$(".bupdate").click(function(){
			if(confirm("수정을 하시겠습니까?")){
				location.href="./boardUpdate?b_no=${detail.b_no}";
			}
		});
	});
	/* $(document).ready(function(){
		$("#textCount").on("click", function(){
			//alert("댓글쓰기를 눌렀습니다.");
			
			var b_no = "${detail.b_no}";
			var commentText = $("#commentText").val();
			
			//동적form만들기
			var newForm = $('<form></form');
			newForm.attr('method', 'post');
			newForm.attr('action', './comment');
			newForm.appendTo('body');
			newForm.append('<input type="hidden" name="b_no" value="' + b_no + '">');
			newForm.append('<input type="hidden" name="comment" value="' + commentText + '">');
			newForm.submit();
		});
	}); */
	
</script>
<style type="text/css">
#detail{
	margin: 0 auto;
	width: 90%;
	height: 100%;
	display: block;
}
table{
	width: 100%;
	max-height: 500px;
	margin: 0 auto;
	border-collapse: collapse;
	margin-bottom: 10px;
}
th{
	background-color: #D8D8D8;
	width: 100px;
}
td, th{
	height: 30px;
	border-bottom: 1px black solid;
	border-top: 1px black solid;
	padding: 10px;
}
#hcontent{
	height: 200px;
	vertical-align: top;
}
#c_no{
	float: left;
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
		<div id="detail">
			<table>
				<tr>
					<th>글번호</th>
					<td>${detail.b_no }</td>
				</tr>
				<tr>
					<th>글제목</th>
					<td>${detail.b_title }
					<c:if test="${detail.u_id eq sessionScope.id }">
						<img alt="update" src="./img/update.png" class="bupdate">
						<img alt="delete" src="./img/delete.png" class="bdelete">
					</c:if>
					</td>
				</tr>
				<tr>
					<th>쓴날짜</th>
					<td>
					<fmt:formatDate value="${detail.b_date }" pattern="yyyy-MM-dd HH:mm:ss" var="bdate"/>
					${bdate }
					</td>
				</tr>
				<tr>	
					<th>글쓴이</th>
					<td>${detail.u_id }</td>
				</tr>
				<tr id="hcontent">
					<td id="hcontent" colspan="2">${detail.b_content }
						<c:forEach items="${fileList }" var="f">
							<img alt="img" src="./resources/upload/${f.f_filename }"><br>
						</c:forEach>
					</td>
				</tr>
			</table>
			<button onclick="location.href='./board'">게시판으로</button>
			<hr>
			<c:if test="${fn:length(cList) gt 0 }">
				<c:forEach items="${cList }" var="c">
					<div id="comment">
						<div id="commenthead">
							<div id="cid">
							<div id="c_no" style="width: 0px; height: 0px; visibility: hidden;">
							${c.c_no }
							</div>
							   ${c.u_id }
							<c:if test="${c.u_id eq sessionScope.id }">
							<img alt="update" src="./img/update.png" class="update">
							<img alt="delete" src="./img/delete.png" class="delete">
							</c:if>
							</div>
							<div id="cdate">
							<fmt:formatDate value="${c.c_date }" pattern="yyyy-MM-dd HH:mm:ss" var="c_date"/>${c_date }
							</div>
						</div>
						<div id="commentbody">${c.c_content }<br></div>
					</div>
				</c:forEach>
			</c:if>
			<!-- 댓글쓰기 시작 -->
			<c:if test="${sessionScope.id ne null }">
			<div id="cwriteform">
				<form action="./comment" method="post" onsubmit="return check()">
					<textarea id="commentText" name="comment" class="comment" maxlength="400"></textarea>
					<input type="hidden" name="b_no" value="${detail.b_no }">
					<button id="textCount" type="submit">댓글쓰기<br>(0/150)</button>
				</form>
			</div>
			</c:if>
			<!-- 댓글쓰기 끝 -->
		</div>
	</div>
</div>	
</body>
</html>