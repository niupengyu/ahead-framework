package com.github.niupengyu.jdbc.util;

import com.github.niupengyu.core.filter.FileFilterForName;
import com.github.niupengyu.core.util.Content;
import com.github.niupengyu.core.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XmlResources {


//    private String basepath;

    private List<Resource> resources=new ArrayList<>();

    private static final Logger logger= LoggerFactory.getLogger(XmlResources.class);

    public XmlResources(){

    }

    /*public XmlResources(String[] xmlPath){

    }*/

    private void read(String basepath,String[] xmlpaths){
        ResourceLoader loader = new DefaultResourceLoader();
        logger.debug("basepath "+basepath);
        logger.debug(Arrays.toString(xmlpaths));
        if(xmlpaths!=null){
            for(String xmlpath:xmlpaths){
                String path=basepath+xmlpath;
                File file=new File(path);
                FilenameFilter filenameFilter=new FileFilterForName("xml");
                File[] files=file.listFiles(filenameFilter);
                if(files!=null){
                    int length=files.length;
                    for(int i=0;i<length;i++){
                        File xml=files[i];
                        String filePath=xmlpath+Content.BACKSLASH+xml.getName();
                        Resource resource = loader.getResource(filePath);
                        resources.add(resource);
                    }
                }
            }
        }
    }

    public Resource[] resources(String[] xmlpaths) {
        String basepath= FileUtil.getResourcesPath(XmlResources.class);
        logger.debug("basepath "+basepath+"-->"+xmlpaths);
        read(basepath,xmlpaths);
        Resource[] array = new Resource[resources.size()];
        return resources.toArray(array);
    }

    public Resource[] resourcesAll(String root){
        String basepath= FileUtil.getResourcesPath(XmlResources.class);
        logger.debug("basepath "+basepath+root+"]");
        File file=new File(basepath+root);
        file(file,".xml",basepath,root);
        Resource[] array = new Resource[resources.size()];
        return resources.toArray(array);
    }


    public void file(File file,String postfix,String basepath,String root){
        ResourceLoader loader = new DefaultResourceLoader();
        if(file.isDirectory()){
            File[] files=file.listFiles();
            logger.debug("file "+file.getPath());
            int filesLength=files.length;
            for(int i=0;i<filesLength;i++){
                File chil=files[i];
                logger.debug("chil "+chil.getPath());
                if(chil.isDirectory()){
                    root=root+Content.BACKSLASH+chil.getName();
                    logger.debug("next "+root);
                    file(chil,postfix,basepath,root);
                }else{
                    if(chil.getName().endsWith(postfix)){
                        logger.debug("root "+root);
                        String path=chil.getPath();
//                        String repath=root+Content.BACKSLASH+chil.getName();
                        String repath=path.substring(path.indexOf("mybatis"));
                        logger.info("load xml mapper file "+repath);
                        Resource resource = loader.getResource(repath);
                        resources.add(resource);
                        logger.debug("xml root"+chil.getParent());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("sdfsdfsdf/mybatis".substring("sdfsdfsdf/mybatis".indexOf("mybatis")));
    }
}
