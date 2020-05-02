package com.github.niupengyu.commons.sftp;

import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.core.util.FileUtil;
import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.core.util.data.NumberUtil;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SFtpUtil {

    private static final Logger logger= LoggerFactory.getLogger(SFtpUtil.class);

    private Session session=null;

    private String ip;

    public Session openSession(String ip,String user,int port,String psw,int time){
        this.ip=ip;
        JSch jsch = new JSch();
        try {
            if(port <=0){
                //连接服务器，采用默认端口
                session = jsch.getSession(user, ip);
            }else{
                //采用指定的端口连接服务器
                session = jsch.getSession(user, ip ,port);
            }
            //如果服务器连接不上，则抛出异常
            if (session == null) {
                throw new Exception("session is null");
            }
            //设置登陆主机的密码
            session.setPassword(psw);//设置密码
            //设置第一次登陆的时候提示，可选值：(ask | yes | no)
            session.setConfig("StrictHostKeyChecking", "no");
            //设置登陆超时时间
            session.connect(time);
            logger.info("连接成功");
            //创建sftp通信通道
        } catch (Exception e) {
            disconnect();
            //e.printStackTrace();
            logger.error("连接失败 "+ip +" "+e.getMessage(),e);
        }
        return session;

    }

    public ChannelSftp openChannelSftp(){
        Channel channel = null;
        try {
            channel = session.openChannel("sftp");
            channel.connect(1000);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        ChannelSftp sftp = (ChannelSftp) channel;
        return sftp;
    }

    public List<String> execCommand(String command) throws SysException {
        logger.info(ip+" command "+command);
        if(StringUtil.isNull(command)){
            throw new SysException("命令空");
        }
//        StringBuffer result = new StringBuffer();
        List<String> results=new ArrayList<>();
        ChannelExec openChannel=null;
        try {
            if(!isConnected()){
                session.connect();
            }
            openChannel = (ChannelExec) session.openChannel("exec");
            openChannel.setCommand(command);
            openChannel.connect();
            InputStream in = openChannel.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));

            String tmpStr = "";
            while ((tmpStr = reader.readLine()) != null) {
//                result.append(new String(tmpStr.getBytes("utf-8"), "UTF-8")).append("\n");
                results.add(new String(tmpStr.getBytes("utf-8"), "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new SysException("查询失败");
        } finally {
            if(openChannel!=null){
                openChannel.disconnect();
            }
            openChannel=null;
        }
        //logger.info("results "+results);
        return results;
    }


    public List<String> runShell(String cmd) throws Exception {
        ChannelShell channel=(ChannelShell)this.session.openChannel("shell");
        InputStream instream = null;
        OutputStream outstream = null;
        List<String> results=new ArrayList<>();
        try{
            instream = channel.getInputStream();
            channel.setPty(true);
            channel.connect();
            outstream = channel.getOutputStream();
            outstream.write(cmd.getBytes());
            outstream.flush();
            TimeUnit.SECONDS.sleep(3);
            byte[] tmp=new byte[1024];
            boolean flag=true;
            while(flag){
                if(instream.available()<1){
                    break;
                }
                while(instream.available()>0){
                    int i=instream.read(tmp, 0, 1024);
                    if(i<0){
                        flag=false;
                        break;
                    }
                    String s = new String(tmp, 0, i);
                    if (s.indexOf("--More--") >= 0 ) {
                        outstream.write((" ").getBytes());
                        outstream.flush();
                    }
                    results.add(s);
                }
                if(channel.isClosed()){
                    results.add("exit-status: "+channel.getExitStatus());
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            FileUtil.close(instream);
            FileUtil.close(outstream);
            if(channel!=null&&channel.isConnected()){
                channel.disconnect();
            }
        }
        return results;
    }



    public void disconnect(){
        if (isConnected()) {
            session.disconnect();
        }
        session=null;
    }

    public boolean isConnected(){
        if (session != null ) {
            return session.isConnected();
        }
        return false;
    }


    public static void main(String[] args) throws SysException {
        String str="mysql    21061     1  0 11月22 ?      02:18:17 /usr/sbin/mysqld";
        SFtpUtil ftpUtil=new SFtpUtil();
        ftpUtil.openSession("localhost","root",22,"123456",2000);
        List<String> processes=ftpUtil.execCommand("ps -aux");
        processes.remove(0);
        for(String line:processes){
            String[] results=ftpUtil.resultInfo(line);
            String pid=results[1];
            String cpu=results[2];
            String mem=results[3];
            String vsz=results[4];
            String rss=results[5];
            String tty=results[6];
            String stat=results[7];
            String start=results[8];
            String time=results[9];
        }
    }


    public static void close(SFtpUtil ftpUtil) {
        if(ftpUtil!=null){
            ftpUtil.disconnect();
        }
    }

    public static boolean isConnected(SFtpUtil ftpUtil) {
        if(ftpUtil!=null){
            return ftpUtil.isConnected();
        }
        return false;
    }

    @Override
    public String toString() {
        return "session ="+ip+"  " + (isConnected());
    }

    public String cpu() throws SysException {
        String command="cat /proc/cpuinfo | grep name | cut -f2 -d: | uniq -c";
        List<String> results=this.execCommand(command);
        return this.resultInfo(results,1);
    }

    public String processor() throws SysException {
        String command="grep 'processor' /proc/cpuinfo | sort -u | wc -l";
        List<String> results=this.execCommand(command);
        return this.resultInfo(results,0);
    }



    public String memory() throws SysException {
        String command="cat /proc/meminfo | grep MemTotal";
        List<String> results=this.execCommand(command);
        String num=this.resultInfo(results,1);
        if(StringUtil.isNull(num)){
            return "";
        }
        return FileUtil.convertFileSize(Long.parseLong(num+"000"));
    }

    public String cpuInfo() throws SysException {

        String command="cat /proc/stat";
        List<String> results=this.execCommand(command);
        if(StringUtil.listIsNull(results)){
            return null;
        }
        return results.get(0);

    }

    public double cpuUsing() throws SysException {
        String res1=this.cpuInfo();
        String res2=this.cpuInfo();
        String[] list1=this.resultInfo(res1);
        String[] list2=this.resultInfo(res2);
        long total1=this.cpuTotal(list1);
        int ide1=Integer.parseInt(list1[4]);
        long total2=this.cpuTotal(list2);
        int ide2=Integer.parseInt(list2[4]);
        double total=total2-total1;
        double ide=ide2-ide1;
        double using= NumberUtil.decimalFormat((total-ide)/total,2);
        return using;
    }

    public List<String> processInfo() throws SysException {
        List<String> processes=this.execCommand("ps -aux");
        if(StringUtil.listIsNull(processes)){
            return null;
        }
        processes.remove(0);
        return processes;
    }


    public static String codeInfo(String[] messages,int index){
        /*int i=0;
        String temp="";
        for(String str:messages){
            temp=str.trim();
            if(!StringUtil.isNull(temp)){
                i++;
            }
            if(i==index){
                break;
            }
        }
        return temp;*/
        return messages[index];
    }

    public static String[] resultInfo(String message){
        //String temp="";
        //List<String> list=new ArrayList<>();
        String[] messages=message.split("\\s+");
        /*for(String str:messages){
            temp=str.trim();
            if(StringUtil.notNull(temp)){
                list.add(temp);
            }
        }*/
        return messages;
    }

    public static long cpuTotal(String[] list) {
        long total=0;
        for(int i=1;i<list.length;i++){
            String num=list[i];
            total+=Long.parseLong(num);
        }
        return total;
    }

    public static String processCode(List<String> str,String name) throws SysException {
        String process=null;
        for(String message:str){
            if(message!=null&&message.indexOf(name)>-1&&message.indexOf("grep "+name)<0){
                String[] messages=message.split("\\s+");
                process=codeInfo(messages,1);
            }
        }
        return process;
    }

    private static String resultInfo(List<String> results, int i) {
        if(StringUtil.isNull(results)){
            return "";
        }
        String[] messages=results.get(0).split("\\s+");
        String process=codeInfo(messages,i);
        return process;
    }


}
