package com.spring.board.dao;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.domain.Board;
import com.spring.board.domain.BoardSelector;

@Repository
public class BoardDAO {
	@Autowired
	SqlSession session;

	private static String namespace = "com.spring.board.dao.BoardMapper.";

	//게시글 수
	public int selectCnt(BoardSelector bs) throws SQLException{
		return session.selectOne(namespace + "selectCnt", bs);
	}
	
	//게시글 조회
	public Board selectByBno(int bno) throws SQLException{
		return session.selectOne(namespace + "selectByBno", bno);	
	}
	
	//게시글 리스트 조회
	public List<Board> selectList(BoardSelector selector) throws SQLException{
		return session.selectList(namespace + "selectList", selector);	
	}	
	
	//게시글 삭제
	public int deleteByWriterAndBno(String writer, int bno) throws SQLException{
		Map map = new HashMap();
		map.put("writer", writer);
		map.put("bno", bno);
		return session.delete(namespace + "deleteByBnoAndWriter", map);	
	}
	
	public int deleteByWriter(String writer) throws SQLException{
		return session.delete(namespace + "deleteByWriter", writer);	
	}
	
	//수정
	public int update(Board board) throws SQLException {
		return session.update(namespace + "update", board);	
	}	
	//조회수 증가
	public int updateViewCntByBno(int bno) throws SQLException{
		return session.update(namespace + "updateViewCntByBno", bno);	
	}
	
	//게시글 추가
	public int insert(Board board) throws SQLException {
		return session.insert(namespace + "insert", board);	
	}
}
