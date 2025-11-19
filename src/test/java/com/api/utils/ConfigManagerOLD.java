package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigManagerOLD {

    // why we required ConfigManagerOLD class?
    // 1. To provide a centralized way to access configuration settings
    // 2. To implement Singleton pattern to ensure only one instance handles
    // 3. Responsible for loading and providing access to properties files
    // 4. Retrieve configuration values based on keys
    // 5. It's a Utility class to manage configuration settings and has lots of static methods
    // 6. Objects creation is restricted for this class because of private constructor

    // read configuration settings from a file or environment variables
    private static Properties prop = new Properties();

    private ConfigManagerOLD() {
        //private constructor to prevent instantiation
    }

    // Operation of loading properties file in the memory
    //because its static block it will be executed only once when the class is loaded
    static {
        //Q. How do ensure your code is platform independent? - File.separator
        File configFile = new File(System.getProperty("user.dir")
                + File.separator + "src" + File.separator + "test" + File.separator
                + "resources" + File.separator + "config" + File.separator + "config.properties");

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(configFile);
            prop.load(fileReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}
