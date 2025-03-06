package com.dugan.fetchreceiptprocessor;

import com.dugan.fetchreceiptprocessor.service.PointCalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PointCalculatorServiceTest {

    @Autowired
    PointCalculatorService uut;

    @Test
    void alphaNumericRule() {
        assertEquals(14, uut.alphaNumericRetailerRule("M&M Corner Market"));
        assertEquals(6, uut.alphaNumericRetailerRule("Target"));
    }

    @Test
    void roundDollarAmountRule() {
        assertEquals(50, uut.roundDollarAmountRule(new BigDecimal(10.00)));
        assertEquals(0, uut.roundDollarAmountRule(new BigDecimal(10.11)));
    }

    @Test
    void multipleOfAQuarterRule(){
        assertEquals(25, uut.multipleOfAQuarterRule(new BigDecimal("9.00")));
        assertEquals(0, uut.multipleOfAQuarterRule(new BigDecimal("10.32")));
        assertEquals(25, uut.multipleOfAQuarterRule(new BigDecimal("123.25")));
    }

    @Test
    void fiveForEveryTwoItemsRule(){
        assertEquals(0, uut.fiveForEveryTwoItemsRule(1));
        assertEquals(10, uut.fiveForEveryTwoItemsRule(5));
        assertEquals(15, uut.fiveForEveryTwoItemsRule(6));
        assertEquals(0, uut.fiveForEveryTwoItemsRule(0));
    }

    @Test
    void multipleOfThreeRule(){
        assertEquals(3,
                uut.descriptionMultipleOfThreeRule("Emils Cheese Pizza", new BigDecimal("12.25")));
        assertEquals(3,
                uut.descriptionMultipleOfThreeRule("Klarbrunn 12-PK 12 FL OZ", new BigDecimal("12.00")));
        assertEquals(3,
                uut.descriptionMultipleOfThreeRule("         Klarbrunn 12-PK 12 FL OZ  ", new BigDecimal("12.00")));
        assertEquals(0,
                uut.descriptionMultipleOfThreeRule("12345", new BigDecimal("12.00")));
    }

    @Test
    void sixPointsForOddDayRule(){
        assertEquals(6, uut.sixPointsForOddDayRule("2024-01-03"));
        assertEquals(6, uut.sixPointsForOddDayRule("2024-01-15"));
        assertEquals(0, uut.sixPointsForOddDayRule("2024-01-20"));
    }

    @Test
    void timeBetweenTwoAndFour(){
        assertEquals(10, uut.timeBetweenTwoAndFourPMRule("15:00"));
        assertEquals(10, uut.timeBetweenTwoAndFourPMRule("15:59"));
        assertEquals(10, uut.timeBetweenTwoAndFourPMRule("14:01"));
        assertEquals(0, uut.timeBetweenTwoAndFourPMRule("14:00"));
        assertEquals(0, uut.timeBetweenTwoAndFourPMRule("16:00"));
        assertEquals(0, uut.timeBetweenTwoAndFourPMRule("4:00"));

    }
}
