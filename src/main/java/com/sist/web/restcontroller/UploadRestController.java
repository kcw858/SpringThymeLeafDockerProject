package com.sist.web.restcontroller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadRestController {
	//yml 값을 읽기
	@Value("${file.upload_dir}")
	private String uploadDir;
	
	private static int count=1; //다중 업로드 (같은 파일명일 경우)
	
	// multipart/form-data => protocol (약속)
	// post 기반
	@PostMapping("/upload_ok")
	public String upload_ok(@RequestParam(value="file",required = false)MultipartFile file) throws Exception
	{
		File f = new File(uploadDir);
		
		//upload 폴더가 없으면 폴더를 생성
		if(!f.exists())
		{
			f.mkdir();
		}
		
		if(file.isEmpty())
		{
			return "파일이 없습니다!!";
		}
		
		String oname = file.getOriginalFilename(); //사용자가 보낸 파일명
		
		File files = new File(uploadDir+"/"+oname);
		String newName = oname; 
		
		//파일명 중복 없이
		if(files.exists())
		{
			String name = oname.substring(0,oname.lastIndexOf("."));//파일명
			String ext = oname.substring(oname.lastIndexOf("."));	//확장자
			
			newName = name+"("+count+")"+ext;
			count++;
		}
		
		//업로드
		Path savePath = Paths.get(uploadDir,newName);
		Files.copy(file.getInputStream(), savePath);
		
		return "업로드 성공: "+oname+", 변경: "+newName;
	}
	
	@PostMapping("/multi-upload")
	public String multi_upload(@RequestParam(value="files",required = false)List<MultipartFile> files) throws Exception
	{
		for(MultipartFile file : files)
		{
			if(file.isEmpty())
			{
				return "파일이 존재하지 않습니다";
			}
			else
			{
				String oname = file.getOriginalFilename();
				System.out.println(oname);
				File f = new File(uploadDir+"/"+oname);
				if(f.exists())
				{
					String name = oname.substring(0,oname.lastIndexOf("."));//파일명
					String ext = oname.substring(oname.lastIndexOf("."));	//확장자
					int cnt = 1;
					
					while(f.exists())
					{
						String newName = name+"("+cnt+")"+ext;
						System.out.println(newName);
						f = new File(uploadDir+"/"+newName);
						cnt++;
					}
				}
				
				Path savePath = Paths.get(uploadDir,f.getName());
				Files.copy(file.getInputStream(), savePath);
			}
		}
		
		return "다중 업로드 완료";
	}
}
