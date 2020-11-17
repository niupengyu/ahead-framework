package com.github.niupengyu.web.exception;

public class CasException extends Exception {

    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;

    private String message="ERROR";

    private int code=401;

    public CasException()
    {
        new CasException("M",2);
    }

    public CasException(String message){
        this.message=message;
    }

    public CasException(String message, int code){
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
