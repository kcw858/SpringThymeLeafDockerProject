package com.sist.web.service;
import java.util.*;

import com.sist.web.entity.Chef;
import com.sist.web.entity.Recipe;

public interface RecipeService {
	public List<Recipe> findByTitleContains(String title);
	public List<Recipe> findByChefContains(String chef);
	public List<Recipe> recipeListData(int page);
	public int[] getPageData(int page,int rowsize);
	public List<Chef> chefListData(int page);
}
