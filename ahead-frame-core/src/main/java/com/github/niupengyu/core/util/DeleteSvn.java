/**  
 * 文件名: DeleteSvn.java
 * 包路径: test
 * 创建描述  
 *        @createPerson：牛鹏宇 
 *        @createDate：Jul 12, 2013 1:33:45 PM
 *        内容描述：
 * 修改描述  
 *        @updatePerson：牛鹏宇
 *        @updateDate：Jul 12, 2013 1:33:45 PM 
 *        修改内容:
 * 版本: V1.0   
 */
package com.github.niupengyu.core.util;

import java.io.File;

public class DeleteSvn {
	
	
	public static void deleteSvnVersion(String path) {
		File file=new File(path);
			if(file.isDirectory()){
				deleteDirectory(file);
			}
	}

	private static void deleteDirectory(File file) {
		for(File f:file.listFiles()){
			if(f.getName().equals(".svn")){
				deleteSvnFile(f);
			}else if(f.isDirectory()){
				deleteDirectory(f);
			}
		}
	}

	private static void deleteSvnFile(File file) {
		if(file.isDirectory()){
			FileUtil.delAllFile(file.getPath());
			file.delete();
		}
	}


	
	/*//删除文件夹
	//param folderPath 文件夹完整绝对路径

	private static void delFolder(String folderPath) {
	     try {
	        delAllFile(folderPath); //删除完里面所有内容
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        java.io.File myFilePath = new java.io.File(filePath);
	        myFilePath.delete(); //删除空文件夹
	     } catch (Exception e) {
	       e.printStackTrace(); 
	     }
	}

	//删除指定文件夹下所有文件
	//param path 文件夹完整绝对路径
	private static boolean delAllFile(String path) {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	             delFolder(path + "/" + tempList[i]);//再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	     }*/
	   public static void main(String[] args) {
		DeleteSvn.deleteSvnVersion("J://realinfo");
	}
}


