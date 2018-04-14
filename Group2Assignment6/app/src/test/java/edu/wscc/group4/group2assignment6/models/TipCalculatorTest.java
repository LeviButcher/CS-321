package edu.wscc.group4.group2assignment6.models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the TipCalculator Class
 *
 * Created by Levi on 2/17/2018.
 */
public class TipCalculatorTest
{

    public TipCalculator returnTipCalculator()
    {
        return new TipCalculator();
    }

    public TipCalculator returnCustomTipCalculator(double bill, double tipPercentage, int guests)
    {
        return new TipCalculator(bill, tipPercentage, guests);
    }

    public boolean returnTrueIfBillWasSet(double bill)
    {
        TipCalculator tp = returnTipCalculator();
        tp.setBill(bill);
        return tp.getBill() == bill;
    }

    public boolean returnTrueIfGuestWasSet(int guest)
    {
        TipCalculator tp = returnTipCalculator();
        tp.setGuests(guest);
        return tp.getGuests() == guest;
    }

    public boolean returnTrueIfTipPercentageWasSet(double tip)
    {
        TipCalculator tp = returnTipCalculator();
        tp.setTipPercentage(tip);
        return tp.getTipPercentage() == tip;
    }

    @Test
    public void testDefaultGuest()
    {
        assertTrue(returnTipCalculator().getGuests() == 2);
    }

    @Test
    public void testDefaultBill()
    {
        assertTrue(returnTipCalculator().getBill() == 0);
    }

    @Test
    public void testDefaultTip()
    {
        assertTrue(returnTipCalculator().getTipPercentage() == 0);
    }

    @Test
    public void setValueAbove0ToGuest()
    {
        assertTrue(returnTrueIfGuestWasSet(5));
    }

    @Test
    public void setValueAbove100ToGuest()
    {
        assertTrue(returnTrueIfGuestWasSet(100));
    }

    @Test
    public void setValueBelow0ToGuest()
    {
        assertTrue(!returnTrueIfGuestWasSet(-400));
    }

    @Test
    public void setValueBelow0Bill()
    {
        assertTrue(!returnTrueIfBillWasSet(-400));
    }

    @Test
    public void setValueAbove0Bill()
    {
        assertTrue(returnTrueIfBillWasSet(4000));
    }

    @Test
    public void setValueAbove0Tip()
    {
        assertTrue(returnTrueIfTipPercentageWasSet(4000));
    }

    @Test
    public void setValueBelow0Tip()
    {
        assertTrue(!returnTrueIfTipPercentageWasSet(-200));
    }

    @Test
    public void testCalculateTotalTip()
    {
        TipCalculator tp = returnCustomTipCalculator(40, 15, 1);

        assertTrue(tp.calculateTotalTip() == 6);
    }

    @Test
    public void testCalculateTotalBill()
    {
        TipCalculator tp = returnCustomTipCalculator(40, 15, 1);

        assertTrue(tp.calculateTotalBill() == 46);
    }


    @Test
    public void testCalculateTipPerGuest()
    {
        TipCalculator tp = returnCustomTipCalculator(40, 15, 4);
        System.out.println(tp.calculateTipPerGuest());
        assertTrue(tp.calculateTipPerGuest() == 1.5);
    }

    @Test
    public void testCalculateTotalAmountPerGuest()
    {
        TipCalculator tp = returnCustomTipCalculator(40, 15, 4);

        assertTrue(tp.calculateTotalAmountPerGuest() == 11.5);
    }

    @Test
    public void testPrint()
    {
        TipCalculator tp = returnCustomTipCalculator(4000, 16, 4);

        System.out.println(tp.toString());
    }
}