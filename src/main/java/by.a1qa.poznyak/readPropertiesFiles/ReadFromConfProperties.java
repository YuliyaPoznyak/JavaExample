package by.a1qa.poznyak.readPropertiesFiles;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadFromConfProperties {

    public static FileInputStream fileInputStream;
    public static Properties prop;
    public static final String WHERE_IS_PROPERTIES = "src/main/resources/conf.properties";

    public static void prepareConfigData() {
        try {
            prop = new Properties();
            fileInputStream = new FileInputStream(WHERE_IS_PROPERTIES);
            prop.load(fileInputStream);
        } catch (IOException e) {
            System.out.println("There is no such file with configuration data");
        }
    }

    public static String getURL() {
        return prop.getProperty("website");
    }

    public static String getUrlSQL() {
        return prop.getProperty("urlSQL");
    }

    public static String getUsernameSQL() {
        return prop.getProperty("usernameSQL");
    }

    public static String getPasswordSQL() {
        return prop.getProperty("passwordSQL");
    }

    public static String getAuthorName() {
        return prop.getProperty("authorName");
    }

    public static String getAuthorLogin() {
        return prop.getProperty("authorLogin");
    }

    public static String getAuthorEmail() {
        return prop.getProperty("authorEmail");
    }

    public static String getSessionKey() {
        return prop.getProperty("sessionKey");
    }

    public static String getProjectName() {
        return prop.getProperty("projectName");
    }

    public static String getBrowserName() {
        return prop.getProperty("browser");
    }

    public static String getEnv() {
        return prop.getProperty("env");
    }
   }
