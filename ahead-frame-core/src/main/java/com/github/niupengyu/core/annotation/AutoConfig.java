package com.github.niupengyu.core.annotation;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(AutoConfigCondition.class)
public @interface AutoConfig {
    String name();
    boolean def() default false;
}

