/**  
 * 
 * 文件名: NumberUtil.java
 * 包路径: core.util
 * 创建描述  
 *        创建人：牛鹏宇 
 *        创建日期：2013-8-19 上午09:27:52
 *        内容描述：
 * 修改描述  
 *        修改人： 
 *        修改日期：2013-8-19 上午09:27:52 
 *        修改内容:
 * 版本: V1.0   
 */
package com.github.niupengyu.core.util.data;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Random;


/**
 * 类:  <code> NumberUtil </code>
 * 功能描述:数字处理工具类 
 * 创建人: 牛鹏宇
 * 创建日期: 2013-8-19 上午09:27:58
 * 开发环境: JDK7.0
 */
public class NumberUtil
{
	public static Long numberSize(Long num)
	{
		Long i = 1l;
		while ((num / 10) > 0)
		{
			num = num / 10;
			i++;
		}
		return i;
	}

	public static Long floatByte(Long num, Long numSize)
	{
		int x = 1;
		for (Long i = 0l; i < numSize; i++)
		{
			x = x * 10;
		}
		return x * num;
	}

	public String getdouble(double d, int w)
	{
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(w);
		return nf.format(d);
	}

	public static String genCode(int count)
	{
		String code = "";
		Random random = new Random();
		for (int i = 0; i < count; i++)
		{
			code += random.nextInt(8) + 1;
		}
		return code;
	}


	/**
	 * 字符串转换成十六进制字符串
	 * 
	 * @param str
	 *            str 待转换的ASCII字符串
	 * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
	 */
	public static String str2HexStr2(String str)
	{

		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;

		for (int i = 0; i < bs.length; i++)
		{
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString().trim();
	}

	/**
	 * bytes转换成十六进制字符串
	 * 
	 * @param b byte数组
	 * @return String 每个Byte值之间空格分隔
	 */
	public static String byte2HexStr(byte[] b)
	{
		String stmp = "";
		StringBuilder sb = new StringBuilder("");
		for (int n = 0; n < b.length; n++)
		{
			stmp = Integer.toHexString(b[n] & 0xFF);
			sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
			//			sb.append(" ");
		}
		return sb.toString().toUpperCase().trim();
	}

	public static int bytes2Int(byte[] bytes) {
		int num = bytes[3] & 0xFF;
		num |= ((bytes[2] << 8) & 0xFF00);
		num |= ((bytes[1] << 16) & 0xFF0000);
		num |= ((bytes[0] << 24) & 0xFF0000);
		return num;
	}

	public static byte[] int2Bytes(int i)
	{
		byte[] result=new byte[4];
		result[0]=(byte)((i >> 24)& 0xFF);
		result[1]=(byte)((i >> 16)& 0xFF);
		result[2]=(byte)((i >> 8)& 0xFF);
		result[3]=(byte)(i & 0xFF);
		return result;
	}


	//转化字符串为十六进制编码
	public static String toHexString(String s)
	{
		String str = "";
		for (int i = 0; i < s.length(); i++)
		{
			int ch = s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}



	/*
	* 16进制数字字符集
	*/
	private static String hexString = "0123456789ABCDEF";

	/*
	* 将字符串编码成16进制数字,适用于所有字符（包括中文）
	*/
	public static String encode(String str)
	{
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++)
		{
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/*
	* 将16进制数字解码成字符串,适用于所有字符（包括中文）
	*/
	public static String decode(String bytes)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}



	public static String hexString2binaryString(String hexString)
	{
		if (hexString == null || hexString.length() % 2 != 0)
			return null;
		String bString = "", tmp;
		for (int i = 0; i < hexString.length(); i++)
		{
			tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		return bString;
	}



	public byte[] getContent(String filePath) throws IOException
	{
		File file = new File(filePath);
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE)
		{
			return null;
		}
		FileInputStream fi = new FileInputStream(file);
		byte[] buffer = new byte[(int) fileSize];
		int offset = 0;
		int numRead = 0;
		while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0)
		{
			offset += numRead;
		}
		// 确保所有数据均被读取
		if (offset != buffer.length)
		{
			throw new IOException("Could not completely read file " + file.getName());
		}
		fi.close();
		return buffer;
	}



	/**
	 * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray3(String filename) throws IOException
	{

		FileChannel fc = null;
		try
		{
			fc = new RandomAccessFile(filename, "r").getChannel();
			MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size()).load();
			byte[] result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0)
			{
				// LogUtil.logger.info("remain");
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
			return result;
		} catch (IOException e)
		{
			e.printStackTrace();
			throw e;
		} finally
		{
			try
			{
				fc.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}


	private static byte charToByte(char c)
	{
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
	
	public static String parseDouble(double value,String reg){
		DecimalFormat df = new DecimalFormat(reg);     
        return df.format(value);      
	}
	
	/**
	 * 保留小数位数
	 * @param count
	 * @param max
	 * @param i
	 * @return
	 */
	public static float percent(double count,double max,int i){
		if(max==0){
			return 0.0f;
		}
		BigDecimal b = new BigDecimal(count/max*100); 
		float f1 = b.setScale(i,BigDecimal.ROUND_HALF_UP).floatValue();
		return f1;
	}
	
	/**
	 * 保留两位小数
	 * @param count
	 * @param max
	 * @return
	 */
	public static float percent(double count,double max){
		if(max==0){
			return 0.0f;
		}
		BigDecimal b = new BigDecimal(count/max*100); 
		float f1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
		return f1;
	}

	public static String format(int num){
		return  String.format("%04d", num);
	}

	public static String format(int num,int size){
		return  String.format("%0"+size+"d", num);
	}

	public static String format(long num){
		return  String.format("%04d", num);
	}

	public static String format(long num,int size){
		return  String.format("%0"+size+"d", num);
	}

	public static String format(String format,int num){
		return  String.format(format, num);
	}

	public static HashSet<Integer> random(int size, int max) {

		HashSet<Integer> hs = new HashSet<Integer>();
		while (true) {
			if (hs.size() == size) {
				break;
			}
			int a = (int)(Math.random() * max);
			if(a >= 0 && a <= max) {
				hs.add(a);
			}

		}
		return hs;
	}

	public static double decimalFormat(double f,int i) {
		BigDecimal b   =   new   BigDecimal(f);
		double   f1   =   b.setScale(i,   BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}

	public static int sum(int[] array){
		int sum=0;
		for(int i:array){
			sum+=i;
		}
		return sum;
	}

	public static String mark(String str,int length) {
		int strLength=str.length();
		if(strLength<length){
			String mark="";
			for(int i=0;i<length-strLength;i++){
				mark+="0";
			}
			return (mark+str);
		}else{
			return (str.substring(strLength-length,strLength));
		}

	}

	public static void main(String[] args) {
		System.out.println(NumberUtil.percent(0.9,16));
	}
}
