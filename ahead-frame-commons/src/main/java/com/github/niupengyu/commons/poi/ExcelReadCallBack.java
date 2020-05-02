package com.github.niupengyu.commons.poi;

public interface ExcelReadCallBack {

    public void read(String fileName, int sheetNum, int rowNum,String[] list);

}
