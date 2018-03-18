package com.wvup.group.groupzlchess.model;

/**
 * AllowedMoves -  an Object used to test and store the allowed moves for each of the pieces in a chess game
 * @author Zachary Roberts - Spring 2018 Anrdoid Application Development CS 321
 */

public class AllowedMoves {

    /**
     * checkMove - checks if the desired location is a valid move for the piece
     * @param inYpos - the Y-position of the desired move
     * @param inXpos - the X-position of the desired move
     * @return boolean - True if it is a valid move, false otherwise
     */
    public static boolean checkMove(ChessPiece inPiece, int inYpos, int inXpos){
        int desiredX = inXpos;
        int desiredY = inYpos;
        int startingX = inPiece.getX();
        int startingY = inPiece.getY();

        switch( inPiece.getValue() ){
            case King:
                return kingMoves(startingX, startingY, desiredX, desiredY);
            case Queen:
                return queenMoves(startingX, startingY, desiredX, desiredY);
            case Bishop:
                return bishopMoves(startingX, startingY, desiredX, desiredY);
            case Knight:
                return knightMoves(startingX, startingY, desiredX, desiredY);
            case Rook:
                return rookMoves(startingX, startingY, desiredX, desiredY);
            case Pawn:
                return pawnMoves(startingX, startingY, desiredX, desiredY, inPiece.getColor());
            default:
                return false;
        }
    }

    private static boolean kingMoves(int startX, int startY, int desX, int desY){
        if(Math.abs(startX - desX) <= 1 && Math.abs(startY - desY) <= 1){
            return true;
        }
        return false;
    }
    private static boolean queenMoves(int startX, int startY, int desX, int desY){
        // Queen can move straight path, any direction
        if(bishopMoves(startX, startY, desX, desY) || rookMoves(startX, startY, desX, desY)){
            return true;
        }
        return false;
    }
    private static boolean bishopMoves(int startX, int startY, int desX, int desY){
        if(Math.abs(startX - desX) == Math.abs(startY - desY)){
            return true;
        }
        return false;
    }

    // Method to check the valid knight moves
    private static boolean knightMoves(int startX, int startY, int desX, int desY){
        if( ( Math.abs(desY - startY) == 2 &&  Math.abs(desX - startX) == 1) ){
            return true;
        }else if( ( Math.abs(desY - startY) == 1 &&  Math.abs(desX - startX) == 2) ){
            return true;
        }else{
            return false;
        }
    }
    private static boolean rookMoves(int startX, int startY, int desX, int desY){
        if(startX != desX && startY == desY){
            return true;
        }else if (desY != startY && startX == desX){
            return true;
        }
        return false;
    }

    // Method to check the pawn moves
    private static boolean pawnMoves(int startX, int startY, int desX, int desY, boolean colored){
        if(colored){
            if(startY == 1){
                if( ( (desY == startY + 1) || (desY == startY + 2) ) && ( desX == startX) ){
                    return true;
                }
            }else{
                if( (desY == startY + 1) && (desX == startX) ){
                    return true;
                }
            }
        }else{
            if(startY == 6 ){
                if( ( (desY == startY - 1) || (desY == startY - 2) ) && (desX == startX) ){
                    return true;
                }
            }else{
                if((desY == startY - 1 ) && ( desX == startX ) ){
                    return true;
                }
            }
        }
        return false;
    }
}