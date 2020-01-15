package com.example.demo.processor;

import com.example.demo.domain.ArquivoEntrada;
import org.springframework.batch.item.ItemProcessor;

public class ArquivoProcessor implements ItemProcessor<ArquivoEntrada, ArquivoEntrada> {

    @Override
    public ArquivoEntrada process(ArquivoEntrada arquivoEntrada) throws Exception {
        return arquivoEntrada;
    }
}
