package com.example.demo.configuration;

import com.example.demo.domain.ArquivoEntradaFieldSetMapper;
import com.example.demo.domain.ArquivoEntrada;
import com.example.demo.domain.MyFlatFileWriter;
import com.example.demo.processor.ArquivoProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.swing.tree.RowMapper;
import java.sql.ResultSet;
import java.util.ArrayList;

@Configuration
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    private Resource outputResource = new FileSystemResource("data/outputData.csv");
    private Resource inputResource = new FileSystemResource("data/input.csv");
    @Bean
    public FlatFileItemReader<ArquivoEntrada> arquivoEntradaFlatFileItemReader(){
        FlatFileItemReader<ArquivoEntrada> reader = new FlatFileItemReader<>();
        //Pular a linha do cabeçalho
        reader.setLinesToSkip(1);
        reader.setResource(inputResource);
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

    @Bean(name = "writerBean")
    public ItemWriter<ArquivoEntrada> arquivoEntradaItemWriter(){
        FlatFileItemWriter<ArquivoEntrada> writer = new FlatFileItemWriter<ArquivoEntrada>();
        writer.setResource(outputResource);

        DelimitedLineAggregator<ArquivoEntrada> lineAggregator = new DelimitedLineAggregator<ArquivoEntrada>();
        lineAggregator.setDelimiter(";");

        writer.setHeaderCallback(new MyFlatFileWriter());
        BeanWrapperFieldExtractor<ArquivoEntrada>  fieldExtractor = new BeanWrapperFieldExtractor<ArquivoEntrada>();
        fieldExtractor.setNames(new String[]{"numero","parOuImpar","multiplo17","resto17"});
        lineAggregator.setFieldExtractor(fieldExtractor);

        writer.setLineAggregator(lineAggregator);
        return writer;
    }

    @Bean
    public ArquivoProcessor processor(){
        //Classe que irá montar o Arquivo de saída
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
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    class ArquivoEntradaLineMapper  implements LineMapper<ArquivoEntrada> {

        @Override
        public ArquivoEntrada mapLine(String s, int rowNum) throws Exception {
            ArquivoEntrada arquivoEntrada = new ArquivoEntrada();
            return arquivoEntrada;
        }
    }
}
