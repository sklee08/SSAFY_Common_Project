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

import com.ssafy.test.model.dto.Chatroom;
import com.ssafy.test.model.service.ChatroomService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/api/chatroom")
public class ChatroomController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private ChatroomService Service;

	@ApiOperation(value = "특정 유저가 속한 모든 roomName을 반환한다", response = List.class)
	@GetMapping("chat/name={uid}")
	public ResponseEntity<List<Chatroom>> retrieveChatroom(@PathVariable String uid) throws Exception {
		return new ResponseEntity<List<Chatroom>>(Service.selectAll(uid), HttpStatus.OK);
	}
	

	@ApiOperation(value = "특정 roomName의 모든 멤버 정보를 반환한다", response = List.class)
	@GetMapping("chat/roomname={roomName}")
	public ResponseEntity<List<Chatroom>> selectMember(@PathVariable String roomName) throws Exception {
		return new ResponseEntity<List<Chatroom>>(Service.selectMember(roomName), HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "새로운 채팅방 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeBoard(@RequestBody Chatroom v) {
		if (Service.insert(v) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "특정 유저가 특정 roomName을 가진 채팅방에서 퇴장한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("delete/roomname={roomName}&uid={uid}")
	public ResponseEntity<String> deleteBoard(@PathVariable String roomName, @PathVariable String uid) {

		Chatroom v = new Chatroom();
		v.setRoomName(roomName);
		v.setUid(uid);
		
		if (Service.delete(v) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	

	@ApiOperation(value = "정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("change/{roomName}&{uid}")
	public ResponseEntity<String> updateBoard(@PathVariable String roomName, @PathVariable String uid) {

		Chatroom v = new Chatroom();
		v.setRoomName(roomName);
		v.setUid(uid);
		
		if (Service.update(v) != 0) {
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