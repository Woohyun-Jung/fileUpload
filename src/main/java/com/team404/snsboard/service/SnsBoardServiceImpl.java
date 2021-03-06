package com.team404.snsboard.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team404.command.SnsBoardVO;
import com.team404.snsboard.mapper.SnsBoardMapper;

@Service("snsBoardService")
public class SnsBoardServiceImpl implements SnsBoardService {

	@Autowired
	private SnsBoardMapper snsBoardMapper;
	
	@Override
	public int insert(SnsBoardVO vo) {
		
		return snsBoardMapper.insert(vo);
	}

	@Override
	public ArrayList<SnsBoardVO> getList() {
		
		return snsBoardMapper.getList();
	}

}
