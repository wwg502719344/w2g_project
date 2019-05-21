package com.w2g.utils;

import com.github.pagehelper.PageInfo;
import com.w2g.emun.ErrorCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于封装JSON数据 ----载体
 * Created by wilson on 2017/3/27.
 */
public class ResponseData<T> {
    /**
     * 获取当前可用的ResponseData对象
     * @return
     */
    public static <T> ResponseData<T> getInstance() {
        return new ResponseData<T>();
    }

    /**
     * 获取当前可用的ResponseData对象(SUCCESS)
     * @return
     */
    public static <T> ResponseData<T> newSuccess() {
        return newSuccess("success");
    }

    /**
     * 获取当前可用的ResponseData对象(SUCCESS)
     * @param data
     * @return
     */
    public static <T> ResponseData<T> newSuccess(T data) {
        return newSuccess(data, "success");
    }

    /**
     * 获取当前可用的ResponseData对象(SUCCESS)
     * @param data
     * @return
     */
    public static <T> ResponseData<T> newSuccess(List<T> data) {
        return newSuccess(data, "success");
    }

    /**
     * 获取当前可用的ResponseData对象(SUCCESS)
     * @param data
     * @return
     */
    public static <T> ResponseData<T> newSuccess(PageInfo<T> data) {
        return newSuccess(data, "success");
    }

    /**
     * 获取当前可用的ResponseData对象(SUCCESS)
     * @param message
     * @return
     */
    public static <T> ResponseData<T> newSuccess(String message) {
        ResponseData<T> rd = getInstance();
        rd.setStatus("success");
        rd.setErrorCode(ErrorCode.GLOBLE_SUCCESS.getErrorCode());
        rd.setMsg(message);

        return rd;
    }

    /**
     * 获取当前可用的ResponseData对象(SUCCESS)
     * @param data
     * @param message
     * @return
     */
    public static <T> ResponseData<T> newSuccess(T data, String message) {
        ResponseData<T> rd = getInstance();
        rd.setStatus("success");
        rd.setErrorCode(ErrorCode.GLOBLE_SUCCESS.getErrorCode());
        rd.setMsg(message);

        rd.setEntity(data);

        return rd;
    }

    /**
     * 获取当前可用的ResponseData对象(SUCCESS)
     * @param data
     * @param message
     * @return
     */
    public static <T> ResponseData<T> newSuccess(List<T> data, String message) {
        ResponseData<T> rd = getInstance();
        rd.setStatus("success");
        rd.setErrorCode(ErrorCode.GLOBLE_SUCCESS.getErrorCode());
        rd.setMsg(message);
        rd.setData(data == null?new ArrayList<T>():data);
        rd.setTotal(null);
        rd.setPageNum(null);
        rd.setPageSize(null);
        rd.setTotalPages(null);

        return rd;
    }

    /**
     * 获取当前可用的ResponseData对象(SUCCESS)
     * @param data
     * @param message
     * @return
     */
    public static <T> ResponseData<T> newSuccess(PageInfo<T> data, String message) {
        ResponseData<T> rd = getInstance();
        rd.setStatus("success");
        rd.setErrorCode(ErrorCode.GLOBLE_SUCCESS.getErrorCode());
        rd.setMsg(message);
        rd.setEntity(null);
        List<T> _tmp = data.getList();
        rd.setData(_tmp == null?new ArrayList<T>():data.getList());
        rd.setTotal(data.getTotal());
        rd.setPageNum(data.getPageNum());
        rd.setPageSize(data.getPageSize());
        rd.setTotalPages(data.getPages());

        return rd;
    }

    /**
     * 获取当前可用的ResponseData对象(FAILURE)
     * @return
     */
    public static <T> ResponseData<T> newFailure() {
        return newFailure("Failure");
    }

    /**
     * 获取当前可用的ResponseData对象(FAILURE)
     * @param exp
     * @return
     */
    public static <T> ResponseData<T> newFailure(Exception exp) {
        return newFailure(exp, "Failure");
    }

    /**
     * 获取当前可用的ResponseData对象(FAILURE)
     * @param message
     * @return
     */
    public static <T> ResponseData<T> newFailure(String message) {
        ResponseData<T> rd = getInstance();
        rd.setStatus("error");
        rd.setMsg(message);
        return rd;
    }

