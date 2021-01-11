package com.github.niupengyu.core.util;

public class PageUtil {
    //开始位置
    private int start;
    //每页数据量
    private int size;
    //最大页数
    private int maxPage;
    //数据总数
    private int total;

    private int page;

    public PageUtil(int page, int size, int total){
        this.page=page;
        this.size=size;
        this.total=total;
        maxPage = total % size == 0 ? total / size : total
                / size + 1;
        start = (page - 1) * size;
    }

    public PageUtil(int page, int size){
        this.page=page;
        this.size=size;
        maxPage = total % size == 0 ? total / size : total
                / size + 1;
        start = (page - 1) * size;
    }

    public int getStart() {
        return start;
    }

    public int getSize() {
        return size;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public static int maxPage(int size, int total){
        return total % size == 0 ? total / size : total / size + 1;
    }

    public static long maxPage(int size, long total){
        return  (total % size == 0 ? total / size : total / size + 1);
    }

    public static int start(int page,int size){
        return (page - 1) * size;
    }

    public static long start(int page,long size){
        return (page - 1) * size;
    }

    public static int oracleStart(int page,int size){
        return (page-1)*size;
    }

    public static int oracleEnd(int page,int size){
        return page*size;
    }

    public static long oracleStart(int page,long size){
        return (page-1)*size;
    }

    public static long oracleEnd(int page,long size){
        return page*size;
    }



    public static void main(String[] args) {
        PageUtil pageUtil=new PageUtil(1,20,100);
        pageUtil.getStart();
        pageUtil.getMaxPage();
    }
}
