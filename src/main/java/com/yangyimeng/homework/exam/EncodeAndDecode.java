package com.yangyimeng.homework.exam;


import sun.nio.cs.StreamDecoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class EncodeAndDecode {

    public static void main(String [] args) throws IOException{
        String file = "output.txt";
        String charset = "UTF-8";
        FileOutputStream outputStream = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, charset);
        try {
            writer.write("测试中文字符");
        } finally {
            writer.close();
        }
    }


}
