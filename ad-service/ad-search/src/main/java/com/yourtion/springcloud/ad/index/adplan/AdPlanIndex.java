package com.yourtion.springcloud.ad.index.adplan;

import com.yourtion.springcloud.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yourtion
 */
@Slf4j
@Component
public class AdPlanIndex implements IndexAware<Long, AdPlanObject> {

    private static Map<Long, AdPlanObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public AdPlanObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdPlanObject value) {

        log.trace("before add: {}", objectMap);
        objectMap.put(key, value);
        log.trace("after add: {}", objectMap);
    }

    @Override
    public void update(Long key, AdPlanObject value) {

        log.trace("before update: {}", objectMap);

        AdPlanObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }

        log.trace("after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, AdPlanObject value) {

        log.trace("before delete: {}", objectMap);
        objectMap.remove(key);
        log.trace("after delete: {}", objectMap);
    }
}
