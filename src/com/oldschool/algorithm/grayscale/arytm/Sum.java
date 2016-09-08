package com.oldschool.algorithm.grayscale.arytm;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-06.
 */
public class Sum extends Operation {

    public Sum(BmpFile file, BmpFile secondFile) throws IOException, BadImageTypeException {
        super(file, secondFile);

        run();
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstPixel = file.getImage().getValue(x, y);

        if (x < file.getHeader().getWidth() && y < file.getHeader().getHeight()) {
            int secondPixel = secondFile.getImage().getValue(x, y);
            int sum = firstPixel + secondPixel;

            file.getImage().setValue(sum, x, y);
        }
        else {
            int secondPixel = 255;
            int sum = firstPixel + secondPixel;
            file.getImage().setValue(sum, x, y);
        }
    }

}
