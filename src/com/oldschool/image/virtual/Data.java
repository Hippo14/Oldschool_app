package com.oldschool.image.virtual;

/**
 * Created by MSI on 2017-01-07.
 */
public class Data {
    private int[][] rgb;

    public Data() {
    }

    public void setRgb(int x, int y, int rgb) {
        this.rgb[x][y] = rgb;
    }

    public void initImageMatrix(int width, int height) {
        this.rgb = new int[width][height];
    }

    public int getRgb(int x, int y) {
        return this.rgb[x][y];
    }
}
