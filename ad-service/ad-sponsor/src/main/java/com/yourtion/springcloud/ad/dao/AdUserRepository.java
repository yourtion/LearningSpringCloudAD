package com.yourtion.springcloud.ad.dao;

import com.yourtion.springcloud.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yourtion
 */
public interface AdUserRepository extends JpaRepository<AdUser, Long> {

    /**
     * 根据用户名查找用户记录
     *
     * @param username 用户名
     * @return AdUser
     */
    AdUser findByUsername(String username);
}
