package com.example.demo;

import com.example.demo.model.ArquivoEntrada;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.io.Writer;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Value("classpath:/input.csv")
    private UrlResource[] inputResources;

    private Resource outputResource = new FileSystemResource("resources/output.csv");


    @Bean
    public FlatFileItemWriter<ArquivoEntrada> writer()
    {
        //Create writer instance
        FlatFileItemWriter<ArquivoEntrada> writer = new FlatFileItemWriter<>();

        String exportFileHeader = "Número, Par/Impar, Multiplo17, Resto17";
        StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);

        writer.setHeaderCallback(headerWriter);
        //Set output file location
        writer.setResource(outputResource);

        //All job repetitions should "append" to same output file
        writer.setAppendAllowed(true);

        LineAggregator<ArquivoEntrada> lineAggregator = createStudentLineAggregator();
        //Name field values sequence based on object properties
        writer.setLineAggregator(lineAggregator);

        return writer;
    }

    private LineAggregator<ArquivoEntrada> createStudentLineAggregator() {
        DelimitedLineAggregator<ArquivoEntrada> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");

        FieldExtractor<ArquivoEntrada> fieldExtractor = createFileFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    private FieldExtractor<ArquivoEntrada> createFileFieldExtractor() {
        BeanWrapperFieldExtractor<ArquivoEntrada> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"numero", "parOuImpar", "multiplo17", "resto17"});
        return extractor;
    }

    @Bean
    public Job readCSVFilesJob() {
        return jobBuilderFactory
                .get("readCSVFilesJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<ArquivoEntrada, ArquivoEntrada>chunk(5)
                .reader(multiResourceItemReader())
                .writer(writer())
                .build();
    }


    @Bean
    public MultiResourceItemReader<ArquivoEntrada> multiResourceItemReader()
    {
        MultiResourceItemReader<ArquivoEntrada> resourceItemReader = new MultiResourceItemReader<ArquivoEntrada>();
        resourceItemReader.setResources(inputResources);
        resourceItemReader.setDelegate(reader());
        return resourceItemReader;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FlatFileItemReader<ArquivoEntrada> reader()
    {
        //Create reader instance
        FlatFileItemReader<ArquivoEntrada> reader = new FlatFileItemReader<ArquivoEntrada>();

        //Set number of lines to skips. Use it if file has header rows.
        reader.setLinesToSkip(1);

        //Configure how each line will be parsed and mapped to different values
        reader.setLineMapper(new DefaultLineMapper() {
            {
                //3 columns in each row
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[] { "Número" });
                    }
                });
                //Set values in Employee class
                setFieldSetMapper(new BeanWrapperFieldSetMapper<ArquivoEntrada>() {
                    {
                        setTargetType(ArquivoEntrada.class);
                    }
                });
            }
        });
        return reader;
    }

}
