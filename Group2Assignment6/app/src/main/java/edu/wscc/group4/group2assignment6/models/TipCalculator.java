package edu.wscc.group4.group2assignment6.models;


import java.math.RoundingMode;
import java.text.DecimalFormat;

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
    private final int GUESTDEFAULT = 2;
    DecimalFormat monetary = new DecimalFormat("$0.00");
    DecimalFormat tipPercent = new DecimalFormat("0.0'%'");


    public TipCalculator() {
        setBill(0);
        setTipPercentage(0);
        setGuests(GUESTDEFAULT);
        tipPercent.setRoundingMode(RoundingMode.HALF_UP);
        monetary.setRoundingMode(RoundingMode.HALF_UP);
    }

    public TipCalculator(double bill, double tipPercentage, int guests) {
        setBill(bill);
        setTipPercentage(tipPercentage);
        setGuests(guests);
        tipPercent.setRoundingMode(RoundingMode.HALF_EVEN);
        monetary.setRoundingMode(RoundingMode.HALF_EVEN);
    }

    public double getBill() {
        return _bill;
    }

    public String getFormattedBill()
    {
        return monetary.format(_bill);
    }

    public void setBill(double bill) {
        if (bill >= 0) {
            this._bill = bill;
        } else {
            this._bill = 0;
        }

    }

    public double getTipPercentage() {
        return _tipPercentage;
    }

    public String getFormattedTipPercentage()
    {
        return tipPercent.format(_tipPercentage);
    }

    public void setTipPercentage(double tipPercentage) {
        if (tipPercentage >= 0) {
            this._tipPercentage = tipPercentage;
        } else {
            this._tipPercentage = 0;
        }

    }

    public int getGuests() {
        return _guests;
    }

    public void setGuests(int guests) {
        if (guests > 0) {
            this._guests = guests;
        } else {
            this._guests = 1;
        }

    }

    @Override
    public String toString() {
        return "TipCalculator{" +
                "_bill=" + getFormattedBill() +
                ", _tipPercentage=" + getFormattedTipPercentage() +
                ", _guests=" + _guests +
                '}';
    }

    public double calculateTotalTip() {
        return getTipPercentage() * getBill();
    }

    public double calculateTotalBill() {
        return getBill() + calculateTotalTip();
    }

    public double calculateTipPerGuest() {
        return calculateTotalTip() / getGuests();
    }

    public double calculateTotalAmountPerGuest() {
        return calculateTotalBill() / getGuests();
    }

    public String formattedTotalTip()
    {
        return monetary.format(calculateTotalTip());
    }

    public String formattedTotalBill()
    {
        return monetary.format(calculateTotalBill());
    }

    public String formattedTipPerGuest()
    {
        return monetary.format(calculateTipPerGuest());
    }

    public String formattedTotalAmountPerGuest()
    {
        return monetary.format(calculateTotalAmountPerGuest());
    }
}
