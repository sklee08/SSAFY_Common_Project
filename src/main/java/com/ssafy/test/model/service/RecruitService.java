package com.ssafy.test.model.service;

import java.util.List;

import com.ssafy.test.model.dto.Addr;
import com.ssafy.test.model.dto.AddrAndTag;
import com.ssafy.test.model.dto.Recruit;
import com.ssafy.test.model.dto.TagList;

public interface RecruitService {

	public List<Recruit> selectAll();

	public Recruit select(int rnum);

	public int insert(Recruit r);

	public int delete(int rnum);

	public int update(Recruit r);
	
	public List<Recruit> selectByAddr(Addr a);
	
	public List<Recruit> selectSame(TagList tl);
	
	public List<Recruit> selectAddrAndTag(AddrAndTag aat);
}
