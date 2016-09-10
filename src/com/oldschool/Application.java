package com.oldschool;

import com.oldschool.algorithm.binary.logical.*;
import com.oldschool.algorithm.grayscale.arytm.Divide;
import com.oldschool.algorithm.grayscale.arytm.DivideConst;
import com.oldschool.algorithm.grayscale.arytm.Exponentation;
import com.oldschool.algorithm.grayscale.arytm.Log;
import com.oldschool.algorithm.grayscale.arytm.Multiplication;
import com.oldschool.algorithm.grayscale.arytm.MultiplicationConst;
import com.oldschool.algorithm.grayscale.arytm.Operation;
import com.oldschool.algorithm.grayscale.arytm.Roots;
import com.oldschool.algorithm.grayscale.arytm.SumConst;
import com.oldschool.algorithm.grayscale.histogram.Equalization;
import com.oldschool.algorithm.grayscale.histogram.Histogram;
import com.oldschool.algorithm.grayscale.histogram.Stretching;
import com.oldschool.algorithm.normalize.Normalize;
import com.oldschool.algorithm.rgb.histogram.RGBHistogram;
import com.oldschool.algorithm.rgb.histogram.Threeshold;
import com.oldschool.algorithm.utils.Config;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageSizeException;
import com.oldschool.image.bitmap.exception.BadImageTypeException;
import com.oldschool.image.bitmap.exception.UnknownFormatException;
import com.oldschool.image.bitmap.read.Read;
import com.oldschool.image.bitmap.write.Write;

import java.io.*;
import java.util.Scanner;

/**
 * Created by KMacioszek on 2016-08-29.
 */
public class Application {

    BmpFile file, secondFile;
    String sDir;

    Scanner input= new Scanner(System.in);
    int option;
    private final String ANSI_CLS = "\u001b[2J";
    private int constant;

    Class clazz;

    public Application() {

    }

    public void initialize() throws Exception {

//        http://stackoverflow.com/questions/1448858/how-to-color-system-out-println-output
//        System.out.println((char)27 + "[31mThis text would show up red" + (char)27 + "[0m");

        // Init config.properties
        Config.init();

        // Get Bmp file
        file = getBmpFile();

        // Choose option
        chooseOption();

        // Write Bmp file
        writeBmp("_" + getName());

        // Normalize
        normalize();

        // Write Bmp file
        writeBmp("_normalize" + "_" + getName());
    }

    private void normalize() {
        Normalize normalize = new Normalize(file);
        file = normalize.getFile();
    }

    private void writeBmp(String string) throws Exception {
        File file = new File(sDir);

        String fileName = "output.bmp";
        int lastDot = fileName.lastIndexOf('.');
        String newFileName = fileName.substring(0, lastDot) + string + fileName.substring(lastDot);

        String newPath = file.getParent() + "\\" + newFileName;

        file = new File(newPath);
        Write write = new Write(this.file, file);
        System.out.println(Config.get("pathToNewFile") + newPath);
    }

    private void chooseOption() throws Exception {
        System.out.println("....");
        System.out.println(Config.get("menu1"));
        System.out.println(Config.get("menu2"));
        System.out.println(Config.get("menu3"));
        System.out.println(Config.get("menu4"));
        System.out.println(Config.get("menu5"));
        System.out.println(Config.get("menu6"));
        System.out.println(Config.get("menu7"));
        System.out.println(Config.get("menu8"));
        System.out.println(Config.get("menu9"));
        System.out.print(Config.get("menuWybierzOpcje"));

        option = input.nextInt();

        System.out.println(ANSI_CLS);

        switch (option) {
            case 1:
                logicBinary();
            break;
            case 2:
                arytmGrayscale();
            break;
            case 3:
                arytmRGB();
            break;
            case 4:
                geometr();
            break;
            case 5:
                histogramGrayscale();
            break;
            case 6:
                histogramRGB();
            break;
            case 7:
                morfBinary();
            break;
            case 8:
                morfGrayscale();
            break;
            case 9:
                filter();
            break;
        }
    }

    private void filter() {

    }

    private void morfGrayscale() {

    }

    private void morfBinary() {

    }

