package com.w2g.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Bernie Chen
 *
 */
@SuppressWarnings("unchecked")
public class POGen {

    private static final int TYPE_CLSNAME = 1;
    private static final int TYPE_ATTRNAME = 2;

    private String genPath = null;
    private String packageName_mapper = null;

    private Map<String, Map<String, String>> dbConnsMap = null;
    private Map<String, Map<String, Object>> packagesMap = null;

    private String queryString = "select * from {0}";

    //private String queryCommentString = "show create table {0}";//用于查询出每个字段的注释供swagger2使用

    private String queryString_mysql = "select * from {0} LIMIT 0 ,1";

    private String comment = "/*\n"
            + "* CreateDate : {0,date,yyyy-MM-dd HH:mm:ss}\n"
            + "* CreateBy   : vigo wu  \n */";

    // package and import
    private String headTemp = "package {0};\n\n"
            + "{1,choice,0#|1#import java.util.Date;\n}"
            + "{2,choice,0#|1#import java.sql.Blob;\n}"
            + "{3,choice,0#|1#import java.sql.Clob;\n}";

    private String attrDecl = "\tprivate {1} {0};\n";

    private String attrDecl_annotation = "\t@Column(name=\"{0}\") \n";
    private String attrDecl_Swagger2_annotation = "\t@ApiModelProperty(value=\"{0}\") \n";
    private String attrDecl_annotation_id = "\t@Id \n";
    private String attrDecl_annotation_GeneratedValue = "\t@GeneratedValue(strategy = GenerationType.AUTO) \n";
    private String attrDecl_annotation_JDBC = "\t@GeneratedValue(generator = \"JDBC\") \n";
    private String setTemp = "\tpublic void set{0}({1} {2})'{'\n"
            + "\t\tthis.{2}={2};\n" + "\t}\n";
    private String getTemp = "\tpublic {1} get{0}()'{'\n"
            + "\t\treturn this.{2};\n" + "\t}\n";

    public void loadConf(){
        loadConf("C:/Users/Me/Desktop/springbootTest/mm-entity/src/main/resources/POGenConf.xml");
    }

    public void loadConf(String path){
        try{
            SAXReader reader = new SAXReader();
            InputStream in = POGen.class.getResourceAsStream(path);
            if(in == null ){
                in = new FileInputStream(path);
            }
//          org.jaxen.JaxenException w =null;
            Document doc = reader.read(in);
            List<Node> dbConnsNodeList = doc.selectNodes("/PO_CONFIG/DB_CONNECTIONS/DB_CONNECTION");
            Node poPathNode = doc.selectSingleNode("/PO_CONFIG/PO_PATH");
            List<Node> packagesNodeList = doc.selectNodes("/PO_CONFIG/PACKAGE");
            dbConnsMap = new Hashtable<String, Map<String,String>>();
            Element connEl = null;
            for(Node node : dbConnsNodeList){
                connEl = (Element)node;
                if(connEl.attributeValue("VALID").equalsIgnoreCase("TRUE")){
                    String type = connEl.attributeValue("TYPE");
                    List<Node> plist = node.selectNodes("PROPERTY");
                    Map<String, String> propertyMap = new Hashtable<String, String>();
                    Element pEl = null;
                    for(Node pnode : plist){
                        pEl = (Element)pnode;
                        String key = pEl.attributeValue("NAME");
                        String value = pEl.getText();
                        System.out.println(key+"="+value);
                        propertyMap.put(key,value);
                    }
                    dbConnsMap.put(type, propertyMap);
                }
            }
            packagesMap = new Hashtable<String, Map<String,Object>>();
            Element packageEl = null;
            for(Node node : packagesNodeList){
                packageEl = (Element)node;
                if(packageEl.attributeValue("IS_GENERATOR").equalsIgnoreCase("TRUE")){
                    String packageName = packageEl.attributeValue("NAME");
                    packageName_mapper = packageEl.attributeValue("MAPPER_NAME");
                    List<Node> tabList = packageEl.selectNodes("TABLE");
                    Element tabEl = null;
                    Map<String, Object> tabMap = new Hashtable<String, Object>();
                    for(Node tabNode : tabList){
                        tabEl = (Element)tabNode;
                        String tabName = tabEl.attributeValue("NAME");
                        System.out.println("Table Name is "+tabName);
                        if(tabName != null && !tabName.equals("")){
                            tabMap.put(tabName, "");
                        }
                    }
                    packagesMap.put(packageName, tabMap);
                }
            }
            genPath = ((Element)poPathNode).attributeValue("PATH");
            System.out.println("genPath=================="+genPath);
        }catch(Exception e){
            System.out.println("配置读取错误，退出执行!"+e.getMessage()+","+e.getCause());
            e.printStackTrace();
            System.exit(0);
        }
    }

