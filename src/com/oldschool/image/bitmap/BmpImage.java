package com.oldschool.image.bitmap;

import com.oldschool.image.Pixel;
import com.oldschool.image.bitmap.bits.Constants;

import java.awt.image.IndexColorModel;

/**
 * Created by KMacioszek on 2016-08-29.
 */
public class BmpImage {

    int imageType;
    int width, height;
    IndexColorModel indexColorModel;
    Pixel[] pixels;

    // Binary (1-bit)
    int[][] bit;

    // Color / Grayscale (4-bit & 8-bit)
    int[][] value;

    // Color (24-bit & 32-bit)
    int[][] red, blue, green, alpha;

    public BmpImage(int imageType, int width, int height) {
        this.imageType = imageType;
        this.width = width;
        this.height = height;
        setColorSpace();
    }

    private void setColorSpace() {
        switch (imageType) {
            case Constants.BITS_1:
                bit = new int[width][height];
            break;
            case Constants.BITS_8:
                value = new int[width][height];
            break;
            case Constants.BITS_24:
                red = new int[width][height];
                green = new int[width][height];
                blue = new int[width][height];
            break;
        }
    }

    public void setIndexColorModel(IndexColorModel indexColorModel) {
        this.indexColorModel = indexColorModel;
    }

    public void setBit(int bit, int x, int y) {
        this.bit[x][y] = bit;
    }

    public void setValue(int value, int x, int y) {
        this.value[x][y] = value;
    }

    public void setRed(int x, int y, int r) {
        this.red[x][y] = r;
    }

    public void setGreen(int x, int y, int g) {
        this.green[x][y] = g;
    }

    public void setBlue(int x, int y, int r) {
        this.blue[x][y] = r;
    }

    public void setAlpha(int x, int y, int a) {
        this.alpha[x][y] = a;
    }

    public int getBit(int x, int y) {
        return this.bit[x][y];
    }

    public int getValue(int x, int y) {
        return value[x][y];
    }

    public int getImageType() {
        return imageType;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getBit() {
        return bit;
    }

    public int[][] getValue() {
        return value;
    }

    public int getRed(int x, int y) {
        return red[x][y];
    }

    public int getBlue(int x, int y) {
        return blue[x][y];
    }

    public int getGreen(int x, int y) {
        return green[x][y];
    }

    public int getAlpha(int x, int y) {
        return alpha[x][y];
    }

    public IndexColorModel getIndexColorModel() {
        return indexColorModel;
    }

    public void setPixels(Pixel[] pixels) {
        this.pixels = pixels;
    }

    public Pixel[] getPixels() {
        return pixels;
    }

    public int[][] getReds() {
        return red;
    }

    public int[][] getGreens() {
        return green;
    }

    public int[][] getBlues() {
        return blue;
    }

    public void setReds(int[][] reds) {
        this.red = reds;
    }

    public void setGreens(int[][] greens) {
        this.green = greens;
    }

    public void setBlues(int[][] blues) {
        this.blue = blues;
    }

    public void createBit() {
        bit = new int[width][height];
    }
}
