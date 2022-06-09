package com.spring.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.board.domain.Reply;
import com.spring.board.service.BoardService;
import com.spring.board.service.ReplyService;

@Controller
@RequestMapping("/reply")
public class ReplyController{
	final ReplyService replyService;
	final BoardService boardService;
	public ReplyController(ReplyService replyService, BoardService boardService) {
		this.replyService = replyService;
		this.boardService = boardService;
	} 
	
	@GetMapping("/reply")
	@ResponseBody
	public ResponseEntity<String> getReply(@RequestParam Integer bno) throws Exception{		
		if (boardService.searchNotIncViewCnt(bno) == null)
			throw new Exception("board not found");
		
		List<Reply> replyList = replyService.searchByBnoHierarchical(bno);
		
		System.out.println(replyList);
		
		JSONObject json = new JSONObject();
		json.put("isSuccess", "Y");
		JSONArray jsonArray = new JSONArray();
		for (Reply reply : replyList) 
			jsonArray.add(reply.toJSONObj());		
		json.put("result", jsonArray);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=UTF-8");
		return new ResponseEntity<>(json.toString(), headers, HttpStatus.OK);
	}	
	
	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<String> add(@RequestBody Reply reply, HttpSession session) throws Exception{
		String sessionId = (String)session.getAttribute("id"); 
		if (sessionId == null) 
			throw new Exception("invalid Session");		
		
		if (reply.getBno() == null)
			throw new Exception("invalid value");
	
		if (boardService.searchNotIncViewCnt(reply.getBno()) == null)
			throw new Exception("board not found");
		
		reply.setReplyer(sessionId);
		
		if (replyService.add(reply) <= 0){
			throw new Exception("reply add error");
		}
		JSONObject json = new JSONObject();
		json.put("isSuccess", "Y");	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=UTF-8");
		return new ResponseEntity<>(json.toString(), headers, HttpStatus.OK);		
	} 
	
	@PostMapping("/modify")
	public ResponseEntity<String> modify(@RequestBody Reply reply, HttpSession session) throws Exception{
	System.out.println(reply);
		String sessionId = (String)session.getAttribute("id"); 
		if (sessionId == null) 
			throw new Exception("invalid session");	
		
		if (reply.getRno() == null)
			throw new Exception("invalid value");
		
		if (replyService.searchByRno(reply.getRno()) == null)
			throw new Exception("reply not found");
		
		if (replyService.modify(reply) <= 0){
			throw new Exception("reply modify error");
		}
	
		JSONObject json = new JSONObject();
		json.put("isSuccess", "Y");	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=UTF-8");
		return new ResponseEntity<>(json.toString(), headers, HttpStatus.OK);		
	} 
	
	@PostMapping("/remove")
	public ResponseEntity<String> remove(@RequestParam Integer rno, HttpSession session) throws Exception{
		String sessionId = (String)session.getAttribute("id"); 
		if (sessionId == null) 
			throw new Exception("invalid Session");	

		Reply reply = replyService.searchByRno(rno);
		
		if (reply == null) 
			throw new Exception("not found reply");
		else if (!reply.getReplyer().equals(sessionId))
			throw new Exception("not authorized");
		
		if (replyService.removeByRnoAndReplyer(rno, sessionId) <= 0){
			throw new Exception("reply remove error");
		}
	
		JSONObject json = new JSONObject();
		json.put("isSuccess", "Y");	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=UTF-8");
		return new ResponseEntity<>(json.toString(), headers, HttpStatus.OK);		
	} 

}
