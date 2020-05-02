package com.github.niupengyu.commons.http;

public class Test {

    public static void main(String[] args) throws Exception {
        HttpConfiguration httpConfiguration=new HttpConfiguration();
        HttpConfig httpConfig=new HttpConfig();
        httpConfig.setConnectTimeout(50000);
        httpConfig.setConnectionRequestTimeout(50000);
        httpConfig.setSocketTimeout(50000);
        httpConfiguration.setHttpConfig(httpConfig);
        HttpAPIService apiService=new HttpAPIService();
        apiService.setConfig(
                httpConfiguration.getRequestConfig(
                        httpConfiguration.getBuilder()));
        System.out.println(apiService.getConfig().getConnectionRequestTimeout());
        apiService.setHttpClient(
                httpConfiguration.getCloseableHttpClient(
                        httpConfiguration.getHttpClientBuilder(
                                httpConfiguration.getHttpClientConnectionManager())));
        String str=apiService.doGet("http://localhost:8083/system/timeout?timeout=10000");
        System.out.println(str);

    }

}
