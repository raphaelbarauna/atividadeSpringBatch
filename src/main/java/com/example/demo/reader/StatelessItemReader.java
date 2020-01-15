package com.example.demo.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Iterator;

public class StatelessItemReader implements ItemReader<String> {

    private final Iterator<String> data;

    public StatelessItemReader(Iterator<String> data) {
        this.data = data;
    }

    @Override
    public String read() throws Exception {

        if(this.data.hasNext()){
            return this.data.next();
        }else{
            return null;
        }
    }
}
