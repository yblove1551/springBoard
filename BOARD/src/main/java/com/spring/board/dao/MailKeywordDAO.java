package com.spring.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.domain.MailKeyword;

@Repository
public class MailKeywordDAO {
	@Autowired
	private SqlSession session;
	private String namespace = "com.spring.board.dao.MailKeywordMapper.";

	public List<MailKeyword> selectAll() {
		return session.selectList(namespace + "selectAll"); 
	}
	
}
