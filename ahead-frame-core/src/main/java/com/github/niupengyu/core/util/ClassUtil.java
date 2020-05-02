/**  
 * 文件名: ClassUtil.java
 * 包路径: cn.newsframework.core.util
 * 创建描述  
 *        @createPerson：牛鹏宇 
 *        @createDate：Apr 10, 2013 1:00:23 PM
 *        内容描述：
 * 修改描述  
 *        @updatePerson：牛鹏宇
 *        @updateDate：Apr 10, 2013 1:00:23 PM 
 *        修改内容:
 * 版本: V1.0   
 */
package com.github.niupengyu.core.util;

import com.github.niupengyu.core.util.callback.ClassCallBackService;
import com.github.niupengyu.core.util.callback.InvokeCallback;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.persistence.Column;



public class ClassUtil {
	

	public static Method createSetState(Object obj) throws Exception{
		
		return setMethod(obj, "setState", new Class[]{Integer.class});
	}

	public static Method createSetDeleteUser(Object obj,Class<?> c) throws Exception{
		
		return setMethod(obj, "setDeleteUser", new Class[]{c});
	}

	public static Method createSetDeleteDate(Object obj) throws Exception{
		
		return setMethod(obj, "setDeleteDate", new Class[]{Date.class});
	}
	/**
	 * 功能描述:获取某实体的set方法
	 * @param obj
	 * @param setName
	 * @param c
	 * @return
	 * @throws Exception 
	 */
	public static Method setMethod(Object obj,String setName,Class<?>[] c) throws Exception{
		setName ="set"+ Character.toUpperCase(setName.charAt(0)) + setName.substring(1);
		Method setMethod;
			try{
				setMethod = obj.getClass().getMethod(setName, c);
			}catch(Exception e){
				
				throw new Exception("实体类:"+obj.getClass().getName()+"中没有找到此方法"+setName);
			}
		return setMethod;
	}

	/**
	 * 功能描述:返回实体类的某个属性
	 * @param obj
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Field getField(Object obj,String str) throws Exception {
		Field field=null;
		try{
			field=obj.getClass().getDeclaredField(str);
		}catch(Exception e){
				throw new Exception(obj.getClass().getName()+"实体类中没有找到该属性:"+str);
		}
		return field;
	}
	
	/**
	 * 功能描述:返回实体类的某个属性
	 * @param
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Field getField(Class<?> c,String str) throws Exception {
		Field field=null;
		try{
			field=c.getDeclaredField(str);
		}catch(Exception e){
				throw new Exception(c.getName()+"实体类中没有找到该属性:"+str);
		}
		return field;
	}
	
	/**
	 * 功能描述:返回实体类的某个属性的子属性
	 * @param obj
	 * @param str
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public static Field getFieldChild(Object obj,String str,String s) throws Exception {
		Field field=getField(obj, str);
		Field cfield=getField(field.getClass(),s);
		return cfield;
	}
	/**
	 * 功能描述:获取一个属性的get 方法
	 * @param c
	 * @param field
	 * @return
	 * @throws Exception
	 */
	public static Method getMethod(Class<?> c,Field field) throws Exception{
		String getName ="get"+ Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
		Method getMethod=c.getMethod(getName,new Class[]{});
		if(StringUtil.isNull(getMethod)){
			throw new Exception("没有找到此属性的get方法:"+field.getName());
		}
		return getMethod;
	}

	/**
	 * 功能描述:获取一个属性的set方法
	 * @param c
	 * @param field
	 * @return
	 * @throws Exception
	 */
	public static Method setMethod(Class<? extends Object> c, Field field) throws Exception {
		String setName ="set"+ Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
		Method setMethod=c.getMethod(setName, new Class[]{field.getType()});
		if(StringUtil.isNull(setMethod)){
			throw new Exception("没有找到此属性的set方法:"+field.getName());
		}
		return setMethod;
	}

