package com.oldschool.algorithm.rgb.histogram;

import com.oldschool.algorithm.grayscale.histogram.Histogram;
import com.oldschool.algorithm.utils.Convert;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by MSI on 2016-09-10.
 */
public class Otsu extends RGBHistogram {

    public Otsu(BmpFile file) throws IOException, BadImageTypeException {
        super(file);

        histogram();

        init();
    }

    @Override
    void init() throws IOException, BadImageTypeException {
        this.file = binarize(file);
    }

    private BmpFile binarize(BmpFile file) throws IOException, BadImageTypeException {
        int bin;
        int newPixel;

        int threshold = otsuThreshold(file);

        file = Convert.convertToBinare(file);
        file.getImage().createBit();

        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                bin = file.getImage().getRed(x, y);

                if (bin > threshold)
                    newPixel = 1;
                else
                    newPixel = 0;

                file.getImage().setBit(newPixel, x, y);
            }
        }

        return file;
    }

    private int otsuThreshold(BmpFile file) throws IOException, BadImageTypeException {
        Histogram grayHistogram = new Histogram(file);
        grayHistogram.histogram();
        int[] histogram = grayHistogram.getHistogram();
        int total = file.getHeader().getWidth() * file.getHeader().getHeight();

        float sum = 0;
        for (int i = 0; i < 256; i++)
            sum += i * histogram[i];

        float sumB = 0;
        int wB = 0;
        int wF = 0;

        float varMax = 0;
        int threshold = 0;

        for (int i = 0; i < 256; i++) {
            wB += histogram[i];
            if (wB == 0)
                continue;
            wF = total - wB;

            if (wF == 0)
                break;

            sumB += (float) (i * histogram[i]);
            float mB = sumB / wB;
            float mF = (sum - sumB) / wF;

            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = i;
            }
        }
        return threshold;
    }

}
