package com.oldschool.algorithm.grayscale.arytm;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-08.
 */
public class Multiplication extends Operation {

    public Multiplication(BmpFile file, BmpFile secondFile) throws IOException, BadImageTypeException {
        super(file, secondFile);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstPixel = file.getImage().getValue(x, y);
        int secondPixel = secondFile.getImage().getValue(x, y);

        int sum = firstPixel * secondPixel;

        file.getImage().setValue(sum, x, y);
    }

}
