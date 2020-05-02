package com.github.niupengyu.generate.util;

import com.github.niupengyu.core.util.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GenUtil {
    /*public static void gen(Configuration cfg, String mPomFile, String mo, Map map) throws Exception {
        Template template = cfg.getTemplate(mo+".ftl");
        File docFile = new File(mPomFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
        // step6 输出文件
        template.process(map, out);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^"+mo+" 文件创建成功 !");
        FileUtil.close(out);
        out=null;
    }*/

    public static void genFile(Configuration cfg,  String path,String temp, Map map) throws Exception {
        Template template = cfg.getTemplate(temp+".ftl");
        File docFile = new File(path);
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
        // step6 输出文件
        template.process(map, out);
        //System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^"+path+" 文件创建成功 !");
        FileUtil.close(out);
        out=null;
    }
}
