package com.oldschool.algorithm.filters;

import com.oldschool.algorithm.utils.Config;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

/**
 * Created by MSI on 2016-09-10.
 */
public abstract class Filter {

     BmpFile file;
    protected int maskSize;
    protected Integer[][] arrayMask;

    public Filter(BmpFile file) throws BadImageTypeException {
        if (file.getHeader().getBinary())
            throw new BadImageTypeException(Config.get("bit_bad_image"));
        this.file = file;
    }

    protected void run() {

        int sR, sG, sB;
        int wR, wG, wB;
        int k, l;

        int[][] reds = new int[file.getHeader().getWidth()][file.getHeader().getHeight()];
        int[][] greens = new int[file.getHeader().getWidth()][file.getHeader().getHeight()];
        int[][] blues = new int[file.getHeader().getWidth()][file.getHeader().getHeight()];

        for (int x = maskSize; x < file.getHeader().getWidth() - maskSize; x++) {
            for (int y = maskSize; y < file.getHeader().getHeight() - maskSize; y++) {
                // Inicjalizacja zmiennych
                sR = 0;
                sG = 0;
                sB = 0;

                k = 0;
                // Oblicz sumy ważone dla każdej składowej piksela (Red, Blue, Green)
                for (int i = x - (maskSize / 2); i <= x + (maskSize / 2); i++) {
                    l = 0;
                    for (int j = y - (maskSize / 2); j <= y + (maskSize / 2); j++) {
                        try {
                            sR = sR + (file.getImage().getRed(i, j) * arrayMask[k][l]);
                            sG = sG + (file.getImage().getRed(i, j) * arrayMask[k][l]);
                            sB = sB + (file.getImage().getRed(i, j) * arrayMask[k][l]);
                        } catch (Exception e) { }
                        l++;
                    }
                    k++;
                }

                // Dzielimy przez sumę wszystkich wag maski (jeżeli jest różna od 0)
                int weightSum = getWeightMaskSum(arrayMask);
                if (weightSum == 0)
                    weightSum = Math.abs(weightSum);

                if (weightSum != 0) {
                    wR = sR / weightSum;
                    wG = sG / weightSum;
                    wB = sB / weightSum;
                } else {
                    wR = sR;
                    wG = sG;
                    wB = sB;
                }

                reds[x][y] = wR;
                greens[x][y] = wG;
                blues[x][y] = wB;
            }
        }
        file.getImage().setReds(reds);
        file.getImage().setGreens(greens);
        file.getImage().setBlues(blues);
    }

    public int getWeightMaskSum(Integer[][] maskArray) {
        int sum = 0;
        for (Integer[] list : maskArray) {
            for (Integer number : list) {
                sum += number;
            }
        }
        return sum;
    }

    public BmpFile getFile() {
        return file;
    }
}
