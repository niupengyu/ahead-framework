package com.github.niupengyu.web.util;

import com.github.niupengyu.core.util.Content;
import com.github.niupengyu.core.util.FileUtil;
import com.github.niupengyu.core.util.IdGeneratorUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TransferTo {

    public static File file(MultipartFile file,String destPath) throws Exception {
        String id = IdGeneratorUtil.uuid32();   //主键id使用32位uuid
        String fileName = file.getOriginalFilename();  //获取文件名称
        String suffix = FileUtil.getFix(fileName);  //获取源文件的后缀
        String destFilePath = destPath+ Content.BACKSLASH+id+Content.POINT+suffix;  //目标文件完整路径
        //把源文件复制到目标文件
        File destFile = new File(destFilePath);
        file.transferTo(destFile);
        return destFile;
    }

    public static File file(MultipartFile file,String destPath,String name) throws Exception {
        String id = name;   //主键id使用32位uuid
        String fileName = file.getOriginalFilename();  //获取文件名称
        String suffix = FileUtil.getFix(fileName);  //获取源文件的后缀
        String destFilePath = destPath+ Content.BACKSLASH+id+Content.POINT+suffix;  //目标文件完整路径
        //把源文件复制到目标文件
        File destFile = new File(destFilePath);
        file.transferTo(destFile);
        return destFile;
    }

    public static File[] files(MultipartFile[] file,String destPath) throws Exception {
        File[] files=new File[file.length];
        for(int i=0;i<file.length;i++){
            files[i]=file(file[i],destPath);
        }
        return files;
    }

    public static void img(String file, HttpServletResponse response){
        img(new File(file),response);
    }

    public static void img(File file, HttpServletResponse response){
        byte[] data;
        response.setContentType("image/png");
        try(FileInputStream inputStream = new FileInputStream(file);
            OutputStream stream = response.getOutputStream();) {
            data = new byte[inputStream.available()];
            inputStream.read(data);
            stream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
