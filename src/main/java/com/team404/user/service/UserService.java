package com.team404.user.service;

import com.team404.command.UserVO;

public interface UserService {

	public int idCheck(UserVO vo); // 중복체크
	public int join(UserVO vo); // 가입요청
	public UserVO login(UserVO vo); // 로그인
	public UserVO getInfo(String userId); // 회원아이디로 정보를 가져오는 메서드
	
}
