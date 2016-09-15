package com.oldschool.algorithm.utils;

import com.oldschool.algorithm.rgb.histogram.Otsu;
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

            file.getHeader().setRGB(false);
            file.getHeader().setBinary(false);
            file.getHeader().setGrayscale(true);

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


    // arg0 - small Image
    // arg1 - big image
    // returns - resize smallImage to bigImage
    public static BmpFile converseSize(BmpFile smallImage, BmpFile bigImage) {
        BmpFile newSmallImage = smallImage;

        System.out.println(Config.get("convertSize"));

        if (newSmallImage.getHeader().getWidth() < bigImage.getHeader().getWidth()) {
            if (newSmallImage.getHeader().getBitsPerPixel() == Constants.BITS_1) {
                int[][] newBits = new int[bigImage.getHeader().getWidth()][bigImage.getHeader().getHeight()];

                for (int x = 0; x < bigImage.getHeader().getWidth(); x++) {
                    for (int y = 0; y < bigImage.getHeader().getHeight(); y++) {
                        if (x < newSmallImage.getHeader().getWidth() && y < newSmallImage.getHeader().getWidth())
                            newBits[x][y] = newSmallImage.getImage().getBit(x, y);
                        else
                            newBits[x][y] = 0;
                    }
                }
                smallImage.getImage().setBits(newBits);
            }
            else if (smallImage.getHeader().getBitsPerPixel() == Constants.BITS_24) {
                int[][] newReds = new int[bigImage.getHeader().getWidth()][bigImage.getHeader().getHeight()];
                int[][] newGreens = new int[bigImage.getHeader().getWidth()][bigImage.getHeader().getHeight()];
                int[][] newBlues = new int[bigImage.getHeader().getWidth()][bigImage.getHeader().getHeight()];

                for (int x = 0; x < bigImage.getHeader().getWidth(); x++) {
                    for (int y = 0; y < bigImage.getHeader().getHeight(); y++) {
                        if (x < newSmallImage.getHeader().getWidth() && y < newSmallImage.getHeader().getWidth()) {
                            newReds[x][y] = newSmallImage.getImage().getRed(x, y);
                            newGreens[x][y] = newSmallImage.getImage().getGreen(x, y);
                            newBlues[x][y] = newSmallImage.getImage().getBlue(x, y);
                        }
                        else {
                            newReds[x][y] = 0;
                            newGreens[x][y] = 0;
                            newBlues[x][y] = 0;
                        }
                    }
                }
                newSmallImage.getImage().setReds(newReds);
                newSmallImage.getImage().setGreens(newGreens);
                newSmallImage.getImage().setBlues(newBlues);
            }
        }

        newSmallImage.getHeader().setWidth(bigImage.getHeader().getWidth());
        newSmallImage.getHeader().setHeight(bigImage.getHeader().getHeight());

        return newSmallImage;
    }

    public static BmpFile convertRGBToBin(BmpFile file) throws IOException, BadImageTypeException {
        System.out.println(Config.get("convertToBin"));

        if (file.getHeader().getBitsPerPixel() == Constants.BITS_24) {
            Otsu otsu = new Otsu(file);
            file = otsu.getFile();

            return file;
        }
        else throw new BadImageTypeException(Config.get("bit_bad_image"));
    }
}
