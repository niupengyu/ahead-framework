package com.github.niupengyu.core.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ScheduleService {
    String value();
    boolean registry() default true;
}
