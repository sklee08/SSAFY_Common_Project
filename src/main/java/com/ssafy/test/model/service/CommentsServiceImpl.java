package com.ssafy.test.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.test.model.dao.CommentsDAO;
import com.ssafy.test.model.dto.Comments;

@Service
public class CommentsServiceImpl implements CommentsService{


	@Autowired
	CommentsDAO Dao;

	@Override
	public List<Comments> selectAll() {

		return Dao.selectAll();
	}

	@Override
	public Comments select(int k) {
		return Dao.select(k);
	}

	@Override
	public int insert(Comments v) {
		return Dao.insert(v);
	}

	@Override
	public int delete(int k) {
		return Dao.delete(k);
	}

	@Override
	public int update(Comments v) {
		return Dao.update(v);
	}

	@Override
	public Comments selectedComments(int k) {
		return Dao.selectedComments(k);
	}

	@Override
	public List<Comments> searchById(String s) {
		return Dao.searchById(s);
	}

	@Override
	public List<Comments> searchSelectedComments(String s) {
		return Dao.searchSelectedComments(s);
	}

	@Override
	public int pick(int k) {
		// TODO Auto-generated method stub
		return Dao.pick(k);
	}
}
