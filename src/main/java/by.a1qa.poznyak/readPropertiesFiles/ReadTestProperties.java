package by.a1qa.poznyak.readPropertiesFiles;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadTestProperties {
    public static FileInputStream fileInputStream;
    public static Properties prop;
    public static final String WHERE_IS_PROPERTIES = "src/test/resources/test.properties";

    public static void prepareTestData() {
        try {
            prop = new Properties();
            fileInputStream = new FileInputStream(WHERE_IS_PROPERTIES);
            prop.load(fileInputStream);
        } catch (IOException e) {
            System.out.println("There is no such file with configuration data");
        }
    }
    public static String getOnePostShouldBe() {
        return prop.getProperty("onePost");
    }
    public static String getOneUserShouldBe() {
        return prop.getProperty("oneUser");
    }
    public static Long getBuildNumber() {
        return Long.parseLong(prop.getProperty("buildNumber"));
    }
    public static String getRepeatNumber() {
        return prop.getProperty("numberForRepeat");
    }
    public static int getArraySize() {
        return Integer.parseInt(prop.getProperty("rawsQuantityToArray"));
    }
    public static Long getDefaultId() {
        return Long.parseLong(prop.getProperty("defaultId"));
    }

    public static int getDefaultCount() {
        return Integer.parseInt(prop.getProperty("defaultCount"));
    }
}
