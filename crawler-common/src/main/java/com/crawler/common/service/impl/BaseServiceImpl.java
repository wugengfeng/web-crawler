package com.crawler.common.service.impl;

import com.crawler.common.mapper.Mapper;
import com.crawler.common.service.BaseService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-22 15:28
 * @description: 公共Service实现, 封装通用的sql操作方法
 * 注意，如果方法需要事务，则必须对Service方法进行重写
 * 业务ServiceImpl继承此类记得需要注入mapper
 **/
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    private Mapper<T> mapper;

    @Autowired
    public void setMapper(Mapper<T> mapper) {
        this.mapper = mapper;
    }

    @Override
    public int deleteByPrimaryKey(Object o) {
        return mapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(T t) {
        return mapper.delete(t);
    }

    @Override
    public int insert(T t) {
        return mapper.insert(t);
    }

    @Override
    public int insertList(List<T> list, Integer size) {
        int result = 0;
        if (CollectionUtils.isNotEmpty(list)) {

            if (Objects.isNull(size)) {
                result = mapper.insertList(list);

            } else {
                List<List<T>> partitionList = Lists.partition(list, size);

                for (List<T> subList : partitionList) {
                    result += mapper.insertList(list);
                }
            }
        }
        return result;
    }

    @Override
    public int insertSelective(T t) {
        return mapper.insertSelective(t);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return mapper.existsWithPrimaryKey(o);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public T selectByPrimaryKey(Object o) {
        return mapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(T t) {
        return mapper.selectCount(t);
    }

    @Override
    public List<T> select(T t) {
        return mapper.select(t);
    }

    @Override
    public T selectOne(T t) {
        return mapper.selectOne(t);
    }

    @Override
    public int updateByPrimaryKey(T t) {
        return mapper.updateByPrimaryKey(t);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        return mapper.updateByPrimaryKeySelective(t);
    }
}
