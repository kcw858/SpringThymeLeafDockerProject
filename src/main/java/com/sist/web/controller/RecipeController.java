package com.sist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import com.sist.web.entity.*;
import com.sist.web.service.RecipeService;

import lombok.RequiredArgsConstructor;
/*
 *  요청 ==== DispatcherServlet ==== @Controller
 *  			|						| 
 *  			-------------------------
 *  					| 연동 (필요한 데이터나 내장객체 => 매개변수)
 */

@Controller
@RequiredArgsConstructor // lombok필요
public class RecipeController {
	private final RecipeService rService;
	/*
	 *  1. Repository / Mapper => 데이터베이스만 연동
	 *  		=> 재료
	 *  2. Service => 조립 (Repository / Mapper에서 받은 값)
	 *  		=> 주방
	 *  3. Controller => 조립된 데이터만 받아서 HTML 전송
	 *  		=> 서빙
	 *  
	 */
	@GetMapping("/main/main")
	public String main_main(@RequestParam(name = "page",required = false)String page,Model model)
	{
		/*
		 *  매개변수
		 *  @RequestParam : 단일값 받기
		 *  @ModelAttribute : VO단위로 받기
		 *  @RequestBody : @RestController
		 *  	자바 스크립트 === 전송
		 *  					|
		 *  					VO
		 *  	JSON ==== VO로 변환
		 * 
		 *  => HttpServletRequrst / HttpServletResponse
		 *  		|						|
		 *  		------------------------- Cookie
		 *  => Principal : 보안 - security: session 대처
		 *  
		 *  
		 *  @RequestParam(name = "page",required = false)
		 *  	=> null값을 허용
		 *  	=> 검색 / 페이지
		 *  상세보기는 null 값으로 넘어오지 않으니 int로 받는다
		 */
		if(page == null)
			page="1";
		
		List<Recipe> list = rService.recipeListData(Integer.parseInt(page));
		int[] pages = rService.getPageData(Integer.parseInt(page));
		
		model.addAttribute("list",list);
		model.addAttribute("pages",pages);
		model.addAttribute("main_html","main/home");
		model.addAttribute("curpage",pages[0]);
		model.addAttribute("totalpage",pages[1]);
		model.addAttribute("startPage",pages[2]);
		model.addAttribute("endPage",pages[3]);
		
		return "main/main";
	}
}
