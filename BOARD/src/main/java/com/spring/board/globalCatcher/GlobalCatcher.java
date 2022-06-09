package com.spring.board.globalCatcher;

import java.sql.SQLException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalCatcher {
	@ExceptionHandler(SQLException.class)
	public String catcher(Exception e, Model m) {
		m.addAttribute("msg", "데이터베이스 에러가 발생했습니다");
		return "error";
	}
}
