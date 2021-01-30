<%@page import="com.kg.dvlpr.member.model.MemberVO"%>
<%@page import="com.kg.dvlpr.member.dao.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta  name="viewport" content="width-device-width" initial-scale="1">
<link rel="stylesheet" href="/KGDevelopers/css/bootstrap.css">
<link rel="stylesheet" href="./css/bootstrap-theme.min.css">
<script src="./js/jquery-3.1.1.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>
  
<jsp:include page="/include/header.jsp"/>
<br><br>
   <%-- 회원이 아니라면 게시판에 들어올 수 없도록 로직을 작성. --%>
   <c:if test="${sessionScope.userid == null}">
      <script>
         alert("회원만 이용 가능한 게시판입니다. 로그인 해 주세요.");
         location.href="member_login.jsp";
      </script>
   </c:if>
   <% 
   	String userid = (String)session.getAttribute("userid");
   MemberDAO mdao = new MemberDAO();
   MemberVO mvo = mdao.getMember(userid);
   int boardClass = Integer.parseInt(request.getParameter("boardClass"));
   %>
   <div class="container" style = "border : 1px solid #eeeeee">
      <div class="row">
         <h3>글쓰기</h3>
            <form action="insertBoard.bo" method="post" enctype="multipart/form-data">
            	<input type="hidden" name="userid" value=<%=userid%>>
            	<input type="hidden" name="nickname" value=<%=mvo.getNickname()%>>
                <input type="hidden" name="boardClass" value="<%=boardClass%>">
               <table class="table table-striped">
                  <tr>
                     <td class="stitle">제목</td>
                     <td><input type="text" name="boardTitle" style="width:900px;"></td>
                  </tr>
                  <tr>
                     <td class="stitle">내용</td>
                     <td colspan="2"><textarea name="boardContent" style="margin:0px;width:900px;height: 368px;"></textarea></td>
                  </tr>
                  <tr>
                     <td class="stitle">관련링크</td>
                     <td><input  type="link" name="link1" style="width:900px;"></td>
                  </tr>
                  <tr>
                     <td class="stitle">파일첨부</td>
                     <td><input type="file" name="file1"><br>
								<input type="file" name="file2"><br>
								<input type="file" name="file3">
					</td>
                  </tr>
               </table>
            <div padding:30px; text-align:right;">
               <input class="btn btn-primary" type="submit" value="작성 완료"onclick="return confirm('등록하시겠습니까?')">
               <input class="btn btn-warning" type="reset" value="취소" onclick="location.href='getBoardList.bo?boardClass=${param.boardClass}'">
            </div>
         </div>
      </form>
   </div>
</div>

<br><br>
   <jsp:include page="/include/footer.jsp"/>
</body>
</html>