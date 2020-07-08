package com.github.niupengyu.jdbc.bean;

import com.github.niupengyu.core.bean.BaseConfig;

import java.util.List;
import java.util.Map;

public class DataSourceBean extends BaseConfig{

    private String name;

    private String driverClassName;

    private String url;

    private String username;

    private String password;

    private boolean xmlConfig;

    private Integer initialSize;

    private Integer maxActive;

    private Integer maxIdle;

    private Integer minIdle;

    private Long maxWait;

    private List<String> mappers;

    private String[] mappersXml;

    private String[] packagexml;

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

    private String xmlBase;

    private Boolean transaction;

    private Boolean pool;

    private Boolean useColumnLabel;

    private Boolean mapUnderscoreToCamelCase;

    private Boolean callSettersOnNulls;

    private Boolean enable;

    private String className;

    private Map<String,Object> prop;

    private MybatisConfiguration configuration;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public MybatisConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(MybatisConfiguration configuration) {
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

    public String getXmlBase() {
        return xmlBase;
    }

    public void setXmlBase(String xmlBase) {
        this.xmlBase = xmlBase;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMappers(List<String> mappers) {
        this.mappers = mappers;
    }

    public void setInitialSize(Integer initialSize) {
        this.initialSize = initialSize;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public void setMaxWait(Long maxWait) {
        this.maxWait = maxWait;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public void setValidationQueryTimeout(Integer validationQueryTimeout) {
        this.validationQueryTimeout = validationQueryTimeout;
    }

    public void setTestWhileIdle(Boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public void setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public void setTimeBetweenEvictionRunsMillis(Integer timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public void setNumTestsPerEvictionRun(Integer numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    public void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public void setRemoveAbandoned(Boolean removeAbandoned) {
        this.removeAbandoned = removeAbandoned;
    }

    public void setRemoveAbandonedTimeout(Integer removeAbandonedTimeout) {
        this.removeAbandonedTimeout = removeAbandonedTimeout;
    }

    public String getName() {
        return name;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getMappers() {
        return mappers;
    }

    public Integer getInitialSize() {
        return initialSize;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public Long getMaxWait() {
        return maxWait;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public Integer getValidationQueryTimeout() {
        return validationQueryTimeout;
    }

    public Boolean getTestWhileIdle() {
        return testWhileIdle;
    }

    public Boolean getTestOnBorrow() {
        return testOnBorrow;
    }

    public Integer getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public Integer getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    public Integer getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public Boolean getRemoveAbandoned() {
        return removeAbandoned;
    }

    public Integer getRemoveAbandonedTimeout() {
        return removeAbandonedTimeout;
    }

    public boolean getXmlConfig() {
        return xmlConfig;
    }

    public void setXmlConfig(boolean xmlConfig) {
        this.xmlConfig = xmlConfig;
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

    public boolean isXmlConfig() {
        return xmlConfig;
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

    public Boolean getTransaction() {
        return transaction;
    }

    public void setTransaction(Boolean transaction) {
        this.transaction = transaction;
    }

    public Boolean getPool() {
        return pool;
    }

    public void setPool(Boolean pool) {
        this.pool = pool;
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "name='" + name + '\'' +
                ", driverClassName='" + driverClassName + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mappers=" + mappers +
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
