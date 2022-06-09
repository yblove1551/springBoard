package com.spring.board.controller;


import static com.spring.board.common.CommonResource.getStringFromRequestBody;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.board.domain.User;
import com.spring.board.service.LoginService;


@Controller
@RequestMapping("/login")
public class LoginController {
	final LoginService loginService;
	
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}
	
	@GetMapping("/login")
	public String login() {
		return "loginForm";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession sesison) {
		sesison.invalidate();
		return "redirect:/"; //홈으로 이동
	}
	
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<String> login(HttpServletRequest request, HttpSession session, HttpServletResponse response){							
		String jsonString = getStringFromRequestBody(request);
		Map<String, String> paramMap = null;
		try {
			JSONObject paramJson = (JSONObject)(new JSONParser().parse(jsonString));
			paramMap = new HashMap<>(paramJson);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String id = paramMap.get("id");
		String pwd = paramMap.get("pwd");
		String rememberId = paramMap.get("rememberId");
		
		User user = null;
		JSONObject obj = new JSONObject();
		obj.put("isSuccess", "N");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=UTF-8");
		try {
			user = loginService.login(id, pwd);
			if (user != null) {
				session.setAttribute("id", user.getId());
				obj.put("isSuccess", "Y");	
				Cookie cookie = new Cookie("id", user.getId());
				
				if (rememberId == null || !rememberId.equals("true")) 
					cookie.setMaxAge(0);
					
				response.addCookie(cookie);
				return new ResponseEntity<String>(obj.toString(), headers, HttpStatus.OK);
			}
			JSONObject sub = new JSONObject();
			sub.put("errCode", "noSearchUser");
			sub.put("errMsg", "아이디 또는 비밀번호가 틀렸습니다");
			obj.put("result", sub);		
			return new ResponseEntity<String>(obj.toString(), headers, HttpStatus.BAD_REQUEST);
				
		}catch (Exception e) {
			JSONObject sub = new JSONObject();
			sub.put("errCode", "dbError");
			sub.put("errMsg", "데이터베이스 에러가 발생했습니다");
			obj.put("result", sub);
			return new ResponseEntity<String>(obj.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
