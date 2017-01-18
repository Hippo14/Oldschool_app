package com.oldschool.image.smietnik.png;

import java.awt.image.*;
import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.InflaterInputStream;

/**
 * Created by MSI on 2017-01-04.
 */
public class PNGDecoder {

    public PNGDecoder() {

    }

    public BufferedImage decode(String filename) {
        try {
            return decode(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage decode (InputStream in) throws IOException {
        DataInputStream dataIn = new DataInputStream(in);
        readSignature(dataIn);
        PNGData chunks = readChunks(dataIn);

        long widthLong = chunks.getWidth();
        long heightLong = chunks.getHeight();
        if (widthLong > Integer.MAX_VALUE || heightLong > Integer.MAX_VALUE) {
            throw new IOException("That image is too wide or tall.");
        }
        int width = (int) widthLong;
        int height = (int) heightLong;

        ColorModel cm = chunks.getColorModel();
        WritableRaster raster = chunks.getRaster();

        BufferedImage image = new BufferedImage(cm, raster, false, null);

        return image;
    }

    private void readSignature(DataInputStream in) throws IOException {
        long signature = in.readLong();
        if (signature != 0x89504e470d0a1a0aL) {
            throw new IOException("PNG Signature not found!");
        }
    }

    private PNGData readChunks(DataInputStream in) throws IOException {
        PNGData chunks = new PNGData();

        boolean trucking = true;
        while (trucking) {
            try {
                // Read the lenght
                int lenght = in.readInt();
                if (lenght < 0) throw new IOException("Sorry, that file is too long.");

                // Read the type
                byte[] typeBytes = new byte[4];
                in.readFully(typeBytes);
                // Read the data
                byte[] data = new byte[lenght];
                in.readFully(data);
                // Read the CRC
                long crc = in.readInt() & 0x00000000ffffffffL;
                // Unsigned.
                if (verifyCRC(typeBytes, data, crc) == false)
                    throw new IOException("That file appears to be corrupted.");

                PNGChunk chunk = new PNGChunk(typeBytes, data);
                chunks.add(chunk);
            } catch (EOFException e) {
                trucking = false;
            }
        }
        return chunks;
    }

    private boolean verifyCRC(byte[] typeBytes, byte[] data, long crc) {
        CRC32 crc32 = new CRC32();
        crc32.update(typeBytes);
        crc32.update(data);
        long calc = crc32.getValue();
        return (calc == crc);
    }

    class PNGData {
        private int numberOfChunks;
        private PNGChunk[] chunks;

        public PNGData() {
            numberOfChunks = 0;
            chunks = new PNGChunk[10];
        }

        public void add(PNGChunk chunk) {
            chunks[numberOfChunks++] = chunk;
            if (numberOfChunks >= chunks.length) {
                PNGChunk[] largerArray = new PNGChunk[chunks.length + 10];
                System.arraycopy(chunks, 0, largerArray, 0, chunks.length);
                chunks = largerArray;
            }
        }

        public long getWidth() {
            return getChunk("IHDR").getUnsignedInt(0);
        }

        public long getHeight() {
            return getChunk("IHDR").getUnsignedInt(4);
        }

        public short getBitsPerPixel() {
            return getChunk("IHDR").getUnsignedByte(8);
        }

        public short getColorType() {
            return getChunk("IHDR").getUnsignedByte(9);
        }

        public short getCompression() {
            return getChunk("IHDR").getUnsignedByte(10);
        }

        public short getFilter() {
            return getChunk("IHDR").getUnsignedByte(11);
        }

        public short getInterlace() {
            return getChunk("IHDR").getUnsignedByte(12);
        }

        public ColorModel getColorModel() {
            short colorType = getColorType();
            int bitsPerPixel = getBitsPerPixel();

            if (colorType == 3) {
                byte[] paletteData = getChunk("PLTE").getData();
                int paletteLenght = paletteData.length / 3;
                return new IndexColorModel(bitsPerPixel, paletteLenght, paletteData, 0, false);
            }
            else if (colorType == 2) {
                byte[] paletteData = getChunk("IDAT").getData();
                int paletteLenght = paletteData.length / 3;
                return new IndexColorModel(bitsPerPixel, paletteLenght, paletteData, 0, false);
            }
            System.out.println("Unsupported color type: " + colorType);
            return null;
        }

        public WritableRaster getRaster() {
            int width = (int) getWidth();
            int height = (int) getHeight();
            int bitsPerPixel = getBitsPerPixel();
            short colorType = getColorType();

            if (colorType == 3) {
                byte[] imageData = getImageData();
                DataBuffer db = new DataBufferByte(imageData, imageData.length);
                WritableRaster raster = Raster.createPackedRaster(db, width, height, bitsPerPixel, null);
                return raster;
            }
            else if (colorType == 2) {
                byte[] imageData = getImageData();
                DataBuffer db = new DataBufferByte(imageData, imageData.length);
                WritableRaster raster = Raster.createPackedRaster(db, width, height, bitsPerPixel, null);
                return raster;
            }
            else {
                System.out.println("Unsupported color type: " + colorType);
            }
            return null;
        }

        public byte[] getImageData() {
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                // Write all the IDAT data into the array
                for (int i = 0; i < numberOfChunks; i++) {
                    PNGChunk chunk = chunks[i];
                    if (chunk.getTypeString().equals("IDAT")) {
                        out.write(chunk.getData());
                    }
                }
                out.flush();
                // Now deflate data
                InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(out.toByteArray()));
                ByteArrayOutputStream inflatedOut = new ByteArrayOutputStream();
                int readLenght;
                byte[] block = new byte[8192];
                while ((readLenght = in.read(block)) != -1)
                    inflatedOut.write(block, 0, readLenght);
                inflatedOut.flush();
                byte[] imageData = inflatedOut.toByteArray();
                // Compute the real lenght
                int width = (int) getWidth();
                int height = (int) getHeight();
                int bitsPerPixel = getBitsPerPixel();
                int lenght = width * height * bitsPerPixel / 8;

                byte[] prunedData = new byte[lenght];

                // We can only deal with non-interlaced images.
                if (getInterlace() == 0) {
                    int index = 0;
                    for (int i = 0; i < lenght; i++) {
                        if ((i * 8 / bitsPerPixel) % width == 0) {
                            index++;
                        }
                        prunedData[i] = imageData[index++];
                    }
                }
                else {
                    System.out.println("Couldn't undo interlacing.");
                }
                return prunedData;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public PNGChunk getChunk(String type) {
            for (int i = 0; i < numberOfChunks; i++)
                if (chunks[i].getTypeString().equals(type))
                    return chunks[i];
            return null;
        }

    }

}
