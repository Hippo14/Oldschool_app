package com.oldschool;

import com.oldschool.algorithm.binary.logical.*;
import com.oldschool.algorithm.binary.morphology.*;
import com.oldschool.algorithm.binary.morphology.Closing;
import com.oldschool.algorithm.binary.morphology.Dilation;
import com.oldschool.algorithm.binary.morphology.Erosion;
import com.oldschool.algorithm.binary.morphology.Morphology;
import com.oldschool.algorithm.binary.morphology.Opening;
import com.oldschool.algorithm.filters.*;
import com.oldschool.algorithm.geomet.Transform;
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
import com.oldschool.algorithm.grayscale.morphology.*;
import com.oldschool.algorithm.normalize.Normalize;
import com.oldschool.algorithm.rgb.histogram.Otsu;
import com.oldschool.algorithm.rgb.histogram.RGBHistogram;
import com.oldschool.algorithm.rgb.histogram.Threeshold;
import com.oldschool.algorithm.utils.Config;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageSizeException;
import com.oldschool.image.bitmap.exception.BadImageTypeException;
import com.oldschool.image.bitmap.exception.UnknownFormatException;
import com.oldschool.image.bitmap.read.Read;
import com.oldschool.image.bitmap.write.Write;

import javax.annotation.PostConstruct;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    private boolean running = true;
    private boolean choosen = true;

    public Application() {

    }

    public void initialize() {

//        http://stackoverflow.com/questions/1448858/how-to-color-system-out-println-output
//        System.out.println((char)27 + "[31mThis text would show up red" + (char)27 + "[0m");

        // Init config.properties
        Config.init();


        while (running) {
            try {
                // Get Bmp file
                file = getBmpFile();

                // Choose option
                chooseOption();

                if (choosen)
                // Write Bmp file
                writeBmp("_" + getName());

                // Normalize
                if (choosen && normalize())
                    // Write Bmp file
                    writeBmp("_normalize" + "_" + getName());

                if (choosen)
                    // Stop ?
                    doYouWantStop();
                choosen = true;
            }
            catch (BadImageSizeException e) {
                printError(e.getMessage());
                // Stop ?
                doYouWantStop();
            }
            catch (BadImageTypeException e) {
                printError(e.getMessage());
                // Stop ?
                doYouWantStop();
            }
            catch (UnknownFormatException e) {
                printError(e.getMessage());
                // Stop ?
                doYouWantStop();
            }
            catch (Exception e) {
                printError(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void printError(String message) {
        System.out.println("[ERROR] : " + (char)27 + "[30;41m" + message + (char)27 + "[0m");
    }

    private void doYouWantStop() {
        System.out.println(Config.get("wantStop") + (char)27 + "[32m" + "(Y)es / (N)o" + (char)27 + "[0m");

        String line = input.next();

        if (line.contains("Y") || line.contains("y") || line.contains("Yes") || line.contains("yes"))
            running = false;
    }

    private boolean normalize() {
        Normalize normalize = new Normalize(file);
        if (normalize.getNormalization())
            file = normalize.getFile();

        return normalize.getNormalization();
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
        cls();

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
        System.out.println(Config.get("menu0"));
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
            case 0:
                System.exit(0);
            break;
        }
    }

    private void filter() throws Exception {
        System.out.println(Config.get("menu91"));
        System.out.println(Config.get("menu92"));
        System.out.println(Config.get("menu93"));
        System.out.println(Config.get("menu94"));
        System.out.println(Config.get("menuBack"));
        System.out.print(Config.get("menuWybierzOpcje"));
        option = input.nextInt();

        switch (option) {
            case 1:
                Class c = FilterList.class;
                int i = 1;
                List<String> methodNames = new ArrayList<>();

                // Find all methods in FilterList.class
                for (Method method : c.getDeclaredMethods()) {
                    System.out.println(i++ + ". " + method.getName());
                    methodNames.add(method.getName());
                }

                System.out.println(Config.get("menuBack"));

                // Choose option
                System.out.print(Config.get("menuWybierzOpcje"));
                option = input.nextInt();

                if (option == 0) {
                    choose();
                    return;
                }

                // Initialize chosen method from FilterList.class
                FilterList filterList = new FilterList(file, methodNames.get(option + 1));
                file = filterList.getFile();

                this.clazz = filterList.getClass();

//                Class c = FilterList.class;
//
//                List<String> methodNames = new ArrayList<>();
//
//                // Find all methods in FilterList.class
//                for (Method method : c.getDeclaredMethods()) {
//                    methodNames.add(method.getName());
//                }
//
//                BmpFile tempFile = this.file;
//
//                for (Method method : c.getDeclaredMethods()) {
//                    FilterList filterList = new FilterList(file, method.getName());
//                    file = filterList.getFile();
//                    this.clazz = filterList.getClass();
//                    writeBmp(method.getName() + "_");
//                    normalize();
//                    writeBmp(method.getName() + "_normalize");
//                    this.file = tempFile;
//                }
                break;
            case 2:
                FilterMaximum filterMaximum = new FilterMaximum(file);
                file = filterMaximum.getFile();
                this.clazz = filterMaximum.getClass();
                break;
            case 3:
                FilterMinimum filterMinimum = new FilterMinimum(file);
                file = filterMinimum.getFile();
                this.clazz = filterMinimum.getClass();
                break;
            case 4:
                FilterMedian filterMedian = new FilterMedian(file);
                file = filterMedian.getFile();
                this.clazz = filterMedian.getClass();
                break;
            case 0:
                choose();
                return;
        }




//        Class c = FilterList.class;
//
//        List<String> methodNames = new ArrayList<>();
//
//        // Find all methods in FilterList.class
//        for (Method method : c.getDeclaredMethods()) {
//            methodNames.add(method.getName());
//        }
//
//        BmpFile tempFile = this.file;
//
//        for (Method method : c.getDeclaredMethods()) {
//            FilterList filterList = new FilterList(file, method.getName());
//            file = filterList.getFile();
//            this.clazz = filterList.getClass();
//            writeBmp(method.getName() + "_");
//            normalize();
//            writeBmp(method.getName() + "_normalize");
//            this.file = tempFile;
//        }





//        Class c = FilterList.class;
//        int i = 1;
//        List<String> methodNames = new ArrayList<>();
//
//        // Find all methods in FilterList.class
//        for (Method method : c.getDeclaredMethods()) {
//            System.out.println(i++ + ". " + method.getName());
//            methodNames.add(method.getName());
//        }
//
//        System.out.println(Config.get("menuBack"));
//
//        // Choose option
//        System.out.print(Config.get("menuWybierzOpcje"));
//        option = input.nextInt();
//
//        if (option == 0) {
//            choose();
//            return;
//        }
//
//        // Initialize chosen method from FilterList.class
//        FilterList filterList = new FilterList(file, methodNames.get(option + 1));
//        file = filterList.getFile();
//
//        this.clazz = filterList.getClass();
    }

    private void morfGrayscale() throws IOException, BadImageTypeException {
        System.out.println(Config.get("menu81"));
        System.out.println(Config.get("menu82"));
        System.out.println(Config.get("menu83"));
        System.out.println(Config.get("menu84"));
        System.out.println(Config.get("menuBack"));
        System.out.print(Config.get("menuWybierzOpcje"));
        option = input.nextInt();


        switch (option) {
            case 1:
                com.oldschool.algorithm.grayscale.morphology.Erosion erosion = new com.oldschool.algorithm.grayscale.morphology.Erosion(file);
                this.file = erosion.getFile();
                clazz = erosion.getClass();
                break;
            case 2:
                com.oldschool.algorithm.grayscale.morphology.Dilation dilation = new com.oldschool.algorithm.grayscale.morphology.Dilation(file);
                this.file = dilation.getFile();
                clazz = dilation.getClass();
                break;
            case 3:
                com.oldschool.algorithm.grayscale.morphology.Opening opening = new com.oldschool.algorithm.grayscale.morphology.Opening(file);
                this.file = opening.getFile();
                clazz = opening.getClass();
                break;
            case 4:
                com.oldschool.algorithm.grayscale.morphology.Closing closing = new com.oldschool.algorithm.grayscale.morphology.Closing(file);
                this.file = closing.getFile();
                clazz = closing.getClass();
                break;
            case 0:
                choose();
                return;
        }
    }

    private void morfBinary() throws BadImageTypeException {
        System.out.println(Config.get("menu81"));
        System.out.println(Config.get("menu82"));
        System.out.println(Config.get("menu83"));
        System.out.println(Config.get("menu84"));
        System.out.println(Config.get("menuBack"));
        System.out.print(Config.get("menuWybierzOpcje"));
        option = input.nextInt();

        switch (option) {
            case 1:
                Erosion erosion = new Erosion(file);
                this.file = erosion.getFile();
                clazz = erosion.getClass();
            break;
            case 2:
                Dilation dilation = new Dilation(file);
                this.file = dilation.getFile();
                clazz = dilation.getClass();
            break;
            case 3:
                Opening opening = new Opening(file);
                this.file = opening.getFile();
                clazz = opening.getClass();
            break;
            case 4:
                Closing closing = new Closing(file);
                this.file = closing.getFile();
                clazz = closing.getClass();
            break;
            case 0:
                choose();
                return;
        }
    }

    private void histogramRGB() throws IOException, BadImageTypeException, InterruptedException {
        System.out.println(Config.get("menu51"));
        System.out.println(Config.get("menu52"));
        System.out.println(Config.get("menu53"));
        System.out.println(Config.get("menu64"));
        System.out.println(Config.get("menu65"));
        System.out.println(Config.get("menuBack"));
        System.out.print(Config.get("menuWybierzOpcje"));
        option = input.nextInt();

        RGBHistogram histogram = new RGBHistogram(file);
        File pathfile = new File(sDir);
        String path = pathfile.getParent() + "\\";

        switch (option) {
            case 1:
                histogram.histogram();
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
                histogram = new Otsu(file);
                file = histogram.getFile();
            break;
            case 0:
                choose();
                return;
        }
        histogram.toTextFile(path);
        clazz = histogram.getClass();
    }

    private void histogramGrayscale() throws IOException, BadImageTypeException {
        System.out.println(Config.get("menu51"));
        System.out.println(Config.get("menu52"));
        System.out.println(Config.get("menu53"));
        System.out.println(Config.get("menuBack"));
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
            case 0:
                choose();
                return;
        }
        histogram.toTextFile(path);
        clazz = histogram.getClass();
    }

    private void geometr() {
        System.out.println(Config.get("menu41"));
        System.out.println(Config.get("menu43"));
        System.out.println(Config.get("menu44"));
        System.out.println(Config.get("menu45"));
        System.out.println(Config.get("menu46"));
        System.out.println(Config.get("menu47"));
        System.out.println(Config.get("menuBack"));;
        System.out.print(Config.get("menuWybierzOpcje"));
        option = input.nextInt();

        Transform transform = new Transform(file);
        int x,y;

        switch (option) {
            case 1:
                x = getNewXVector();
                y = getNewYVector();
                file = transform.moveImage(x, y);
            break;
            case 2:
                System.out.println(Config.get("newSize"));
                x = getNewWidth();
                y = getNewHeight();
                transform.scaleImage(x, y);
            break;
            case 3:
                int degree= getDegree();
                file = transform.rotateImage(degree);
            break;
            case 4:
                file = transform.symmetryImageOX();
            break;
            case 5:
                file = transform.symmetryImageOY();
            break;
            case 6:
                file = transform.symmetryImageOXOY();
            break;
            case 0:
                choose();
                return;
        }
        clazz = transform.getClass();
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
        System.out.println(Config.get("menuBack"));
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
            case 0:
                choose();
                return;
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
        System.out.println(Config.get("menuBack"));
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
            case 0:
                choose();
                return;
        }
        clazz = operation.getClass();
    }

    private void logicBinary() throws Exception {
        System.out.println(Config.get("menu11"));
        System.out.println(Config.get("menu12"));
        System.out.println(Config.get("menu13"));
        System.out.println(Config.get("menu14"));
        System.out.println(Config.get("menuBack"));
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
            case 0:
                choose();
                return;
        }
        clazz = logical.getClass();
    }

    private void choose() {
        choosen = false;
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

    public int getNewWidth() {
        System.out.println(Config.get("newWidth"));
        return input.nextInt();
    }

    public int getNewHeight() {
        System.out.println(Config.get("newHeight"));
        return input.nextInt();
    }

    public String getName() {
        String string = clazz.getName();
        return string.replace("com.oldschool.algorithm", "").replace(".", "_");
    }

    public int getNewXVector() {
        System.out.println(Config.get("newXVector"));
        return input.nextInt();
    }

    public int getNewYVector() {
        System.out.println(Config.get("newYVector"));
        return input.nextInt();
    }

    public int getDegree() {
        System.out.println(Config.get("degree"));
        return input.nextInt();
    }

    public void cls() {


        System.out.print("\033[H\033[2J");
    }
}