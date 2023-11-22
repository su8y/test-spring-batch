package com.example.demo21.batch;

import com.example.demo21.TradeLineAggregator;
import com.example.demo21.domain.Trade;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.validation.BindException;

import java.io.File;
import java.io.IOException;

@Slf4j
public class BatchTest {
    @Test
    void FieldSet() {
        String[] tokens = new String[]{"foo", "1", "true"};
        FieldSet fs = new DefaultFieldSet(tokens);
        String name = fs.readString(0);
        int value = fs.readInt(1);
        boolean booleanValue = fs.readBoolean(2);
        log.info("name ={},value={}, boolean={}", name, value, booleanValue);
    }

    class TradeFieldSetMapper implements FieldSetMapper<Trade> {

        @Override
        public Trade mapFieldSet(FieldSet fieldSet) throws BindException {
            Trade trade = new Trade();
            trade.setId(fieldSet.readString("id"));
            trade.setCountry1(fieldSet.readString("country1"));
            trade.setCountry2(fieldSet.readString("country2"));
            trade.setYear(fieldSet.readInt("year"));
            Double export;
            try {
                export = fieldSet.readDouble("export");
            } catch (NumberFormatException e) {
                export = 0.0D;
            }
            trade.setExport(export);
            return trade;
        }
    }

    @Test
    void flatFile() throws Exception {
        FlatFileItemReader<Trade> itemReader = new FlatFileItemReader<>();

        Resource resource = new ClassPathResource("trade.csv");
//        FileSystemResource fileSystemResource = new FileSystemResource("file:\\\\wsl$\\Ubuntu\\home\\su8y\\workspaces\\demo21\\src\\main\\resources\\trade.csv");
        itemReader.setResource(resource);

        DefaultLineMapper<Trade> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer fixedLengthTokenizer = new DelimitedLineTokenizer();
        fixedLengthTokenizer.setNames(new String[]{"id", "country1", "country2", "year", "export"});
        lineMapper.setLineTokenizer(fixedLengthTokenizer);
        lineMapper.setFieldSetMapper(new TradeFieldSetMapper());

        itemReader.setLineMapper(lineMapper);
        ExecutionContext executionContext = new ExecutionContext();
        itemReader.open(executionContext);
        log.warn("{}", executionContext.get("count"));

        Trade read = itemReader.read();
        log.info("{}", read);
        log.warn("{}", executionContext.get("count"));
    }

    @Test
    void SimpleFileWriter() throws Exception {
        WritableResource writableResource =  new FileSystemResource("output.csv");
        log.info("{}",writableResource.getURI());
        if(!writableResource.exists()){
            File file = new File(writableResource.getURI());
            log.info("만들다");
            writableResource.createRelative("./");
            file.createNewFile();
        }
        FlatFileItemWriter<Trade> itemWriter = new FlatFileItemWriterBuilder<Trade>()
                .name("itemWriter")
                .resource(writableResource)
                .lineAggregator(new TradeLineAggregator())
                .build();

        Trade trade = new Trade();
        trade.setCountry1("퉁퉁이");
        Chunk<Trade> trades = new Chunk<>();
        trades.add(trade);

//        itemWriter.doWrite(trades);
        itemWriter.write(trades);
        System.out.println("HELLO");

    }
}
