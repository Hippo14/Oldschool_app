package com.oldschool.algorithm.grayscale.arytm;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-08.
 */
public class Log extends Operation {

    public Log(BmpFile file) throws IOException, BadImageTypeException {
        super(file);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        double firstPixel = file.getImage().getRed(x, y);

//        int max = 0;
//        int min = 255;
//
//        for (int i = 0; i < file.getImage().getReds().length; i++) {
//            for (int j = 0; j < file.getImage().getReds()[i].length; j++) {
//                if (file.getImage().getReds()[i][j] > max)
//                    max = file.getImage().getReds()[i][j];
//                if (file.getImage().getReds()[i][j] < min)
//                    min = file.getImage().getReds()[i][j];
//            }
//        }
//
//
//
//        firstPixel = (int) (255 * (Math.log10(firstPixel * 1.0) / Math.log10(max * 1.0)));

        double sum = Math.log10(firstPixel + 1);

        file.getImage().setRed(x, y, (int) (1.0 * sum));
        file.getImage().setGreen(x, y, (int) (1.0 * sum));
        file.getImage().setBlue(x, y, (int) (1.0 * sum));
    }

}
