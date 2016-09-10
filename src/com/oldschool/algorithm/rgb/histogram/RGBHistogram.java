package com.oldschool.algorithm.rgb.histogram;

import com.oldschool.algorithm.utils.Config;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by KMacioszek on 2016-09-09.
 */
public class RGBHistogram {

    BmpFile file;
    int[] redHistogram = new int[256];
    int[] greenHistogram = new int[256];
    int[] blueHistogram = new int[256];

    public RGBHistogram(BmpFile file) throws IOException, BadImageTypeException {
        if (!file.getHeader().getRGB())
            throw new BadImageTypeException(Config.get("bit_not_rgb"));
        else
            this.file = file;
    }

    void init() {

    }

    public void histogram() {
        int reds[][] = file.getImage().getReds();
        int greens[][] = file.getImage().getGreens();
        int blues[][] = file.getImage().getBlues();

        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                redHistogram[reds[x][y]]++;
                greenHistogram[greens[x][y]]++;
                blueHistogram[blues[x][y]]++;
            }
        }
    }

    public void toTextFile(String directory) throws IOException {
        File file = new File(directory + "histogram_rgb.txt");

        if (!file.exists())
            file.createNewFile();

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        writeHistogram(redHistogram, bw, "red histogram");
        writeHistogram(greenHistogram, bw, "green histogram");
        writeHistogram(blueHistogram, bw, "blue histogram");

        bw.close();

        System.out.println("Done");
    }

    private void writeHistogram(int[] histogram, BufferedWriter bw, String name) throws IOException {
        bw.write(name + "\n");
        for (int i = 0; i < histogram.length; i++)
            bw.write(i + " |\t" + histogram[i] + "\n");
        bw.write("\n");
    }

    public BmpFile getFile() {
        return file;
    }

}