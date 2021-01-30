<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content = "text/html; charset=UTF-8">
<meta name="viewport" content = "width=device-width", initail-scale="1">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<title>Insert title here</title>
<style type="text/css">
   
    .td1{
       width:12%;
       text-align:center;
       /* border:1px solid #444444; */
    }
    .td2{
       width:50%;
       /* border:1px solid #444444; */
    }
   
    a{
      text-decoration: none;
        display: inline-block;
        padding: 8px 16px;
        margin:20px 0;
   }
   
</style>

<script src="js/jquery-3.3.1.js"></script>
<script src="js/jquery-1.4.4.min.js"></script>

<script>
reloadtime =500;
function get_cookie(Name) {
var search = Name + "="
var returnvalue = "";

if (document.cookie.length > 0) {
offset = document.cookie.indexOf(search)

if (offset != -1) {
offset += search.length
end = document.cookie.indexOf(";", offset);
if (end == -1) end = document.cookie.length;
returnvalue=unescape(document.cookie.substring(offset, end))
}

}
return returnvalue;
}

function oneload() {
var cookiename=window.location.pathname
var flag = eval(get_cookie(window.location.pathname));
if(flag || flag == null) {
var cookievalue="false;"
document.cookie=cookiename+"="+cookievalue;

location.href = "getBoardListAll.bo";
}
else {
var cookievalue="true;"
document.cookie=cookiename+"="+cookievalue;
}
}
</script>

</head>
<body onload="oneload()">

 	<%
	 	String userid = null;
	    if (session.getAttribute("userid") != null) {
	       userid = (String) session.getAttribute("userid");
	    }
 	%>
 
   <jsp:include page="/include/header.jsp"/>
   <br><br>
   <div class ="container" style="margin: auto; align : center">
      <div class ="row">
         <p style="font-size:25px; margin-bottom:50px;text-align:center;color:#944534">KG에서 만난 예비 개발자들의 놀이터입니다.<br>
         여러분의 아이디어를 맘껏 나누세요.</p>
         
         
         
         <div class="col-sm-6">
         <div class="container-fluid">
          <h3 style="text-align:center;">Q&A게시판</h3>

            <table class="table table-striped" style="width:550px;margin-top:50px">
               <thead style="font-size: 14px;">
               <tr style="background-color:#fff7fe;">
                  <th>글 번호</th>
                  <th>작성자</th>
                  <th>제목</th>
                  <th>날짜</th>
                  <th>조회수</th>
               </tr>
               </thead>
               <tbody>
               
               		 <c:forEach var="article" items="${blist}" begin="0" end="9">
               		 	<c:set var="boardClass" value="${article.boardClass}" />
               		 	<c:if test="${boardClass == 2}">
			               <tr>
			                  <td class="td1">${article.boardNum}</td>
			                  <td class="td1">${article.userid}</td>
			                  <td class="td2">
			                  <% if(userid == null) { %>
			                  	${article.boardTitle}
			                  <% } else { %>
			                  	<a href="getBoard.bo?boardNum=${article.boardNum}&update=no&userid=<%=userid%>">${article.boardTitle}</a>
			                  <% } %>
			                  </td>
			                  <td class="td1">${article.writeDate}</td>
			                  <td class="td1">${article.readCnt}</td>
			               </tr>
		               </c:if>
		            </c:forEach>
		            
               </tbody>
            </table>
            
	         <div style="padding:30px; text-align:center;">
	         	<% if(session.getAttribute("userid") != null) { %>
	                <a href="getBoardList.bo?boardClass=2&userid=<%=userid%>&select=2"><button class="btn btn-primary">더보기</button></a>
	            <% } else { %>
		            <button class="btn btn-primary pull-right"
		               onclick="if(confirm('로그인후 이용가능합니다'))location.href='/KGDevelopers/member_login.jsp';"
		               type="button">더보기</button>
	         	<% } %>
	         </div>
	         
         </div>
      </div>  
      
      
       
      <div class="col-sm-6">   
               <h3 style="text-align:center;">자유게시판</h3>
               <table class="table table-striped" style="width:550px;margin-top:50px">
                  <thead style="font-size: 14px">
                  <tr style="background-color:#f7f9ff;">
                     <th>글 번호</th>
                     <th>작성자</th>
                     <th>제목</th>
                     <th>날짜</th>
                     <th>조회수</th>
                  </tr>
                  </thead>
                  <tbody>
                  
                  	<c:forEach var="article" items="${blist}" begin="0" end="9">
               		 	<c:set var="boardClass" value="${article.boardClass}" />
               		 	<c:if test="${boardClass == 1}">
			               <tr>
			                  <td class="td1">${article.boardNum}</td>
			                  <td class="td1">${article.userid}</td>
			                  <td class="td2">
			                  <% if(userid == null) { %>
			                  	${article.boardTitle}
			                  <% } else { %>
			                  	<a href="getBoard.bo?boardNum=${article.boardNum}&update=no&userid=<%=userid%>">${article.boardTitle}</a>
			                  <% } %>
			                  </td>
			                  <td class="td1">${article.writeDate}</td>
			                  <td class="td1">${article.readCnt}</td>
			               </tr>
		               </c:if>
		            </c:forEach>
		          
                  </tbody>
               </table>
               
               <div style="padding:30px; text-align:center;">
            		<% if(session.getAttribute("userid") != null) { %>
		                <a href="getBoardList.bo?boardClass=1&userid=<%=userid%>&select=2"><button class="btn btn-primary">더보기</button></a>
		            <% } else { %>
			            <button class="btn btn-primary pull-right"
			               onclick="if(confirm('로그인후 이용가능합니다'))location.href='/KGDevelopers/member_login.jsp';"
			               type="button">더보기</button>
		         	<% } %>
               </div>
               
               
               
               
         </div>
      </div>
   </div>
   <br><br>
<jsp:include page="/include/footer.jsp"/>
</body>
</html>
</body>
</html>