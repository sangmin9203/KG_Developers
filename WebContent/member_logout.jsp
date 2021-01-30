<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content = "text/html charset=UTF-8" >
<title>Logout</title>
</head>
<%
 session.invalidate();
%>
	<script>
		location.href = 'index.jsp';
	</script>


</body>
</html>