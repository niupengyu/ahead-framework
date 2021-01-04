package com.github.niupengyu.core.message;

public interface DataRunner<T> {

    void execute(T messageBean);

}
