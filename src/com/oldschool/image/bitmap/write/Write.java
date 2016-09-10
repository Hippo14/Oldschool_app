package com.oldschool.image.bitmap.write;

import com.oldschool.image.Pixel;
import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.BmpHeader;
import com.oldschool.image.bitmap.BmpImage;
import com.oldschool.image.bitmap.bits.*;

import java.awt.image.IndexColorModel;
import java.io.*;

/**
 * Created by KMacioszek on 2016-09-05.
 */
public class Write {

    BmpFile file;

    // New bmp file
    BmpHeader header;
    BmpImage image;

    // Header parameters
    int headerSize;
    int mapBytes;
    int offset;
    int bytesPerLine;
    int fileSize;
    IndexColorModel indexColorModel;
    Pixel[] pixels;

    public Write(BmpFile bmpFile, File file) throws Exception {
        this.file = bmpFile;
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            write(bufferedOutputStream);
            bufferedOutputStream.flush();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException ex) {

            }
        }
    }

    private void write (OutputStream outputStream) throws Exception {
        // Create empty header
        header = createHeader();
        // Prepare header
        prepareHeader();

        LitEndOutputStream input = new LitEndOutputStream(outputStream);

        // Write header
        writeFileHeader(input);

        // Write image
        writeImage(input);

        // TEST , 'Krzysztof Macioszek' in SHA-512
        byte[] author = "4c701ab9fef083b8ea130642f68be0d0f15d53c9afb80d812a138cdb591943f70b3a4e6b2b619b6741784e7e907feb6df0f8b45e7af04fadbc468f98bc7da867".getBytes("UTF-8");
        input.write(author);
    }

    private void writeImage(LitEndOutputStream input) throws IOException {
        if (header.getBitsPerPixel() <= Constants.BITS_8) writeColorMap(pixels, input);

        if (header.getBitsPerPixel() == Constants.BITS_1)       Bits.BIT1.write(file, input);
        else if (header.getBitsPerPixel() == Constants.BITS_8)  Bits.BIT8.write(file, input);
        else if (header.getBitsPerPixel() == Constants.BITS_24) Bits.BIT24.write(file, input);
    }

    private void writeColorMap(Pixel[] pixels, LitEndOutputStream input) throws IOException {
        int mapSize = pixels.length;
        for (int i = 0; i < mapSize; i++) {
            int r = pixels[i].getRed();
            int g = pixels[i].getGreen();
            int b = pixels[i].getBlue();

            input.writeByte(b);
            input.writeByte(g);
            input.writeByte(r);
            input.writeByte(0);
        }
    }

    private void writeFileHeader(LitEndOutputStream input) throws IOException {
        byte[] signature = Constants.BITMAP_TYPE.getBytes("UTF-8");
        input.write(signature);

        input.writeInteger(fileSize);

        // Reserved
        input.writeShort(0);
        input.writeShort(0);

        input.writeInteger(offset);

        header.write(input);
    }

    private void prepareHeader() {
        int mapSize = 0;
        indexColorModel = file.getImage().getIndexColorModel();

        pixels = file.getImage().getPixels();

        // Header size
        headerSize = 14 + header.getSizeOfBitMapInfoHeader();
        // Map Size
        mapBytes = 4 * mapSize;
        // Data offset
        offset = headerSize + mapBytes;
        // Bytes per line
        bytesPerLine = 0;

        if (header.getBitsPerPixel() == Constants.BITS_1) bytesPerLine = Bits.BIT1.getBytesPerLine(header.getWidth());
        else if (header.getBitsPerPixel() == Constants.BITS_8)  bytesPerLine = Bits.BIT8.getBytesPerLine(header.getWidth());
        else if (header.getBitsPerPixel() == Constants.BITS_24)  bytesPerLine = Bits.BIT24.getBytesPerLine(header.getWidth());

        // File size
        fileSize = offset + bytesPerLine * header.getHeight();
    }

    private BmpHeader createHeader() throws IOException {
        return new BmpHeader(file.getHeader());
    }

}
