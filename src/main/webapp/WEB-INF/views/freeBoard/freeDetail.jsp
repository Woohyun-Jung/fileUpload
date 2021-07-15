<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<section>
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-md-9 write-wrap">
				<div class="titlebox">
					<p>상세보기</p>
				</div>

				<form action="freeModify" method="post">


					<div>
						<label>DATE</label>
						<p>
							<fmt:formatDate value="${boardVO.regdate }" pattern="yyyy-MM-dd" />
						</p>
					</div>
					<div class="form-group">
						<label>번호</label> <input class="form-control" name='bno'
							value='${boardVO.bno}' readonly>
					</div>
					<div class="form-group">
						<label>작성자</label> <input class="form-control" name='writer'
							value='${boardVO.writer}' readonly>
					</div>
					<div class="form-group">
						<label>제목</label> <input class="form-control" name='title'
							value='${boardVO.title }' readonly>
					</div>

					<div class="form-group">
						<label>내용</label>
						<textarea class="form-control" rows="10" name='content' readonly>${boardVO.content }</textarea>
					</div>


					<button type="submit" class="btn btn-primary">변경</button>
					<button type="button" class="btn btn-dark"
						onclick="location.href='freeList'">목록</button>
				</form>
			</div>
		</div>
	</div>
</section>

<section style="margin-top: 80px;">
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-md-9 write-wrap">
				<form class="reply-wrap">
					<div class="reply-image">
						<img src="../resources/img/profile.png">
					</div>
					<!--form-control은 부트스트랩의 클래스입니다 (네임기술)-->
					<div class="reply-content">
						<textarea class="form-control" rows="3" name="reply" id="reply"></textarea>
						<div class="reply-group">
							<div class="reply-input">
								<input type="text" class="form-control" placeholder="이름"
									name="replyId" id="replyId"> <input type="password"
									class="form-control" placeholder="비밀번호" name="replyPw"
									id="replyPw">
							</div>

							<button type="button" class="right btn btn-info" id="replyRegist">등록하기</button>
						</div>

					</div>
				</form>

				<!--여기에접근 반복-->
				<div id="replyList">
					<!-- 
                        <div class='reply-wrap'>
                            <div class='reply-image'>
                                <img src='../resources/img/profile.png'>
                            </div>
                            <div class='reply-content'>
                                <div class='reply-group'>
                                    <strong class='left'>honggildong</strong> 
                                    <small class='left'>2019/12/10</small>
                                    <a href='#' class='right'><span class='glyphicon glyphicon-pencil'></span>수정</a>
                                    <a href='#' class='right'><span class='glyphicon glyphicon-remove'></span>삭제</a>
                                </div>
                                <p class='clearfix'>여기는 댓글영역</p>
                            </div>
                        </div>
                        -->

				</div>
				
				<button type="button" class="btn btn-default btn-block" id="moreList">더보기</button>
				
			</div>
		</div>
	</div>
</section>

<!-- 모달 -->
<div class="modal fade" id="replyModal" role="dialog">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="btn btn-default pull-right"
					data-dismiss="modal">닫기</button>
				<h4 class="modal-title">댓글수정</h4>
			</div>
			<div class="modal-body">
				<!-- 수정폼 id값을 확인하세요-->
				<div class="reply-content">
					<textarea class="form-control" rows="4" id="modalReply"
						placeholder="내용입력"></textarea>
					<div class="reply-group">
						<div class="reply-input">
							<input type="hidden" id="modalRno"> <input
								type="password" class="form-control" placeholder="비밀번호"
								id="modalPw">
						</div>
						<button class="right btn btn-info" id="modalModBtn">수정하기</button>
						<button class="right btn btn-info" id="modalDelBtn">삭제하기</button>
					</div>
				</div>
				<!-- 수정폼끝 -->
			</div>
		</div>
	</div>
</div>

