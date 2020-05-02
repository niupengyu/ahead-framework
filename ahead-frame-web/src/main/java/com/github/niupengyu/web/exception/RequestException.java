package com.github.niupengyu.web.exception;

public class RequestException extends Exception {

    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;

    private String message="ERROR";

    private int code=201;

    public RequestException()
    {
        new RequestException("M",2);
    }

    public RequestException(String message){
        this.message=message;
    }

    public RequestException(String message, int code){
        this.message=message;
        this.code=code;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public int getCode() {
        return code;
    }

}
