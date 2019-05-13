package com.yourtion.springcloud.ad.dao;

import com.yourtion.springcloud.ad.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author yourtion
 */
public interface AdPlanRepository extends JpaRepository<AdPlan, Long> {

    /**
     * 根据ID与用户ID进行查询
     *
     * @param id     ID
     * @param userId 用户ID
     * @return AdPlan
     */
    AdPlan findByIdAndUserId(Long id, Long userId);

    /**
     * 根据ID列表与用户ID进行查询
     *
     * @param ids    ID列表
     * @param userId 用户ID
     * @return AdPlan列表
     */
    List<AdPlan> findAllByIdInAndUserId(List<Long> ids, Long userId);

    /**
     * 根据与计划名进行查询
     *
     * @param userId   用户ID
     * @param planName 计划名
     * @return AdPlan
     */
    AdPlan findByUserIdAndPlanName(Long userId, String planName);

    /**
     * 根据计划状态进行查询
     *
     * @param status 计划状态
     * @return AdPlan列表
     */
    List<AdPlan> findAllByPlanStatus(Integer status);
}
