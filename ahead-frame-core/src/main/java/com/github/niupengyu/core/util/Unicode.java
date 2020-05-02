/**  
 * 文件名: Unicode.java
 * 包路径: minaExamle.common
 * 创建描述  
 *        @createPerson：牛鹏宇 
 *        @createDate：2015-9-19 下午05:01:44
 *        内容描述：
 * 修改描述  
 *        @updatePerson：牛鹏宇
 *        @updateDate：2015-9-19 下午05:01:44 
 *        修改内容:
 * 版本: V1.0   
 */
package com.github.niupengyu.core.util;

import java.io.UnsupportedEncodingException;


public class Unicode
{

	/**
	 * 把中文转成Unicode码
	 * @param str
	 * @return
	 */
	public static String chinaToUnicode(String str)
	{
		String result = "";
		for (int i = 0; i < str.length(); i++)
		{
			int chr1 = str.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941)
			{//汉字范围 \u4e00-\u9fa5 (中文)
				result += "\\u" + Integer.toHexString(chr1);
			} else
			{
				result += str.charAt(i);
			}
		}
		return result;
	}
	
	/**
	 * 把中文转成Unicode码
	 * @param str
	 * @return
	 */
	public static String chinaToUnicode2(String str)
	{
		String result = "";
		for (int i = 0; i < str.length(); i++)
		{
			int chr1 = str.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941)
			{//汉字范围 \u4e00-\u9fa5 (中文)
				result += Integer.toHexString(chr1);
			} else
			{
				result += str.charAt(i);
			}
		}
		return result;
	}

	/**
	 * 判断是否为中文字符
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c)
	{
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
		{
			return true;
		}
		return false;
	}
	
	public static String string2Unicode2(String s)
	{
		try
		{
			StringBuffer out = new StringBuffer("");
			byte[] bytes = s.getBytes("unicode");
			for (int i = 2; i < bytes.length - 1; i += 2)
			{
				out.append("u");
				String str = Integer.toHexString(bytes[i + 1] & 0xff);
				for (int j = str.length(); j < 2; j++)
				{
					out.append("0");
				}
				String str1 = Integer.toHexString(bytes[i] & 0xff);

				out.append(str);
				out.append(str1);
				out.append(" ");
			}
			return out.toString().toUpperCase();
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static String string2Unicode2(String s,String u,String k)
	{
		try
		{
			StringBuffer out = new StringBuffer("");
			byte[] bytes = s.getBytes("unicode");
			for (int i = 2; i < bytes.length - 1; i += 2)
			{
				out.append(u);
				String str = Integer.toHexString(bytes[i + 1] & 0xff);
				for (int j = str.length(); j < 2; j++)
				{
					out.append("0");
				}
				String str1 = Integer.toHexString(bytes[i] & 0xff);

				out.append(str);
				out.append(str1);
				out.append(k);
			}
			return out.toString().toUpperCase();
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static String string2Unicode(String s)
	{
		try
		{
			StringBuffer out = new StringBuffer("");
			byte[] bytes = s.getBytes("unicode");
			for (int i = 2; i < bytes.length - 1; i += 2)
			{
//				out.append("u");
				String str = Integer.toHexString(bytes[i + 1] & 0xff);
				for (int j = str.length(); j < 2; j++)
				{
					out.append("0");
				}
				String str1 = Integer.toHexString(bytes[i] & 0xff);

				out.append(str);
				out.append(str1);
//				out.append(" ");
			}
			return out.toString().toUpperCase();
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static String unicode2String(String unicodeStr) throws Exception
	{
		StringBuffer sb = new StringBuffer();
		String str[] = unicodeStr.toUpperCase().split("U");
		for (int i = 0; i < str.length; i++)
		{
			if (str[i].equals(""))
				continue;
			char c = (char) Integer.parseInt(str[i].trim(), 16);
			sb.append(c);
		}
		return sb.toString();
	}
	
	public static String unicode2String2(String unicodeStr)
	{
		StringBuffer sb = new StringBuffer();
//		String str[] = unicodeStr.toUpperCase().split("U");
		for (int i = 0; i < unicodeStr.length(); i+=4)
		{
//			if ("".equals(unicodeStr.substring(f, f+4)))
//				continue;
			String str=unicodeStr.substring(i, i+4);
			char c = (char) Integer.parseInt(str.trim(), 16);
			sb.append(c);
		}
		return sb.toString();
	}
	
	/** 
	* 将字符串转成unicode 
	* @param str 待转字符串 
	* @return unicode字符串 
	*/ 
	public static String convert(String str) 
	{ 
	str = (str == null ? "" : str); 
	String tmp; 
	StringBuffer sb = new StringBuffer(1000); 
	char c; 
	int i, j; 
	sb.setLength(0); 
	for (i = 0; i < str.length(); i++) 
	{ 
	c = str.charAt(i); 
	sb.append("\\u"); 
	j = (c >>>8); //取出高8位 
	tmp = Integer.toHexString(j); 
	if (tmp.length() == 1) 
	sb.append("0"); 
	sb.append(tmp); 
	j = (c & 0xFF); //取出低8位 
	tmp = Integer.toHexString(j); 
	if (tmp.length() == 1) 
	sb.append("0"); 
	sb.append(tmp); 

	} 
	return (new String(sb)); 
	} 


