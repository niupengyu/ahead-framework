package com.github.niupengyu.core.init.console;

import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.core.init.InitService;
import com.github.niupengyu.core.util.StringUtil;
import org.springframework.boot.ApplicationArguments;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class ConsoleLineInitService implements InitService {

    public void run(ApplicationArguments applicationArguments) {
        String[] args=applicationArguments.getSourceArgs();
        try {
            if(StringUtil.arrNotNull(args)){
                Map<String,String> params=params(args);
                run(applicationArguments,params);
            }
            Scanner scan=new Scanner(System.in);
            while(true){
                System.out.print("输入指令-->:)");
                String com=scan.nextLine();
                if(StringUtil.isNull(com)){
                    continue;
                }
                if("exit".endsWith(com)){
                    break;
                }
                //args=com.split(" ");
                args=StringUtil.split(com);
                Map<String,String> params=params(args);
                run(applicationArguments,params);
            }
        } catch (SysException e) {
            e.printStackTrace();
        }
    }

    public abstract void run(ApplicationArguments applicationArguments,Map<String,String> params);

    public Map<String,String> params(String[] args) throws SysException {
        Map<String,String> map=new HashMap<>();
        int length=args.length;
        if((length%2)==0){
            for(int i=0;i<length/2;i++){
                String key=args[i];
                String value=args[i+1];
                map.put(key,value);
            }
        }else{
            throw new SysException("参数错误");
        }
        return map;
    }
}
