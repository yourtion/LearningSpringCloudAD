package com.yourtion.springcloud.ad.dao;

import com.yourtion.springcloud.ad.entity.AdUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author yourtion
 */
public interface AdUnitRepository extends JpaRepository<AdUnit, Long> {

    /**
     * 根据计划ID与推广单元名称进行查询
     *
     * @param planId   计划ID
     * @param unitName 推广单元名称
     * @return AdUnit
     */
    AdUnit findByPlanIdAndUnitName(Long planId, String unitName);

    /**
     * 根据推广单元状态进行查询
     *
     * @param unitStatus 推广单元状态
     * @return AdUnit列表
     */
    List<AdUnit> findAllByUnitStatus(Integer unitStatus);
}
