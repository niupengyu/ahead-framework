/**
 * 文件名: StringUtil.java
 * 包路径: cn.news.util.string
 * 创建描述
 *
 * @createPerson：牛鹏宇
 * @createDate：Jan 8, 2013 12:08:20 PM
 * 内容描述：
 * 修改描述
 * @updatePerson：牛鹏宇
 * @updateDate：Jan 8, 2013 12:08:20 PM
 * 修改内容:
 * 版本: V1.0
 */
package com.github.niupengyu.core.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类:  <code> StringUtil </code>
 * 功能描述: String字符串操作工具栏
 * 创建人: 牛鹏宇
 * 创建日期: Apr 1, 2013 7:21:47 PM
 * 开发环境: JDK7.0
 */
public class StringUtil implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private static final String reg = "\\[.*\\d+.*\\]";

    /**
     * 功能描述:传入一个对象判断是否为空
     * @param obj
     * @return boolean为空返回true
     */
    public static boolean isNull(Object obj) {
        boolean flag = false;
        if (obj == null || "".equals(obj)) {
            flag = true;
        }
        return flag;
    }

    public static boolean notNull(Object obj) {
        boolean flag = false;
        if (obj != null && !"".equals(obj)) {
            flag = true;
        }
        return flag;
    }

    public static boolean setIsNull(Set<?> mains) {
        if (isNull(mains)) {
            return true;
        }
        if (mains.size() < 1 || mains.isEmpty()) {

            return true;
        }
        return false;
    }

    public static boolean mapIsNull(Map<?, ?> mapSet) {
        if (isNull(mapSet) || mapSet.size() < 1 || mapSet.isEmpty()) {
            return true;
        }
        return false;
    }

    public static <T> T mapValue(Map<?, ?> mapSet,String key){
        if(mapSet!=null&& mapSet.containsKey(key)){
            return (T) mapSet.get(key);
        }
        return null;
    }

    public static String mapValueString(Map<?, ?> mapSet,String key){
        if(mapSet!=null&& mapSet.containsKey(key)){
            return StringUtil.valueOf(mapSet.get(key));
        }
        return "";
    }

    public static boolean listIsNull(List<?> mains) {
        if (isNull(mains)) {
            return true;
        }
        if (mains.size() < 1 || mains.isEmpty()) {

            return true;
        }
        return false;
    }

    public static boolean arrNotNull(Object[] mains) {
        return mains != null && mains.length > 0;
    }

    public static boolean isNull(String[] strs) {
        if (strs == null || strs.length < 1) {
            return true;
        }
        return false;
    }

    public static String[] split(String str) {
        String[] strs = str.split(" ");
        List<String> sets = new ArrayList<>();
        for (String s : strs) {
            if (" ".equals(s)) {
                continue;
            } else {
                sets.add(s);
            }
        }
        return sets.toArray(new String[sets.size()]);
    }

    public static boolean mapNotNull(Map map) {
        return map != null && !map.isEmpty();
    }

    public static boolean hasText(String text) {
        return !StringUtil.isNull(text.trim());
    }

    public static String valueOf(String value) {
        return (isNull(value) ? "" : value);
    }

    public static String valueOf(Integer value) {

        return (isNull(value) ? "" : String.valueOf(value));
    }

    public static String valueOf(Object value) {
        return (isNull(value) ? "" : String.valueOf(value));
    }

    public static boolean matchArr(String key) {
        Pattern pattern = Pattern.compile(reg);
        Matcher isNum = pattern.matcher(key);
        return isNum.matches();
    }

    public static <T> T valueOf(String value, String defaults, Class<T> type) {
        if (StringUtil.isNull(value)) {
            return type.cast(defaults);
        }
        return type.cast(value);
    }

    public static String valueOf(String value, String defaults) {
        if (StringUtil.isNull(value)) {
            return valueOf(defaults);
        }
        return valueOf(value);
    }

    public static <T> T valueOf(T ... values) {
        for(T value:values){
            if (StringUtil.notNull(value)) {
                return value;
            }
        }
        return null;
    }

    public static String valueOf(Object value, String defaults) {
        if (StringUtil.isNull(value)) {
            return valueOf(defaults);
        }
        return valueOf(value);
    }

    public static String valueOf(Object value, Object defaults) {
        if (StringUtil.isNull(value)) {
            return valueOf(defaults);
        }
        return valueOf(value);
    }

   /* public static Integer valueOf(Integer value, Integer defaults) {
        if (StringUtil.isNull(value)) {
            return defaults;
        }
        return value;
    }

    public static Long valueOf(Long value, Long defaults) {
        if (StringUtil.isNull(value)) {
            return defaults;
        }
        return value;
    }

    public static Boolean valueOf(Boolean value, Boolean defaults) {
        if (StringUtil.isNull(value)) {
            return defaults;
        }
        return value;
    }*/

    public static Integer integerValueOf(Integer value, Integer defaults) {
        if (value==null) {
            return defaults;
        }
        return value;
    }

    public static Integer integerValueOf(String value, Integer defaults) {
        if (StringUtil.isNull(value)) {
            return defaults;
        }
        return Integer.parseInt(value);
    }

    public static Double doubleValueOf(Double value, Double defaults) {
        if (value==null) {
            return defaults;
        }
        return value;
    }

    public static Double doubleValueOf(String value, Double defaults) {
        if (StringUtil.isNull(value)) {
            return defaults;
        }
        return Double.parseDouble(value);
    }



    public static Integer integerValueOf(Integer value,Integer value1, Integer defaults) {
        if (value!=null) {
            return value;
        }
        if (value1!=null) {
            return value1;
        }
        return defaults;
    }

    public static Long longValueOf(Long value, Long defaults) {
        if (value==null) {
            return defaults;
        }
        return value;
    }

    public static <T> T objectValueOf(T value, T defaults) {

        return value==null?defaults:value;
    }

    public static Long longValueOf(Long value,Long value1, Long defaults) {
        if (value!=null) {
            return value;
        }
        if (value1!=null) {
            return value1;
        }
        return defaults;
    }

    public static Boolean booleanValueOf(Boolean value, Boolean defaults) {
        if (value==null) {
            return defaults;
        }
        return value;
    }

    public static Boolean booleanValueOf(String value, boolean defaults) {
        if (StringUtil.isNull(value)) {
            return defaults;
        }
        return Boolean.valueOf(value);
    }

    public static Boolean booleanValueOf(Object value, Boolean defaults) {
        if (value==null) {
            return defaults;
        }
        return Boolean.class.cast(value);
    }

    public static Boolean booleanValueOf(Boolean value,Boolean value1, Boolean defaults) {
        if (value!=null) {
            return value;
        }
        if (value1!=null) {
            return value1;
        }
        return defaults;
    }

    public static String subString(String str, int length) {
        int strLength = str.length();
        if (strLength > length) {
            return str.substring(0, length);
        }
        return str;
    }

    public static String[] valueOf(String[] mappersXml, String[] mappersXml1) {
        if (arrNotNull(mappersXml)) {
            return mappersXml;
        }
        return mappersXml;
    }

    public static List<String> valueOf(List<String> mappers, List<String> mappers1) {
        if (listIsNull(mappers)) {
            return mappers1;
        }
        return mappers;
    }

    public static Map<String,Object> mapValueOf(Map<String, Object> prop, Map<String, Object> prop1) {
        if(prop==null||prop.isEmpty()){
            return prop;
        }
        return prop1;
    }

    public static String append(Object ...strings) {
        StringBuilder sb=new StringBuilder();
        for(Object s:strings){
            sb.append(s);
        }
        return sb.toString();
    }

    public static String join(int num,String str,String sep){
        StringBuilder sb=new StringBuilder();
        int size=num-1;
        for(int i=0;i<size;i++){
            sb.append(str).append(sep);
        }
        sb.append(str);
        return sb.toString();
    }

    /**
     * ${xxx} 替换为参数
     * @param content
     * @param kvs
     * @return
     */
    private static String parse(String content, Map<String,String> kvs){
        Pattern p = Pattern.compile("(\\$\\{)([\\w]+)(\\})");
        Matcher m = p.matcher(content);
        StringBuffer sr = new StringBuffer();
        while(m.find()){
            String group = m.group();
            m.appendReplacement(sr, String.valueOf(kvs.get(group)));
        }
        m.appendTail(sr);
        return sr.toString();
    }
}


