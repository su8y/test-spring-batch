package com.example.demo21;

import org.springframework.batch.item.file.transform.LineAggregator;

public class TradeLineAggregator<T> implements LineAggregator<T> {

    @Override
    public String aggregate(T item) {
        return item.toString();
    }
}
