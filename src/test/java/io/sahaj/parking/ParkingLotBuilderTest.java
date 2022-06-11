package io.sahaj.parking;


import io.sahaj.parking.enums.VehicleParkingSize;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingLotBuilderTest {

    @Test(expected = IllegalStateException.class)
    public void testParkingLotWithNoCapacity(){
        ParkingLot.builder().build();
    }

    @Test
    public void testParkingLotWithSomeCapacity(){
        ParkingLot parkingLot=ParkingLot.builder().withCapacity(VehicleParkingSize.SMALL,5).build();
        assertEquals(parkingLot.getAvailableCapacity(VehicleParkingSize.SMALL),5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParkingLotWithNullSize(){
        ParkingLot parkingLot=ParkingLot.builder().withCapacity(null,5).build();

    }

    @Test
    public void testParkingLotWithZeroCapcity(){
        ParkingLot parkingLot=ParkingLot.builder().withCapacity(VehicleParkingSize.SMALL,0).build();
        assertEquals(parkingLot.getAvailableCapacity(VehicleParkingSize.SMALL),0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParkingLotWithNegativeCapcity(){
        ParkingLot parkingLot=ParkingLot.builder().withCapacity(VehicleParkingSize.SMALL,-1).build();
    }


}
