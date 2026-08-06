package com.sist.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sist.web.entity.Chef;
import com.sist.web.entity.Recipe;
import com.sist.web.repository.ChefRepository;
import com.sist.web.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService{
	private final RecipeRepository rDao;
	private final ChefRepository cDao;
	
	@Override
	public List<Recipe> findByTitleContains(String title) {

		return rDao.findByTitleContains(title);
	}

	@Override
	public List<Recipe> findByChefContains(String chef) {
	
		return rDao.findByChefContains(chef);
	}

	@Override
	public List<Recipe> recipeListData(int page) {
		//Pageable -> 페이지 요청 정보
		// 페이지 번호 / 페이지 크기 , 정렬조건
		Pageable pg = PageRequest.of(page-1, 12,Sort.by(Sort.Direction.ASC,"no"));
		
		Page<Recipe> pList = rDao.findAll(pg);
		List<Recipe> list = new ArrayList<Recipe>();
		
		//Page를 List변환
		if(pList != null && pList.hasContent())
		{
			list = pList.getContent();
		}
		return list;
		/*
		 *  JPA => 중심이 객체 단위로 사용
		 *  	
		 *  	객체 ===== Column(메소드) = ORM
		 *  
		 */
	}

	@Override
	public int[] getPageData(int page,int rowsize) {
		
		int count = (int)rDao.count();
		int totalpage = (int)(Math.ceil(count/(double)rowsize));
		int startPage = ((page-1)/10*10)+1;
		int endPage = ((page-1)/10*10)+10;
		if(endPage > totalpage)
			endPage = totalpage;
		int[] pages = {page,totalpage,startPage,endPage};
		return pages;
	}

	@Override
	public List<Chef> chefListData(int page) {
		
	
		Pageable pg = PageRequest.of(page-1,20);
		
		Page<Chef> pList = cDao.findAll(pg);
		List<Chef> list = new ArrayList<Chef>();
		
		if(pList != null && pList.hasContent())
		{
			list = pList.getContent();
		}
		
		return list;
	}

}
