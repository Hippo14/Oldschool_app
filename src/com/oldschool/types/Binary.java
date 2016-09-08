package com.oldschool.types;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

/**
 * Created by KMacioszek on 2016-09-08.
 */
public class Binary {

    BmpFile firstFile, secondFile;

    public Binary (BmpFile firstFile, BmpFile secondFile, int option) throws Exception {
        if (!firstFile.getHeader().getBinary())
            throw new BadImageTypeException("Pierwszy obrazek nie jest binarny!");
        if (!secondFile.getHeader().getBinary())
            throw new BadImageTypeException("Drugi obrazek nie jest binarny!");
    }

}
