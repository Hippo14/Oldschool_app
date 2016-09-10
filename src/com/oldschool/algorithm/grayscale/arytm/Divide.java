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
        int firstPixel = file.getImage().getRed(x, y);

        int secondPixel = secondFile.getImage().getValue(x, y);

        int sum = firstPixel;
        if (secondPixel != 0)
            sum = firstPixel / secondPixel;

        file.getImage().setRed(x, y, sum);
        file.getImage().setGreen(x, y, sum);
        file.getImage().setBlue(x, y, sum);
    }

}
