package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.vo.DataBoardVO;

import lombok.RequiredArgsConstructor;

import java.util.*;

import com.sist.web.mapper.DataBoardMapper;
import com.sist.web.vo.*;
@Service
@RequiredArgsConstructor
public class DataBoardServiceImpl implements DataBoardService{
	private final DataBoardMapper mapper;
	
	@Override
	public List<DataBoardVO> dataBoardListData(int start) {
		
		return mapper.dataBoardListData(start);
	}

	@Override
	public int dataBoardTotalPage() {
		
		return mapper.dataBoardTotalPage();
	}

	@Override
	public void dataBoardInsert(DataBoardVO vo) {
		
		mapper.dataBoardInsert(vo);
	}

}
