package io.sahaj.parking.fee;

import com.google.common.collect.Range;
import io.sahaj.parking.enums.RateType;

import java.math.RoundingMode;

public class FeeRange{

    Range<Integer> hourRange;
    private double rate;
    private RateType rateType;

    private FeeRange(int startHour, int endHour, double rate, RateType rateType) {
        this.hourRange=Range.openClosed(startHour,endHour);
        this.rate = rate;
        this.rateType = rateType;
    }

    public static FeeRangeBuilder builder(){
        return new FeeRangeBuilder();
    }

    public boolean canApply(int startHour,int endHour){
        Range<Integer> givenHourRange=Range.openClosed(startHour,endHour);

        return  hourRange.isConnected(givenHourRange) && !hourRange.intersection(givenHourRange).isEmpty();
    }

    public double calculateFee(int startHour,int endHour){

        if(canApply(startHour,endHour))
        {
            Range<Integer> commonRange=hourRange.intersection(Range.openClosed(startHour,endHour));
           int diff= commonRange.upperEndpoint() - commonRange.lowerEndpoint();

           if(diff>0)
           {
               if(RateType.FLAT.equals(rateType)){
                   return rate;
               }else if(RateType.PER_HOUR.equals(rateType)){
                   return rate*diff;
               }else {
                   int noOfDays= (int) Math.ceil(diff/(24*1.0));
                   return rate*noOfDays;

               }

           }
        }

        return 0;
    }

   public static class FeeRangeBuilder{

        private int starHour=Integer.MIN_VALUE;
        private int endHour=Integer.MAX_VALUE;
        private double rate=0;
        private RateType rateType=RateType.FLAT;

        public FeeRange build(double rate,RateType type){
            this.rate=rate;
            this.rateType=type;
            return new FeeRange(starHour,endHour,rate,rateType);
        }

        public FeeRangeBuilder fromHour(int starHour){
            this.starHour=starHour;
            return this;
        }

        public FeeRangeBuilder toHour(int endHour){
            this.endHour=endHour;
            return this;
        }



    }

}