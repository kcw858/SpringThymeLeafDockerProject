package com.sist.web.service;

import java.util.List;

import com.sist.web.vo.DataBoardVO;

public interface DataBoardService {
	public List<DataBoardVO> dataBoardListData(int start);
	
	public int dataBoardTotalPage();
	
	public void dataBoardInsert(DataBoardVO vo);
}
