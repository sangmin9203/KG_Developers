<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content = "text/html; charset=UTF-8">
<meta name="viewport" content = "width=device-width", initail-scale="1">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<title>KGDevelopers</title>
</head>
<body>
	<%
         //로그한사람이라면    userID라는 변수에 해당 아이디가 담기고 그렇지 않으면 null값
         String userid = null;
         String admin = null;//관리자의 session
         if (session.getAttribute("userid") != null) {
            userid = (String) session.getAttribute("userid");
         }
      %>
	
<nav class ="navbar navbar-default">
	<div class = "container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"  data-toggle="collapse" 
			 data-target="#bs-example-navbar-collapse-1" aria-expanded="false"> 
            <span class= "sr-only"></span>
			<span class= "icon-bar"></span> 
			<span class= "icon-bar"></span> 
 			<span class= "icon-bar"></span>
			</button>
			<table>
				<tr>
					<td><img src="/KGDevelopers/img/logo.jpg" width = 50px>
				 	<a class = "navbar-brand" href="getBoardListAll.bo" style="color:#b337b8;">KGDEVELOPERS</a></td>
				</tr>
			</table>
		</div>
		<div class ="collapse navbar-collapse" id ="bs-example-navbar-collapse-1">
			<ul class ="nav navbar-nav">
			
				<%if (userid == null){ %>
				<li><a href="getBoardListAll.bo">HOME<span class = "sr-only"></span></a></li>
				<li><a href="member_login.jsp">로그인</a></li>	<!-- 비회원 헤더 표시  -->
				
				<%} else if(userid.equals("admin")){ %>
				<li><a href="getBoardListAll.bo">HOME<span class = "sr-only"></span></a></li>
				<li><a href="getBoardList.bo?boardClass=1&userid=<%=userid%>">자유게시판</a></li>
				<li><a href="getBoardList.bo?boardClass=2&userid=<%=userid%>">Q&A게시판</a></li>
				<li><a href="getMemberList.do?userid=<%=userid%>&page=1">회원관리</a></li>	<!-- 관리자 헤더 표시  -->
				<li><a href="Login.do?action=logout">로그아웃</a></li>
				
				<%} else { %>
				<li><a href="getBoardListAll.bo">HOME<span class = "sr-only"></span></a></li>
				<li><a href="getBoardList.bo?boardClass=1&userid=<%=userid%>">자유게시판</a></li>
				<li><a href="getBoardList.bo?boardClass=2&userid=<%=userid%>">Q&A게시판</a></li>
				<li><a href="getMember.do?userid=<%=userid%>">My Page</a></li>	<!-- 회원 헤더 표시  -->
				<li><a href="Login.do?action=logout">로그아웃</a></li>
				<%} %>
				
			</ul>				
		</div>
	</div>
</nav>
</body>
</html>