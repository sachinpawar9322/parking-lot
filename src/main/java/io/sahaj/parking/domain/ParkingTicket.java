package io.sahaj.parking.domain;

import io.sahaj.parking.enums.VehicleParkingSize;

public class ParkingTicket {

    private String spotId;
    private long timestamp;
    private VehicleParkingSize parkingSize;

    public ParkingTicket(String spotId, long timestamp, VehicleParkingSize parkingSize) {
        this.parkingSize=parkingSize;
        this.spotId = spotId;
        this.timestamp = timestamp;
    }

    public String getSpotId() {
        return spotId;
    }

    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public VehicleParkingSize getParkingSize() {
        return parkingSize;
    }

    public void setParkingSize(VehicleParkingSize parkingSize) {
        this.parkingSize = parkingSize;
    }
}
