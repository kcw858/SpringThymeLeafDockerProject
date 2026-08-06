package com.sist.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
public class Chef{
	@Id
	private String chef;
	private String poster;
	private String mem_cont1,mem_cont3,mem_cont7,mem_cont2;
}
