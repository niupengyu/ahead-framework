package com.github.niupengyu.core.init;


import com.github.niupengyu.core.util.Content;
import com.github.niupengyu.core.util.FileUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 *
 * 文件名: SysinitBean.java
 * 包路径: cn.newsframework.sys.Initializer
 * 创建描述
 *        创建人：牛鹏宇
 *        创建日期：2013-8-6 上午09:22:43
 *        内容描述：
 * 修改描述
 *        修改人：
 *        修改日期：2013-8-6 上午09:22:43
 *        修改内容:
 * 版本: V1.0
 */
@Component
public class BaseSysinitBean extends ApplicationObjectSupport implements InitializingBean {


	private static ApplicationContext ac1;

	@Autowired
	private Environment environment;

	@Override
	public void afterPropertiesSet() throws Exception {
		ac1=this.getApplicationContext();
	}

	public static <T> T getBean(String beanName){
		Object t=ac1.getBean(beanName);
		return t==null?null:(T)t;
	}

	public static <T> T getBean(Class<T> className){
		return (T)ac1.getBean(className);
	}

	public static String getValue(String key){
		Environment environment = ac1.getBean(Environment.class);
		return environment.getProperty(key);
	}

	public static String getValue(String key,String dev){
		Environment environment = ac1.getBean(Environment.class);
		return environment.getProperty(key,dev);
	}

	public static <T> T getValue(String key,Class<T> clazz){
		Environment environment = ac1.getBean(Environment.class);
		return (T)environment.getProperty(key,clazz);
	}

	public static <T> T getValue(String key,Class<T> clazz,T defaultV){
		Environment environment = ac1.getBean(Environment.class);
		return (T)environment.getProperty(key,clazz,defaultV);
	}

	public static String diskFilePath(String subPath)
	{
		String diskPath= getValue("news.file-config.diskPath");
		String path=diskPath+ Content.BACKSLASH+subPath;
		return FileUtil.mkdirs(path);
	}

	public static String diskFilePath()
	{
		String diskPath= getValue("news.file-config.diskPath");
		//String path=diskPath+"/generate";
		FileUtil.mkdirs1(diskPath);
		return diskPath;
	}

	public static ApplicationContext getAc1() {
		return ac1;
	}
}

