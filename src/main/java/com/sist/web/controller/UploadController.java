package com.sist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UploadController {
	
	@GetMapping("/upload")
	public String upload_page() {
		
		return "upload";
	}
	
	@GetMapping("/upload2")
	public String upload_page2() {
		
		return "upload2";
	}
	
	@GetMapping("/upload3")
	public String upload_page3() {
		
		return "upload3";
	}
}