    private void histogramRGB() throws IOException, BadImageTypeException, InterruptedException {
        System.out.println(Config.get("menu51"));
        System.out.println(Config.get("menu52"));
        System.out.println(Config.get("menu53"));
        System.out.println(Config.get("menu64"));
        System.out.println(Config.get("menu65"));
        System.out.print(Config.get("menuWybierzOpcje"));
        option = input.nextInt();

        RGBHistogram histogram = new RGBHistogram(file);
        File pathfile = new File(sDir);
        String path = pathfile.getParent() + "\\";

        switch (option) {
            case 1:
                file = histogram.getFile();
                break;
            case 2:
                histogram = new com.oldschool.algorithm.rgb.histogram.Equalization(file);
                file = histogram.getFile();
                break;
            case 3:
                histogram = new com.oldschool.algorithm.rgb.histogram.Stretching(file);
                file = histogram.getFile();
                break;
            case 4:
                constant = getConstant();
                histogram = new Threeshold(file, constant);
                file = histogram.getFile();
            break;
            case 5:

            break;
        }
        histogram.toTextFile(path);
        clazz = histogram.getClass();
    }

    private void histogramGrayscale() throws IOException, BadImageTypeException {
        System.out.println(Config.get("menu51"));
        System.out.println(Config.get("menu52"));
        System.out.println(Config.get("menu53"));
        System.out.print(Config.get("menuWybierzOpcje"));
        option = input.nextInt();

        Histogram histogram = new Histogram(file);
        File pathfile = new File(sDir);
        String path = pathfile.getParent() + "\\";

        switch (option) {
            case 1:
                histogram.histogram();
                file = histogram.getFile();
            break;
            case 2:
                histogram = new Equalization(file);
                file = histogram.getFile();
            break;
            case 3:
                histogram = new Stretching(file);
                file = histogram.getFile();
            break;
        }
        histogram.toTextFile(path);
        clazz = histogram.getClass();
    }

    private void geometr() {

    }

    private void arytmRGB() throws BadImageSizeException, UnknownFormatException, BadImageTypeException, IOException, InterruptedException {
        System.out.println(Config.get("menu21"));
        System.out.println(Config.get("menu22"));
        System.out.println(Config.get("menu23"));
        System.out.println(Config.get("menu24"));
        System.out.println(Config.get("menu25"));
        System.out.println(Config.get("menu26"));
        System.out.println(Config.get("menu27"));
        System.out.println(Config.get("menu28"));
        System.out.println(Config.get("menu29"));
        System.out.print(Config.get("menuWybierzOpcje"));
        option = input.nextInt();

        com.oldschool.algorithm.rgb.arytm.Operation operation = null;

        switch (option) {
            case 1:
                secondFile = getSecondBmpFile();
                operation = new com.oldschool.algorithm.rgb.arytm.Sum(file, secondFile);
                file = operation.getFile();
                break;
            case 2:
                constant = getConstant();
                operation = new com.oldschool.algorithm.rgb.arytm.SumConst(file, constant);
                file = operation.getFile();
                break;
            case 3:
                secondFile = getSecondBmpFile();
                operation = new com.oldschool.algorithm.rgb.arytm.Multiplication(file, secondFile);
                file = operation.getFile();
                break;
            case 4:
                constant = getConstant();
                operation = new com.oldschool.algorithm.rgb.arytm.MultiplicationConst(file, constant);
                file = operation.getFile();
                break;
            case 5:
                constant = getConstant();
                operation = new com.oldschool.algorithm.rgb.arytm.Exponentation(file, constant);
                file = operation.getFile();
                break;
            case 6:
                secondFile = getSecondBmpFile();
                operation = new com.oldschool.algorithm.rgb.arytm.Divide(file, secondFile);
                file = operation.getFile();
                break;
            case 7:
                constant = getConstant();
                operation = new com.oldschool.algorithm.rgb.arytm.DivideConst(file, constant);
                file = operation.getFile();
                break;
            case 8:
                operation = new com.oldschool.algorithm.rgb.arytm.Roots(file);
                file = operation.getFile();
                break;
            case 9:
                operation = new com.oldschool.algorithm.rgb.arytm.Log(file);
                file = operation.getFile();
            break;
        }
        clazz = operation.getClass();
    }