//	2)unicode转成字符串，与上述过程反向操作即可 
	/** 
	* 将unicode 字符串 
	* @param str 待转字符串 
	* @return 普通字符串 
	*/ 
	public static String revert(String str) 
	{ 
	str = (str == null ? "" : str); 
	if (str.indexOf("\\u") == -1)//如果不是unicode码则原样返回 
	return str; 

	StringBuffer sb = new StringBuffer(1000); 

	for (int i = 0; i < str.length() - 6;) 
	{ 
	String strTemp = str.substring(i, i + 6); 
	String value = strTemp.substring(2); 
	int c = 0; 
	for (int j = 0; j < value.length(); j++) 
	{ 
	char tempChar = value.charAt(j); 
	int t = 0; 
	switch (tempChar) 
	{
	case 'a': 
	t = 10; 
	break; 
	case 'b': 
	t = 11; 
	break; 
	case 'c': 
	t = 12; 
	break; 
	case 'd': 
	t = 13; 
	break; 
	case 'e': 
	t = 14; 
	break; 
	case 'f': 
	t = 15; 
	break; 
	default: 
	t = tempChar - 48; 
	break; 
	}

	c += t * ((int) Math.pow(16, (value.length() - j - 1))); 
	}
	sb.append((char) c); 
	i = i + 6; 
	} 
	return sb.toString(); 
	}
	
	
	/** 
	* 将字符串转成unicode 
	* @param str 待转字符串 
	* @return unicode字符串 
	*/ 
	public static String convert1(String str) 
	{
	str = (str == null ? "" : str); 
	String tmp; 
	StringBuffer sb = new StringBuffer(1000); 
	char c; 
	int i, j; 
	sb.setLength(0); 
	for (i = 0; i < str.length(); i++) 
	{ 
	c = str.charAt(i); 
//	sb.append("\\u"); 
	j = (c >>>8); //取出高8位 
	tmp = Integer.toHexString(j); 
	if (tmp.length() == 1) 
	sb.append("0"); 
	sb.append(tmp); 
	j = (c & 0xFF); //取出低8位 
	tmp = Integer.toHexString(j); 
	if (tmp.length() == 1) 
	sb.append("0"); 
	sb.append(tmp); 

	} 
	return (new String(sb)); 
	} 


//	2)unicode转成字符串，与上述过程反向操作即可 
	/** 
	* 将unicode 字符串 
	* @param str 待转字符串 
	* @return 普通字符串 
	*/ 
	public static String revert1(String str) 
	{ 
	str = (str == null ? "" : str); 
//	if (str.indexOf("\\u") == -1)//如果不是unicode码则原样返回 
	if(StringUtil.isNull(str)){
		return str; 
	}

	StringBuffer sb = new StringBuffer(1000); 

	for (int i = 0; i < str.length();i = i + 4) 
	{
	String strTemp = str.substring(i, i + 4); 
	String value = strTemp;
	//.substring(2); 
	int c = 0; 
	for (int j = 0; j < value.length(); j++) 
	{ 
	char tempChar = value.charAt(j); 
	int t = 0; 
	switch (tempChar) 
	{
	case 'a': 
	t = 10; 
	break; 
	case 'b': 
	t = 11; 
	break; 
	case 'c': 
	t = 12; 
	break; 
	case 'd': 
	t = 13; 
	break; 
	case 'e': 
	t = 14; 
	break; 
	case 'f': 
	t = 15; 
	break; 
	default: 
	t = tempChar - 48; 
	break; 
	}

	c += t * ((int) Math.pow(16, (value.length() - j - 1))); 
	}
	sb.append((char) c); 
	
	}
	return sb.toString(); 
	}
	
	public static void main(String[] args)
	{
		try
		{
			unicode2String("asdf");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
