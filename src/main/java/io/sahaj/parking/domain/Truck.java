package io.sahaj.parking.domain;

import io.sahaj.parking.enums.ParkingSize;

public class Truck implements Vehicle{
    @Override
    public ParkingSize getParkingSize() {
        return ParkingSize.LARGE;
    }
}
