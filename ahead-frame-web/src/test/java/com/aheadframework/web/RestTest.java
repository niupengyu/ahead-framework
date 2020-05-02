package com.aheadframework.web;


import com.github.niupengyu.core.util.FileUtil;
import com.github.niupengyu.web.beans.ResponseData;
import com.github.niupengyu.web.content.ClientContent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("resttest")
public class RestTest extends ClientContent{

    @RequestMapping("test")
    public ResponseData test(){
        String str=null;
        str.length();
        return this.rdSuccess();
    }


    public static void main(String[] args) {
        System.out.println(FileUtil.convertFileSize(51700));
    }
}
