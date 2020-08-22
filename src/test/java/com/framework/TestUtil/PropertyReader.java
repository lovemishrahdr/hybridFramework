package com.framework.TestUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {
    Logger log = LogManager.getLogger(PropertyReader.class.getName());
    private String baseURL;


    public Properties readProperty() {
        Properties pro = new Properties();
        try {
            FileInputStream fileInput = new FileInputStream("./datafiles/input.properties");
            pro.load(fileInput);
            return pro;
        } catch (Exception e) {
            log.error("Unable to read properties file. Please check the file or it's contents");
            return pro;
        }
    }
}

