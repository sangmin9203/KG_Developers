<%@page import="com.kg.dvlpr.member.dao.MemberDAO"%>
<%@page import="com.kg.dvlpr.member.model.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width-device-width" initial-scale="1">
<link rel="stylesheet" href="/KGDevelopers/css/bootstrap.css">
<link rel="stylesheet" href="./css/bootstrap-theme.min.css">
<script src="./js/jquery-3.1.1.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="include/header.jsp" />
	<br>
	<br>
	<br>
	<br>
	<%
		/*
		- 사용자가 이 페이지에 들어왔을 때
		 getUserInfo()를 이용하여 회원 정보를 불러온 후
		 아래의 input 태그의 value에 해당하는 값이 보여지도록 처리
		*/
		String id = (String) session.getAttribute("userid");
		MemberVO vo = new MemberDAO().getMember(id);
	%>
	
<%-- 	<c:if test="${!empty message}">
         <script>
            alert("비밀번호가 틀립니다");
         </script>
         
   </c:if> --%>
	
	<div calss="container"
		style="margin: auto; align: center; width: 700px">
		<form action="deleteUser.do" method="post">
			<h1>회원탈퇴</h1>
			<br>
			<p>
				사용하고 계신 아이디<font color="red">${vo.getId()}</font>는<br> 탈퇴할 경우
				재사용 및 복구가 불가능합니다.<br> 탈퇴한 아이디는 본인과 타인 모두 재사용 및 복구가 불가하오니<br>
				신중하게 선택하시기 바랍니다.
			<p></p>
			<br>
			<div class="form-group">
				<label for="password1">비밀번호:</label> 
				<input type="password"
					class="form-control" id="password1" name="password" onchange="check_pw()">
			</div>
			<div class="form-group" length="40px">
				<label for="password2">비밀번호 확인:</label> 
				<input type="password"
					class="form-control" id="password2" name="password_confirm" onchange="check_pw()">
			</div>
			<div class="btn-group">
				<input style="margin: 8px" class="btn btn-success" type="submit"
					value="회원탈퇴" onclick="return confirm('탈퇴하시겠습니까?')"> <input
					style="margin: 8px" class="btn btn-warning" type="button"
					value="취소" onclick="location.href='member_mypage.jsp'">
			</div>
		</form>
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
</body>
</html>