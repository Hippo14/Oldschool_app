package com.oldschool.algorithm.rgb.arytm;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-08.
 */
abstract public class Operation {

    BmpFile file, secondFile;
    int constant;

    public Operation(BmpFile file, int constant) throws IOException, BadImageTypeException {
        this.constant = constant;

        if (!file.getHeader().getRGB())
            throw new BadImageTypeException("Obrazek nie jest RGB!");
        else
            this.file = file;
    }

    public Operation(BmpFile file, BmpFile secondFile) throws IOException, BadImageTypeException {
        if (!file.getHeader().getRGB())
            throw new BadImageTypeException("Pierwszy obrazek nie jest RGB!");
        else
            this.file = file;

        if (!secondFile.getHeader().getRGB())
            throw new BadImageTypeException("Drugi obrazek nie jest RGB!");
        else
            this.secondFile = secondFile;
    }

    public Operation(BmpFile file) throws IOException, BadImageTypeException {
        if (!file.getHeader().getRGB())
            throw new BadImageTypeException("Obrazek nie jest RGB!");
        else
            this.file = file;
    }

    protected void run() {
        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                makeAlgorithm(x, y);
            }
        }
    }

    public abstract void makeAlgorithm(int x, int y);

}