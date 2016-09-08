package com.oldschool.algorithm.grayscale.arytm;

import com.oldschool.image.Pixel;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.bits.Constants;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-06.
 */
abstract public class Operation {

    BmpFile file, secondFile;
    int constant;

    public Operation(BmpFile file, int constant) throws IOException, BadImageTypeException {
        this.constant = constant;

        if (!file.getHeader().getGrayscale())
            this.file = convertToGrayscale(file);
        else
            this.file = file;
    }

    public Operation(BmpFile file, BmpFile secondFile) throws IOException, BadImageTypeException {
        if (!file.getHeader().getGrayscale())
            this.file = convertToGrayscale(file);
        else
            this.file = file;

        if (!secondFile.getHeader().getGrayscale())
            this.secondFile = convertToGrayscale(secondFile);
        else
            this.secondFile = secondFile;
    }

    public Operation(BmpFile file) throws IOException, BadImageTypeException {
        if (!file.getHeader().getGrayscale())
            this.file = convertToGrayscale(file);
        else
            this.file = file;
    }

    private BmpFile convertToGrayscale(BmpFile file) throws IOException, BadImageTypeException {
        if (file.getHeader().getBitsPerPixel() == Constants.BITS_4 || file.getHeader().getBitsPerPixel() == Constants.BITS_8) {
            Pixel[] pixels = file.getImage().getPixels();

            for (int i = 0; i < pixels.length; i++) {
                Pixel pixel = pixels[i];

                int grayscale = (int) (0.3 * pixel.getRed() + 0.6 * pixel.getGreen() + 0.1 * pixel.getBlue());

                pixels[i] = new Pixel(grayscale);
            }
            file.getImage().setPixels(pixels);
            file.setNewIndexColorModel(pixels);

            return file;
        }
        else if (file.getHeader().getBitsPerPixel() == Constants.BITS_24 || file.getHeader().getBitsPerPixel() == Constants.BITS_32) {
            throw new BadImageTypeException("Obrazek jest 24bit lub 32bit!");
        }
        else throw new BadImageTypeException("Zly typ obrazka!");
    }

    protected void run() {
        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                makeAlgorithm(x, y);
            }
        }
    }

    public abstract void makeAlgorithm(int x, int y);

    public BmpFile getFile() {
        return file;
    }
}
