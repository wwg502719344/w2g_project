package com.w2g.core.markerMapper;

/**
 * Created by W2G on 2018/4/21.
 */

import com.w2g.emun.FilterType;
import com.w2g.exception.ServiceException;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>FilterType</h2>
 * <table style="border-collapse:collapse;">
 * <tr><td>NUL</td><td>为空</td></tr>
 * <tr><td> EQ</td><td>相等</td></tr>
 * <tr><td> NE</td><td>不等</td></tr>
 * <tr><td> LT</td><td>小于</td></tr>
 * <tr><td> GT</td><td>大于</td></tr>
 * <tr><td> LE</td><td>小于等于</td></tr>
 * <tr><td> GE</td><td>大于等于</td></tr>
 * <tr><td> LK</td><td>模糊</td></tr>
 * <tr><td>RLK</td><td>右模糊</td></tr>
 * <tr><td>LLK</td><td>左模糊</td></tr>
 * <tr><td>BTW</td><td>范围操作</td></tr>
 * <tr><td> IN</td><td>范围操作</td></tr>
 * <tr><td>USR</td><td>手写SQL</td></tr>
 * </table>
 */
public class MapperFilter {
    private Class<?> clz;
    private Condition cond;
    private List<String> orderBys;
    private Example.Criteria workCriteria;
    private List<Example.Criteria> usedCriterias;

    public Class<?> getDataClass() {
        return this.clz;
    }

    public Condition getCondition() { return this.cond; }

    /**
     * 带参构造
     * @param
     */
    public MapperFilter(Class<?> clz) {
        this.clz = clz;
        this.cond = new Condition(clz, true);
        this.orderBys = new ArrayList<>();
        this.workCriteria = cond.createCriteria();
        this.usedCriterias = new ArrayList<>();
    }

    /**
     * 添加排序
     * @param name
     * @param isAsc
     */
    public void addOrderby(String name, boolean isAsc) throws ServiceException {
        orderBys.add(MapperUtils.propertyToColumn(name) + (isAsc ? " ASC" : " DESC"));
        String orderByClause = "";
        for (String orderby : orderBys)
            orderByClause += "," + orderby;
        cond.setOrderByClause(orderByClause.substring(1));
    }

    /**
     * 开始一个OR条件
     */
    public void startOr() {
        usedCriterias.add(workCriteria);
        workCriteria = cond.or();
    }

    /**
     * 结束当前OR条件
     */
    public void stopOr() {
        int idx = usedCriterias.size() - 1;
        if (idx >= 0)
            workCriteria = usedCriterias.remove(idx);
    }

    /**
     * 开始一个Sub条件
     */
    public void startSub() {
        usedCriterias.add(workCriteria);
        workCriteria = cond.createCriteria();
    }

    /**
     * 结束一个Sub条件
     */
    public void stopSub() {
        int idx = usedCriterias.size() - 1;
        if (idx >= 0)
            workCriteria = usedCriterias.remove(idx);
    }

    /**
     * 添加条件
     * @param name 属性名称(或:手写SQL片段)
     * @param type 操作类型
     * @param value 过滤数值
     * @throws ServiceException
     */
    public void addFilter(String name, FilterType type, Object value) throws ServiceException {
        this.addFilter(name, type, value, false);
    }


    /**
     * 添加条件
     * @param name 属性名称(或:手写SQL片段)
     * @param type 操作类型
     * @param value 过滤数值
     * @param reverse 是否反向处理
     * @throws ServiceException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addFilter(String name, FilterType type, Object value, boolean reverse) throws ServiceException {
        if (FilterType.NUL != type && FilterType.USR != type && value == null)
            return;
        MapperUtils.addFilterWorkCriteria(name, type, value, reverse, workCriteria);
    }

    /**
     * builder
     */
    public static class Builder {

        private Class<?> clz;
        private Condition cond;
        private List<String> orderBys;
        private Example.Criteria workCriteria;
        private List<Example.Criteria> usedCriterias;

        public Builder(Class<?> clz) {
            this.clz = clz;
            this.cond = new Condition(clz, true);
            this.orderBys = new ArrayList<>();
            this.workCriteria = cond.createCriteria();
            this.usedCriterias = new ArrayList<>();
        }

        /**
         * 添加排序
         * @param name
         * @param isAsc
         */
        public Builder addOrderby(String name, boolean isAsc) throws ServiceException {
            orderBys.add(MapperUtils.propertyToColumn(name) + (isAsc ? " ASC" : " DESC"));
            String orderByClause = "";
            for (String orderby : orderBys)
                orderByClause += "," + orderby;
            cond.setOrderByClause(orderByClause.substring(1));
            return this;
        }

        /**
         * 添加条件
         * @param name 属性名称(或:手写SQL片段)
         * @param type 操作类型
         * @param value 过滤数值
         * @throws ServiceException
         */
        public Builder addFilter(String name, FilterType type, Object value) throws ServiceException {
            return this.addFilter(name, type, value, false);
        }

        /**
         * 添加条件
         * @param name 属性名称(或:手写SQL片段)
         * @param type 操作类型
         * @param value 过滤数值
         * @param reverse 是否反向处理
         * @throws ServiceException
         */
        public Builder addFilter(String name, FilterType type, Object value, boolean reverse) throws ServiceException {
            if (FilterType.NUL != type && FilterType.USR != type && value == null)
                return this;
            MapperUtils.addFilterWorkCriteria(name, type, value, reverse, workCriteria);
            return this;
        }

        /**
         * 构建，返回一个新对象
         * @return
         */
        public MapperFilter build() {
            return new MapperFilter(this);
        }
    }

    /**
     * builder 构造
     * @param builder
     */
    private MapperFilter (Builder builder) {
        clz = builder.clz;
        cond = builder.cond;
        orderBys = builder.orderBys;
        workCriteria = builder.workCriteria;
        usedCriterias = builder.usedCriterias;
    }

    /**
     * 创建builder
     * @param clz
     * @return
     */
    public static Builder custom (Class<?> clz) {
        return new Builder(clz);
    }
}
