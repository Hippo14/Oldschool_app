package com.oldschool.algorithm.grayscale.arytm;

import com.oldschool.algorithm.utils.Config;
import com.oldschool.algorithm.utils.Convert;
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

        if (file.getHeader().getRGB())
            this.file = Convert.convertToGrayscale(file);
        else if (file.getHeader().getGrayscale())
            this.file = file;
        else
            throw new BadImageTypeException(Config.get("bit_bad_image"));

        run();
    }

    public Operation(BmpFile file, BmpFile secondFile) throws IOException, BadImageTypeException {
        if (file.getHeader().getRGB())
            this.file = Convert.convertToGrayscale(file);
        else if (file.getHeader().getGrayscale())
            this.file = file;
        else
            throw new BadImageTypeException(Config.get("bit_bad_image"));

        if (secondFile.getHeader().getRGB())
            this.secondFile = Convert.convertToGrayscale(secondFile);
        else if (secondFile.getHeader().getGrayscale())
            this.secondFile = secondFile;
        else
            throw new BadImageTypeException(Config.get("bit_bad_image"));

        run();
    }

    public Operation(BmpFile file) throws IOException, BadImageTypeException {
        if (file.getHeader().getRGB())
            this.file = Convert.convertToGrayscale(file);
        else if (file.getHeader().getGrayscale())
            this.file = file;
        else
            throw new BadImageTypeException(Config.get("bit_bad_image"));

        run();
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
