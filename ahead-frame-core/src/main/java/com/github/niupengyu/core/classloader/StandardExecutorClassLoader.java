package com.github.niupengyu.core.classloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class StandardExecutorClassLoader extends URLClassLoader {
    //private final static String baseDir = System.getProperty("user.dir") + File.separator + "modules" + File.separator;

	private static Logger logger= LoggerFactory.getLogger(StandardExecutorClassLoader.class);

    public StandardExecutorClassLoader(String baseDir,
                                       URL[] urls, ClassLoader classLoader) {
		super(urls, classLoader);
		loadResource(baseDir);
		//init();
	}

	public void init() {
		try {
			addThisToParentClassLoader(StandardExecutorClassLoader.class
					.getClassLoader().getParent());
		} catch (Exception e) {
			logger.error("设置classloader到容器中时出现错误！",e);
		}
	}

	/**
	 * 将this替换为指定classLoader的parent ClassLoader
	 *
	 * @param classLoader
	 */
	private void addThisToParentClassLoader(ClassLoader classLoader) throws Exception {
		Field field = ClassLoader.class.getDeclaredField("parent");
		field.setAccessible(true);
		field.set(classLoader, this);
	}


	@Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
    	// 测试时可打印看一下
    	return super.loadClass(name);
    }
    
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            return super.findClass(name);
        } catch(ClassNotFoundException e) {
            return StandardExecutorClassLoader.class.getClassLoader().loadClass(name);
        }
    }
    
    private void loadResource(String baseDir) {
    	String jarPath = baseDir;
    	// 加载对应版本目录下的 Jar 包
    	tryLoadJarInDir(jarPath);
    	// 加载对应版本目录下的 lib 目录下的 Jar 包
    	tryLoadJarInDir(jarPath + File.separator + "lib");
    }
    
    private void tryLoadJarInDir(String dirPath) {
    	File dir = new File(dirPath);
    	if(!dir.isDirectory()){
			dir.mkdirs();
		}
    	// 自动加载目录下的jar包
		//System.out.println("jar path "+dir.getPath());
    	if (dir.exists() && dir.isDirectory()) {
    		for (File file : dir.listFiles()) {
    			if (file.isFile() && file.getName().endsWith(".jar")) {
    				this.addURL(file);
    				continue;
    			}
    		}
    	}
    }
    
    private void addURL(File file) {
    	try {
    		//System.out.println("getCanonicalPath "+file.getCanonicalPath());
    		super.addURL(new URL("file", null, file.getCanonicalPath()));
    	} catch (MalformedURLException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

}