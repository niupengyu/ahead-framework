package com.github.niupengyu.web.beans;

public class ResponseData {

    private String message;

    private int code;

    private Object data;

    private boolean state;

    private String token;

    private long timestamp;

    public ResponseData(){

    }

    public ResponseData(String token,boolean state,
                        String message,int code){
        this.setState(state);
        this.setMessage(message);
        this.setTimestamp(System.currentTimeMillis());
        this.setToken(token);
        this.setCode(code);
    }

    public ResponseData(String id, boolean stateSuccess, String success, int code, Object data) {
        this.setState(stateSuccess);
        this.setMessage(success);
        this.setTimestamp(System.currentTimeMillis());
        this.setToken(id);
        this.setCode(code);
        this.data=data;
    }

    public ResponseData(String id, boolean stateSuccess, String success, int code, String data) {
        this.setState(stateSuccess);
        this.setMessage(success);
        this.setTimestamp(System.currentTimeMillis());
        this.setToken(id);
        this.setCode(code);
        this.data=data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


}
