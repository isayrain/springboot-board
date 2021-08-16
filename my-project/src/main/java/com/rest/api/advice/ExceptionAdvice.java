package com.rest.api.advice;

import com.rest.api.advice.exception.CUserNotFoundException;
import com.rest.api.model.response.CommonResult;
import com.rest.api.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice  // 예외발생 시, json 형태로 결과 return (프로젝트의 모든 controller에 로직 적용됨)
public class ExceptionAdvice {

    private final ResponseService responseService;

//    @ExceptionHandler(Exception.class)  // Exception 발생 시, 해당 Handler로 처리하겠다고 명시. Exception.class 는 최상위 객체
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 해당 Exception 발생 시, code가 500으로 내려가도록 설정(성공시 200)
//    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
//        return responseService.getFailResult();  // Exception 발생 시, CommonResult의 실패 결과를 json으로 출력하도록 설정
//    }

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
        return responseService.getFailResult();
    }
}
