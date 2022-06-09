package com.spring.board.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.domain.MailTemplete;

@Repository
public class MailTempleteDAO {
	@Autowired
	private SqlSession session;
	private String namespace = "com.spring.board.dao.MailTempleteMapper.";
		
	public List<MailTemplete> selectAll() throws SQLException{
		return session.selectList(namespace + "selectAll");
	}
	
	public MailTemplete selectByKind(int kind) throws SQLException{
		return session.selectOne(namespace + "selectByKind", kind);
	}	

}
