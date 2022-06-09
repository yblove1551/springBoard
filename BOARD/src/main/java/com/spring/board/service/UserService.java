package com.spring.board.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.board.dao.BoardDAO;
import com.spring.board.dao.ReplyDAO;
import com.spring.board.dao.UserDAO;
import com.spring.board.domain.User;

@Service
public class UserService { 
	private UserDAO userDAO;
	private BoardDAO boardDAO;
	private ReplyDAO replyDAO;
	
	public UserService(UserDAO userDAO, BoardDAO boardDAO, ReplyDAO replyDAO ) {
		this.userDAO = userDAO;
		this.boardDAO = boardDAO;
		this.replyDAO = replyDAO;
	}
		
	public User searchById(String id){
		return userDAO.selectById(id); 
	}
	
	public User searchByIdAndPwd(String id, String pwd){
		return userDAO.selectByIdAndPwd(id,pwd); 
	}
	
	public User searchByEmail(String email) {
		return userDAO.selectByEmail(email);
	}
	
	public User searchByEmailAndName(String email, String name) {
		return userDAO.selectByEmailAndName(email, name);	
	}
	
	//이부분만 테스트
	@Transactional(rollbackFor = Exception.class)
	public int removeUser(String id) throws Exception {
		replyDAO.deleteByReplyer(id);
		boardDAO.deleteByWriter(id);
		return userDAO.deleteById(id); 
	}
	
	public int modifyUser(User user){
		return userDAO.update(user);
	}
	
	public int modifyPw(String id, String pw){
		return userDAO.updatePwd(id, pw);		
	}
	
	public int addUser(User user){
		return userDAO.insert(user);
	}
	

	
}
