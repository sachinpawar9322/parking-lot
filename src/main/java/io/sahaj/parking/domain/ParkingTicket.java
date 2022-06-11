package io.sahaj.parking.domain;

public class ParkingTicket {

    private String spotId;
    private long timestamp;

    public ParkingTicket(String spotId, long timestamp) {
        this.spotId = spotId;
        this.timestamp = timestamp;
    }

    public String getSpotId() {
        return spotId;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
