package com.example.demo;

import com.example.demo.configuration.BatchConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
		//BatchConfiguration batchConfiguration = new BatchConfiguration();
		//batchConfiguration.readCSVFilesJob();
		double numero = 5266;
		double multiplo = 17;

		double resultado = numero/multiplo;
		System.out.println(resultado);
		String resto = String.valueOf(resultado);

		String s1 = resto.split("\\.")[1];
		System.out.println(s1);
		double s2 = Double.valueOf(s1);
		double resto17 = s2%17;
		int v = (int) Math.rint(resto17);

		double teste= this.arredondar(s2, 6);
		System.out.println("Resultado: " + resultado + " Resto : "+ teste);
	}

	double arredondar(double valor, int casas) {
		double verif,arredondado = valor;
		int multp=1;
		for (int i =0;i<casas;i++)
			multp*=10; arredondado *= multp;
			verif = arredondado;
			arredondado = Math.floor(arredondado);
			verif-=arredondado;
			verif=Math.floor(verif*10);
			if (verif > 4)
				arredondado++;
			arredondado /= multp;
			return arredondado;
	}



}
