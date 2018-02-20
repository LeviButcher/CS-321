package edu.wscc.group4.group2assignment6.models;


import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Represents a Calculator that takes in a bill amount,
 * amount of Guests, and a Percentage of a tip and can calculate the total
 * amount of the bill including tip, <br/>the tip amount, and the amount each guess will owe for
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

    /**
     * Default Constructor<br/>
     *
     * Initializes values to: <br/>
     * Bill = 0<br/>
     * TipPercent = 0<br/>
     * Guest = 2<br/>
     *
     */
    public TipCalculator() {
        setBill(0);
        setTipPercentage(0);
        setGuests(GUESTDEFAULT);
        tipPercent.setRoundingMode(RoundingMode.HALF_UP);
        monetary.setRoundingMode(RoundingMode.HALF_UP);
    }

    /**
     * Overloaded constructor<br/>
     *
     * Use when you already have the bill, tipPercentage, and guests at the same time.<br/>
     *
     * @param bill Price of the bill - double
     * @param tipPercentage Percentage of desired tip amount - double
     * @param guests Number of Guest - Int
     */
    public TipCalculator(double bill, double tipPercentage, int guests) {
        setBill(bill);
        setTipPercentage(tipPercentage);
        setGuests(guests);
        tipPercent.setRoundingMode(RoundingMode.HALF_EVEN);
        monetary.setRoundingMode(RoundingMode.HALF_EVEN);
    }

    /**
     * Returns the current value of the bill<br/>
     *
     * @return Current value of bill<br/>
     */
    public double getBill() {
        return _bill;
    }

    /**
     * Returns a formatted String of the current bill<br/>
     *
     * EX: $4.30<br/>
     *
     * @return formatted value of bill - String
     */
    public String getFormattedBill()
    {
        return monetary.format(_bill);
    }

    /**
     * Sets the bill value to the value Passed into the method<br/>
     *
     * Value passed in has to be greater than or equal to <br/>
     * @param bill Value set bill too - double
     */
    public void setBill(double bill) {
        if (bill >= 0) {
            this._bill = bill;
        } else {
            this._bill = 0;
        }

    }

    /**
     * Returns the current Tip value
     *
     *
     * @return current tip - double
     */
    public double getTipPercentage() {
        return _tipPercentage;
    }

    /**
     * Returns a formatted string of the Tip value
     *
     * EX: 0.24%
     *
     * @return formatted tip - string
     */
    public String getFormattedTipPercentage()
    {
        return tipPercent.format(_tipPercentage);
    }

    /**
     * Sets the tip value to the value passed in
     *
     * value has to be greater then or equal to 0
     *
     *
     * @param tipPercentage Value to set tip too - double
     */
    public void setTipPercentage(double tipPercentage) {
        if (tipPercentage >= 0) {
            this._tipPercentage = tipPercentage;
        } else {
            this._tipPercentage = 0;
        }

    }

    /**
     * Returns the amount of guests
     *
     * @return value of guests - int
     */
    public int getGuests() {
        return _guests;
    }

    /**
     * Sets guests to the value passed in
     *
     * EX: 4
     *
     * @param guests Value to set guests too - int
     */
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

    /**
     * Calculates the total dollar amount the tip will be
     *
     * formula - tipPercent * bill
     *
     * @return dollar amount of tip - double
     */
    public double calculateTotalTip() {
        return getTipPercentage() * getBill();
    }

    /**
     * Calculates the total bill amount, this includes the tip
     *
     * formula - tipAmount + bill
     *
     *
     * @return Total bill amount - double
     */
    public double calculateTotalBill() {
        return getBill() + calculateTotalTip();
    }

    /**
     * Calculates the total tip amount per guest
     *
     * formula - tipAmount / guests
     * @return TotalTipAmount per guests - doublee
     */
    public double calculateTipPerGuest() {
        return calculateTotalTip() / getGuests();
    }

    /**
     * Calculates the total amount paid per guests
     *
     * Formula - TotalBill / guests
     *
     * @return TotalAmount To Pay Per Guests - double
     */
    public double calculateTotalAmountPerGuest() {
        return calculateTotalBill() / getGuests();
    }

    /**
     * Returns a formatted string of the totalTip
     *
     * EX: $5.30
     *
     * @return string of totalTip - string
     */
    public String formattedTotalTip()
    {
        return monetary.format(calculateTotalTip());
    }

    /**
     * Returns a formatted string of the TotalBill
     *
     * EX: $50.30
     *
     * @return string of TotalBill - string
     */
    public String formattedTotalBill()
    {
        return monetary.format(calculateTotalBill());
    }

    /**
     * Returns a formatted string of the Total Tip Per Guests
     *
     * EX: $2.30
     *
     * @return string of Total Tip Per Guests - string
     */
    public String formattedTipPerGuest()
    {
        return monetary.format(calculateTipPerGuest());
    }

    /**
     * Returns a formatted string of the Total Amount Per Guests
     *
     * EX: $10.30
     *
     * @return string of Total Amount Per Guests - string
     */
    public String formattedTotalAmountPerGuest()
    {
        return monetary.format(calculateTotalAmountPerGuest());
    }
}
