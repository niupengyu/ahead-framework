package com.github.niupengyu.web.headler;

import com.github.niupengyu.web.beans.ResponseData;
import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.web.content.ClientContent;
import com.github.niupengyu.web.exception.CasException;
import com.github.niupengyu.web.exception.RequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice(annotations = RestController.class,assignableTypes = ClientContent.class)
@ResponseBody
public class GlobalExceptionHandler1 {

    private static final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler1.class);

    @ExceptionHandler(value = SysException.class)
    @ResponseBody
    public ResponseData baseErrorHandler(HttpServletRequest req, HttpServletResponse response, SysException e){
        logger.error("GlobalExceptionHandler1---SysException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
        response.setContentType(ClientContent.CONTENT_JSON);
        ResponseData responseData=new ResponseData();
        responseData.setCode(e.getCode());
        //e.printStackTrace();
        responseData.setToken(req.getSession().getId());
        responseData.setTimestamp(System.currentTimeMillis());
        responseData.setMessage(e.getMessage());
        responseData.setState(false);
        return responseData;
    }

    @ExceptionHandler(value = RequestException.class)
    @ResponseBody
    public ResponseData requestException(HttpServletRequest req, HttpServletResponse response, RequestException e){
        logger.error("---GlobalExceptionHandler1 RequestException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
        response.setContentType(ClientContent.CONTENT_JSON);
        ResponseData responseData=new ResponseData();
        responseData.setCode(e.getCode());
        //e.printStackTrace();
        responseData.setToken(req.getSession().getId());
        response.setStatus(e.getCode());
        responseData.setTimestamp(System.currentTimeMillis());
        responseData.setMessage(e.getMessage());
        responseData.setState(false);
        return responseData;
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public ResponseData defaultErrorHandler(HttpServletRequest req, HttpServletResponse response, NoHandlerFoundException e){
        logger.error("---GlobalExceptionHandler1 DefaultException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
        response.setContentType(ClientContent.CONTENT_JSON);
        ResponseData responseData=new ResponseData();
        responseData.setCode(404);
        //e.printStackTrace();
        responseData.setToken(req.getSession().getId());
        responseData.setTimestamp(System.currentTimeMillis());
        responseData.setMessage(e.getMessage());
        responseData.setState(false);
        return responseData;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseData defaultErrorHandler(HttpServletRequest req, HttpServletResponse response, Exception e){
        logger.error("GlobalExceptionHandler1---Exception Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage(),e);
        response.setContentType(ClientContent.CONTENT_JSON);
        ResponseData responseData=new ResponseData();
//        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
//            responseData.setCode(404);
//        } else if(e instanceof SysException){
//            SysException e1=SysException.class.cast(e);
//            responseData.setCode(e1.getCode());
//        }else{
            responseData.setCode(500);
//        }
        e.printStackTrace();
        responseData.setToken(req.getSession().getId());
        responseData.setTimestamp(System.currentTimeMillis());
        responseData.setMessage(e.getMessage());
        responseData.setState(false);
        return responseData;
    }


}
