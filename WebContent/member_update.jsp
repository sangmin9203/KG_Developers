<%@page import="com.kg.dvlpr.member.dao.MemberDAO"%>
<%@page import="com.kg.dvlpr.member.model.MemberVO"%>
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
       
       String memid = (String) request.getParameter("userid");
       MemberVO memvo = new MemberDAO().getMember(memid);
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

	<%if(id.equals("admin")) { %>
	
	<div class="container"
		style="margin: auto; width: 700px; align: center">
		<div class="row">
			<h2>
				<%=memvo.getUserid() %>님 회원정보 수정
			</h2>
			<br>
			<form action="updateUser.do" method="post">
				<input type="hidden" name="userid" value="<%=memvo.getUserid()%>">
				<div class="form-group">
					<label for="name1">이름:</label> <input type="text"
						class="form-control" id="name" name="name" value="<%=memvo.getName()%>">
				</div>
				<div class="form-group">
					<label for="nick1">별명:</label> <input type="text"
						class="form-control" id="nickname" name="nickname"
						value="<%=memvo.getNickname()%>">
					<div class="check_font" align="left" id="nickCheck"></div>
				</div>
				<div class="form-group">
					<label for="email1">이메일:</label> <input type="email"
						class="form-control" id="email" name="email"
						value="<%=memvo.getEmail()%>">
				</div>

				<div style="margin: 50px 20px; text-align: right;">
					<input class="btn btn-primary" type="submit" value="수정" id="rbtn"
						onclick="return confirm('수정하시겠습니까?')"> 
					<input class="btn btn-warning" type="button" value="취소"
						onclick="goBack()">
				</div>
			</form>
		</div>
	</div>
	
	
	<% } else { %>

	<div class="container"
		style="margin: auto; width: 700px; align: center">
		<div class="row">
			<h2>
				<%=vo.getUserid() %>님 회원정보 수정
			</h2>
			<br>
			<form action="updateUser.do" method="post">
				<input type="hidden" name="userid" value="<%=vo.getUserid()%>">
				<div class="form-group">
					<label for="name1">이름:</label> <input type="text"
						class="form-control" id="name" name="name" value="<%=vo.getName()%>">
				</div>
				<div class="form-group">
					<label for="nick1">별명:</label> <input type="text"
						class="form-control" id="nickname" name="nickname"
						value="<%=vo.getNickname()%>">
					<div class="check_font" align="left" id="nickCheck"></div>
				</div>
				<div class="form-group">
					<label for="email1">이메일:</label> <input type="email"
						class="form-control" id="email" name="email"
						value="<%=vo.getEmail()%>">
				</div>

				<div style="margin: 50px 20px; text-align: right;">
					<input class="btn btn-primary" type="submit" value="수정" id="rbtn"
						onclick="return confirm('수정하시겠습니까?')"> 
					<input class="btn btn-warning" type="button" value="취소"
						onclick="goBack()">
				</div>
			</form>
		</div>
	</div>

	<% } %>


	<br>
	<br>
	<jsp:include page="include/footer.jsp" />


	<script>
		var flag = 0;
		var nflag = 0;
		//$("#rbtn").attr("disabled", true);
		$("#nick1").blur(function() {
			var url = "nickCheck.do";
			var nickname = $("#nick1").val();
			var idJ = /^[0-9a-zA-Z가-힣]{2,10}$/;
			$.ajax({
				data : {
					id : nickname
				},
				dataType : "text",
				url : url,
				type : "get",
				contentType : "application/x-json; charset=UTF-8",
				success : function(data) {

					console.log(data);
					if (data === "NO") {
						$("#nickCheck").text("사용중인 별명입니다!");
						$("#nickCheck").css("color", "red");
						nflag = 0;
						$("#rbtn").attr("disabled", true);
					} else {
						if (idJ.test(nickname)) {
							$("#nickCheck").text("사용 가능한 별명입니다!");
							$("#nickCheck").css("color", "green");
							nflag = 1;
							if (nflag >= 1) {
								$("#rbtn").removeAttr("disabled");
							}
							console.log("nflag : " + nflag);
							console.log("flag : " + flag);
						} else if (nickname == "") {
							$("#nickCheck").text("별명를 입력해주세요!");
							$("#nickCheck").css("color", "red");
							nflag = 0;
							$("#rbtn").attr("disabled", true);
						} else if (!idJ.test(nickname)) {
							$('#nickCheck').text("한영 소문자, 숫자 2~10자리 입력!");
							$('#nickCheck').css('color', 'red');
							nflag = 0;
							$("#rbtn").attr("disabled", true);
						}
					}

				},
				error : function() {
					console.log("실패");
				}
			});
		});

		function goBack() {
			window.history.back();
		}
	</script>

</body>
</html>