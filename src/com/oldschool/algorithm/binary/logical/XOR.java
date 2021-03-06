package com.oldschool.algorithm.binary.logical;

import com.oldschool.image.bitmap.BmpFile;

/**
 * Created by KMacioszek on 2016-09-06.
 */
public class XOR extends Logical {

    public XOR(BmpFile file, BmpFile secondFile) throws Exception {
        super(file, secondFile);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int p = file.getImage().getBit(x, y);
        int q = secondFile.getImage().getBit(x, y);

        int sum = (p & ~q) | (~p & q);

        file.getImage().setBit(sum, x, y);

    }

}
