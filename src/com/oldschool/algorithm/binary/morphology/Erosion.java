package com.oldschool.algorithm.binary.morphology;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

/**
 * Created by MSI on 2016-09-10.
 */
public class Erosion extends Morphology {

    public Erosion(BmpFile file) throws BadImageTypeException {
        super(file);
    }

    @Override
    public int makeAlgorithm(int i, int j, int sum) {
        int center = sum;
        if ((1 & file.getImage().getBit(i, j)) == 1) {
            center = 1;
        }
        return center;
    }

}
