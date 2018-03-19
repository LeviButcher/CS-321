package com.wvup.group.groupzlchess.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wvup.group.groupzlchess.R;
import com.wvup.group.groupzlchess.model.BoardPosition;

/**
 * Created by Levi on 3/17/2018.
 */

public class ChessBoardView extends GridLayout{
    private int side;
    private RelativeLayout[][] grid;
    public final int COLOR_1 = 0xFF666666;
    public final int COLOR_2 = 0xFFFFFFFF;


    /**
     * Overloaded Constructor
     * @param context Context to add this too
     * @param width How wide is the Grid
     * @param newSide How many GridSquares should there be
     * @param listener OnClick Listener for each TextView
     */
    public ChessBoardView(Context context, int width, int newSide, View.OnTouchListener listener, View.OnDragListener drag)
    {
        super( context );
        side = newSide;

        //Set up Grid Row and Column
        setColumnCount(side);
        setRowCount(side);

        //Create the TextViews
        grid = new RelativeLayout[side][side];

        ColorSwitcher color = new ColorSwitcher();

        for(int row = 0; row < side; row++)
        {
            color.switchColor();
            for(int col = 0; col < side; col++)
            {
                grid[row][col] = new RelativeLayout(context);
                grid[row][col].addView(new ImageView(context));
                grid[row][col].setOnDragListener(drag);
                grid[row][col].getChildAt(0).setOnTouchListener(listener);
                grid[row][col].setBackgroundColor(color.switchColor());
                //grid[row][col].setBackgroundResource(R.drawable.gridoutline);
                this.addView(grid[row][col], width, width);
            }
        }
    }

    /**
     * sets the Background Color on the TextView within the grid
     * @param row Row to look at
     * @param col Column to look at
     * @param color Color to put at this Background Color
     */
    public void setBackgroundColor(int row, int col, int color)
    {
        grid[row][col].setBackgroundColor(color);
    }

    /**
     * Returns the color of square at the row and col specified
     * @param row
     * @param col
     * @return
     */
    public int getBackgroundColor(int row, int col)
    {
        if(grid[row][col].getBackground() instanceof ColorDrawable)
        {
            ColorDrawable colorD = (ColorDrawable) grid[row][col].getBackground();
            return colorD.getColor();
        }
        return 0;
    }

    public void setImage(int row, int col, int imageResource)
    {
        //imageViews[row][col].setImageResource(imageResource);
        ImageView IV = (ImageView) grid[row][col].getChildAt(0);
        IV.setImageResource(imageResource);
    }

    /**
     * Determines if the View provided is the same view at the certain position
     * @param v view to compare
     * @param row Row to look at
     * @param column Column to look at
     * @return True if the TextView the same TextView at the row and column
     */
    public boolean isSameView(View v, int row, int column)
    {
        return v == grid[row][column].getChildAt(0);
    }

    public BoardPosition getImageViewsPosition(View v)
    {
        BoardPosition position = null;
        for(int row = 0; row < side; row++) {
            for (int col = 0; col < side; col++) {
                if (isSameView(v, row, col)) {
                    position = new BoardPosition(row, col);
                }
            }
        }
        return position;
    }

    private class ColorSwitcher {
        int color = COLOR_1;

        public int switchColor() {
            if (color == COLOR_1) {
                color = COLOR_2;
                return COLOR_1;
            } else {
                color = COLOR_1;
                return COLOR_2;
            }
        }
    }
}