	public static Method setMethod(Class<? extends Object> c,String name) throws Exception {
		Field field=c.getDeclaredField(name);
		String setName ="set"+ Character.toUpperCase(name.charAt(0)) + name.substring(1);
		Method setMethod=c.getMethod(setName, new Class[]{field.getType()});
		if(StringUtil.isNull(setMethod)){
			throw new Exception("没有找到此属性的set方法:"+name);
		}
		return setMethod;
	}


	/**
	 * 功能描述:获取一个对象某个方法
	 * @param
	 * @param
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	public static Method method(Class<?> c, String funcname,Class<?>[] carr) throws Exception {
		Method method=c.getMethod(funcname,carr);
		if(StringUtil.isNull(method)){
			throw new Exception("实体类中没有找到该方法:"+funcname);
		}
		
		return method;
	}
	/**
	 * 功能描述:获取属性get方法方法名
	 * @param field
	 * @return
	 */
	public static String getmethodName(Field field){
		String getName ="get"+ Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
		return getName;
	}
	/**
	 * 功能描述:获取属性set方法方法名
	 * @param field
	 * @return
	 */
	public static String setmethodName(Field field){
		String setName ="set"+ Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
		return setName;
	}

	public static Field getDeclaredField(Class<?> c, String str) throws Exception {
		Field field=null;
		try{
			field=c.getDeclaredField(str);
		}catch(Exception e){
				throw new Exception("实体类中没有找到该属性:"+c.getName()+str);
		}
		return field;
	}


	public static Method getMethod(Class<?> class1, String name) throws Exception {
		String getName ="get"+ Character.toUpperCase(name.charAt(0)) + name.substring(1);
		Method getMethod;
			try{
				getMethod = class1.getMethod(getName);
			}catch(Exception e){
				
				throw new Exception("实体类:"+class1.getName()+"中没有找到此方法"+getName);
			}
		return getMethod;
	}
	
	/**
	 * 功能描述:获取某属性的set方法
	 * @param
	 * @param setName
	 * @param c
	 * @return
	 * @throws Exception 
	 */
	public static Method setMethod(Class<?> c1,String setName,Class<?>[] c) throws Exception{
		setName ="set"+ Character.toUpperCase(setName.charAt(0)) + setName.substring(1);
		Method setMethod;
			try{
				setMethod = c1.getMethod(setName, c);
			}catch(Exception e){
				
				throw new Exception("实体类:"+c1.getName()+"中没有找到此方法"+setName);
			}
		return setMethod;
	}

	public static void invokeSetMethod(Object o1, String fname,
			Class<?>[] s, Object[] objects) throws  Exception {
		String setName ="set"+ Character.toUpperCase(fname.charAt(0)) + fname.substring(1);
		method(o1.getClass(), setName, s).invoke(o1,objects);
	}

	public static void invokeSetMethod(Object o1, String fname,
									   Class<?> s, Object object) throws  Exception {
		String setName ="set"+ Character.toUpperCase(fname.charAt(0)) + fname.substring(1);
		method(o1.getClass(), setName, new Class[]{s}).invoke(o1,new Object[]{object});
	}
	
	/**
	 * 功能描述:执行一个属性的Get方法
	 * @param obj
	 * @param
	 * @throws Exception 
	 */
	public static Object invokeGetMethod(Object obj,String funcname) throws Exception {
		String getName ="get"+ Character.toUpperCase(funcname.charAt(0)) + funcname.substring(1);
		Method m=method(obj.getClass(),getName, new Class[]{});
		Object o=null;
		try{
			o=m.invoke(obj, new Object[]{});
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("方法执行异常 类名:"+obj.getClass().getName()+"方法名:"+funcname);
		}
		return o;
	}

	public static <T> T invokeGetMethod1(Object obj,String funcname) throws Exception {
		String getName ="get"+ Character.toUpperCase(funcname.charAt(0)) + funcname.substring(1);
		Method m=method(obj.getClass(),getName, new Class[]{});
		Object o=null;
		try{
			o=m.invoke(obj, new Object[]{});
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("方法执行异常 类名:"+obj.getClass().getName()+"方法名:"+funcname);
		}
		return (T)o;
	}

