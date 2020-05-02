package com.aheadframework.web;

import com.github.niupengyu.web.content.ContentController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("pageTest")
public class PageTest extends ContentController{

    @RequestMapping("test")
    public String test(){


        return "content";
    }

}
