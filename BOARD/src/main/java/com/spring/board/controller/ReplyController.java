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
		JSONObject json = new JSONObject();
		json.put("isSuccess", "N");	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=UTF-8");
		
		if (reply.getBno() == null) {
			json.put("msg", "????????? ???????????????");
			return new ResponseEntity<>(json.toString(), headers, HttpStatus.BAD_REQUEST);	
		}
	
		if (boardService.searchNotIncViewCnt(reply.getBno()) == null) {
			json.put("msg", "????????? ????????? ??????????????????.");
			return new ResponseEntity<>(json.toString(), headers, HttpStatus.BAD_REQUEST);	
		}
		
		reply.setReplyer((String)session.getAttribute("id"));
		
		if (replyService.add(reply) <= 0){
			json.put("msg", "?????? ????????? ????????? ??????????????????.");
			return new ResponseEntity<>(json.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		json.put("isSuccess", "Y");
		return new ResponseEntity<>(json.toString(), headers, HttpStatus.OK);		
	} 
	
	@PostMapping("/modify")
	public ResponseEntity<String> modify(@RequestBody Reply reply, HttpSession session) throws Exception{
		JSONObject json = new JSONObject();
		json.put("isSuccess", "N");	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=UTF-8");	
		
		System.out.println(reply);
		if (reply.getBno() == null) {
			json.put("msg", "????????? ???????????????");
			return new ResponseEntity<>(json.toString(), headers, HttpStatus.BAD_REQUEST);	
		}
		
		if (replyService.modify(reply) <= 0){
			json.put("msg", "??????????????? ?????? ??????????????????.");
			return new ResponseEntity<>(json.toString(), headers, HttpStatus.BAD_REQUEST);
		}
	
		json.put("isSuccess", "Y");	
		return new ResponseEntity<>(json.toString(), headers, HttpStatus.OK);		
	} 
	
	@PostMapping("/remove")
	public ResponseEntity<String> remove(@RequestParam Integer rno, HttpSession session) throws Exception{
		JSONObject json = new JSONObject();
		json.put("isSuccess", "N");	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=UTF-8");
		
		String sessionId = (String)session.getAttribute("id"); 

		Reply reply = replyService.searchByRno(rno);
		if (reply == null) {
			json.put("msg", "??????????????? ?????? ??????????????????.");
			return new ResponseEntity<>(json.toString(), headers, HttpStatus.BAD_REQUEST);				
		}
		
		if (!reply.getReplyer().equals(sessionId)){
			json.put("msg", "????????? ????????????.");
			return new ResponseEntity<>(json.toString(), headers, HttpStatus.BAD_REQUEST);			
		}
		
		if (replyService.removeByRnoAndReplyer(rno, sessionId) <= 0){
			json.put("msg", "????????? ????????? ??????????????????");
			return new ResponseEntity<>(json.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
	
		json.put("isSuccess", "Y");
		return new ResponseEntity<>(json.toString(), headers, HttpStatus.OK);
		
	} 

}
