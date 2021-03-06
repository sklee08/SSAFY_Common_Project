package com.ssafy.test.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.test.model.dao.FollowingDao;
import com.ssafy.test.model.dto.Following;


@Service
public class FollowingServiceImpl implements FollowingService {

	@Autowired
	FollowingDao fDao;
	
	@Override
	public List<Following> selectAll() {
		// TODO Auto-generated method stub
		return fDao.selectAll();
	}

	@Override
	public int insert(Following f) {
		// TODO Auto-generated method stub
		return fDao.insert(f);
	}

	@Override
	public int delete(Following f) {
		// TODO Auto-generated method stub
		return fDao.delete(f);
	}

	@Override
	public int update(Following f) {
		// TODO Auto-generated method stub
		return fDao.update(f);
	}

	@Override
	public List<Following> selectById(String id) {
		// TODO Auto-generated method stub
		return fDao.selectById(id);
	}

	@Override
	public List<Following> selectByTarget(String target) {
		// TODO Auto-generated method stub
		return fDao.selectByTarget(target);
	}

	@Override
	public Following select(Following f) {
		// TODO Auto-generated method stub
		return fDao.select(f);
	}




}
