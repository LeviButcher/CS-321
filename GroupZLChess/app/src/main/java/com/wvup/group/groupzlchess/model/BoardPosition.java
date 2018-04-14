package com.wvup.group.groupzlchess.model;


/**
 * BoardPositions - a set of integers, when used together represents a space on the Chess Board
 * @author Zachary Roberts - Spring 2018 Android Application Development CS 321
 */

public class BoardPosition {
    private int x;
    private int y;

    /**
     * BoardPosition() creates a new Board position
     * @param row - the row, or Y-Position, of the space on the board
     * @param col the Column, or X-Position, of the space on the board
     */
    public BoardPosition(int row, int col){
        this.x = col;
        this.y = row;
    }

    /**
     * getX - returns the column or x-position, of the space represented by this object
     * @return int - the x position
     */
    public int getX(){
        return this.x;
    }

    /**
     * getY - returns the Row, or Y-position, of the space represented by this object
     * @return int - the y position
     */
    public int getY(){
        return this.y;
    }

    public boolean equals(BoardPosition otherBoard)
    {
        if(otherBoard != null)
        {
            if(this.getY() == otherBoard.getY() && this.getX() == otherBoard.getX())
                return true;
        }
        return false;
    }
}