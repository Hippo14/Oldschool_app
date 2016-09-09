package com.oldschool.algorithm.grayscale.histogram;

import com.oldschool.algorithm.utils.Convert;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-08.
 */
public class Histogram {

    BmpFile file;
    int[] histogram = new int[256];

    public Histogram(BmpFile file) throws IOException, BadImageTypeException {
        if (!file.getHeader().getGrayscale())
            this.file = Convert.convertToGrayscale(file);
        else
            this.file = file;

        histogram();
    }


    public void histogram() {
        int grayscale[][] = file.getImage().getReds();

        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                histogram[grayscale[x][y]]++;
            }
        }
    }

    public void toTextFile(String directory) throws IOException {
        File file = new File(directory + "histogram_gray.txt");

        if (!file.exists())
            file.createNewFile();

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);


//        for (int i = 0; i < histogram.length; i++) {
//            bw.write(i + "|  ");
//            for (int j = 0; j < histogram[i]; j++) {
//                bw.write("-");
//            }
//            bw.write("\n");
//        }
//
//        bw.write("\n");
//        bw.write("\n");
//        bw.write("\n");

        for (int i = 0; i < histogram.length; i++)
            bw.write(i + " |\t" + histogram[i] + "\n");

        bw.close();

        System.out.println("Done");
    }

    public void equalization() {
        int sum = 0;
        int anzpixel = file.getHeader().getWidth() * file.getHeader().getHeight();
        int[] iarray = new int[1];

        float[] lut = new float[anzpixel];
        for (int i = 0; i < histogram.length; ++i) {
            sum += histogram[i];
            lut[i] = sum * 255 / anzpixel;
        }

        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                int valueBefore = file.getImage().getRed(x, y);
                int valueAfter = (int) lut[valueBefore];
                iarray[0] = valueAfter;

                file.getImage().setRed(x, y, iarray[0]);
                file.getImage().setGreen(x, y, iarray[0]);
                file.getImage().setBlue(x, y, iarray[0]);
            }
        }
    }

    public void stretching() {
        int g;
        int minG = (int) Math.pow(2, 8) , maxG = 0;

        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                if (file.getImage().getRed(x, y) > maxG)
                    maxG = file.getImage().getRed(x, y);
                if (file.getImage().getRed(x, y)  < minG)
                    minG = file.getImage().getRed(x, y) ;
            }
        }

        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                g = ( 255 / (maxG - minG) ) * (file.getImage().getRed(x, y)  - minG);
                int[] dArray = {g};

                file.getImage().setRed(x, y, dArray[0]);
                file.getImage().setGreen(x, y, dArray[0]);
                file.getImage().setBlue(x, y, dArray[0]);
            }
        }

    }

    public BmpFile getFile() {
        return file;
    }
}