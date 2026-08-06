package com.sist.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Recipe {
	@Id
	private int no;
	private String title,poster,chef,link;
	private int hit;
}