	public static Object invokeGetMethodArr(Object obj, String fieldes) throws Exception {
		String[] fieldnames=fieldes.split("\\.");
		Object o=obj;
		for(int i=0;i<fieldnames.length;i++){
			if(StringUtil.isNull(o)){
				break;
			}
			o=invokeGetMethod(o, fieldnames[i]);
		}
		return o;
	}
	public static String valueOfString(Object obj,String format){
		String value=null;
		if(StringUtil.isNull(obj)){
			return value;
		}
		if(obj instanceof Date&&!StringUtil.isNull(format)){
			value = DateUtil.dateFormat(format,(Date)obj);
		}else{
			value=StringUtil.valueOf(value);
//				String.valueOf(obj);
		}
		return value;
	}
	
	/**
	 * 功能描述:@代表执行一个方法#输出数值$代表执行一个回调
	 * @param
	 * @param fieldes
	 * @param format 
	 * @return
	 * @throws Exception 
	 */
	public static String invokeValue(Object obj, String fieldes,String format, InvokeCallback invokeCallback) throws Exception
	{
		ClassCallBackService classCallBackService=new ClassCallBackService(invokeCallback);
		String[] fieldnames=fieldes.split("\\.");
		Object o=obj;
		for(String str:fieldnames){
			if(StringUtil.isNull(o)||StringUtil.isNull(str)){
				break;
			}
			switch(str.charAt(0)){
				case 64://@
					o=invokeMethodArr(o, str.substring(1));
					break;
				case 36: //getlist  $
					Integer index=Integer.parseInt(str.substring(str.indexOf("[")+1,str.indexOf("]")));
					String filed=str.substring(str.indexOf("]")+1);
					o=invokeListGet(invokeGetMethod(o, filed), index);
					break;
				case 94:// getmap  ^
					String key=str.substring(str.indexOf("[")+1,str.indexOf("]"));
					String mapfiled=str.substring(str.indexOf("]")+1);
					o=invokeMapGet(invokeGetMethod(o, mapfiled), key);
					break;
				case 33:// getset !
//					String key=str.substring(str.indexOf("[")+1,str.indexOf("]"));
					String setfiled=str.substring(str.indexOf("]")+1);
					o=invokeSetGet(invokeGetMethod(o, setfiled));
					break;
				case 42:// getarray *
					Integer arrayindex=Integer.parseInt(str.substring(str.indexOf("[")+1,str.indexOf("]")));
					String arrayfiled=str.substring(str.indexOf("]")+1);
					o=invokeArrayGet(invokeGetMethod(o, arrayfiled), arrayindex);
					break;
				case 35:// # object callback
					o=classCallBackService.invokeobject(str.substring(str.indexOf("[")+1,str.indexOf("]")),obj);
					break;
				default :
					o=invokeGetMethod(o, str);
					
			}

		}
		if(o instanceof Date){
			o=DateUtil.dateFormat(format, (Date)o);
		}
		return ClassUtil.valueOfString(o);
	}
	
	
	
	
	
	
	
	public static Object invokeSetGet(Object o)
	{
		Object value=null;
		if(o instanceof Set<?>){
			Set<?> set=Set.class.cast(o);
			if(StringUtil.setIsNull(set)){
				return value;
			}
			value=set.iterator().next();
		}
		return value;
	}

	public static Object invokeMapGet(Object o, String key)
	{
		Object value=null;
		if(o instanceof Map<?,?>){
			Map<?,?> map=Map.class.cast(o);
			if(StringUtil.mapIsNull(map)){
				return value;
			}
			value=map.get(key);
		}
		return value;
	}

	/**
	 * 功能描述:获取list元素
	 * @param o
	 * @param index
	 * @return
	 */
	public static Object invokeListGet(Object o, int index)
	{
		Object value=null;
		if(o instanceof List<?>){
			List<?> list=List.class.cast(o);
			if(StringUtil.listIsNull(list)){
				return null;
			}
			value=list.get(index);
		}
		return value;
	}
	
