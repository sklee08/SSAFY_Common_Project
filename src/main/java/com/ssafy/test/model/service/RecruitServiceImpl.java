package com.ssafy.test.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.test.model.dao.RecruitDAO;
import com.ssafy.test.model.dto.Addr;
import com.ssafy.test.model.dto.AddrAndTag;
import com.ssafy.test.model.dto.Recruit;
import com.ssafy.test.model.dto.RecruitPjt;
import com.ssafy.test.model.dto.RecruitPjtPinterest;
import com.ssafy.test.model.dto.SearchParameter;
import com.ssafy.test.model.dto.TagList;
import com.ssafy.test.model.dto.Two;

@Service
public class RecruitServiceImpl implements RecruitService {

	@Autowired
	RecruitDAO rDao;

	@Override
	public List<RecruitPjt> selectAll(Two<Integer,Integer> v) {
		return rDao.selectAll(v);
	}

	@Override
	public Recruit select(int rnum) {
		return rDao.select(rnum);
	}

	@Override
	public int insert(Recruit r) {
		return rDao.insert(r);
	}

	@Override
	public int delete(int rnum) {
		return rDao.delete(rnum);
	}

	@Override
	public int update(Recruit r) {
		return rDao.update(r);
	}

	@Override
	public List<Recruit> selectByAddr(Addr a) {
		return rDao.selectByAddr(a);
	}

	@Override
	public List<Recruit> selectSame(TagList tl) {
		return rDao.selectSame(tl);
	}

	@Override
	public List<Recruit> selectAddrAndTag(AddrAndTag aat) {
		return rDao.selectAddrAndTag(aat);
	}

	@Override
	public List<RecruitPjtPinterest> searchAll(SearchParameter sp) {
		return rDao.searchAll(sp);
	}

	@Override
	public List<RecruitPjtPinterest> selectAllRecruitPjtPinterest(Two<Integer,Integer> v) {
		return rDao.selectAllRecruitPjtPinterest(v);
	}

}
