package com.github.niupengyu.commons.http;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Param {

    public Param(String name, String value) {
        this.name=name;
        this.value=value;
    }
    public Param(String name, File value) {
        this.name=name;
        this.file=value;
    }

    private List<Param> params;

    private List<Param> fileParams;

    public void addFileParam(String name, File value){
        fileParams.add(new Param(name,value));
    }

    public void addParam(String name, String value){
        params.add(new Param(name,value));
    }

    public void addParam(String ... str){
        params.add(new Param(name,value));
    }

    public List<Param> getParams() {
        return params;
    }

    public List<Param> getFileParams() {
        return fileParams;
    }

    public static List<NameValuePair> postParams(String ... str){
        int num=str.length/2;
        List<NameValuePair> list = new ArrayList<>();
        for(int i=0;i<num;i++){
            list.add(new BasicNameValuePair(str[i], str[i+1]));
        }
        return list;
    }

    public static List<Param> getParams(String ... str){
        int num=str.length/2;
        List<Param> list = new ArrayList<>();
        for(int i=0;i<num;i++){
            list.add(new Param(str[i], str[i+1]));
        }
        return list;
    }



    private String name;

    private String value;

    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
