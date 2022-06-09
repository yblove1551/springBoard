package com.spring.board.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.spring.board.domain.User;

@Repository
public class UserDAO {
	@Autowired
	private SqlSession session;
	private static String namespace = "com.spring.board.dao.UserMapper.";
		
	//아이디, 비밀번호로 조회
	public User selectByIdAndPwd(String id, String pwd){
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("pwd", pwd);
		return session.selectOne(namespace + "selectByIdAndPwd", map);	
	}	
	//아이디로 조회(아이디 중복 확인)
	public User selectById(String id){
		return session.selectOne(namespace + "selectById", id);
	}
	//이메일로 조회(이메일 중복 확인)
	public User selectByEmail(String email){
		return session.selectOne(namespace + "selectByEmail", email);
	}
	//이메일, 이름으로 조회
	public User selectByEmailAndName(String email, String name){
		Map<String, String> map = new HashMap<>();
		map.put("email", email);
		map.put("name", name);
		return session.selectOne(namespace + "selectByEmailAndName", map);	
	}
	
	//아이디로 삭제
	public int deleteById(String id){
		return session.delete(namespace + "deleteById", id);
	}
//	수정
	public int update(User user){
		return session.update(namespace + "update", user);
	}
	//비밀번호수정 - 비밀번호 찾기
	public int updatePwd(String id, String pwd){
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("pwd", pwd);
		return session.update(namespace + "updatePwd", map);		
	}
	
	
//	추가
	public int insert(User user){
		return session.insert(namespace + "insert", user);				
	}	
}
