<%@page import="java.util.ArrayList"%>
<%@page import="com.kg.dvlpr.comment.model.CommentVO"%>
<%@page import="com.kg.dvlpr.board.dao.BoardDAO"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.kg.dvlpr.board.model.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<script src="js/jquery-3.3.1.js"></script>
<script src="js/jquery-1.4.4.min.js"></script>

<script>
/* $(function(){
 	var parent = new Array();//지정
 		for(var i=0; i<=1000; i++){
 			parent[i] = i+1;
			}
	
		$.each(parent,function(index,value){
				$('#showBtn'+value).click(function(){
					if($('#step_comment' + value).is(':hidden'))
						$('#step_comment' + value).show();
							else
								$('#step_comment' + value).hide();
			});
		})
		$.each(parent,function(index,value){
 			$('#insertBtn'+value).click(function(){
 				if($('#insert_step_comment' + value).is(':hidden'))
 					$('#insert_step_comment' + value).show();
 						else
 							$('#insert_step_comment' + value).hide();		
			});
		})
	}) */
	
	
	function updateComment1(boardNum, commentNum) {
		var boardNum2 = boardNum;
		var commentNum2 = commentNum;
		var commentInput2 = $("#commentInput1").val();
		location.href = "updateComment.co?boardNum=" + boardNum2 
				+ "&commentNum=" + commentNum2 + "&commentContent=" + commentInput2;
	}
	
	function updateComment2(boardNum, commentNum, select) {
		var boardNum4 = boardNum;
		var commentNum4 = commentNum;
		var commentInput4 = $("#commentInput2").val();
		location.href = "updateComment.co?boardNum=" + boardNum4 
				+ "&commentNum=" + commentNum4 + "&commentContent=" + commentInput4;
	}
	
	function deleteComment(boardNum, commentNum) {
		var boardNum3 = boardNum;
		var commentNum3 = commentNum;
		location.href = "deleteComment.co?boardNum=" + boardNum3
				+ "&commentNum=" + commentNum3;
	}
	
</script>
<meta http-equiv="Content-Type" content = "text/html; charset=UTF-8">
<meta name="viewport" content = "width-device-width" initail-scale="1">
<link rel="stylesheet" href = "css/bootstrap.css">
<title>게시글 보기</title>
</head>
<body>

<jsp:include page="/include/header.jsp"/>
<br><br>
<!-- 조회수처리 -->
<%
	String userid = (String) session.getAttribute("userid"); //세션 아이디

	BoardVO board = (BoardVO) request.getAttribute("board");
	BoardDAO bdao = new BoardDAO();
	bdao.upCnt(board.getBoardNum()); //조회수 증가!!!
%>

<div class ="container" align = "center">
	<!-- <a href = "#" class = "btn btn-danger pull-right">신고하기</a> -->
 <div class ="row">
 <table class="table table-striped" style="tex-align : center; border : 1px soid #dddddd">
 <thead>
 <th colspan="3" style="background-color: #eeeeee; text-align : center;">게시판 글 보기</th>
 </thead>
 <tbody>
 	<tr>
 		<td>글 제목</td>
 		<td colspan = "2">${board.boardTitle}</td>
 	</tr>
 	<tr>
 		<td>작성자</td>
 		<td>${board.userid}</td>
 	</tr>
 	<tr>
 		<td>작성 일자</td>
 		<td> ${board.writeDate}</td>
 	</tr>
 	<tr>
 		<td>첨부파일</td>
 		<td>
 			<c:set var="file11" value="${board.file1}"></c:set>
 			<c:set var="file22" value="${board.file2}"></c:set>
 			<c:set var="file33" value="${board.file3}"></c:set>
 			<c:if test="${not empty file11}">
 				<a href="fileDownload.bo?fileName=${board.file1}">${board.file1}</a><br>
 			</c:if>
 			<c:if test="${not empty file22}">
 				<a href="fileDownload.bo?fileName=${board.file2}">${board.file2}</a><br>
 			</c:if>
 			<c:if test="${not empty file33}">
 				<a href="fileDownload.bo?fileName=${board.file3}">${board.file3}</a><br>
 			</c:if>
 		</td>
 	</tr>
 	<tr>
 		<td>내용</td>
 		<td colspan="2" style="min-height : 400px; text-align: left">
 			<textarea rows="15" cols="140" class="form-control" name="boardContent" readonly>${board.boardContent}</textarea>
 		</td>
 	</tr>
</tbody>
</table>
</div>
</div>
<br>
<div class="container" align = "center">
		<table>
			<tr>
				<td width = 20px><a href="likeCnt.bo?select=2&boardNum=${board.boardNum}"><img src = "img/thumbup.png"></a></td>
				<td width = 20px>${board.likeCnt}</td>
				<td width = 20px><a href="dislikeCnt.bo?select=2&boardNum=${board.boardNum}"><img src = "img/thumbdown.png"></a></td>
				<td width = 20px>${board.warningCnt}</td>
			</tr>
		</table>		
