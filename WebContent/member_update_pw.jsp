<%@page import="com.kg.dvlpr.member.model.MemberVO"%>
<%@page import="com.kg.dvlpr.member.dao.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	/*
	- 사용자가 이 페이지에 들어왔을 때
	 getUserInfo()를 이용하여 회원 정보를 불러온 후
	 아래의 input 태그의 value에 해당하는 값이 보여지도록 처리
	*/
	String id = (String) session.getAttribute("userid");
	MemberVO vo = new MemberDAO().getMember(id);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width" initial-scale="1">
<link rel="stylesheet" href="/KGDevelopers/css/bootstrap.css">
<script src="./js/jquery-3.1.1.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<link rel="stylesheet" href="./css/bootstrap-theme.min.css">
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="include/header.jsp" />
	<br>
	<br>
	<br>
	<br>

	<div calss="container"
		style="margin: auto; width: 700px; align: center">
		<div class="row">
			<h2 align="center"><%=vo.getUserid()%>님 비밀번호 수정
			</h2>
			<br>
			<br>
			<br>
			<form action="updatePassword.do" method="post">
				<div class="form-group">
					<label for="password1">변경하실 비밀번호:</label> <input type="password"
						class="form-control" id="password1" name="password"
						onchange="check_pw()">
					<!--  <input type="password"  class="form-control" id="password1" name="password1"> -->
				</div>
				<div class="form-group">
					<label for="password2">변경하실 비밀번호 확인:</label> <input type="password"
						class="form-control" id="password2" name="password2"
						onchange="check_pw()"> &nbsp;<span id="check">
				</div>
				<div style="margin: 50px 20px; text-align: right;">
					<input class="btn btn-primary " type="submit" value="수정"
						onclick="return confirm('수정하시겠습니까?')"> 
					<input class="btn btn-warning" type="button" value="취소"
						onclick="location.href='member_mypage.jsp'">
				</div>
			</form>
		</div>
	</div>

	<br>
	<br>
	<br>
	<br>
	<jsp:include page="include/footer.jsp" />
	<script>
		function check_pw() {
			if (document.getElementById('password1').value != ''
					&& document.getElementById('password2').value != '') {
				if (document.getElementById('password1').value == document
						.getElementById('password2').value) {
					document.getElementById('check').innerHTML = '비밀번호가 일치합니다.'
					document.getElementById('check').style.color = 'green';
				} else {
					document.getElementById('check').innerHTML = '비밀번호가 일치하지 않습니다.';
					document.getElementById('check').style.color = 'red';
				}
			}
		}
	</script>
	</script>
</body>
</html>