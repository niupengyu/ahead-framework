package com.github.niupengyu.core.util;
  
import java.io.*;
import java.util.Enumeration;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
  
public class ZipTools {
  
    public static void main(String[] args) throws FileNotFoundException, Exception {
//        Main main = new Main("e:/test1.zip");
//        main.createZipOut();
//        main.packToolFiles("e:/test", "");
//        main.closeZipOut();
  
        ZipTools main = new ZipTools("E:\\apache-tomcat-6.0.35-windows-x86\\apache-tomcat-6.0.35\\webapps\\lybl\\fileupload\\zip\\1.zip");
        main.createZipOut();
        main.packToolFiles("E:\\apache-tomcat-6.0.35-windows-x86\\apache-tomcat-6.0.35\\webapps\\lybl\\fileupload\\scenic\\1", "");
        main.closeZipOut();
        System.out.println(System.getProperty("user.dir"));
        //Main.unZipToFolder("e:/test1.zip", "e:/test1");
         
        //Main.unZipToFolder("e:/test2.zip", "e:/test2");
    }
    private OutputStream out = null;
    private BufferedOutputStream bos = null;
    private ZipArchiveOutputStream zaos = null;
    private String zipFileName = null;
  
    public ZipTools(String zipname) {
        this.zipFileName = zipname;
    }
  
    public void createZipOut() throws FileNotFoundException, IOException {
        File f = new File(zipFileName);
        out = new FileOutputStream(f);
        bos = new BufferedOutputStream(out);
        zaos = new ZipArchiveOutputStream(bos);
        zaos.setEncoding("GBK");
  
    }
  
    public void closeZipOut() throws Exception {
        zaos.flush();
        zaos.close();
  
        bos.flush();
        bos.close();
  
        out.flush();
        out.close();
    }
  
    /**
     * 把一个目录打包到zip文件中的某目录
     * @param dirpath   目录绝对地址
     * @param pathName  zip中目录
     */
    public void packToolFiles(String dirpath, String pathName) throws FileNotFoundException, IOException {
        if (!StringUtil.isNull(pathName)) {
            pathName = pathName + File.separator;
        }
        packToolFiles(zaos, dirpath, pathName);
    }
  
    /**
     * 把一个目录打包到一个指定的zip文件中
     * @param dirpath           目录绝对地址
     * @param pathName       zip文件抽象地址
     */
    public void packToolFiles(ZipArchiveOutputStream zaos, String dirpath, String pathName) throws FileNotFoundException, IOException {
  
        ByteArrayOutputStream tempbaos = new ByteArrayOutputStream();
        BufferedOutputStream tempbos = new BufferedOutputStream(tempbaos);
  
        File dir = new File(dirpath);
        //返回此绝对路径下的文件
        File[] files = dir.listFiles();
        if (files == null || files.length < 1) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            //判断此文件是否是一个文件夹
            if (files[i].isDirectory()) {
                packToolFiles(zaos, files[i].getAbsolutePath(), pathName + files[i].getName() + File.separator);
            } else {
                zaos.putArchiveEntry(new ZipArchiveEntry(pathName + files[i].getName()));
                IOUtils.copy(new FileInputStream(files[i].getAbsolutePath()), zaos);
                zaos.closeArchiveEntry();
  
            }
  
        }
  
        tempbaos.flush();
        tempbaos.close();
  
        tempbos.flush();
        tempbos.close();
    }
  
    /**
     * 把一个zip文件解压到一个指定的目录中
     * @param zipfilename   zip文件抽象地址
     * @param outputdir     目录绝对地址
     */
    public static void unZipToFolder(String zipfilename, String outputdir) throws IOException {
        File zipfile = new File(zipfilename);
        if (zipfile.exists()) {
            outputdir = outputdir + File.separator;
            FileUtils.forceMkdir(new File(outputdir));
  
            ZipFile zf = new ZipFile(zipfile, "GBK");
            Enumeration<ZipArchiveEntry> zipArchiveEntrys = zf.getEntries();
            while (zipArchiveEntrys.hasMoreElements()) {
                ZipArchiveEntry zipArchiveEntry = zipArchiveEntrys.nextElement();
                if (zipArchiveEntry.isDirectory()) {
                    FileUtils.forceMkdir(new File(outputdir + zipArchiveEntry.getName() + File.separator));
                } else {
                    InputStream inputStream=zf.getInputStream(zipArchiveEntry);
                    OutputStream outputStream=FileUtils.openOutputStream(new File(outputdir + zipArchiveEntry.getName()));
                    IOUtils.copy(zf.getInputStream(zipArchiveEntry), outputStream);
                    FileUtil.close(inputStream);
                    FileUtil.close(outputStream);
                }
            }
            zf.close();
        } else {
            throw new IOException("指定的解压文件不存在：\t" + zipfilename);
        }
    }
}