</div>
<hr>
		
		<!-- 댓글 처리 시작 -->

		<!-- 댓글 작성 -->
		<div class="container" align="center">
			<form action="insertComment.co" method="post">
				<div class="input-group">
					<input type="hidden" name="userid" value="<%=userid%>" />
					<input type="hidden" name="boardNum" value="${board.boardNum}" />
					<input type="text" class="form-control" name="commentContent"
						placeholder="댓글을 입력하세요." />
					<span class="input-group-btn">
	         			<button class="btn btn-default" type="submit" name="commentInsertBtn">등록</button>
	        		 </span>
        		 </div>
			</form>
		</div>
		
		<br>
		
		<!-- 댓글 보기 -->
		<div class="container">
			<label for="content">comment</label>
			
			<c:forEach var="cmt" items="${clist}">
			
				<div id="parent_comment" style="font-size: 12px">
					<table class="tabel table-striped" style="border: 1px solid #eeeeee; background-color:">
						
							<c:set var="parentNum" value="${cmt.parentNum}" />
							<c:set var="cmtUserid" value="${cmt.userid}" />
							<c:set var="sesUserid" value="${sessionScope.userid}" />
							<c:choose>
								<c:when test="${parentNum == 0}">
								
									<tr>
										<td>
											<img src="/KGDevelopers/img/logo.jpg" class="img-responsive img-circle"
												style="height: 30px; width: 30px; display: inline; align: left"> &nbsp;
											${cmt.userid} &nbsp; (${cmt.writeDate}) &nbsp;
											
											<c:choose>
												<c:when test="${cmtUserid == sesUserid}">
													<a onclick="updateComment1(${board.boardNum}, ${cmt.commentNum})">수정</a> &nbsp;
													<a onclick="deleteComment(${board.boardNum}, ${cmt.commentNum})">삭제</a> &nbsp;
												</c:when>
											</c:choose>
											
											<a href="likeCnt.co?commentNum=${cmt.commentNum}&select=2&boardNum=${board.boardNum}"><img src="img/thumbup.png"></a> ${cmt.likeCnt} &nbsp;
											<a href="dislikeCnt.co?commentNum=${cmt.commentNum}&select=2&boardNum=${board.boardNum}"><img src="img/thumbdown.png"></a> ${cmt.dislikeCnt}
										</td>
									</tr>
									<tr>
										<td>
											<input type="text" id="commentInput1" value="${cmt.commentContent}"
												size="100" class="form-control" style="font-size: 12px">
										</td>
									</tr>
									
									<tr>
										<td>
											<!-- 답글 작성 -->
											<span class="container" id="insert_step_comment1" style="font-size: 11px">
												<form action="insertCommentRe.co" name="commentInsert">
													<input type="hidden" name="userid" value="<%=userid%>" />
													<input type="hidden" name="boardNum" value="${board.boardNum}" />
													<input type="hidden" name="parentCommentNum" value="${cmt.commentNum}" />
													<font color="lightgray"></font>
													&nbsp;&nbsp;&nbsp;&nbsp;<input type="text"
														style="border: 1px solid #eeeeee; width: 550px; height: 30px; word-spacing: 1px; word-break: break-all"
														name="commentContent" placeholder="┗답글을 입력하세요.">
													<button style="border: 1px solid #eeeeee; height: 30px" type="submit"
														name="commentInsertBtn">등록</button>
												</form>
											</span>
										</td>
									</tr>
									<br>
								</c:when>
								
								<c:otherwise>
									<tr>
										<td>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<img src="/KGDevelopers/img/logo.jpg" class="img-responsive img-circle"
												style="height: 30px; width: 30px; display: inline; align: left"> &nbsp;
											${cmt.userid} &nbsp; (${cmt.writeDate}) &nbsp;
											
											<c:choose>
												<c:when test="${cmtUserid == sesUserid}">
													<a onclick="updateComment2(${board.boardNum}, ${cmt.commentNum})">수정</a> &nbsp;
													<a onclick="deleteComment(${board.boardNum}, ${cmt.commentNum})">삭제</a> &nbsp;
												</c:when>
											</c:choose>
											
											<a href="likeCnt.co?commentNum=${cmt.commentNum}&select=2&boardNum=${board.boardNum}"><img src="img/thumbup.png"></a> ${cmt.likeCnt} &nbsp;
											<a href="dislikeCnt.co?commentNum=${cmt.commentNum}&select=2&boardNum=${board.boardNum}"><img src="img/thumbdown.png"></a> ${cmt.dislikeCnt}
										</td>
									</tr>
									<tr>
										<td>
											<input type="text" id="commentInput2" value="          ┗ re:     ${cmt.commentContent}"
												size="100" class="form-control" style="font-size: 12px">
										</td>
									</tr>
								</c:otherwise>
							</c:choose>
							
					</table>
				</div>
			
			</c:forEach>
			
		</div>
		
		<!-- 댓글 처리 끝 -->
		
		<hr>
		
		
<div style="padding-top:30px 100px;" align="center">
	<a href = "getBoardList.bo?boardClass=${board.boardClass}"class = "btn btn-primary">목록</a>
	<!-- 작성자가 본인인지 아닌지 판단  관리자는 모든 버튼의 권한을 받아야합니다
		  삭제 액션이 발생했을때 관리자인자 본인 작성글인지 판단해서 회원에게 경고가 누적되도록 만들어야합니다.-->
	
	<% if(board.getUserid().equals(userid) || userid.equals("admin")) { %>
		<a href = "getBoard.bo?boardNum=${board.boardNum}&update=yes" class = "btn btn-primary">수정</a>
		<a href = "deleteBoard.bo?boardNum=${board.boardNum}" class = "btn btn-primary">삭제</a>
	<% } %>
	
</form>
</div>
</div>
<br><br>
<jsp:include page="/include/footer.jsp"/>
</body>
</html>