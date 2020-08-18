package com.framework.WebDriverBinary;

import com.framework.TestUtil.PropertyReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class BrowserCapabilities {
    WebDriver driver;
    PropertyReader pro = new PropertyReader();
    Logger log = LogManager.getLogger(BrowserCapabilities.class.getName());
    ChromeOptions chromeOption = new ChromeOptions();

    public WebDriver getBrowser() {
        String browserName = pro.readProperty().getProperty("browser_name");
        if (browserName.equalsIgnoreCase("chrome")) {
            return ChromeDriverCapabilities();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            driver = FirefoxDriverCapabilities();
            return driver;
        }
        return driver;
    }

    private WebDriver ChromeDriverCapabilities() {
        try {
            log.info("Browser Selected = Google Chrome");
            log.info("Setting Up Chrome Browser");
            System.setProperty("wdm.cachePath", System.getProperty("user.dir") + File.separator + "webdriverfiles");
            WebDriverManager.chromedriver().clearCache();
            WebDriverManager.chromedriver().setup();
            log.info("ChromeDriver version: " + WebDriverManager.chromedriver().getDownloadedVersion());

            if (pro.readProperty().getProperty("headless").equalsIgnoreCase("true")) {
                log.info("Starting headless mode for chrome");
                chromeOption.addArguments("--headless");
                chromeOption.addArguments("--window-size=1366,768");
            }

            if (pro.readProperty().getProperty("mobile_emulation").equalsIgnoreCase("true")) {
                log.info("Starting mobile emulation for chrome: " + pro.readProperty().getProperty("mobile_device"));
                Map<String, String> mobileEmulation = new HashMap<>();
                String deviceName = pro.readProperty().getProperty("mobile_device");
                mobileEmulation.put("deviceName", deviceName);
                chromeOption.setExperimentalOption("mobileEmulation", mobileEmulation);
            }

            //Setting Custom Download Folder
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("download.default_directory", System.getProperty("user.dir") + File.separator + "recordings" + File.separator + "download");
            chromeOption.setExperimentalOption("prefs", prefs);
            driver = new ChromeDriver(chromeOption);
            log.info("Starting Chrome Browser");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }

    private WebDriver FirefoxDriverCapabilities() {
        return driver;
    }
}
