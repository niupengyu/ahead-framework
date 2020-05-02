package com.github.niupengyu.core.util;

import java.util.*;

public class Utils {
    public static Map chanceMap(Map map, String[] formats) {

        for(String format:formats){
            String[] strs=format.split(":");
            String key=strs[0];
            if(map.containsKey(key)){
                String formatStr="";
                if(strs.length==2){
                    formatStr=strs[1];
                }
                Object value=map.get(key);
                if(StringUtil.notNull(value)&&value instanceof Date){
                    Date date=Date.class.cast(value);
                    map.put(key,ThreadLocalDateUtil.formatDate(date));
                }
            }
        }
        return map;
    }

    public static Set<String> oneList(List<Map<String,Object>> list,String key){
        Set<String> set=new HashSet<>();
        for(Map<String,Object> map:list){
            set.add(StringUtil.valueOf(map.get(key)));
        }
        return set;
    }
}
