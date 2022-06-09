package com.spring.board.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.board.domain.Board;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardServiceTest {
	@Autowired
	BoardService bs;
	
	@Test
	public void test() throws Exception {
		Board board = new Board();
		board.setWriter("test123");
		board.setContent("1235");
		board.setTitle("1234");
		
		board = bs.search(7);
		System.out.println(board);
		assertTrue(board != null);
		//assertTrue(bs.add(board) > 0);
	}
}
