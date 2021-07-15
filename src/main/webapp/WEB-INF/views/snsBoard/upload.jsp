<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div style="margin-top:100px">

	<h3>
		commons-fileupload 라이브로리 추가<br>
		파일 업로드 초기설정
	</h3>


	<!-- 단일 파일 업로드 형식 -->
	<form action="upload_ok" method="post" enctype="multipart/form-data"> 
		파일선택<input type="file" name="file">
		<input type="submit" class="btn btn-info" value="전송">
	</form>
	
	<!-- 다중 파일 업로드 형식 (multiple) -->
	<form action="upload_ok2" method="post" enctype="multipart/form-data">
		파일선택:<input type="file" name="file" multiple="multiple">
		<input type="submit" class="btn btn-info" value="전송">
	</form>
	
	<!-- 다중 파일 업로드 형식 2 -->
	<form action="upload_ok3" method="post" enctype="multipart/form-data">
		파일선택<input type="file" name="file">
		파일선택<input type="file" name="file">
		파일선택<input type="file" name="file">
		<input type="submit" class="btn btn-info" value="전송">
	</form>
	
	<!-- 가변적인 폼 형식의 업로드 -->
	<form action="upload_ok4" method="post" enctype="multipart/form-data">
		이름:<input type="text" name="List[0].name"> <br>
		파일선택:<input type="file" name="List[0].file">
		
		이름:<input type="text" name="List[1].name"> <br>
		파일선택:<input type="file" name="List[1].file">
		
		이름:<input type="text" name="List[2].name"> <br>
		파일선택:<input type="file" name="List[2].file">
		<input type="submit" class="btn btn-info" value="전송">
	</form>
	
</div>