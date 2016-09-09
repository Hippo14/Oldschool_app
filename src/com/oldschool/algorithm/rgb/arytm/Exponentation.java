package com.oldschool.algorithm.rgb.arytm;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-09.
 */
public class Exponentation extends Operation {

    public Exponentation(BmpFile file, int constant) throws IOException, BadImageTypeException {
        super(file, constant);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstRedPixel = file.getImage().getRed(x, y);
        int firstGreenPixel = file.getImage().getGreen(x, y);
        int firstBluePixel = file.getImage().getBlue(x, y);

        firstRedPixel = (int)Math.pow(firstRedPixel, constant);
        firstGreenPixel = (int)Math.pow(firstGreenPixel, constant);
        firstBluePixel = (int)Math.pow(firstBluePixel, constant);


        file.getImage().setRed(x, y, firstRedPixel);
        file.getImage().setGreen(x, y, firstGreenPixel);
        file.getImage().setBlue(x, y, firstBluePixel);
    }

}
