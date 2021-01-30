<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content = "text/html; charset=UTF-8">
<meta name="viewport" content = "width-device-width" initail-scale="1">
<link rel="stylesheet" href = "css/bootstrap.css">
<title>Insert title here</title>
<style type="text/css">
   .virtual-box {
   margin-bottom: 20px;
}
   
   a{
   }

</style>
</head>
<body>
<div class="virtual-box"></div>
<jsp:include page="/include/header.jsp"/>
<form action = "updateBoard.bo?boardNum=${board.boardNum}" method="post" enctype="multipart/form-data">
<div class ="container">
 <div class ="row">
 <table class="table table-striped" style="tex-align : center; border : 1px soid #dddddd">
 <thead>
 <th colspan="3" style="background-color: #eeeeee; text-align : center;">글 수정하기</th>
 </thead>
 <tbody>
    <tr>
       <td style="width : 20%">글 제목</td>
       <td colspan = "2"><input type="text" name="boardTitle" value="${board.boardTitle}"></td>
    </tr>
    <tr>
       <td>작성자</td>
       <td colspan = "2">${board.userid}</td>
    </tr>   
    <tr>
       <td>작성 일자</td>
       <td colspan = "2">${board.writeDate}</td>
    </tr>
    <tr>
       <td>내용</td>
       <td colspan="2" style="min-height : 400px; text-align: left">
          <textarea  rows="15" cols="140" class="form-control" name="boardContent">${board.boardContent}</textarea>
       </td>
    </tr>
    <tr>
		<td class="stitle">파일첨부</td>
		<td>${board.file1}<input type="file" name="file1"><br>
			${board.file2}<input type="file" name="file2"><br>
			${board.file3}<input type="file" name="file3">
		</td>
	</tr>
</table>
<div style="padding-top:30px; text-align:center;">
	<a href = "getBoardList.bo?boardClass=${board.boardClass}" class = "btn btn-primary">목록</a>
   <!-- 작성자가 본인인지 아닌지 판단 -->
   <input type="submit" value="수정" class = "btn btn-primary">
   <a href = "deleteBoard.bo?boardNum=${board.boardNum}" class = "btn btn-primary">삭제</a>
</div>
</div>
</div>
</form>
<jsp:include page="/include/footer.jsp"/>
</body>
</html>