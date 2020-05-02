package com.github.niupengyu.jdbc.util;

import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.jdbc.bean.DataSourceBean;
import com.github.niupengyu.jdbc.bean.DbConfig;
import org.springframework.core.io.Resource;

import java.util.List;

public class ResourcesUtil {

    public Resource[] resources(String[] xmlpaths,String xmlpath) throws Exception {
        Resource[] resources = null;
        if(StringUtil.arrNotNull(xmlpaths)){
            XmlResources xmlResources=new XmlResources();
            resources = xmlResources.resources(xmlpaths);
        }else if(StringUtil.notNull(xmlpath)){

            XmlResources xmlResources=new XmlResources();
            if(xmlpath!=null&&xmlpath.startsWith("file:")){
                xmlpath=xmlpath.substring(5);
                XmlFileSystemResources fileSystemResources=new XmlFileSystemResources(xmlpath,".xml");
                resources=fileSystemResources.resourcesAll();
            }else{
                resources = xmlResources.resourcesAll(xmlpath);
            }
        }
        return resources;
    }

    public static void main(String[] args) {
        System.out.println("file:sdfsd".substring(5));
    }

    public Resource[] resources(DataSourceBean ds, DbConfig dbConfig) throws Exception {
        String[] xmlpaths=StringUtil.valueOf(ds.getMappersXml(),dbConfig.getMappersXml());
        String xmlpath=StringUtil.valueOf(ds.getXmlBase(),dbConfig.getXmlBase());
        return resources(xmlpaths,xmlpath);
    }
}
