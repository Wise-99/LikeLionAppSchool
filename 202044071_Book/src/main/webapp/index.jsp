<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table{
		width : 100%;
	}
	td{
		padding : 10px;
	}
</style>
</head>
<body>
	<table>
	<tr><td bgcolor = "333333">
	<jsp:include page="/menu.jsp" flush="false"/>	<!-- 상단 menu -->
	</td></tr>
	<tr><td height = "300" bgcolor = "EEEEEE" align = "center">
	<h1>도서 웹 쇼핑몰</h1>
	</td></tr>
	<tr><td align = "center">
	<br>
	<b>Welcome to Book Market!</b><br><br>
	<%
		Date nowTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy년 MM월 dd일 a hh:mm:ss");
	%>
	현재 접속 시각 : <%= sf.format(nowTime) %>
	</td></tr>
	<tr><td align = "center">
	<jsp:include page="/footer.jsp" flush="false"/> <!-- 하단 footer -->
	</table>
</body>
</html>