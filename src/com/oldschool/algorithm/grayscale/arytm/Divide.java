package com.oldschool.algorithm.grayscale.arytm;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-08.
 */
public class Divide extends Operation {

    public Divide(BmpFile file, BmpFile secondFile) throws IOException, BadImageTypeException {
        super(file, secondFile);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        double firstPixel = file.getImage().getRed(x, y);
        double secondPixel = secondFile.getImage().getRed(x, y);

        double sum;

        if (secondPixel == 0)
            sum = firstPixel;
        else
            sum = firstPixel / secondPixel;


        file.getImage().setRed(x, y, (int) (1.0 * sum));
        file.getImage().setGreen(x, y, (int) (1.0 * sum));
        file.getImage().setBlue(x, y, (int) (1.0 * sum));
    }

}
