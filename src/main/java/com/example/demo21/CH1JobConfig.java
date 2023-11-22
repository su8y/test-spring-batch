package com.example.demo21;

import com.example.demo21.batch.domain.Foo;
import com.example.demo21.batch.domain.JsonFoo;
import com.example.demo21.batch.processor.FooProcessor;
import com.example.demo21.batch.processor.JsonFooProcessor;
import com.example.demo21.domain.Trade;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
@EnableBatchProcessing
public class CH1JobConfig extends DefaultBatchConfiguration {
    @Bean
    public Job exampleJob(
            final JobRepository jobRepository,
            final Step step1
    ) {
        return new JobBuilder("exampleJob", jobRepository)
                .preventRestart()
                .start(step1)
//                .validator(parameters -> {
//                    throw new JobParametersInvalidException("no value : admin");
//                })
                .build();
    }

    @Bean
    public Step step1(
            final JobRepository jobRepository,
            final PlatformTransactionManager platformTransactionManager
    ) {

        CompositeItemProcessor<Trade, JsonFoo> compositeProcessor = new CompositeItemProcessor<>();
        List itemProcessor = new ArrayList<>();
        itemProcessor.add(new FooProcessor());
        itemProcessor.add(new JsonFooProcessor());
        compositeProcessor.setDelegates(itemProcessor);
        return new StepBuilder("step1", jobRepository)
                .<Trade, JsonFoo>chunk(2, platformTransactionManager)
                .reader(itemReader())
                .processor(compositeProcessor)
                .writer(itemWriter())
                .build();

    }

    @Bean
    public ItemReader<Trade> itemReader() {
        DefaultLineMapper<Trade> tradeDefaultLineMapper = new DefaultLineMapper<>();
        tradeDefaultLineMapper.setLineTokenizer(new DelimitedLineTokenizer());
        tradeDefaultLineMapper.setFieldSetMapper(new TradeFieldSet());

        return new FlatFileItemReaderBuilder<Trade>()
                .name("itemReader")
                .lineMapper(tradeDefaultLineMapper)
                .resource(new ClassPathResource("trade.csv"))
                .build();
    }

    @Bean
    public ItemWriter<? super Object> itemWriter() {
        WritableResource writableResource = new FileSystemResource("output.csv");



        return new FlatFileItemWriterBuilder<>()
                .name("itemWriter")
                .resource(writableResource)
                .lineAggregator(new TradeLineAggregator())
                .build();
    }


    @PostConstruct
    void init() {
        System.out.println("init Config");
    }


}

