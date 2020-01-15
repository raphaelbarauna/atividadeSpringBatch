package com.example.demo.model;

import com.example.demo.model.enums.ParOuImparEnum;

public class ArquivoEntrada {

        private int numero;

    public ArquivoEntrada(int numero) {
        super();
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }



    @Override
    public String toString() {
        return "ArquivoEntrada{" +
                "numero=" + numero +
                '}';
    }
}
