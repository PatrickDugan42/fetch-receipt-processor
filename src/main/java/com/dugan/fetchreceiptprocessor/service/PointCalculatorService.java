package com.dugan.fetchreceiptprocessor.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class PointCalculatorService {

    public PointCalculatorService(){}

    // a point for each alphaNumeric character
    public Integer alphaNumericRetailerRule(String retailer){
        return (int) retailer.chars().filter(Character::isLetterOrDigit).count();
    }

    // 50 points if a round dollar amount
    public Integer roundDollarAmountRule(BigDecimal value){
        if(value.stripTrailingZeros().scale() <= 0){
            return 50;
        }
        else return 0;
    }

    //25 points if the total is a multiple of .25
    public Integer multipleOfAQuarterRule(BigDecimal value) {
        if( value.remainder(new BigDecimal("0.25")).compareTo(BigDecimal.ZERO) == 0){
            return 25;
        }
        else return 0;
    }

    // 5 points for every two items on the receipt.
    public Integer fiveForEveryTwoItemsRule(int itemCount){
        if(itemCount == 0) return 0;
        return 5 * (itemCount / 2);
    }

    // If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2
    // and round up to the nearest integer. The result is the number of points earned.
    public Integer descriptionMultipleOfThreeRule(String description, BigDecimal price){
        String trimmed = description.trim();
        if(trimmed.length() % 3 == 0){
            return price.multiply(new BigDecimal(".2"))
                    .setScale(0, RoundingMode.CEILING).toBigInteger().intValue();
        }
        else return 0;
    }

    public Integer sixPointsForOddDayRule(String date){
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        if(localDate.getDayOfMonth() % 2 == 1){
            return 6;
        }
        else return 0;
    }

    // 10 points for time after 2:00 PM and before 4:00 PM
    public Integer timeBetweenTwoAndFourPMRule(String timeString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime time = LocalTime.parse(timeString, formatter);

        // set up 2 and 4 pm as local time to compare
        LocalTime twoPM = LocalTime.of(14, 0);
        LocalTime fourPM = LocalTime.of(16, 0);

        if(time.isAfter(twoPM) && time.isBefore(fourPM)){
            return 10;
        }
        else return 0;
    }


}
