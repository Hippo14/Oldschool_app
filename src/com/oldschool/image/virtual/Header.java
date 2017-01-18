package com.oldschool.image.virtual;

/**
 * Created by MSI on 2017-01-07.
 */
public class Header {
    private byte[] signature;
    private int width;
    private int height;
    private byte type;
    private int size;

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public byte getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public byte[] getSignature() {
        return signature;
    }
}
