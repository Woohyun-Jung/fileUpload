package com.team404.util.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.team404.command.UserVO;

public class LoginSuccessHandler extends HandlerInterceptorAdapter {

	
	// 로그인 이후에 실행되는 PostHandler 오버라이딩
	// 로그인 요청으로 들어올 때 실행되도록 핸들러 등록
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		// 매개변수 ModelAndView에는 컨트롤러에서 반환하는 mv객체가 들어있다.
		ModelMap mv = modelAndView.getModelMap(); // ModelAndView 객체에 들어있는 정보를 map형식으로 변환
		UserVO userVO = (UserVO)mv.get("login");
		
		if(userVO != null) { // 로그인 성공
			
			// 세션에 저장 
			HttpSession session = request.getSession();
			session.setAttribute("userVO", userVO);
			
			response.sendRedirect(request.getContextPath()); // 홈화면으로
			
		}
			
			// redirect를 만나더라도 viewName은 지정이 필요하다.
			modelAndView.setViewName("/user/userLogin");

		
	}

	
	
}
