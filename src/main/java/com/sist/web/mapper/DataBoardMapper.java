package com.sist.web.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import java.util.*;
import com.sist.web.vo.*;
@Mapper
@Repository
public interface DataBoardMapper {
	/*
	 * @Query("SELECT no,name,subject,TO_CHAR(regdate,'yyyy-mm-dd') as dbday,hit,filecount "
			+ "FROM springdataboard "
			+ "ORDER BY NO DESC "
			+ "OFFSET :start ROWS FETCH NEXT 10 ROWS ONLY")
	 *  JPA => public Page<DataBoardVO> findAll(Pageable pg)
	 *  => 무조건 * 전체를 가져오기 때문에 @Getter를 가진 VO를 만들어야한다
	 */
	@Select("SELECT no,name,subject,TO_CHAR(regdate,'yyyy-mm-dd') as dbday,hit,filecount "
			+ "FROM springdataboard "
			+ "ORDER BY NO DESC "
			+ "OFFSET #{start} ROWS FETCH NEXT 10 ROWS ONLY")
	public List<DataBoardVO> dataBoardListData(int start);
	
	@Select("SELECT CEIL(COUNT(*)/10.0) FROM springdataboard")
	public int dataBoardTotalPage();
	
	@SelectKey(keyProperty = "no", 
			resultType = int.class, 
			before = true,
			statement = "SELECT NVL(MAX(no)+1,1) as no FROM springdataboard")
	@Insert("INSERT INTO springdataboard VALUES(#{no},#{name},#{subject},"
			+ "#{content},#{pwd},SYSDATE,0,#{filename},#{filesize},#{filecount})")
	public void dataBoardInsert(DataBoardVO vo);
	
	//상세보기 , 수정 , 삭제
}
