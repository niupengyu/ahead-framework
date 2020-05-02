package com.github.niupengyu.core.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class WriteFile {
    private BufferedWriter writer;

    private FileOutputStream fos;

    private OutputStreamWriter osw;

    public WriteFile(String fileName) throws IOException {
        fos = new FileOutputStream(fileName);
        osw = new OutputStreamWriter(fos, Content.UTF8);
        writer=new BufferedWriter(osw);
    }

    public WriteFile(String fileName,String charsetName) throws IOException {
        fos = new FileOutputStream(fileName);
        osw = new OutputStreamWriter(fos, charsetName);
        writer=new BufferedWriter(osw);
    }

    public void append(String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendLine(String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(WriteFile writeFile){
        if(writeFile!=null){
            writeFile.close();
        }
    }

    public void close() {
        try {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer = null;
            osw=null;
            fos=null;
        }

    }

    public static void main(String[] args) throws IOException {
        WriteFile writeFile=new WriteFile("D:\\data\\testwriter.txt");
        writeFile.appendLine("11111");

        writeFile.appendLine("22222");
        writeFile.close();

        /*bufferedWriter.append("11111");
        bufferedWriter.newLine();
        bufferedWriter.append("22222");
        bufferedWriter.flush();
        bufferedWriter.close();*/
    }

    public void newLine() throws IOException {
        writer.newLine();
    }
}
