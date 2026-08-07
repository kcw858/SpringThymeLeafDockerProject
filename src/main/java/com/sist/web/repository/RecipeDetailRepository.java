package com.sist.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sist.web.entity.RecipeDetail;

@Repository
public interface RecipeDetailRepository extends JpaRepository<RecipeDetail, Integer>{
	public RecipeDetail findByNo(int no);
}
