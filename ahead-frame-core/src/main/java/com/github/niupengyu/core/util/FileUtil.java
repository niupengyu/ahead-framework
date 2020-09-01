package com.github.niupengyu.core.util;

import com.github.niupengyu.core.filter.FileFilterForName;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {


    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    /**
     * 功能描述:返回文件名
     * @param fp
     * @return
     */
    public static String getFileName(String fp)
    {
        int index=fp.lastIndexOf(Content.POINT);
        int end=index>-1?index:fp.length()-1;
        String filename = fp.substring(fp.lastIndexOf("\\") + 1,end);
        return filename;
    }

    public static String getFileName0(String fn)
    {
        int index=fn.lastIndexOf(Content.POINT);
        int end=index>-1?index:fn.length()-1;
        String filename = fn.substring(0,end);
        return filename;
    }

    public static void main(String[] args) throws Exception {
        ResourceLoader loader = new DefaultResourceLoader();
        System.out.println(root());
//        System.out.println(getFileName("dsdf\\asd\\asd\\sdfsdf.sdf"));
//        System.out.println(getFileName0("sdfsdf.sdf"));
    }

    public static void file(File file,String postfix,String root){
        List<String> fileList=new ArrayList<>();
        File[] files=file.listFiles();
        int filesLength=files.length;
        for(int i=0;i<filesLength;i++){
            String basePath=root+file.getName()+Content.BACKSLASH;
            File chil=files[i];
            if(chil.isDirectory()){
                //System.out.println(basePath);
                file(chil,postfix,basePath);
            }else{
                if(chil.getName().endsWith(postfix)){
                    //System.out.println(basePath+chil.getName());
                    fileList.add(basePath+chil.getName());
                }
            }
        }
    }

    public static File[] files(String path,String postfix){
        File file=new File(path);
        if(file.isDirectory()){
            return file.listFiles(new FileFilterForName(postfix));
        }
        return new File[]{file};
    }


    public static void close(InputStream in) {
        closeable(in);
    }



    public static void close(Writer out) {
        closeable(out,out);
    }

    public static void closeable(Closeable out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                out = null;
            }
        }
    }

    public static void closeable(Closeable out,Flushable flushable) {
        if (out != null&&flushable!=null) {
            try {
                flushable.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                out = null;
            }
        }
    }

    public static void close(Reader in) {
        closeable(in);
    }

    public static void close(OutputStream out) {
        closeable(out,out);
    }

    public static String getImgStr(String imgFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        File file=new File(imgFile);
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();
            if(data!=null&&data.length>0){
                //CommonUtil.fileDelete(file);  //删除图片
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //BASE64Encoder encoder = new BASE64Encoder();
        //return encoder.encode(data);
        return Base64.encodeBase64String(data);
    }

    public static String mkdirs(String path) {
        File file=new File(path);
        if(!file.isDirectory()){
            file.mkdirs();
        }
        return file.getPath();
    }

    public static void mkdirs1(String path) {
        File file=new File(path);
        if(!file.isDirectory()){
            file.mkdirs();
        }
    }

    public static String root() throws IOException {
        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        return courseFile+Content.BACKSLASH;
    }

    public static String getResourcesPath(Class<?> clazz) {
        return clazz.getResource("/").getPath();
    }

    public static String getMd5ByFile(File file) throws FileNotFoundException
    {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try
        {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (null != in)
            {
                try
                {
                    in.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    /**
     * 功能描述:根据路径返回文件后缀
     * @param path
     */
    public static String getFix(String path)
    {
        String fix=null;
        int index=path.lastIndexOf(".");
        if(index>-1){
            fix = path.substring(index+1);
        }else{
            fix="";
        }
        return fix;
    }

    //删除文件夹
    //param folderPath 文件夹完整绝对路径

    public static void delFolder(String folderPath)
    {
        try
        {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //删除指定文件夹下所有文件
    //param path 文件夹完整绝对路径
    public static boolean delAllFile(String path)
    {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists())
        {
            return flag;
        }
        if (!file.isDirectory())
        {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++)
        {
            if (path.endsWith(File.separator))
            {
                temp = new File(path + tempList[i]);
            } else
            {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile())
            {
                temp.delete();
            }
            if (temp.isDirectory())
            {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    public static String setContentType(String returnFileName)
    {
        String contentType = "application/octet-stream";
        if (returnFileName.lastIndexOf(".") < 0)
            return contentType;
        returnFileName = returnFileName.toLowerCase();
        returnFileName = returnFileName.substring(returnFileName.lastIndexOf(".") + 1);

        if (returnFileName.equals("html") || returnFileName.equals("htm") || returnFileName.equals("shtml"))
        {
            contentType = "text/html";
        } else if (returnFileName.equals("apk"))
        {
            contentType = "application/vnd.android.package-archive";
        } else if (returnFileName.equals("sis"))
        {
            contentType = "application/vnd.symbian.install";
        } else if (returnFileName.equals("sisx"))
        {
            contentType = "application/vnd.symbian.install";
        } else if (returnFileName.equals("exe"))
        {
            contentType = "application/x-msdownload";
        } else if (returnFileName.equals("msi"))
        {
            contentType = "application/x-msdownload";
        } else if (returnFileName.equals("css"))
        {
            contentType = "text/css";
        } else if (returnFileName.equals("xml"))
        {
            contentType = "text/xml";
        } else if (returnFileName.equals("gif"))
        {
            contentType = "image/gif";
        } else if (returnFileName.equals("jpeg") || returnFileName.equals("jpg"))
        {
            contentType = "image/jpeg";
        } else if (returnFileName.equals("js"))
        {
            contentType = "application/x-javascript";
        } else if (returnFileName.equals("atom"))
        {
            contentType = "application/atom+xml";
        } else if (returnFileName.equals("rss"))
        {
            contentType = "application/rss+xml";
        } else if (returnFileName.equals("mml"))
        {
            contentType = "text/mathml";
        } else if (returnFileName.equals("txt"))
        {
            contentType = "text/plain";
        } else if (returnFileName.equals("jad"))
        {
            contentType = "text/vnd.sun.j2me.app-descriptor";
        } else if (returnFileName.equals("wml"))
        {
            contentType = "text/vnd.wap.wml";
        } else if (returnFileName.equals("htc"))
        {
            contentType = "text/x-component";
        } else if (returnFileName.equals("png"))
        {
            contentType = "image/png";
        } else if (returnFileName.equals("tif") || returnFileName.equals("tiff"))
        {
            contentType = "image/tiff";
        } else if (returnFileName.equals("wbmp"))
        {
            contentType = "image/vnd.wap.wbmp";
        } else if (returnFileName.equals("ico"))
        {
            contentType = "image/x-icon";
        } else if (returnFileName.equals("jng"))
        {
            contentType = "image/x-jng";
        } else if (returnFileName.equals("bmp"))
        {
            contentType = "image/x-ms-bmp";
        } else if (returnFileName.equals("svg"))
        {
            contentType = "image/svg+xml";
        } else if (returnFileName.equals("jar") || returnFileName.equals("var") || returnFileName.equals("ear"))
        {
            contentType = "application/java-archive";
        } else if (returnFileName.equals("doc"))
        {
            contentType = "application/msword";
        } else if (returnFileName.equals("pdf"))
        {
            contentType = "application/pdf";
        } else if (returnFileName.equals("rtf"))
        {
            contentType = "application/rtf";
        } else if (returnFileName.equals("xls"))
        {
            contentType = "application/vnd.ms-excel";
        } else if (returnFileName.equals("ppt"))
        {
            contentType = "application/vnd.ms-powerpoint";
        } else if (returnFileName.equals("7z"))
        {
            contentType = "application/x-7z-compressed";
        } else if (returnFileName.equals("rar"))
        {
            contentType = "application/x-rar-compressed";
        } else if (returnFileName.equals("swf"))
        {
            contentType = "application/x-shockwave-flash";
        } else if (returnFileName.equals("rpm"))
        {
            contentType = "application/x-redhat-package-manager";
        } else if (returnFileName.equals("der") || returnFileName.equals("pem") || returnFileName.equals("crt"))
        {
            contentType = "application/x-x509-ca-cert";
        } else if (returnFileName.equals("xhtml"))
        {
            contentType = "application/xhtml+xml";
        } else if (returnFileName.equals("zip"))
        {
            contentType = "application/zip";
        } else if (returnFileName.equals("mid") || returnFileName.equals("midi") || returnFileName.equals("kar"))
        {
            contentType = "audio/midi";
        } else if (returnFileName.equals("mp3"))
        {
            contentType = "audio/mpeg";
        } else if (returnFileName.equals("ogg"))
        {
            contentType = "audio/ogg";
        } else if (returnFileName.equals("m4a"))
        {
            contentType = "audio/x-m4a";
        } else if (returnFileName.equals("ra"))
        {
            contentType = "audio/x-realaudio";
        } else if (returnFileName.equals("3gpp") || returnFileName.equals("3gp"))
        {
            contentType = "video/3gpp";
        } else if (returnFileName.equals("mp4"))
        {
            contentType = "video/mp4";
        } else if (returnFileName.equals("mpeg") || returnFileName.equals("mpg"))
        {
            contentType = "video/mpeg";
        } else if (returnFileName.equals("mov"))
        {
            contentType = "video/quicktime";
        } else if (returnFileName.equals("flv"))
        {
            contentType = "video/x-flv";
        } else if (returnFileName.equals("m4v"))
        {
            contentType = "video/x-m4v";
        } else if (returnFileName.equals("mng"))
        {
            contentType = "video/x-mng";
        } else if (returnFileName.equals("asx") || returnFileName.equals("asf"))
        {
            contentType = "video/x-ms-asf";
        } else if (returnFileName.equals("wmv"))
        {
            contentType = "video/x-ms-wmv";
        } else if (returnFileName.equals("avi"))
        {
            contentType = "video/x-msvideo";
        }

        return contentType;
    }
}
