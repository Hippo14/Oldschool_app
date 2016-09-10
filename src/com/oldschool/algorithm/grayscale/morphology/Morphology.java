package com.oldschool.algorithm.grayscale.morphology;

import com.oldschool.algorithm.utils.Convert;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by MSI on 2016-09-10.
 */
public abstract class Morphology {

    BmpFile file;
    private int size = 2;

    public Morphology(BmpFile file) throws BadImageTypeException, IOException {
        if (!file.getHeader().getGrayscale())
            this.file = Convert.convertToGrayscale(file);
        else
            this.file = file;

        run();
    }

    public void run() {
        for (int x = 0; x < file.getHeader().getHeight(); x++) {
            for (int y = 0; y < file.getHeader().getWidth(); y++) {
                int centralPixel = file.getImage().getRed(x, y);

                for (int i = x - size; i < x + size; i++) {
                    for (int j = y - size; j < y + size; j++) {
                        if ((i >= 0&&j >= 0) && (i < file.getHeader().getHeight()&&j < file.getHeader().getWidth())) {
                            centralPixel = makeAlgorithm(centralPixel, i, j);
                        }
                    }
                }
                file.getImage().setRed(x, y, centralPixel);
                file.getImage().setGreen(x, y, centralPixel);
                file.getImage().setBlue(x, y, centralPixel);
            }
        }
    }

    protected abstract int makeAlgorithm(int centralPixel, int i, int j);

    public BmpFile getFile() {
        return file;
    }
}

