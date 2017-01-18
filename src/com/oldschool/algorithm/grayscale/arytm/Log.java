package com.oldschool.algorithm.grayscale.arytm;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-08.
 */
public class Log extends Operation {

    public Log(BmpFile file) throws IOException, BadImageTypeException {
        super(file);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        double firstPixel = file.getImage().getRed(x, y);
        double sum = Math.log10(firstPixel + 1);

        file.getImage().setRed(x, y, (int) (1.0 * sum));
        file.getImage().setGreen(x, y, (int) (1.0 * sum));
        file.getImage().setBlue(x, y, (int) (1.0 * sum));
    }

}
