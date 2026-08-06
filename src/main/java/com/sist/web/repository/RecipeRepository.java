package com.sist.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sist.web.entity.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
	public List<Recipe> findByTitleContains(String title);
	public List<Recipe> findByChefContains(String chef);
	
	/*
	 *  findBy컬럼명연산자
	 *  	  ---- ----
	 *  findByName(String name)
	 *  
	 *  findByTitleStartsWith   title%
	 *  findByTitleContains		%title%
	 *  findByTitleEndsWith		%title
	 *  
	 *  findByOrderByTitleDesc
	 *  
	 *  findAll(Pageable,Sort)
	 *  count()
	 *  save() / delete()
	 */
}
