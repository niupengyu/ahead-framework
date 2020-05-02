package com.github.niupengyu.core.init.console;

import com.github.niupengyu.core.init.InitService;
import com.github.niupengyu.core.util.StringUtil;
import org.springframework.boot.ApplicationArguments;

import java.util.Arrays;
import java.util.Scanner;

public abstract class ConsoleInitService implements InitService {

    public void run(ApplicationArguments applicationArguments){
        String[] args=applicationArguments.getSourceArgs();
        System.out.println(Arrays.toString(args));
        if(StringUtil.arrNotNull(args)){
            run(applicationArguments,args);
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
            args=StringUtil.split(com);
            run(applicationArguments,args);
        }
    }

    public abstract void run(ApplicationArguments applicationArguments,String[] args);

}
