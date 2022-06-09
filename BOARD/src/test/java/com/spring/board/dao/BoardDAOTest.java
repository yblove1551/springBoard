package com.spring.board.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.board.domain.Board;
import com.spring.board.domain.Reply;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardDAOTest {
	@Autowired
	private ReplyDAO replyDAO;	
	@Autowired
	private BoardDAO boardDAO;	

	@Test
	public void insert() throws Exception{
		Board board = new Board();
	
		for (int i=1; i<150; i++) {
			board.setContent("내용" + i);
			board.setTitle("제목" + i);
			board.setWriter("test123");
			boardDAO.insert(board);		
			System.out.println("test");
		}

	}
	
	@Test
	@Ignore
	public void select() throws Exception{
		List<Reply> list2 = replyDAO.selectByBnoHierarchical(0);
		assertTrue(list2.size() == 0);
		
		list2 = replyDAO.selectByBnoHierarchical(7);
		assertTrue(list2.size() == 2);
		
		for (Reply reply : list2)
			System.out.println(reply);
		
		
		
	}	
	
	@Test
	@Ignore
	
	public void deleteTest() throws Exception{
//		assertTrue(replyDAO.deleteByRnoAndReplyer(-1, "test123") == 0);
//		assertTrue(replyDAO.deleteByRnoAndReplyer(24, "test321") == 1);
//		assertTrue(replyDAO.deleteByReplyer("test444")  == 5);
		assertTrue(replyDAO.deleteByBno(7) > 0);
		assertTrue(replyDAO.selectByBnoHierarchical(7).size() == 0);


		
		
		
	}

}
