package com.oldschool.image.bitmap;

import com.oldschool.image.bitmap.bits.Constants;
import com.oldschool.image.bitmap.read.LitEndInputStream;
import com.oldschool.image.bitmap.write.LitEndOutputStream;

import java.awt.image.IndexColorModel;
import java.io.IOException;

/**
 * Created by KMacioszek on 2016-08-29.
 */
public class BmpHeader {

    String signature;
    int size;
    short reserved1;
    short reserved2;
    int offset;
    int sizeOfBitMapInfoHeader;
    int width;
    int height;
    short planes;
    short bitsPerPixel;
    int compressionType;
    int sizeOfImageDataInBytes;
    int xppm;
    int yppm;
    int numerOfColorsInImage;
    int numberOfImportantColors;

    // not in header!
    boolean binary = false;
    boolean grayscale = false;
    boolean rgb = false;

    int numColors;
    private boolean RGB;

    public BmpHeader(LitEndInputStream input) throws IOException {
        readSignature(input);
        readHeader(input);
    }

    public BmpHeader(BmpHeader header) {
        this.signature = header.getSignature();
        this.size = header.getSize();
        this.reserved1 = header.getReserved1();
        this.reserved2 = header.getReserved2();
        this.offset = header.getOffset();
        this.sizeOfBitMapInfoHeader = header.getSizeOfBitMapInfoHeader();
        this.width = header.getWidth();
        this.height = header.getHeight();
        this.planes = header.getPlanes();
        this.bitsPerPixel = header.getBitsPerPixel();
        this.compressionType = header.getCompressionType();
        this.sizeOfImageDataInBytes = header.getSizeOfImageDataInBytes();
        this.xppm = header.getXppm();
        this.yppm = header.getYppm();
        this.numerOfColorsInImage = header.getNumerOfColorsInImage();
        this.numberOfImportantColors = header.getNumberOfImportantColors();

        this.binary = header.getBinary();
        this.grayscale = header.getGrayscale();
        this.rgb = header.getRGB();
    }

    private void readHeader(LitEndInputStream input) throws IOException {
        size = input.readInt();
        reserved1 = input.readShort();
        reserved2 = input.readShort();
        offset = input.readInt();
        sizeOfBitMapInfoHeader = input.readInt();
        width = input.readInt();
        height = input.readInt();
        planes = input.readShort();
        bitsPerPixel = input.readShort();
        compressionType = input.readInt();
        sizeOfImageDataInBytes = input.readInt();
        xppm = input.readInt();
        yppm = input.readInt();
        numerOfColorsInImage = input.readInt();
        numberOfImportantColors = input.readInt();
        numColors = (int) Math.pow(2, bitsPerPixel);
    }

    private void readSignature(LitEndInputStream input) throws IOException {
        byte[] bSignature = new byte[2];
        input.read(bSignature);
        signature = new String(bSignature, "UTF-8");

        if (!Constants.BITMAP_TYPE.equals(signature))
            throw new IOException("Niepoprawna sygnatura " + signature);
    }

    public short getBitsPerPixel() {
        return bitsPerPixel;
    }

    public int getNumColors() {
        return numColors;
    }


    public void setBinary(boolean binary) {
        this.binary = binary;
    }

    public void setGrayscale(boolean grayscale) {
        this.grayscale = grayscale;
    }

    public void setRGB(boolean rgb) {
        this.rgb = rgb;
    }

    public int getCompressionType() {
        return compressionType;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean getGrayscale() {
        return grayscale;
    }

    public String getSignature() {
        return signature;
    }

    public int getSize() {
        return size;
    }

    public short getReserved1() {
        return reserved1;
    }

    public short getReserved2() {
        return reserved2;
    }

    public int getOffset() {
        return offset;
    }

    public int getSizeOfBitMapInfoHeader() {
        return sizeOfBitMapInfoHeader;
    }

    public short getPlanes() {
        return planes;
    }

    public int getSizeOfImageDataInBytes() {
        return sizeOfImageDataInBytes;
    }

    public int getXppm() {
        return xppm;
    }

    public int getYppm() {
        return yppm;
    }

    public int getNumerOfColorsInImage() {
        return numerOfColorsInImage;
    }

    public int getNumberOfImportantColors() {
        return numberOfImportantColors;
    }

    public boolean getBinary() {
        return binary;
    }

    public boolean getRGB() {
        return RGB;
    }

    public void write(LitEndOutputStream input) throws IOException {
//        litEndOutputStream.writeInteger(size);
//        litEndOutputStream.writeShort(reserved1);
//        litEndOutputStream.writeShort(reserved2);
//        litEndOutputStream.writeInteger(offset);
        input.writeInteger(sizeOfBitMapInfoHeader);
        input.writeInteger(width);
        input.writeInteger(height);
        input.writeShort(planes);

        if (bitsPerPixel == Constants.BITS_4_GRAY) bitsPerPixel = Constants.BITS_4;
        if (bitsPerPixel == Constants.BITS_8_GRAY) bitsPerPixel = Constants.BITS_8;

        input.writeShort(bitsPerPixel);
        input.writeInteger(compressionType);
        input.writeInteger(sizeOfImageDataInBytes);
        input.writeInteger(xppm);
        input.writeInteger(yppm);
        input.writeInteger(numerOfColorsInImage);
        input.writeInteger(numberOfImportantColors);
    }

    public void setBitsPerPixel(short bitsPerPixel) {
        this.bitsPerPixel = bitsPerPixel;
    }
}
