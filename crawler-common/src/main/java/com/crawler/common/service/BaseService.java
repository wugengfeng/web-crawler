package com.crawler.common.service;

import java.util.List;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-22 15:27
 * @description: 公共Service
 **/
public interface BaseService<T> {

    /**
     * 根据主键删除
     *
     * @param o
     * @return
     */
    int deleteByPrimaryKey(Object o);

    /**
     * 删除
     *
     * @param t
     * @return
     */
    int delete(T t);

    /**
     * 新增
     *
     * @param t
     * @return
     */
    int insert(T t);

    /**
     * 批量新增，当一次性插入数据过大时，会根据size分割插入数据，
     * 分批多次插入数据
     *
     * @param list 插入数据
     * @param size 每次插入条数
     * @return
     */
    int insertList(List<T> list, Integer size);

    /**
     * 选择性新增
     *
     * @param t
     * @return
     */
    int insertSelective(T t);

    /**
     * 判断主键是否存在
     *
     * @param o
     * @return
     */
    boolean existsWithPrimaryKey(Object o);

    /**
     * 查询所有
     *
     * @return
     */
    List<T> selectAll();

    /**
     * 根据主键查询
     *
     * @param o
     * @return
     */
    T selectByPrimaryKey(Object o);

    /**
     * 总数查询
     *
     * @param t
     * @return
     */
    int selectCount(T t);

    /**
     * 列表查询
     *
     * @param t
     * @return
     */
    List<T> select(T t);

    /**
     * 查询一条，当查询结果为多条会抛异常
     *
     * @param t
     * @return
     */
    T selectOne(T t);

    /**
     * 根据主键更新
     *
     * @param t
     * @return
     */
    int updateByPrimaryKey(T t);

    /**
     * 根据主键选择性更新
     *
     * @param t
     * @return
     */
    int updateByPrimaryKeySelective(T t);
}
