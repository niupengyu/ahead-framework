package com.github.niupengyu.jdbc.temp;

import com.github.niupengyu.core.util.DateUtil;

import java.time.LocalDateTime;

public class MillTest {

    public static void main(String[] args) {
        LocalDateTime ldt=LocalDateTime.now();
        System.out.println(DateUtil.toMilli(ldt));
        System.out.println(DateUtil.toSecond(ldt));
        String msg="会计师的联发科，说了的咖啡机";
        int index=msg.indexOf("，");
        System.out.println(msg.substring(0,index));
        System.out.println(msg.substring(index+1));
    }

}
