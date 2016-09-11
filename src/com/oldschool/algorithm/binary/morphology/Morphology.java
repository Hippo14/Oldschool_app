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
        int[][] oldBits = file.getImage().getBits();
        int[][] newBits = new int[file.getHeader().getWidth()][file.getHeader().getWidth()];

        for (int x = 0; x < oldBits.length; x++) {
            for (int y = 0; y < oldBits[x].length; y++) {
                int result = oldBits[x][y];

                for (int i = x - size; i < x + size; i++) {
                    for (int j = y - size; j < y + size; j++) {
                        try {
                            result = makeAlgorithm(i, j, result);
                        } catch (Exception e) {}
                    }
                }

                newBits[x][y] = result;
            }
        }
        file.getImage().setBits(newBits);
    }

//    public abstract int makeAlgorithm(int i, int j, int k, int l, int sum, Integer[][] maskArray);
    public abstract int makeAlgorithm(int i, int j, int sum);

    public BmpFile getFile() {
        return file;
    }
}

