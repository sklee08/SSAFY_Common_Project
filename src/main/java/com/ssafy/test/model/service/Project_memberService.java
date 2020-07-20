package com.ssafy.test.model.service;

import java.util.List;

import com.ssafy.test.model.dto.Project_member;

public interface Project_memberService {
	
	public List<Project_member> selectAll();

	public Project_member select(Project_member pm);

	public int insert(Project_member pm);

	public int delete(Project_member pm);

	public int update(Project_member pm);
}
