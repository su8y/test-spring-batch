package com.example.demo21.batch.processor;

import com.example.demo21.batch.domain.Foo;
import com.example.demo21.domain.Trade;
import org.springframework.batch.item.ItemProcessor;

public class FooProcessor implements ItemProcessor<Trade, Foo> {
    @Override
    public Foo process(Trade item) throws Exception {
        return new Foo(item);
    }
}
