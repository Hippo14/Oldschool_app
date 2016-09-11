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
        double firstRedPixel = file.getImage().getRed(x, y);
        double firstGreenPixel = file.getImage().getGreen(x, y);
        double firstBluePixel = file.getImage().getBlue(x, y);

        double secondRedPixel = secondFile.getImage().getRed(x, y);
        double secondGreenPixel = secondFile.getImage().getGreen(x, y);
        double secondBluePixel = secondFile.getImage().getBlue(x, y);

        double redSum;
        if (secondRedPixel == 0)
            redSum = firstRedPixel;
        else
            redSum = firstRedPixel / secondRedPixel;

        double greenSum;
        if (secondRedPixel == 0)
            greenSum = firstGreenPixel;
        else
            greenSum = firstGreenPixel / secondGreenPixel;

        double blueSum;
        if (secondRedPixel == 0)
            blueSum = firstBluePixel;
        else
            blueSum = firstBluePixel / secondBluePixel;

        file.getImage().setRed(x, y, (int) (1.0 * redSum));
        file.getImage().setGreen(x, y, (int) (1.0 * greenSum));
        file.getImage().setBlue(x, y, (int) (1.0 * blueSum));
    }

}
