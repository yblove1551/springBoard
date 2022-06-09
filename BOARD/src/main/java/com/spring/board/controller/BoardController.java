package com.spring.board.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.spring.board.domain.Board;
import com.spring.board.domain.BoardSelector;
import com.spring.board.service.BoardService;
	
@Controller
@RequestMapping("/board")
public class BoardController {
	final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@GetMapping("/add")
	public String add() {
		return "board";
	}
	
	@GetMapping("/list")
	public String getBoardList(HttpServletRequest request, HttpSession session, BoardSelector boardSelector, Model m, String msg) {
		List<Board> boardList = null;
	
		Map<String, ?> flashMap =RequestContextUtils.getInputFlashMap(request);
		msg = flashMap != null ?  (String)flashMap.get("msg") : "";
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date today = cal.getTime();
				
		try {
			boardSelector.setTotalCnt(boardService.searchCnt(boardSelector)); //검색조건으로 총 게시글 수 조회
			boardSelector.calcPageInfo(); // 페이징
			boardList = boardService.searchList(boardSelector);					
		} catch (Exception e) {
			e.printStackTrace();
		}

		m.addAttribute("boardList", boardList);
		m.addAttribute("today", today);
		m.addAttribute("msg", msg);
		return "boardList";
	}
	
	@GetMapping("/board")
	public String getBoard(@RequestParam Integer bno, BoardSelector boardSelector, RedirectAttributes rattr,   Model m) {
		try {
			Board board = boardService.search(bno);	
			if (board == null) {
				rattr.addFlashAttribute("msg", "없거나 삭제된 게시물입니다");
				return "redirect:/board/list";
			}
			m.addAttribute("board", board); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "board";
	}
	
	@PostMapping("/modify")
	@ResponseBody
	public ResponseEntity<String> modify(@RequestBody Board board, BoardSelector boardSelector,  HttpSession session) {
		JSONObject obj = new JSONObject();
		obj.put("isSuccess", "N");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=UTF-8");
		
		if (session.getAttribute("id") == null) {
			obj.put("msg", "세션이 유효하지 않습니다");
			return new ResponseEntity<>(obj.toString(), headers, HttpStatus.BAD_REQUEST); 
		}
		
		if (board.getBno() == null) {
			obj.put("msg", "잘못된 요청입니다");
			return new ResponseEntity<>(obj.toString(), headers, HttpStatus.BAD_REQUEST); 
		}
			
		String id = (String)session.getAttribute("id");
		board.setWriter(id);
		if (!id.equals(board.getWriter())) {
			obj.put("msg", "권한이 없습니다.");
			return new ResponseEntity<>(obj.toString(), headers, HttpStatus.BAD_REQUEST); 			
		}
		
		try {
			if (boardService.modify(board) == 1) {
				obj.put("isSuccess", "Y");
				return new ResponseEntity<>(obj.toString(), headers, HttpStatus.OK); 					
			}
			obj.put("msg", "삭제된 게시물입니다");
			return new ResponseEntity<>(obj.toString(), headers, HttpStatus.BAD_REQUEST); 	
		} catch (Exception e) {
			obj.put("msg", "데이터베이스 에러가 발생했습니다");
			return new ResponseEntity<>(obj.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR); 	
		}			
	}
	
	@PostMapping("/remove")
	public ResponseEntity<String> remove(@RequestParam Integer bno, HttpSession session) {
		JSONObject obj = new JSONObject();
		obj.put("isSuccess", "N");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=UTF-8");
		
		String id = (String)session.getAttribute("id");
		try {
			if (boardService.removeByWriterAndBno(id, bno) > 0) {
				obj.put("isSuccess", "Y");
				return new ResponseEntity<>(obj.toString(), headers, HttpStatus.OK); 	
			};	
			obj.put("msg", "삭제에 실패했습니다");
			return new ResponseEntity<>(obj.toString(), headers, HttpStatus.BAD_REQUEST); 
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("msg", "데이터베이스 에러가 발생했습니다");
			return new ResponseEntity<>(obj.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR); 
		}		
	}
	
	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<String> add(@RequestBody Board board, HttpSession session) {
		JSONObject obj = new JSONObject();
		obj.put("isSuccess", "N");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=UTF-8");
		
		String id = (String)session.getAttribute("id");
		
		board.setWriter(id); 
		try {
			if (boardService.add(board) > 0) {
				obj.put("isSuccess", "Y");
				return new ResponseEntity<>(obj.toString(), headers, HttpStatus.OK); 
			} 	
			obj.put("msg", "삭제에 실패했습니다");
			return new ResponseEntity<>(obj.toString(), headers, HttpStatus.BAD_REQUEST); 
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("msg", "데이터베이스 에러가 발생했습니다");
			return new ResponseEntity<>(obj.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}	
}
