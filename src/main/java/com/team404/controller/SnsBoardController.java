package com.team404.controller;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.team404.command.MultiUploadVO;
import com.team404.command.SnsBoardVO;
import com.team404.command.UploadVO;
import com.team404.command.UserVO;
import com.team404.snsboard.service.SnsBoardService;

@Controller
@RequestMapping("/snsBoard")
public class SnsBoardController {

	@Autowired
	@Qualifier("snsBoardService")
	private SnsBoardService snsBoardService;
	
	@RequestMapping("/upload")
	public void upload() {
		
	}
	
	// 단일 파일 업로드 형식
	@RequestMapping("/upload_ok")
	public String upload_ok(@RequestParam("file") MultipartFile file) {
		
		try {
			
			String fileRealName = file.getOriginalFilename(); // 실제 파일명
			long size = file.getSize(); // 파일 크기
			// 확장자명
			String fileExtention = fileRealName.substring( fileRealName.lastIndexOf("."), fileRealName.length() );
			
			System.out.println("파일명: " + fileRealName);
			System.out.println("파일크기: " + size);
			System.out.println("확장자명: " + fileExtention);
			
			File saveFile = new File(APP_CONSTANT.UPLOAD_PATH + "\\" + fileRealName); // 업로드 경로
			
			file.transferTo(saveFile); // 실제 파일을 로컬환경으로 저장
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return "snsBoard/upload_ok"; // 결과 화면
	}
	
	//다중 파일 업로드
	@RequestMapping("upload_ok2")
	public String upload_ok2(MultipartHttpServletRequest files) {
	
		List<MultipartFile> file = files.getFiles("file"); // 폼에서 전달받는 이름
		
		try {
			
			for (int i = 0; i < file.size(); i++) {
				
				String fileRealName = file.get(i).getOriginalFilename(); // 실제 파일명
				long size = file.get(i).getSize(); // 파일 크기
				// 확장자명
				String fileExtention = fileRealName.substring( fileRealName.lastIndexOf("."), fileRealName.length() );
				
				System.out.println("파일명: " + fileRealName);
				System.out.println("파일크기: " + size);
				System.out.println("확장자명: " + fileExtention);
				
				File saveFile = new File(APP_CONSTANT.UPLOAD_PATH + "\\" + fileRealName); // 업로드 경로
				
				file.get(i).transferTo(saveFile); // 실제 파일을 로컬환경으로 저장
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return "snsBoard/upload_ok";
	}
	
	// 다중 파일 업로드 2
	@RequestMapping("/upload_ok3")
	public String upload_ok3(@RequestParam("file") List<MultipartFile> file) {
		
		try {
			
			for(int i = 0; i < file.size(); i++) {
				
				String fileRealName = file.get(i).getOriginalFilename(); // 실제 파일명
				long size = file.get(i).getSize(); // 파일 크기
				// 확장자명
				String fileExtention = fileRealName.substring( fileRealName.lastIndexOf("."), fileRealName.length() );
				
				System.out.println("파일명: " + fileRealName);
				System.out.println("파일크기: " + size);
				System.out.println("확장자명: " + fileExtention);
				
				File saveFile = new File(APP_CONSTANT.UPLOAD_PATH + "\\" + fileRealName); // 업로드 경로
				
				file.get(i).transferTo(saveFile); // 실제 파일을 로컬환경으로 저장
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return "snsBoard/upload_ok";
	}
	
	// 가변적인 폼 형식의 업로드
	@RequestMapping("/upload_ok4")
	public String upload_ok4(MultiUploadVO vo) {
		
		ArrayList<UploadVO> list = vo.getList();
		
		try {
			
			for(int i = 0; i < list.size(); i++) {
				
				String fileRealName = list.get(i).getFile().getOriginalFilename(); // 실제 파일명
				long size = list.get(i).getFile().getSize(); // 파일 크기
				// 확장자명
				String fileExtention = fileRealName.substring( fileRealName.lastIndexOf("."), fileRealName.length() );
				
				String name = list.get(i).getName(); // 폼에서 작성한 이름
				
				System.out.println("파일명: " + fileRealName);
				System.out.println("파일크기: " + size);
				System.out.println("확장자명: " + fileExtention);
				System.out.println("이름: " + name);
				
				File saveFile = new File(APP_CONSTANT.UPLOAD_PATH + "\\" + fileRealName); // 업로드 경로
				
				list.get(i).getFile().transferTo(saveFile); // 실제 파일을 로컬환경으로 저장
				
			}
			
		} catch (Exception e) {
			
			
			
		}
		
		return "snsBoard/upload_ok";
	}
	
	////////////////////////////////////////////////////////
	
	// 비동기 업로드 화면처리
	@RequestMapping("/snsList")
	public void snsList() {
		
	}
	
	// 
	@ResponseBody
	@RequestMapping(value = "/snsUpload", method = RequestMethod.POST)
	public String upload(@RequestParam("content") String content,
						 @RequestParam("file") MultipartFile file,
						 HttpSession session) {
		
		
		try {
			
			UserVO userVO = (UserVO)session.getAttribute("userVO");
			String writer = "test"; //userVO.getUserId();
			
			// 업로드별 날짜폴더 생성
			Date date = new Date(); // java.util.Date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			
			String fileLoca = sdf.format(date);
			
			File folder = new File(APP_CONSTANT.UPLOAD_PATH + "\\" + fileLoca); // 폴더를 만들 위치
			
			if(!folder.exists()) {
				folder.mkdir(); // 폴더 생성
			}
			
			// 파일명
			String fileRealName = file.getOriginalFilename();
			
			// 사이즈
			Long size = file.getSize();
			
			// 저장된 전체 경로
			String uploadPath = folder.getPath(); // 폴더명을 포함한 경로
			
			// 확장자
			String fileExtention = fileRealName.substring( fileRealName.lastIndexOf("."), fileRealName.length() );
			
			UUID uuid = UUID.randomUUID();
			
			String uuids = uuid.toString().replaceAll("-", ""); // 가짜 파일명
			
			// 업로드 파일명
			String fileName = uuids + fileExtention;
			
			System.out.println("폴더위치: " + fileLoca);
			System.out.println(("원본파일명: " + fileRealName));
			System.out.println("크기: " + size);
			System.out.println("업로드경로: " + uploadPath);
			System.out.println("업로드파일명: " + fileName);
			
			File saveFile = new File(uploadPath + "\\" + fileName);
			file.transferTo(saveFile); // 파일쓰기
			
			// DB 작업
			SnsBoardVO vo = new SnsBoardVO(0, writer, content, uploadPath, fileLoca, fileName, fileRealName, null);
			int result = snsBoardService.insert(vo);
			
			if(result == 1) {
				return "success";
			} else {
				return "fail";
			}
			
		} catch (NullPointerException e) { // 에러시에 fail
			return "idFail";
		} catch (Exception e) {
			return "fail1";
		}
		
	}
	
	// 조회 요청
	@ResponseBody
	@RequestMapping(value="/getList", method = RequestMethod.GET)
	public ArrayList<SnsBoardVO> getList() {
		
		ArrayList<SnsBoardVO> list = snsBoardService.getList();
		
		return list;
	}
	
	// 이미지 데이터 반환요청
//	@ResponseBody
//	@RequestMapping(value="/view/{fileLoca}/{fileName:.+}") // 경로에 특수문자를 허용
//	public byte[] view(@PathVariable("fileLoca") String fileLoca,
//					   @PathVariable("fileName") String fileName) {
//		
//		byte[] result = null;
//		
//		try {
//			
//			// 파일 데이터를 바이트데이터로 변환해서 반환
//			
//			File file = new File(APP_CONSTANT.UPLOAD_PATH + "\\" + fileLoca + "\\" + fileName);
//		
//			result = FileCopyUtils.copyToByteArray(file);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
	
	// 이미지데이터 반환 2nd
	@ResponseBody
	@RequestMapping(value="/view/{fileLoca}/{fileName:.+}") // 경로에 특수문자를 허용
	public ResponseEntity<byte []> view(@PathVariable("fileLoca") String fileLoca,
					   					@PathVariable("fileName") String fileName) {
		
		ResponseEntity<byte[]> result = null;
		
		try {
			
			// 파일 데이터를 바이트데이터로 변환해서 반환
			
			File file = new File(APP_CONSTANT.UPLOAD_PATH + "\\" + fileLoca + "\\" + fileName);
		
			// 반환할 헤더객체(다운로드 형식으로 속성을 추가)
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-type", Files.probeContentType(file.toPath()) );
			
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK); // body에 담을 내용, header의 내용, 상태정보
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 파일 다운로드
	@ResponseBody
	@RequestMapping(value="/download/{fileLoca}/{fileName:.+}") // 경로에 특수문자를 허용
	public ResponseEntity<byte []> download(@PathVariable("fileLoca") String fileLoca,
					   						@PathVariable("fileName") String fileName) {
		
		ResponseEntity<byte[]> result = null;
		
		try {
			
			// 파일 데이터를 바이트데이터로 변환해서 반환
			
			File file = new File(APP_CONSTANT.UPLOAD_PATH + "\\" + fileLoca + "\\" + fileName);
		
			// 반환할 헤더객체(다운로드 형식으로 속성을 추가)
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-Disposition", "attachment; fileName=" + fileName );
			
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK); // body에 담을 내용, header의 내용, 상태정보
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
































