package com.oldschool.algorithm.binary.logical;

import com.oldschool.image.bitmap.BmpFile;

/**
 * Created by KMacioszek on 2016-09-06.
 */
public class Product extends Logical {

    public Product(BmpFile file, BmpFile secondFile) throws Exception {
        super(file, secondFile);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int sum = file.getImage().getBit(x, y) & secondFile.getImage().getBit(x, y);

        if (sum == 0)
            file.getImage().setBit(1, x, y);
        else
            file.getImage().setBit(0, x, y);
    }

}
