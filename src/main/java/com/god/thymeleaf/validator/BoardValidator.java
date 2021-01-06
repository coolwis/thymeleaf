package com.god.thymeleaf.validator;

//import antlr.StringUtils;
//import org.springframework.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import  com.god.thymeleaf.model.*;
import org.thymeleaf.util.StringUtils;
@Component
public class BoardValidator  implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return  Board.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Board b=(Board)o;
        if(StringUtils.isEmpty(b.getContent())){
            errors.rejectValue("content", "key","내용을 입력하세요");
        }

    }
}
