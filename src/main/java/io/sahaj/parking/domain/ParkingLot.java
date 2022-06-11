package io.sahaj.parking.domain;


import io.sahaj.parking.enums.ParkingSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {

    private Map<ParkingSize,Integer> parkingSpace;
    private Map<ParkingSize, List<ParkingTicket>> parkedVehicles;

    public int getAvailableCapacity(ParkingSize parkingSize){
        List<ParkingTicket> parkedVehicles = this.parkedVehicles.computeIfAbsent(parkingSize, v -> new ArrayList<ParkingTicket>());
        return  parkingSpace.get(parkingSize)- parkedVehicles.size();
    }

    private ParkingLot(Map<ParkingSize,Integer> parkingSpace){
        this.parkingSpace=parkingSpace;
        this.parkedVehicles =new HashMap<>();
    }

    public static ParkingLotBuilder builder(){
        return new ParkingLotBuilder();
    }

    public ParkingTicket parkVehicle(Vehicle vehicle){

        ParkingSize vehicleParkingSize = vehicle.getParkingSize();

        if(vehicleParkingSize ==null || !parkingSpace.containsKey(vehicleParkingSize)){
            throw new IllegalArgumentException("Cannot park vehichle with invalid vehichle size :" + vehicleParkingSize);
        }

        List<ParkingTicket> parkedVehicles = this.parkedVehicles.computeIfAbsent(vehicleParkingSize, v -> new ArrayList<ParkingTicket>());
        int availableCapacity=parkingSpace.get(vehicleParkingSize)- parkedVehicles.size();

        if(availableCapacity==0)
            throw new IllegalStateException("No more space available for vehicle size : "+ vehicleParkingSize);

        ParkingTicket parkingTicket = new ParkingTicket(vehicleParkingSize +""+availableCapacity,System.currentTimeMillis());

        parkedVehicles.add(parkingTicket);
        this.parkedVehicles.put(vehicleParkingSize,parkedVehicles);

        return parkingTicket;

    }


    public static class ParkingLotBuilder{

        private ParkingLotBuilder(){

        }

        private Map<ParkingSize,Integer> parkingSpace=new HashMap<>();

        public ParkingLotBuilder withCapacity(ParkingSize parkingSize, int capacity){

            if(parkingSize==null)
                throw new IllegalArgumentException("Please specify valid parking size");

            if(capacity<0){
                throw new IllegalArgumentException("Please specify valid parking capacity");
            }

            parkingSpace.put(parkingSize,capacity);

            return this;
        }

        public ParkingLot build(){

            if(parkingSpace.isEmpty())
                throw new IllegalStateException("Please specify capacity for atleast one vehichle size");

            return  new ParkingLot(parkingSpace);
        }

    }
}
