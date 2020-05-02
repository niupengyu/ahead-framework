/**  
 * 文件名: N.java
 * 包路径: cn.newsframework.core.util
 * 创建描述  
 *        @createPerson：牛鹏宇 
 *        @createDate：Apr 25, 2013 2:10:34 PM
 *        内容描述：
 * 修改描述  
 *        @updatePerson：牛鹏宇
 *        @updateDate：Apr 25, 2013 2:10:34 PM 
 *        修改内容:
 * 版本: V1.0   
 */
package com.github.niupengyu.core.util;

public class N {
	public static String $(Object fieldobj) {
		char ss='\"';
		String s=(StringUtil.isNull(fieldobj)?ss+""+ss:ss+fieldobj.toString()+ss);
		return s;
	}
}


