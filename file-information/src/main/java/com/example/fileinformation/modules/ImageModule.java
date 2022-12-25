package com.example.fileinformation.modules;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class ImageModule extends Module {
    @Override
    public boolean isSuitableExtension(File file) {
        String extension = getExtension(file);
        return extension != null && (extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png"));
    }
    
    @Override
    public String getDescription() {
        return "size - Вывод размера файла\n" + "exif - Вывод информации exif\n" + "invisible - Вывод количества невидимых пикселей";
    }
    
    @Override
    public void executeCommand(String command, File file) {
        switch (command.toLowerCase()) {
            case "size":
                printSize(file);
                break;
            case "exif":
                printExif(file);
                break;
            case "invisible":
                printInvisiblePixels(file);
                break;
        }
    }
    
    private void printSize(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            System.out.println("Разрешение: " + image.getWidth() + "x" + image.getHeight() + "px");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void printExif(File file) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            
            System.out.println("EXIF: ");
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    System.out.println(tag);
                }
            }
        } catch (IOException | ImageProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void printInvisiblePixels(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            int amount = 0;
            
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    Color color = new Color(image.getRGB(x, y), true);
                    if (color.getAlpha() == 0) {
                        amount += 1;
                    }
                }
            }
            
            System.out.println(amount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
