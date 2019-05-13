package com.yourtion.springcloud.ad.dao.unit;

import com.yourtion.springcloud.ad.entity.unit.AdUnitKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yourtion
 */
public interface AdUnitKeywordRepository extends
        JpaRepository<AdUnitKeyword, Long> {
}
