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
<style type="text/css">
    .mpsub{
       width:150px;
    }
    
</style>
</head>
<body>
<jsp:include page="include/header.jsp" />
<br><br>

<div calss="container" style="margin : auto; width:600px; align : center">
   <div class="row">
      <h2>MYPage</h2>
    <div style="margin-top:50px;">
            <table class="table table-striped" style="border-top:none;">
            
              <tr>
            <td class="mpsub">아이디</td>
            <td>${vo.getUserid()}</td>
         </tr>
         <tr>
            <td class="mpsub">이름</td>
           <td>${vo.getName()}</td>
         </tr>
         <tr>
            <td class="mpsub">이메일</td>
           <td>${vo.getEmail()}</td>
         </tr>
         <tr>
            <td class="mpsub">닉네임</td>
            <td>${vo.getNickname()}</td>
         </tr>
         <tr>
            <td class="mpsub">경고횟수</td>
            <td>${vo.getWarningCnt()}</td>
         </tr>
         <tr>
            <td class="mpsub">가입일</td>
            <td>${vo.getJoinDate()}</td>
         </tr>
       <%--   <tr>
         <c:forEach></c:forEach>
            <td class="mpsub"><a href ="#">내가 쓴 글</a> </td> 
                     <!-- 실행하실때 주석 풀고 아래 test는 지워주시고 최신글 하나만 띄워주세요 
                         내가 쓴 글 -> board_list.jsp에서 볼 예정입니다.-->
            <td>test<input type="text" name="title" value="${.content }">
               <input type="hidden" name="userId" value="${.userId }">
            </td> --%>
      </table>
      <p>
         <div class="mpmenu">
         <a href="updateUser.do">
            <input class="btn btn-primary" type = "button" style="width:110px;font-size: small;" value="회원 정보 변경"></a> 
         <a href="member_update_pw.jsp">
            <input class="btn btn-warning" type = "button" style="width:110px;font-size: small;" value="비밀번호 변경"></a> 
         <a href="member_delete.jsp">
            <input type="hidden" id="userid" name="userid" value="${vo.getUserid()}" />
             <input type="hidden" id="password" name="password" value="${vo.getPassword()}" />
            <input class="btn btn-danger" type = "submit" style="width:110px;font-size: small;" value="회원 탈퇴">
         </div>
      </p><br>
     </div>
  </div>
</div>
   
<br><br>
<jsp:include page="include/footer.jsp" />
</body>
</html>