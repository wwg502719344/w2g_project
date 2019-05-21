package com.w2g.emun;

/**
 * 定义一个枚举，用于mysql过滤操纵类型
 * Created by W2G on 2017/2/4.
 */
public enum FilterType {

    /*** 为空*/
    NUL,

    /** 相等*/
    EQ,

    /** 不等*/
    NE,

    /** 小于*/
    LT,

    /** 大于*/
    GT,

    /** 小于等于*/
    LE,

    /** 大于等于*/
    GE,

    /** 模糊查询*/
    LK,

    /** 右模糊*/
    RLK,

    /** 左模糊*/
    LLK,

    /** 范围操作*/
    BTW,

    /** 范围操作*/
    IN,

    /** 手写SQL*/
    USR,
}