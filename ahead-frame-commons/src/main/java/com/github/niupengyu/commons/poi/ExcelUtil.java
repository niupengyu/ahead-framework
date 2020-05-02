package com.github.niupengyu.commons.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtil {

    private void addCell(Workbook wb, HSSFRow row, int i, String s) {
        HSSFCell cell = row.createCell(i);
        HSSFRichTextString text = new HSSFRichTextString(s);
        cell.setCellValue(text);
        CellStyle cellStyle =cellStyle(wb);
        cell.setCellStyle(cellStyle);
    }

    private CellStyle cellStyle(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    private void addCell(Workbook wb,HSSFRow row,int i, String s,HorizontalAlignment h,VerticalAlignment v) {
        HSSFCell cell = row.createCell(i);
        HSSFRichTextString text = new HSSFRichTextString(s);
        cell.setCellValue(text);
        CellStyle cellStyle =cellStyle(wb,h,v);
        cell.setCellStyle(cellStyle);
    }

    private CellStyle cellStyle(Workbook wb,HorizontalAlignment h,VerticalAlignment v) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(h);
        cellStyle.setVerticalAlignment(v);
        return cellStyle;
    }

    public void merge(Sheet sheet,int firstRow, int lastRow, int firstCol, int lastCol){
        CellRangeAddress region0 = new CellRangeAddress(firstRow,lastRow,firstCol,lastCol);
        sheet.addMergedRegion(region0);
    }

}
