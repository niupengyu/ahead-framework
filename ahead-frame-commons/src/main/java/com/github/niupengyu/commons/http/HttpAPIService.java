package com.github.niupengyu.commons.http;

import com.github.niupengyu.core.annotation.AutoConfig;
import com.github.niupengyu.core.util.Content;
import com.github.niupengyu.core.util.FileUtil;
import com.github.niupengyu.core.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service("httpAPIService")
@AutoConfig(name = "news.http-config.enable")
public class HttpAPIService {

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;

    private static final Logger logger = LoggerFactory.getLogger(HttpAPIService.class);

    public HttpAPIService() {

    }

    /**
     * 不带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    @Deprecated
    public String doGet(String url) throws Exception {
        logger.debug(url);
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);

        // 装载配置信息
        httpGet.setConfig(config);

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);

        // 判断状态码是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            // 返回响应体的内容
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return null;
    }

    public HttpResult doGetResult(String url,Map<String,String> map) throws Exception {
        logger.debug(url);
        URIBuilder uriBuilder = new URIBuilder(url);
        if (map != null) {
            Iterator var4 = map.entrySet().iterator();

            while(var4.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry)var4.next();
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }
        HttpGet httpGet = new HttpGet(uriBuilder.build().toString());
        httpGet.setConfig(this.config);
        CloseableHttpResponse response = this.httpClient.execute(httpGet);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), "UTF-8"));
    }

    public HttpResult doGetResult(String url) throws Exception {
        logger.debug(url);
        URIBuilder uriBuilder = new URIBuilder(url);
        HttpGet httpGet = new HttpGet(uriBuilder.build().toString());
        httpGet.setConfig(this.config);
        CloseableHttpResponse response = this.httpClient.execute(httpGet);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), "UTF-8"));
    }

    /**
     * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url, Map<String, String> map) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (map != null) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, String> entry : map.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }
        // 调用不带参数的get请求
        return this.doGet(uriBuilder.build().toString());

    }

    /**
     * 带参数的post请求
     *
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url, Map<String, String> map) throws Exception {
        logger.debug("doPost " + url);
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (map != null) {
            List<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            // 构造from表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
            // 把表单放到post里
            httpPost.setEntity(urlEncodedFormEntity);

        }

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }

    public HttpResult doPost(String url, Map<String, String> map,Map<String,String> headers) throws Exception {
        logger.debug("doPost " + url);
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);
        if(StringUtil.mapNotNull(headers)){
            for(Map.Entry<String,String> entry:headers.entrySet()){
                httpPost.addHeader(entry.getKey(),entry.getValue());
            }
        }
        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (map != null) {
            List<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            // 构造from表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

            // 把表单放到post里
            httpPost.setEntity(urlEncodedFormEntity);
        }

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }


    public HttpResult sendFile(String url, Map<String, String> map,
                                Map<String,String> headers,Map<String,File> fileMap) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        if(StringUtil.mapNotNull(headers)){
            for(Map.Entry<String,String> entry:headers.entrySet()){
                httpPost.addHeader(entry.getKey(),entry.getValue());
            }
        }
        //加上该header访问会404,不知道原因...
        //httpPost.setHeader("Content-Type", "multipart/form-data; boundary=-----ZR8KqAYJyI2jPdddL");


        MultipartEntityBuilder multipartEntityBuilder=MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                multipartEntityBuilder.addPart(entry.getKey(),
                        new StringBody(entry.getValue(), Charset.defaultCharset()));
            }
        }


        if (fileMap != null) {
            for (Map.Entry<String, File> entry : fileMap.entrySet()) {
                multipartEntityBuilder.addPart(entry.getKey(), new FileBody(entry.getValue()));
            }
        }

        HttpEntity reqEntity = multipartEntityBuilder.build();


        httpPost.setEntity(reqEntity);
        HttpResponse response = httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }


    /**
     * 带参数的post请求
     *
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    public HttpResult doJson(String url, String json) throws Exception {
        logger.debug("doPost " + url);
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        // 构造from表单对象
        StringEntity urlEncodedFormEntity = new StringEntity(json, "UTF-8");

        // 把表单放到post里
        httpPost.setEntity(urlEncodedFormEntity);
        httpPost.addHeader("Content-Type", "application/json");
        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }

    public HttpResult doJson(String url, String json,Map<String,String> headers) throws Exception {
        logger.debug("doPost " + url);
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        // 构造from表单对象
        StringEntity urlEncodedFormEntity = new StringEntity(json, "UTF-8");

        // 把表单放到post里
        httpPost.setEntity(urlEncodedFormEntity);
        if(StringUtil.mapNotNull(headers)){
            for(Map.Entry<String,String> entry:headers.entrySet()){
                httpPost.addHeader(entry.getKey(),entry.getValue());
            }
        }
        httpPost.addHeader("Content-Type", "application/json");
        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }

    public HttpResult doXml(String url, String xml) throws Exception {
        logger.debug("doXml " + url);
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);
        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        // 构造from表单对象
        StringEntity urlEncodedFormEntity = new StringEntity(xml, "UTF-8");
        // 把表单放到post里
        httpPost.setEntity(urlEncodedFormEntity);
        httpPost.addHeader("Content-Type", "text/xml");
        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }

    public HttpResult doXml(String url, String xml,Map<String,String> headers) throws Exception {
        logger.debug("doXml " + url);
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);
        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        // 构造from表单对象
        StringEntity urlEncodedFormEntity = new StringEntity(xml, "UTF-8");
        // 把表单放到post里
        httpPost.setEntity(urlEncodedFormEntity);
        if(StringUtil.mapNotNull(headers)){
            for(Map.Entry<String,String> entry:headers.entrySet()){
                httpPost.addHeader(entry.getKey(),entry.getValue());
            }
        }
        httpPost.addHeader("Content-Type", "text/xml");
        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }

    public HttpResult doPut(String url, String json) throws Exception {
        logger.debug("doPost " + url);
        // 声明httpPost请求
        HttpPut httpPut = new HttpPut(url);
        // 加入配置信息
        httpPut.setConfig(config);

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        // 构造from表单对象
        StringEntity urlEncodedFormEntity = new StringEntity(json, "UTF-8");

        // 把表单放到post里
        httpPut.setEntity(urlEncodedFormEntity);
        httpPut.addHeader("Content-Type", "application/json");
        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPut);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }

    public HttpResult doPut(String url, String json,Map<String,String> headers) throws Exception {
        logger.debug("doPost " + url);
        // 声明httpPost请求
        HttpPut httpPut = new HttpPut(url);
        // 加入配置信息
        httpPut.setConfig(config);

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        // 构造from表单对象
        StringEntity urlEncodedFormEntity = new StringEntity(json, "UTF-8");

        // 把表单放到post里
        httpPut.setEntity(urlEncodedFormEntity);
        httpPut.addHeader("Content-Type", "application/json");
        if(StringUtil.mapNotNull(headers)){
            for(Map.Entry<String,String> entry:headers.entrySet()){
                httpPut.addHeader(entry.getKey(),entry.getValue());
            }
        }
        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPut);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }

    /**
     * 下载
     *
     * @param url
     * @return
     * @throws Exception
     */
    public File download(String url, String localPath) {
        logger.debug("download " + url);
        // 声明 http get 请求
        InputStream in = null;
        OutputStream out = null;
        HttpGet httpGet = new HttpGet(url);
        // 装载配置信息
        httpGet.setConfig(config);
        // 发起请求
        CloseableHttpResponse response = null;
        File file = null;
        try {
            response = this.httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            in = entity.getContent();
            long length = entity.getContentLength();
            if (length <= 0) {
                return null;
            }
            String localFileName = filename(url, response);
            file = new File(localPath + localFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new FileOutputStream(file);
            IOUtils.copy(in, out);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtil.close(in);
            FileUtil.close(out);
        }
        return null;
    }

    /**
     * 不带参数post请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url) throws Exception {
        return this.doPost(url, null);
    }


    public static String getFileName(HttpResponse response) throws Exception {
        String filename = null;
        
        /*Header headers[] = response.getAllHeaders();
        int ii = 0;
		while (ii < headers.length) {
			System.out.println(headers[ii].getName() + ":"
					+ headers[ii].getValue());
			++ii;
		}*/
        Header contentHeader = response.getFirstHeader("Content-Disposition");
        if (contentHeader != null) {
            HeaderElement[] values = contentHeader.getElements();
            if (values.length == 1) {
                NameValuePair param = values[0].getParameterByName("filename");
                if (param != null) {
                    //filename = new String(param.getValue().toString().getBytes(), "utf-8");
                    filename = URLDecoder.decode(param.getValue(), Content.UTF8);
                    //filename = param.getValue();
                }
            }
        }

        return filename;
    }

    public static String filename(String url, HttpResponse response) {
        String file = "异常.txt";
//        String str="sdfsd/sdfsdf/1sdfsdf.pdf";
        try {
            String last = url.substring(url.lastIndexOf("/") + 1);
            if (last.indexOf(".") > -1) {
                file = last;
            } else {
                file = getFileName(response);
            }
        } catch (Exception e) {
            logger.debug("识别文件名异常 " + url);
        }
        return file;
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public RequestConfig getConfig() {
        return config;
    }

    public void setConfig(RequestConfig config) {
        this.config = config;
    }
}