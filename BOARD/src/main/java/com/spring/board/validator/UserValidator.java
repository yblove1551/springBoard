package com.spring.board.validator;

import java.util.stream.Stream;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.spring.board.domain.User;

public class UserValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id",  "required", "아이디는 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "required", "비밀번호는 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required", "이름은 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required", "이메일은 필수입니다.");

        String id = user.getId();
        if (id != null)
	        if(id.length() <  4 || id.length() > 15) 
	            errors.rejectValue("id", "invalidLength", "아이디 길이는 4~15자여야 합니다." );
	        
        
        String pwd = user.getPwd();
        if (pwd != null) {
	        if(pwd.length() <  4 || pwd.length() > 15) 
	            errors.rejectValue("pwd", "invalidLength", "비밀번호 길이는 4~15자여야 합니다.");
        

	        if (pwd.trim().equals(id)) 
	        	errors.rejectValue("pwd", "idEqaulPwd", "아이디와 비밀번호가 같습니다.");
        }
        
        String name = user.getName();
        if (name != null)
	        if(name.length() <  2 || name.length() > 30) {
	            errors.rejectValue("pwd", "invalidLength", "이름은 2~30자 사이입니다.");
	        }
	        
        String email = user.getEmail();
        if (email != null)
	        if(email.indexOf('@') == -1 
	        		||email.substring(0, email.indexOf('@') - 1).trim().isEmpty()
	        		||email.substring(email.indexOf('@') + 1, email.length()).trim().isEmpty())
	        	errors.rejectValue("email", "invalidValue", "이메일 형식이 맞지 않습니다.");
        
    }
	
	
}
