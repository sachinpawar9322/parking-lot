package io.sahaj.parking.domain;

import io.sahaj.parking.enums.VehicleParkingSize;

public class Bus implements Vehicle{
    @Override
    public VehicleParkingSize getParkingSize() {
        return VehicleParkingSize.LARGE;
    }
}
