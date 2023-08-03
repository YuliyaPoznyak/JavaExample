package by.a1qa.poznyak.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DownloadingFile {
    public static File downloadPhoto(String linkToDownloadPhoto) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new URL(linkToDownloadPhoto));
        File fileToSave = new File("saved.jpg");
        ImageIO.write(bufferedImage, "jpg", fileToSave);
        return fileToSave;
    }
}
