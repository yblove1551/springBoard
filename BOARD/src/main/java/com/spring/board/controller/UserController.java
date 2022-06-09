package com.spring.board.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.board.domain.User;
import com.spring.board.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@InitBinder
	public void init(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
	}
		
	@GetMapping("/modify")
	public String modify(HttpSession session, HttpServletRequest request, Model m) {
		if (session.getAttribute("id") == null) //로그인 페이지로 이동
			return "redirect:/login/login";
	
		try {
			//조회결과가 없으면 유효한 아이디가 아님 홈으로 이동
			User user = userService.searchById((String)session.getAttribute("id")); 				
			if (user != null) {
				m.addAttribute(user);
				return "userForm";
			}
				
			m.addAttribute("msg", "퇼퇴한 회원입니다.");
			session.invalidate();
			return "/";
		} catch (Exception e) { //DB에러가 난 경우 에러페이지로 이동
			m.addAttribute("msg", "에러가 발생했습니다");
			return "/";
		}		
	}
	
	@GetMapping("/add")   public String	 	add(Model m) {return "userForm";}	
	@GetMapping("/find")  public String 	find() {return "findUser";}
	
	@GetMapping("/newpw") 
	public String newpw(@RequestParam String id, HttpSession session, Model m) {
		System.out.println(session.getAttribute("authedYN"));
		if (session.getAttribute("authedYN") == null) {
			m.addAttribute("msg", "인증되지 않은 접근입니다");
			return "index";
		}
		
		System.out.println(id);
		session.removeAttribute("authedYN");
		m.addAttribute("vid", id);
		return "newpw";
	}

	
	@PostMapping("/remove")
	@ResponseBody
	public ResponseEntity<String> remove(HttpSession session, String id) {		
		JSONObject obj = new JSONObject(); 
		obj.put("isSuccess", "N");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
		
		if (id == null 
				|| session.getAttribute("id") == null
				||!id.equals((String)session.getAttribute("id"))){
			obj.put("msg", "로그인이 필요합니다.");		
			return new ResponseEntity<String>(obj.toString(), responseHeaders, HttpStatus.BAD_REQUEST);		
		}
		
		try {
			if (userService.removeUser(id) > 0) {
				obj.put("isSuccess", "Y");	
				session.invalidate();
				return new ResponseEntity<String>(obj.toString(), responseHeaders, HttpStatus.OK);		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		obj.put("msg", "오류가 발생했습니다.");
		return new ResponseEntity<String>(obj.toString(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	//회원수정
	@PostMapping("/modify")
	public ResponseEntity<String> modify(@Valid @RequestBody User user, BindingResult result, Model m){
		JSONObject obj = new JSONObject(); 
		obj.put("isSuccess", "N");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
		
		//검증에러가 없다면
		if (!result.hasErrors()) {
			try {
				if (userService.modifyUser(user) > 0) {
					obj.put("isSuccess", "Y");
					return new ResponseEntity<String>(obj.toString(), responseHeaders, HttpStatus.OK);		
				}
				obj.put("msg", "수정에 실패했습니다.");								
			}catch (Exception e) {
				e.printStackTrace();	
				obj.put("msg", "에러가 발생했습니다.");
			}
			return new ResponseEntity<String>(obj.toString(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {		
			obj.put("msg", result.getAllErrors().get(0));			
			return new ResponseEntity<String>(obj.toString(), responseHeaders, HttpStatus.BAD_REQUEST);
		} 
	}
	
	//ajax 요청
	@ResponseBody
	@PostMapping("/add")
	public ResponseEntity<String> add(@Valid @RequestBody User user, BindingResult rslt, Model m, HttpSession session, HttpServletRequest request) throws Exception{
		JSONObject obj = new JSONObject(); 
		obj.put("isSuccess", "N");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
		//바인딩이나 검증에 에러가 있는 경우
		if (!rslt.hasErrors()) {
			try {
				if (userService.addUser(user) > 0) {
					session.setAttribute("id",user.getId()); //세션설정
					obj.put("isSuccess", "Y");
					return new ResponseEntity<String>(obj.toString(), responseHeaders, HttpStatus.OK);	
				}		
			} catch (Exception e) {
				e.printStackTrace(); 
			}
			obj.put("msg", "회원등록에 실패했습니다.");
			return new ResponseEntity<String>(obj.toString(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);	
		}else {
			obj.put("msg", rslt.getAllErrors().get(0).getDefaultMessage());
			return new ResponseEntity<String>(obj.toString(), responseHeaders, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/newpw")
	@ResponseBody
	public ResponseEntity<String> updatePwd(@RequestParam String id, @RequestParam String pw) {
		JSONObject json = new JSONObject();
		json.put("isSuccess", "N");
		try {
			if (userService.modifyPw(id, pw) >= 1) { 
				json.put("isSuccess", "Y");
				return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<String>(json.toString(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>(json.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
	@GetMapping("/id")
	@ResponseBody
	public ResponseEntity<String> searchById(@RequestParam String id) {
		JSONObject json = new JSONObject();
		json.put("isSuccess", "N");
		try {
			User user = userService.searchById(id);	
			
			if (user == null) {
				json.put("isSuccess", "Y");
			}
			
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(json.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}

	@GetMapping("/email")
	@ResponseBody
	public ResponseEntity<String> searchByEmail(@RequestParam String email) {
		JSONObject json = new JSONObject();
		json.put("isSuccess", "N");		
		try {
			User user = userService.searchByEmail(email);	
			
			if (user == null) 
				json.put("isSuccess", "Y");		
			
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(json.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@GetMapping("/checkEmail")
	@ResponseBody
	public ResponseEntity<String> checkEmail(String email, String name){
		JSONObject json = new JSONObject();
		json.put("isSuccess", "N");		
		
		try {
			User user = userService.searchByEmailAndName(email, name);
			if (user != null){
				json.put("isSuccess", "Y");	
				json.put("id", user.getId());
			}
			
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(json.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}	
}
