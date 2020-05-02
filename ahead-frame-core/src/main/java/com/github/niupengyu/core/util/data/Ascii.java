/**  
 * 文件名: Ascii.java
 * 包路径: minaExamle.common
 * 创建描述  
 *        @createPerson：牛鹏宇 
 *        @createDate：2015-9-19 下午05:09:55
 *        内容描述：
 * 修改描述  
 *        @updatePerson：牛鹏宇
 *        @updateDate：2015-9-19 下午05:09:55 
 *        修改内容:
 * 版本: V1.0   
 */
package com.github.niupengyu.core.util.data;

public class Ascii
{
	public static String stringToAscii(String value)
	{
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			if (i != chars.length - 1)
			{
				sbu.append((int) chars[i]).append(",");
			} else
			{
				sbu.append((int) chars[i]);
			}
		}
		return sbu.toString();
	}

	public static String stringToAscii1(String value)
	{
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			if (i != chars.length - 1)
			{
				sbu.append((int) chars[i]);
			} else
			{
				sbu.append((int) chars[i]);
			}
		}
		return sbu.toString();
	}

	//	2）将ASCII转成字符串的java方法

	//	view plaincopy to clipboardprint?
	public static String asciiToString(String value)
	{
		StringBuffer sbu = new StringBuffer();
		String[] chars = value.split(",");
		for (int i = 0; i < chars.length; i++)
		{
			sbu.append((char) Integer.parseInt(chars[i]));
		}
		return sbu.toString();
	}

	public static String asciiToString1(String value)
	{
		StringBuffer sbu = new StringBuffer();
		String[] chars = value.split(",");
		for (int i = 0; i < chars.length; i++)
		{
			sbu.append((char) Integer.parseInt(chars[i]));
		}
		return sbu.toString();
	}

}
