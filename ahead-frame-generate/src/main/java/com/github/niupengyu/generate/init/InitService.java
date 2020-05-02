package com.github.niupengyu.generate.init;

import com.github.niupengyu.core.init.console.ConsoleInitService;
import com.github.niupengyu.generate.service.ProGenerate;
import com.github.niupengyu.generate.service.ScheduleGenerate;
import org.springframework.boot.ApplicationArguments;

public class InitService extends ConsoleInitService {
    @Override
    public void run(ApplicationArguments applicationArguments, String[] args) {
        try {
        String param=args[0];
            switch (param){
                case "project":
                    ProGenerate.exe();
                case "sch":
                    ScheduleGenerate.exe();
                case "ddl":

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
