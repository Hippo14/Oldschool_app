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

    public BmpFile getFile() {
        return file;
    }
}
