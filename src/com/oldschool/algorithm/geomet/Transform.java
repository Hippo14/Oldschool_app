package com.oldschool.algorithm.geomet;

import com.oldschool.image.bitmap.BmpFile;

/**
 * Created by MSI on 2016-09-11.
 */
public class Transform {

    BmpFile file;

    public Transform (BmpFile file) {
        this.file = file;
    }

    public BmpFile moveImage(int uX, int uY) {
        int[][] reds = newMatrix(file.getHeader().getWidth(), file.getHeader().getHeight());
        int[][] greens = newMatrix(file.getHeader().getWidth(), file.getHeader().getHeight());
        int[][] blues = newMatrix(file.getHeader().getWidth(), file.getHeader().getHeight());

        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                try {
                    reds[x + uX][y + uY] = file.getImage().getRed(x, y);
                    greens[x + uX][y + uY] = file.getImage().getGreen(x, y);
                    blues[x + uX][y + uY] = file.getImage().getBlue(x, y);
                } catch (Exception e) {}
            }
        }
        file.getImage().setReds(reds);
        file.getImage().setGreens(greens);
        file.getImage().setBlues(blues);

        return file;
    }

    public BmpFile scaleImage(int newWidth, int newHeight) {
//        int[][] reds = newMatrix(file.getHeader().getWidth(), file.getHeader().getHeight());
//        int[][] greens = newMatrix(file.getHeader().getWidth(), file.getHeader().getHeight());
//        int[][] blues = newMatrix(file.getHeader().getWidth(), file.getHeader().getHeight());

        int[][] reds = newMatrix(newWidth, newHeight);
        int[][] greens = newMatrix(newWidth, newHeight);
        int[][] blues = newMatrix(newWidth, newHeight);

        double xRatio = file.getHeader().getWidth() / (double) newWidth;
        double yRatio = file.getHeader().getHeight() / (double) newHeight;
        double pX, pY;

        resizeImage(newWidth, newHeight);

        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                pX = Math.floor(j * xRatio);
                pY = Math.floor(i * yRatio);

                try {
                    reds[j][i] = file.getImage().getRed((int)pX, (int)pY);
                    greens[j][i] = file.getImage().getGreen((int)pX, (int)pY);
                    blues[j][i] = file.getImage().getBlue((int)pX, (int)pY);
                } catch (Exception e) {}

            }
        }
        file.getImage().setReds(reds);
        file.getImage().setGreens(greens);
        file.getImage().setBlues(blues);

        return file;
    }

    private void resizeImage(int newWidth, int newHeight) {
        file.getHeader().setWidth(newWidth);
        file.getHeader().setHeight(newHeight);
    }

    public BmpFile rotateImage(int degree) {
        int[][] reds = newMatrix(file.getHeader().getWidth(), file.getHeader().getHeight());
        int[][] greens = newMatrix(file.getHeader().getWidth(), file.getHeader().getHeight());
        int[][] blues = newMatrix(file.getHeader().getWidth(), file.getHeader().getHeight());

        for (int x = 0; x < file.getHeader().getWidth(); x++) {
            for (int y = 0; y < file.getHeader().getHeight(); y++) {
                int hWidth = file.getHeader().getWidth() / 2;
                int hHeight = file.getHeader().getHeight() / 2;

                int xT = x - hWidth;
                int yT = y - hHeight;

                double sin = Math.sin(Math.toRadians(degree));
                double cos = Math.cos(Math.toRadians(degree));

                int xS = (int) ((cos * xT - sin * yT) + hWidth);
                int yS = (int) ((sin * xT + cos * yT) + hHeight);

                if(xS >= 0 && xS < file.getHeader().getWidth() && yS >= 0 && yS < file.getHeader().getHeight()) {
                    reds[xS][yS] = file.getImage().getRed(x, y);
                    greens[xS][yS] = file.getImage().getGreen(x, y);
                    blues[xS][yS] = file.getImage().getBlue(x, y);
                }
            }
        }
        file.getImage().setReds(reds);
        file.getImage().setGreens(greens);
        file.getImage().setBlues(blues);

        return file;
    }

    private int[][] newMatrix(int newWidth, int newHeight) {
        int[][] matrix = new int[newWidth][newHeight];
        for(int x = 0; x < matrix.length; x++)
            for(int y = 0; y < matrix[x].length; y++)
                matrix[x][y] = 0;
        return matrix;
    }

}
