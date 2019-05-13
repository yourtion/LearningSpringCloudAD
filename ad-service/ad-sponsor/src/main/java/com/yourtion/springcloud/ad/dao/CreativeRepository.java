package com.yourtion.springcloud.ad.dao;

import com.yourtion.springcloud.ad.entity.Creative;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yourtion
 */
public interface CreativeRepository extends JpaRepository<Creative, Long> {
}
