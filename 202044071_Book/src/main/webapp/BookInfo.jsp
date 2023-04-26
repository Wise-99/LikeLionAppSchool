<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.io.File" %>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	td{							
		padding 		: 10px;				/* 테이블 줄 간격 */
	}
	img{						
		width 			: 250px;			/* 이미지 크기 고정 및 여백 설정 */
		height 			: 300px;
		margin-left 	: 50px; 
		margin-right 	: 50px; 
		margin-top 		: 20px;
	}
</style>
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");
	String fileSave = "/fileSave";
	String real = application.getRealPath(fileSave);
	int max = 1024 * 1024 * 10;
	
	MultipartRequest mr = 
			new MultipartRequest(request, real, max, "utf-8",
			new DefaultFileRenamePolicy());
	
	String code 		= mr.getParameter("code");
	String title 		= mr.getParameter("title");
	String price 		= mr.getParameter("price");
	String writer 		= mr.getParameter("writer");
	String publisher	= mr.getParameter("publisher");
	String date 		= mr.getParameter("date");
	String pages 		= mr.getParameter("pages");
	String info 		= mr.getParameter("info");
	String field 		= mr.getParameter("field");
	String stock 		= mr.getParameter("stock");
	String state 		= mr.getParameter("state");

	String filename 	= mr.getFilesystemName("file");
	File file 			= new File(real+"/"+filename);
%>
<table width="100%">	<!-- 상단 메뉴와 제목을 다른 테이블로 분리하여 창의 크기에 맞춰 보이도록 설정 -->
	<tr><td bgcolor = "333333" colspan="4">
	<jsp:include page="/menu.jsp" flush="false"/>
	</td></tr>
	<tr><td height = "100" bgcolor = "EEEEEE" align = "left" colspan="4">
	<h1>도서 정보</h1>
	</td></tr>
</table>
<table>
<tr><td> <!-- 이미지 여백 설정 및 출력 -->
	<img alt="<%= filename%>" src="fileSave/<%=filename%>" style="margin-left : 50px; margin-right : 50px; margin-top : 20px">
</td>
<td width="600px" style="word-break:break-all"> <!-- 해당 td의 넓이 고정 및 자동 줄 바꿈 설정 -->
	<h2><%=title %></h2>
	<b><%=info %><br><br>
	도서코드 : 	<%= code%><br>
	저자 : 		<%= writer%><br>
	출판사 : 		<%= publisher%><br>
	출판일 : 		<%= date%><br>
	총 페이지 수 : <%= pages%><br>
	재고 수 : 	<%= stock%><br>
	분류 : 		<%= field%><br>
	상태 : 		<%= state%><br>
	가격 : 		<%= price%><br><br>
	</b>
<form method="post" action="BookList.jsp">

	<!-- type = "hidden"으로 리스트에 필요한 정보를 넘긴다.  -->
	
	<input type = "hidden" name="filename" 	value="<%=filename %>">
	<input type = "hidden" name="title" 	value="<%=title %>">
	<input type = "hidden" name="info" 		value="<%=info %>">
	<input type = "hidden" name="writer" 	value="<%=writer %>">
	<input type = "hidden" name="publisher" value="<%=publisher %>">
	<input type = "hidden" name="price" 	value="<%=price %>">
	
	<input type = "submit" value = "도서목록">
</form>
</td></tr>
</table>
<div align="center">		<!--footer는 창 크기에 맞게 늘어나거나 줄어들도록 div 설정 -->
	<jsp:include page="/footer.jsp" flush="false"/>
</div>
</body>
</html>