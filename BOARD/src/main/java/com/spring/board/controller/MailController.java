package com.spring.board.controller;

import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.board.common.MailAuth;
import com.spring.board.domain.Mail;
import com.spring.board.domain.MailTemplete;
import com.spring.board.service.MailKeywordService;
import com.spring.board.service.MailService;
import com.spring.board.service.MailTempleteService;

@Controller
@RequestMapping("/mail")
public class MailController{
	final MailTempleteService mailTempleteService;
	final MailKeywordService mailKeywordService;
	final MailService mailService;
	
	private Set<MailAuth> mailAuthSet = new HashSet<>();//인증번호 보관 셋

	public MailController(MailTempleteService mailTempleteService, MailKeywordService mailKeywordService, MailService mailService ) {
		this.mailTempleteService = mailTempleteService;
		this.mailKeywordService = mailKeywordService;
		this.mailService = mailService;		
	}
	
	@PostMapping("/mail")
	@ResponseBody
	public ResponseEntity<String> sendMail(HttpServletRequest request) throws Exception {
		removeTimeOut();
		try {
		//입력이 JSON
		Map jsonMap = (Map) new JSONParser().parse(request.getReader());
		
		//메일세팅 불러오기
		String sKind = String.valueOf(jsonMap.get("kind"));
		int kind = Integer.parseInt(sKind);
		MailTemplete mailTemplete = mailTempleteService.selectByKind(kind);	
				
		if (mailTemplete == null)
			throw new Exception("mail Templete not found");
		
		//메일세팅에 내용 반영
		Mail mail = setMailContent(mailTemplete, new Mail(), jsonMap);
		try {
			sendMail(mail);	//보내기
			mail.setSuccess("Y");
		}catch(Exception e) {
			e.printStackTrace();
			mail.setSuccess("N");						
		}
						
		try {
			mailService.insertMailLog(mail); //로그
		}catch(Exception e){
			e.printStackTrace();
		}
		
		JSONObject json = new JSONObject();
		json.put("isSuccess", "Y");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "application/json; charset=utf-8");
		if (mail.getSuccess().equals("Y")) 	
			return new ResponseEntity<>(json.toString(), headers, HttpStatus.OK);			
		else
			return new ResponseEntity<>(json.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping("/auth")
	@ResponseBody
	public ResponseEntity<String> mailAuth(@RequestParam Integer authNumber,
											@RequestParam String email) throws Exception{
		removeTimeOut();
		JSONObject json = new JSONObject();
		if (!findByEmailAndAuthNum(email, authNumber)) {
			json.put("isSuccess", "N");
			return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
		}
	
					
		removeByEmail(email);
		json.put("isSuccess", "Y");
		return new ResponseEntity<>(json.toString(), HttpStatus.OK);
	
	}
	
	public void sendMail(Mail mail) throws Exception{
		//설정파일 읽기
		Properties p = new Properties();
		FileReader fr = null;
		try {
			fr = new FileReader(new File("c:\\mailset.properties"));
			p.load(fr);
		} catch (Exception e) {
			throw new Exception("메일설정파일을 찾을 수 없습니다");
		}
		finally {
			fr.close();
		}
		
	    //보내는사람 설정
		String email = p.getProperty("email");
		String password = p.getProperty("password");
		String sender = p.getProperty("name"); //sender를 못 읽어오는 이유

		
		mail.setSender(sender);
		mail.setSender_email(email);

	    //메일세션 획득	? 1회성인지 아닌지 일단 1회성으로	
		Session session = Session.getDefaultInstance(p, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() { 
				return new PasswordAuthentication(email, password); 
			} 
		});	

		//메시시지 전송
	    try {
	        MimeMessage message = new MimeMessage(session);
	        
			message.setFrom(new InternetAddress(mail.getSender_email(), mail.getSender(), "UTF-8")); //보낸이 이메일, 보낸이 명
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getReceiver_email())); //수신인 이메일
	        message.setSubject(mail.getTitle(), "UTF-8"); //메일 제목
	        message.setText(mail.getContent(), "UTF-8");    //메일 내용
	        Transport.send(message); ////전송
	    } catch (AddressException e) {
	    	//이메일 주소가 잘못된경우
	        e.printStackTrace();
	        throw e;
	    } catch (MessagingException e) {
	    	//메시지 전송 에러
	        e.printStackTrace();
	        throw e;
	    }	
	}
	
	public Integer add(String email) {
		//인증번호생성
		int authNum = -1;
		Set<Integer> authNumSet = mailAuthSet.stream().map(MailAuth::getAuthNumber).collect(Collectors.toSet());   
		while(true) {
			authNum = (int)(Math.random() * 9000 + 1000);
			if (!authNumSet.contains(authNum))
				break;
		}
		//맵에추가
		mailAuthSet.add(new MailAuth(email, authNum, System.currentTimeMillis()));
		//랜덤생성 값 리턴
		return authNum;
	}
	
	public void removeTimeOut() {
		Long currentTime = System.currentTimeMillis(); 
		mailAuthSet.removeIf(s -> currentTime - s.getCreateTime() > 1000 * 60 * 5); //5분	
	}
	
	public void removeByEmail(String email) {
		mailAuthSet.removeIf(s -> s.getEmail().equals(email));
	}
	
	public boolean findByEmailAndAuthNum(String email, int authNum) {
		return mailAuthSet.contains(new MailAuth(email, authNum, 0L));
	}
	
	public Mail setMailContent(MailTemplete temp, Mail mail, Map inputMap) {
		mail.setKind(temp.getKind()); //메일타입
		mail.setTitle(temp.getTitle()); //메일제목
		mail.setContent(temp.getContent()); //메일내용
  		mail.setReceiver(String.valueOf(inputMap.get("name"))); //받는사람
		mail.setReceiver_email(String.valueOf(inputMap.get("email"))); //받는사람이메일
		mail.setSuccess("N");
		
		if (temp.getKind() == 3)
			mail.setContent(mail.getContent().replaceAll("%id", String.valueOf(inputMap.get("id"))));
		else
			mail.setContent(mail.getContent().replaceAll("%authNumber", String.valueOf(add(String.valueOf(inputMap.get("email"))))));
		
		
		//%name, %id등 메일 템플릿의 키워드를 입력값으로 변환
		inputMap.forEach((s1, s2) -> {
			mail.setTitle(mail.getTitle().replaceAll("%"+String.valueOf(s1), String.valueOf(s2)));
			mail.setContent(mail.getContent().replaceAll("%"+String.valueOf(s1), String.valueOf(s2)));
		});
		
		return mail;
	}
}
