package com.adel.h.kalenderapi.feiertag;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;


public class HolidayDTO {
    @JsonProperty("holiday")
    private String name;
    @JsonProperty("date")
    private LocalDate date;

}
