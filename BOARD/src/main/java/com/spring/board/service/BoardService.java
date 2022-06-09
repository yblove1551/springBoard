package com.spring.board.service;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.board.dao.BoardDAO;
import com.spring.board.dao.ReplyDAO;
import com.spring.board.dao.ReplyDAO;
import com.spring.board.domain.Board;
import com.spring.board.domain.BoardSelector;


@Service
public class BoardService { 
	final BoardDAO boardDAO;
	final ReplyDAO replyDAO;
	
	public int searchCnt(BoardSelector bs) throws SQLException{
		return boardDAO.selectCnt(bs);	
	}
	
	public BoardService(BoardDAO boardDAO, ReplyDAO replyDAO) {
		this.boardDAO = boardDAO;
		this.replyDAO = replyDAO;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Board search(int bno) throws SQLException{
		boardDAO.updateViewCntByBno(bno);
		return boardDAO.selectByBno(bno);
	}
	
	public Board searchNotIncViewCnt(int bno) throws SQLException{
		return boardDAO.selectByBno(bno);
	}
	
	public List<Board> searchList(BoardSelector selector) throws SQLException{
		return boardDAO.selectList(selector);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int removeByWriterAndBno(String writer, int bno) throws SQLException{
		replyDAO.deleteByBno(bno);
		return  boardDAO.deleteByWriterAndBno(writer, bno);
	}
	
	public int modify(Board board) throws SQLException {
		return boardDAO.update(board);
	}
	
	public int modifyViewCnt(int bno) throws SQLException{
		return boardDAO.updateViewCntByBno(bno);
	}

	public int add(Board board) throws SQLException {
		return boardDAO.insert(board);
	}
	
	
	
}
