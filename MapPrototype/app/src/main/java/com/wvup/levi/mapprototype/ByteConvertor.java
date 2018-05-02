package com.wvup.levi.mapprototype;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/**
 * Converts byte[] to something
 * and that something to a byte[]
 *
 * As of now conversion included are for Bitmaps
 * Created by Levi on 4/24/2018.
 */
public class ByteConvertor {


    /**
     * Converts a Bitmap to a Byte[] then returns that byte[]<br/>
     * Bitmap keeps its original quality. <br/>
     * Found at -> https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
     * @param bitmap Bitmap to convert to a byte[]
     * @return byte[] of the Bitmap
     */
    public static byte[] convertToByteArr(Bitmap bitmap){

        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, blob);
        return blob.toByteArray();
    }

    /**
     * Converts a byte[] to a Bitmap object <br/>
     *
     * Found at -> https://stackoverflow.com/questions/7620401/how-to-convert-byte-array-to-bitmap<br/>
     * Precondition: byte[] was a Bitmap before being byte[]<br/>
     *
     * @param array byte[] to convert
     * @return Bitmap that was converted from the byte[]
     */
    public static Bitmap convertToBitmap(byte[] array){
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }
}
