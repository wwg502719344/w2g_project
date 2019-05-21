package com.w2g.core.markerMapper;

import com.w2g.emun.FilterType;
import com.w2g.exception.ServiceException;
import com.w2g.utils.DataUtil;
import tk.mybatis.mapper.entity.Example;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by W2G on 2018/4/21.
 */
public class MapperUtils {

    public static void addOrderBy(MapperFilter filter, String orderBy) throws ServiceException {
        String[] orderbys = orderBy.split(":");
        boolean isAsc = true;
        if (orderbys.length > 1 && "DESC".equalsIgnoreCase(orderbys[1])) {
            isAsc = false;
        }
        filter.addOrderby(orderbys[0], isAsc);
    }

    /*public static void setOrderBy(String orderBy) throws ServiceException {
        String[] orderbys = orderBy.split(":");
        String field = orderbys[0];
        String flag = " ASC";
        if (orderbys.length > 1 && "DESC".equalsIgnoreCase(orderbys[1])) {
            flag = " DESC";
        }
        PageHelper.orderBy(propertyToColumn(field) + flag);
    }*/

    public static String propertyToColumn(String property) throws ServiceException {
        if (DataUtil.chkInvalid(property))
            throw new ServiceException("Invalid property");
        StringBuilder column = new StringBuilder();
        column.append(property.substring(0, 1).toLowerCase());
        for (int i = 1; i < property.length(); i++) {
            char ch = property.charAt(i);
            if (DataUtil.chkCharUpper(ch))
                column.append("_");
            column.append(ch);
        }
        return column.toString();
    }

    /**
     * 添加mapper条件
     * @param name 属性名称(或:手写SQL片段)
     * @param type 操作类型
     * @param value 过滤数值
     * @param reverse 是否反向处理
     * @param workCriteria
     */
    public static void addFilterWorkCriteria(String name, FilterType type, Object value, boolean reverse, Example.Criteria workCriteria) {
        String val;
        List<Object> values;
        switch (type) {
            case NUL: //为空
                workCriteria = reverse ? workCriteria.andIsNotNull(name) : workCriteria.andIsNull(name);
                break;
            case NE: //不等
                workCriteria = reverse ? workCriteria.andEqualTo(name, value) : workCriteria.andNotEqualTo(name, value);
                break;
            case EQ: //相等
                workCriteria = reverse ? workCriteria.andNotEqualTo(name, value) : workCriteria.andEqualTo(name, value);
                break;
            case LT: //小于
                workCriteria = reverse ? workCriteria.andGreaterThanOrEqualTo(name, value) : workCriteria.andLessThan(name, value);
                break;
            case GT: //大于
                workCriteria = reverse ? workCriteria.andLessThanOrEqualTo(name, value) : workCriteria.andGreaterThan(name, value);
                break;
            case LE: //小于等于
                workCriteria = reverse ? workCriteria.andGreaterThan(name, value) : workCriteria.andLessThanOrEqualTo(name, value);
                break;
            case GE: //大于等于
                workCriteria = reverse ? workCriteria.andLessThan(name, value) : workCriteria.andGreaterThanOrEqualTo(name, value);
                break;
            case LK: //模糊
                val = "%%" + value + "%%";
                workCriteria = reverse ? workCriteria.andNotLike(name, val) : workCriteria.andLike(name, val);
                break;
            case RLK: //右模糊
                val = value + "%%";
                workCriteria = reverse ? workCriteria.andNotLike(name, val) : workCriteria.andLike(name, val);
                break;
            case LLK: //左模糊
                val = "%%" + value;
                workCriteria = reverse ? workCriteria.andNotLike(name, val) : workCriteria.andLike(name, val);
                break;
            case BTW: //范围操作
                values = new LinkedList<Object>();
                if (value instanceof List) {
                    values = (List) value;
                } else if (value instanceof Object[]) {
                    for (Object v : (Object[]) value)
                        values.add(v);
                    //values = Arrays.asList(value);
                } else {
                    values.add(value);
                }
                if (DataUtil.chkInvalid(values) || values.size() != 2)
                    throw new ServiceException("Invalid filter value");
                workCriteria = reverse ? workCriteria.andNotBetween(name, values.get(0), values.get(1))
                        : workCriteria.andBetween(name, values.get(0), values.get(1));
                break;
            case IN: //范围操作
                values = new LinkedList<Object>();
                Class<?> valueClass = value.getClass();
                if (List.class.isAssignableFrom(valueClass)) {
                    values = (List) value;
                } else if (valueClass.isArray()) {
                    for (Object v : (Object[]) value)
                        values.add(v);
                    //values = Arrays.asList(value);
                } else {
                    values.add(value);
                }
                if (DataUtil.chkInvalid(values))
                    throw new ServiceException("Invalid filter value");
                workCriteria = reverse ? workCriteria.andNotIn(name, values) : workCriteria.andIn(name, values);
                break;
            case USR: //手写SQL
                workCriteria.andCondition(name);
                break;
            default: //默认
                //TODO: NOTHING
                break;
        }
    }
}
