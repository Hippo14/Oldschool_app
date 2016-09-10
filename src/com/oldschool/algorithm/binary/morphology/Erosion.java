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
    public int makeAlgorithm(int i, int j, int k, int l, int sum, Integer[][] maskArray) {
        int center = sum;
        if ((maskArray[k][l] & file.getImage().getRed(i, j)) == 1) {
            center = 1;
        }
        return center;
    }

}