	/**
	 * 功能描述:获取list元素
	 * @param o
	 * @param index
	 * @return
	 */
	public static Object invokeArrayGet(Object o, int index)
	{
		Object[] value=null;
		if(o.getClass().isArray()){
			value=(Object[]) o;
			
		}
		return value[index];
	}

	public static Object invokeMethodArr(Object obj,String funcname) throws Exception{
		Method m=method(obj.getClass(),funcname, new Class[]{});
		Object o=null;
		try{
			o=m.invoke(obj, new Object[]{});
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("方法执行异常 类名:"+obj.getClass().getName()+"方法名:"+funcname);
		}
		return o;
	}

	public static String valueOfString(Object obj)
	{
		String value=null;
		if(!StringUtil.isNull(obj)){
			value=String.valueOf(obj);
		}
		return value;
	}
	
	/**
	 * 从包package中获取所有的Class
	 * 
	 * @param pack
	 * @return
	 */
	public static Set<Class<?>> getClasses(String pack) {

		// 第一个class类的集合
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		// 是否循环迭代
		boolean recursive = true;
		// 获取包的名字 并进行替换
		String packageName = pack;
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(
					packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					System.err.println("file类型的扫描");
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath,
							recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					// 定义一个JarFile
					System.err.println("jar类型的扫描");
					JarFile jar;
					try {
						// 获取jar
						jar = ((JarURLConnection) url.openConnection())
								.getJarFile();
						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 如果是以/开头的
							if (name.charAt(0) == '/') {
								// 获取后面的字符串
								name = name.substring(1);
							}
							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包
								if (idx != -1) {
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx)
											.replace('/', '.');
								}
								// 如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									// 如果是一个.class文件 而且不是目录
									if (name.endsWith(".class")
											&& !entry.isDirectory()) {
										// 去掉后面的".class" 获取真正的类名
										String className = name.substring(
												packageName.length() + 1, name
														.length() - 6);
										try {
											// 添加到classes
											classes.add(Class
													.forName(packageName + '.'
															+ className));
										} catch (ClassNotFoundException e) {
											// log
											// .error("添加用户自定义视图类错误 找不到此类的.class文件");
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						// log.error("在扫描用户定义视图时从jar包获取文件出错");
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}
	
	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	public static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, Set<Class<?>> classes) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			// log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)

			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "."
						+ file.getName(), file.getAbsolutePath(), recursive,
						classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					// 添加到集合中去
					//classes.add(Class.forName(packageName + '.' + className));
                                         //经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));  
                     } catch (ClassNotFoundException e) {
					// log.error("添加用户自定义视图类错误 找不到此类的.class文件");
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Integer[] ls=new Integer[]{1,2,3,4};
		ls[0]=1;ls[0]=2;ls[0]=3;ls[0]=4;

	}
	
	/**
	 * 功能描述:获取list元素
	 * @param o
	 * @param index
	 * @return
	 */
	public static Object test(Object o, int index)
	{
		Object[] value=null;
		if(o.getClass().isArray()){
			value=(Object[]) o;
		}
		return value[index];
	}

	public static <T> T getEntity(Class<T> clazz,Map<String, Object> map) {
		try {
			T obj=clazz.newInstance();
			Field[] fields=clazz.getDeclaredFields();
			for(Field f:fields){
				Column column=f.getAnnotation(Column.class);
				if(!StringUtil.isNull(column)){
					Method setMethod=ClassUtil.setMethod(clazz, f);
					String name=column.name();
					Object value=map.get(name);
					setMethod.invoke(obj, new Object[]{value});
				}
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> List<T> getListEntity(Class<T> clazz,List<Map<String, Object>> maps) {
		List<T> list = new ArrayList<T>();
		for(Map<String,Object> map:maps){
			list.add(ClassUtil.getEntity(clazz, map));
		}
		return list;
	}
}


