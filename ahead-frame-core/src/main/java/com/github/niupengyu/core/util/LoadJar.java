package com.github.niupengyu.core.util;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadJar {

    public void loadJar(String jarPath){
        File jarFile=new File(jarPath);
        Method method = null;
        boolean accessible=false;
        try{
            method = URLClassLoader.class
                    .getDeclaredMethod("addURL", URL.class);
            accessible = method.isAccessible();
            method.setAccessible(true);
            URLClassLoader classLoader=(URLClassLoader)ClassLoader.getSystemClassLoader();
            URL url = jarFile.toURI().toURL();
            method.invoke(classLoader,url);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            method.setAccessible(accessible);
        }
    }

    public static void main(String[] args) {

    }

}
