/*
 *
 * Author : Bernie
 *
 * CrtDate: 2014-10-08
 *
 * Desc   :   create entity according to a table
 *
 */
package com.w2g.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.LinkedHashMap;


/**
 * @author KevinSun
 *
 */
public class POGenUtil {

    public static String ISAUTOINCREMENT="isAutoIncrement";
    @SuppressWarnings("rawtypes")
	public static LinkedHashMap<String, Class> getResultSetMetaData(String jdbcDriver, ResultSet rs) throws Exception {
        LinkedHashMap<String, Class> maps = new LinkedHashMap<String, Class>();
        ResultSetMetaData rsmd = rs.getMetaData();
        String colName = null;
        Class cls = null;
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            colName = rsmd.getColumnName(i);
            cls = getJavaType(rsmd.getColumnName(i), rsmd.getColumnType(i), rsmd.getPrecision(i), rsmd.getScale(i));
            if(rsmd.isAutoIncrement(i)){
                maps.put(colName+ISAUTOINCREMENT, cls);
            }else{
                maps.put(colName, cls);
            }

            System.out.println(rsmd.getColumnName(i) + "\t" + rsmd.getColumnTypeName(i) + "\t" + rsmd.getPrecision(i)
                    + "\t" + rsmd.getScale(i));
        }

        return maps;
    }

    /**
     * 根据列的类型和colPrecision、colScale的值来获取此列对应的java对象，并返回。<br>
     * 于2009-05-11 15:12 添加clob、blob对象的支持，并修改BIGINT类型转换为java.lang.Long类型;<br>
     * 同时将对java.sql.Types.DECIMAL和java.sql.Types.NUMERIC类型的处理提取到方法中。
     * @param colName
     * @param colType
     * @param colPrecision
     * @param colScale
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	public static Class getJavaType(String colName, int colType,int colPrecision, int colScale) throws Exception {
        Class cls = null;
        String nullline = "";
        switch (colType) {
            case Types.ARRAY:nullline="ARRAY";
                break;
            case Types.BIGINT:
                cls = Long.class;
                break;
            case Types.BINARY:nullline="BINARY";
                break;
            case Types.BIT:nullline="BIT";
                cls = Boolean.class;
                break;
            case Types.BLOB:
                cls = java.sql.Blob.class;
                break;
            case Types.BOOLEAN:
                cls = Boolean.class;
                break;
            case Types.CHAR:
                cls = String.class;
                break;
            case Types.CLOB:
                cls = java.sql.Clob.class;
                break;
            case Types.DATALINK:nullline="DATALINK";
                break;
            case Types.DATE:
                cls = java.util.Date.class;
                break;
            case Types.DECIMAL:
                cls = getClassByPrecisionAndScale(colPrecision, colScale);
                break;
            case Types.DISTINCT:nullline="DISTINCT";
                break;
            case Types.DOUBLE:
                cls = Double.class;
                break;
            case Types.FLOAT:
                cls = Float.class;
                break;
            case Types.INTEGER:
                cls = Integer.class;
                break;
            case Types.JAVA_OBJECT:nullline="JAVA_OBJECT";
                break;
            case Types.LONGVARBINARY:nullline="LONGVARBINARY";
                break;
            case Types.LONGVARCHAR:nullline="LONGVARCHAR";
                cls = String.class;
                break;
            case Types.NULL:nullline="NULL";
                break;
            case Types.NUMERIC:
                cls = getClassByPrecisionAndScale(colPrecision, colScale);
                break;
            case Types.OTHER:nullline="OTHER";
                break;
            case Types.REAL:
                cls = Float.class;
                break;
            case Types.REF:nullline="REF";
                break;
            case Types.SMALLINT:
                cls = Integer.class;
                break;
            case Types.STRUCT:nullline="STRUCT";
                break;
            case Types.TIME:nullline="TIME";
                break;
            case Types.TIMESTAMP:
                cls = java.util.Date.class;
                break;
            case Types.TINYINT:
                cls = Integer.class;
                break;
            case Types.VARBINARY:nullline="VARBINARY";
                break;
            case Types.VARCHAR:
                cls = String.class;
                break;
            default :
                cls = String.class;
        }

        if (cls != null) {
            return cls;
        } else {
            throw new Exception("Not supported column type! colName=" + colName
                    + " type=" + colType + " precision=" + colPrecision
                    + " Scale=" + colScale +"   "+nullline);
        }
    }
    /**
     * 根据number(p,s),decimal(p,s),numeric(p,s)的p和s的值，安装po的对象转换规则转换成相应
     * <br>的对象，并返回此对象的Class实例。
     * @param colPrecision - precision
     * @param colScale - scale
     * @return
     */
    @SuppressWarnings("rawtypes")
	private static Class getClassByPrecisionAndScale(int colPrecision, int colScale){
        Class cls = null;
        if (colScale == Constants.MinScale && colPrecision >= Constants.MinPrecision) {// number(m);
            if (colPrecision <= Constants.IntegerMaxPrecision) {
                return Integer.class;
            }
            if (colPrecision > Constants.LongMinPrecision && colPrecision <= Constants.LongMaxPrecision) {
                return Long.class;
            }
        }
        if (colScale > Constants.MinScale && colPrecision > Constants.MinPrecision) { // number(m,n)
            if (colPrecision - colScale  < Constants.FloatMaxPRS && colScale < Constants.FloatMaxScale) {
                return Float.class;
            } else {
                return Double.class;
            }
        }
        if(colPrecision == Constants.MinPrecision && colScale == -127){//处理orcale 10g number as alias Name
            return BigDecimal.class;
        }
        return cls;
    }

    public static <F,T>void POCopy(F from,T to) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
        for(Field field:from.getClass().getDeclaredFields()){
            Field toField = to.getClass().getDeclaredField(field.getName());
            if(toField!=null){
                toField.setAccessible(true);
                toField.set(to, field.get(from));
            }
        }
    }
}
