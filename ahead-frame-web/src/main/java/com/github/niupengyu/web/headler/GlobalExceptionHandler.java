package com.github.niupengyu.web.headler;

import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.web.content.ClientContent;
import com.github.niupengyu.web.content.ContentController;
import com.github.niupengyu.web.exception.CasException;
import com.github.niupengyu.web.exception.RequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice(annotations = Controller.class,assignableTypes = ContentController.class)
public class GlobalExceptionHandler {

    private static final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /*@ExceptionHandler(value = SysException.class)
    public ModelAndView baseErrorHandler(HttpServletRequest req, HttpServletResponse response, SysException e){
        logger.error("---GlobalExceptionHandler SysException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage(),e);
        ModelAndView modelAndView=new ModelAndView();
        System.out.println("111111````````````222222222222211");
        modelAndView.setViewName("/error");
        modelAndView.addObject("code",e.getCode());
        modelAndView.addObject("timestamp",System.currentTimeMillis());
        modelAndView.addObject("error",e.getMessage());
        modelAndView.addObject("status",e.getCode());
        modelAndView.addObject("message",e.getMessage());
        response.setCharacterEncoding("utf-8");
        return modelAndView;
    }*/


    @ExceptionHandler(value = CasException.class)
    public void requestException(HttpServletRequest req, HttpServletResponse response, CasException e) throws IOException {
        logger.error("---GlobalExceptionHandler CasException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage(),e);
        logger.error("GlobalExceptionHandler1---CasException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage(),e);
        response.sendRedirect(e.getMessage());
    }

    /*@ExceptionHandler(value = NoHandlerFoundException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, HttpServletResponse response, NoHandlerFoundException e){
        logger.error("---GlobalExceptionHandler NoHandlerFoundException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage(),e);
        ModelAndView modelAndView=new ModelAndView();
        System.out.println("222````````````````222222222");
        modelAndView.setViewName("/error");
        modelAndView.addObject("code",404);
        modelAndView.addObject("timestamp",System.currentTimeMillis());
        modelAndView.addObject("error",e.getMessage());
        modelAndView.addObject("status",404);
        modelAndView.addObject("message",e.getMessage());
        response.setCharacterEncoding("utf-8");
        return modelAndView;
    }*/

    /*@ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, HttpServletResponse response, Exception e){
        logger.error("--- GlobalExceptionHandler Exception Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage(),e);
        ModelAndView modelAndView=new ModelAndView();
        System.out.println("3333````````````33333333");
        modelAndView.setViewName("/error");
        modelAndView.addObject("code",500);
        modelAndView.addObject("timestamp",System.currentTimeMillis());
        modelAndView.addObject("error",e.getMessage());
        modelAndView.addObject("status",500);
        modelAndView.addObject("message",e.getMessage());
        response.setCharacterEncoding("utf-8");
        return modelAndView;
    }*/

}
