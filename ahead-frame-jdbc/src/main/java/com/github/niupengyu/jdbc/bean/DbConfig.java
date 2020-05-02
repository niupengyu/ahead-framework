package com.github.niupengyu.jdbc.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component("db")
@ConfigurationProperties(prefix="dbcp")
public class  DbConfig {

	private String name;

	private String url;

	private String username;

	private String password;

	private String driverClassName;

	private String className;

	private String[] urls;

	private DbUser[] users;

	private List<DataSourceBean> dataSources;

	private Integer initialSize;

	private Integer maxActive;

	private Integer maxIdle;

	private Integer minIdle;

	private Long maxWait;

	private String validationQuery;

	private Integer validationQueryTimeout;

	private Boolean testWhileIdle;

	private Boolean testOnBorrow;

	private Integer timeBetweenEvictionRunsMillis;

	private Integer numTestsPerEvictionRun;

	private Integer minEvictableIdleTimeMillis;

	private Boolean removeAbandoned;

	private Integer removeAbandonedTimeout;

	private Integer minPoolSize;

	private Integer maxPoolSize;

	private Integer borrowConnectionTimeout;

	private List<String> mappers;

	private String[] mappersXml;

	private String[] packagexml;

	private String xmlBase;

	private Boolean useColumnLabel;

	private Boolean mapUnderscoreToCamelCase;

	private Boolean callSettersOnNulls;

	//private Boolean pool;

	private Map<String,Object> prop;

	private Map<String,Object> configuration;

	private Boolean transaction;

	public DbConfig(){

	}

	public Map<String, Object> getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Map<String, Object> configuration) {
		this.configuration = configuration;
	}

	public Map<String, Object> getProp() {
		return prop;
	}

	public void setProp(Map<String, Object> prop) {
		this.prop = prop;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Boolean getCallSettersOnNulls() {
		return callSettersOnNulls;
	}

	public void setCallSettersOnNulls(Boolean callSettersOnNulls) {
		this.callSettersOnNulls = callSettersOnNulls;
	}

	public Boolean getUseColumnLabel() {
		return useColumnLabel;
	}

	public void setUseColumnLabel(Boolean useColumnLabel) {
		this.useColumnLabel = useColumnLabel;
	}

	public Boolean getMapUnderscoreToCamelCase() {
		return mapUnderscoreToCamelCase;
	}

	public void setMapUnderscoreToCamelCase(Boolean mapUnderscoreToCamelCase) {
		this.mapUnderscoreToCamelCase = mapUnderscoreToCamelCase;
	}

	public Boolean getTransaction() {
		return transaction;
	}

	public void setTransaction(Boolean transaction) {
		this.transaction = transaction;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public List<DataSourceBean> getDataSources() {
		return dataSources;
	}

	public void setDataSources(List<DataSourceBean> dataSources) {
		this.dataSources = dataSources;
	}

	public Integer getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}

	public Integer getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}

	public Integer getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public Long getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(Long maxWait) {
		this.maxWait = maxWait;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public Integer getValidationQueryTimeout() {
		return validationQueryTimeout;
	}

	public void setValidationQueryTimeout(Integer validationQueryTimeout) {
		this.validationQueryTimeout = validationQueryTimeout;
	}

	public Boolean getTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(Boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public Boolean getTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(Boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public Integer getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(Integer timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public Integer getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}

	public void setNumTestsPerEvictionRun(Integer numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	public Integer getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public Boolean getRemoveAbandoned() {
		return removeAbandoned;
	}

	public void setRemoveAbandoned(Boolean removeAbandoned) {
		this.removeAbandoned = removeAbandoned;
	}

	public Integer getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}

	public void setRemoveAbandonedTimeout(Integer removeAbandonedTimeout) {
		this.removeAbandonedTimeout = removeAbandonedTimeout;
	}

	public String[] getUrls() {
		return urls;
	}

	public void setUrls(String[] urls) {
		this.urls = urls;
	}

	public DbUser[] getUsers() {
		return users;
	}

	public void setUsers(DbUser[] users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(Integer minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public Integer getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public Integer getBorrowConnectionTimeout() {
		return borrowConnectionTimeout;
	}

	public void setBorrowConnectionTimeout(Integer borrowConnectionTimeout) {
		this.borrowConnectionTimeout = borrowConnectionTimeout;
	}

	public List<String> getMappers() {
		return mappers;
	}

	public void setMappers(List<String> mappers) {
		this.mappers = mappers;
	}

	public String[] getMappersXml() {
		return mappersXml;
	}

	public void setMappersXml(String[] mappersXml) {
		this.mappersXml = mappersXml;
	}

	public String[] getPackagexml() {
		return packagexml;
	}

	public void setPackagexml(String[] packagexml) {
		this.packagexml = packagexml;
	}

	public String getXmlBase() {
		return xmlBase;
	}

	public void setXmlBase(String xmlBase) {
		this.xmlBase = xmlBase;
	}

	/*public Boolean getPool() {
		return pool;
	}

	public void setPool(Boolean pool) {
		this.pool = pool;
	}*/

	@Override
	public String toString() {
		return "DbConfig{" +
				"driverClassName='" + driverClassName + '\'' +
				", urls=" + Arrays.toString(urls) +
				", users=" + Arrays.toString(users) +
				", dataSources=" + dataSources +
				", initialSize=" + initialSize +
				", maxActive=" + maxActive +
				", maxIdle=" + maxIdle +
				", minIdle=" + minIdle +
				", maxWait=" + maxWait +
				", validationQuery='" + validationQuery + '\'' +
				", validationQueryTimeout=" + validationQueryTimeout +
				", testWhileIdle=" + testWhileIdle +
				", testOnBorrow=" + testOnBorrow +
				", timeBetweenEvictionRunsMillis=" + timeBetweenEvictionRunsMillis +
				", numTestsPerEvictionRun=" + numTestsPerEvictionRun +
				", minEvictableIdleTimeMillis=" + minEvictableIdleTimeMillis +
				", removeAbandoned=" + removeAbandoned +
				", removeAbandonedTimeout=" + removeAbandonedTimeout +
				'}';
	}
}
