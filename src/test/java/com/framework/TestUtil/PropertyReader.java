package com.framework.TestUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {
    Logger log = LogManager.getLogger(PropertyReader.class.getName());

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

    public String getBaseURL() {
        String baseURL;
        if (System.getenv("BASE_URL") != null && !System.getenv("BASE_URL").isEmpty()) {
            baseURL = System.getenv("BASE_URL");
        } else {
            baseURL = readProperty().getProperty("BASE_URL");
        }
        return baseURL;
    }

    public String getBrowser() {
        String browser;
        if (System.getenv("BROWSER_NAME") != null && !System.getenv("BROWSER_NAME").isEmpty()) {
            browser = System.getenv("BROWSER_NAME");
        } else {
            browser = readProperty().getProperty("BROWSER_NAME");
        }
        return browser;
    }

    public boolean isHeadless() {
        boolean isHeadless;
        if (System.getenv("HEADLESS") != null && !System.getenv("HEADLESS").isEmpty()) {
            isHeadless = System.getenv("HEADLESS").equalsIgnoreCase("true");
        } else {
            isHeadless = readProperty().getProperty("HEADLESS").equalsIgnoreCase("true");
        }
        return isHeadless;
    }

    public boolean isMobileEmulation() {
        boolean mobileEmulation;
        if (System.getenv("MOBILE_EMULATION") != null && !System.getenv("MOBILE_EMULATION").isEmpty()) {
            mobileEmulation = System.getenv("MOBILE_EMULATION").equalsIgnoreCase("true");
        } else {
            mobileEmulation = readProperty().getProperty("MOBILE_EMULATION").equalsIgnoreCase("true");
        }
        return mobileEmulation;
    }

    public String getMobileDeviceName() {
        String deviceName;
        if (System.getenv("MOBILE_DEVICE") != null && !System.getenv("MOBILE_DEVICE").isEmpty()) {
            deviceName = System.getenv("MOBILE_DEVICE");
        } else {
            deviceName = readProperty().getProperty("MOBILE_DEVICE");
        }
        return deviceName;
    }

    public String getWorkBookName() {
        String workBookName;
        if (System.getenv("BOOK_NAME") != null && !System.getenv("BOOK_NAME").isEmpty()) {
            workBookName = System.getenv("BOOK_NAME");
        } else {
            workBookName = readProperty().getProperty("BOOK_NAME");
        }
        return workBookName;
    }

    public String getSheetName() {
        String sheetName;
        if (System.getenv("SHEET_NAME") != null && !System.getenv("SHEET_NAME").isEmpty()) {
            sheetName = System.getenv("SHEET_NAME");
        } else {
            sheetName = readProperty().getProperty("SHEET_NAME");
        }
        return sheetName;
    }


}

