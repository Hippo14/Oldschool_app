package com.oldschool.algorithm.grayscale.arytm;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-08.
 */
public class SumConst extends Operation {

    public SumConst(BmpFile file, int constant) throws IOException, BadImageTypeException {
        super(file, constant);

        run();
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstPixel = file.getImage().getValue(x, y);

        firstPixel += constant;

        file.getImage().setValue(firstPixel, x, y);
    }

}
