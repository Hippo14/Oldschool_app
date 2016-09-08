package com.oldschool.image.bitmap.write;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by KMacioszek on 2016-08-11.
 */
public class LitEndOutputStream extends FilterOutputStream {

    protected int written;

    public LitEndOutputStream(OutputStream out) {
        super(out);
    }

    public void writeInteger(int i) throws IOException {
        out.write(i & 0xFF);
        out.write((i >>> 8) & 0xFF);
        out.write((i >>> 16) & 0xFF);
        out.write((i >>> 24) & 0xFF);
        written += 4;
    }

    public void writeShort(int s) throws IOException {
        out.write(s & 0xFF);
        out.write((s >>> 8) & 0xFF);
        written += 2;

    }

    public void writeByte(int b) throws IOException {
        out.write(b);
        written++;
    }

}