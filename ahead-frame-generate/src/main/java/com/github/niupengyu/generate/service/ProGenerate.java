package com.github.niupengyu.generate.service;

import com.github.niupengyu.core.util.FileUtil;
import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.generate.util.GenUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ProGenerate {

    static Configuration cfg =null;

    static String groupId;

    static String packageName;

    static String version;

    static boolean tomcat;

    static String path;

    static String projectPath;

    static String name;

    public static void exe() throws Exception {
        String group="com.test.df.";
        path="d:\\data\\project";
        String projectName="guiyjz";
        boolean webapp=true;
        String moduleName="evaluation2";
        tomcat=true;

        //String groupId;
        //String packageName;
        if(StringUtil.isNull(moduleName)){
            name=projectName;
            groupId=projectName;
            packageName=projectName;
            version=projectName+".version";
        }else{
            name=projectName+"-"+moduleName;
            groupId=group+projectName+"."+moduleName;
            version=projectName+"."+moduleName+".version";
            packageName=projectName+"\\"+moduleName;
        }
        projectPath=new StringBuilder()
                .append(path)
                .append("\\")
                .append(name).toString();
        File file=new File(projectPath);

        if(!file.isDirectory()){
            file.mkdirs();
        }

        String projectPom=projectPath+"\\pom.xml";

        Map<String,String> map=new HashMap<>();
        map.put("groupId",groupId);
        map.put("name",name);
        map.put("version","${"+version+"}");
        map.put("versionName",version);
        //map.put("","");
        cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setDirectoryForTemplateLoading(new File("D:\\data\\project\\tem"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        Template template = cfg.getTemplate("pom.ftl");
        File docFile = new File(projectPom);
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
        // step6 输出文件
        template.process(map, out);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^User.java 文件创建成功 !");
        FileUtil.close(out);
        out=null;
        String[] modules=new String[]{"beans","common","dao","service","web"};
        for(String mo:modules){
            genMo(mo,name,projectPath,packageName,map,cfg);
        }
        genStarter(name,projectPath,packageName,map,cfg,webapp);
        //"starter"
        genWebJava();
    }

    private static void genWebJava() throws Exception {
        String[] mos={"web"};
        Map<String,String[]> java=new HashMap<>();
        java.put("web",new String[]{"TestRestController","TestPageController"});
        java.put("dao",new String[]{"TestDao"});
        java.put("service",new String[]{"TestService"});
        java.put("beans",new String[]{"Test"});
        for(String mo:mos){
            String packagePath=moClassPath(mo);
            String pack=groupId+"."+mo;
            String tempJava=packagePath+"\\Temp.java";
            Map<String,String> map=new HashMap<>();
            map.put("pack",pack);
            map.put("groupId",groupId);
            GenUtil.genFile(cfg,tempJava,"temp",map);

            String sysPackagePath=packagePath+"\\sys";
            //String sysPackage=groupId+"web."+mo;
            String[] j=java.get(mo);
            for(String n:j){
                String path=sysPackagePath+"\\"+n+".java";
                GenUtil.genFile(cfg,path,"java/"+n,map);
            }
        }


    }

    private static String moPath(String mo){
        return projectPath+"\\"+moduleName(mo);
    }

    private static String moClassPath(String mo){
        return moPath(mo)+"\\src\\main\\java\\com\\test\\df\\"+packageName+"\\"+mo;
    }

    private static String moduleName(String mo){
        return new StringBuilder()
                .append(name)
                .append("-")
                .append(mo).toString();
    }

    private static void genStarter(String name, String projectPath, String packageName,
                                   Map<String, String> map, Configuration cfg,boolean webapp) throws Exception {
        String mo="zstarter";
        genMo(mo,name,projectPath,packageName,map,cfg);
        String moduleNames=moduleName(mo);
        String mBase=projectPath+"\\"+""+moduleNames;


        String packgetPath=mBase+"\\src\\main\\java\\com\\test\\df\\"+packageName+"\\"+mo;
        Map<String,String> startMap=new HashMap<>();

        startMap.put("basePath",groupId);
        startMap.put("packagePath",groupId+"."+mo);

        GenUtil.genFile(cfg,packgetPath+"\\Starter.java","starterClass",startMap);

        String xmlBase;

        String logbacks;

        //String mJavaFile=mBase+"\\src\\main\\resources";
        String resourceDir;
        if(tomcat){
            resourceDir=mBase+"\\src\\main\\resources";
            xmlBase="mybatis";
            logbacks="../logbacks";
        }else{
            resourceDir=mBase+"\\config";
            xmlBase="file:config/mybatis";
            logbacks="logbacks";
        }
        FileUtil.mkdirs1(resourceDir);


        String application=resourceDir+"\\application.yml";
        String applicationDev=resourceDir+"\\application-dev.yml";
        String applicationTest=resourceDir+"\\application-test.yml";
        String applicationPro=resourceDir+"\\application-pro.yml";
        String log4j=resourceDir+"\\log4j.xml";
        String mybatisDir=resourceDir+"\\mybatis";
        Map<String,String> resourceMap=new HashMap<>();
        resourceMap.put("groupId",groupId);
        resourceMap.put("logbacks",logbacks);
        resourceMap.put("xmlBase",xmlBase);
        GenUtil.genFile(cfg,application,"application",resourceMap);
        GenUtil.genFile(cfg,applicationDev,"application-temp",resourceMap);
        GenUtil.genFile(cfg,applicationTest,"application-temp",resourceMap);
        GenUtil.genFile(cfg,applicationPro,"application-temp",resourceMap);
        GenUtil.genFile(cfg,log4j,"log4j",resourceMap);
        FileUtil.mkdirs(mybatisDir);

        if(webapp){
            String webappFile=mBase+"\\src\\main\\webapp";
            FileUtil.mkdirs1(webappFile);
            String pagePath=webappFile+"\\pages";
            String staticPath=webappFile+"\\static";
            String webPath=webappFile+"\\WEB-INF";
            String indexPath=webappFile+"\\index.jsp";
            String jsPath=staticPath+"\\js";
            String cssPath=staticPath+"\\css";
            String imagePath=staticPath+"\\image";
            String jspPath=pagePath+"\\jsp";
            String templatesPath=pagePath+"\\templates";
            FileUtil.mkdirs1(webPath);
            FileUtil.mkdirs1(indexPath);
            FileUtil.mkdirs1(jsPath);
            FileUtil.mkdirs1(cssPath);
            FileUtil.mkdirs1(imagePath);
            FileUtil.mkdirs1(jspPath);
            FileUtil.mkdirs1(templatesPath);
            String webXml=webPath+"\\web.xml";
            GenUtil.genFile(cfg,webXml,"webxml",new HashMap());


        }
    }

    private static void genMo(String mo,String name,String projectPath,String packageName,
                              Map map,Configuration cfg) throws Exception {
        String moduleNames=new StringBuilder()
                .append(name)
                .append("-")
                .append(mo).toString();
        String mBase=moPath(mo);
        String mJavaFile=moClassPath(mo);
        String mTestFile=mBase+"\\src\\test";
        String mTargetFile=mBase+"\\target";
        String mPomFile=mBase+"\\pom.xml";
        FileUtil.mkdirs1(mJavaFile);
        FileUtil.mkdirs1(mTestFile);
        FileUtil.mkdirs1(mTargetFile);
        //Map<String,String> map1=new HashMap<>();
        map.put("moduleNames",moduleNames);
        GenUtil.genFile(cfg,mPomFile,mo,map);
    }

}
