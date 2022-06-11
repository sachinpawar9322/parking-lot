package io.sahaj.parking.domain;

import io.sahaj.parking.enums.VehicleParkingSize;

public class Motorcycle implements Vehicle{
    @Override
    public VehicleParkingSize getParkingSize() {
        return VehicleParkingSize.SMALL;
    }
}
