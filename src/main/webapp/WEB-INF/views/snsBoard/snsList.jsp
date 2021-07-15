<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<style type="text/css">
	section {
		margin-top: 70px;
	}
	</style>

	<section>
		<div class="container">
			<div class="row">
				<aside class="col-sm-2">
					<div class="aside-inner">
						<div class="menu1">
							<p>
								<img src="../resources/img/profile.png">박인욱
							</p>
							<ul>
								<li>내정보</li>
								<li>내쿠폰</li>
								<li>내좋아요게시물</li>
							</ul>
						</div>
						<div class="menu2">
							<p>둘러보기</p>
							<ul>
								<li>기부 캠페인</li>
								<li>페이지</li>
								<li>그룹</li>
								<li>이벤트</li>
								<li>친구리스트</li>
							</ul>
						</div>
					</div>
				</aside>
				<div class="col-xs-12 col-sm-8 section-inner">
					<h4>게시물 만들기</h4>
					<!-- 파일 업로드 폼입니다 -->
					<div class="fileDiv">
						<img id="fileImg" src="../resources/img/img_ready.png">
					</div>
					<div class="reply-content">
						<textarea class="form-control" rows="3" name="content"
							id="content" placeholder="무슨 생각을 하고 계신가요?"></textarea>
						<div class="reply-group">
							<div class="filebox pull-left">
								<label for="file">이미지업로드</label> 
								<input type="file" name="file" id="file">
							</div>
							<button type="button" class="right btn btn-info" id="uploadBtn">등록하기</button>
						</div>
					</div>


					<!-- 파일 업로드 폼 끝 -->
					<div id="contentDiv">
					
					<div class="title-inner">
						제목영역
						<div class="profile">
							<img src="../resources/img/profile.png">
						</div>
						<div class="title">
							<p>테스트</p>
							<small>21시간</small>
						</div>
					</div>
					<div class="content-inner">
						내용영역
						<p>삶이 우리를 끝없이 시험하기에 고어텍스는 한계를 테스트합니다</p>
					</div>
					<div class="image-inner">
						이미지영역
						<img src="../resources/img/facebook.jpg">
						
					</div>
					<div class="like-inner">
						좋아요
						<img src="../resources/img/icon.jpg"> <span>522</span>
					</div>
					<div class="link-inner">
						<a href="##"><i class="glyphicon glyphicon-thumbs-up"></i>좋아요</a>
						<a href="##"><i class="glyphicon glyphicon-comment"></i>댓글달기</a> 
						<a href="##"><i class="glyphicon glyphicon-remove"></i>삭제하기</a>
					</div>
					
					</div>
				</div>
				<!--우측 어사이드-->
				<aside class="col-sm-2">
					<div class="aside-inner">
						<div class="menu1">
							<p>목록</p>
							<ul>
								<li>목록1</li>
								<li>목록2</li>
								<li>목록3</li>
								<li>목록4</li>
								<li>목록5</li>
							</ul>
						</div>
					</div>
				</aside>
			</div>
		</div>
	</section>

	<!-- 모달 -->
	<div class="modal fade" id="snsModal" role="dialog">
			<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-body row">
					<div class="modal-img col-sm-8 col-xs-6" >
						<img src="../resources/img/img_ready.png" id="snsImg" width="100%">
					</div>
					<div class="modal-con col-sm-4 col-xs-6">
						<div class="modal-inner">
						<div class="profile">
							<img src="../resources/img/profile.png">
						</div>
						<div class="title">
							<p id="snsWriter">테스트</p>
							<small id="snsRegdate">21시간전</small>
						</div>
						<div class="content-inner">
							<p id="snsContent">삶이 우리를 끝없이 시험하기에 고어텍스는 한계를 테스트합니다</p>
						</div>
						<div class="link-inner">
							<a href="##"><i class="glyphicon glyphicon-thumbs-up"></i>좋아요</a>
							<a href="##"><i class="glyphicon glyphicon-comment"></i>댓글달기</a> 
							<a href="##"><i class="glyphicon glyphicon-share-alt"></i>공유하기</a>
						</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
	
		$(document).ready(function() {
			
			$("#uploadBtn").click(function() {
				
				var writer = '${sessionScope.userVO.userId}'; // 아이디
				var file = $("#file").val();
				var content = $("#content").val();
				
				file = file.slice(file.lastIndexOf(".", file.length) + 1, file.length); // 파일 확장자
				
				if(file != 'jpg' && file != 'png' && file != 'bmp') {
					alert("이미지 파일형태만 등록이 가능합니다.(jpg, png, bmp)");
					return;
				} else if (/* wirter == '' */ false) {
					alert("로그인이 필요한 서비스입니다.")
					return;
				}
				
				// 파일 데이터
				//console.log($("#file")[0]);
				//console.log( $("#file")[0].files );
				//console.log( $("#file")[0].files[0] );
				
				// 비동기 형식의 폼데이터 사용
				var formData = new FormData();
				formData.append("content", content); // 키, 값
				formData.append("file", $("#file")[0].files[0]);
				
				$.ajax({
					
					
					type: "post",
					url: "snsUpload",
					processData: false, // 키 = 값으로 전성되는 것을 막는 옵션
					contentType: false, // default multipart formdata 형식으로 지정
					data: formData,
					success: function(data) {
						
						console.log(data);
						
						if(data == "success") {
							$("#file").val(""); // 태그
							$("#content").val(""); // 내용
							$(".fileDiv").css("display", "none"); // 안보이도록 처리
							
						} else if(data == "idFail") {
							alert("로그인이 필요한 서비스입니다.");
						} else {
							alert("서버에 문제가 발생했습니다. 관리자에게 문의하세요.")
						}
						
					},
					error: function(status, error) {
						alert("서버에 문제가 발생했습니다. 관리자에게 문의하세요.")
					}
					
				});
				
				
			}); // 등록이벤트 끝
			
			/* function getList() {
				
				var strAdd = "";
				
				$.getJSON("getList", function(data) {
					
					console.log(data);
					
					for(var i = 0; i < data.length; i++) {
						strAdd += '<div class="title-inner">'
						strAdd += '<div class="profile">'
						strAdd += '<img src="../resources/img/profile.png">'
						strAdd += '</div>';
						strAdd += '<div class="title">';
						strAdd += '<p>'+ data[i].writer +'</p>';
						strAdd += '<small>'+ data[i].regdate +'</small>';
						strAdd += '</div>';
						strAdd += '</div>';
						strAdd += '<div class="content-inner">';
						strAdd += '<p>'+ data[i].content +'</p>';
						strAdd += '</div>';
						strAdd += '<div class="image-inner">';
						strAdd += '<img src="' + "view/"+data[i].fileLoca+"/"+data[i].fileName + '">';
						strAdd += '</div>';
						strAdd += '<div class="like-inner">';
						strAdd += '<img src="../resources/img/icon.jpg"><span>522</span>';
						//파일다운로드
						strAdd += '<a href="download/'+ data[i].fileLoca+"/"+data[i].fileName +'">파일다운로드</a>'
						strAdd += '</div>';
						strAdd += '<div class="link-inner">';
						strAdd += '<a href="##"><i class="glyphicon glyphicon-thumbs-up"></i>좋아요</a>';
						strAdd += '<a href="##"><i class="glyphicon glyphicon-comment"></i>댓글달기</a>'; 
						strAdd += '<a href="##"><i class="glyphicon glyphicon-remove"></i>삭제하기</a>';
						strAdd += '</div>';
					}
					
					$("#contentDiv").html(strAdd);
					
				});
				
			}
			
			(function() {
				getList();
			})(); */
			
		});
	
		
	
	</script>

	<script>
		//자바 스크립트 파일 미리보기 기능
		function readURL(input) {
        	if (input.files && input.files[0]) {
        		
            	var reader = new FileReader(); //비동기처리를 위한 파읽을 읽는 자바스크립트 객체
            	//readAsDataURL 메서드는 컨텐츠를 특정 Blob 이나 File에서 읽어 오는 역할 (MDN참조)
	        	reader.readAsDataURL(input.files[0]); 
            	//파일업로드시 화면에 숨겨져있는 클래스fileDiv를 보이게한다
	            $(".fileDiv").css("display", "block");
            	
            	reader.onload = function(event) { //읽기 동작이 성공적으로 완료 되었을 때 실행되는 익명함수
                	$('#fileImg').attr("src", event.target.result); 
                	/* console.log(event.target);//event.target은 이벤트로 선택된 요소를 의미 */
	        	}
        	}
	    }
		$("#file").change(function() {
	        readURL(this); //this는 #file자신 태그를 의미
	        
	    });
	</script>
	
	