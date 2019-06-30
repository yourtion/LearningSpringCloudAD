package com.yourtion.springcloud.ad.service;

import com.yourtion.springcloud.ad.dao.AdPlanRepository;
import com.yourtion.springcloud.ad.dao.AdUnitRepository;
import com.yourtion.springcloud.ad.dao.CreativeRepository;
import com.yourtion.springcloud.ad.dao.unit.AdUnitDistrictRepository;
import com.yourtion.springcloud.ad.dao.unit.AdUnitItRepository;
import com.yourtion.springcloud.ad.dao.unit.AdUnitKeywordRepository;
import com.yourtion.springcloud.ad.dao.unit.CreativeUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yourtion
 */
@Component
public class DumpDataService {

    @Autowired
    private AdPlanRepository planRepository;
    @Autowired
    private AdUnitRepository unitRepository;
    @Autowired
    private CreativeRepository creativeRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;
    @Autowired
    private AdUnitDistrictRepository districtRepository;
    @Autowired
    private AdUnitItRepository itRepository;
    @Autowired
    private AdUnitKeywordRepository keywordRepository;

}
