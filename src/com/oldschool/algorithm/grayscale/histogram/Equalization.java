package com.oldschool.algorithm.grayscale.histogram;

import com.oldschool.algorithm.grayscale.histogram.Histogram;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by MSI on 2016-09-10.
 */
public class Equalization extends Histogram {
    public Equalization(BmpFile file) throws IOException, BadImageTypeException {
        super(file);

        init();
    }

    @Override
    void init() {
        int sum = 0;
        int anzpixel = file.getHeader().getWidth() * file.getHeader().getHeight();
        int[] iarray = new int[1];

        float[] lut = new float[anzpixel];
        for (int i = 0; i < histogram.length; ++i) {
            sum += histogram[i];
            lut[i] = sum * 255 / anzpixel;
        }

        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                int valueBefore = file.getImage().getRed(x, y);
                int valueAfter = (int) lut[valueBefore];
                iarray[0] = valueAfter;

                file.getImage().setRed(x, y, iarray[0]);
                file.getImage().setGreen(x, y, iarray[0]);
                file.getImage().setBlue(x, y, iarray[0]);
            }
        }
    }


}
