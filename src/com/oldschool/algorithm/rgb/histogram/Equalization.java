package com.oldschool.algorithm.rgb.histogram;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by MSI on 2016-09-10.
 */
public class Equalization extends RGBHistogram {
    public Equalization(BmpFile file) throws IOException, BadImageTypeException {
        super(file);
        histogram();
        init();
    }

    @Override
    void init() {
        int redSum = 0, greenSum = 0, blueSum = 0;
        int anzpixel = file.getHeader().getWidth() * file.getHeader().getHeight();
        int[] iarray = new int[3];

        float[] redLut = new float[anzpixel];
        float[] greenLut = new float[anzpixel];
        float[] blueLut = new float[anzpixel];

        for (int i = 0; i < 256; ++i) {
            redSum += redHistogram[i];
            greenSum += greenHistogram[i];
            blueSum += blueHistogram[i];

            redLut[i] = redSum * 255 / anzpixel;
            greenLut[i] = greenSum * 255 / anzpixel;
            blueLut[i] = blueSum * 255 / anzpixel;
        }

        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                int valueBefore = file.getImage().getRed(x, y);
                int valueAfter = (int) redLut[valueBefore];
                iarray[0] = valueAfter;

                valueBefore = file.getImage().getGreen(x, y);
                valueAfter = (int) greenLut[valueBefore];
                iarray[1] = valueAfter;

                valueBefore = file.getImage().getBlue(x, y);
                valueAfter = (int) blueLut[valueBefore];
                iarray[2] = valueAfter;


                file.getImage().setRed(x, y, iarray[0]);
                file.getImage().setGreen(x, y, iarray[1]);
                file.getImage().setBlue(x, y, iarray[2]);
            }
        }
    }
}
