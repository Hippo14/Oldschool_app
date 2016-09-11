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
    public int makeAlgorithm(int i, int j, int sum) {
        int center = sum;
        if ((1 & file.getImage().getBit(i, j)) == 0) {
            center = 0;
        }
        return center;
    }
}
