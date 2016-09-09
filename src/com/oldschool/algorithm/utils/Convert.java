package com.oldschool.algorithm.utils;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.bits.Constants;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-09.
 */
public class Convert {

    public static BmpFile convertToGrayscale(BmpFile file) throws IOException, BadImageTypeException {
        if (file.getHeader().getBitsPerPixel() == Constants.BITS_24) {
            int reds[][] = file.getImage().getReds();
            int greens[][] = file.getImage().getGreens();
            int blues[][] = file.getImage().getBlues();

            for (int x = 0; x < file.getHeader().getWidth(); x++) {
                for (int y = 0; y < file.getHeader().getHeight(); y++) {
                    int grayscale = (int) (0.3 * reds[x][y] + 0.6 * greens[x][y] + 0.1 * blues[x][y]);

                    reds[x][y] = grayscale;
                    greens[x][y] = grayscale;
                    blues[x][y] = grayscale;
                }
            }

            file.getImage().setReds(reds);
            file.getImage().setGreens(greens);
            file.getImage().setBlues(blues);

            return file;
        }
        else throw new BadImageTypeException("Zly typ obrazka!");
    }

}
