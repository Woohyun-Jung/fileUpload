package com.team404.command;

import java.sql.Timestamp;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

		private String userId;
		private String userPw;
		private String userName;
		private String userEmail1;
		private String userEmail2;
		private String addrZipNum;
		private String addrBasic;
		private String addrDetail;
		private Timestamp regdate;
		
		// N관계의 테이블을 list형태로 선언
		private ArrayList<FreeBoardVO> userBoardList;
	
}
