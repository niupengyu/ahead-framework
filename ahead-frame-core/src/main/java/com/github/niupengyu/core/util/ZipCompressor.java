package com.github.niupengyu.core.util;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



public class ZipCompressor {
    static final int BUFFER = 8192;  

    /**
     * 执行压缩操作
     * @param file 被压缩的文件/文件夹
     */
    public void compressExe(File file,File zipFile) {
        if (!file.exists()){
        	throw new RuntimeException(file.getPath() + "不存在！");
        }
        try(FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
             CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,new CRC32());
             ZipOutputStream out = new ZipOutputStream(cos)) {

            String basedir = "";  
            compressByType(file, out, basedir);  
            out.close();  
        } catch (Exception e) { 
        	e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
  
    /**
     * 判断是目录还是文件，根据类型（文件/文件夹）执行不同的压缩方法
     * @param file 
     * @param out
     * @param basedir
     */
    private void compressByType(File file, ZipOutputStream out, String basedir) {  
        /* 判断是目录还是文件 */  
        if (file.isDirectory()) {  
            this.compressDirectory(file, out, basedir);
        } else {  
            this.compressFile(file, out, basedir);
        }  
    }  
  
    /**
     * 压缩一个目录
     * @param dir
     * @param out
     * @param basedir
     */
    private void compressDirectory(File dir, ZipOutputStream out, String basedir) {  
        if (!dir.exists()){
        	 return;  
        }
           
        File[] files = dir.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            /* 递归 */  
        	compressByType(files[i], out, basedir + dir.getName() + "/");  
        }  
    }  
  
    /**
     * 压缩一个文件
     * @param file
     * @param out
     * @param basedir
     */
    private void compressFile(File file, ZipOutputStream out, String basedir) {  
        if (!file.exists()) {  
            return;  
        }  
        try {  
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));  
            ZipEntry entry = new ZipEntry(basedir + file.getName());  
            out.putNextEntry(entry);  
            int count;  
            byte data[] = new byte[BUFFER];  
            while ((count = bis.read(data, 0, BUFFER)) != -1) {  
                out.write(data, 0, count);  
            }
            bis.close();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }
    }

    public static void main(String[] args) {
        ZipCompressor zip=new ZipCompressor();
        //文件
        String path="d:\\data";
        String name="test1112.pdf";
        File file=new File(path,name);
        System.out.println();
        zip.compressExe(file,new File(path,FileUtil.getFileName0(name)+".zip"));
        //目录
        name="www";
        file=new File(path,name);
        System.out.println();
        zip.compressExe(file,new File(path,FileUtil.getFileName0(name)+".zip"));
    }
}