    private String tabName2PoName(int type, String tname) {
        byte ascii = 'Z' - 'z';
        tname = tname.toLowerCase();
        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < tname.length(); i++) {
            if (tname.charAt(i) == '_' && i + 1 < tname.length()) {
                i++;
                if (tname.charAt(i) >= 'a' && tname.charAt(i) <= 'z') {
                    sbd.append((char) (tname.charAt(i) + ascii));
                } else {
                    sbd.append(tname.charAt(i));
                }
            } else {
                sbd.append(tname.charAt(i));
            }
        }
        if (type == POGen.TYPE_CLSNAME) {
            if (sbd.charAt(0) >= 'a' && sbd.charAt(0) <= 'z')
                sbd.setCharAt(0, (char) (sbd.charAt(0) + ascii));
        }
        return sbd.toString();
    }

    private void writePOFile(String tabName, StringBuilder sbd,String pkgName) {
        try {
            String file = this.genPath + '/' + pkgName.replace('.', '/')
                    + '/';
            File dir = new File(file);
            if (!dir.exists())
                dir.mkdirs();
            String fileName = this.tabName2PoName(POGen.TYPE_CLSNAME, tabName);
            file += fileName+ ".java";

            System.out.println("Write Class File : " + file);
            FileWriter fw = new FileWriter(file);
            fw.write(sbd.toString());
            fw.close();

            genMapperFile(pkgName,fileName,packageName_mapper);


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void genMapperFile(String pkgName ,String entityName,String packageName_mapper) throws IOException{
        if(packageName_mapper==null){
            System.out.println("packageName_mapper is null");
        }else{
            System.out.println("packageName_mapper is : " +packageName_mapper);
            String file = this.genPath + '/' + packageName_mapper.replace('.', '/')
                    + '/';
            File dir = new File(file);
            String fileName = "";
            if (!dir.exists())
                dir.mkdirs();
            fileName =entityName+ "Mapper";
            file += fileName+ ".java";

            System.out.println("Write Class File : " + file);
            FileWriter fw = new FileWriter(file);
            StringBuffer Contents   = new StringBuffer();
            Contents.append("package ").append(packageName_mapper).append(";\n")
                    .append("import ").append(pkgName).append(".").append(entityName) .append(";\n")
                    .append("import com.w2g.markerMapper.CommonMapper;\n")
                    .append("public interface ").append(fileName).append(" extends CommonMapper<") .append(entityName)  .append("> {}\n");


            fw.write(Contents.toString());
            fw.close();

        }
    }

    private void genMethod(StringBuilder sbd, String attrName, Class<?> clsType) {
        try {
            String attrName1 = this.tabName2PoName(POGen.TYPE_CLSNAME, attrName);
            String attrName2 = this.tabName2PoName(POGen.TYPE_ATTRNAME,attrName);
            sbd.append(MessageFormat.format(this.setTemp, attrName1, clsType.getSimpleName(), attrName2));
            sbd.append("\n");
            sbd.append(MessageFormat.format(this.getTemp, attrName1, clsType.getSimpleName(), attrName2));
            sbd.append("\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * 重构后的生产方法----支持直接生成数据库中表的注释支持swagger2注解
     * @param tabName
     * @param infos
     * @param map
     * @param pkgName
     */
    @SuppressWarnings("rawtypes")
	public void genPOFile(String tabName, LinkedHashMap<String, Class> infos,Map<String,String> map,String pkgName) {
        try {
            int impDate = infos.containsValue(Date.class) ? 1 : 0;
            int impBlob = infos.containsValue(java.sql.Blob.class) ? 1
                    : 0;
            int impClob = infos.containsValue(java.sql.Clob.class) ? 1
                    : 0;

            StringBuilder sbd = new StringBuilder();
            sbd.append(MessageFormat.format(this.comment, new Date(), System
                    .getenv("USERNAME")));
            sbd.append("\n");
            sbd.append(MessageFormat.format(this.headTemp, pkgName,
                    impDate, impBlob, impClob));
            sbd.append("\n");
            sbd.append(MessageFormat.format(this.getClsTemp(), this.tabName2PoName(
                    POGen.TYPE_CLSNAME, tabName),tabName));
            sbd.append("\n");
            Iterator<String> ite = infos.keySet().iterator();
            String key = null;
            String key_t =null;
            int kkk  =  0 ;
            while (ite.hasNext()) {
                key = ite.next();
                System.out.println("----------------------" + key);
                if(key.indexOf(POGenUtil.ISAUTOINCREMENT)>-1){
//                    sbd.append(this.attrDecl_annotation_id);
                    sbd.append(this.attrDecl_annotation_JDBC);
                    key_t =key.replaceAll(POGenUtil.ISAUTOINCREMENT, "");
                }else{
                    key_t = key;
                }
                if( kkk == 0 ){
                    sbd.append(this.attrDecl_annotation_id);
                }
                kkk++;
                System.out.println("----------------------" + key_t);
//              if(key_t.equalsIgnoreCase("id")){
//                  sbd.append(this.attrDecl_annotation_id);
//              }

                sbd.append(MessageFormat.format(this.attrDecl_annotation,key_t));
                String comment = "";
                if(map.containsKey(key_t)){
                    comment = map.get(key_t);
                }
                sbd.append(MessageFormat.format(this.attrDecl_Swagger2_annotation,comment));//此处添加swagger2注解value值为数据库中的字段注释方便前端使用
                sbd.append(MessageFormat.format(this.attrDecl, this
                        .tabName2PoName(POGen.TYPE_ATTRNAME, key_t), infos.get(
                        key).getSimpleName()));
                sbd.append("\n");
            }
            sbd.append("\n");

            ite = infos.keySet().iterator();
            while (ite.hasNext()) {
                key = ite.next();
                if(key.indexOf(POGenUtil.ISAUTOINCREMENT)>-1){
                    //sbd.append(this.attrDecl_annotation_GeneratedValue);
                    key_t =key.replaceAll(POGenUtil.ISAUTOINCREMENT, "");
                }else{
                    key_t = key;
                }
                genMethod(sbd, key_t, infos.get(key));
            }

            sbd.append("}");

            writePOFile(tabName, sbd,pkgName);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @SuppressWarnings("rawtypes")
	public void genPOFile(String tabName, LinkedHashMap<String, Class> infos,String pkgName) {
        try {
            int impDate = infos.containsValue(Date.class) ? 1 : 0;
            int impBlob = infos.containsValue(java.sql.Blob.class) ? 1
                    : 0;
            int impClob = infos.containsValue(java.sql.Clob.class) ? 1
                    : 0;

            StringBuilder sbd = new StringBuilder();
            sbd.append(MessageFormat.format(this.comment, new Date(), System
                    .getenv("USERNAME")));
            sbd.append("\n");
            sbd.append(MessageFormat.format(this.headTemp, pkgName,
                    impDate, impBlob, impClob));
            sbd.append("\n");
            sbd.append(MessageFormat.format(this.getClsTemp(), this.tabName2PoName(
                    POGen.TYPE_CLSNAME, tabName),tabName));
            sbd.append("\n");

            Iterator<String> ite = infos.keySet().iterator();
            String key = null;
            String key_t =null;
            int kkk  =  0 ;
            while (ite.hasNext()) {
                key = ite.next();
                System.out.println("----------------------" + key);
                if(key.indexOf(POGenUtil.ISAUTOINCREMENT)>-1){
//                    sbd.append(this.attrDecl_annotation_id);
                  sbd.append(this.attrDecl_annotation_GeneratedValue);
                    key_t =key.replaceAll(POGenUtil.ISAUTOINCREMENT, "");
                }else{
                    key_t = key;
                }
                if( kkk == 0 ){
                    sbd.append(this.attrDecl_annotation_id);
                }
                kkk++;
                System.out.println("----------------------" + key_t);
//              if(key_t.equalsIgnoreCase("id")){
//                  sbd.append(this.attrDecl_annotation_id);
//              }

                sbd.append(MessageFormat.format(this.attrDecl_annotation,key_t));
                sbd.append(MessageFormat.format(this.attrDecl, this
                        .tabName2PoName(POGen.TYPE_ATTRNAME, key_t), infos.get(
                        key).getSimpleName()));
                sbd.append("\n");
            }
            sbd.append("\n");

            ite = infos.keySet().iterator();
            while (ite.hasNext()) {
                key = ite.next();
                if(key.indexOf(POGenUtil.ISAUTOINCREMENT)>-1){
                    sbd.append(this.attrDecl_annotation_GeneratedValue);
                    key_t =key.replaceAll(POGenUtil.ISAUTOINCREMENT, "");
                }else{
                    key_t = key;
                }
                genMethod(sbd, key_t, infos.get(key));
            }

            sbd.append("}");

            writePOFile(tabName, sbd,pkgName);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public String getClsTemp() {
        return "import javax.persistence.Entity; \n"
                +"import javax.persistence.Table; \n"
                +"import javax.persistence.GeneratedValue; \n"
//              +"import javax.persistence.GenerationType; \n"
                +"import javax.persistence.Id; \n"
                +"import javax.persistence.Column; \n"
                //+"import io.swagger.annotations.ApiModel; \n"
                +"import io.swagger.annotations.ApiModelProperty; \n"



                +"@Entity \n"
                +"@Table( name = \"{1}\" ) \n"
                //+"@ApiModel() \n"
                + "public class {0} '{'\n";
    }

    public void setClsTemp(String clsTemp) {
    }

    @SuppressWarnings("rawtypes")
	public void gen() throws Exception{
        Connection conn = null;
        try{
            if(!this.dbConnsMap.isEmpty()){
                for(Entry<String, Map<String, String>> entry : this.dbConnsMap.entrySet()){
                    String type = entry.getKey();
                    if(type.equalsIgnoreCase("oracle")||type.equalsIgnoreCase("db2")||type.equalsIgnoreCase("mssql")||type.equalsIgnoreCase("mysql")){
                        Map<String, String> dbConnMap = entry.getValue();
                        Class.forName(dbConnMap.get("DB_DRIVER"));
                        conn = DriverManager.getConnection(dbConnMap.get("DB_URL"), dbConnMap.get("DB_USER"), dbConnMap.get("DB_PASSWORD"));
                        Statement ste = conn.createStatement();

                        if(!this.packagesMap.isEmpty()){
                            if(type.equalsIgnoreCase("mysql")){
                                this.queryString=this.queryString_mysql;
                            }

                            for(Entry<String,Map<String, Object>> ety : this.packagesMap.entrySet()){
                                String pkgName = ety.getKey();
                                Map<String, Object> tabMap = ety.getValue();
                                for(Entry<String, Object> en : tabMap.entrySet()){
                                    System.out.println("------Gen tab : " + en.getKey() + "------");
                                    System.out.println(MessageFormat.format(this.queryString, en.getKey()));
                                    ResultSet rs = ste.executeQuery(MessageFormat.format(
                                            this.queryString, en.getKey()));
                                    //*************************************************
                                    List<String> lista = new ArrayList();
                                    lista.add(en.getKey());
                                    Map<String, String> mapp = getColumnCommentByTableName(lista,conn);//表中字段对应其注释的map
                                    //**************************************************
                                    LinkedHashMap<String, Class> infos = POGenUtil.getResultSetMetaData(
                                            dbConnMap.get("DB_DRIVER"), rs);

                                    genPOFile(en.getKey(), infos,mapp,pkgName);
                                    rs.close();
                                }
                            }
                        }
                        ste.close();
                    }else{
                        throw new Exception(type+"类型的数据库，PO暂不支持！如有需要请联系POV3的开发人员。");
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    /**
     * 获得某表中所有字段的注释
     * @param tableName
     * @return
     * @throws Exception
     */
    public static Map<String, String> getColumnCommentByTableName(List<String> tableName,Connection conn) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        Statement stmt = conn.createStatement();
        for (int i = 0; i < tableName.size(); i++) {
            String table = (String) tableName.get(i);
            ResultSet rs = stmt.executeQuery("show full columns from " + table);
            System.out.println("【"+table+"】");
//			if (rs != null && rs.next()) {
            //map.put(rs.getString("Field"), rs.getString("Comment"));
            while (rs.next()) {
//			    System.out.println("字段名称：" + rs.getString("Field") + "\t"+ "字段注释：" + rs.getString("Comment") );
                map.put(rs.getString("Field"),rs.getString("Comment"));
                System.out.println(rs.getString("Field") + "\t:\t"+  rs.getString("Comment") );
            }
//			}
        }
		return map;
    }


    public static void main(String[] args) throws Exception {
        System.out.println(args.length);
        if(args.length == 0){
            System.out.println("### 使用系统默认路径的PO配置文件<POGenConf.xml>###");
            POGen gen = new POGen();
            gen.loadConf();
            gen.gen();
            System.out.println("###########################\n\n");
        }else{
            for (int i = 0; i < args.length; i++) {
                System.out.println("### config file : " + args[i] + " ###");
                POGen gen = new POGen();
                gen.loadConf(args[i]);
                gen.gen();
                System.out.println("###########################\n\n");
            }
        }
    }

}
