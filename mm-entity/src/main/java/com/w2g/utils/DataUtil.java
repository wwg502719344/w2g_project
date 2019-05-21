package com.w2g.utils;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


/**
 * @author Half.Lee
 */
public class DataUtil {

    private static Logger logger = Logger.getLogger(DataUtil.class);

    /* 数据默认值 */
    public static final String EMPTY_DATA_TAG = "";
    public static final String ERROR_DATA_TAG = "N/A";

    //--------------------------------------------------------------------------------------------------------------------

    /**
     * 检查对象合法性
     * @param obj
     * @return true--不合法, false--合法
     */
    public static boolean chkNull(Object obj) {
        return obj == null;
    }

    /**
     * 检查对象合法性
     * @param list
     * @return true--不合法, false--合法
     */
    @SuppressWarnings("rawtypes")
    public static boolean chkInvalid(List list) {
        return (chkNull(list) || list.size() == 0);
    }

    /**
     * 检查对象合法性
     * @param list
     * @return true--不合法, false--合法
     */
    public static boolean chkInvalid(Object[] list) {
        return (chkNull(list) || list.length == 0);
    }

    /**
     * 检查对象合法性
     * @param text
     * @return true--不合法, false--合法
     */
    public static boolean chkInvalid(String text) {
        return (chkNull(text) || text.trim().equals(""));
    }

