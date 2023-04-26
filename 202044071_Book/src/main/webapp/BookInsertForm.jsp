<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	td{
		padding : 10px;			/* 테이블 줄 간격을 위해 padding 설정 */
	}
	th{
		text-align : left;		/* 입력 항목 왼쪽 정렬 */
	}
</style>
</head>
<body>
<form method="post" action="BookInfo.jsp" enctype="multipart/form-data">
<table width="100%">		<!-- 상단 메뉴와 제목을 다른 테이블로 분리하여 창의 크기에 맞춰 보이도록 설정 -->
	<tr><td bgcolor = "333333" colspan="4">
	<jsp:include page="/menu.jsp" flush="false"/>
	</td></tr>
	<tr><td height = "100" bgcolor = "EEEEEE" align = "left" colspan="4">
	<h1>도서 등록</h1>
	</td></tr>
</table>
<div style="padding-left : 50px">
<table width="400px">	<!-- 창이 늘어나도 해당 테이블은 늘어나면 안되게 때문에 테이블 넓이 고정  -->
	<!-- 모든 항목을 입력해야 넘어갈 수 있게 required = "required"로 설정 -->
	<tr>
		<th>도서 코드 </th><td><input type="text" name="code" required = "required"></td>
	</tr>
	<tr>
		<th>도서 명 </th><td><input type="text" name="title" required = "required"></td>
	</tr>
	<tr>
		<th>가격 </th><td><input type="text" name="price" required = "required"></td>
	</tr>
	<tr>
		<th>저자 </th><td><input type="text" name="writer" required = "required"></td>
	</tr>
	<tr>
		<th>출판사 </th><td><input type="text" name="publisher" required = "required"></td>
	</tr>
	<tr>
		<th>출판일 </th><td><input type="text" name= "date" required = "required"></td>
	</tr>
	<tr>
		<th>총 페이지 수 </th><td><input type="text" name="pages" required = "required"></td>
	</tr>
	<tr>
	<th>상세정보 </th><td><textarea name = "info" rows="5" cols="30" placeholder = "100자 이상 적어주세요." required = "required" minlength="100"></textarea></td>
	<!-- 100자 이상 적어야 하기 때문에 minlength = 100으로 설정 -->
	<tr>
		<th>분류 </th><td><input type="text" name="field" required = "required"></td>
	</tr>
	<tr>
		<th>재고 수 </th><td><input type="text" name="stock" required = "required"></td>
	</tr>
	<tr>
		<th>상태</th><td>
		<input type="radio" id="new" name="state" value="신규도서" checked><label for="new">신규도서</label>
		<input type="radio" id="used" name="state" value="중고도서"><label for="used">중고도서</label>
		<input type="radio" id="e-book" name="state" value="E-book"><label for="e-book">E-book</label></td>
	</tr>
	<tr>
		<th>이미지</th><td><input type="file" name="file" required = "required"></td>
	</tr>
	<tr>
		<td><input type="submit" value="등록"></td>
	</tr>
</table>
</div>
</form>
<div align="center">	<!--footer는 창 크기에 맞게 늘어나거나 줄어들도록 div 설정 -->
	<jsp:include page="/footer.jsp" flush="false"/>
</div>
</body>
</html>