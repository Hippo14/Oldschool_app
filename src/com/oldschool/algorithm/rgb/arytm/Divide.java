package com.oldschool.algorithm.rgb.arytm;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-09.
 */
public class Divide extends Operation {


    public Divide(BmpFile file, BmpFile secondFile) throws IOException, BadImageTypeException {
        super(file, secondFile);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstRedPixel = file.getImage().getRed(x, y);
        int firstGreenPixel = file.getImage().getGreen(x, y);
        int firstBluePixel = file.getImage().getBlue(x, y);

        int secondRedPixel = secondFile.getImage().getRed(x, y);
        int secondGreenPixel = secondFile.getImage().getGreen(x, y);
        int secondBluePixel = secondFile.getImage().getBlue(x, y);

        int redSum = firstRedPixel;
        if (secondRedPixel != 0)
            redSum = firstRedPixel / secondRedPixel;

        int greenSum = firstGreenPixel;
        if (secondGreenPixel != 0)
            greenSum = firstGreenPixel / secondGreenPixel;

        int blueSum = firstBluePixel;
        if (secondBluePixel != 0)
            blueSum = firstBluePixel / secondBluePixel;

        file.getImage().setRed(x, y, redSum);
        file.getImage().setGreen(x, y, greenSum);
        file.getImage().setBlue(x, y, blueSum);
    }

}
