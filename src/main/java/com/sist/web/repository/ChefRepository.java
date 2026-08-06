package com.sist.web.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sist.web.entity.Chef;

@Repository
public interface ChefRepository extends JpaRepository<Chef, String> {
	
}
