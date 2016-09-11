package com.oldschool.algorithm.filters;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.util.Arrays;

/**
 * Created by MSI on 2016-09-11.
 */
public class FilterMedian extends Filter {

    int maskSize = 3;
    int medianRed, medianGreen, medianBlue;
    Integer[] redPixels, greenPixels, bluePixels;

    public FilterMedian(BmpFile file) throws BadImageTypeException {
        super(file);

        init();
    }

    public void init() {
        int z;

        int[][] reds = new int[file.getHeader().getWidth()][file.getHeader().getHeight()];
        int[][] greens = new int[file.getHeader().getWidth()][file.getHeader().getHeight()];
        int[][] blues = new int[file.getHeader().getWidth()][file.getHeader().getHeight()];

        for (int x = maskSize; x < file.getHeader().getHeight() - maskSize; x++) {
            for (int y = maskSize; y < file.getHeader().getWidth() - maskSize; y++) {
                redPixels = new Integer[2 * maskSize + 1];
                greenPixels = new Integer[2 * maskSize + 1];
                bluePixels = new Integer[2 * maskSize + 1];

                z = 0;

                for (int i = x - (maskSize / 2); i <= x + (maskSize / 2); i++) {
                    for (int j = y - (maskSize / 2); j <= y + (maskSize / 2); j++) {
                        try {
                            redPixels[z] = file.getImage().getRed(i, j);
                            greenPixels[z] = file.getImage().getGreen(i, j);
                            bluePixels[z] = file.getImage().getBlue(i, j);
                        } catch (Exception e) {

                        }

                        z++;
                    }
                }

                Arrays.sort(redPixels);
                Arrays.sort(greenPixels);
                Arrays.sort(bluePixels);

                medianRed = getMedianFromArray(redPixels);
                medianGreen = getMedianFromArray(greenPixels);
                medianBlue = getMedianFromArray(bluePixels);

                reds[x][y] = medianRed;
                greens[x][y] = medianGreen;
                blues[x][y] = medianBlue;
            }
        }
        file.getImage().setReds(reds);
        file.getImage().setGreens(greens);
        file.getImage().setBlues(blues);
    }

    private int getMedianFromArray(Integer[] pixelArray) {
        if (pixelArray.length % 2 == 0)
            return (pixelArray[pixelArray.length / 2] + pixelArray[pixelArray.length / 2 - 1]) / 2;
        else
            return pixelArray[pixelArray.length / 2];
    }

}
