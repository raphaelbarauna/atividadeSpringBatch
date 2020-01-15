package com.example.demo.domain;

import com.example.demo.model.enums.ParOuImparEnum;

import java.text.DecimalFormat;

public class ArquivoEntrada {

        private long numero;
        private String parOuImpar;
        private long multiplo17;
        private long resto17;

    public ArquivoEntrada() {
    }

    public ArquivoEntrada(long numero) {
        this.numero = numero;
        this.parOuImpar = parOuImpar;
        this.multiplo17 = multiplo17;
        this.resto17 = resto17;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public String getParOuImpar() {
        return parOuImpar;
    }

    public void setParOuImpar(String parOuImpar) {
        this.parOuImpar = parOuImpar;
    }

    public long getMultiplo17() {
        return multiplo17;
    }

    public void setMultiplo17(long multiplo17) {
        this.multiplo17 = multiplo17;
    }

    public long getResto17() {

        return resto17;
    }

    public void setResto17(long resto17) {
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
