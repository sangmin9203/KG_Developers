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
<jsp:include page="include/header.jsp" />
<br><br>

<div calss="container" style="margin : auto; width:700px; align : center">
   <div class="row">
         <h2>로그인</h2><br>
          <c:if test="${!empty message}">
			${message}
			</c:if>
      <form name="loginInfo" action="Login.do" method="post" onSubmit="return checkValue()">
         <div class="form-group">
               <label for="id1">아이디:</label>
                 <input type="text" class="form-control" id="id1" name ="userid">
              </div>
         <div class="form-group">
               <label for="password1">비밀번호:</label>
                <input type="password" class="form-control" id="password1" name="password">
         </div>
         
            <div class="btn-group">
                <input style = "margin:8px" class="btn btn-success" type="submit" value="로그인">   
                <input style = "margin:8px" class="btn btn-warning" type="button" value="회원가입"
                onclick="location.href='member_join.jsp'">   
               
             </div>
             <!-- <div>
           		  비밀번호를 잊으셨나요?<a href="member_find_pw.jsp"> 비밀번호찾기</a>
             </div> -->
      </form>
   </div>
</div>

<br><br><br><br>
<jsp:include page="include/footer.jsp" />


<script>
	function checkValue(){
		inputForm = eval("document.loginInfo");
		if(!inputForm.userid.value){
			alert("아이디를 입력하세요!");
			inputForm.userid.focus();
			return false;
		}
		if(!inputForm.password.value){
			alert("비밀번호를 입력하세요!");
			inputForm.password.focus();
			return false;
		}
	}
</script>

</body>
</html>