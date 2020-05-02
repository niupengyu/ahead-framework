package com.github.niupengyu.web.content;

import com.github.niupengyu.core.init.BaseSysinitBean;
import com.github.niupengyu.core.util.Content;
import com.github.niupengyu.core.util.FileUtil;
import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.web.util.NetworkUtil;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class RequestContent<T> implements java.io.Serializable{

    private transient ThreadLocal<ServletContent<T>> localContent=
            new ThreadLocal<>();

    protected static final long serialVersionUID = 1L;

    public static final String CONTENT_JSON="application/json;charset=UTF-8";

    private String addr="0";

    protected static final String MESSAGE="系统出错";

    protected static final String SUCCESS="操作成功";

    protected static final String URL="user/main";



    public String addr() {
        HttpServletRequest request=this.getServletContent().getRequest();
        if(request!=null){
            try
            {
                this.addr= NetworkUtil.getIpAddress(request);
            } catch (IOException e)
            {
                this.addr=request.getRemoteAddr();
            }
        }
        return addr;
    }

    public String filePath()
    {
        HttpSession session=this.getServletContent().getSession();
        if(session==null){
            return "";
        }
        String root= BaseSysinitBean.getValue("news.file-config.webappPath");
        String path=session.getServletContext().getRealPath("/") + root;
        File file=new File(path);
        if(!file.isDirectory()){
            file.mkdirs();
        }
        return path;
    }

    public String filePath(String subPath)
    {
        HttpSession session=this.getServletContent().getSession();
        if(session==null){
            return "";
        }
        String root=BaseSysinitBean.getValue("news.file-config.webappPath");
        String path=session.getServletContext().getRealPath("/") + root+ Content.BACKSLASH+subPath;
        File file=new File(path);
        if(!file.isDirectory()){
            file.mkdirs();
        }
        return path;
    }

    public String diskFilePath(String subPath)
    {
        return BaseSysinitBean.diskFilePath(subPath);
    }

    public String diskFilePath()
    {
        return BaseSysinitBean.diskFilePath();
    }

    public String realPath(){
        HttpSession session=this.getServletContent().getSession();
        if(session==null){
            return "";
        }
        return session.getServletContext().getRealPath("/");
    }


    public void setServletContent(HttpServletRequest request,HttpServletResponse response){
        ServletContent content=this.getServletContent();
        content.setRequest(request);
        content.setResponse(response);
        //this.servletContent.set();
        //T login=setLogin(request,response);
        //this.servletContent.set(new ServletContent(request,response,login));
    }

    public void initLogin(T login){
        //T login=setLogin(request,response);
        ServletContent content=this.getServletContent();
        content.login=login;
    }


    //public abstract T setLogin(HttpServletRequest request, HttpServletResponse response);

    public T getLogin(){
        return this.getServletContent().login;
    }

    public ServletContent<T> getServletContent() {
        ServletContent content=localContent.get();
        if(content==null){
            content=new ServletContent();
            localContent.set(content);
        }
        return content;
    }


    protected String getServer(){
        HttpServletRequest request=request();
        String server=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
        return server;
    }

    protected String getServer(HttpServletRequest request){
        String server=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
        return server;
    }

    protected String getServerUrl(HttpServletRequest request,String path){
        String server=request.getRequestURL().toString();
        server=server.substring(0,server.indexOf(path)+1);
        return server;
    }

    protected String getServerUrl(String path){
        String server=request().getRequestURL().toString();
        server=server.substring(0,server.indexOf(path)+1);
        return server;
    }

    protected HttpSession session() {
        return this.getServletContent().getSession();
    }

    protected HttpServletRequest request() {
        return this.getServletContent().getRequest();
    }

    protected HttpServletResponse response() {
        return this.getServletContent().getResponse();
    }

    protected String token() {
        return this.getServletContent().getToken();
    }


    static class ServletContent<T>{

        private HttpServletRequest request;

        private HttpServletResponse response;

        private HttpSession session;

        private String token;

        private T login;

        public ServletContent(){

        }

        public ServletContent(HttpServletRequest request,
                              HttpServletResponse response,T login) {
            super();
            this.request=request;
            this.response=response;
            if(request!=null){
                this.session=request.getSession();
            }
            this.login=login;
//			this.token=request.getHeader("Auth-Token");
        }


        public HttpServletRequest getRequest() {
            return request;
        }

        public void setRequest(HttpServletRequest request) {
            this.request = request;
            if(request!=null){
                this.session=request.getSession();
            }
        }

        public HttpServletResponse getResponse() {
            return response;
        }

        public void setResponse(HttpServletResponse response) {
            this.response = response;
        }

        public HttpSession getSession() {
            return session;
        }

        public void setSession(HttpSession session) {
            this.session = session;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    protected List<MultipartFile> getFiles(){
        List<MultipartFile> files=new ArrayList<>();
        HttpServletRequest request= request();
        if(request instanceof MultipartHttpServletRequest){
            MultipartHttpServletRequest req=(MultipartHttpServletRequest)request;
            MultiValueMap<String, MultipartFile> multiValueMap = req.getMultiFileMap();
            int i=0;
            for(Map.Entry<String, List<MultipartFile>> e: multiValueMap.entrySet()){
                files.addAll(e.getValue());
            }
        }
        return files;
    }

    protected void download(String path,String name){
        download(new File(path),name);
    }

    protected void download(File file,String name) {
        HttpServletRequest request=request();
        HttpServletResponse response=response();
        long fSize = file.length();
        // 下载
        response.setContentType("application/x-download");
        String isoFileName = null;
        try(ServletOutputStream out = response.getOutputStream();
            BufferedOutputStream bufferOut = new BufferedOutputStream(out);
            InputStream inputStream = new FileInputStream(file)) {
            isoFileName = URLEncoder.encode(name, "UTF-8");
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Length", String.valueOf(fSize));
            response.setHeader("Content-Disposition", "attachment; filename="
                    + isoFileName);
            long pos = 0;
            if (null != request.getHeader("Range")) {
                // 断点续传
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                try {
                    pos = Long.parseLong(request.getHeader("Range").replaceAll(
                            "bytes=", "").replaceAll("-", ""));
                } catch (NumberFormatException e) {
                    pos = 0;
                }
            }

            String contentRange = new StringBuffer("bytes ").append(
                    new Long(pos).toString()).append("-").append(
                    new Long(fSize - 1).toString()).append("/").append(
                    new Long(fSize).toString()).toString();
            response.setHeader("Content-Range", contentRange);
            inputStream.skip(pos);
            byte[] buffer = new byte[5 * 1024];
            int length = 0;
            while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                bufferOut.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //bufferOut.flush();
            //bufferOut.close();
            //out.close();
            //inputStream.close();
        }


        /*String range=request.getHeader("Range");
        if (file.isFile()){
            //开始下载位置
            long startByte = 0;
            //结束下载位置
            long endByte = file.length() - 1;
            //有range的话
            if (range != null && range.contains("bytes=") && range.contains("-")) {
                range = range.substring(range.lastIndexOf("=") + 1).trim();
                String ranges[] = range.split("-");
                try {
                    //判断range的类型
                    if (ranges.length == 1) {
                        //类型一：bytes=-2343
                        if (range.startsWith("-")) {
                            endByte = Long.parseLong(ranges[0]);
                        }
                        //类型二：bytes=2343-
                        else if (range.endsWith("-")) {
                            startByte = Long.parseLong(ranges[0]);
                        }
                    }
                    //类型三：bytes=22-2343
                    else if (ranges.length == 2) {
                        startByte = Long.parseLong(ranges[0]);
                        endByte = Long.parseLong(ranges[1]);
                    }

                } catch (NumberFormatException e) {
                    startByte = 0;
                    endByte = file.length() - 1;
                }
            }

            //要下载的长度（为啥要加一问小学数学老师去）
            long contentLength = endByte - startByte + 1;
            //文件名
            String fileName = file.getName();
            //文件类型
            //String contentType = request.getServletContext().getMimeType(fileName);
            name= StringUtil.isNull(name)?file.getName():name;
            String contentType=FileUtil.setContentType(fileName);
            BufferedOutputStream outputStream = null;
            RandomAccessFile randomAccessFile = null;
            try {
                //各种响应头设置
                //坑爹地方一：看代码
                response.setHeader("Accept-Ranges", "bytes");
                //坑爹地方二：http状态码要为206
                response.setStatus(response.SC_PARTIAL_CONTENT);
                response.setContentType(contentType);
                response.setHeader("Content-Type", contentType);
                //这里文件名换你想要的，inline表示浏览器直接实用（我方便测试用的）
                response.setHeader("Content-Disposition", "inline;filename="
                        .concat(String.valueOf(URLEncoder.encode(name, "UTF-8")))
                );
                response.setHeader("Content-Length", String.valueOf(contentLength));
                //坑爹地方三：Content-Range，格式为
                // [要下载的开始位置]-[结束位置]/[文件总大小]
                response.setHeader("Content-Range", "bytes " + startByte + "-" + endByte + "/" + file.length());


                //已传送数据大小
                long transmitted = 0;
                randomAccessFile = new RandomAccessFile(file, "r");
                outputStream = new BufferedOutputStream(response.getOutputStream());
                byte[] buff = new byte[4096];
                int len = 0;
                randomAccessFile.seek(startByte);
                //坑爹地方四：判断是否到了最后不足4096（buff的length）个byte这个逻辑（(transmitted + len) <= contentLength）要放前面！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
                //不然会会先读取randomAccessFile，造成后面读取位置出错，找了一天才发现问题所在
                while ((transmitted + len) <= contentLength && (len = randomAccessFile.read(buff)) != -1) {
                    outputStream.write(buff, 0, len);
                    transmitted += len;
                    //停一下，方便测试，用的时候删了就行了
                    //Thread.sleep(10);
                }
                //处理不足buff.length部分
                if (transmitted < contentLength) {
                    len = randomAccessFile.read(buff, 0, (int) (contentLength - transmitted));
                    outputStream.write(buff, 0, len);
                    transmitted += len;
                }

                outputStream.flush();
                response.flushBuffer();
                randomAccessFile.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }

    protected void download1(String path,String filename){
        download1(new File(path),filename);
    }

    protected void download1(File file,String filename){
        BufferedInputStream bis = null;
        HttpServletRequest request=request();
        HttpServletResponse response=response();
        try {
            String name= StringUtil.isNull(filename)?file.getName():filename;
            response.setHeader("content-type", "application/octet-stream");
            //response.setContentType("application/octet-stream");
            response.setContentType(FileUtil.setContentType(name));
//			System.out.println(file.getName());
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(name, "UTF-8"))));
            byte[] buff = new byte[1024];

            OutputStream os = null;

            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
