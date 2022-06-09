package com.spring.board.service;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.board.dao.UserDAO;
import com.spring.board.domain.User;


@Service
public class LoginService { 
	UserDAO userDAO;
	
	public LoginService(UserDAO userDAO){
		this.userDAO = userDAO;
	}

	public User login(String id, String pw) throws SQLException{
		return userDAO.selectByIdAndPwd(id,  pw);
	}	
}
