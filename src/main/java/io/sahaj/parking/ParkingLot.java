package io.sahaj.parking;


import io.sahaj.parking.domain.ParkingTicket;
import io.sahaj.parking.domain.Vehicle;
import io.sahaj.parking.enums.VehicleParkingSize;
import io.sahaj.parking.fee.FeeCalculator;
import io.sahaj.parking.fee.FeeRange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {

    private Map<VehicleParkingSize, Integer> parkingSpace;
    private Map<VehicleParkingSize, List<ParkingTicket>> parkedVehicles;
    private FeeCalculator feeCalculator;


    private ParkingLot(Map<VehicleParkingSize, Integer> parkingSpace, FeeCalculator feeCalculator) {
        this.parkingSpace = parkingSpace;
        this.feeCalculator = feeCalculator;
        this.parkedVehicles = new HashMap<>();
    }

    public int getAvailableCapacity(VehicleParkingSize vehicleParkingSize) {
        List<ParkingTicket> parkedVehicles = this.parkedVehicles.computeIfAbsent(vehicleParkingSize, v -> new ArrayList<>());
        return parkingSpace.get(vehicleParkingSize) - parkedVehicles.size();
    }


    public static ParkingLotBuilder builder() {
        return new ParkingLotBuilder();
    }

    public ParkingTicket parkVehicle(Vehicle vehicle) {

        VehicleParkingSize vehicleParkingSize = vehicle.getParkingSize();

        if (vehicleParkingSize == null || !parkingSpace.containsKey(vehicleParkingSize)) {
            throw new IllegalArgumentException("Cannot park vehicle with invalid vehicle size :" + vehicleParkingSize);
        }

        List<ParkingTicket> parkedVehicles = this.parkedVehicles.computeIfAbsent(vehicleParkingSize, v -> new ArrayList<ParkingTicket>());
        int availableCapacity = parkingSpace.get(vehicleParkingSize) - parkedVehicles.size();

        if (availableCapacity == 0)
            throw new IllegalStateException("No more space available for vehicle size : " + vehicleParkingSize);

        ParkingTicket parkingTicket = new ParkingTicket(vehicleParkingSize + "-" + availableCapacity, System.currentTimeMillis(), vehicleParkingSize);

        parkedVehicles.add(parkingTicket);
        this.parkedVehicles.put(vehicleParkingSize, parkedVehicles);

        return parkingTicket;

    }

    public double unparkVehicle(ParkingTicket ticket) {

        Long currentTime = System.currentTimeMillis();
        Long parkedDuartion = Math.abs(currentTime - ticket.getTimestamp());
        int parkedHours = millisToHour(parkedDuartion);

        return feeCalculator.calculateFee(ticket.getParkingSize(), parkedHours);
    }

    private int millisToHour(Long millis) {
        double hours = millis / (1.0 * 1000 * 60 * 60);
        return (int) Math.ceil(hours);
    }


    public static class ParkingLotBuilder {

        private ParkingLotBuilder() {
        }

        private Map<VehicleParkingSize, Integer> parkingSpace = new HashMap<>();
        private Map<VehicleParkingSize, List<FeeRange>> feeRanges = new HashMap<>();


        public ParkingLotBuilder withCapacity(VehicleParkingSize vehicleParkingSize, int capacity) {

            if (vehicleParkingSize == null)
                throw new IllegalArgumentException("Please specify valid parking size");

            if (capacity < 0) {
                throw new IllegalArgumentException("Please specify valid parking capacity");
            }

            parkingSpace.put(vehicleParkingSize, capacity);

            return this;
        }

        public ParkingLotBuilder withFeeRange(VehicleParkingSize parkingSize, List<FeeRange> ranges) {
            feeRanges.put(parkingSize, ranges);
            return this;
        }


        public ParkingLot build() {
            if (parkingSpace.isEmpty())
                throw new IllegalStateException("Please specify capacity for at least one vehicle size");
            FeeCalculator calculator = new FeeCalculator(this.feeRanges);
            return new ParkingLot(parkingSpace, calculator);
        }
    }


}
