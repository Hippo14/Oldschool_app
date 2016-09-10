package com.oldschool.algorithm.binary.morphology;


import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

/**
 * Created by MSI on 2016-09-10.
 */
public class Dilation extends Morphology {

    public Dilation(BmpFile file) throws BadImageTypeException {
        super(file);

        run();
    }

    @Override
    public int makeAlgorithm(int i, int j, int k, int l, int sum, Integer[][] maskArray) {
        int center = sum;
        if ((maskArray[k][l] & file.getImage().getRed(i, j)) == 0) {
            center = 0;
        }
        return center;
    }
}
