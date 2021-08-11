package com.rest.api.controller.v1;

import com.rest.api.entity.User;
import com.rest.api.repo.UserJpaRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController  // 결과값을 JSON으로 출력
@RequestMapping(value = "/v1")
public class UserController {
    private final UserJpaRepo userJpaRepo;

    @ApiOperation(value = "회원 조회", notes = "모든 회원 조회")
    @GetMapping(value = "/user")
    public List<User> findAllUser() {
        return userJpaRepo.findAll();
    }

    @ApiOperation(value = "회원 입력", notes = "회원 입력")
    @PostMapping(value = "/user")
    public User save(@ApiParam(value = "회원ID", required = true) @RequestParam String uid,
                     @ApiParam(value = "회원명", required = true) @RequestParam String name) {
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
//        User user = User.builder()
//                .uid("jay@naver.com")
//                .name("재춘")
//                .build();
        return userJpaRepo.save(user);
    }
}
