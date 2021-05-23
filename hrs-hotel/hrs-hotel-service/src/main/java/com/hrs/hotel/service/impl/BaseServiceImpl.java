package com.hrs.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.hrs.hotel.domain.BaseDomain;
import com.hrs.hotel.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Slf4j
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseDomain> implements BaseService<T> {

    @Autowired
    protected M baseMapper;

    protected Boolean reBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(T t) {
        return reBool(baseMapper.insert(t));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        return reBool(baseMapper.update(entity, updateWrapper));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(T entity) {
        return reBool(baseMapper.updateById(entity));
    }

    @Override
    public T getById(Serializable id) {
        return baseMapper.selectById(id);
    }

    @Override
    public int count(Wrapper<T> queryWrapper) {
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public List<T> list(Wrapper<T> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper) {
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteById(Long id) {
        return reBool(baseMapper.deleteById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Wrapper<T> queryWrapper) {
        return reBool(baseMapper.delete(queryWrapper));
    }

}
