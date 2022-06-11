package io.sahaj.parking.domain;

import io.sahaj.parking.enums.VehicleParkingSize;

public class SUV implements Vehicle{
    @Override
    public VehicleParkingSize getParkingSize() {
        return VehicleParkingSize.MEDIUM;
    }
}
