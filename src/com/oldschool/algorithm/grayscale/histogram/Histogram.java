package com.oldschool.algorithm.grayscale.histogram;

import com.oldschool.image.bitmap.BmpFile;

/**
 * Created by KMacioszek on 2016-09-08.
 */
public class Histogram {

    BmpFile file;

    public Histogram(BmpFile file) {
        this.file = file;
    }


    public void histogram(BufferedImage bufferedImage) {
        Raster raster = bufferedImage.getRaster();

        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                histogram[raster.getSample(x, y, 0)]++;
            }
        }

        seriesGray = new XYChart.Series();
        seriesGray.setName("gray");

        for (int i = 0; i < histogram.length; i++)
            seriesGray.getData().add(new XYChart.Data(String.valueOf(i), histogram[i]));
    }

}
