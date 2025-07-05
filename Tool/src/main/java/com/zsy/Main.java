package com.zsy;


import com.zsy.utils.WordToPdfConverter;


public class Main {



    public static void main(String[] args) throws Exception {
//        PdfToWordConverter.convert("C:\\Users\\24244\\Desktop\\a.pdf",
//                "C:\\Users\\24244\\Desktop");
        WordToPdfConverter.convert("C:\\Users\\24244\\Desktop\\test.doc",
                "C:\\Users\\24244\\Desktop\\test2.pdf");
    }
}