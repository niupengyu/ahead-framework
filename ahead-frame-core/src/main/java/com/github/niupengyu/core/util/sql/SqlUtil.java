package com.github.niupengyu.core.util.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlUtil {

    public static String trim(String str){
        String values=values(str);

        values=values.substring(values.indexOf("("));

        StringBuffer sb=new StringBuffer(values);
        boolean delete=true;
        boolean flag=true;
        int start=0;
        while (flag){
            char c=sb.charAt(start);
            if(compare(c,'\'')){
                delete=!delete;
            }
            if((compare(c,'\n')&&delete)
                    ||(compare(c,' ')&&delete)){
                sb.deleteCharAt(start);
            }else{
                start++;
            }
            if(start>=sb.length()){
                break;
            }
        }
        return sb.toString();
    }

    private static String values(String str) {
        String ls=str.toLowerCase();
        int valueIndex=ls.indexOf("values");

        //int valueIndex= index1==-1?index2:index1;
        String values=str.substring(valueIndex);
        return values;
    }

    public static String[] columns(String str) {
        int start1=str.indexOf("(");
        int end=str.indexOf(")");

        String values=str.substring(start1+1,end);
        values=values.replaceAll("\n","")
                .replaceAll(" ","");
        return values.split(",");
    }

    public static List<String> params(String sql){
        String values=trim(sql);
        boolean flag=true;
        int startIndex=0;
        int endIndex=0;
        boolean param=false;
        boolean fh=false;
        int start=1;
        int t=0;
        boolean str=false;
        List<String> params=new ArrayList<>();
        while(flag){
            char pri=values.charAt(start-1);
            char c=values.charAt(start);
            //char next=values.charAt(start+1);
            //System.out.println(pri+" "+c+" "+param);

            if(!param&&(compare(pri,'(')||compare(pri,','))){
                if(compare(c,'\'')){
                    startIndex=start+1;
                    str=true;
                    fh=true;
                }else{
                    startIndex=start;
                    str=false;
                    fh=false;
                }
                param=true;
            }
            //System.out.println(c+"   "+startIndex+"    "+param);
            if(param){

                if(t==0&&(compare(c,',')||compare(c,')'))){
                    //System.out.println("values "+values.substring(startIndex));
                    if(compare(pri,'\'')&&fh){
                        endIndex=start-1;
                        String v=values.substring(startIndex,endIndex);
                        params.add(v);
                        param=false;
                        //System.out.println("v "+v);
                    }else if(notCompare(pri,'\'')&&!fh){
                        endIndex=start;
                        String v=values.substring(startIndex,endIndex);
                        params.add(v);
                        param=false;
                        //System.out.println("v "+v);
                    }
                }

                if(compare(c,'(')&&!str){
                    t++;
                }
                //System.out.println("t++ "+t);
                if(compare(c,')')&&!str&&t>0){
                    t--;
                }

                //System.out.println("t-- "+t);

            }

            start++;
            if(start>=values.length()){
                break;
            }
        }
        return params;
    }

    public static boolean compare(char c,char c1){
        return c==c1;
    }

    public static boolean notCompare(char c,char c1){
        return c!=c1;
    }

    public static void main(String[] args) {
        //写一个测试sql
        String insert="insert into test(col1, col2 , col3, \n col4,col5,col6,col7,col8) " +
                "values('',123,'测试(彩色',123,'',dateformat(sdfsdf,'erter',\n test(test(111,test('222')))),'测试2\n彩色',now('test',1123))";

        //返回列
        String[] str=SqlUtil.columns(insert);

        //返回参数
        List<String> list=SqlUtil.params(insert);
        System.out.println(insert);
        System.out.println(Arrays.toString(str)+" 列数量 "+str.length+" 参数数量 "+list.size());

        for(String s:list){
            System.out.println("参数 "+s);
        }

    }
}
