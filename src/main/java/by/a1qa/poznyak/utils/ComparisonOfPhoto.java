package by.a1qa.poznyak.utils;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;

import java.awt.image.BufferedImage;

public class ComparisonOfPhoto {
    public static ImageComparisonResult compare(String actualPhotoName, String nameOfExpectedPhoto) {
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(System.getProperty("user.dir")+ nameOfExpectedPhoto);
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(actualPhotoName);
        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage).compareImages();
        return imageComparisonResult;
    }
}
