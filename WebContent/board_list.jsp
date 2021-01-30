<%@page import="com.kg.dvlpr.board.dao.BoardDAO"%>
<%@page import="com.kg.dvlpr.comment.model.CommentVO"%>
<%@page import="com.kg.dvlpr.board.model.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ElCeil" uri="/WEB-INF/tlds/el-function.tld" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content = "text/html; charset=UTF-8">
<meta name="viewport" content = "width-device-width", initail-scale="1">
<link rel="stylesheet" href = "/KGDevelopers/css/bootstrap.css">
<title>Insert title here</title>
</head>
<body>
<style type="text/css">
.thead1{
	background-color: #eeeeee;
	text-align : center;
}
.tbody1{
	text-align : center;
}
</style>

<!-- <form action = "#" method="post"> -->
<jsp:include page="/include/header.jsp"/>
<br><br>
<div class ="container">
<div class ="row">
	<%
		String userid = (String) session.getAttribute("userid");
    %>
      
	<c:if test="${boardClass == 1}">
		<div style ="text-align : center; font-size : 50px; color : #000000">자유게시판</div>
	</c:if>
	<c:if test="${boardClass == 2}">
		<div style ="text-align : center; font-size : 50px; color : #000000">Q&A게시판</div>
	</c:if>
<div>
<!-- 검색 -->
<form action="searchBoard.bo" method="post">
		<input type="hidden" name="boardClass" value="${boardClass}">
		<select name="category">
			<option value="글제목">글제목</option>
			<option value="글내용">글내용</option>
			<option value="작성자">작성자</option>
		</select>
        <input type="text" style = "height : 30px" name="keyword" placeholder="검색글을 입력하세요">
        <input type="submit" value="검색">
        <!-- <button type="button" name ="search">검색</button> -->
</form>
		<a href = "board_write.jsp?boardClass=${boardClass}" class = "btn btn-primary pull-right">글쓰기</a>
</div>


 	<table class="table table-striped" style="tex-align : center; border : 1px solid #ffffff">
 		<thead>
 		<tr>
 			<th class = "thead1">번호</th>
			<th class = "thead1">글 제목</th>
			<th class = "thead1">작성자</th>
 			<th class = "thead1">작성일자</th>
 			<th class = "thead1">조회수</th>
 		</tr>
 		</thead>
 		
 		<tbody>
 			<c:forEach var="vo1" items="${blist}">
 			<tr>
 				<td class= "tbody1">${vo1.boardNum} </td>
 				<td class= "tbody1"><a href ="getBoard.bo?boardNum=${vo1.boardNum}&update=no&userid=<%=userid%>" style = "text-decoration: none; color: #000000">${vo1.boardTitle}</a></td>
 				<td class= "tbody1">${vo1.userid}</td>
 				<td class= "tbody1">${vo1.writeDate}</td>
 				<td class= "tbody1">${vo1.readCnt }</td>
 			</tr>
 			</c:forEach>
 		</tbody>
 		
	</table>


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
				<a href="getBoardList.bo?page=${startPage-1}&boardClass=${boardClass}" style="font-size: 18px">◀ 이전</a>
			</c:if>
			
			<c:forEach begin="${startPage}" end="${endPage}" step="1" varStatus="status">
				<a href="getBoardList.bo?page=${status.count}&boardClass=${boardClass}" style="font-size: 18px">[${status.count}]</a>
			</c:forEach>
			
			<c:if test="${nowPageBlock lt totalPageBlock}">
				<a href="getBoardList.bo?page=${endPage+1}&boardClass=${boardClass}" style="font-size: 18px">다음 ▶</a>
			</c:if>
		</h6>
		</td>
		
		
 </div>
</div>

<br><br>
<jsp:include page="/include/footer.jsp"/>
<!-- </form> -->
</body>
</html>