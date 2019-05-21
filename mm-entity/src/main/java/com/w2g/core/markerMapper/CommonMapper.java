package com.w2g.core.markerMapper;


import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.RowBoundsMapper;

/**
 * Created by W2G on 2018/4/21.
 */
public interface CommonMapper<T> extends BaseMapper<T> ,RowBoundsMapper<T>, ConditionMapper<T>, MySqlMapper<T> {

}
