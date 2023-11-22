package com.example.demo21;

import com.example.demo21.domain.Trade;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.transaction.PlatformTransactionManager;

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
        return new StepBuilder("step1", jobRepository)
                .chunk(2, platformTransactionManager)
                .reader(itemReader())
                .writer(itemWriter())
                .build();

    }

    @Bean
    public ItemReader<Trade> itemReader() {
        DefaultLineMapper<Trade> tradeDefaultLineMapper = new DefaultLineMapper<>();
        tradeDefaultLineMapper.setLineTokenizer(new DelimitedLineTokenizer());
        tradeDefaultLineMapper.setFieldSetMapper(new TradeFieldSet());

        return new FlatFileItemReaderBuilder<Trade>()
//                .fieldSetMapper(new TradeFieldSet())
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

