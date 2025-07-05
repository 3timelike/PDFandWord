package com.zsy.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class WordToPdfConverter {
    public static void convert(String docxPath, String pdfPath) throws Exception {
        // 1. 加载Word文档
        XWPFDocument wordDoc = new XWPFDocument(new FileInputStream(docxPath));

        // 2. 创建PDF文档
        try (PDDocument pdfDoc = new PDDocument()) {
            PDPage page = new PDPage();
            pdfDoc.addPage(page);

            // 3. 加载字体
            InputStream fontStream = WordToPdfConverter.class
                    .getResourceAsStream("/fonts/NotoSansCJKsc-VF.ttf");
            PDFont font = PDType0Font.load(pdfDoc, fontStream);

            // 4. 初始化参数
            float marginX = 50;
            float startY = 700;
            float lineHeight = 20;

            // 5. 手动管理ContentStream
            PDPageContentStream contentStream = null;
            try {
                contentStream = new PDPageContentStream(pdfDoc, page);
                contentStream.setFont(font, 12);

                for (XWPFParagraph para : wordDoc.getParagraphs()) {
                    String text = para.getText();
                    if (text != null && !text.trim().isEmpty()) {
                        String[] lines = text.split("\\r?\\n");
                        for (String line : lines) {
                            if (!line.trim().isEmpty()) {
                                // 检查是否需要换页
                                if (startY < 50) {
                                    contentStream.close();
                                    page = new PDPage();
                                    pdfDoc.addPage(page);
                                    contentStream = new PDPageContentStream(pdfDoc, page);
                                    contentStream.setFont(font, 12);
                                    startY = 700;
                                }

                                // 写入文本
                                contentStream.beginText();
                                contentStream.newLineAtOffset(marginX, startY);
                                contentStream.showText(line);
                                contentStream.endText();
                                startY -= lineHeight;
                            }
                        }
                    }
                }
            } finally {
                if (contentStream != null) {
                    contentStream.close();
                }
            }

            // 6. 保存PDF
            pdfDoc.save(new File(pdfPath));
        }
    }
}
