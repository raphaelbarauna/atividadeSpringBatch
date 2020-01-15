package com.example.demo.configuration;

import com.example.demo.domain.ArquivoEntradaFieldSetMapper;
import com.example.demo.domain.ArquivoEntrada;
import com.example.demo.processor.ArquivoProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;

@Configuration
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    private Resource outputResource = new FileSystemResource("data/outputData.csv");

    @Bean
    public FlatFileItemReader<ArquivoEntrada> arquivoEntradaFlatFileItemReader(){
        FlatFileItemReader<ArquivoEntrada> reader = new FlatFileItemReader<>();
        //Pular a linha do cabeçalho
        reader.setLinesToSkip(1);
        reader.setResource(new ClassPathResource("data/input.csv"));
        reader.setStrict(true);
        DefaultLineMapper<ArquivoEntrada> arquivoEntradaDefaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("numero");

        arquivoEntradaDefaultLineMapper.setLineTokenizer(tokenizer);
        arquivoEntradaDefaultLineMapper.setFieldSetMapper(new ArquivoEntradaFieldSetMapper());
        arquivoEntradaDefaultLineMapper.afterPropertiesSet();

        reader.setLineMapper(arquivoEntradaDefaultLineMapper);

        return reader;
    }

    @Bean
    public ItemWriter<ArquivoEntrada> arquivoEntradaItemWriter(){
        //Create writer instance
        FlatFileItemWriter<ArquivoEntrada> writer = new FlatFileItemWriter<>();
        ArrayList<ArquivoEntrada> lista = new ArrayList<>();
        //Set output file location
        writer.setResource(outputResource);
        //All job repetitions should "append" to same output file
        writer.setAppendAllowed(true);
        //Name field values sequence based on object properties
        writer.setLineAggregator(new DelimitedLineAggregator<ArquivoEntrada>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<ArquivoEntrada>() {
                    {
                        setNames(new String[] { "Numero", "Par/Impar", "Multiplo17, Resto17" });
                    }
                });
            }
        });
        return writer;
    }

    @Bean
    public ArquivoProcessor processor(){
        return new ArquivoProcessor();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<ArquivoEntrada, ArquivoEntrada>chunk(10)
                .reader(arquivoEntradaFlatFileItemReader())
                .processor(processor())
                .writer(arquivoEntradaItemWriter())
                .build();
    }

    @Bean
    public Job readCSVFilesJob() {
        return jobBuilderFactory
                .get("readCSVFilesJob")
                .start(step1())
                .build();
    }



}
