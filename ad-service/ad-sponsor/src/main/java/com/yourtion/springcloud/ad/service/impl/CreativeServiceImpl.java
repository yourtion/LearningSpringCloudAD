package com.yourtion.springcloud.ad.service.impl;

import com.yourtion.springcloud.ad.dao.CreativeRepository;
import com.yourtion.springcloud.ad.entity.Creative;
import com.yourtion.springcloud.ad.service.ICreativeService;
import com.yourtion.springcloud.ad.vo.CreativeRequest;
import com.yourtion.springcloud.ad.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yourtion
 */
@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeRepository creativeRepository;

    @Autowired
    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponse createCreative(CreativeRequest request) {

        Creative creative = creativeRepository.save(request.convertToEntity());

        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
