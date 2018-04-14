package com.wvup.group.groupzlchess.model;

/**
 * Created by Levi on 3/16/2018.
 */

public interface IChessGame {

    /**
     * Picks up a piece for movement
     * returns true if the piece was succesfully picked up
     *
     * @param x Horizontal Position to pick up piece, starting at 0
     * @param y Vertical Position to pick up piece, starting at 0
     * @return Return true if piece is picked up
     */
    boolean pickUpAPiece(int x, int y);

    /**
     * Sets down the piece picked up at the
     * positions specified
     *
     * @param x Horizontal Position to set piece, starting at 0
     * @param y Vertical Position to set piece, starting at 0
     * @return Return true if the piece is set down
     */
    boolean setPickedUpPieceDown(int x, int y);

    /**
     * Returns true if the piece currently picked up can be set down
     * at the cordinates given
     *
     * @param x Horizontal Position to test , starting at 0
     * @param y Vertical Position to test, starting at 0
     * @return Return true if you can set the piece down
     */
    boolean canSetPiecePickedHere(int x, int y);

    /**
     * Returns true if a piece is currently picked up
     * @return true if piece picked up
     */
    boolean isPieceCurrentlyPickedUp();



    ChessPiece getPiece(int x, int y);

    /**
     * Returns the length of the board
     * @return Return the board length
     */
    int getBoardLength();

    /**
     * Returns the width of the board
     * @return Return the board width
     */
    int getBoardWidth();
}
