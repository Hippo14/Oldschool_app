package com.oldschool.algorithm.rgb.histogram;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by MSI on 2016-09-10.
 */
public class Stretching extends RGBHistogram {
    public Stretching(BmpFile file) throws IOException, BadImageTypeException {
        super(file);
        histogram();
        init();
    }

    @Override
    void init() {
        int r, g, b;

        int minR = 255, maxR = 0;
        int minG = 255, maxG = 0;
        int minB = 255, maxB = 0;

        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                if (file.getImage().getRed(x, y) > maxR)
                    maxR = file.getImage().getRed(x, y);
                if (file.getImage().getRed(x, y) < minR)
                    minR = file.getImage().getRed(x, y);

                if (file.getImage().getGreen(x, y) > maxG)
                    maxG = file.getImage().getGreen(x, y);
                if (file.getImage().getGreen(x, y) < minG)
                    minG = file.getImage().getGreen(x, y);

                if (file.getImage().getBlue(x, y) > maxB)
                    maxB = file.getImage().getBlue(x, y);
                if (file.getImage().getBlue(x, y) < minB)
                    minB = file.getImage().getBlue(x, y);
            }
        }


        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                r = (255 / (maxR - minR)) * (file.getImage().getRed(x, y) - minR);
                g = (255 / (maxG - minG)) * (file.getImage().getGreen(x, y) - minG);
                b = (255 / (maxB - minB)) * (file.getImage().getBlue(x, y) - minB);

                file.getImage().setRed(x, y, r);
                file.getImage().setGreen(x, y, g);
                file.getImage().setBlue(x, y, b);
            }
        }
    }
}
