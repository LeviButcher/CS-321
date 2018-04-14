package com.wvup.group.groupzlchess.model;

import android.util.Log;

import java.util.ArrayList;

public class ChessGame implements IChessGame{
    public static final int GRID_SIZE = 8;
    private ChessPiece[][] gameOfChess;
    private ChessPiece pickedUpPiece;
    private ArrayList<BoardPosition> previoslySwapped;
    private String TAG = "ChessGame";

    /**
     * Chess() - creates a new game of chess, assigns the pieces to the starting position on an 8x8 grid
     */
    public ChessGame(){
        previoslySwapped = new ArrayList<>();
        gameOfChess = new ChessPiece[GRID_SIZE][GRID_SIZE];
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                if(row == 0 ){
                    if(col == 0 || col == 7){
                        gameOfChess[row][col] = new ChessPiece("bR", CPValue.Rook, true, row, col);
                    }else if (col == 1  || col == 6){
                        gameOfChess[row][col] = new ChessPiece("bKN", CPValue.Knight, true, row, col);
                    }else if (col == 2 || col == 5){
                        gameOfChess[row][col] = new ChessPiece("bB", CPValue.Bishop, true, row, col);
                    }else if(col == 3){
                        gameOfChess[row][col] = new ChessPiece("bK", CPValue.King, true, row, col);
                    }else if (col == 4){
                        gameOfChess[row][col] = new ChessPiece("bQ", CPValue.Queen, true, row, col);
                    }
                }else if(row == 1){
                    gameOfChess[row][col] = new ChessPiece("bP", CPValue.Pawn, true, row, col);
                }else if(row == 6){
                    System.out.println("White Pawns");
                    gameOfChess[row][col] = new ChessPiece("wP", CPValue.Pawn, false, row, col);
                }else if(row == 7){
                    if(col == 0 || col == 7){
                        gameOfChess[row][col] = new ChessPiece("wR", CPValue.Rook, false, row, col);
                    }else if (col == 1  || col == 6){
                        gameOfChess[row][col] = new ChessPiece("wKN", CPValue.Knight, false, row, col);
                    }else if (col == 2 || col == 5){
                        gameOfChess[row][col] = new ChessPiece("wB", CPValue.Bishop, false, row, col);
                    }else if(col == 3){
                        gameOfChess[row][col] = new ChessPiece("wK", CPValue.King, false, row, col);
                    }else if (col == 4){
                        gameOfChess[row][col] = new ChessPiece("wQ", CPValue.Queen, false, row, col);
                    }
                }else{
                    gameOfChess[row][col] = new ChessPiece("E", CPValue.empty, false, row, col);
                }
            }
        }
    }

    /**
     * getGame() - returns the current game of chess
     * @return the Current game of chess, a multi-dimensional array of ChessPieces;
     */
    private ChessPiece[][] getGame(){
        return gameOfChess;
    }

    /**
     * checkSpace() - used to check if the current space is currently occupied by a piece
     * @param inRow - the Row (or Y position) of the space to-be checked
     * @param inCol - the Column (or X position) of the space to-be checked
     * @return boolean - true if the space is occupied, false if it is not
     */
    public boolean checkSpace(int inRow, int inCol){
        //System.out.println("Checking " + x + ", " + y + "\n\n");
        if(gameOfChess[inRow][inCol].getValue() == CPValue.empty){
            return false;
        }else {
            return true;
        }
    }

    /**
     * getPiece() returns the ChessPiece that is in the provided coordinates.
     * @param inRow - the Row (or Y position) of the space to-be checked.
     * @param inCol - the Column (or X position) of the space to-be checked.
     * @return ChessPiece - the ChessPiece that is found in that position
     * @throws NullPointerException - thrown if a space is requested where a ChessPiece is not currently.
     */
    @Override
    public ChessPiece getPiece(int inRow, int inCol) throws NullPointerException{
        //if(checkSpace(inRow, inCol)){
        return gameOfChess[inRow][inCol];
    }

    @Override
    public boolean pickUpAPiece(int row, int col) {
        if(checkSpace(row,col))
        {
            pickedUpPiece = getPiece(row,col);
            return true;
        }
        else
        {
            return false;
        }
    }

    //Side effect is that the piece previously picked up will be dropped if the movement wasn't allowed
    @Override
    public boolean setPickedUpPieceDown(int row, int col) {
        Log.d(TAG, "desired row:" + row + "col" + col);
        if(canSetPiecePickedHere(row, col))
        {
            previoslySwapped.clear();
            int startingRow = pickedUpPiece.getY();
            int startingCol = pickedUpPiece.getX();
            Log.d(TAG, "starting in row:" + startingRow + " col" + startingCol);
            previoslySwapped.add( new BoardPosition(startingRow, startingCol));
            previoslySwapped.add( new BoardPosition(row, col));

            pickedUpPiece.setX(col);
            pickedUpPiece.setY(row);

            gameOfChess[row][col] = pickedUpPiece;
            gameOfChess[startingRow][startingCol] = new ChessPiece("E", CPValue.empty, false, startingRow, startingCol);
            pickedUpPiece = null;
            return true;
        }

        pickedUpPiece = null;

        return false;
    }

    @Override
    public boolean canSetPiecePickedHere(int desiredRow, int desiredCol) {
        if(isPieceCurrentlyPickedUp())
        {
            if(pickedUpPiece.isValidMove(desiredRow,desiredCol)){
                //If we are a knight go ahead and check Overtake, or if the path is clear then check overtake
                if(pickedUpPiece.getValue() == CPValue.Knight || checkPath(pickedUpPiece.getX(), pickedUpPiece.getY(), desiredCol, desiredRow))
                {
                    return canOvertakePosition(desiredRow,desiredCol);
                }
            }
        }
        return false;
    }

    /**
     * Checks to see if the piece currently picked up can
     * take over the position passed in.
     *
     * @param desiredRow Y position on the board
     * @param desiredCol X position on the board
     * @return True if the position can be overtaken
     */
    private boolean canOvertakePosition(int desiredRow, int desiredCol)
    {
        if(isPieceCurrentlyPickedUp()){
            if(checkSpace(desiredRow, desiredCol))
            {
                //Piece there has to be a different color
                return pickedUpPiece.getColor() != getPiece(desiredRow,desiredCol).getColor();
            }
            //There is no piece there so we can take that position no matter what
            return true;
        }
        return false;
    }

    @Override
    public boolean isPieceCurrentlyPickedUp() {
        return pickedUpPiece != null;
    }

    @Override
    public int getBoardLength() {
        return GRID_SIZE;
    }

    @Override
    public int getBoardWidth() {
        return GRID_SIZE;
    }

    /**
     *Returns a arrayList containing 2 Board Position elements
     * that contain the 2 positions that were just changed
     *
     * @return Arraylist of board positions
     */
    public ArrayList<BoardPosition> lastPreviouslySwappedPositions()
    {
        return previoslySwapped;
    }

    /**
     * Checks to see if anything is in the way of a move,
     * doesn't matter for a knight
     *
     * returns true if the path is clear, false otherwise
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @return True if the path is clear
     *
     * ASSUMES Values passed in are within board values
     */
    private boolean checkPath(int startX, int startY, int endX, int endY)
    {
        if(startX < endX)
        {
            startX++;
        }
        else if(startX > endX){
            startX--;
        }

        if(startY < endY){
            startY++;
        }
        else if(startY > endY)
        {
            startY--;
        }

        //Base Case
        if(startX == endX && startY == endY)
        {
            return true;
        }

        //Base Case
        if (checkSpace(startY, startX)){
            return false;
        }

        //General Case
        return checkPath(startX, startY, endX, endY);
    }
}