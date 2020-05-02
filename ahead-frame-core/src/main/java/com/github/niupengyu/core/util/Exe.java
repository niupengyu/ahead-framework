package com.github.niupengyu.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Exe {

    public List<String> execCommand(String command){
        List<String> message=new ArrayList<>();
        Process pro1=null;
        BufferedReader in1=null;
        try {
            Runtime r = Runtime.getRuntime();
            pro1 = r.exec(command);
            in1 = new BufferedReader(new InputStreamReader(pro1.getInputStream()));
            String line = null;
            while((line=in1.readLine()) != null){
                message.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in1!=null){
                try {
                    in1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    in1=null;
                }
            }
            if(pro1!=null){
                pro1.destroy();
                pro1=null;
            }
        }
        return message;
    }

}