    private void arytmGrayscale() throws BadImageSizeException, UnknownFormatException, BadImageTypeException, IOException, InterruptedException {
        System.out.println(Config.get("menu21"));
        System.out.println(Config.get("menu22"));
        System.out.println(Config.get("menu23"));
        System.out.println(Config.get("menu24"));
        System.out.println(Config.get("menu25"));
        System.out.println(Config.get("menu26"));
        System.out.println(Config.get("menu27"));
        System.out.println(Config.get("menu28"));
        System.out.println(Config.get("menu29"));
        System.out.print(Config.get("menuWybierzOpcje"));
        option = input.nextInt();

        Operation operation = null;

        switch (option) {
            case 1:
                secondFile = getSecondBmpFile();
                operation = new com.oldschool.algorithm.grayscale.arytm.Sum(file, secondFile);
                file = operation.getFile();
            break;
            case 2:
                constant = getConstant();
                operation = new SumConst(file, constant);
                file = operation.getFile();
            break;
            case 3:
                secondFile = getSecondBmpFile();
                operation = new Multiplication(file, secondFile);
                file = operation.getFile();
            break;
            case 4:
                constant = getConstant();
                operation = new MultiplicationConst(file, constant);
                file = operation.getFile();
            break;
            case 5:
                constant = getConstant();
                operation = new Exponentation(file, constant);
                file = operation.getFile();
            break;
            case 6:
                secondFile = getSecondBmpFile();
                operation = new Divide(file, secondFile);
                file = operation.getFile();
            break;
            case 7:
                constant = getConstant();
                operation = new DivideConst(file, constant);
                file = operation.getFile();
            break;
            case 8:
                operation = new Roots(file);
                file = operation.getFile();
            break;
            case 9:
                operation = new Log(file);
                file = operation.getFile();
            break;
        }
        clazz = operation.getClass();
    }

    private void logicBinary() throws Exception {
        System.out.println(Config.get("menu11"));
        System.out.println(Config.get("menu12"));
        System.out.println(Config.get("menu13"));
        System.out.println(Config.get("menu14"));
        System.out.print(Config.get("menuWybierzOpcje"));
        option = input.nextInt();

        Logical logical = null;

        switch (option) {
            case 1:
                logical = new Negation(file);
                file = logical.getFile();
            break;
            case 2:
                secondFile = getSecondBmpFile();
                logical = new com.oldschool.algorithm.binary.logical.Sum(file, secondFile);
                file = logical.getFile();
            break;
            case 3:
                secondFile = getSecondBmpFile();
                logical = new Product(file, secondFile);
                file = logical.getFile();
            break;
            case 4:
                secondFile = getSecondBmpFile();
                logical = new XOR(file, secondFile);
                file = logical.getFile();
            break;
        }
        clazz = logical.getClass();
    }

    private File getFileFromPath() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(Config.get("getFileFromPath"));

        sDir = input.readLine();

        File f = new File(sDir);
        while (!f.exists()) {
            System.out.println(Config.get("fileNotExists"));
            System.out.println(Config.get("getFileFromPath"));
            sDir = input.readLine();
            f = new File(sDir);
        }

        return f;
    }

    private BmpFile getBmpFile() throws IOException, UnknownFormatException, InterruptedException {
        File file = getFileFromPath();
        sDir = file.toURI().getPath();

        DataInputStream dis = new DataInputStream(new FileInputStream(sDir));
        Read read = new Read(dis);

        return read.getBmpFile();
    }

    public BmpFile getSecondBmpFile() throws IOException, UnknownFormatException, BadImageTypeException, BadImageSizeException, InterruptedException {
        System.out.println(Config.get("pathToSecondFile"));

        BmpFile secondFile = getBmpFile();

        if (file.getHeader().getBitsPerPixel() != secondFile.getHeader().getBitsPerPixel())
            throw new BadImageTypeException(Config.get("bit_exception_bin"));
        if (file.getHeader().getWidth() != secondFile.getHeader().getWidth() ||
                file.getHeader().getHeight() != secondFile.getHeader().getHeight())
            throw new BadImageSizeException(Config.get("bis_exception"));

        return secondFile;
    }

    public int getConstant() {
        System.out.println(Config.get("constant"));
        return input.nextInt();
    }

    public String getName() {
        String string = clazz.getName();
        return string.replace("com.oldschool.algorithm", "").replace(".", "_");
    }
}