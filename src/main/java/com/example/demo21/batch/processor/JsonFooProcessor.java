package com.example.demo21.batch.processor;

import com.example.demo21.batch.domain.Foo;
import com.example.demo21.batch.domain.JsonFoo;
import org.springframework.batch.item.ItemProcessor;

public class JsonFooProcessor implements ItemProcessor<Foo, JsonFoo> {
    @Override
    public JsonFoo process(Foo item) throws Exception {
        return new JsonFoo(item);
    }
}
