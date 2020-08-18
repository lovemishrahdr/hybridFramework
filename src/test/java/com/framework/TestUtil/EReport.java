package com.framework.TestUtil;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestResult;

import java.io.File;
import java.util.Arrays;

public class EReport {

    ExtentSparkReporter extentSparkReporter;
    ExtentReports extentReports;
    ExtentTest extentTest;

    public EReport() {
        extentSparkReporter = new ExtentSparkReporter(new File(System.getProperty("user.dir") + "/recordings/report.html"));
        extentSparkReporter.config().setDocumentTitle("Automation Run Report");
        extentSparkReporter.config().setReportName("Automation Run Result");
        extentSparkReporter.config().setTheme(Theme.DARK);
        extentSparkReporter.config().setTimelineEnabled(true);
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
    }

    public void createTest(String testName) {
        extentTest = extentReports.createTest(testName);
    }

    public void passTest() {
        extentTest.log(Status.PASS, "Test Case Passed Successfully");
    }

    public void failTest(String screenShotPath, ITestResult result) {
        String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
        extentTest.fail("<details><summary><b>Exception Occurred. Click to see details</b></summary>"
                + exceptionMessage.replaceAll(",", "<br>") + "</details>\n");
        extentTest.fail("<b>Screenshot of failure</b>",
                MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
    }

    public void flush() {
        extentReports.flush();
    }
}
