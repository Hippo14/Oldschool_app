package com.oldschool.algorithm.binary.morphology;

import com.oldschool.algorithm.utils.Config;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

/**
 * Created by MSI on 2016-09-10.
 */
public class Opening {

    BmpFile file;

    public Opening(BmpFile file) throws BadImageTypeException {
        if (!file.getHeader().getBinary())
            throw new BadImageTypeException(Config.get("bit_not_bin"));

        Erosion erosion = new Erosion(file);
        Dilation dilation = new Dilation(erosion.getFile());
        this.file = dilation.getFile();
    }

    public BmpFile getFile() {
        return file;
    }
}
