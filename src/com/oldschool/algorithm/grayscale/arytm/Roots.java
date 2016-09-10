package com.oldschool.algorithm.grayscale.arytm;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-08.
 */
public class Roots extends Operation {

    public Roots(BmpFile file) throws IOException, BadImageTypeException {
        super(file);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstPixel = file.getImage().getRed(x, y);

        firstPixel = (int) Math.sqrt((double)firstPixel);

        file.getImage().setRed(x, y, firstPixel);
        file.getImage().setGreen(x, y, firstPixel);
        file.getImage().setBlue(x, y, firstPixel);
    }

}
