package com.oldschool.algorithm.binary.morphology;

import com.oldschool.algorithm.utils.Config;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

/**
 * Created by MSI on 2016-09-10.
 */
public abstract class Morphology {

    BmpFile file;
    private int size = 2;

    public Morphology(BmpFile file) throws BadImageTypeException {
        if (!file.getHeader().getBinary())
            throw new BadImageTypeException(Config.get("bit_not_bin"));

        this.file = file;

        run();
    }

    public void run() {
        for (int x = 0; x < file.getHeader().getHeight(); x++) {
            for (int y = 0; y < file.getHeader().getWidth(); y++) {
                try {
                    MaskArray mask = new MaskArray(2 * size + 1);
                    Integer[][] maskArray = mask.getMaskArray();
                    int sum = file.getImage().getBit(x, y);
                    int k, l;

                    for (int i = x - size; i < x + size; i++) {
                        k = 0;
                        l = 0;
                        for (int j = y - size; j < y + size; j++) {
                            if ((i >= 0 && j >= 0) && (i < file.getHeader().getHeight() && j < file.getHeader().getWidth())) {
                                try {
                                    sum = makeAlgorithm(i, j, k, l, sum, maskArray);
                                } catch (Exception e) {

                                }
                                l++;
                            }
                        }
                        k++;
                    }

                    file.getImage().setBit(sum, x, y);
                } catch (Exception e) {

                }
            }
        }
    }

    public abstract int makeAlgorithm(int i, int j, int k, int l, int sum, Integer[][] maskArray);

    public BmpFile getFile() {
        return file;
    }
}

