package com.wvup.group.groupzlchess;

import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wvup.group.groupzlchess.model.BoardPosition;
import com.wvup.group.groupzlchess.model.CPValue;
import com.wvup.group.groupzlchess.model.ChessGame;
import com.wvup.group.groupzlchess.model.ChessPiece;
import com.wvup.group.groupzlchess.view.ChessBoardView;

import java.util.ArrayList;

import static com.wvup.group.groupzlchess.model.ChessGame.GRID_SIZE;

/**
 *
 *
 * Used the Chess pngs from By Wikipedia user: Cburnett [CC BY-SA 3.0 (https://creativecommons.org/licenses/by-sa/3.0)], via Wikimedia Commons
 *
 *
 */
public class MainActivity extends AppCompatActivity {
    private ChessGame ChessGame;
    private ChessBoardView chessView;
    private RelativeLayout container;
    private final String TAG = "Main Activity";
    private ChessSounds chessSounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int gridWidth = size.x / GRID_SIZE;

        container = findViewById(R.id.Container);

        ChessGame = new ChessGame();
        MyTouchListener bh = new MyTouchListener();
        DragListener dl = new DragListener();
        chessView = new ChessBoardView(this, gridWidth, GRID_SIZE, bh, dl);
        setUpChessPiecesOnBoard();
        container.addView(chessView);

        chessSounds = new ChessSounds(this, R.raw.waaa, R.raw.alert, R.raw.grunt);
    }

    private void setUpChessPiecesOnBoard()
    {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {

                ChessPiece curr = ChessGame.getPiece(row,col);
                if(curr.getValue() != CPValue.empty){
                    chessView.setImage(row, col, getChessPiecesImage(curr));
                }
            }
        }
    }

    /**
     * Returns the image id for a ChessPiece
     * @param piece
     * @return
     */
    private int getChessPiecesImage(ChessPiece piece)
    {

        switch (piece.getValue())
        {
            case King:
                return piece.getColor() == true ? R.drawable.blackking : R.drawable.whiteking;
            case Queen:
                return piece.getColor() == true ? R.drawable.blackqueen : R.drawable.whitequeen;
            case Bishop:
                return piece.getColor() == true ? R.drawable.blackbishop : R.drawable.whitebishop;
            case Pawn:
                return piece.getColor() == true ? R.drawable.blackpawn : R.drawable.whitepawn;
            case Knight:
                return piece.getColor() == true ? R.drawable.blackknight : R.drawable.whiteknight;
            case Rook:
                return piece.getColor() == true ? R.drawable.blackrook : R.drawable.whiterook;
        }

        return 0;
    }


    /**
     * Updates the board to reflect what was previously swapped
     *
     * previosly swapped is stored within the chessGame object
     */
    private void updateSwappedPositions()
    {
        ArrayList<BoardPosition> swapped = ChessGame.lastPreviouslySwappedPositions();

        for( BoardPosition position : swapped)
        {
            int col = position.getX();
            int row = position.getY();
            Log.d(TAG, "Swapped row:" + row + " col: " + col);
            ChessPiece curr = ChessGame.getPiece(row,col);
            chessView.setImage(row,col, getChessPiecesImage(curr));
        }
    }

    /**
     * Found at -> http://www.vogella.com/tutorials/AndroidDragAndDrop/article.html
     */
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                //Data about what is being drag, left empty for now
                ClipData data = ClipData.newPlainText("", "");

                //Creates a copy of the image and displays it as  the shadow as the view is being dragged
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);

                //Starts a drag on this image
                view.startDrag(data, shadowBuilder, view, 0);
                //Make the view invisible while dragging
                view.setVisibility(View.INVISIBLE);
                return true;
            }
            else
                {
                return false;
            }
        }
    }

    /**
     * 2 tutorials that helped me, Levi, write this
     * https://www.101apps.co.za/index.php/articles/drag-and-drop-tutorial.html
     * http://www.vogella.com/tutorials/AndroidDragAndDrop/article.html
     */
    private final class DragListener implements View.OnDragListener
    {
        public static final int ALLOWED_PLACEMENT = Color.GREEN;
        public static final int DENY_PLACEMENT = Color.RED;
        ImageView piece;
        RelativeLayout draggedOverRL;
        RelativeLayout piecesParent;
        BoardPosition originalPosition;
        BoardPosition hoveredPosition;
        //Default to yellow to show errors with color
        int previousColor = Color.YELLOW;

        @Override
        public boolean onDrag(View draggedOverView, DragEvent dragEvent)
        {
            //Gives the view being dragged
            piece = (ImageView) dragEvent.getLocalState();
            draggedOverRL = (RelativeLayout) draggedOverView;
            piecesParent = (RelativeLayout) piece.getParent();

            switch(dragEvent.getAction())
            {
                //user has started a drag event
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.i(TAG, "Drag event started");
                    originalPosition = chessView.getImageViewsPosition(piece);
                    try
                    {
                        ChessGame.pickUpAPiece(originalPosition.getY(), originalPosition.getX());
                        if(ChessGame.isPieceCurrentlyPickedUp())
                        {
                            chessSounds.playSound(1);
                            return true;
                        }
                        else{
                            return false;
                        }
                    }
                    catch(Exception e)
                    {
                        Log.i(TAG, "Drag event failed " + e);
                        return false;
                    }

                //User has entered a place that could be dropped into
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.i(TAG, "msg Drag event entered");

                    hoveredPosition = chessView.getImageViewsPosition(draggedOverRL.getChildAt(0));
                    previousColor = chessView.getBackgroundColor(hoveredPosition.getY(), hoveredPosition.getX());
                    boolean placementAllowed = ChessGame.canSetPiecePickedHere(hoveredPosition.getY(), hoveredPosition.getX());

                    //For checking for original position, don't need to highlight it
                    if(!originalPosition.equals(hoveredPosition))
                    {
                        chessView.setBackgroundColor(hoveredPosition.getY(), hoveredPosition.getX(), placementAllowed ? ALLOWED_PLACEMENT : DENY_PLACEMENT);
                    }
                    return true;

                //User is over a location that could be dropped into
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.i(TAG, "msg Drag event over location");
                    return true;

                //User has dropped the thing being dragged
                case DragEvent.ACTION_DROP:
                    //Gotta make sure to set the background color back
                    chessView.setBackgroundColor(hoveredPosition.getY(), hoveredPosition.getX(), previousColor);

                    if(ChessGame.setPickedUpPieceDown(hoveredPosition.getY(), hoveredPosition.getX())){
                        //Could drop images then swap based on what was moved
                        //Easier to call this method that gives the positions that were moved there images
                        updateSwappedPositions();
                        chessSounds.playSound(2);
                        //piece.setVisibility(View.VISIBLE);
                        return true;
                    }
                    else{
                        //piece.setVisibility(View.VISIBLE);
                        chessSounds.playSound(3);
                        return false;
                    }
                //Exit is basically we have gone outside of a place to drop what is being dragged
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.i(TAG, "msg Drag event exited");
                    //hoveredPosition = chessView.getImageViewsPosition(draggedOverRL.getChildAt(0));
                    chessView.setBackgroundColor(hoveredPosition.getY(), hoveredPosition.getX(), previousColor);
                    return true;

                //Ended is exactly what you think. Everything is over for this drag
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.i(TAG, "msg Drag event ended");

                    piece.setVisibility(View.VISIBLE);
                    return true;


            }
            return true;
        }
    }

}
