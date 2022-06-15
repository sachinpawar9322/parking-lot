package io.sahaj.parking;

import io.sahaj.parking.domain.*;
import io.sahaj.parking.enums.RateType;
import io.sahaj.parking.enums.VehicleParkingSize;
import io.sahaj.parking.fee.FeeCalculator;
import io.sahaj.parking.fee.FeeRange;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ParkingLotTest {

    private ParkingLot parkingLot;

    @Before
    public void setup() {

        parkingLot = ParkingLot.builder()
                .withCapacity(VehicleParkingSize.SMALL, 10)
                .withCapacity(VehicleParkingSize.MEDIUM, 10)
                .withCapacity(VehicleParkingSize.LARGE, 10)
                .build();

    }

    @Test
    public void testParkingScooter() {
        Vehicle scooter = new Scooter();
        ParkingTicket ticket = parkingLot.parkVehicle(scooter);
        assertNotNull(ticket);
        assertEquals(9, parkingLot.getAvailableCapacity(scooter.getParkingSize()));
    }

    @Test
    public void testParkingSUV() {
        Vehicle suv = new SUV();
        ParkingTicket ticket = parkingLot.parkVehicle(suv);
        assertNotNull(ticket);
        assertEquals(9, parkingLot.getAvailableCapacity(suv.getParkingSize()));
    }

    @Test
    public void testParkingBus() {
        Vehicle bus = new Bus();
        ParkingTicket ticket = parkingLot.parkVehicle(bus);
        assertNotNull(ticket);
        assertEquals(9, parkingLot.getAvailableCapacity(bus.getParkingSize()));
    }

    @Test
    public void testSpotIdUniqueness() {

        List<String> spotIds = new ArrayList<>();

        String spotId = parkingLot.parkVehicle(new Scooter()).getSpotId();
        assertFalse(spotIds.contains(spotId));
        spotIds.add(spotId);

        spotId = parkingLot.parkVehicle(new Scooter()).getSpotId();
        assertFalse(spotIds.contains(spotId));
        spotIds.add(spotId);

        spotId = parkingLot.parkVehicle(new Motorcycle()).getSpotId();
        assertFalse(spotIds.contains(spotId));
        spotIds.add(spotId);

        spotId = parkingLot.parkVehicle(new Motorcycle()).getSpotId();
        assertFalse(spotIds.contains(spotId));
        spotIds.add(spotId);

        spotId = parkingLot.parkVehicle(new Car()).getSpotId();
        assertFalse(spotIds.contains(spotId));
        spotIds.add(spotId);

        spotId = parkingLot.parkVehicle(new Car()).getSpotId();
        assertFalse(spotIds.contains(spotId));
        spotIds.add(spotId);

        spotId = parkingLot.parkVehicle(new SUV()).getSpotId();
        assertFalse(spotIds.contains(spotId));
        spotIds.add(spotId);

        spotId = parkingLot.parkVehicle(new SUV()).getSpotId();
        assertFalse(spotIds.contains(spotId));

    }

    @Test(expected = IllegalStateException.class)
    public void testParkingAfterFull() {

        ParkingLot parkingLot = ParkingLot.builder()
                .withCapacity(VehicleParkingSize.MEDIUM, 3)
                .build();

        parkingLot.parkVehicle(new SUV()).getSpotId();
        parkingLot.parkVehicle(new SUV()).getSpotId();
        parkingLot.parkVehicle(new SUV()).getSpotId();
        parkingLot.parkVehicle(new SUV()).getSpotId();

    }

    @Test(expected = IllegalArgumentException.class)
    public void testParkingWithCapacityNotDefinedForSize() {
        ParkingLot parkingLot = ParkingLot.builder()
                .withCapacity(VehicleParkingSize.SMALL, 3)
                .build();

        parkingLot.parkVehicle(new SUV()).getSpotId();

    }

    @Test(expected = IllegalArgumentException.class)
    public void testParkingVehicleWithInvalidParkingSize() {
        Vehicle vehicle = new Vehicle() {
            @Override
            public VehicleParkingSize getParkingSize() {
                return null;
            }
        };
        parkingLot.parkVehicle(vehicle);
    }

    @Test
    public void testSimpleUnparking() {

        List<FeeRange> ranges = new ArrayList<>();

        ranges.add(FeeRange.builder().fromHour(0).toHour(4).build(30, RateType.FLAT));
        ranges.add(FeeRange.builder().fromHour(4).toHour(12).build(60, RateType.FLAT));
        ranges.add(FeeRange.builder().fromHour(12).build(100, RateType.PER_HOUR));


        parkingLot = ParkingLot.builder()
                .withCapacity(VehicleParkingSize.SMALL, 10)
                .withFeeRange(VehicleParkingSize.SMALL, ranges)
                .build();

        ParkingTicket parkingTicket = parkingLot.parkVehicle(new Scooter());

        parkingTicket.setTimestamp(addHours(parkingTicket.getTimestamp(),15));

        double parkingFee = parkingLot.unparkVehicle(parkingTicket);

        assertEquals(390,parkingFee,0.01);


    }

    private long addHours(long timestamp, int hours) {

        return addMinutes(timestamp, hours * 60);

    }

    private long addMinutes(long timestamp, int minutes) {
        return timestamp + minutes * 60 * 1000;
    }


}
