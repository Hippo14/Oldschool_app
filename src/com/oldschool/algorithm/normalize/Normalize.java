package com.oldschool.algorithm.normalize;

import com.oldschool.image.Pixel;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.bits.Constants;

/**
 * Created by MSI on 2016-09-08.
 */
public class Normalize {

    BmpFile file;

    int min = 255;
    int max = 0;

    boolean normalization = true;

    public Normalize(BmpFile file) {
        this.file = file;

        init();
    }

    public void init() {
        // Szukanie największej i najmniejszej wartości piksla
        if (file.getHeader().getBitsPerPixel() == Constants.BITS_24) {
            find(file.getImage().getReds(), file.getImage().getGreens(), file.getImage().getBlues());
            normalize();
        }
        else
            normalization = false;
    }

    private void normalize() {
        if (file.getHeader().getBitsPerPixel() == Constants.BITS_24) {
            int reds[][] = file.getImage().getReds();
            int greens[][] = file.getImage().getGreens();
            int blues[][] = file.getImage().getBlues();

            for (int x = 0; x < reds.length; x++) {
                for (int y = 0; y < reds[x].length; y++) {
                    int red = 255 * (reds[x][y] - min) / (max - min);
                    int green = 255 * (greens[x][y] - min) / (max - min);
                    int blue = 255 * (blues[x][y] - min) / (max - min);

                    reds[x][y] = red;
                    greens[x][y] = green;
                    blues[x][y] = blue;
                }
            }
            file.getImage().setReds(reds);
            file.getImage().setGreens(greens);
            file.getImage().setBlues(blues);
        }
    }

    private void find(int[][] reds, int[][] greens, int[][] blues) {
        int redMin = findMin(reds);
        int redMax = findMax(reds);

        int greenMin = findMin(greens);
        int greenMax = findMax(greens);

        int blueMin = findMin(blues);
        int blueMax = findMax(blues);

        min = Math.min(Math.min(redMin, greenMin), blueMin);
        max = Math.max(Math.max(redMax, greenMax), blueMax);

    }

    private int findMax(int[][] array) {
        int max = 0;
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[x].length; y++) {
                if (array[x][y] > max)
                    max = array[x][y];
            }
        }
        return max;
    }

    private int findMin(int[][] array) {
        int min = 0;
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[x].length; y++) {
                if (array[x][y] < min)
                    min = array[x][y];
            }
        }
        return min;
    }

    public BmpFile getFile() {
        return file;
    }

    public boolean getNormalization() {
        return normalization;
    }
}
