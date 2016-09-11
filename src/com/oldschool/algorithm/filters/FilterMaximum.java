package com.oldschool.algorithm.filters;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

/**
 * Created by MSI on 2016-09-11.
 */
public class FilterMaximum extends Filter{

    int maskSize = 3;

    public FilterMaximum(BmpFile file) throws BadImageTypeException {
        super(file);

        init();
    }

    public void init() {
        int minimumRedPixel, minimumGreenPixel, minimumBluePixel;

        int[][] reds = new int[file.getHeader().getWidth()][file.getHeader().getHeight()];
        int[][] greens = new int[file.getHeader().getWidth()][file.getHeader().getHeight()];
        int[][] blues = new int[file.getHeader().getWidth()][file.getHeader().getHeight()];

        for (int x = maskSize; x < file.getHeader().getHeight() - maskSize; x++) {
            for (int y = maskSize; y < file.getHeader().getWidth() - maskSize; y++) {
                minimumRedPixel = file.getImage().getRed(x, y);
                minimumGreenPixel = file.getImage().getGreen(x, y);
                minimumBluePixel = file.getImage().getBlue(x, y);

                for (int i = x - (maskSize / 2); i <= x + (maskSize / 2); i++) {
                    for (int j = y - (maskSize / 2); j <= y + (maskSize / 2); j++) {
                        if (minimumRedPixel < file.getImage().getRed(i, j))
                            minimumRedPixel = file.getImage().getRed(i, j);
                        if (minimumGreenPixel < file.getImage().getGreen(i, j))
                            minimumGreenPixel = file.getImage().getGreen(i, j);
                        if (minimumBluePixel < file.getImage().getBlue(i, j))
                            minimumBluePixel = file.getImage().getBlue(i, j);
                    }
                }

                reds[x][y] = minimumRedPixel;
                greens[x][y] = minimumGreenPixel;
                blues[x][y] = minimumBluePixel;
            }
        }

        file.getImage().setReds(reds);
        file.getImage().setGreens(greens);
        file.getImage().setBlues(blues);
    }

}
