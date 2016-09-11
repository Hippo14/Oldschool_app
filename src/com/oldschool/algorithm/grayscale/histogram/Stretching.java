package com.oldschool.algorithm.grayscale.histogram;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by MSI on 2016-09-10.
 */
public class Stretching extends Histogram {
    public Stretching(BmpFile file) throws IOException, BadImageTypeException {
        super(file);

        histogram();

        init();
    }

    @Override
    void init() {
        int g;
        int minG = (int) Math.pow(2, 8) , maxG = 0;

        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                if (file.getImage().getRed(x, y) > maxG)
                    maxG = file.getImage().getRed(x, y);
                if (file.getImage().getRed(x, y)  < minG)
                    minG = file.getImage().getRed(x, y) ;
            }
        }

        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                g = ( 255 / (maxG - minG) ) * (file.getImage().getRed(x, y)  - minG);
                int[] dArray = {g};

                file.getImage().setRed(x, y, dArray[0]);
                file.getImage().setGreen(x, y, dArray[0]);
                file.getImage().setBlue(x, y, dArray[0]);
            }
        }
    }
}
