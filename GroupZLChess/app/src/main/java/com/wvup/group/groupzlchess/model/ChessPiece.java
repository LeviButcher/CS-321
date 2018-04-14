package com.wvup.group.groupzlchess.model;


import java.util.ArrayList;

/**
 * CheessPiece: a representation of a Physical ChessPiece
 * @author Zahchary Roberts - Spring 2018 Android Application Development CS 321
 */

public class ChessPiece {
    private String displayName;
    private CPValue value;
    private AllowedMoves moves;
    private int yPos;
    private int xPos;
    private boolean color;
    /**
     * Chess Piece Creation
     * @param inDisplayName <String> The display name of the chess piece
     * @param inValue <CPValue> The value of the chess piece (Pawn, Rook, Queen, etc...)
     * @param inColor <boolean> a boolean value, true representing black pieces, false representing white
     * @param inYpos the Y position (or row) where the chess piece exists on the chess board
     * @param inXpos the X position (or column) where the chess pieces exists on the chess board
     */
    public ChessPiece(String inDisplayName, CPValue inValue, boolean inColor, int inYpos, int inXpos){
        this.displayName = inDisplayName;
        this.value = inValue;
        this.color = inColor;
        this.yPos = inYpos;
        this.xPos = inXpos;

    }

    /**
     * getX - returns the current X position (or column) of the chess piece on the chess board
     * @return the current X position
     */
    public int getX(){
        return this.xPos;
    }

    /**
     * getY - returns the current Y position (or row) of the chess piece on the chess board
     * @return the current Y position
     */
    public int getY(){
        return this.yPos;
    }

    /**
     * getValue - returns the value of the Chess Piece, (King, Queen, Bishop, Knight, Rook, Pawn)
     * @return - the value of the chess piece
     */
    public CPValue getValue(){
        return this.value;
    }

    /**
     * Sets Pieces xPosition to X
     * @param x new X
     */
    public void setX(int x)
    {
        xPos = x;
    }

    /**
     * Sets Pieces yPosition to Y
     * @param y new y
     */
    public void setY(int y)
    {
        yPos = y;
    }

    /**
     * getDisplayName - returns the value used to represent the pieces on the chess board
     * @return - the visual representation of the chess piece, currently set to a String
     */
    public String getDisplayName(){
        return this.displayName;
    }

    /**
     * getColor - returns the color of the chess piece.
     * @return boolean - true represents Black pieces, false represents the white pieces
     */
    public boolean getColor(){
        return this.color;
    }

    /**
     * Checks if the position specified would be a possible move
     * for the type of piece this is.
     *
     * @param row Row position to look at
     * @param col Column position to look at
     * @return True if a valid move
     */
    public boolean isValidMove(int row, int col)
    {
        return AllowedMoves.checkMove(this, row, col);
    }

    /**
     * getValidMoves() - returns an Array List of BoardPositions. This represents all of the possible moves the chess piece can make
     * @return - ArrayLisst of BoardPositions that the piece can move to
     */
    public ArrayList<BoardPosition> getValidMoves(){
        ArrayList<BoardPosition> validMoves = new ArrayList<BoardPosition>();
        //AllowedMoves theMoves = new AllowedMoves(this);
        for(int row = 0; row < ChessGame.GRID_SIZE; row++){
            for(int col = 0; col < ChessGame.GRID_SIZE; col++){
                if( AllowedMoves.checkMove(this, row, col)){
                    validMoves.add(new BoardPosition(row, col));
                }
            }
        }
        return validMoves;
    }
}
