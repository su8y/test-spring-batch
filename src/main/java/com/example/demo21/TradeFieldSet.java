package com.example.demo21;

import com.example.demo21.domain.Trade;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class TradeFieldSet implements FieldSetMapper<Trade> {
    @Override
    public Trade mapFieldSet(FieldSet fieldSet) throws BindException {
        Trade trade = new Trade();
        trade.setId(fieldSet.readString(0));
        trade.setCountry1(fieldSet.readString(1));
        trade.setCountry2(fieldSet.readString(2));
        trade.setYear(fieldSet.readInt(3));
        Double export;
        try {
            export = fieldSet.readDouble(4);
        } catch (NumberFormatException e) {
            export = 0.0D;
        }
        trade.setExport(export);


        return trade;
    }
}
