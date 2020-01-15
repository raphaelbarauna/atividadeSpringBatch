package com.example.demo.domain;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class ArquivoEntradaFieldSetMapper implements FieldSetMapper<ArquivoEntrada>{

    @Override
    public ArquivoEntrada mapFieldSet(FieldSet fieldSet) throws BindException {
        return new ArquivoEntrada(fieldSet.readLong("numero"));

    }

}
