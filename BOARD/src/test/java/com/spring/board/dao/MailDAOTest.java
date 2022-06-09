package com.spring.board.dao;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.board.domain.Mail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class MailDAOTest {
	@Autowired
	MailDAO mailDAO;
	@Test
	public void insert() {
		Mail mail = new Mail();
		mail.setContent("메일내용");
		mail.setTitle("메일 제목");
		mail.setReceiver("수신자");
		mail.setReceiver_email("수신자@naver.com");
		mail.setSender("발신자");
		mail.setSender_email("발신자@naver.com");
		mail.setSuccess("Y");
		mail.setKind(1);
		mail.setSend_time(new Date());
		mail.setErr_msg("에러메시지");
		try {
			assertTrue(mailDAO.insert(mail) == 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
