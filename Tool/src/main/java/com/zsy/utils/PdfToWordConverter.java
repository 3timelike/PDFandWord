package com.zsy.utils;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.*;
import org.apache.poi.xwpf.usermodel.*;
import java.io.*;

public class PdfToWordConverter {
    public  PdfToWordConverter(){}

    public static void convert(String pdfPath, String docxPath) throws Exception {
        // 加载PDF文档（PDFBox 3.x方式）
        File file = new File(new File(docxPath),"test.doc");
        if(!file.exists()){
            boolean flag =  file.createNewFile();
            if(!flag){
                throw new Exception("file create error");
            }
        }
        PDDocument pdfDoc = Loader.loadPDF(new File(pdfPath)); // 使用Loader类

        // 创建Word文档
        XWPFDocument wordDoc = new XWPFDocument();
        XWPFParagraph para = wordDoc.createParagraph();
        XWPFRun run = para.createRun();

        // 提取PDF文本
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(pdfDoc);

        // 写入Word（保留换行符）
        String[] lines = text.split("\\r?\\n");
        for (String line : lines) {
            run.setText(line);
            run.addBreak(); // 换行
        }
        for(String line : lines) {
            System.out.println(line);
        }
        // 保存并关闭
        wordDoc.write(new FileOutputStream(docxPath + "//test.doc"));
        wordDoc.close();
        pdfDoc.close();
    }
}