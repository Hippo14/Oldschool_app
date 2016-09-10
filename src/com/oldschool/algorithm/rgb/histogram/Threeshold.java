package com.oldschool.algorithm.rgb.histogram;

import com.oldschool.algorithm.utils.Config;
import com.oldschool.algorithm.utils.Convert;
import com.oldschool.algorithm.utils.Loader;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by MSI on 2016-09-10.
 */
public class Threeshold extends RGBHistogram {

    int t;

    public Threeshold(BmpFile file, int t) throws IOException, BadImageTypeException, InterruptedException {
        super(file);

        this.t = t;

        threeshold();
    }

    public Threeshold(BmpFile file) throws IOException, BadImageTypeException {
        super(file);
    }

    public void threeshold() throws IOException, BadImageTypeException, InterruptedException {
        int newPixel;
        int bin;

        file = Convert.convertToGrayscale(file);
        file.getImage().createBit();

        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                bin = file.getImage().getRed(x, y);

                if (bin > t)
                    newPixel = 1;
                else
                    newPixel = 0;

                file.getImage().setBit(newPixel, x, y);
            }
        }

        file = Convert.convertToBinare(file);
    }

}
