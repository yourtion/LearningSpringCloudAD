package com.yourtion.springcloud.ad.service.impl;

import com.yourtion.springcloud.ad.constant.Constants;
import com.yourtion.springcloud.ad.dao.AdUserRepository;
import com.yourtion.springcloud.ad.entity.AdUser;
import com.yourtion.springcloud.ad.exception.AdException;
import com.yourtion.springcloud.ad.service.IUserService;
import com.yourtion.springcloud.ad.utils.CommonUtils;
import com.yourtion.springcloud.ad.vo.CreateUserRequest;
import com.yourtion.springcloud.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yourtion
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final AdUserRepository userRepository;

    @Autowired
    public UserServiceImpl(AdUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {

        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdUser oldUser = userRepository.findByUsername(request.getUsername());
        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        AdUser newUser = userRepository.save(new AdUser(
                request.getUsername(),
                CommonUtils.md5(request.getUsername())
        ));

        return new CreateUserResponse(
                newUser.getId(), newUser.getUsername(), newUser.getToken(),
                newUser.getCreateTime(), newUser.getUpdateTime()
        );
    }
}
