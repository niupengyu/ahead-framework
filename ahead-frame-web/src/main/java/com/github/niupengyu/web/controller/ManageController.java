package com.github.niupengyu.web.controller;

import com.github.niupengyu.web.beans.ResponseData;
import com.github.niupengyu.web.content.ClientContent;
import com.github.niupengyu.web.init.ShutDownContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("manage")
public class ManageController extends ClientContent {

    public ManageController(){
        //System.out.println("sssssssssssssss");
    }

    @Resource(name="shutDownContext")
    private ShutDownContext shutDownContext;

    @RequestMapping("shutdown")
    public ResponseData shutdown(){
        shutDownContext.showdown();
        return this.rdSuccess();
    }

}
