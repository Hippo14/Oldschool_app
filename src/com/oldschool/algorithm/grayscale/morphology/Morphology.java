package com.oldschool.algorithm.grayscale.morphology;

import com.oldschool.algorithm.utils.Config;
import com.oldschool.algorithm.utils.Convert;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by MSI on 2016-09-10.
 */
public abstract class Morphology {

    BmpFile file;
    private int size = 3;

    public Morphology(BmpFile file) throws BadImageTypeException, IOException {
        if (!file.getHeader().getGrayscale() || !file.getHeader().getRGB())
            throw new BadImageTypeException(Config.get("bit_bad_image"));
        if (!file.getHeader().getGrayscale())
            this.file = Convert.convertToGrayscale(file);
        else
            this.file = file;

        run();
    }

    public void run() {
        int[][] oldPixels = file.getImage().getReds();
        int[][] newPixels = new int[file.getHeader().getWidth()][file.getHeader().getHeight()];


        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                int pixel = oldPixels[x][y];

                for (int i = x - size; i < x + size; i++) {
                    for (int j = y - size; j < y + size; j++) {
                        try {
                            pixel = makeAlgorithm(pixel, i, j);
                        } catch (Exception e) {

                        }
                    }
                }
                newPixels[x][y] = pixel;
            }
        }
        file.getImage().setReds(newPixels);
        file.getImage().setGreens(newPixels);
        file.getImage().setBlues(newPixels);
    }

    protected abstract int makeAlgorithm(int centralPixel, int i, int j);

    public BmpFile getFile() {
        return file;
    }
}

