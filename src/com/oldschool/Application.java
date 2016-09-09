package com.oldschool;

import com.oldschool.algorithm.binary.logical.Negation;
import com.oldschool.algorithm.binary.logical.Product;
import com.oldschool.algorithm.binary.logical.Sum;
import com.oldschool.algorithm.binary.logical.XOR;
import com.oldschool.algorithm.grayscale.arytm.SumConst;
import com.oldschool.algorithm.grayscale.histogram.Histogram;
import com.oldschool.algorithm.normalize.Normalize;
import com.oldschool.algorithm.rgb.histogram.RGBHistogram;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageSizeException;
import com.oldschool.image.bitmap.exception.BadImageTypeException;
import com.oldschool.image.bitmap.exception.UnknownFormatException;
import com.oldschool.image.bitmap.read.Read;
import com.oldschool.image.bitmap.write.Write;
import com.oldschool.menu.MenuBinary;
import com.oldschool.menu.MenuGrayscale;

import java.io.*;
import java.util.Scanner;

/**
 * Created by KMacioszek on 2016-08-29.
 */
public class Application {

    BmpFile file;
    String sDir;
    private BmpFile secondBmpFile;

    public Application() {

    }

    public void initialize() throws Exception {

//        http://stackoverflow.com/questions/1448858/how-to-color-system-out-println-output
//        System.out.println((char)27 + "[31mThis text would show up red" + (char)27 + "[0m");



        // Get Bmp file
        file = getBmpFile();

        // Choose option
        chooseOption();

        // Write Bmp file
        writeBmp("_before");

        // Normalize
        normalize();

        // Write Bmp file
        writeBmp("_normalize");
    }

    private void normalize() {
        Normalize normalize = new Normalize(file);
        file = normalize.getFile();
    }

    private void writeBmp(String string) throws Exception {
        File file = new File(sDir);

//        String fileName = file.getName();
        String fileName = "output.bmp";
        int lastDot = fileName.lastIndexOf('.');
        String newFileName = fileName.substring(0, lastDot) + string + fileName.substring(lastDot);

        String newPath = file.getParent() + "\\" + newFileName;

        file = new File(newPath);
        Write write = new Write(this.file, file);
        System.out.println("Koniec\n" + newPath);
    }

    private void chooseOption() throws Exception {
        System.out.println("....");
        System.out.println("Wybierz opcje:");
        System.out.println("1. Operacje logiczne na obrazach binarnych");
        System.out.println("2. Operacje sumowania arytmetycznego obrazów szarych");
        System.out.println("3. Operacje sumowania arytmetycznego obrazów barwowych");
        System.out.println("4. Operacje geometryczne na obrazie");
        System.out.println("5. Operacje na histogramie obrazu szarego");
        System.out.println("6. Operacje na histogramie obrazu barwowego");
        System.out.println("7. Operacje morfologiczne na obrazach binarnych");
        System.out.println("8. Operacje morfologiczne na obrazach szarych");
        System.out.println("9. Filtrowanie liniowe i nieliniowe");

        Scanner input= new Scanner(System.in);
        int option = input.nextInt();

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

    private void histogramRGB() {

    }

    private void histogramGrayscale() {

    }

    private void geometr() {

    }

    private void arytmRGB() {

    }

    private void arytmGrayscale() {
    }

    private void logicBinary() {
        Scanner input= new Scanner(System.in);
        int option = input.nextInt();

        System.out.println("1. Negacja obrazu");

        switch (option) {
            case 1:

            break:
        }
    }

    private void rgbHistogram() throws IOException, BadImageTypeException {
        RGBHistogram rgbHistogram = new RGBHistogram(file);
        File file = new File(sDir);
        String histogramDir =file.getParent() + "\\";
        rgbHistogram.equalization();
        rgbHistogram.toTextFile(histogramDir);

        this.file = rgbHistogram.getFile();
    }

    private void grayHistogram() throws IOException, BadImageTypeException {
        Histogram histogram = new Histogram(file);
        File file = new File(sDir);
        String histogramDir =file.getParent() + "\\";
        histogram.toTextFile(histogramDir);

        this.file = histogram.getFile();
    }

    private void grayHistogramEqua() throws IOException, BadImageTypeException {
        Histogram histogram = new Histogram(file);
        File file = new File(sDir);
        String histogramDir =file.getParent() + "\\";
        histogram.equalization();
        histogram.toTextFile(histogramDir);

        this.file = histogram.getFile();
    }

    private void grayHistogramStretch() throws IOException, BadImageTypeException {
        Histogram histogram = new Histogram(file);
        File file = new File(sDir);
        String histogramDir =file.getParent() + "\\";
        histogram.stretching();
        histogram.toTextFile(histogramDir);

        this.file = histogram.getFile();
    }

    private void rgbSum() throws BadImageSizeException, UnknownFormatException, BadImageTypeException, IOException {
        BmpFile secondFile = getSecondBmpFile();

        com.oldschool.algorithm.rgb.arytm.Sum sum = new com.oldschool.algorithm.rgb.arytm.Sum(file, secondFile);
        file = sum.getFile();
    }

    private void graySumConst() throws IOException, BadImageTypeException {
        System.out.println("Podaj stala: ");
        Scanner input= new Scanner(System.in);
        int constant = input.nextInt();

        SumConst sumConst = new SumConst(file, constant);
        file = sumConst.getFile();
    }

    private void graySum() throws IOException, UnknownFormatException, BadImageTypeException, BadImageSizeException {
        BmpFile secondFile = getSecondBmpFile();

        com.oldschool.algorithm.grayscale.arytm.Sum sum = new com.oldschool.algorithm.grayscale.arytm.Sum(file, secondFile);
        file = sum.getFile();
    }

    private void logicXOR() throws Exception {
        BmpFile secondFile = getSecondBmpFile();

        XOR xor = new XOR(file, secondFile);
        file = xor.getFile();
    }

    private void logicProduct() throws Exception {
        BmpFile secondFile = getSecondBmpFile();

        Product product = new Product(file, secondFile);
        file = product.getFile();
    }

    private void logicSum() throws Exception {
        BmpFile secondFile = getSecondBmpFile();

        Sum sum = new Sum(file, secondFile);
        file = sum.getFile();
    }

    private void logicNegation() throws BadImageTypeException {
        Negation negation = new Negation(file);
        file = negation.getFile();
    }

    private File getFileFromPath() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Sciezka pliku:");

        sDir = input.readLine();

        File f = new File(sDir);
        while (!f.exists()) {
            System.out.println("Plik nie istnieje");
            System.out.println("Sciezka pliku:");
            sDir = input.readLine();
            f = new File(sDir);
        }

        return f;
    }

    private BmpFile getBmpFile() throws IOException, UnknownFormatException {
        File file = getFileFromPath();
        sDir = file.toURI().getPath();

        DataInputStream dis = new DataInputStream(new FileInputStream(sDir));
        Read read = new Read(dis);

        return read.getBmpFile();
    }

    public BmpFile getSecondBmpFile() throws IOException, UnknownFormatException, BadImageTypeException, BadImageSizeException {
        BmpFile secondFile = getBmpFile();

        if (file.getHeader().getBitsPerPixel() != secondFile.getHeader().getBitsPerPixel())
            throw new BadImageTypeException("Obrazki maja rozne bity!");
        if (file.getHeader().getWidth() != secondFile.getHeader().getWidth() ||
                file.getHeader().getHeight() != secondFile.getHeader().getHeight())
            throw new BadImageSizeException("Obrazki nie maja takich samych rozmiarow!");

        return secondFile;
    }
}
