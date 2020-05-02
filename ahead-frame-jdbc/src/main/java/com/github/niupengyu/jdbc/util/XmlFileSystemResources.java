package com.github.niupengyu.jdbc.util;

import com.github.niupengyu.core.filter.FileFilterForName;
import com.github.niupengyu.core.util.Content;
import com.github.niupengyu.core.util.FileUtil;
import com.github.niupengyu.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlFileSystemResources {


//    private String basepath;

    private List<Resource> resources=new ArrayList<>();

    private static final Logger logger= LoggerFactory.getLogger(XmlFileSystemResources.class);

    private String basePath;

    private String root;

    private String postfix;

    public XmlFileSystemResources(String root,String postfix) throws Exception {
        this.root=root;
        this.basePath=FileUtil.root();
        this.postfix=postfix;
    }

    public Resource[] resourcesAll(){

        File file=new File(basePath+root);
        file(file,postfix,"");
        Resource[] array = new Resource[resources.size()];
        return resources.toArray(array);
    }


    public void file(File file,String postfix,String parent){
        ResourceLoader loader = new FileSystemResourceLoader();
                //new DefaultResourceLoader();
        if(StringUtil.isNull(parent)){
            parent=root;
        }else{
            parent=parent+Content.BACKSLASH+file.getName();
        }
        if(file.isDirectory()){
            File[] files=file.listFiles();
            int filesLength=files.length;

            for(int i=0;i<filesLength;i++){
                File chil=files[i];
                if(chil.isDirectory()){
                    file(chil,postfix,parent);
                }else{
                    if(chil.getName().endsWith(postfix)){
                        String repath=parent+Content.BACKSLASH+chil.getName();
                        logger.info("load xml mapper file "+repath);
                        Resource resource = loader.getResource(repath);
                                //loader.getResource(repath);
                        resources.add(resource);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        XmlFileSystemResources xml=new XmlFileSystemResources("ahead-frame-demo/ahead-frame-demo-main/" +
                "config/mybatis",".xml");
        Resource[] resource=xml.resourcesAll();
        System.out.println("111111111111111111");
        for(Resource resource1:resource){
            System.out.println(resource1);
        }
    }

}
