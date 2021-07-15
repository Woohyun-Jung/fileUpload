package com.team404.command;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadVO {

	// 폼 화면에서 전달되는 변수 지정
	private String name;
	private MultipartFile file;
	
}
