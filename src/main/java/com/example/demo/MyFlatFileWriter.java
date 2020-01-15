package com.example.demo;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

public class MyFlatFileWriter implements FlatFileHeaderCallback {

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write("Número , Par/Impar , Multiplo17 , Resto17");
    }
}
