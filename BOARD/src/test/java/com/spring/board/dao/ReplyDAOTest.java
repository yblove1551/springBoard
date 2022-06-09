package com.spring.board.dao;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.board.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class ReplyDAOTest {
    @Autowired
    private UserDAO userDAO;
    
    @Test
    @Ignore("검증")
    public void insertTest() throws Exception {
    	User user = new User();
    	user.setBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1995-10-23"));
    	user.setEmail("dydqja3911@nate.com");
    	user.setName("정용범");
    	user.setPwd("test123");
    	user.setId("test123");
    	user.setPhone("919-111-1111");
    
    	assertTrue(userDAO.insert(user) == 1); 	
    }
    
    @Test
    @Ignore("검증")
    public void select() {
    	String id = "test123";
    	String pwd = "test123";
    	String email = "dydqja3911@nate.com";
    	String name = "정용범";
    	
    	assertTrue(userDAO.selectById(id) != null); 	
    	assertTrue(userDAO.selectByEmail(email) != null); 
    	assertTrue(userDAO.selectByEmailAndName(email, name) != null); 
    	assertTrue(userDAO.selectByIdAndPwd(id, pwd) != null); 
 
    	assertTrue(userDAO.selectById("zzz") == null); 	
    	assertTrue(userDAO.selectByEmail(null) == null); 
    	assertTrue(userDAO.selectByEmailAndName(null, null) == null); 
    	assertTrue(userDAO.selectByIdAndPwd(null, null) == null); 	
    }
    
    @Test
    @Ignore("검증")
    public void delete() {
    	String id = "test123";	
    	assertTrue(userDAO.deleteById(id) == 1); 
    	assertTrue(userDAO.deleteById(id) == 0); 
    }
    
    @Test
    public void update() throws Exception{
    	String test = "test123";
    	userDAO.deleteById(test); 
    	//userDAO.deleteById("test123");
    	
    	User user = new User();
    	user.setBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1995-10-23"));
    	user.setEmail("dydqja3911@nate.com");
    	user.setName("정용범");
    	user.setPwd("test123");
    	user.setId("test123");
    	user.setPhone("919-111-1111");
    
    	assertTrue(userDAO.insert(user) == 1); 
    	assertTrue(userDAO.updatePwd(user.getId(), "test124") == 1); 
    	user =  userDAO.selectById(user.getId());
    	assertTrue(user.getPwd().equals("test124"));
    	
    	user.setPhone("123");
    	user.setName("테스트");
    	user.setEmail("yblove1551@naver.com");
    	user.setBirth(new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-12"));
    	
    	assertTrue(userDAO.update(user) == 1); 
    	user =  userDAO.selectById(user.getId());
    	assertTrue(user.getPhone().equals("123") 
    			&& user.getName().equals("테스트") 
    			&& user.getEmail().equals("yblove1551@naver.com")   
    			);
    	
    	System.out.println(user.getBirth());
    	
   
    	
    	
    	
    }
    
    
    

    
    
    
    
}
