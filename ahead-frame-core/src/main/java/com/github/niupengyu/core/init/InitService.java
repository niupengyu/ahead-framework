package com.github.niupengyu.core.init;

import org.springframework.boot.ApplicationArguments;

public abstract interface InitService {

    public void run(ApplicationArguments applicationArguments);

}
