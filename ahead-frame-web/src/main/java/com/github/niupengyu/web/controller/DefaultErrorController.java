package com.github.niupengyu.web.controller;

import com.github.niupengyu.web.content.ContentController;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.PrintWriter;

//@Controller
//@RequestMapping("/")
public class DefaultErrorController extends ContentController implements ErrorController{

    /*@RequestMapping("/")
    public void main(PrintWriter out){
        out.write("illegal request");
    }*/

    @RequestMapping("/error")
    public void error(PrintWriter out){
        out.write("error request");
    }

    @RequestMapping("/")
    public String main(){
        System.out.println("sssssssssssssssssssss");
        return "forward:/index";
    }


    @Override
    public String getErrorPath() {
        return null;
    }
}
