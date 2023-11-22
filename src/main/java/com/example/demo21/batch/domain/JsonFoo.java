package com.example.demo21.batch.domain;

public class JsonFoo {
    String string;

    public JsonFoo(Foo foo) {
        this.string = foo.toString();
    }

    @Override
    public String toString() {
        return "JsonFoo{" + string + '}';
    }
}
