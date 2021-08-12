package com.rest.api.controller.v1;

import com.rest.api.entity.User;
import com.rest.api.model.response.CommonResult;
import com.rest.api.model.response.ListResult;
import com.rest.api.model.response.SingleResult;
import com.rest.api.repo.UserJpaRepo;
import com.rest.api.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController  // 결과값을 JSON으로 출력
@RequestMapping(value = "/v1")
public class UserController {
    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;  // 결과를 처리할 Service

    @ApiOperation(value = "회원 조회", notes = "모든 회원 조회")
    @GetMapping(value = "/user")
    public ListResult<User> findAllUser() {
        // 결과데이터가 여러 건일 경우, getListResult 이용하여 결과 출력
        return responseService.getListResult(userJpaRepo.findAll());
    }

    @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원 조회")
    @GetMapping(value = "/user{msrl}")
    public SingleResult<User> findUserById(@ApiParam(value = "회원 ID", required = true) @PathVariable long msrl) {
        // 결과데이터가 단일 건인 경우, getSingleResult 이용하여 결과 출력
        return responseService.getSingleResult(userJpaRepo.findById(msrl).orElse(null));
    }

    @ApiOperation(value = "회원 입력", notes = "회원 입력")
    @PostMapping(value = "/user")
    public SingleResult<User> save(@ApiParam(value = "회원ID", required = true) @RequestParam String uid,
                                   @ApiParam(value = "회원명", required = true) @RequestParam String name) {
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiOperation(value = "회원 수정", notes = "회원 수정")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(@ApiParam(value = "회원번호", required = true) @RequestParam long msrl,
                                     @ApiParam(value = "회원ID", required = true) @RequestParam String uid,
                                     @ApiParam(value = "회원명", required = true) @RequestParam String name) {
        User user = User.builder()
                .msrl(msrl)
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiOperation(value = "회원 삭제", notes = "회원번호로 회원 삭제")
    @PutMapping(value = "/user{msrl}")
    public CommonResult delete(@ApiParam(value = "회원번호", required = true) @RequestParam long msrl) {
        userJpaRepo.deleteById(msrl);
        // 성공 결과 정보만 필요한 경우, getSuccessResult 이용하여 결과 출력
        return responseService.getSuccessResult();
    }
}
