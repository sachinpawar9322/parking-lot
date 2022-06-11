package io.sahaj.parking;

import io.sahaj.parking.enums.RateType;
import io.sahaj.parking.fee.FeeCalculator;
import io.sahaj.parking.fee.FeeRange;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FeeCalculatorTest {


    @Test
    public void testPerHourNoStartAndEndHourSpecified(){

        List<FeeRange >ranges = new ArrayList<>();

        ranges.add(FeeRange.builder().build(10,RateType.PER_HOUR));

        FeeCalculator feeCalculator = new FeeCalculator(ranges);

        double fee=feeCalculator.calculateFee(10);

        assertEquals(100,fee,0.01);

    }

    @Test
    public void testFlatNoStartAndEndHourSpecified(){

        List<FeeRange >ranges = new ArrayList<>();

        ranges.add(FeeRange.builder().build(10,RateType.FLAT));

        FeeCalculator feeCalculator = new FeeCalculator(ranges);

        double fee=feeCalculator.calculateFee(10);

        assertEquals(10,fee,0.01);

    }

    @Test
    public void testFlatStartAndEndHourSpecified(){

        List<FeeRange >ranges = new ArrayList<>();

        ranges.add(FeeRange.builder().fromHour(0).toHour(4).build(30,RateType.FLAT));
        ranges.add(FeeRange.builder().fromHour(4).toHour(12).build(60,RateType.FLAT));
        ranges.add(FeeRange.builder().fromHour(12).build(100,RateType.PER_HOUR));

        FeeCalculator feeCalculator = new FeeCalculator(ranges);

        double fee=feeCalculator.calculateFee(10);
        assertEquals(90,fee,0.01);

        fee=feeCalculator.calculateFee(15);
        assertEquals(390,fee,0.01);
    }

    @Test
    public void testPerDayFees(){

        List<FeeRange> ranges = new ArrayList<>();

        ranges.add(FeeRange.builder().fromHour(0).toHour(1).build(0,RateType.FLAT));
        ranges.add(FeeRange.builder().fromHour(1).toHour(8).build(60,RateType.FLAT));
        ranges.add(FeeRange.builder().fromHour(8).toHour(24).build(80,RateType.FLAT));
        ranges.add(FeeRange.builder().fromHour(24).build(100,RateType.PER_DAY));

        FeeCalculator feeCalculator = new FeeCalculator(ranges);

        double fee=feeCalculator.calculateFee(20);
        assertEquals(140,fee,0.01);


        fee=feeCalculator.calculateFee(73);
        assertEquals(400,fee,0.01);

    }


}
