package com.example.demo.domain;

import com.example.demo.model.enums.ParOuImparEnum;

public class ArquivoEntrada {

        private final long numero;
        private String parOuImpar;
        private long multiplo17;
        private long resto17;

    public ArquivoEntrada(long numero, String parOuImpar, long multiplo17, long resto17) {
        this.numero = numero;
        this.parOuImpar = parOuImpar;
        this.multiplo17 = multiplo17;
        this.resto17 = resto17;
    }

    @Override
    public String toString() {
        return "ArquivoEntrada{" +
                "numero=" + numero +
                ", parOuImpar='" + parOuImpar + '\'' +
                ", multiplo17=" + multiplo17 +
                ", resto17=" + resto17 +
                '}';
    }
}
