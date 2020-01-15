package com.example.demo.model.enums;

public enum ParOuImparEnum {

        PAR( 1 ),
        IMPAR(2);

        private Integer cod;
        private String value;

    ParOuImparEnum(Integer cod) {
        this.cod = cod;
    }

    public int getCod() {
        return cod;
    }

    public static ParOuImparEnum fromValue(Integer value){

        if(value == null) {

            return null;
        }
        for(ParOuImparEnum cod : ParOuImparEnum.values())
            if (cod.equals(cod.getCod())) {
                return cod;
            }

        throw new IllegalArgumentException("Invalid ID");
    }


}
