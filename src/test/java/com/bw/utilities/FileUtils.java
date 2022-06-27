package com.bw.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public static synchronized void writeFile(String data, String path) throws IOException {
        FileWriter fileWriter=null;
        try{
            String userDir = System.getProperty("user.dir");
            String filePath = userDir + File.separator + path;
            fileWriter = new FileWriter(path);
            fileWriter.write(data);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fileWriter!=null)
                fileWriter.close();
        }
    }

    public static synchronized void deleteAndCreateADirectory(String path){
        try{
            File file= new File(path);
            if(file.exists()) {
                deleteDirectory(file);
            }
            file.mkdir();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void deleteDirectory(File directory){
        File[] list =  directory.listFiles();
        if(list!=null) {
            for (File item : list) {
                deleteDirectory(item);
            }
        }
        directory.delete();
    }
}
