package com.spring.board.dao;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.domain.Reply;

@Repository
public class ReplyDAO {
	@Autowired
	private SqlSession session;
	private String namespace = "com.spring.board.dao.ReplyMapper.";
	
	
	//조회
	public List<Reply> selectByBnoHierarchical(int bno) throws SQLException{
		return session.selectList(namespace + "selectByBnoHierarchical" ,bno);
	}	
	
	public Reply selectByRno(int rno) throws Exception{
		return session.selectOne(namespace + "selectByRno", rno);
	}
	
	
	//계층형삭제
	public int deleteByRnoAndReplyer(int rno, String replyer) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		map.put("rno", rno);
		map.put("replyer", replyer);
		return session.delete(namespace + "deleteByRnoAndReplyer", map);	
	}
	
	//
	public int deleteByReplyer(String replyer) throws SQLException{
		return session.delete(namespace + "deleteByReplyer", replyer);	
	}
	
	public int deleteByBno(int bno) throws SQLException{
		return session.delete(namespace + "deleteByBno", bno);			
	}
	
//수정
	public int update(Reply reply) throws SQLException {
		return session.update(namespace + "update", reply);		
	}
	
	
//	추가
	public int insert(Reply reply) throws SQLException {
		return session.insert(namespace + "insert", reply);		
	}	
}
