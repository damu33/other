package kgrzesiak.webapp.models;

import lombok.*;

@Data
@AllArgsConstructor

public class Baggage {
    private long id;
    private long weight;
    private String weightUnit;
    private long pieces;

    public Baggage(){

    }
}