    /**
     * 获取当前可用的ResponseData对象(FAILURE)
     * @param exp
     * @param message
     * @return
     */
    public static <T> ResponseData<T> newFailure(Exception exp, String message) {
        ResponseData<T> rd = getInstance();
        rd.setStatus("error");
        rd.setMsg(message);
        return rd;
    }

    /**
     * 简单的返回描述性成功
     * @param msg ---成功描述语
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> returnSuccessMsg(String msg,List<T> data ,T entity,Object extra){

        ResponseData<T> rd = new ResponseData<T>();

        rd.setStatus(ErrorCode.GLOBLE_SUCCESS.getMsg());
        rd.setMsg(msg);
        rd.setErrorCode(ErrorCode.GLOBLE_SUCCESS.getErrorCode());

        rd.setEntity(entity);
        rd.setExtra(extra);
        rd.setData(data == null?new ArrayList<T>():data);

        rd.setTotal(null);
        rd.setPageNum(null);
        rd.setPageSize(null);
        rd.setTotalPages(null);

        return rd;
    }

    /**
     * 简单的返回描述性成功
     * @param msg ---成功描述语
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> returnErrorMsg(String msg,List<T> data ,T entity,Object extra){

        ResponseData<T> rd = new ResponseData<T>();

        rd.setStatus(ErrorCode.GLOBLE_ERROR.getMsg());
        rd.setMsg(msg);
        rd.setErrorCode(ErrorCode.GLOBLE_ERROR.getErrorCode());


        rd.setEntity(entity);
        rd.setExtra(extra);
        rd.setData(data == null?new ArrayList<T>():data);

        rd.setTotal(null);
        rd.setPageNum(null);
        rd.setPageSize(null);
        rd.setTotalPages(null);

        return rd;
    }

    /**
     * 简单的返回分页查询描述性成功
     * @param msg ---成功描述语
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> returnSuccessPageMsg(String msg,PageInfo<T> data ,T entity,Object extra){

        ResponseData<T> rd = new ResponseData<T>();

        rd.setStatus(ErrorCode.GLOBLE_SUCCESS.getMsg());
        rd.setMsg(msg);
        rd.setErrorCode(ErrorCode.GLOBLE_SUCCESS.getErrorCode());


        rd.setEntity(entity);
        rd.setExtra(extra);
        rd.setData(data == null?new ArrayList<T>():data.getList());

        rd.setTotal(data == null ? 0 : data.getTotal());
        rd.setPageNum(data == null ? 1 : data.getPageNum());
        rd.setPageSize(data == null ? 30 : data.getPageSize());
        rd.setTotalPages(data == null ? 1 : data.getPages());

        return rd;
    }



    /**
     * 获取当前可用的ResponseData对象(SUCCESS)
     * @param data
     * @param message
     * @return
     */
    public static <T> ResponseData<T> newSuccessPage(String message ,PageInfo<T> data,T entity) {

        ResponseData<T> rd = new ResponseData<T>();

        rd.setStatus(ErrorCode.GLOBLE_SUCCESS.getMsg());
        rd.setMsg(message);
        rd.setErrorCode(ErrorCode.GLOBLE_SUCCESS.getErrorCode());

        rd.setEntity(entity);
        List<T> _tmp = data.getList();
        rd.setData(_tmp == null?new ArrayList<T>():data.getList());
        rd.setTotal(data.getTotal());
        rd.setPageNum(data.getPageNum());
        rd.setPageSize(data.getPageSize());
        rd.setTotalPages(data.getPages());

        return rd;
    }

    //---------------------------------------------------------
    //请求信息
    private String status;
    private String msg;
    private String errorCode;
    //数据对象
    private T entity;
    //数据对象组
    private List<T> data;
    //数据对象组分页数据
    private Long total;
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPages;
    //额外信息
    private Object extra;

    //---------------------------------------------------------
    public ResponseData() {

    }

    //---------------------------------------------------------
    public void setStatus(String status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public T getEntity() {
        return entity;
    }

    public Long getTotal() {
        return total;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Object getExtra() {
        return extra;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }
}
