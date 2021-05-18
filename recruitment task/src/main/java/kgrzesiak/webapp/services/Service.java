package kgrzesiak.webapp.services;


import kgrzesiak.webapp.models.Baggage;
import kgrzesiak.webapp.models.Cargo;
import kgrzesiak.webapp.models.Flight;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import javax.annotation.PostConstruct;
import java.util.*;


@org.springframework.stereotype.Service


public class Service {

    private LinkedList<Flight> flightRepo = new LinkedList<>();
    private LinkedList<Cargo> cargoRepo = new LinkedList<>();


    @PostConstruct

    public void jsonReaderFlight(){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/main/resources/generated (1).json"));
            JSONArray flights = (JSONArray) obj;
            flights.forEach(flight -> parseFlight((JSONObject) flight));


            for(int i=0; i< flightRepo.size(); i++){
                System.out.println(flightRepo.get(i));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostConstruct

    public void jsonReaderCargo(){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/main/resources/generated (2).json"));
            JSONArray cargos = (JSONArray) obj;
            cargos.forEach(cargo -> parseCargo((JSONObject) cargo));

            for(int i=0; i<cargoRepo.size(); i++){
                System.out.println(cargoRepo.get(i));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void parseFlight(JSONObject object){
        Flight flight = new Flight();
        flight.setId((long) object.get("flightId"));
        flight.setNumber((long) object.get("flightNumber"));
        flight.setDeparture((String) object.get("departureAirportIATACode"));
        flight.setArrival((String) object.get("arrivalAirportIATACode"));
        flight.setDate((String) object.get("departureDate"));

        flightRepo.add(flight);
    }

    public void parseCargo(JSONObject object){
        Cargo cargo = new Cargo();
        List<Baggage> baggageList = new LinkedList<>();
        List<Baggage> cargoList = new LinkedList<>();
        cargo.setFlightId((long) object.get("flightId"));
        JSONArray baggages = (JSONArray) object.get("baggage");
        JSONArray cargos = (JSONArray) object.get("cargo");

        for(int i=0; i<baggages.size(); i++){
            Baggage baggage = new Baggage();
            JSONObject bag=(JSONObject) baggages.get(i);
            baggage.setId((long) bag.get("id"));
            baggage.setWeight((long) bag.get("weight"));
            baggage.setWeightUnit((String) bag.get("weightUnit"));
            baggage.setPieces((long)(bag.get("pieces")));
            baggageList.add(baggage);
        }

        for(int i=0; i<cargos.size(); i++){
            Baggage carg = new Baggage();
            JSONObject bag=(JSONObject) cargos.get(i);
            carg.setId((long) bag.get("id"));
            carg.setWeight((long) bag.get("weight"));
            carg.setWeightUnit((String) bag.get("weightUnit"));
            carg.setPieces((long)(bag.get("pieces")));
            cargoList.add(carg);
        }
        cargo.setBaggage(baggageList);
        cargo.setCargo(cargoList);

        cargoRepo.add(cargo);

    }

    public List<Cargo> getCargos(){
        return this.cargoRepo;
    }

    public List<Flight> getFlights(){
        return this.flightRepo;
    }


}
