package com.wvup.group.groupzlchess;

import android.content.Context;
import android.media.SoundPool;

/**
 * Okay
 * Created by Levi on 3/18/2018.
 */
public class ChessSounds
{
    SoundPool pool;
    int successfulPickupId;
    int successfulMoveId;
    int badMoveId;


    public ChessSounds(Context context, int successfulPickup, int successfulMove, int badMove)
    {
        SoundPool.Builder poolBuilder = new SoundPool.Builder();
        pool = poolBuilder.build();
        successfulPickupId = pool.load(context, successfulPickup, 1);
        successfulMoveId = pool.load(context, successfulMove, 1);
        badMoveId = pool.load(context, badMove, 1);
    }

    /**
     * Choices range from 1-3
     *
     * <ul>
     *     <li>1 = successfulPickup</li>
     *     <li>2 = successfulMove</li>
     *     <li>3 = badMove</li>
 *     <ul/>
     * @param choice
     */
    public void playSound(int choice)
    {
        switch(choice)
        {
            case 1:
                pool.play(successfulPickupId, 1.0f,1.0f,1,0,1.0f);
                break;
            case 2:
                pool.play(successfulMoveId, 1.0f,1.0f,1,0,1.0f);
                break;
            case 3:
                pool.play(badMoveId, 1.0f,1.0f,1,0,1.0f);
                break;
            default:
                //No beuno
        }
        return;
    }
}
