package com.oldschool.algorithm.grayscale.morphology;

import com.oldschool.algorithm.utils.Convert;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.IOException;

/**
 * Created by MSI on 2016-09-10.
 */
public class Opening {

    BmpFile file;

    public Opening(BmpFile file) throws IOException, BadImageTypeException {
        if (!file.getHeader().getGrayscale())
            this.file = Convert.convertToGrayscale(file);
        else
            this.file = file;

        Erosion erosion = new Erosion(file);
        Dilation dilation = new Dilation(erosion.getFile());
        this.file = dilation.getFile();
    }

    public BmpFile getFile() {
        return file;
    }
}
