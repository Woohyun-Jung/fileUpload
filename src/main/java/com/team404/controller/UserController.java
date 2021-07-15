package com.team404.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team404.command.UserVO;
import com.team404.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	// 가입화면
	@RequestMapping("/userJoin")
	public String userJoin() {
		return "user/userJoin";
	}
	
	// 로그인 화면
	@RequestMapping("/userLogin")
	public String userLogin() {
		return "user/userLogin";
	}
	
	// 마이페이지 화면
	@RequestMapping("/userMypage")
	public String userMypage(HttpSession session, Model model ) {
		
//		if(session.getAttribute("userVO") == null) {
//			return "redirect:/user/userLogin";
//		}
		
		// 아이디를 세션으로 얻는다.
		UserVO userVO = (UserVO)session.getAttribute("userVO");
		
		String userId = userVO.getUserId();
		
		// 회원정보 조회
		UserVO userInfo = userService.getInfo(userId);
		
		model.addAttribute("userInfo", userInfo);
		
		
		return "user/userMypage";
	}
	
	// 중복처리 메서드
	@ResponseBody // 일반 컨트롤러에서 ResponseBody는 요청 경로로 return값을 돌려준다.
	@PostMapping(value = "/idCheck", produces = "application/json")
	public int idCheck(@RequestBody UserVO vo) {
		
//		System.out.println(vo.toString());
		
		int result = userService.idCheck(vo);
		
		return result; // 0 이라면 중복x, 1 이라면 중복
	}
	
	// 가입요청
	@RequestMapping( value = "/joinForm", method = RequestMethod.POST)
	public String joinFrom(UserVO vo,
						   RedirectAttributes RA) {
		
		int result = userService.join(vo);
		
		if(result == 1) { // 성공
			
			RA.addFlashAttribute("msg", "가입을 축하합니다.");
			
		} else { // 실패
			
			RA.addFlashAttribute("msg", "가입에 실패했습니다. 다시 시도해주세요.");
			
		}
		
		return "redirect:/user/userLogin";
	}
	

	// PostHandler를 이용한 로그인 요청
	@RequestMapping(value = "/loginForm", method = RequestMethod.POST)
	public ModelAndView loginForm(UserVO vo) {
		
		UserVO userVO = userService.login(vo);
		
		ModelAndView mv = new ModelAndView();
		
		if(userVO != null) { // 로그인 성공
			
			mv.addObject("login", userVO);
			
		} else {
			
			mv.addObject("msg", "아이디 비밀번호를 확인하세요");
			
		}
		
		
		return mv;
	}
	

	// 로그인 요청
//	public String loginForm(UserVO vo, HttpSession session, Model model) {
//		
//		// 로그인 처리
//		
//		UserVO userVO = userService.login(vo);
//		
//		System.out.println(userVO);
//		
//		if(userVO != null) { // 로그인 성공
//			
//			session.setAttribute("userVO", userVO); // 회원정보 저장
//			return "redirect:/"; // 홈 화면
//			
//		} else { // 로그인 실패
//			
//			model.addAttribute("msg", "아이디 비밀번호를 확인하세요");
//			return "user/userLogin"; // 로그인 화면
//			
//		}
//		
//	}
	
	// 로그아웃
	
	@RequestMapping("/userLogout")
	public String userLogout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/";
	}
	
}


























