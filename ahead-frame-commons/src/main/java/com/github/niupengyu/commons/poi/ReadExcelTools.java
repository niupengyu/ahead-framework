package com.github.niupengyu.commons.poi;

import com.github.niupengyu.core.util.IdGeneratorUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadExcelTools {
    /**
     * 默认读取的 sheet 位置 1 代表第一个sheet
     */
    private int sheet=1;
    /**
     * 默认从第几行开始读取 1 代表从第二行开始 0 是第一行
     */
    private int firstrow=1;
    /**
     * 读取到第几行截止 如果这个值和 firstrow 相同 那么读取 excel 全部数据
     */
    private int lastRow=1;

    /**
     * 读取那几列的数据 如果 只读取第一 、三、五列 那就传 new int[]{0,2,4};
     */
    private int[] cols=new int[]{};

    //private FileInputStream inputStream;
    /**
     * excel 文件名称
     */
    private String fileName;
    /**
     * 初始化 poi 对象
     */
    private Workbook workbook = null;

    private static final Log logger = LogFactory.getLog(ReadExcelTools.class);

    private final static String xls = "xls";

    private final static String xlsx = "xlsx";

    /**
     * 构造函数
     * @param inputStream 文件流
     * @param fileName  文件名称
     * @param sheet 读取的 sheet 位置
     * @param cols 读取的列
     */
    public ReadExcelTools(InputStream inputStream,String fileName,int sheet, int[] cols){
        this.cols=cols;
        this.sheet=sheet;
        //this.inputStream=inputStream;
        this.workbook=getWorkBook(inputStream,fileName);
    }

    /**
     *
     * @param inputStream 文件流
     * @param fileName  文件名称
     * @param sheet 读取的 sheet 位置
     * @param cols 读取的列
     * @param firstrow 从第几行开始读取
     */
    public ReadExcelTools(InputStream inputStream,String fileName,int sheet, int[] cols, int firstrow){
        init(inputStream,fileName,sheet,cols,firstrow,firstrow);
    }

    /**
     *
     * @param inputStream 文件流
     * @param fileName 文件名称
     * @param sheet 读取的 sheet 位置
     * @param cols 读取的列
     * @param firstrow 从第几行开始读取
     * @param lastRow 到第几行结束读取 （如果这个值和 firstrow 相同 那么读取 excel 全部数据）
     */
    private void init(InputStream inputStream, String fileName, int sheet, int[] cols, int firstrow, int lastRow) {
        this.cols=cols;
        this.firstrow=firstrow;
        this.lastRow=lastRow;
        this.sheet=sheet;
        //this.inputStream=inputStream;
        this.workbook=getWorkBook(inputStream,fileName);
        this.fileName=fileName;
    }

    /**
     *
     * @param inputStream 文件流
     * @param fileName 文件名称
     * @param sheet 读取的 sheet 位置
     * @param cols 读取的列
     * @param firstrow 从第几行开始读取
     * @param lastRow 到第几行结束读取 （如果这个值和 firstrow 相同 那么读取 excel 全部数据）
     */
    public ReadExcelTools(InputStream inputStream,String fileName,int sheet, int[] cols, int firstrow,int lastRow){
        /*this.cols=cols;
        this.firstrow=firstrow;
        this.lastRow=lastRow;
        this.sheet=sheet;
        //this.inputStream=inputStream;
        this.workbook=getWorkBook(inputStream,fileName);
        this.fileName=fileName;*/
        init(inputStream,fileName,sheet,cols,firstrow,lastRow);
    }
    /**
     *
     * @param inputStream 文件流
     * @param fileName 文件名称
     * @param sheet 读取的 sheet 位置
     * @param firstrow 从第几行开始读取
     * @param lastRow 到第几行结束读取 （如果这个值和 firstrow 相同 那么读取 excel 全部数据）
     */
    public ReadExcelTools(InputStream inputStream,String fileName,int sheet, int firstrow,int lastRow){
        /*this.firstrow=firstrow;
        this.lastRow=lastRow;
        this.sheet=sheet;
        //this.inputStream=inputStream;
        this.workbook=getWorkBook(inputStream,fileName);
        this.fileName=fileName;*/
        init(inputStream,fileName,sheet,new int[]{},firstrow,lastRow);
    }
    /**
     *
     * @param inputStream 文件流
     * @param fileName 文件名称
     * @param sheet 读取的 sheet 位置
     * @param firstrow 从第几行开始读取
     */
    public ReadExcelTools(InputStream inputStream,String fileName,int sheet, int firstrow){
        /*this.firstrow=firstrow;
        this.lastRow=firstrow;
        this.sheet=sheet;
        //this.inputStream=inputStream;
        this.workbook=getWorkBook(inputStream,fileName);
        this.fileName=fileName;*/
        init(inputStream,fileName,sheet,new int[]{},firstrow,firstrow);
    }
    /**
     *
     * @param inputStream 文件流
     * @param fileName 文件名称
     * @param firstrow 从第几行开始读取
     */
    public ReadExcelTools(InputStream inputStream,String fileName,int firstrow){
        /*this.firstrow=firstrow;
        this.lastRow=firstrow;
        //this.inputStream=inputStream;
        this.workbook=getWorkBook(inputStream,fileName);
        this.fileName=fileName;*/
        init(inputStream,fileName,1,new int[]{},firstrow,firstrow);
    }
    /**
     *
     * @param inputStream 文件流
     * @param fileName 文件名称
     * @param cols 读取的列
     * @param firstrow 从第几行开始读取
     * @param lastRow 到第几行结束读取 （如果这个值和 firstrow 相同 那么读取 excel 全部数据）
     */
    public ReadExcelTools(InputStream inputStream,String fileName,int[] cols,int firstrow,int lastRow){
        /*this.firstrow=firstrow;
        this.lastRow=lastRow;
        this.cols=cols;
        //this.inputStream=inputStream;
        this.workbook=getWorkBook(inputStream,fileName);
        this.fileName=fileName;*/
        init(inputStream,fileName,1,cols,firstrow,lastRow);
    }
    /**
     *
     * @param inputStream 文件流
     * @param fileName 文件名称
     */
    public ReadExcelTools(InputStream inputStream,String fileName){
        //this.inputStream=inputStream;
        /*this.workbook=getWorkBook(inputStream,fileName);
        this.fileName=fileName;*/
        init(inputStream,fileName,1,new int[0],1,1 );
    }




   /* public ReadExcelTools(int sheet, int[] cols){
        this.cols=cols;
        this.sheet=sheet;
    }

    public ReadExcelTools(int sheet, int[] cols, int firstrow){
        this.cols=cols;
        this.firstrow=firstrow;
        this.lastRow=firstrow;
        this.sheet=sheet;
    }

    public ReadExcelTools(int sheet, int[] cols, int firstrow,int lastRow){
        this.cols=cols;
        this.firstrow=firstrow;
        this.lastRow=lastRow;
        this.sheet=sheet;
    }

    public ReadExcelTools(int sheet, int firstrow,int lastRow){
        this.firstrow=firstrow;
        this.lastRow=lastRow;
        this.sheet=sheet;
    }

    public ReadExcelTools(int sheet, int firstrow){
        this.firstrow=firstrow;
        this.sheet=sheet;
    }

    public ReadExcelTools(int firstrow){
        this.firstrow=firstrow;
        this.lastRow=firstrow;
    }

    public ReadExcelTools(int[] cols,int firstrow,int lastRow){
        this.firstrow=firstrow;
        this.lastRow=lastRow;
        this.cols=cols;
    }

    public ReadExcelTools(){

    }*/

    /**
     * 读入excel文件，解析后返回 通过实现回调
     * @throws IOException
     */
    public void readExcel(ExcelReadCallBack readCallBack) throws IOException{
        //获得Workbook工作薄对象
        //Workbook workbook = getWorkBook(inputStream,fileName);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        readWorkBook(readCallBack);
    }

    public void readExcelSheet(ExcelReadCallBack readCallBack) throws IOException{
        //获得Workbook工作薄对象
        //Workbook workbook = getWorkBook(inputStream,fileName);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        if(workbook != null) {
            readWorkBookSheet(readCallBack, sheet);
        }
    }

    /**
     * 读入excel文件，解析后返回 通过实现回调
     * @param readCallBack 回调
     */
    private void readWorkBook(ExcelReadCallBack readCallBack) {
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < sheet;sheetNum++){
                readWorkBookSheet(readCallBack,sheetNum);
            }
        }
    }

    private void readWorkBookSheet(ExcelReadCallBack readCallBack, int sheetNum) {
        //获得当前sheet工作表
        Sheet sheet = workbook.getSheetAt(sheetNum);
        if(sheet == null){
            return;
        }
        //获得当前sheet的开始行
        int firstRowNum  = sheet.getFirstRowNum();
        //获得当前sheet的结束行
        int lastRowNum = (firstrow<lastRow)?lastRow:sheet.getLastRowNum();
        //循环除了第一行的所有行
        //List<String[]> list = new ArrayList<String[]>();
        for(int rowNum = firstRowNum+firstrow;rowNum <= lastRowNum;rowNum++){ //为了过滤到第一行因为我的第一行是数据库的列
            //获得当前行
            Row row = sheet.getRow(rowNum);
            if(row == null){
                continue;
            }
            //获得当前行的开始列
            int firstCellNum = row.getFirstCellNum();
            //获得当前行的列数
            int lastCellNum = row.getLastCellNum();//为空列获取
//                    int lastCellNum = row.getPhysicalNumberOfCells();//为空列不获取
//                    String[] cells = new String[row.getPhysicalNumberOfCells()];
            String[] cells =null;
            if(cols.length>0){
                cells = new String[cols.length];
                each(row,cells);
            }else{
                cells = new String[row.getLastCellNum()];
                each(firstCellNum,lastCellNum,row,cells);
            }
            //循环当前行
            //list.add(cells);
            readCallBack.read(fileName,sheetNum,rowNum,cells);
        }
    }

    /**
     * 指定读取某一行数据
     * @param sheetNum 读取sheet 位置
     * @param rowNum 读取哪一行 读取全部 传 new int[]{}
     * @return 返回读取数据数组
     */
    public String[] readWorkRow(int sheetNum,int rowNum) {
        String[] cells =null;
        if(workbook != null){
            //获得当前sheet工作表
            Sheet sheet = workbook.getSheetAt(sheetNum-1);
            //获得当前行
            Row row = sheet.getRow(rowNum);
            //获得当前行的开始列
            int firstCellNum = row.getFirstCellNum();
            //获得当前行的列数
            int lastCellNum = row.getLastCellNum();//为空列获取
//                    int lastCellNum = row.getPhysicalNumberOfCells();//为空列不获取
//                    String[] cells = new String[row.getPhysicalNumberOfCells()];
            if(cols.length>0){
                cells = new String[cols.length];
                each(row,cells);
            }else{
                cells = new String[row.getLastCellNum()];
                each(firstCellNum,lastCellNum,row,cells);
            }
            //循环当前行
            //list.add(cells);
            //readCallBack.read(fileName,sheetNum,cells);
        }
        return cells;
    }


    /**
     * 读入excel文件，解析后返回
     * @param
     * @throws IOException
     */
    /*public void readExcel(File file,ExcelReadCallBack readCallBack) throws IOException{
        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        //Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        readWorkBook(file.getName(),readCallBack);

    }*/

    private void each(int firstCellNum,int lastCellNum,Row row,String[] cells) {
        for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
            Cell cell = row.getCell(cellNum);
            cells[cellNum] = getCellValue(cell);
        }
    }

    private void each(Row row,String[] cells) {
        for(int i = 0; i < cols.length;i++){
            Cell cell = row.getCell(cols[i]);
            cells[i] = getCellValue(cell);
        }
    }

    private void checkFile(File file) throws IOException{
        //判断文件是否存在
        if(null == file){
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        //String fileName = file.getName();
        //判断文件是否是excel文件
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            throw new IOException(fileName + "不是excel文件");
        }
    }
    /*private Workbook getWorkBook(File file) {
        //获得文件名
        String fileName = file.getName();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = new FileInputStream(file);
            return getWorkBook(is,fileName);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return workbook;
    }*/

    private Workbook getWorkBook(InputStream is,String fileName) {
        //获得文件名
        //创建Workbook工作薄对象，表示整个excel

        try {
            //获取excel文件的io流
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith(xls)){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith(xlsx)){
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return workbook;
    }

    public String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA: //公式
//                cellValue = String.valueOf(cell.getCellFormula());
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK: //空值
                cellValue = "";
                break;
            case ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    public static List<String[]> readExcel(String path) throws Exception {
        File file=new File(path);
        InputStream inputStream=new FileInputStream(file);
        List<String[]> list=new ArrayList<>();
        ReadExcelTools readExcelTools=new ReadExcelTools(inputStream,file.getName(),4,new int[]{0,1,2},2);
        readExcelTools.readExcelSheet(  new ExcelReadCallBack() {
            @Override
            public void read(String s, int i, int i1, String[] strings) {
                //params.add(new Object[]{IdGeneratorUtil.uuid32(),strings[0],strings[1],strings[2]});
                try {
                    list.add(strings);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return list;
    }
}
