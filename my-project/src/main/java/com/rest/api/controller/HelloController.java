package com.rest.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
//    화면에 Hello World 출력
    @GetMapping(value = "/helloworld/string")
    @ResponseBody
    public String helloworldString() {
        return "Hello, World";
    }

//    화면에 {message : "Hello, World"} 출력
    @GetMapping(value = "/helloworld/json")
    @ResponseBody
    public Hello helloworldJson() {
        Hello hello = new Hello();
        hello.setMessage("Hello, World");
//        hello.message = "Hello, World";
        return hello;
    }

//    화면에 helloworld.ftl의 내용이 출력됩니다.
    @GetMapping(value = "/helloworld/page")
    public String helloworld() {
        return "helloworld";
    }
}
