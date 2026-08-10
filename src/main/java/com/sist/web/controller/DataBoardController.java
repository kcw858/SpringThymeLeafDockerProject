package com.sist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sist.web.service.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import com.sist.web.vo.*;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DataBoardController {
	private final DataBoardService dService;
	
	@GetMapping("/databoard/list")
	public String databoard_list(@RequestParam(value = "page",required = false)String page,Model model)
	{
		if(page == null)
			page="1";
	
		int curpage = Integer.parseInt(page);
		int start = (curpage*10)-10;
		List<DataBoardVO> list = dService.dataBoardListData(start);
		int totalpage = dService.dataBoardTotalPage();
		
		model.addAttribute("list", list);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("curpage", curpage);
		model.addAttribute("main_html", "databoard/list");
		return "main/main";
	}
	
	@GetMapping("/databoard/insert")
	public String databoard_insert(Model model)
	{
		model.addAttribute("main_html", "databoard/insert");
		return "main/main";
	}
	
	@PostMapping("/databoard/insert_ok")
	public String databoard_insert_ok(@ModelAttribute("vo")DataBoardVO vo,HttpServletRequest request) throws Exception
	{
		//환경이 ubuntu, window.. 등 각 다르기 때문에 realPath
		String uploadDir = request.getServletContext().getRealPath("/upload");
		System.out.println(uploadDir);
		File dir = new File(uploadDir);
		
		//upload폴더가 없으면 생성
		if(!dir.exists())
		{
			dir.mkdirs();
			/*
			 *  new File("upload") => mkdir
			 *  new File("/upload/image"); => mkdirs
			 */
		}
		
		List<MultipartFile> files = vo.getFiles();
		String filename = ""; //a.jpg,b.jpg ..
		String filesize = ""; 
		
		boolean bCheck = false; //파일 구분
		
		for(MultipartFile file : files)
		{
			if(file.isEmpty())
			{
				bCheck = false;
			}
			else
			{
				String oname = file.getOriginalFilename();
				File f = new File(uploadDir,oname);
				
				if(f.exists()) //같은 파일명이 존재한다면
				{
					String name = oname.substring(0,oname.lastIndexOf("."));
					String ext = oname.substring(oname.lastIndexOf("."));
					int count = 1;
					while(f.exists())
					{
						String newname = name+"("+count+")"+ext;
						f= new File(uploadDir+"/"+newname);
						count++;
					}
			
				}
				
				bCheck=true;
				//Paths.get -> 운영체제에 따라서 / \ 자동 변경
				Path path = Paths.get(uploadDir,f.getName());
				Files.copy(file.getInputStream(), path);
				filename+=f.getName()+",";
				filesize+=f.length()+",";
			}
		}
		
		//DB처리
		if(bCheck == true)
		{
			//마지막 콤마는 지운다
			filename = filename.substring(0,filename.lastIndexOf(","));
			filesize = filesize.substring(0,filesize.lastIndexOf(","));
			vo.setFilename(filename);
			vo.setFilesize(filesize);
			vo.setFilecount(files.size());
			
		}else
		{
			vo.setFilename("");
			vo.setFilesize("");
			vo.setFilecount(0);
		}
		
		dService.dataBoardInsert(vo);
		return "redirect:/databoard/list";
	}
}
