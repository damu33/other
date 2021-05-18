package kgrzesiak.webapp.models;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor

public class Flight {
    private long id;
    private long number;
    private String departure;
    private String arrival;
    private String date;

    public Flight(){

    }

}
