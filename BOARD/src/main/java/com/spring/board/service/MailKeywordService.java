package com.spring.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.board.dao.MailKeywordDAO;
import com.spring.board.domain.MailKeyword;

@Service
public class MailKeywordService {
	MailKeywordDAO mailKeywordDAO; 
	
	public MailKeywordService(MailKeywordDAO mailKeywordDAO) {
		this.mailKeywordDAO = mailKeywordDAO;
	} 

	public List<MailKeyword> searchAll(){
		return mailKeywordDAO.selectAll();
	}

}
