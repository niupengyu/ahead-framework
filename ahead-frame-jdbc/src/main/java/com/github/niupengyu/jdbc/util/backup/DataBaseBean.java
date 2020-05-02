package com.github.niupengyu.jdbc.util.backup;

public class DataBaseBean {

    private String data_name;

    private String size;

    private String url;

    private String version;

    public String getData_name() {
        return data_name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setData_name(String data_name) {
        this.data_name = data_name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }
}
