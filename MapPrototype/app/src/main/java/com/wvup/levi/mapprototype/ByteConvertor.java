package com.wvup.levi.mapprototype;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/**
 * Converts byte[] to something
 * and that something to a byte[]
 * Created by Levi on 4/24/2018.
 */

public class ByteConvertor {


    /**
     *    //https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
     * @param bitmap
     * @return
     */
    public static byte[] convertToByteArr(Bitmap bitmap){

        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, blob);
        return blob.toByteArray();
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//
//        int size = bitmap.getRowBytes() * bitmap.getHeight();
//        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
//        bitmap.copyPixelsToBuffer(byteBuffer);
//        return byteBuffer.array();
    }

    /**
     * https://stackoverflow.com/questions/7620401/how-to-convert-byte-array-to-bitmap
     * @param array
     * @return
     */
    public static Bitmap convertToBitmap(byte[] array){
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }
}
