package com.oldschool.algorithm.grayscale.morphology;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by MSI on 2016-09-10.
 */
public class Dilation extends Morphology {

    public Dilation(BmpFile file) throws BadImageTypeException, IOException {
        super(file);
    }

    @Override
    protected int makeAlgorithm(int centralPixel, int i, int j) {
        int actualPixel = file.getImage().getRed(i, j);
        if (actualPixel < centralPixel)
            centralPixel = actualPixel;
        return centralPixel;
    }

}