    /**
     * 检查JAVA Bean对象是否合法
     * <br>此方法首先会尝试设置Bean对象的id属性为null，然后检查所有属性是否都为null
     * @param bean
     * @return true--JAVA Bean所以属性均为null, false--JAVA Bean有属性不为null
     */
    public static boolean chkBeanValue(Object bean) {
        if (chkNull(bean)) return true;
        try {
            Field id = bean.getClass().getDeclaredField("id");
            Method setId = bean.getClass().getDeclaredMethod("setId", id.getType());
            setId.invoke(bean, new Object[] { null });
        } catch (Exception e) { }

        try {
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("get"))
                    if (!chkNull(method.invoke(bean, new Object[0])))
                        return false;
            }
        } catch (Exception e) {
            return true;
        }
        return true;
    }

    /**
     * 检查字符是否在[a~z,A~Z,0~9,汉字]中
     * @param ch
     * @return
     */
    public static boolean chkCharValid(char ch) {
        return (ch >= 0x4E00 && ch <= 0X9FA5)
                || (ch >= 'a' && ch <= 'z')
                || (ch >= 'A' && ch <= 'Z')
                || (ch >= '0' && ch <= '9');
    }

    public static boolean chkCharUpper(char ch) {
        return (ch >= 'A' && ch <= 'Z');
    }

    public static boolean chkCharLower(char ch) {
        return (ch >= 'a' && ch <= 'z');
    }

    //--------------------------------------------------------------------------------------------------------------------

    /**
     * 检查status里是否包含flag
     * @param status -- flag1 | flag2 | ...
     * @param flag -- 2^n
     * @return
     */
    public static boolean hasStatus(int status, int flag) {
        return flag == (status & flag);
    }

    public static int removeStatus(int status, int flag) {
        return status & ~flag;
    }

    public static int addStatus(int status, int flag) {
        return status | flag;
    }

    //--------------------------------------------------------------------------------------------------------------------

    /**
     * 获取有效的数据值(Valid Value)<br>
     * 若传进来的数据为NULL则返回系统EMPTY_DATA_TAG
     * @param value
     * @return
     */
    public static String getVV(String value) {
        return value == null ? EMPTY_DATA_TAG : value;
    }

    //--------------------------------------------------------------------------------------------------------------------

    /**
     * 获取枚举类中所有的类型
     * @param enumClz 枚举类
     * @return
     * @throws Throwable
     */
    public static List<Map<String, Object>> getEnums(Class<?> enumClz) throws Throwable {
        Method[] methods = enumClz.getDeclaredMethods();
        List<Map<String, Object>> enums = new ArrayList<Map<String, Object>>();
        if (enumClz.isEnum()) {
            List<?> list = Arrays.asList(enumClz.getEnumConstants());
            for (Object _enum_ : list) {
                Map<String, Object> enumm = new HashMap<String, Object>();
                for (Method method : methods) {
                    if (method.getName().startsWith("get")) {
                        String attrb = method.getName().substring(3);
                        attrb = attrb.substring(0, 1).toLowerCase() + attrb.substring(1);
                        enumm.put(attrb, method.invoke(_enum_));
                    }
                }
                enums.add(enumm);
            }
        }
        return enums;
    }

    /**
     * 根据给定的属性和值获取对应的枚举类<br>
     * 没有找到指定枚举类时抛出异常
     * @param enumClz 枚举类类型
     * @param field 属性名称
     * @param value 属性参考值
     */
    @SuppressWarnings("unchecked")
    public static <T, P> List<T> getEnumsByField(Class<T> enumClz, String field, P value) throws Exception {
        if (chkNull(value)) {
            throw new Exception("No found specified Enum.");
        }

        if (!chkCharUpper(field.charAt(1)))
            field = field.substring(0, 1).toUpperCase() + field.substring(1);
        Method getter = enumClz.getDeclaredMethod("get" + field, new Class[0]);
        List<T> _src_ = Arrays.asList(enumClz.getEnumConstants());
        List<T> _out_ = new ArrayList<T>();
        P fieldValue = null;
        for (T _enum_ : _src_) {
            fieldValue = (P) getter.invoke(_enum_);
            if (fieldValue.equals(value))
                _out_.add(_enum_);
        }
        if (chkInvalid(_out_)) {
            throw new Exception("No found specified Enum.");
        }

        return _out_;
    }

    /**
     * 根据给定的属性和值获取对应的枚举类<br>
     * 没有找到指定枚举类时抛出异常
     * @param enumClz 枚举类类型
     * @param field 属性名称
     * @param value 属性参考值
     */
    @SuppressWarnings("unchecked")
    public static <T, P> T getEnumByField(Class<T> enumClz, String field, P value) throws Exception {
        if (chkNull(value))
            throw new Exception("No found specified Enum.");
        if (!chkCharUpper(field.charAt(1)))
            field = field.substring(0, 1).toUpperCase() + field.substring(1);
        Method getter = enumClz.getDeclaredMethod("get" + field, new Class[0]);
        List<T> list = Arrays.asList(enumClz.getEnumConstants());
        P fieldValue = null;
        for (T _enum_ : list) {
            fieldValue = (P) getter.invoke(_enum_);
            if (fieldValue.equals(value))
                return _enum_;
        }
        throw new Exception("No found specified Enum.");
    }

    /**
     * 根据code值获取枚举类
     * @param enumClz
     * @param code
     * @return
     * @throws Exception
     */
    public static <T> T getEnumByCode(Class<T> enumClz, String code) throws Exception {
        return getEnumByField(enumClz, "code", code);
    }

    /**
     * 根据name值获取枚举类
     * @param enumClz
     * @param name
     * @return
     * @throws Exception
     */
    public static <T> T getEnumByName(Class<T> enumClz, String name) throws Exception {
        return getEnumByField(enumClz, "name", name);
    }

    /**
     * 根据value值获取枚举类
     * @param enumClz
     * @param value
     * @return
     * @throws Exception
     */
    public static <T> T getEnumByValue(Class<T> enumClz, Integer value) throws Exception {
        return getEnumByField(enumClz, "value", value);
    }

    //--------------------------------------------------------------------------------------------------------------------

    /*public static <T> String fmtInfo(T val, String prefix) {
        if (val == null) return "";
        Class<?> valueClass = val.getClass();
        StringBuffer info = new StringBuffer();
        info.append("Class{").append(valueClass).append("}\r\n");

        prefix = " - " + (chkInvalid(prefix) ? "" : prefix);

        if (List.class.isAssignableFrom(valueClass)) {
            List<T> vals = (List<T>) val;
            for (T vo : vals) {
                info.append(prefix).append(vo).append("\r\n");
            }
        } else if (PageInfo.class.isAssignableFrom(valueClass)) {
            PageInfo<T> page = (PageInfo<T>) val;
            info.append(prefix).append(page).append("\r\n");
            info.append(fmtInfo(page.getList(), prefix)).append("\r\n");
        } else {
            info.append(val);
        }

        return info.toString();
    }*/
    /**
     * 获取本机Ip
     *  yushuai
     *  通过 获取系统所有的networkInterface网络接口 然后遍历 每个网络下的InterfaceAddress组。
     *  获得符合 <code>InetAddress instanceof Inet4Address</code> 条件的一个IpV4地址
     * @return
     */
    /*@SuppressWarnings("rawtypes")
    public static String localIp(){
        String ip = null;
        Enumeration allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
                for (InterfaceAddress add : InterfaceAddress) {
                    InetAddress Ip = add.getAddress();
                    if (Ip != null && Ip instanceof Inet4Address) {
                        ip = Ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            logger.error("获取本机ip地址失败....call yushuai");
        }
        return ip;
    }*/
    //--------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        int status = 0, level;

        status |=  (int) Math.pow(2, 3);
        System.out.println(status);

        status |=  (int) Math.pow(2, 6);
        System.out.println(status);

        status &= ~(int) Math.pow(2, 6);
        System.out.println(status);

        status |=  (int) Math.pow(2, 9);
        System.out.println(status);

        status |=  (int) Math.pow(2, 3);
        System.out.println(status);

        status &= ~(int) Math.pow(2, 6);
        System.out.println(status);

        System.out.println("-------------------------------------------------------------------------------------------");
        level = 3;
        System.out.println("hasStatus(" + status + ", 2^" + level + ") - " + hasStatus(status, (int) Math.pow(2, level)));
        level = 6;
        System.out.println("hasStatus(" + status + ", 2^" + level + ") - " + hasStatus(status, (int) Math.pow(2, level)));

    }
}