package com.github.niupengyu.jdbc.temp;

import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.jdbc.datasource.BasicDataSource;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;

public class Test {

    public static void main(String[] args) throws Exception {
        Test t=new Test();
        DataSource dataSource=t.dataSource();
        String temp="";
        Connection conn=dataSource.getConnection();
        for(int i=0;i<50;i++){
            String name="yyap_address_tree.sql.3h"+i;
            try(
                BufferedReader out=new BufferedReader(new FileReader("D:\\BaiduNetdiskDownload\\"+name));
                ){

                System.out.println("name "+name);
                String str=null;
                while((str=out.readLine())!=null){
                    boolean strstart=str.startsWith("INSERT INTO");
                    boolean strend=str.endsWith(");");
                    boolean strnull=StringUtil.isNull(str);

                    boolean tempstart=temp.startsWith("INSERT INTO");
                    boolean tempend=temp.startsWith(");");
                    boolean tempnull=!StringUtil.isNull(temp);
                    if(strstart&&strend&&!strnull){
                        conn.createStatement().execute(str);
                    }/*else if(strstart&&!strend&&!strnull&&tempnull){
                        temp=str;
                    }else if(!strstart&&strend&&!strnull&&!tempnull){
                        str=temp+str;
                        conn.createStatement().execute(str.replace(";",""));
                    }else if(strstart&&!strend&&!strnull&&!tempnull){
                        str=str+temp;
                        conn.createStatement().execute(str.replace(";",""));
                    }*/
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    public DataSource dataSource(){
        BasicDataSource dataSource=new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/app_test?characterEncoding=UTF-8");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        return dataSource;
    }

}
