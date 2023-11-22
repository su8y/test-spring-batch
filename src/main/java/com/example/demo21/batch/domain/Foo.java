package com.example.demo21.batch.domain;

import com.example.demo21.domain.Trade;

public class Foo {
    String foo;

    public Foo(Trade trade) {
        String id = trade.getId();
        String country1 = trade.getCountry1();
        String country2 = trade.getCountry2();
        int year = trade.getYear();
        double export = trade.getExport();

        this.foo = String.format("%s,%s,%s,%d,%f", id, country1, country2, year, export);
    }

    @Override
    public String toString() {
        return "Foo:" + foo;
    }
}
