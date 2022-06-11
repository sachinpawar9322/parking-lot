package io.sahaj.parking;


import io.sahaj.parking.domain.ParkingTicket;
import io.sahaj.parking.domain.Vehicle;
import io.sahaj.parking.enums.VehicleParkingSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {

    private Map<VehicleParkingSize,Integer> parkingSpace;
    private Map<VehicleParkingSize, List<ParkingTicket>> parkedVehicles;

    public int getAvailableCapacity(VehicleParkingSize vehicleParkingSize){
        List<ParkingTicket> parkedVehicles = this.parkedVehicles.computeIfAbsent(vehicleParkingSize, v -> new ArrayList<ParkingTicket>());
        return  parkingSpace.get(vehicleParkingSize)- parkedVehicles.size();
    }

    private ParkingLot(Map<VehicleParkingSize,Integer> parkingSpace){
        this.parkingSpace=parkingSpace;
        this.parkedVehicles =new HashMap<>();
    }

    public static ParkingLotBuilder builder(){
        return new ParkingLotBuilder();
    }

    public ParkingTicket parkVehicle(Vehicle vehicle){

        VehicleParkingSize vehicleParkingSize = vehicle.getParkingSize();

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

        private Map<VehicleParkingSize,Integer> parkingSpace=new HashMap<>();

        public ParkingLotBuilder withCapacity(VehicleParkingSize vehicleParkingSize, int capacity){

            if(vehicleParkingSize ==null)
                throw new IllegalArgumentException("Please specify valid parking size");

            if(capacity<0){
                throw new IllegalArgumentException("Please specify valid parking capacity");
            }

            parkingSpace.put(vehicleParkingSize,capacity);

            return this;
        }

        public ParkingLot build(){

            if(parkingSpace.isEmpty())
                throw new IllegalStateException("Please specify capacity for atleast one vehichle size");

            return  new ParkingLot(parkingSpace);
        }

    }

}
