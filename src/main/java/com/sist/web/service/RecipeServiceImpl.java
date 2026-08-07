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
import com.sist.web.entity.RecipeDetail;
import com.sist.web.repository.ChefRepository;
import com.sist.web.repository.RecipeDetailRepository;
import com.sist.web.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService{
	private final RecipeRepository rDao;
	private final ChefRepository cDao;
	private final RecipeDetailRepository rdDao;
	
	@Override
	public List<Recipe> findByTitleContains(String title,int page) {

		final int ROWSIZE = 12;
		Pageable pg = PageRequest.of(page-1, ROWSIZE,Sort.by(Sort.Direction.ASC,"no"));
		/*
			SELECT * FROM recipe WHERE title LIKE '%데이터%'
			ORDER BY no ASC
	  		OFFSET (page-1) ROWS FETCH NEXT 12 ROWS ONLY
		 */
		Page<Recipe> pList = rDao.findByTitleContains(title, pg);
		List<Recipe> list = new ArrayList<Recipe>();
		if(pList != null && pList.hasContent())
		{
			list = pList.getContent();
		}
		return list;
	}

	@Override
	public List<Recipe> findByChefContains(String chef,int page) {
	
		final int ROWSIZE = 12;
		Pageable pg = PageRequest.of(page-1, ROWSIZE,Sort.by(Sort.Direction.ASC,"no"));
		/*
			SELECT * FROM recipe WHERE title LIKE '%데이터%'
			ORDER BY no ASC
	  		OFFSET (page-1) ROWS FETCH NEXT 12 ROWS ONLY
		 */
		Page<Recipe> pList = rDao.findByChefContains(chef, pg);
		List<Recipe> list = new ArrayList<Recipe>();
		if(pList != null && pList.hasContent())
		{
			list = pList.getContent();
		}
		return list;
	}

	@Override
	public List<Recipe> recipeListData(int page) {
		int start = (page*12)-12;
		List<Recipe> list = rDao.recipeListData(start);
		
		return list;
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

	@Override
	public int[] getPageDataFind(int mode,int page, int rowsize,String fd) {
		int count = 0;
		if(mode ==1)
		{
			count = (int)rDao.countByTitleContains(fd);
		}else
		{
			count = (int)rDao.countByChefContains(fd);
		}
		int totalpage = (int)(Math.ceil(count/12.0));
		
		
		int startPage = ((page-1)/10*10)+1;
		int endPage = ((page-1)/10*10)+10;
		if(endPage > totalpage)
			endPage = totalpage;
		int[] pages = {page,totalpage,startPage,endPage};
		
		return pages;
	}

	@Override
	public int recipeCount() {
		
		return rDao.recipeCount();
	}

	@Override
	public RecipeDetail findByNo(int no) {
		
		return rdDao.findByNo(no);
	}

}