<script>
	
		$(document).ready(function() {
			
			$("#replyRegist").click(function() {
				
				var bno = "${boardVO.bno}"; // 글 번호
				var reply = $("#reply").val();
				var replyId = $("#replyId").val();
				var replyPw = $("#replyPw").val();
				
				if(reply == '' || replyId == '' || replyPw == '') {
					
					alert("이름, 비밀번호, 내용은 필수입니다.");
					return;
					
				}
				
				$.ajax({
					
					type : "post",
					url : "../reply/replyRegist",
					dataType : "json",
					contentType : "application/json; charset=UTF-8",
					data : JSON.stringify({"bno" : bno, "reply" : reply, "replyId" : replyId, "replyPw" : replyPw}),
					success : function(data) {
					
						if(data == 1) { // 성공
							
							$("#reply").val("");
							$("#replyId").val("");
							$("#replyPw").val("");
							getList(1, true); // 데이터 조회 메서드 호출
							
						} else { // 실패
							
							alert("등록에 실패했습니다. 다시 시도해주세요");
							
						}
						
					
					},
					error : function(status, error) {
						
						alert("등록 실패입니다. 잠시 후 다시 시도해 주세요");
						
					}
					
					
				});
				
			});
			
			
			// 페이지 기능
			var page = 1; // 페이지 번호
			var str = ""; // 댓글 목록 누적 변수
			
			$("#moreList").click(function() {
				
				getList(++page, false); // 목록 호출 (페이지 넘버가 리셋되어야 하는 경우)
				
			});
			
			getList(1, true);
			// 데이터 조회
			function getList(pageNum, reset) {
				
				var bno = "${boardVO.bno}" // 게시글 번호
				
				$.getJSON(
					"../reply/getList/" + bno + "/" + pageNum,
					function(data) {
						
						console.log(data);	
						
						var total = data.total // 전체 게시글 수
						var data = data.list
						
						// 페이지에 조건 처리
						if(page * 20 >= total) {
							
							$("#moreList").css("display", "none"); // 더보기 버튼 처리
							
						} else {
							
							$("#moreList").css("display", "block");
							
						}
						
						// reset이 true라면 str을 공백으로 비우고 page = 1로 변경하고 다시 호출
						if(reset == true) {
							str = "";
							page = 1;
						}
						
						
						
						// 누적할 문자열을 만들고 innerHTML형식으로 replyList아래 삽입
						
						for(var i = 0; i < data.length; i++) {
							
							str += "<div class='reply-wrap'>"
							str += "<div class='reply-image'>"
							str += "<img src='../resources/img/profile.png'>"
							str += "</div>"
							str += "<div class='reply-content'>"
							str += "<div class='reply-group'>"
							str +=" <strong class='left'>" + data[i].replyId + "</strong>" 
							str += "<small class='left'>"+ data[i].timegap +"</small>"
							str += "<a href='"+ data[i].rno +"' class='right replyModify'><span class='glyphicon glyphicon-pencil'></span>수정</a>"
							str += "<a href='"+ data[i].rno +"' class='right replyDelete'><span class='glyphicon glyphicon-remove'></span>삭제</a>"
							str += "</div>"
							str += "<p class='clearfix'>"+ data[i].reply +"</p>"
							str += "</div>"
							str += "</div>"
							
						}
						
						$("#replyList").html(str);
						
					});
				
			} // end getList
			
			// 수정 삭제
			/*
			ajax 실행이 더 늦게 완료가 되므로, 실제 이벤트 등록이 먼저 일어나게 된다. (정상 동작 x)
			부모에 on함수를 이용해서 이벤트를 걸고 이벤트를 a태그에 전파시켜서 사용하는 방법
			*/
			
			$("#replyList").on("click", "a", function() {
			
				event.preventDefault(); // 고유이벤트 중지
				
				// 클릭한 대상의 번호를 모달창에 저장
				var rno = $(this).attr("href");
				$("#modalRno").val(rno);
				
				// replyModify 라면 수정창, replyDelete라면 삭제창의 형태로 사용
				
				if( $(this).hasClass("replyModify") ) { // 수정창
					
					$(".modal-title").html("댓글수정");
					$("#modalModBtn").css("display", "inline"); // 수정버튼이 보여지도록
					$("#modalDelBtn").css("display" , "none"); // 삭제 버튼이 안보여지도록
					$("#modalReply").css("display", "inline"); // 수정창이 보여지도록
					
				} else { // 삭제창
					
					$(".modal-title").html("댓글삭제");
					$("#modalModBtn").css("display", "none");
					$("#modalDelBtn").css("display" , "inline");
					$("#modalReply").css("display", "none");
					
				}
				
				
				$("#replyModal").modal("show"); // 부트스트랩 모달 함수
				
				
			})
			
			// 수정 함수
			$("#modalModBtn").click(function() {
				
				var rno = $("#modalRno").val();
				var reply = $("#modalReply").val();
				var replyPw = $("#modalPw").val();
				
				if(rno == "" || reply == "" || replyPw == "") {
					
					alert("내용과 비밀번호는 필수입니다.");
					return;
					
				}
				
				$.ajax({
					
					type : "post",
					url : "../reply/update",
					contentType : "application/json; charset=UTF-8",
					data : JSON.stringify({"rno" : rno, "reply" : reply, "replyPw" : replyPw}),
					success : function(data) {
						
						if (data == 1) { // 업데이트 성공
							
							$("#modalReply").val(""); // 내용 비우기
							$("#modalPw").val("");
							$("#modalRno").val("");
							
							$("#replyModal").modal("hide"); // 모달창 내리기
							getList(1, true); // 조회 메서드 호출
							
						} else { // 실패
							
							alert("비밀번호를 확인하세요");
							$("#modalPw").val("");
							
						}
						
						
					},
					error : function(status, error) {
						
						alert("수정에 실패했습니다. 잠시 후 다시 시도해주세요");
						
					}
				
					
				});
				
				
			})
			
			// 삭제 함수
			$("#modalDelBtn").click(function() {
				
				/*
				1. 모달창에서 rno값, replyPw값을 얻는다.
				2. ajax 함수를 이용해서 post방식으로 "reply/delete" 요청을 보낸다.
				     필요한 값은 REST API에서 JSON형식으로 받아서 처리
				3. 서버에서는 요청을 받아서 비밀번호 확인하고, 비밀번호가 일치하면 삭제를 진행
				4. 비밀번호가 틀린경우는 0을 반환해서 경고창을 띄워준다.
				*/
				
				var rno = $("#modalRno").val();
				var replyPw = $("#modalPw").val();
				
				if (rno == "" || replyPw == "") {
					
					alert("비밀번호 입력은 필수입니다.");
					return;
					
				}
				
				$.ajax({
					
					type : "post",
					url : "../reply/delete",
					contentType : "application/json; charset=UTF-8",
					data : JSON.stringify({"rno" : rno, "replyPw" : replyPw}),
					success : function(data) {
						
						if(data == 1) {
							
							$("#modalPw").val("");
							$("#modalRno").val("");
							
							$("#replyModal").modal("hide");
							getList(1, true);
							
						} else {
							
							alert("비밀번호를 확인하세요");
							$("#modalPw").val("");
							
						}
						
						
					},
					error : function(status, error) {
						
						
						
					}
					
				});
				
				
			})
			
			
			
			
		}); // end ready
		
		
	
	</script>






























