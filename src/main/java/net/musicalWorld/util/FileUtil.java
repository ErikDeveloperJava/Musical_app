package net.musicalWorld.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class FileUtil {

    private Environment environment;
    private String[] imageFormats;
    private String musicFormat;
    private String rootImagePath;
    private String rootMusicPath;

    @Autowired
    public FileUtil(Environment environment) {
        this.environment = environment;
        this.imageFormats = new String[]{"image/jpeg","image/png"};
        this.musicFormat = "audio/mp4";
        this.rootImagePath  = environment.getProperty("root.image.path");
        this.rootMusicPath  = environment.getProperty("root.music.path");
    }

    public boolean isValidImgFormat(String format){
        for (String imgFormat : imageFormats) {
            if(imgFormat.equals(format)){
                return true;
            }
        }
        return false;
    }

    public boolean isValidMusicFormat(String format){
        return musicFormat.equals(format);
    }

    public void saveImage(String parent,String img,byte[] bytes){
        File file = new File(rootImagePath,parent);
        if(!file.exists()){
            if(!file.mkdir()){
                throw new RuntimeException(file + " failed created");
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(new File(file,img));
            out.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    public void saveMusic(String parent,String img,byte[] bytes){
        File file = new File(rootMusicPath,parent);
        if(!file.exists()){
            if(!file.mkdir()){
                throw new RuntimeException(file + " failed created");
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(new File(file,img));
            out.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    public void deleteImg(String fileName){
        File file = new File(rootImagePath,fileName);
        if(file.exists()){
            delete(file);
        }
    }

    public void deleteMusic(String fileName){
        File file = new File(rootMusicPath,fileName);
        if(file.exists()){
            delete(file);
        }
    }

    public void delete(File file){
        if(file.isDirectory()){
            for (File f : file.listFiles()) {
                delete(f);
            }
        }else {
            file.delete();
        }
    }


}
