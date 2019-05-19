package com.yourtion.springcloud.ad.index;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yourtion
 */
@Component
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    private static final Map<Class, Object> DATA_TABLE_MAP = new ConcurrentHashMap<>();
    private static ApplicationContext applicationContext;

    @SuppressWarnings("unchecked")
    public static <T> T of(Class<T> clazz) {

        T instance = (T) DATA_TABLE_MAP.get(clazz);
        if (null != instance) {
            return instance;
        }

        DATA_TABLE_MAP.put(clazz, bean(clazz));
        return (T) DATA_TABLE_MAP.get(clazz);
    }

    @SuppressWarnings("unchecked")
    private static <T> T bean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    @SuppressWarnings("unchecked")
    private static <T> T bean(Class clazz) {
        return (T) applicationContext.getBean(clazz);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }
}
