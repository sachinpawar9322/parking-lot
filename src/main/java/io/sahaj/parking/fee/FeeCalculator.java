package io.sahaj.parking.fee;

import io.sahaj.parking.enums.VehicleParkingSize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeeCalculator {

    Map<VehicleParkingSize,List<FeeRange>> feeRanges=new HashMap<>();

    public FeeCalculator() {
    }

    public FeeCalculator(Map<VehicleParkingSize, List<FeeRange>> feeRanges) {
        this.feeRanges = feeRanges;
    }


    public void setFeeRanges(VehicleParkingSize parkingSize, List<FeeRange> ranges){
        feeRanges.put(parkingSize,ranges);
    }

    public double calculateFee(VehicleParkingSize parkingSize, int hours) {
        List<FeeRange> ranges = feeRanges.get(parkingSize);
        if(ranges!=null){
            double amount = 0;
            for (FeeRange r : ranges) {
                amount = amount + r.calculateFee(0, hours);
            }
            return amount;
        }else{
            throw  new IllegalStateException("Fee ranges not specified");
        }
    }
}