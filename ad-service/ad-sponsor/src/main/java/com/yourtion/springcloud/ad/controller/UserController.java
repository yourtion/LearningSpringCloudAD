package com.yourtion.springcloud.ad.controller;

import com.alibaba.fastjson.JSON;
import com.yourtion.springcloud.ad.exception.AdException;
import com.yourtion.springcloud.ad.service.IUserService;
import com.yourtion.springcloud.ad.vo.CreateUserRequest;
import com.yourtion.springcloud.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yourtion
 */
@Slf4j
@RestController
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create/user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws AdException {
        log.info("ad-sponsor: createUser -> {}", JSON.toJSONString(request));
        return userService.createUser(request);
    }
}
