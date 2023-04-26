<%@page import="java.util.ArrayList"%>
<%@page import="java.io.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	td{
		padding 		: 10px;			/* td 가로 길이 고정 */
		border-spacing	: 50px;			/* 줄 간격 조정 */
	}
	img{
		width 			: 150px;		/* 이미지 크기 고정 */
		height 			: 180px;
	}
</style>
</head>
<body>
<%!
	/* 파일 이름을 저장할 리스트를 전역 변수로 생성 */
	ArrayList<String> fileText = new ArrayList<String>();
%>
<%
	request.setCharacterEncoding("UTF-8");

	String fileName 	= request.getParameter("filename");
	String title 		= request.getParameter("title");
	String info 		= request.getParameter("info");
	String writer 		= request.getParameter("writer");
	String publisher	= request.getParameter("publisher");
	String price 		= request.getParameter("price");
	
	/* 책 정보를 저장할 파일명 - 텍스트 파일에 _를 붙여 이미지 파일과 텍스트 파일 구별 */
	String textFileName = fileName + "_.txt"; 
	
	/* 저장 경로를 이미지와 같은 경로로 설정 */
	String filePath 	= application.getRealPath("/fileSave/"+textFileName); 
	
	
	FileWriter fileWriter = new FileWriter(filePath);
	try{
		/* 파일에 저장할 내용(스크립트까지 넣어 저장) */
		String str = "<h2>"+ title + "</h2>\n";
		str += "<b>" + info + "<b>\n";
		str +="<b>" + writer + " | " + publisher + " | " + price + "</b>";
		fileWriter.write(str);
		fileText.add(fileName);		/* 파일의 이름을 리스트에 저장 */
		out.println();

	} catch (IOException e){
		out.println("파일에 데이터를 저장할 수 없습니다.");
	} finally{
		fileWriter.close();
	}
%>

<table width="100%">  <!-- 상단 메뉴와 제목을 다른 테이블로 분리하여 창의 크기에 맞춰 보이도록 설정 -->
	<tr><td bgcolor = "333333" colspan="4">
	<jsp:include page="/menu.jsp" flush="false"/>
	</td></tr>
	<tr><td height = "100" bgcolor = "EEEEEE" align = "left" colspan="4">
	<h1>도서 목록</h1>
	</td></tr>
</table>

<!-- 책 목록을 출력할 테이블 -->
<table>
<%
	/* for문을 이용하여 list에 있는 모든 파일의 이미지와 정보를 출력 */
	for(int i = 0; i < fileText.size(); i++){	
%>
<tr><td>
<img alt="<%= fileText.get(i)%>" src="fileSave/<%=fileText.get(i) %>" style="margin-left : 50px; margin-right : 50px; margin-top : 20px">
</td>
<td width="600px" style="word-break:break-all">		<!-- 해당 td의 넓이 고정 및 자동 줄 바꿈 설정 -->
<%
	/* list 요소에 _를 붙여 텍스트 파일 불러오기 */
	String real 		= application.getRealPath("fileSave/"+fileText.get(i)+"_.txt");
	BufferedReader br 	= new BufferedReader(new FileReader(real));
	
	while(true){		/* 파일의 끝까지 반복 출력 */
		String str = br.readLine();
		if(str == null)	/* 읽어올 내용이 없을 경우 */
			break;
		out.println(str+"<br><br>");	
	}
	br.close();
%>
</td></tr>
<%
	}		/* for문의 끝 */
%>
</table>
<div align="center">		<!--footer는 창 크기에 맞게 늘어나거나 줄어들도록 div 설정 -->
	<jsp:include page="/footer.jsp" flush="false"/>
</div>
</body>
</html>