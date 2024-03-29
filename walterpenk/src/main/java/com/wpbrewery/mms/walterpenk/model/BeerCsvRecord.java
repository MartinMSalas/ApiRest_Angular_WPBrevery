package com.wpbrewery.mms.walterpenk.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeerCsvRecord {
    //"row","count.x","abv","ibu","id","beer","style","brewery_id","ounces","style2","count.y","brewery","city","state","label"
    @CsvBindByName
    private Integer row;
    @CsvBindByName(column = "count.x")
    private Integer count;
    @CsvBindByName
    private String abv;
    @CsvBindByName
    private String ibu;
    @CsvBindByName
    private Integer id;
    @CsvBindByName
    private String beer;
    @CsvBindByName
    private String style;
    @CsvBindByName(column = "brewery_id")
    private Integer breweryId;
    @CsvBindByName
    private Float ounces;
    @CsvBindByName
    private String style2;
    @CsvBindByName(column = "count.y")
    private String countY;
    @CsvBindByName
    private String city;
    @CsvBindByName
    private String state;
    @CsvBindByName
    private String label;

}
