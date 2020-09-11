package com.framework.WebDriverBinary;

import com.framework.TestUtil.PropertyReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author love.mishra
 */

public class BrowserCapabilities {
    WebDriver driver;
    PropertyReader pro = new PropertyReader();
    Logger log = LogManager.getLogger(BrowserCapabilities.class.getName());
    ChromeOptions chromeOption = new ChromeOptions();
    FirefoxOptions firefoxOptions = new FirefoxOptions();


    public WebDriver getBrowser() {
        WebDriverManager.globalConfig().setClearResolutionCache(false);
        String browserName = pro.getBrowser();
        if (browserName.equalsIgnoreCase("chrome")) {
            return ChromeDriverCapabilities();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            return FirefoxDriverCapabilities();
        } else if (browserName.equalsIgnoreCase("edge")) {
            return EdgeCapabilities();
        }
        return driver;
    }

    private WebDriver ChromeDriverCapabilities() {
        try {

            log.info("Browser Selected = Google Chrome");
            log.info("Setting Up Chrome Browser");
            System.setProperty("wdm.cachePath", System.getProperty("user.dir") + File.separator + "webdriverfiles");
            WebDriverManager.chromedriver().setup();

            log.info("ChromeDriver version: " + WebDriverManager.chromedriver().getDownloadedDriverVersion());

            if (pro.isHeadless()) {
                log.info("Starting headless mode for chrome");
                chromeOption.addArguments("--headless");
                chromeOption.addArguments("--window-size=1366,768");
            }

            if (pro.isMobileEmulation()) {
                log.info("Starting mobile emulation for chrome: " + pro.readProperty().getProperty("mobile_device"));
                Map<String, String> mobileEmulation = new HashMap<>();
                String deviceName = pro.getMobileDeviceName();
                mobileEmulation.put("deviceName", deviceName);
                chromeOption.setExperimentalOption("mobileEmulation", mobileEmulation);
            }

            //Setting Custom Download Folder
            Map<String, Object> prefs = new HashMap<>();
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
        try {
            log.info("Browser Selected = Firefox");
            log.info("Setting Up Firefox Browser");
            System.setProperty("wdm.cachePath", System.getProperty("user.dir") + File.separator + "webdriverfiles");
            WebDriverManager.firefoxdriver().setup();
            log.info("Firefox version: " + WebDriverManager.firefoxdriver().getDownloadedDriverVersion());

            if (pro.isHeadless()) {
                log.info("Starting headless mode for chrome");
                firefoxOptions.addArguments("--headless");
                firefoxOptions.addArguments("--window-size=1366,768");
            }

            //Setting Custom Download Folder
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("browser.download.folderList", 2);
            profile.setPreference("browser.download.dir", System.getProperty("user.dir") + File.separator + "recordings" + File.separator + "download");
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                    "text/csv,application/java-archive, application/x-msexcel,application/excel,application/vnd.openxmlformats-officedocument.wordprocessingml.document," +
                            "application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/vnd.microsoft.portable-executable");

            firefoxOptions.setProfile(profile);
            driver = new FirefoxDriver(firefoxOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driver;

    }

    private WebDriver EdgeCapabilities() {
        try {
            log.info("Browser Selected = Microsoft Edge");
            log.info("Setting Up Microsoft Edge Browser");
            System.setProperty("wdm.cachePath", System.getProperty("user.dir") + File.separator + "webdriverfiles");
            WebDriverManager.edgedriver().setup();
            log.info("Microsoft Edge version: " + WebDriverManager.edgedriver().getDownloadedDriverVersion());
            driver = new EdgeDriver();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }
}
