package com.spring.board.validator;



import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.google.common.collect.Lists;
import com.spring.board.domain.Board;
import com.spring.board.domain.User;

public class BoardValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Board board = (Board)target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title",  "required", "글제목은 필수입니다");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "required", "글내용은 필수입니다.");

        List<Integer> t = new ArrayList<>();
        Integer[] arr = (Integer[]) t.toArray();
    }	
}
