package com.github.niupengyu.core.bean;

import com.github.niupengyu.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ReplaceMap {

    private String[] keys;

    public ReplaceMap(String... keys){
        this.keys=keys;
    }

    private static final Logger logger= LoggerFactory.getLogger(ReplaceMap.class);


    public static List<Map<String,Object>> replaceSet(List<Map<String,Object>> datas,String ... keys){
        if(datas==null||datas.isEmpty()){
            return datas;
        }
        int source=datas.size();
        Map<String,String> map=new HashMap<>();
        List<Map<String,Object>> newData=new ArrayList<>();
        for(int i=datas.size()-1;i>=0;i--){
            Map<String,Object> data=datas.get(i);
            String uid="";
            for(int j=0;j<keys.length;j++){
                String key=keys[j];
                uid =uid + StringUtil.valueOf(data.get(key));
            }
            if(!map.containsKey(uid)){
                newData.add(data);
                map.put(uid,uid);
            }
        }
        logger.info("replace source ["+source+"] real ["+ newData.size() +"]");
        return newData;
    }

    public static void main(String[] args) {
        List<Map<String,Object>> list=new ArrayList<>();

        Map<String,Object> map=new HashMap<>();
        map.put("id",111);
        map.put("name","222");
        map.put("age",System.currentTimeMillis());
        list.add(map);

        Map<String,Object> map1=new HashMap<>();
        map1.put("id",222);
        map1.put("name","222");
        map1.put("age",System.currentTimeMillis());
        list.add(map1);

        Map<String,Object> map2=new HashMap<>();
        map2.put("id",333);
        map2.put("name","222");
        map2.put("age",System.currentTimeMillis());
        list.add(map2);

        Map<String,Object> map3=new HashMap<>();
        map3.put("id",222);
        map3.put("name","222");
        map3.put("age",System.currentTimeMillis());
        list.add(map3);

        Map<String,Object> map4=new HashMap<>();
        map4.put("id",333);
        map4.put("name","222");
        map4.put("age",System.currentTimeMillis());
        list.add(map4);

        System.out.println(replaceSet(list,"id"));
        //System.out.println(map.equals(map1));
    }
}
