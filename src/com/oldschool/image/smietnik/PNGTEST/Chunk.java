package com.oldschool.image.smietnik.PNGTEST;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by MSI on 2017-01-04.
 */
class Chunk extends InputStream {
    int[] type = new int[4];
    int[] crc = new int[4];
    int rolling_crc = 0;
    long length = 0;
    long count = 0;
    InputStream imageFile;

    public Chunk(InputStream imageFile) throws IOException, PNG.PNGException {
        byte[] lengths = new byte[4];
        imageFile.read(lengths);
        length = combine(lengths);
        //length = imageFile.nextNBytes(4);
        for(int i = 0; i < 4; i++) {
            type[i] = imageFile.read();
            if(type[i]==-1) throw new PNG.PNGException("Invalid chunks");
        }
        this.imageFile = imageFile;
    }

    private boolean checkCRC() throws IOException {
        for(int i = 0; i < 4; i++) {
            crc[i] = imageFile.read();
        }
        return true;
    }

    void skip() throws IOException {
        while(read()!=-1);
    }

    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < 4; i++) {
            s+=(char) type[i];
        }
        return s;
    }

    @Override
    public int read() throws IOException {
        int r = -1;
        if(count>=length) return r;
        count++;
        rolling_crc++;
        r = imageFile.read();
        if(count==length) {
            checkCRC();
        }
        return r;
    }

    public long read(int n) throws IOException {
        byte[] bytes = new byte[n];
        super.read(bytes);
        return combine(bytes);
    }

    static long combine(byte[] bytes) {
        int r = 0;
        int n = bytes.length;
        for(int i=0; i<n; i++) {
            r += (bytes[i] & 0xFF) * (Math.pow(2,8*(n-1-i)));
            if(bytes[i]==-1) return -1;
        }
        return r;
    }

    @Override
    public void mark(int readLimit) {
        imageFile.mark(readLimit);
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public void reset() throws IOException {
        imageFile.reset();
    }

}
