package com.oldschool.algorithm.utils;

import java.io.IOException;

/**
 * Created by MSI on 2016-09-10.
 */
public class Loader {

    private static boolean loading = true;
    private static Thread th;
    public static synchronized void start(String msg) throws IOException, InterruptedException {
        System.out.println(msg);
        th = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.write("\r|".getBytes());
                    while(loading) {
                        System.out.write("-".getBytes());
                        Thread.sleep(500);
                    }
                    System.out.write("| Done \r\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        th.start();
    }

    public static synchronized void stop() {
        loading = false;
        while(Loader.getStatus());
        loading = true;
    }

    public static boolean getStatus() {
        return th.isAlive();
    }
}
