package com.github.niupengyu.jdbc.util;

import com.github.niupengyu.jdbc.dao.JdbcDao;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Value {

    private String value;

    public Value(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int valueInt() {
        return Integer.parseInt(value);
    }

    public float valueFloat() {
        return Float.parseFloat(value);
    }

    public double valueDouble() {
        return Double.valueOf(value);
    }

    public String valueString() {
        return value;
    }

    public Object valueObject() {
        return value;
    }

    public long valueLong() {
        return Long.valueOf(value);
    }

    public Object valueTimestamp() {
        if("now".equals(value)){
            return new Timestamp(System.currentTimeMillis());
        }
        if("0".equals(value)){
            return new Timestamp(0);
        }
        return Timestamp.valueOf(value);
    }

    public boolean valueBoolean() {
        return Boolean.valueOf(value);
    }

    public Date valueDate() {
        if("now".equals(value)){
            return new Date(System.currentTimeMillis());
        }
        if("0".equals(value)){
            return new Date(0);
        }
        return Date.valueOf(value);
    }

    public Time valueTime() {
        if("now".equals(value)){
            return new Time(System.currentTimeMillis());
        }
        if("0".equals(value)){
            return new Time(0);
        }
        return Time.valueOf(value);
    }

    public void setValue(String value) {
        this.value = value;
    }


}