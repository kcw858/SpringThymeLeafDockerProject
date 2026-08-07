package com.sist.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sist.web.entity.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
	/*
	 * SELECT * FROM recipe WHERE title LIKE '%데이터%'
	 * OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
	 */
	public Page<Recipe> findByTitleContains(String title,Pageable pg);	
	public Page<Recipe> findByChefContains(String chef,Pageable pg);
	
	/*
	 * SELECT COUNT(*) FROM recipe WHERE title LIKE '%데이터%'
	 */
	public long countByTitleContains(String title);
	public long countByChefContains(String chef);
	
	
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
	@Query(value ="""
			SELECT *
			FROM recipe
			WHERE no IN(SELECT no FROM recipe
						INTERSECT
						SELECT no FROM recipedetail)
			ORDER BY no DESC
			OFFSET :start ROWS FETCH NEXT 12 ROWS ONLY
			""",nativeQuery = true)
	public List<Recipe> recipeListData(@Param("start") int page);
	
	@Query(value ="""
			SELECT count(*)
			FROM recipe
			WHERE no IN(SELECT no FROM recipe
						INTERSECT
						SELECT no FROM recipedetail)
			""",nativeQuery = true)
	public int recipeCount();
}
