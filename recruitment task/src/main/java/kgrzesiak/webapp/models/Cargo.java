package kgrzesiak.webapp.models;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor

public class Cargo {
    private long flightId;
    private List<Baggage> baggage;
    private List<Baggage> cargo;

    public Cargo(){

    }
}
