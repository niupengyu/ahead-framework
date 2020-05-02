package com.github.niupengyu.jdbc.util.backup;

import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.core.util.DateUtil;
import com.github.niupengyu.core.util.FileUtil;
import com.github.niupengyu.core.util.IdGeneratorUtil;

import java.io.BufferedReader;

import java.io.BufferedWriter;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.io.OutputStream;

import java.io.OutputStreamWriter;

import java.nio.channels.FileChannel;


public class DataBaseUtils {

    /**
     * 数据库备份
     *
     * @param command
     * @param savePath
     * @return
     */

    public static String backup(String command, String savePath) {
        String flag;
        long size = 0;
        // 获得与当前应用程序关联的Runtime对象
        Runtime r = Runtime.getRuntime();
        BufferedReader br = null;
        BufferedWriter bw = null;
        FileChannel fc = null;
        try {
            // 在单独的进程中执行指定的字符串命令
            Process p = r.exec(command);
            // 获得连接到进程正常输出的输入流，该输入流从该Process对象表示的进程的标准输出中获取数据
            InputStream is = p.getInputStream();
            // InputStreamReader是从字节流到字符流的桥梁：它读取字节，并使用指定的charset将其解码为字符
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            //BufferedReader从字符输入流读取文本，缓冲字符，提供字符，数组和行的高效读取
            br = new BufferedReader(isr);
            String s;
            StringBuffer sb = new StringBuffer("");
            // 创建文件输出流
            FileOutputStream fos = new FileOutputStream(savePath);
            // OutputStreamWriter是从字符流到字节流的桥梁，它使用指定的charset将写入的字符编码为字节
            OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
            fc = fos.getChannel();
            // BufferedWriter将文本写入字符输出流，缓冲字符，以提供单个字符，数组和字符串的高效写入
            bw = new BufferedWriter(osw);
            // 组装字符串
            while ((s = br.readLine()) != null) {
                //sb.append(s + System.lineSeparator());
                bw.write(s + System.lineSeparator());
            }
            //s = sb.toString();
            size = fc.size();
            bw.flush();
            flag = "1";
        } catch (IOException e) {
            flag = "0";
            e.printStackTrace();
        } finally {
            //由于输入输出流使用的是装饰器模式，所以在关闭流时只需要调用外层装饰类的close()方法即可，
            //它会自动调用内层流的close()方法
            FileUtil.close(bw);
            FileUtil.close(br);
            bw=null;
            br=null;
        }
        return flag + size;
    }

    /**
     * 数据库备份恢复
     *
     * @param command
     * @param savePath
     * @return
     */

    public static boolean recover(String command, String savePath) {

        boolean flag;

        Runtime r = Runtime.getRuntime();

        BufferedReader br = null;

        BufferedWriter bw = null;

        try {

            Process p = r.exec(command);

            OutputStream os = p.getOutputStream();

            FileInputStream fis = new FileInputStream(savePath);

            InputStreamReader isr = new InputStreamReader(fis, "utf-8");

            br = new BufferedReader(isr);

            String s;

            StringBuffer sb = new StringBuffer("");

            while ((s = br.readLine()) != null) {

                sb.append(s + System.lineSeparator());

            }

            s = sb.toString();

            OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");

            bw = new BufferedWriter(osw);

            bw.write(s);

            bw.flush();

            flag = true;

        } catch (IOException e) {

            flag = false;

            e.printStackTrace();

        } finally {

            try {

                if (null != bw) {

                    bw.close();

                }

            } catch (IOException e) {

                e.printStackTrace();

            }


            try {

                if (null != br) {

                    br.close();

                }

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        return flag;

    }

    public Object insertDataBase(DataBaseBean dataBaseBean,String img_path) throws SysException {

        long count = System.currentTimeMillis();

        int num = 1;

// 测试备份 /usr/bin/mysqldump为数据库的路径以及备份命令 -u用户名 -p密码  数据库名称database

        String command1 = "/usr/bin/mysqldump -uroot -p123456 database";// 参数依次是IP、账号、密码、数据库名

        String fileName = DateUtil.dateFormat("yyyyMMddHHmmssSSS") + "-" + IdGeneratorUtil.uuid32() + ".sql";

        //img_path linux服务器路径

        String savePath1 = img_path + "/database/" + fileName;

        String b1 = DataBaseUtils.backup(command1, savePath1);

        if ("1".equals(b1.substring(0, 1))) {

            dataBaseBean.setSize(b1.substring(1));

            dataBaseBean.setData_name(fileName);

            dataBaseBean.setUrl(img_path + "/database/" + fileName);

            dataBaseBean.setVersion("V" + count);

            //num = settingDaoC.insertDataBase(dataBaseBean);

            if (num <= 0) {
                throw new SysException("备份失败");
            }
        } else {
            throw new SysException("备份失败");
        }
        return num;
    }


    //  2、恢复

    public Object updateDataBase(DataBaseBean dataBaseBean,String img_path) throws SysException {
        String command2 = "/usr/bin/mysql -uroot -p123456 --default-character-set=utf8 database";
        String savePath2 = img_path + "/database/" + dataBaseBean.getData_name();
        boolean b2 = DataBaseUtils.recover(command2, savePath2);
        if (b2) {
            return "1";
        } else {
            throw new SysException("还原失败");
        }

    }

    public static void main(String[] args) {
        DataBaseUtils dataBaseUtils=new DataBaseUtils();

    }


}