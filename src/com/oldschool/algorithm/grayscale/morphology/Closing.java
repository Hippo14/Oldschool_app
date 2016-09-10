package com.oldschool.algorithm.grayscale.morphology;

import com.oldschool.algorithm.utils.Convert;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by MSI on 2016-09-10.
 */
public class Closing {

    BmpFile file;

    public Closing(BmpFile file) throws IOException, BadImageTypeException {
        if (!file.getHeader().getGrayscale())
            this.file = Convert.convertToGrayscale(file);
        else
            this.file = file;

        Dilation dilation = new Dilation(file);
        Erosion erosion = new Erosion(dilation.getFile());
        this.file = erosion.getFile();
    }

    public BmpFile getFile() {
        return file;
    }
}
