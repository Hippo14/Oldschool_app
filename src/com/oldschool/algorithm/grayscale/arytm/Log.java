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

        run();
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstPixel = file.getImage().getValue(x, y);

        firstPixel = (int) Math.log((double)firstPixel);

        file.getImage().setValue(firstPixel, x, y);
    }

}
