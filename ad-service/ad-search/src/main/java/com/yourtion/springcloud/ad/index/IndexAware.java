package com.yourtion.springcloud.ad.index;

/**
 * @author yourtion
 */
public interface IndexAware<K, V> {

    /**
     * 获取索引数据
     *
     * @param key 键
     * @return 值
     */
    V get(K key);

    /**
     * 添加索引
     *
     * @param key   键
     * @param value 值
     */
    void add(K key, V value);

    /**
     * 更新索引
     *
     * @param key   键
     * @param value 值
     */
    void update(K key, V value);

    /**
     * 删除索引
     *
     * @param key   键
     * @param value 值
     */
    void delete(K key, V value);
}
