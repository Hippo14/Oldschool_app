package com.oldschool.algorithm.rgb.arytm;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.awt.*;
import java.io.IOException;

/**
 * Created by MSI on 2016-09-08.
 */
public class Sum extends Operation {

    public Sum(BmpFile file, BmpFile secondFile) throws IOException, BadImageTypeException {
        super(file, secondFile);

        run();
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstRedPixel = file.getImage().getRed(x, y);
        int firstGreenPixel = file.getImage().getGreen(x, y);
        int firstBluePixel = file.getImage().getBlue(x, y);

        int secondRedPixel = secondFile.getImage().getRed(x, y);
        int secondGreenPixel = secondFile.getImage().getGreen(x, y);
        int secondBluePixel = secondFile.getImage().getBlue(x, y);

        int redSum = firstRedPixel + secondRedPixel;
        int greenSum = firstGreenPixel + secondGreenPixel;
        int blueSum = firstBluePixel + secondBluePixel;

        file.getImage().setRed(x, y, redSum);
        file.getImage().setGreen(x, y, greenSum);
        file.getImage().setBlue(x, y, blueSum);
    }

}
