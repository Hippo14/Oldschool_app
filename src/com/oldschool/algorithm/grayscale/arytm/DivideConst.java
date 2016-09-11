package com.oldschool.algorithm.grayscale.arytm;

import com.oldschool.algorithm.utils.Config;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-08.
 */
public class DivideConst extends Operation {


    public DivideConst(BmpFile file, int constant) throws IOException, BadImageTypeException {
        super(file, constant);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        double firstPixel = file.getImage().getRed(x, y);

        if (constant != 0)
            firstPixel /= constant;
        else
            throw new IllegalArgumentException(Config.get("0_exception"));

        file.getImage().setRed(x, y, (int) (1.0 * firstPixel));
        file.getImage().setGreen(x, y, (int) (1.0 * firstPixel));
        file.getImage().setBlue(x, y, (int) (1.0 * firstPixel));
    }

}
