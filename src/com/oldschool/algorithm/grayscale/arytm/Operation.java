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

        run();
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

        run();
    }

    public Operation(BmpFile file) throws IOException, BadImageTypeException {
        if (!file.getHeader().getGrayscale())
            this.file = convertToGrayscale(file);
        else
            this.file = file;

        run();
    }

    private BmpFile convertToGrayscale(BmpFile file) throws IOException, BadImageTypeException {
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
        else throw new BadImageTypeException("Zly typ obrazka!");
    }

    protected void run() {
        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                makeAlgorithm(x, y);
            }
        }

//        for (int y = file.getHeader().getHeight() - 1; y >= 0; y--) {
//            for (int x = 0; x < file.getHeader().getWidth(); x++) {
//                makeAlgorithm(x, y);
//            }
//        }
    }

    public abstract void makeAlgorithm(int x, int y);

    public BmpFile getFile() {
        return file;
    }
}
