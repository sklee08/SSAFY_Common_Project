package com.ssafy.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.test.model.dto.Pinterest;
import com.ssafy.test.model.dto.Pmember;
import com.ssafy.test.model.dto.Project;
import com.ssafy.test.model.dto.Projectcnt;
import com.ssafy.test.model.service.PinterestService;
import com.ssafy.test.model.service.PmemberService;
import com.ssafy.test.model.service.ProjectService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/api/project")
public class ProjectController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	
	@Autowired
	private ProjectService pService;
	
	@Autowired
	private PmemberService pmService;

	@Autowired
	private PinterestService Service;

	@ApiOperation(value = "모든 프로젝트의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<Project>> retrieveBoard() throws Exception {
		return new ResponseEntity<List<Project>>(pService.selectAll(), HttpStatus.OK);
	}

	@ApiOperation(value = "글번호에 해당하는 프로젝트의 정보를 반환한다.", response = Project.class)
	@GetMapping("{pid}")
	public ResponseEntity<Project> detailBoard(@PathVariable int pid) {
		return new ResponseEntity<Project>(pService.select(pid), HttpStatus.OK);
	}
	
	@ApiOperation(value = "UserId에 해당하는 프로젝트의 정보를 반환한다.", response = Project.class)
	@GetMapping("/searchByUserId/{userId}")
	public ResponseEntity<List<Projectcnt>> searchByUserId(@PathVariable String userId) throws Exception {
		
		List<Projectcnt> v = pService.searchByUserId(userId);
		
		for(int i = 0; i < v.size(); i++) {
			List<Pinterest> v2 = Service.select(v.get(i).getPid());
			int size = v2.size();
			if(size > 0) v.get(i).setTag1(v2.get(0).getInterest());
			if(size > 1) v.get(i).setTag2(v2.get(1).getInterest());
			if(size > 2) v.get(i).setTag3(v2.get(2).getInterest());
			if(size > 3) v.get(i).setTag4(v2.get(3).getInterest());
			if(size > 4) v.get(i).setTag5(v2.get(4).getInterest());
		}
		
		//<List<Pinterest>>(Service.select(pid)
		return new ResponseEntity<List<Projectcnt>>(v, HttpStatus.OK);
	}

	@ApiOperation(value = "새로운 프로젝트 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<Integer> writeBoard(@RequestBody Project p) {
		if (pService.insert(p) != 0) {
			Project tmp = pService.searchByPJT(p);
			int pid = tmp.getPid();
			Pmember pm = new Pmember(pid, tmp.getMakeId(), tmp.getPjtState(), 1);
			pmService.insert(pm);
			return new ResponseEntity<Integer>(pid, HttpStatus.OK);
		}
		return new ResponseEntity<Integer>(-1, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "글번호에 해당하는 프로젝트의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("{pid}")
	public ResponseEntity<String> updateBoard(@RequestBody Project p) {

		if (pService.update(p) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "글번호에 해당하는 프로젝트의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("{pid}")
	public ResponseEntity<String> deleteBoard(@PathVariable int pid) {

		if (pService.delete(pid) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	private ResponseEntity<Map<String, Object>> handleSuccess(Object data) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("status", true);
		resultMap.put("data", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> handleException(Exception e) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("status", false);
		resultMap.put("data", e.getMessage());
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
