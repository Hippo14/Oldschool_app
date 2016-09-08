package com.oldschool;

import com.oldschool.algorithm.binary.logical.Negation;
import com.oldschool.algorithm.binary.logical.Product;
import com.oldschool.algorithm.binary.logical.Sum;
import com.oldschool.algorithm.binary.logical.XOR;
import com.oldschool.algorithm.grayscale.arytm.SumConst;
import com.oldschool.algorithm.normalize.Normalize;
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

    BmpFile file;
    String sDir;
    private BmpFile secondBmpFile;

    public Application() {

    }

    public void initialize() throws Exception {
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

        String fileName = file.getName();
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
        System.out.println("1. ");

        Scanner input= new Scanner(System.in);

        int option = input.nextInt();

        switch (option) {
            case 1:
                logicNegation();
                break;
            case 2:
                logicSum();
                break;
            case 3:
                logicProduct();
                break;
            case 4:
                logicXOR();
                break;
            case 5:
                graySum();
                break;
            case 6:
                graySumConst();
                break;
            case 7:
                rgbSum();
                break;
        }


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
