package pers.gon.infrastructure.common.utils;


import lombok.SneakyThrows;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static void dirCreate(String dir){
        File file = new File(dir);
        if(!file.exists()){
            file.mkdir();
        }
    }

    public static String getRootPath(){
        String projectPath = System.getProperty("user.dir");
        return projectPath;
    }

    public static String getWebPath(){
        return getRootPath()+"/interfaces/manage/src/main/java/pers/gon/manage/";
    }

    public static String getDomainPath(String moduleName){
        return getRootPath()+"/domain/"+moduleName+"/src/main/java/pers/gon/domain/";
    }

    public static String getPagePath(){
        return getRootPath()+"/interfaces/manage/src/main/resources/templates/modules/";
    }

    public static String getTemplatePath(){
        return getRootPath()+"/domain/scrum/src/main/resources/templates";
    }

    @SneakyThrows
    public static File createFile(String path,String fileName){
        dirCreate(path);
        File file = new File(path,fileName);
        if(!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @SneakyThrows
    public  static Writer getBufferedWriter(File file){
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
        return out;
    }
}
