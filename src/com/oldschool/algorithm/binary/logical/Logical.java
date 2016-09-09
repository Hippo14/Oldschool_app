package com.oldschool.algorithm.binary.logical;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageSizeException;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

/**
 * Created by KMacioszek on 2016-09-05.
 */
abstract class Logical {

    BmpFile file;
    BmpFile secondFile;

    public Logical(BmpFile file) throws BadImageTypeException {
        if (!file.getHeader().getBinary())
            throw new BadImageTypeException("Obrazek nie jest binarny!");

        this.file = file;

        run();
    }

    public Logical(BmpFile file, BmpFile secondFile) throws Exception {
        if (!file.getHeader().getBinary())
            throw new BadImageTypeException("Pierwszy obrazek nie jest binarny!");
        if (!secondFile.getHeader().getBinary())
            throw new BadImageTypeException("Drugi obrazek nie jest binarny!");

        this.file = file;
        this.secondFile = secondFile;

        run();
    }

    public abstract void makeAlgorithm(int x, int y);

    protected void run() {
        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y <  file.getHeader().getHeight(); y++) {
                makeAlgorithm(x, y);
            }
        }

    }

    public BmpFile getFile() {
        return file;
    }
}
