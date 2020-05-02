package com.github.niupengyu.web.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

public class ServletUtil {

    public static void printlnHeader(HttpServletRequest request){
        Enumeration<String> enumeration=request.getHeaderNames();
        while (enumeration.hasMoreElements()){
            System.out.println(request.getHeader(enumeration.nextElement()));
        }
    }

    public static void printlnRequestParams(HttpServletRequest request){
        Enumeration<String> enumeration=request.getParameterNames();
        while (enumeration.hasMoreElements()){
            System.out.println(request.getParameter(enumeration.nextElement()));
            System.out.println(Arrays.toString(request.getParameterValues(enumeration.nextElement())));
        }
    }

}
