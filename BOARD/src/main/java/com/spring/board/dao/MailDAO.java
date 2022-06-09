package com.spring.board.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.domain.Mail;

@Repository
public class MailDAO {
	@Autowired
	private SqlSession session;
	private String namespace = "com.spring.board.dao.MailMapper.";

	public int insert(Mail mail) throws SQLException {
		return session.insert(namespace + "insert", mail); 
	
	}

	public List<Mail> selectBySender(String sender) throws SQLException {
		return session.selectList(namespace + "selectBySender", sender); 
	}
	
	public List<Mail> selectByReceiver(String receiver) throws SQLException {
		return session.selectList(namespace + "selectByReceiver", receiver); 
	}
	
	public List<Mail> selectBySenderEmail(String sender_email) throws SQLException{
		return session.selectList(namespace + "selectBySenderEmail", sender_email); 
	}

	public List<Mail> selectByReceiverEmail(String receiver_email) throws SQLException{
		return session.selectList(namespace + "selectByReceiverEmail", receiver_email); 
		
	}
	
	public int deleteAll() throws SQLException{
		return session.delete(namespace + "deleteAll");
	}
	
	public int deleteByseqList(List<Integer> list) throws SQLException{
		return session.delete(namespace + "deleteByRnoList",list); 
	} 
}
