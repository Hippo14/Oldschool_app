package com.oldschool.algorithm.binary.logical;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

/**
 * Created by KMacioszek on 2016-09-05.
 */
public class Negation extends Logical {

    public Negation(BmpFile file) throws BadImageTypeException {
        super(file);

        run();
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int bit = file.getImage().getBit(x, y);

        if (bit == 0)
            file.getImage().setBit(1, x, y);
        else
            file.getImage().setBit(0, x, y);
    }
}
