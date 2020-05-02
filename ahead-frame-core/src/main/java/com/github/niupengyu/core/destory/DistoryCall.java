package com.github.niupengyu.core.destory;

import java.util.concurrent.Callable;

public class DistoryCall<Integer> implements Callable {

    private int m;

    public DistoryCall(int m){
        this.m=m;
    }

    @Override
    public Object call() throws Exception {

        return m;
    }
}
