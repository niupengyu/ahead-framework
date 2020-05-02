package com.github.niupengyu.commons.poi;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class WordUtil {

    private static final Logger logger= LoggerFactory.getLogger(WordUtil.class);


    public static XWPFTableRow tableRow(XWPFTable table, int i) {
        XWPFTableRow row=table.getRow(i);
        //row.setHeight(800);
        return row;
    }
    public static XWPFTableRow tableRow(XWPFTable table, int i, int height) {
        XWPFTableRow row=table.getRow(i);
        row.setHeight(height);
        return row;
    }

    //垂直合并
    public static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if ( rowIndex == fromRow ) {
                //使用RESTART合并值设置第一个合并单元格
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                //连接（合并）第一个单元格的单元格设置为CONTINUE
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    //水平合并
    public static void mergeCellsHorizo​​ntal(XWPFTable table, int  row, int  fromCell, int  toCell){

        for(int  cellIndex = fromCell; cellIndex <= toCell; cellIndex ++){
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if (cellIndex == fromCell){
                //使用RESTART合并值设置第一个合并单元格
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            }  else  {
                //连接（合并）第一个单元格的单元格设置为CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    //水平合并
    public static void mergeCellsHorizo​​ntal(XWPFTable table, XWPFTableRow row, int  fromCell, int  toCell){

        for(int  cellIndex = fromCell; cellIndex <= toCell; cellIndex ++){
            XWPFTableCell cell = row.getCell(cellIndex);
            if (cellIndex == fromCell){
                //使用RESTART合并值设置第一个合并单元格
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            }  else  {
                //连接（合并）第一个单元格的单元格设置为CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    public static void setTableWidth(XWPFTable table, int width){
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth =tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        CTJc cTJc=tblPr.addNewJc();
        cTJc.setVal(STJc.Enum.forString("center"));
        //tblWidth.setW(BigInteger.valueOf(width));
        tblWidth.setType(STTblWidth.DXA);
    }

    public static void setText(XWPFTableRow row0, int i, String text, int width, boolean bolb) {
        XWPFTableCell cell=row0.getCell(i);
        WordUtil.setCellText(cell,text,width,bolb);
    }


    public static void setText(XWPFTableRow row0, int i, String text, boolean bolb) {
        XWPFTableCell cell=row0.getCell(i);
        WordUtil.setCellText(cell,text,2000,bolb);
    }

    public static void setText(XWPFTableRow row0, int i, String text, int width) {
        XWPFTableCell cell=row0.getCell(i);
        WordUtil.setCellText(cell,text,width,false);
    }

    public static void setText(XWPFTableRow row0, int i, String text) {
        XWPFTableCell cell=row0.getCell(i);
        WordUtil.setCellText(cell,text,2000,false);
    }

    public static void setTextBolb(XWPFTableRow row0, int i, String text, int width) {
        XWPFTableCell cell=row0.getCell(i);
        WordUtil.setCellText(cell,text,width,true);
    }

    public static void setTextBolb(XWPFTableRow row0, int i, String text) {
        XWPFTableCell cell=row0.getCell(i);

        WordUtil.setCellText(cell,text,2000,true);
    }

    public static void cellUtil(XWPFTable table, Integer rowId, Integer cellId,
                                Integer rowHight , String text , String bgcolor , Integer cellHight , boolean bolb) {
        XWPFTableRow row = table.getRow(rowId);
        row.setHeight(rowHight);
        setCellText(row.getCell(cellId), text, bgcolor, cellHight,bolb);
    }


    public static void setCellText(XWPFTableCell cell, String text, String bgcolor, int width, boolean bolb) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        cell.setColor(bgcolor);
        CTTcPr ctPr = cttc.addNewTcPr();
        CTShd ctshd = ctPr.addNewShd();
        ctshd.setFill(bgcolor);
        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        XWPFParagraph pIO =cell.getParagraphs().get(0);
        XWPFRun rIO = pIO.createRun();
        rIO.setFontFamily("宋体");
        rIO.setFontSize(10);
        rIO.setBold(bolb);
        rIO.setText(text);
    }

    public static void setCellText(XWPFTableCell cell, String text, int width, boolean bolb) {
        setCellText(cell,text,width,bolb,16);
    }

    public static void setCellText(XWPFTableCell cell, String text, int width, boolean bolb,
                                   int fontSize) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        if(width>0){
            cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        }
        CTTcPr ctPr = cttc.addNewTcPr();
        CTShd ctshd = ctPr.addNewShd();
        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        XWPFParagraph pIO =cell.getParagraphs().get(0);
        XWPFRun rIO = pIO.createRun();
        rIO.setFontFamily("宋体");
        rIO.setFontSize(fontSize);
        rIO.setBold(bolb);
        rIO.setText(text);
    }

    public static void addCellText(XWPFTableCell cell, String text, String font,
                                   int fontSize, boolean bolb) {
        XWPFParagraph pIO =cell.getParagraphs().get(0);
        XWPFRun rIO = pIO.createRun();
        rIO.setFontFamily(font);
        rIO.setFontSize(fontSize);
        rIO.setBold(bolb);
        rIO.setText(text);
    }

    public static void addHtCellText(XWPFTableCell cell, String text, String font,
                                     int fontSize, boolean bolb) {
        XWPFParagraph pIO =cell.getParagraphs().get(0);

        //XWPFRun rIO = pIO.createRun();

        XWPFRun rIO = pIO.createRun();
        CTFonts fonts=rIO.getCTR().addNewRPr().addNewRFonts();
        fonts.setEastAsia("黑体");
        //rIO.setFontFamily(font);
        rIO.setFontSize(fontSize);
        rIO.setBold(bolb);
        rIO.setText(text);
    }

    public static void setCellAlign(XWPFTableCell cell, int width,
                                    STVerticalJc.Enum valign,
                                    STJc.Enum align) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        if(width>0){
            cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        }
        CTTcPr ctPr = cttc.addNewTcPr();
        CTShd ctshd = ctPr.addNewShd();
        ctPr.addNewVAlign().setVal(valign);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(align);
    }

    public static void setCellAlign(XWPFTableCell cell, int width) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        if(width>0){
            cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        }
        CTTcPr ctPr = cttc.addNewTcPr();
        CTShd ctshd = ctPr.addNewShd();
        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
    }


    public static void setCellText(XWPFTableCell cell, String text, int width, boolean bolb,
                                   int fontSize, STJc.Enum val) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        if(width>0){
            cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        }
        CTTcPr ctPr = cttc.addNewTcPr();
        CTShd ctshd = ctPr.addNewShd();
        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);

        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(val);
        XWPFParagraph pIO =cell.getParagraphs().get(0);
        XWPFRun rIO = pIO.createRun();
        rIO.setFontFamily("宋体");
        rIO.setFontSize(fontSize);
        rIO.setBold(bolb);
        rIO.setText(text);
    }


    public static void setCellText(XWPFTableCell cell, String text, boolean bolb,
                                   int fontSize) {
        XWPFParagraph pIO =cell.getParagraphs().get(0);
        XWPFRun rIO = pIO.createRun();
        rIO.setFontFamily("宋体");
        rIO.setFontSize(fontSize);
        rIO.setBold(bolb);
        rIO.setText(text);
    }

    public static void addCellBreak(XWPFTableCell cell) {
        XWPFParagraph pIO =cell.getParagraphs().get(0);
        pIO.createRun().addBreak();
    }

    public static XWPFTableRow createRow(XWPFTable table) {
        XWPFTableRow row=table.createRow();
        row.setHeight(500);
        return row;
    }

    public static XWPFTableRow createRow(XWPFTable table, int height) {
        XWPFTableRow row=table.createRow();
        row.setHeight(height);
        return row;
    }

    public static XWPFParagraph createPracraph(XWPFDocument document,
                                               String text, String font, int fontSize, ParagraphAlignment paragraphAlignment){
        XWPFParagraph p = document.createParagraph();// 新建一个段落
        p.setAlignment(paragraphAlignment);// 设置段落的对齐方式
        XWPFRun r = p.createRun();//创建段落文本
        r.setText(text); //内容标题
        //r.setBold(true);//设置为粗体
        r.setFontSize(fontSize);
        CTRPr rpr = r.getCTR().isSetRPr() ? r.getCTR().getRPr() : r.getCTR().addNewRPr();
        CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii(font);
        fonts.setEastAsia(font);
        fonts.setHAnsi(font);
        return p;
    }

    public static void main(String[] args) {


    }
}
