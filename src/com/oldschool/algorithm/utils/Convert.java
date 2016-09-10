package com.oldschool.algorithm.utils;

import com.oldschool.image.Pixel;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.bits.Constants;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-09.
 */
public class Convert {

    public static BmpFile convertToGrayscale(BmpFile file) throws IOException, BadImageTypeException {
        System.out.println(Config.get("convertToGrayscale"));

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
        else throw new BadImageTypeException(Config.get("bit_bad_image"));
    }

    public static BmpFile convertToBinare(BmpFile file) throws BadImageTypeException {
        System.out.println(Config.get("convertToBin"));

        if (file.getHeader().getBitsPerPixel() == Constants.BITS_24) {
            file.getHeader().setRGB(false);
            file.getHeader().setGrayscale(false);
            file.getHeader().setBinary(true);

            file.getHeader().setBitsPerPixel((short) 1);

            // Create pixel table
            Pixel[] pixels = new Pixel[2];
            pixels[0] = new Pixel(0, 0, 0);
            pixels[1] = new Pixel(255, 255, 255);
            file.getImage().setPixels(pixels);

            return file;
        }
        else throw new BadImageTypeException(Config.get("bit_bad_image"));
    }
}
