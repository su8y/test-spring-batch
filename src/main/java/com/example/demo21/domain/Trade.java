package com.example.demo21.domain;

import lombok.Data;

@Data
public class Trade {

    String id;
    String country1;
    String country2;
    int year;
    double export;

    @Override
    public String toString() {
        return "Trade{" +
                "id=" + id +
                ", country1='" + country1 + '\'' +
                ", country2='" + country2 + '\'' +
                ", year=" + year +
                ", export=" + export +
                '}';
    }
}
