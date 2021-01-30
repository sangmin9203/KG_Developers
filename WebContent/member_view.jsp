<%@page import="com.kg.dvlpr.member.model.MemberVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.kg.dvlpr.member.dao.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ElCeil" uri="/WEB-INF/tlds/el-function.tld" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
<%//scriptlet 
request.setCharacterEncoding("UTF-8");
%>
   <div class="container">
      <div class="row">
         <h3>회원정보 관리</h3><br>
         <!-- <form action="#.do"> --><!-- 중요 -->
         <!-- <input type="submit" value="모두보기">back으로 가서 회원목록을 가져옴 -->
         <!-- </form> -->
         <table class="table table-striped">
            <thead>
               <th>아이디</th>
               <th>이름</th>
               <th>이메일</th>
               <th>별명</th>
               <th>가입일</th>
               <%-- <th>경고횟수</th> --%>
            </thead>
            <!-- request scope에 ArrayList alist에 저장해놓은 것 -->
            <c:forEach var="member" items="${lists}">
				<tr>
				<td>${member.userid}</td>
				<td>${member.name}</td>
				<td>${member.email}</td>
				<td>${member.nickname}</td>
				<td>${member.joinDate}</td>
				<%-- <td>${member.warningCnt}</td> --%>
				<td><a href="updateUser.do?userid=${member.userid}">
					<input type="button" class="btn btn-warning" value = "수정"></a>
				<a href="deleteUser.do?userid=${member.userid}&password=${member.password}">
					<input type="button" class="btn btn-warning" value = "삭제"></a>
				</td>
				</tr>
			</c:forEach>
			
         </table>
         
      </div>
   </div>
   
   <!-- 페이징 처리 -->
		<td colspan=4>
		<h6 align="center">
			<c:set var="totalPageBlock" value="${ElCeil:ElCeil(totalPageCount/10.0)}" />
			<c:set var="nowPageBlock" value="${ElCeil:ElCeil(page/10.0)}" />
			<c:set var="startPage" value="${(nowPageBlock-1)*10+1}" />
			<c:choose>
				<c:when test="${totalPageCount gt nowPageBlock*10}">
					<c:set var="endPage" value="${nowPageBlock*10}" />
				</c:when>
				<c:otherwise>
					<c:set var="endPage" value="${totalPageCount}" />
				</c:otherwise>
			</c:choose>
			
			<c:if test="${nowPageBlock gt 1}">
				<a href="getMemberList.do?page=${startPage-1}" style="font-size: 18px">◀ 이전</a>
			</c:if>
			
			<c:forEach begin="${startPage}" end="${endPage}" step="1" varStatus="status">
				<a href="getMemberList.do?page=${status.count}" style="font-size: 18px">[${status.count}]</a>
			</c:forEach>
			
			<c:if test="${nowPageBlock lt totalPageBlock}">
				<a href="getMemberList.do?page=${endPage+1}" style="font-size: 18px">다음 ▶</a>
			</c:if>
		</h6>
		</td>
   
<br><br>
<jsp:include page="include/footer.jsp" />
</body>
</html>