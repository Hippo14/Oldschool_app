package com.oldschool.algorithm.rgb.histogram;

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
            throw new BadImageTypeException("Obrazek nie jest typu RGB!");
        else
            this.file = file;

        histogram();
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

    public void equalization() {
        int redSum = 0, greenSum = 0, blueSum = 0;
        int anzpixel = file.getHeader().getWidth() * file.getHeader().getHeight();
        int[] iarray = new int[3];

        float[] redLut = new float[anzpixel];
        float[] greenLut = new float[anzpixel];
        float[] blueLut = new float[anzpixel];

        for (int i = 0; i < 256; ++i) {
            redSum += redHistogram[i];
            greenSum += greenHistogram[i];
            blueSum += blueHistogram[i];

            redLut[i] = redSum * 255 / anzpixel;
            greenLut[i] = greenSum * 255 / anzpixel;
            blueLut[i] = blueSum * 255 / anzpixel;
        }

        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                int valueBefore = file.getImage().getRed(x, y);
                int valueAfter = (int) redLut[valueBefore];
                iarray[0] = valueAfter;

                valueBefore = file.getImage().getGreen(x, y);
                valueAfter = (int) greenLut[valueBefore];
                iarray[1] = valueAfter;

                valueBefore = file.getImage().getBlue(x, y);
                valueAfter = (int) blueLut[valueBefore];
                iarray[2] = valueAfter;


                file.getImage().setRed(x, y, iarray[0]);
                file.getImage().setGreen(x, y, iarray[1]);
                file.getImage().setBlue(x, y, iarray[2]);
            }
        }
    }

    public void stretching() {
        int r, g, b;

        int minR = 255, maxR = 0;
        int minG = 255, maxG = 0;
        int minB = 255, maxB = 0;

        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                if (file.getImage().getRed(x, y) > maxR)
                    maxR = file.getImage().getRed(x, y);
                if (file.getImage().getRed(x, y) < minR)
                    minR = file.getImage().getRed(x, y);

                if (file.getImage().getGreen(x, y) > maxG)
                    maxG = file.getImage().getGreen(x, y);
                if (file.getImage().getGreen(x, y) < minG)
                    minG = file.getImage().getGreen(x, y);

                if (file.getImage().getBlue(x, y) > maxB)
                    maxB = file.getImage().getBlue(x, y);
                if (file.getImage().getBlue(x, y) < minB)
                    minB = file.getImage().getBlue(x, y);
            }
        }


        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                r = (255 / (maxR - minR)) * (file.getImage().getRed(x, y) - minR);
                g = (255 / (maxG - minG)) * (file.getImage().getGreen(x, y) - minG);
                b = (255 / (maxB - minB)) * (file.getImage().getBlue(x, y) - minB);

                file.getImage().setRed(x, y, r);
                file.getImage().setGreen(x, y, g);
                file.getImage().setBlue(x, y, b);
            }
        }
    }

    public BmpFile getFile() {
        return file;
    }

}