package com.oldschool.algorithm.binary.morphology;

import com.oldschool.algorithm.utils.Config;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

/**
 * Created by MSI on 2016-09-10.
 */
public class Closing {

    BmpFile file;

    public Closing(BmpFile file) throws BadImageTypeException {
        if (!file.getHeader().getBinary())
            throw new BadImageTypeException(Config.get("bit_not_bin"));

        Dilation dilation = new Dilation(file);
        Erosion erosion = new Erosion(dilation.getFile());
        this.file = erosion.getFile();
    }

    public BmpFile getFile() {
        return file;
    }
}
