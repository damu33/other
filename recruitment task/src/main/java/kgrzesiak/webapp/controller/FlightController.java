package kgrzesiak.webapp.controller;



import kgrzesiak.webapp.models.Baggage;
import kgrzesiak.webapp.models.Cargo;
import kgrzesiak.webapp.models.Flight;
import kgrzesiak.webapp.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;


@RestController
public class FlightController {

    public long getFlightId(List<Flight> flights, long number, String date){
        long id=-1;
        for(int i=0; i<flights.size(); i++){
            if(flights.get(i).getNumber()==number && flights.get(i).getDate().equals(date)){
                id=flights.get(i).getId();
            }
        }
        return id;
    }

    public List<List<Long>> getFlightIds(List<Flight> flights, String code, String date){
        List<Long> departureIds = new LinkedList<>();
        List<Long> arrivalIds = new LinkedList<>();
        for(int i=0; i<flights.size(); i++){
            if(flights.get(i).getDeparture().equals(code) && flights.get(i).getDate().equals(date)){
                departureIds.add(flights.get(i).getId());
            }
            else if(flights.get(i).getArrival().equals(code) && flights.get(i).getDate().equals(date)){
                arrivalIds.add(flights.get(i).getId());
            }
        }
        List<List<Long>> allFlights = new LinkedList<>();
        allFlights.add(departureIds);
        allFlights.add(arrivalIds);

        return allFlights;
    }

    public double cargoWeights(Cargo cargo){
        double sum=0;
        List<Baggage> cargos = cargo.getCargo();
        for(int i=0; i<cargos.size(); i++){
            if(cargos.get(i).getWeightUnit().equals("lb")){
                sum+=0.4536*cargos.get(i).getWeight();
            }
            else{
                sum+=cargos.get(i).getWeight();
            }
        }
        return sum;
    }

    public double baggageWeights(Cargo cargo){
        double sum=0;
        List<Baggage> baggages = cargo.getBaggage();
        for(int i=0; i<baggages.size(); i++){
            if(baggages.get(i).getWeightUnit().equals("lb")){
                sum+=0.4536*baggages.get(i).getWeight();
            }
            else{
                sum+=baggages.get(i).getWeight();
            }
        }
        return sum;
    }

    public int countPieces(Cargo cargo){
        int pieces=0;
        for(int i=0; i<cargo.getBaggage().size(); i++){
            pieces+=cargo.getBaggage().get(i).getPieces();
        }
        return pieces;
    }

    @Autowired
    Service service;

    @GetMapping("/task1/{flightNumber}/{date}")
    public String task1(@PathVariable(value="flightNumber") long number, @PathVariable(value="date") String date){
        List<Cargo> cargos = service.getCargos();
        List<Flight> flights = service.getFlights();
        long id=getFlightId(flights, number, date);
        double bagWeight = 0;
        double cargoWeight = 0;
        for(int i=0; i<cargos.size(); i++){
            if(cargos.get(i).getFlightId()==id){
                bagWeight=baggageWeights(cargos.get(i));
                cargoWeight=cargoWeights(cargos.get(i));
            }
        }
        double total=bagWeight+cargoWeight;
        if(total==0){
            return "no such flight found";
        }
        return "cargo weight: "+cargoWeight+", baggage weight: "+bagWeight+", total weight: "+total+ " (kg)";
    }


    @GetMapping("/task2/{code}/{date}")
    public String task2(@PathVariable(value="code") String code, @PathVariable(value="date") String date){
        List<Cargo> cargos = service.getCargos();
        List<Flight> flights = service.getFlights();
        List<List<Long>> allFlights=getFlightIds(flights, code, date);
        int departing = allFlights.get(0).size();
        int arriving = allFlights.get(1).size();

        int departingPieces=0;
        int arrivingPieces=0;

        for(int i=0; i<allFlights.get(0).size(); i++){
            long id=allFlights.get(0).get(i);
            for(int j=0; j<cargos.size(); j++){
                if(cargos.get(j).getFlightId()==id){
                    departingPieces+=countPieces(cargos.get(j));
                }
            }
        }

        for(int i=0; i<allFlights.get(1).size(); i++){
            long id=allFlights.get(1).get(i);
            for(int j=0; j<cargos.size(); j++){
                if(cargos.get(j).getFlightId()==id){
                    arrivingPieces+=countPieces(cargos.get(j));
                }
            }
        }
        if(departing+arriving==0){
            return "no such flights found";
        }
        return "departing flights: "+departing+", arriving flights: "+arriving+", departing baggage number: "+departingPieces + ", arriving baggage number: "+arrivingPieces;
    }



}
