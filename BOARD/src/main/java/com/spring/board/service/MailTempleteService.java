package com.spring.board.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.board.dao.MailTempleteDAO;
import com.spring.board.domain.MailTemplete;

@Service
public class MailTempleteService {
	MailTempleteDAO mailTempleteDAO; 
	
	MailTempleteService(MailTempleteDAO mailTempleteDAO){
		this.mailTempleteDAO = mailTempleteDAO;
	}

	public MailTemplete selectByKind(int kind) throws SQLException{
		return mailTempleteDAO.selectByKind(kind);
	}
		
	public List<MailTemplete> selectAll() throws SQLException{
		return mailTempleteDAO.selectAll();
	}
}
