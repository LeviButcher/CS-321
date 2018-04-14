package com.wvup.group.groupzlchess.model;

/**
 * enum CPValues - the allowed values of the chess pieces
 * @author Zachary Roberts - Spring 2018 Android Application Development CS 321
 */


public enum CPValue {
    King(0), Queen(1), Bishop(2), Knight(3), Rook(4), Pawn(5), empty(6);

    private final int value;
    CPValue(int givenValue){
        this.value = givenValue;
    }
    public int getValue(){
        return this.value;
    }
}

