package io.sahaj.parking;


import io.sahaj.parking.domain.ParkingLot;
import io.sahaj.parking.enums.ParkingSize;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingLotBuilderTest {

    @Test(expected = IllegalStateException.class)
    public void testParkingLotWithNoCapacity(){
        ParkingLot.builder().build();
    }

    @Test
    public void testParkingLotWithSomeCapacity(){
        ParkingLot parkingLot=ParkingLot.builder().withCapacity(ParkingSize.SMALL,5).build();
        assertEquals(parkingLot.getAvailableCapacity(ParkingSize.SMALL),5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParkingLotWithNullSize(){
        ParkingLot parkingLot=ParkingLot.builder().withCapacity(null,5).build();

    }

    @Test
    public void testParkingLotWithZeroCapcity(){
        ParkingLot parkingLot=ParkingLot.builder().withCapacity(ParkingSize.SMALL,0).build();
        assertEquals(parkingLot.getAvailableCapacity(ParkingSize.SMALL),0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParkingLotWithNegativeCapcity(){
        ParkingLot parkingLot=ParkingLot.builder().withCapacity(ParkingSize.SMALL,-1).build();
    }


}
