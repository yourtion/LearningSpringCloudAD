package com.yourtion.springcloud.ad.service;

import com.yourtion.springcloud.ad.exception.AdException;
import com.yourtion.springcloud.ad.vo.CreateUserRequest;
import com.yourtion.springcloud.ad.vo.CreateUserResponse;

/**
 * @author yourtion
 */
public interface IUserService {

    /**
     * 创建用户
     *
     * @param request 请求
     * @return AdUser
     * @throws AdException 创建用户出错
     */
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
