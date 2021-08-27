package com.rest.api.controller.v1;

import com.rest.api.advice.exception.CUserNotFoundException;
import com.rest.api.entity.User;
import com.rest.api.model.response.CommonResult;
import com.rest.api.model.response.ListResult;
import com.rest.api.model.response.SingleResult;
import com.rest.api.repo.UserJpaRepo;
import com.rest.api.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {

    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;  // 결과 처리할 Service

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원 조회")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {
        // 결과데이터가 여러건인 경우 getListResult를 이용해서 결과 출력
        return responseService.getListResult(userJpaRepo.findAll());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "회원번호(msrl)로 회원 조회")
    @GetMapping(value = "/user")
    public SingleResult<User> findUser(@ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        // SecurityContext에서 인증받은 회원의 정보 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        // 결과 데이터가 단일건인 경우 getSingleResult를 이용하여 결과 출력
        return responseService.getSingleResult(userJpaRepo.findByUid(id).orElseThrow(CUserNotFoundException::new));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원정보 수정")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원번호", required = true) @RequestParam long msrl,
            @ApiParam(value = "회원ID", required = true) @RequestParam String uid,
            @ApiParam(value = "회원명", required = true) @RequestParam String name) {
        User user = User.builder()
                .msrl(msrl)
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "회원번호(msrl)로 회원정보 삭제")
    @GetMapping(value = "/user/{msrl}")
    public CommonResult delete(
            @ApiParam(value = "회원번호", required = true) @PathVariable long msrl) {
        userJpaRepo.deleteById(msrl);
        // 성공 결과 정보만 필요한 경우 getSuccessResult()를 이용하여 결과 출력
        return responseService.getSuccessResult();
    }
}

//@Api(tags = {"1. User"})
//@RequiredArgsConstructor
//@RestController  // 결과값을 JSON으로 출력
//@RequestMapping(value = "/v1")
//public class UserController {
//    private final UserJpaRepo userJpaRepo;
//    private final ResponseService responseService;  // 결과를 처리할 Service
//
//    @ApiOperation(value = "회원 조회", notes = "모든 회원 조회")
//    @GetMapping(value = "/user")
//    public ListResult<User> findAllUser() {
//        // 결과데이터가 여러 건일 경우, getListResult 이용하여 결과 출력
//        return responseService.getListResult(userJpaRepo.findAll());
//    }
//
//    @ApiOperation(value = "회원 단건 조회", notes = "회원번호로 회원 조회")
//    @GetMapping(value = "/user/{msrl}")
//    public SingleResult<User> findUserById(@ApiParam(value = "회원번호", required = true) @PathVariable long msrl,
//                                           @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
//        // 결과데이터가 단일 건인 경우, getSingleResult 이용하여 결과 출력
//        return responseService.getSingleResult(userJpaRepo.findById(msrl).orElseThrow(CUserNotFoundException::new));
//    }
//
//    @ApiOperation(value = "회원 입력", notes = "회원 입력")
//    @PostMapping(value = "/user")
//    public SingleResult<User> save(@ApiParam(value = "회원ID", required = true) @RequestParam String uid,
//                                   @ApiParam(value = "회원명", required = true) @RequestParam String name) {
//        User user = User.builder()
//                .uid(uid)
//                .name(name)
//                .build();
//        return responseService.getSingleResult(userJpaRepo.save(user));
//    }
//
//    @ApiOperation(value = "회원 수정", notes = "회원 수정")
//    @PutMapping(value = "/user")
//    public SingleResult<User> modify(@ApiParam(value = "회원번호", required = true) @RequestParam long msrl,
//                                     @ApiParam(value = "회원ID", required = true) @RequestParam String uid,
//                                     @ApiParam(value = "회원명", required = true) @RequestParam String name) {
//        User user = User.builder()
//                .msrl(msrl)
//                .uid(uid)
//                .name(name)
//                .build();
//        return responseService.getSingleResult(userJpaRepo.save(user));
//    }
//
//    @ApiOperation(value = "회원 삭제", notes = "회원번호로 회원 삭제")
//    @PutMapping(value = "/user/{msrl}")
//    public CommonResult delete(@ApiParam(value = "회원번호", required = true) @RequestParam long msrl) {
//        userJpaRepo.deleteById(msrl);
//        // 성공 결과 정보만 필요한 경우, getSuccessResult 이용하여 결과 출력
//        return responseService.getSuccessResult();
//    }
//}
