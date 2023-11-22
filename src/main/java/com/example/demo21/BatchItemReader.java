package com.example.demo21;

import org.springframework.batch.item.*;
import org.springframework.batch.item.database.JdbcCursorItemReader;

public class BatchItemReader implements ItemStreamReader<PushMember> {

    @Override
    public PushMember read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
