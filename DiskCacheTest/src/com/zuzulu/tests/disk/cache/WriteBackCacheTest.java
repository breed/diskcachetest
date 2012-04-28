package com.zuzulu.tests.disk.cache;

import java.io.File;

public class WriteBackCacheTest {

    /**
     * Runs a write/read test to see if data is lost
     * when writing to a disk with the disk cache turned on
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // Do a clean start
        if (args.length < 2) {
            System.err.println("USAGE: WriteBackCacheTest filePath bufferSize delay [nosync] [nobarrier] [nocache]");
            System.exit(2);
        }
        File scratchFile = new File(args[0]);
        int bufferSize = Integer.parseInt(args[1]);
        long delay = Long.parseLong(args[2]);
        boolean sync = true;
        boolean diskCache = true;
        boolean barrier = true;
        for(int i = 3; i < args.length; i++) {
            if (args[i].equals("nosync")) {
                sync = false;
            } else if (args[i].equals("nocache")) {
                diskCache = false;
            } else if (args[i].equals("nobarrier")) {
                barrier = false;
            } else {
                System.err.println("Invalid option: " + args[i]);
                System.exit(2);
            }
        }
        unmountDisk();
        killDisk();
        startDisk(diskCache);
        mountDisk(barrier);
        WriteResult wr = WriteTest.runWriteTest(scratchFile, bufferSize, delay, sync);
        startDisk(diskCache);
        mountDisk(barrier);
        ReadResult rr = ReadTest.readTest(scratchFile, bufferSize);
        System.out.println("MissingTime:\t" + (wr.getLastTime() - rr.getLastTime()) + "\tMissingBytes:\t" + (wr.getWritten() - rr.getLastPos()) + "\tWritten:\t" + wr.getWritten() + "\tDuration:\t" + wr.getDuration() + "\t" + (sync?"sync":"nosync") + "\t" + (diskCache?"diskCache":"nodiskCache") + "\t" + (barrier?"barrier":"nobarrier"));
        System.exit(0);
    }

    static void unmountDisk() {
        try {
            Runtime.getRuntime().exec("./unmountDisk.sh").waitFor();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    static void killDisk() {
        try {
            Runtime.getRuntime().exec("./killDisk.sh").waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void mountDisk(boolean barrier) {
        try {
            Runtime.getRuntime().exec("./mountDisk.sh " + (barrier?1:0)).waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void startDisk(boolean diskCache) {
        try {
            Runtime.getRuntime().exec("./startDisk.sh " + (diskCache?1:0)).waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
