package com.oldschool.image;

import com.oldschool.image.bitmap.read.LitEndInputStream;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-08-29.
 */
public class Pixel {

    int reserved;
    int red;
    int green;
    int blue;

    public Pixel(LitEndInputStream litEndInputStream) throws IOException {
        blue = litEndInputStream.readUnsignedByte();
        green = litEndInputStream.readUnsignedByte();
        red = litEndInputStream.readUnsignedByte();
        reserved = litEndInputStream.readUnsignedByte();
    }

    public Pixel(int grayscale) {
        this.red = grayscale;
        this.green = grayscale;
        this.blue = grayscale;
        this.reserved = 0;
    }

    public Pixel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.reserved = 0;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}