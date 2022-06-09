package com.spring.board.service;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.board.dao.ReplyDAO;
import com.spring.board.domain.Reply;


@Service
public class ReplyService { 
	ReplyDAO replyDAO;
	
	public ReplyService(ReplyDAO replyDAO) {
		this.replyDAO = replyDAO;
	} 

	public List<Reply> searchByBnoHierarchical(int bno) throws SQLException{
		return replyDAO.selectByBnoHierarchical(bno);
	}
	
	public Reply searchByRno(int rno) throws Exception{
		return replyDAO.selectByRno(rno);
	}
	
	public int removeByRnoAndReplyer(int rno, String replyer) throws SQLException {
		return replyDAO.deleteByRnoAndReplyer(rno, replyer);
	}
	
	public int modify(Reply reply) throws SQLException {
		return replyDAO.update(reply);
	}
	
	public int add(Reply reply) throws SQLException {
		return replyDAO.insert(reply);
	}
}
