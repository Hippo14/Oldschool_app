package com.oldschool.algorithm.grayscale.arytm;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-08.
 */
public class Exponentation extends Operation {

    public Exponentation(BmpFile file, int constant) throws IOException, BadImageTypeException {
        super(file, constant);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstPixel = file.getImage().getValue(x, y);

        firstPixel = (int)Math.pow(firstPixel, constant);

        file.getImage().setValue(firstPixel, x, y);
    }

}
