package com.example.demo.processor;

import com.example.demo.domain.ArquivoEntrada;
import org.springframework.batch.item.ItemProcessor;

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

        double d = (double)arquivoEntrada.getNumero();
        double resultado = d/17.0;
        String resto = String.valueOf(resultado);
        String s1 = resto.split("\\.")[1];
        double s2 = Double.valueOf(s1);
        long retorno = (long) s2;
        DecimalFormat df = new DecimalFormat("#000000");
        long result = Long.valueOf(df.format(retorno));
        novoArquivo.setResto17(result);

        return novoArquivo;
    }
}
