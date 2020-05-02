package com.github.niupengyu.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ThreadLocalMapDateUtil {

  private ThreadLocalMapDateUtil() {
  }

  private static final String[] date_format = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss",
          "dd"};

  private static ThreadLocal<Map<String,DateFormat>> formatMap = new ThreadLocal<>();

  private static final Logger logger= LoggerFactory.getLogger(ThreadLocalMapDateUtil.class);

  public static Map<String,DateFormat> init(){
    logger.info("init ThreadLocalMapDateUtil.formatMap()");
    Map<String,DateFormat> map=new HashMap<>();
    for(int i=0;i<date_format.length;i++){
      String key=date_format[0];
      map.put(key,new SimpleDateFormat(key));
    }
   /* String key=date_format[0];
    map.put(key,new SimpleDateFormat(key));
    String key1=date_format[1];
    map.put(key1,new SimpleDateFormat(key1));*/
    formatMap.set(map);
    return formatMap.get();
  }

  public static DateFormat getDateFormat(String key) {

    Map<String,DateFormat> df = formatMap.get();
    if(df==null){
      df=init();
    }
    DateFormat sdf=null;
    key=StringUtil.isNull(key)?date_format[1]:key;
    if(df.containsKey(key)){
        sdf = df.get(key);
    }else{
      sdf=new SimpleDateFormat(key);
      df.put(key,sdf);
    }
    return sdf;
  }


  public static String getDateString(Date date, int hour, int min, int sec, String i) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, hour);
    cal.set(Calendar.MINUTE, min);
    cal.set(Calendar.SECOND, sec);
    return getDateFormat(i).format(cal.getTime());
  }



  public static String formatDate(Date date){
    if(date==null){
      return "";
    }
    return getDateFormat(date_format[0]).format(date);
  }

  public static String formatDate(Object obj){
    if(obj instanceof Date){
      return getDateFormat(date_format[0]).format(obj);
    }
    return "";
  }

  public static String formatDate(Object obj,String i){
    if(obj instanceof Date){
      return getDateFormat(i).format(obj);
    }
    return "";
  }

  public static String formatDateTime(Date date){
    return getDateFormat(date_format[1]).format(date);
  }

  public static String formatDateTime(Object obj){
    if(obj instanceof Date){
      return getDateFormat(date_format[1]).format(obj);
    }
    return "";
  }

  public static String formatDate(Date date, String i){
    return getDateFormat(i).format(date);
  }

  public static Date parse(String strDate, String i){
    try {
      return getDateFormat(i).parse(strDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }


  /**
   * 功能描述:获取前一天时间 此方法应当每天只执行一次
   */
  public static String getNextDay(int i, String j) throws ParseException {
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, i);
    date = calendar.getTime();
    return ThreadLocalMapDateUtil.formatDate(date, j);
  }


  public static void main(String[] args) {
    Thread t1=new Thread(new Runnable() {
      @Override
      public void run() {
        for(int i=0;i<100;i++){
          System.out.println(ThreadLocalMapDateUtil.formatDate(new Timestamp(System.currentTimeMillis()),
                  "yyyy-MM-dd HH:mm:ss"));
        }
      }
    });
    Thread t2=new Thread(new Runnable() {
      @Override
      public void run() {
        for(int i=0;i<100;i++){
          System.out.println(ThreadLocalMapDateUtil.formatDate(new Timestamp(System.currentTimeMillis()),
                  "yyyy-MM-dd HH:mm:ss"));
        }
      }
    });
    Thread t3=new Thread(new Runnable() {
      @Override
      public void run() {
        for(int i=0;i<100;i++){
          System.out.println(ThreadLocalMapDateUtil.formatDate(new Timestamp(System.currentTimeMillis()),
                  "yyyy-MM-dd HH:mm:ss"));
        }
      }
    });
    t1.start();
    t2.start();
    t3.start();
  }

}