package com.github.niupengyu.commons.http;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("httpConfig")
@ConfigurationProperties(prefix="news.http-config")
public class HttpConfig {

	private int maxTotal=10;
    private int defaultMaxPerRoute=5;
	private int maxPerRoute=5;
	private int socketTimeout=5000;
	private int connectTimeout=50000;
	private int connectionRequestTimeout=5000;
	private String protocol="http";
	private boolean authentication=false;
	private boolean authType;
	private String keyStore;
	private String trustStore;
	private String keyStorepass;
	private String trustStorepass;
	private String algorithm;
	private String charSet="UTF-8";
	
	public int getMaxTotal() {
		return maxTotal;
	}
	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}
	
	public int getDefaultMaxPerRoute() {
		return defaultMaxPerRoute;
	}
	public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
		this.defaultMaxPerRoute = defaultMaxPerRoute;
	}
	public int getMaxPerRoute() {
		return maxPerRoute;
	}
	public void setMaxPerRoute(int maxPerRoute) {
		this.maxPerRoute = maxPerRoute;
	}
	public int getSocketTimeout() {
		return socketTimeout;
	}
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	public int getConnectTimeout() {
		return connectTimeout;
	}
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	public int getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}
	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}
	
	
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public boolean isAuthentication() {
		return authentication;
	}
	public void setAuthentication(boolean authentication) {
		this.authentication = authentication;
	}
	public boolean isAuthType() {
		return authType;
	}
	public void setAuthType(boolean authType) {
		this.authType = authType;
	}
	public String getKeyStore() {
		return keyStore;
	}
	public void setKeyStore(String keyStore) {
		this.keyStore = keyStore;
	}
	public String getTrustStore() {
		return trustStore;
	}
	public void setTrustStore(String trustStore) {
		this.trustStore = trustStore;
	}
	public String getKeyStorepass() {
		return keyStorepass;
	}
	public void setKeyStorepass(String keyStorepass) {
		this.keyStorepass = keyStorepass;
	}
	public String getTrustStorepass() {
		return trustStorepass;
	}
	public void setTrustStorepass(String trustStorepass) {
		this.trustStorepass = trustStorepass;
	}
	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	public String getCharSet() {
		return charSet;
	}
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}
	
	
 
}
