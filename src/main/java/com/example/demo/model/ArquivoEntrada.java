package com.example.demo.model;

import com.example.demo.model.enums.ParOuImparEnum;

public class ArquivoEntrada {

        private Integer numero;
        private ParOuImparEnum parOuImpar;
        private Integer multiplo17;
        private Integer resto17;

    public ArquivoEntrada(Integer numero, ParOuImparEnum parOuImpar, Integer multiplo17, Integer resto17) {
        super();
        this.numero = numero;
        this.parOuImpar = parOuImpar;
        this.multiplo17 = multiplo17;
        this.resto17 = resto17;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public ParOuImparEnum getParOuImpar() {
        return parOuImpar;
    }

    public void setParOuImpar(ParOuImparEnum parOuImpar) {
        this.parOuImpar = parOuImpar;
    }

    public Integer getMultiplo17() {
        return multiplo17;
    }

    public void setMultiplo17(Integer multiplo17) {
        this.multiplo17 = multiplo17;
    }

    public Integer getResto17() {
        return resto17;
    }

    public void setResto17(Integer resto17) {
        this.resto17 = resto17;
    }
}
