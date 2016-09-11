package com.oldschool.algorithm.rgb.arytm;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-09.
 */
public class MultiplicationConst extends Operation {

    public MultiplicationConst(BmpFile file, int constant) throws IOException, BadImageTypeException {
        super(file, constant);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        double firstRedPixel = file.getImage().getRed(x, y);
        double firstGreenPixel = file.getImage().getGreen(x, y);
        double firstBluePixel = file.getImage().getBlue(x, y);


        firstRedPixel *= constant;
        firstGreenPixel *= constant;
        firstBluePixel *= constant;


        file.getImage().setRed(x, y, (int) (1.0 * firstRedPixel));
        file.getImage().setGreen(x, y, (int) (1.0 * firstGreenPixel));
        file.getImage().setBlue(x, y, (int) (1.0 * firstBluePixel));
    }

}
