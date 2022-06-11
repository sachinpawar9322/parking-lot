package io.sahaj.parking.fee;

import java.util.List;

public class FeeCalculator {

    List<FeeRange> feeRanges;

    public FeeCalculator(List<FeeRange> feeRanges) {
        this.feeRanges=feeRanges;
    }

    public double calculateFee( int hours) {
        double amount = 0;
        for (FeeRange r : feeRanges) {
            amount = amount + r.calculateFee(0, hours);
        }
        return amount;
    }
}