package io.sahaj.parking.domain;

import io.sahaj.parking.enums.VehicleParkingSize;

public class Truck implements Vehicle{
    @Override
    public VehicleParkingSize getParkingSize() {
        return VehicleParkingSize.LARGE;
    }
}
