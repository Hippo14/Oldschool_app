package com.oldschool.algorithm.grayscale.arytm;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-08.
 */
public class DivideConst extends Operation {


    public DivideConst(BmpFile file, int constant) throws IOException, BadImageTypeException {
        super(file, constant);

        run();
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstPixel = file.getImage().getValue(x, y);

        if (constant != 0)
            firstPixel /= constant;
        else
            throw new IllegalArgumentException("Nie mozna dzielic przez 0!");

        file.getImage().setValue(firstPixel, x, y);
    }

}
