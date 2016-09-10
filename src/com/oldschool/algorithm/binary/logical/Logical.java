package com.oldschool.algorithm.binary.logical;

import com.oldschool.algorithm.utils.Config;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

/**
 * Created by KMacioszek on 2016-09-05.
 */
public abstract class Logical {

    BmpFile file;
    BmpFile secondFile;

    public Logical(BmpFile file) throws BadImageTypeException {
        if (!file.getHeader().getBinary())
            throw new BadImageTypeException(Config.get("bit_not_bin"));

        this.file = file;

        run();
    }

    public Logical(BmpFile file, BmpFile secondFile) throws Exception {
        if (!file.getHeader().getBinary())
            throw new BadImageTypeException(Config.get("bit_first_not_bin"));
        if (!secondFile.getHeader().getBinary())
            throw new BadImageTypeException(Config.get("bit_second_not_bin"));

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
