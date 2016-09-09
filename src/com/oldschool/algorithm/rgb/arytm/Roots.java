package com.oldschool.algorithm.rgb.arytm;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-09.
 */
public class Roots extends Operation {

    public Roots(BmpFile file) throws IOException, BadImageTypeException {
        super(file);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstRedPixel = file.getImage().getRed(x, y);
        int firstGreenPixel = file.getImage().getGreen(x, y);
        int firstBluePixel = file.getImage().getBlue(x, y);

        firstRedPixel = (int) Math.sqrt((double)firstRedPixel);
        firstGreenPixel = (int) Math.sqrt((double)firstGreenPixel);
        firstBluePixel = (int) Math.sqrt((double)firstBluePixel);

        file.getImage().setRed(x, y, firstRedPixel);
        file.getImage().setGreen(x, y, firstGreenPixel);
        file.getImage().setBlue(x, y, firstBluePixel);
    }

}
