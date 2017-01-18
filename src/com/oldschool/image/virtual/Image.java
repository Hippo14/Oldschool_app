package com.oldschool.image.virtual;

/**
 * Created by MSI on 2017-01-07.
 */
public class Image {

    private Header header;
    private Data data;

    public Image() {
        this.header = new Header();
        this.data = new Data();
    }

    public void setRGB(int x, int y, int rgb) {
        this.data.setRgb(x, y, rgb);
    }

    public void setSignature(byte[] signature) {
        this.header.setSignature(signature);
    }

    public void setWidth(int width) {
        this.header.setWidth(width);
    }

    public void setHeight(int height) {
        this.header.setHeight(height);
    }

    public void setType(byte type) {
        this.header.setType(type);
    }

    public void setSize(int size) {
        this.header.setSize(size);
    }

    public int getWidth() {
        return this.header.getWidth();
    }

    public int getHeight() {
        return this.header.getHeight();
    }

    public byte getType() {
        return this.header.getType();
    }

    public int getSize() {
        return this.header.getSize();
    }

    public void initImageMatrix() {
        this.data.initImageMatrix(this.header.getWidth(), this.header.getWidth());
    }

    public byte[] getSignature() {
        return this.header.getSignature();
    }

    public int getRGB(int x, int y) {
        return this.data.getRgb(x, y);
    }
}
