<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
         <h2 align="center">비밀번호찾기</h2><br>
      <form action="getPassword.do" method="post">
         <div class="form-group">
               <label for="id1">아이디:</label>
                 <input type="text" class="form-control" id="id1" name ="userid">
              </div>
         <div class="form-group">
               <label for="name1">이름:</label>
                <input type="text" class="form-control" id="name1" name="name">
         </div>
               <div class="form-group">
               <label for="email1">이메일:</label>
                <input type="email" class="form-control" id="email1" name="email">
         </div>
            <div class="btn-group">
                <input style = "margin:8px" class="btn btn-success" type="submit" value="찾기">   
                <input style = "margin:8px" class="btn btn-warning" type="reset" value="취소">   
             </div>
      </form>
   </div>
</div>

<br><br>
<jsp:include page="include/footer.jsp" />
</body>
</html>