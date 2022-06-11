package io.sahaj.parking.domain;

import io.sahaj.parking.enums.ParkingSize;

public class Bus implements Vehicle{
    @Override
    public ParkingSize getParkingSize() {
        return ParkingSize.LARGE;
    }
}
