package com.framework.Base;

import com.framework.TestUtil.EReport;
import com.framework.TestUtil.ExcelReader;
import com.framework.TestUtil.PropertyReader;
import com.framework.WDController.WebDriverController;
import com.framework.WebDriverBinary.BrowserCapabilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.util.ArrayList;


public class BaseClass {

    Logger log = LogManager.getLogger(BaseClass.class.getName());
    PropertyReader pro = new PropertyReader();
    ExcelReader excelData = new ExcelReader();
    ArrayList<String> dataList = new ArrayList<>();
    WebDriver driver;
    WebDriverController executeCommand;
    EReport report;
    BrowserCapabilities browser = new BrowserCapabilities();

    @DataProvider(name = "input")
    public Object[] inputDataObject() {
        return excelData.getData();
    }

    @BeforeSuite
    public void beforeSuite() {
        log.info("Starting Test Execution");
        log.info("Automation running on " + pro.readProperty().getProperty("base_url"));
        report = new EReport();
    }

    @BeforeClass
    public void beforeClassMethod() {
        driver = browser.getBrowser();
        driver.get(pro.getBaseURL());
        driver.manage().window().maximize();
        executeCommand = new WebDriverController(driver);
    }

    @BeforeMethod
    public void beforeTestMethod() throws InterruptedException {
        Thread.sleep(650);
    }

    @Test(dataProvider = "input")
    public void testMethod(String testID, String testScenario, String testDescription, String keyword, String object,
                           String objectName, String testData) {
        dataList.add(testID);// 0
        dataList.add(testScenario);// 1
        dataList.add(testDescription);// 2
        dataList.add(keyword);// 3
        dataList.add(object);// 4
        dataList.add(objectName);// 5
        dataList.add(testData.trim());// 6
        log.info("==========================Executing Test Case ID - " + testID + "==========================");
        log.info("[" + testScenario + "] -> [" + testDescription + "]");
        log.info("Keyword To Perform: " + keyword);
        report.createTest(testID + ". " + testScenario + " : " + testDescription);
        executeCommand.commandExecutor(dataList);
    }

    @AfterMethod
    public void afterTestMethod(ITestResult result) {

        if (result.getStatus() == ITestResult.SUCCESS) {
            log.info("Test Case Passed");
            report.passTest();
        } else if (result.getStatus() == ITestResult.FAILURE) {
            log.info("Test Case Failed");
            String screenShotPath = executeCommand.screenShot();
            System.out.println(screenShotPath);
            report.failTest(screenShotPath, result);
        }
        dataList.clear();
    }

    @AfterClass
    public void afterClassMethod() {

    }

    @AfterSuite
    public void afterSuiteMethod() {
        report.flush();
        driver.quit();
    }
}
