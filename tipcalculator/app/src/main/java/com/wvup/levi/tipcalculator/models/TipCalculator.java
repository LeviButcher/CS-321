package com.wvup.levi.tipcalculator.models;

/**
 * Represents a Calculator that takes in a bill amount,
 * amount of Guests, and a Percentage of a tip and can calculate the total
 * amount of the bill including tip, the tip amount, and the amount each guess will owe for
 * the tip and for the total amount including tip.
 *
 *
 * Created by Levi on 2/17/2018.
 */
public class TipCalculator {

    private double _bill;
    private double _tipPercentage;
    private int _guests;

    public TipCalculator()
    {
        setBill(0);
        setTipPercentage(0);
        setGuests(1);
    }

    public TipCalculator(double bill, double tipPercentage, int guests)
    {
        setBill(bill);
        setTipPercentage(tipPercentage);
        setGuests(guests);
    }

    public double getBill()
    {
        return _bill;
    }

    public void setBill(double bill)
    {
        if(bill >= 0) {
            this._bill = bill;
        }
        else{
            this._bill = 0;
        }

    }

    public double getTipPercentage()
    {
        return _tipPercentage;
    }

    public void setTipPercentage(double tipPercentage)
    {
        if(tipPercentage >= 0) {
            this._tipPercentage = tipPercentage;
        }
        else{
            this._tipPercentage = 0;
        }

    }

    public int getGuests()
    {
        return _guests;
    }

    public void setGuests(int guests)
    {
        if(guests > 0) {
            this._guests = guests;
        }
        else{
            this._guests = 1;
        }

    }

    @Override
    public String toString() {
        return "TipCalculator{" +
                "_bill=" + _bill +
                ", _tipPercentage=" + _tipPercentage +
                ", _guests=" + _guests +
                '}';
    }

    public double calculateTotalTip()
    {
        return getTipPercentage() * getBill();
    }

    public double calculateTotalBill()
    {
        return getBill() + calculateTotalTip();
    }

    public double calculateTipPerGuest()
    {
        return calculateTotalTip() / getGuests();
    }

    public double calculateTotalAmountPerGuest()
    {
        return calculateTotalBill() / getGuests();
    }


}
