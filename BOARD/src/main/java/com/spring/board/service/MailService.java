package com.spring.board.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.board.dao.MailDAO;
import com.spring.board.domain.Mail;

@Service
public class MailService {
	MailDAO mailDAO;
	
	public MailService(MailDAO mailDAO) {
		this.mailDAO = mailDAO;
	}

	public int insertMailLog(Mail mail) throws SQLException {
		return mailDAO.insert(mail);
	}
}
