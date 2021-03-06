package com.ssafy.test.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.test.model.dto.AddrAndTag;
import com.ssafy.test.model.dto.Interest;
import com.ssafy.test.model.dto.TagList;
import com.ssafy.test.model.dto.TagResult;
import com.ssafy.test.model.dto.UserInfo;
import com.ssafy.test.model.dto.Usertag;

@Repository
public class UsertagDAOImpl implements UsertagDAO{

	private static final String ns = "com.ssafy.test.Usertag.";

	@Autowired
	SqlSessionTemplate template;
	

	@Override
	public List<Interest> selectAll(String k) {
		return template.selectList(ns + "selectAll",k);
	}

	@Override
	public Interest select(Interest v) {
		return template.selectOne(ns + "select", v);
	}

	@Override
	public int insert(Interest v) {
		return template.insert(ns + "insert", v);
	}

	@Override
	public int delete(Interest v) {
		return template.delete(ns + "delete", v);
	}

	@Override
	public List<UserInfo> selectSame(TagList v) {
		return template.selectList(ns + "selectSame",v);
	}

	@Override
	public List<UserInfo> selectSimilar(TagList v) {
		return template.selectList(ns + "selectSimilar",v);
	}

	@Override
	public List<UserInfo> selectAddrAndTag(AddrAndTag v) {
		return template.selectList(ns + "selectAddrAndTag",v);
	}

}
