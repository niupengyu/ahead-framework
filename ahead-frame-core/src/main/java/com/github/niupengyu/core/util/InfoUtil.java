package com.github.niupengyu.core.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfoUtil {

    static String regex ="([1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx])|([1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3})";

    public static Set<String> idCard(String text){
        Set<String> list = new HashSet<>();
        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(text);
        while(m.find()){
            list.add(m.group());
        }
        return list;
    }

}
