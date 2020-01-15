package com.example.demo.processor;

import com.example.demo.domain.ArquivoEntrada;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ArquivoProcessor implements ItemProcessor<ArquivoEntrada, ArquivoEntrada> {

    @Override
    public ArquivoEntrada process(ArquivoEntrada arquivoEntrada) throws Exception {

        ArquivoEntrada novoArquivo = new ArquivoEntrada();

        novoArquivo.setNumero(arquivoEntrada.getNumero());

        if(arquivoEntrada.getNumero() % 2 == 0 ){
            novoArquivo.setParOuImpar("PAR");
        }else{
            novoArquivo.setParOuImpar("IMPAR");
        }
        novoArquivo.setMultiplo17(arquivoEntrada.getNumero()/17);

        long retorno = arquivoEntrada.getNumero()%17;

        novoArquivo.setResto17(retorno);

        return novoArquivo;
    }
}
