package com.team404.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.team404.command.EmployeeVO;



public class TestController {

	@GetMapping("/getPath/{key}/{code}")
	public HashMap<String, EmployeeVO> getPath(@PathVariable("key") String key,
										   	   @PathVariable("code") String code) {
		
		HashMap<String, EmployeeVO> map = new HashMap<>();
		
		EmployeeVO vo = new EmployeeVO("홍길동", "20", "사원", "서울시 강남구");
		map.put("info", vo);
		
		return map;
		
	}
	
}
