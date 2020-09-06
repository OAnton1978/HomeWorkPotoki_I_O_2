package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        GameProgress gameProgress1 = new GameProgress(45, 10, 4, 12.13);
        GameProgress gameProgress2 = new GameProgress(65, 13, 5, 14.18);
        GameProgress gameProgress3 = new GameProgress(85, 12, 2, 10.45);

        saveGame("D:/Games/savegames/save1.dat", gameProgress1);
        saveGame("D:/Games/savegames/save2.dat", gameProgress2);
        saveGame("D:/Games/savegames/save3.dat", gameProgress3);

        ArrayList<String> list = new ArrayList<>();
        String path = "D:/Games/savegames/zip.zip";
        list.add("D:/Games/savegames/save1.dat");
        list.add("D:/Games/savegames/save2.dat");
        list.add("D:/Games/savegames/save3.dat");
        zipFiles(path, list);

        File dir = new File("D:/Games/savegames/");
        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                if (item.isFile()) {
                    if (item.getName().contains("zip")) {
                    } else {
                        String temp = item.getName();
                        if (item.delete()) System.out.println(temp + " - удален");
                    }
                }
            }
        }
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, ArrayList<String> list) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(path));
        for (String s : list) {
            try (FileInputStream fis = new FileInputStream(s)) {
                ZipEntry entry = new ZipEntry(s.split("/")[s.split("/").length - 1]);  //в аргументе выделяем имя файла из полного пути
                zos.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zos.write(buffer);
                zos.closeEntry();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        zos.close();
    }
}


