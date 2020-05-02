package com.github.niupengyu.core.util;


import java.security.SecureRandom;
import java.util.UUID;


/**
 * 
 * 此类描述的是：   	ID生成器
 * @author: daigang 
 */
public class IdGeneratorUtil {
	private static SecureRandom random = new SecureRandom();

	private static String[] BASE_CHARACTER = {"2","3","4","5","6","7","8","9"
											,"A","B","C","D","E","F","G","H","J"
											,"K","L","M","N","P","Q","R","S","T"
											,"U","V","W","X","Y","Z"};

	/**
	 * 生成32位uuid字符串
	 * @return
	 */
	public static String uuid32() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 生成36位uuid字符串
	 * @return
	 */
	public static String uuid36() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 生成随机long数字
	 * @return
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}
	
	/**
	 * 随机生成12位字符串,去掉1，0，O，I等易混淆字符
	 * @return
	 */
	public static String random12Str(){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<12;i++){
			sb.append(BASE_CHARACTER[(int)(32*Math.random())]);
		}
		return sb.toString();
	}
	/**
	 * 申鹏飞
	 * 随机生成10位字符串
	 * @return
	 */

	public static String random10Str() {
		 char[] ss = new char[10];
		 int i=0;
		while(i<10) {
		    int f = (int) (Math.random()*3);
		    if(f==0)  
		     ss[i] = (char) ('A'+Math.random()*26);
		    else if(f==1)  
		     ss[i] = (char) ('a'+Math.random()*26);
		    else 
		     ss[i] = (char) ('0'+Math.random()*10);    
		    i++;
		 }
		String is=new String(ss);
		 return is;
		}
	public static String random3Str() {
		 char[] ss = new char[3];
		 int i=0;
		while(i<3) {
		     ss[i] = (char) ('0'+Math.random()*3);    
		    i++;
		 }
		String is=new String(ss);
		 return is;
		}
	//////////////////////////////////////////////////
	public static void main(String[] args){
		System.out.println(uuid32());
	}
}