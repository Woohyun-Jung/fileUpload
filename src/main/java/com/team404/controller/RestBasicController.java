package com.team404.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team404.command.FreeReplyVO;
import com.team404.command.TestVO;

@RestController // 비동기 전용 컨트롤러 (requestBody + responseBody)
public class RestBasicController {

	/*
	 *  1. RestController는 기본적으로 return에 담기는 값이 리졸버 뷰로 가는게 아니고,
	 *  요청된 주소로 반환된다.
	 *  2. Rest API에서는 produces키워드는 보내는 데이터에 대한 내용
	 *  consumers는 전달받는 데이터에 대한 내용.
	 */
	
//	@RequestMapping(value="/getText", method=RequestMethod.GET)
	@GetMapping(value="/getText", produces="text/plain")
	public String getTest() {
		
		return "hello world";
	}
	
	/*
	 * 자바에서는 json형식을 받고, json형식을 보낼 때 jackson 라이브러리가 반드시 필요하다.
	 * json 형식의 데이터라면 produces에 내용을 생략할 수 있다.
	 * consumer를 장성하게 되면 해당 데이터 타입이 아니라면 요청을 거절한다.
	 */
	
	@GetMapping(value="/getObject", produces="application/json")
	public TestVO getObject() {
		
		return new TestVO("홍길동", "20", "2020", 2000);
	}
	
	// 단일 값을 받고, 객체를 반환, 요청방식 /getCollection?num=값
	@GetMapping(value = "/getCollection")
	public ArrayList<TestVO> getCollection(@RequestParam("num") String num) {	
		
		System.out.println("받은 값: " + num);
		
		ArrayList<TestVO> list = new ArrayList<>();
		for(int i = 1; i <= 10; i ++) {
			
			TestVO vo = new TestVO("홍길동", i+"", "2020", 2000);
			list.add(vo);
			
		}
		
		return list;
		
	}
	
	// 값/값/값 형태로 받고 Map으로 반환
	@GetMapping("/getPath/{sort}/{rank}/{page}")
	public HashMap<String, TestVO> getPath(@PathVariable("sort") String sort,
										   @PathVariable("rank") String rank,
										   @PathVariable("page") int page) {
		
		System.out.println(sort);
		System.out.println(rank);
		System.out.println(page);
		
		HashMap<String, TestVO> map = new HashMap<>();
		
		TestVO vo = new TestVO("홍길동", "20", "2020", 2000);
		map.put("info", vo);
		
		return map;
		
	}
	
	// post형식의 JSON 데이터를 받는다.
	// 요청값의 키:값을 뽑아서 vo에 자동으로 맵핑하는 태그 @RequestBody
	@PostMapping("/getJson")
	public ArrayList<TestVO> getJson(@RequestBody TestVO vo) {
		
		ArrayList<TestVO> list = new ArrayList<>();
		
		TestVO t = new TestVO("홍길동", "20", "2020", 2000);
		list.add(t);
		
		return list;
		
	}

	@CrossOrigin(origins = "*")
	// consumes - json으로 보내라
	// produces - json으로 보낸다
	@PostMapping(value = "/getAjax", consumes = "application/json", produces = "applicaion/json")
	public ArrayList<TestVO> getAjax(@RequestBody TestVO vo) {
		
		System.out.println(vo.toString());
		
		ArrayList<TestVO> list = new ArrayList<>();
		
		TestVO t = new TestVO("아이유", "20", "2020", 2000);
		list.add(t);
		
		return list;
		
		
	}
	
	// xml 형식으로 반환 (jackson xml라이브러리 필요)
	@CrossOrigin(origins = "*")
	// consumes - json으로 보내라
	// produces - xml로 보낸다
	@PostMapping(value = "/getXML", consumes = "application/json", produces = "application/xml")
	public ArrayList<TestVO> getXML(@RequestBody TestVO vo) {
		
		System.out.println(vo.toString());
		
		ArrayList<TestVO> list = new ArrayList<>();
		
		TestVO t = new TestVO("아이유", "20", "2020", 2000);
		list.add(t);
		
		return list;
		
		
	}
}








































