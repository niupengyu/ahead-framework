package com.github.niupengyu.core.util;

import com.github.niupengyu.core.util.callback.ReadCallBack;
import com.github.niupengyu.core.util.callback.ReadCallBacks;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {

    private int size;

    private File file;

    private int total;

    private String charSet;

    public ReadFile(String path) {
        this.file = new File(path);
        this.charSet=Content.UTF8;
    }

    public ReadFile(String path, int size,int total) {
        this.size = size;
        this.file = new File(path);
        this.total = total;
        this.charSet=Content.UTF8;
    }

    public ReadFile(String path,String charSet) {
        this.file = new File(path);
        this.charSet=charSet;
    }

    public ReadFile(String path, int size,int total,String charSet) {
        this.size = size;
        this.file = new File(path);
        this.total = total;
        this.charSet=charSet;
    }



    public ReadFile(File file) {
        this.file = file;
        this.charSet=Content.UTF8;
    }

    public ReadFile(File file, int size,int total) {
        this.size = size;
        this.file = file;
        this.total = total;
        this.charSet=Content.UTF8;
    }

    public ReadFile(File file,String charSet) {
        this.file = file;
        this.charSet=charSet;
    }

    public ReadFile(File file, int size,int total,String charSet) {
        this.size = size;
        this.file = file;
        this.total = total;
        this.charSet=charSet;
    }



    public void read(ReadCallBack readCallBack) {
        try (
                FileInputStream fin=new FileInputStream(file);
                InputStreamReader inr=new InputStreamReader(fin,charSet);
                BufferedReader out=new BufferedReader(inr)
        ) {
            String str = null;
            while ((str = out.readLine()) != null) {
                readCallBack.call(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void read(ReadCallBack readCallBack,int max) {
        int count=0;
        try (
                FileInputStream fin=new FileInputStream(file);
                InputStreamReader inr=new InputStreamReader(fin,charSet);
                BufferedReader out=new BufferedReader(inr)
        ) {
            String str = null;
            while ((str = out.readLine()) != null) {
                readCallBack.call(str);
                if(count>max){
                    break;
                }else{
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String read(String path) {
        File file=new File(path);
        String str=null;
        try (
                FileInputStream fin=new FileInputStream(file);
                InputStreamReader inr=new InputStreamReader(fin,Content.UTF8);
                BufferedReader out=new BufferedReader(inr)
        ) {
            if ((str = out.readLine()) == null) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String read(File file) {
        String str=null;
        try (
                FileInputStream fin=new FileInputStream(file);
                InputStreamReader inr=new InputStreamReader(fin,Content.UTF8);
                BufferedReader out=new BufferedReader(inr)
        ) {
            if ((str = out.readLine()) == null) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String read(String path,String charSet) {
        File file=new File(path);
        String str=null;
        try (
                FileInputStream fin=new FileInputStream(file);
                InputStreamReader inr=new InputStreamReader(fin,charSet);
                BufferedReader out=new BufferedReader(inr)
        ) {
            if ((str = out.readLine()) == null) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String read(File file,String charSet) {
        String str=null;
        try (
                FileInputStream fin=new FileInputStream(file);
                InputStreamReader inr=new InputStreamReader(fin,charSet);
                BufferedReader out=new BufferedReader(inr)
        ) {
            if ((str = out.readLine()) == null) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public void reads(ReadCallBacks readCallBacks) {
        try (
                BufferedReader out = new BufferedReader(new FileReader(file));
        ) {
            String str = null;
            int i = 0;
//                String[] strs = new String[size];
            List<String> strs=new ArrayList<>();
            for (int index = 0; index < total; index++) {
                if ((str = out.readLine()) != null) {
                    if (i == size) {
                        readCallBacks.call(strs);
                        strs =new ArrayList<>();
                        i = 0;
                    }
                    strs.add(str);
                    i++;
                } else {
                    break;
                }
            }
            if (strs.size() > 0) {
                readCallBacks.call(strs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int total=10;
        int thread=4;
        String path="D:\\data\\sd_out_1w.csv";
        int size=PageUtil.maxPage(thread,total);
        ReadFile readFile=new ReadFile(path,size,total);
        long start=System.currentTimeMillis();
        readFile.reads(new ReadCallBacks() {
            @Override
            public void call(List<String> strs) {
                System.out.println(strs);
                System.out.println(strs.size());
            }
        });
    }

}
