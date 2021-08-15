package com.rest.api.advice;

import com.rest.api.model.response.CommonResult;
import com.rest.api.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice  // 예외발생 시, json 형태로 결과 return (프로젝트의 모든 controller에 로직 적용됨)
public class ExceptionAdvice {

    private final ResponseService responseService;

    @ExceptionHandler
    @ResponseStatus
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult();
    }
}
