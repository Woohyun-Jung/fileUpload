package com.team404.reply.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.team404.command.FreeReplyVO;
import com.team404.util.Criteria;

public interface ReplyMapper {

	public int regist(FreeReplyVO vo);
//	public ArrayList<FreeReplyVO> getList(int bno);
	public ArrayList<FreeReplyVO> getList(@Param("bno") int bno,@Param("cri") Criteria cri);
	public int getTotal(int bno);
	public int pwCheck(FreeReplyVO vo);
	public int update(FreeReplyVO vo);
	public int delete(FreeReplyVO vo);
	
}
