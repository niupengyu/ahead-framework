package com.github.niupengyu.jdbc.util;

import com.github.niupengyu.core.util.Content;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapperResource {

    private List<Resource> resources=new ArrayList<>();

    private String basePath;

    private String classpath;

    private int start;

    public MapperResource(String basePath){
        this.basePath=basePath.replaceAll("\\.","/");
        classpath=this.getClass().getResource("/").getPath();
        start=classpath.length()-1;
    }

    public Resource[] getResource(){
        File file=new File(classpath+Content.BACKSLASH+basePath);
        files(file);
        Resource[] array = new Resource[resources.size()];
        return resources.toArray(array);
    }

    public List<Resource> getResourceList(){
        File file=new File(classpath+Content.BACKSLASH+basePath);
        files(file);
        return resources;
    }

    public void files(File file){
//        FilenameFilter filenameFilter=new FileFilterForName("java");
        if(file.isDirectory()){
            File[] files=file.listFiles();
            int filesLength=files.length;
            for(int i=0;i<filesLength;i++){
                File chil=files[i];
                String fileName=chil.getName();
                if(chil.isDirectory()){
                    files(chil);
                }else{
                    if (fileName.endsWith("xml")) {
                        Resource resource =new ClassPathResource(chil.getPath().substring(start));
                        resources.add(resource);
                        this.resources.add(resource);
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        File file=new File("d:\\data\\empty");
        System.out.println(file.listFiles().length);
    }

}
