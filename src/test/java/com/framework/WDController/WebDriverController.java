package com.framework.WDController;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class WebDriverController {

    Logger log = LogManager.getLogger(WebDriverController.class.getName());
    WebDriverWait wait;
    WebDriver driver;
    String parentWindow;
    Actions action;
    JavascriptExecutor jsExecute;

    public WebDriverController(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 15);
        parentWindow = driver.getWindowHandle();
        action = new Actions(driver);
        jsExecute = (JavascriptExecutor) driver;
    }

    public void commandExecutor(ArrayList<String> dataList) {
        String keyword = dataList.get(3).toLowerCase();

        switch (keyword) {

            case "click":
                clickElement(dataList.get(4), dataList.get(5));
                break;

            case "entertext":
                enterText(dataList.get(4), dataList.get(5), dataList.get(6));
                break;

            case "acceptalert":
                acceptBrowserAlert();
                break;

            case "dismissalert":
                dismissBrowserAlert();
                break;

            case "back":
                back();
                break;

            case "closewindows":
                closeWindows();
                break;

            case "parentwindow":
                parentWindowClose();
                break;

            case "switchwindow":
                switchWindow();
                break;

            case "pause":
                pause(dataList.get(6));
                break;

            case "elementdisplayed":
                elementDisplayed(dataList.get(4), dataList.get(5));
                break;

            case "elementnotdisplayed":
                elementNotDisplayed(dataList.get(4), dataList.get(5));
                break;

            case "elementenabled":
                elementEnabled(dataList.get(4), dataList.get(5));
                break;

            case "elementnotenabled":
                elementNotEnabled(dataList.get(4), dataList.get(5));
                break;

            case "elementselected":
                elementSelected(dataList.get(4), dataList.get(5));
                break;

            case "elementnotselected":
                elementNotSelected(dataList.get(4), dataList.get(5));
                break;

            case "elementclickable":
                elementClickable(dataList.get(4), dataList.get(5));
                break;

            case "elementnotclickable":
                elementNotClickable(dataList.get(4), dataList.get(5));
                break;

            case "forward":
                forward();
                break;

            case "elementcontainstext":
                elementContainsText(dataList.get(4), dataList.get(5), dataList.get(6));
                break;

            case "dropdownvalue":
                selectDropDownByValue(dataList.get(4), dataList.get(5), dataList.get(6));
                break;

            case "dropdownindex":
                selectDropDownByIndex(dataList.get(4), dataList.get(5), dataList.get(6));
                break;

            case "dropdowntext":
                selectDropDownByText(dataList.get(4), dataList.get(5), dataList.get(6));
                break;

            case "switchwindowtitle":
                switchWindowWithTitle(dataList.get(6));
                break;

            case "presstab":
                tabPress();
                break;

            case "verifyalerttext":
                verifyAlertText(dataList.get(6));
                break;

            case "maximize":
                maximizeScreen();
                break;

            case "mousehover":
                mouseHover(dataList.get(4), dataList.get(5));
                break;

            case "mousehoverbyoffset":
                mouseHoverByOffset(dataList.get(4), dataList.get(5), dataList.get(6));
                break;

            case "refresh":
                refresh();
                break;

            case "navigate":
                navigate(dataList.get(6));
                break;

            case "rightclickopen":
                rightClickOpen(dataList.get(4), dataList.get(5));
                break;

            case "newtabswitch":
                newTabSwitch();
                break;

            case "addcookie":
                addCookie(dataList.get(6));
                break;

            case "doubleclick":
                doubleClick(dataList.get(4), dataList.get(5));
                break;

            case "clearcookiebyname":
                clearCookieByName(dataList.get(6));
                break;

            case "clearallcookies":
                clearAllCookies();
                break;

            case "movehorizontal":
                moveHorizontal(dataList.get(4), dataList.get(5), dataList.get(6));
                break;

            case "verifyurlcontainstext":
                verifyUrlContainsText(dataList.get(6));
                break;

            default:
                log.info("No keyword matching. Please provide a correct keyword.");
                Assert.fail();
                break;
        }

    }

    public void clickElement(String object, String objectType) {
        log.info("Clicking the element: (" + object + " & " + objectType + ")");
        getElement(object, objectType).click();
    }

    public void enterText(String object, String objectType, String inputData) {
        log.info("Entering text " + inputData + " on the element: (" + object + " & " + objectType + ")");
        getElement(object, objectType).clear();
        getElement(object, objectType).sendKeys(inputData);
    }

    public void acceptBrowserAlert() {
        log.info("Accepting the alert box");
        driver.switchTo().alert().accept();
    }

    public void dismissBrowserAlert() {
        log.info("Accepting the alert box");
        driver.switchTo().alert().dismiss();
    }

    public void back() {
        log.info("Navigating back");
        driver.navigate().back();
    }

    public void closeWindows() {
        log.info("Closing other windows than the current one");
        String currentWindow = driver.getWindowHandle();
        Set<String> windowsList = driver.getWindowHandles();
        for (String window : windowsList) {
            if (!window.equals(currentWindow)) {
                driver.close();
            }
        }
    }

    public void parentWindowClose() {
        log.info("Closing other windows than the parent window");
        Set<String> windowsList = driver.getWindowHandles();
        for (String window : windowsList) {
            if (!window.equals(parentWindow)) {
                driver.close();
            }
        }
        driver.switchTo().window(parentWindow);
    }

    public void switchWindow() {
        log.info("Switching the window");
        String currentWindow = driver.getWindowHandle();
        Set<String> windowsList = driver.getWindowHandles();
        for (String window : windowsList) {
            if (!window.equals(currentWindow)) {
                currentWindow = window;
                break;
            }
        }
        driver.switchTo().window(currentWindow);
    }

    public void elementDisplayed(String object, String objectType) {
        boolean check = driver.findElement(getElementType(object, objectType)).isDisplayed();
        if (!check) {
            log.info("Element is not displayed: Assertion Failed.");
            Assert.fail();
        } else if (check) {
            log.info("Element is displayed:Assertion Passed.");
            Assert.assertTrue(true);
        }
    }

    public void elementNotDisplayed(String object, String objectType) {
        boolean check = driver.findElement(getElementType(object, objectType)).isDisplayed();
        if (!check) {
            log.info("Element is not displayed: Assertion Passed.");
            Assert.assertTrue(true);
        } else if (check) {
            log.info("Element is displayed: Assertion Failed.");
            Assert.fail();
        }
    }

    public void elementEnabled(String object, String objectType) {
        boolean check = driver.findElement(getElementType(object, objectType)).isEnabled();
        if (check) {
            log.info("Element is enabled: Assertion is passed");
            Assert.assertTrue(true);
        } else if (!check) {
            log.info("Element is not enabled: Assertion is failed");
            Assert.fail();
        }
    }

    public void elementNotEnabled(String object, String objectType) {
        boolean check = driver.findElement(getElementType(object, objectType)).isEnabled();
        if (check) {
            log.info("Element is enabled: Assertion is failed");
            Assert.fail();
        } else if (!check) {
            log.info("Element is not enabled: Assertion is passed");
            Assert.assertTrue(true);
        }
    }

    public void elementSelected(String object, String objectType) {
        boolean check = driver.findElement(getElementType(object, objectType)).isSelected();
        if (check) {
            log.info("Element is selected: Assertion is passed");
            Assert.assertTrue(true);
        } else if (!check) {
            log.info("Element is not selected: Assertion is failed");
            Assert.fail();
        }
    }

    public void elementNotSelected(String object, String objectType) {
        boolean check = driver.findElement(getElementType(object, objectType)).isSelected();
        if (check) {
            log.info("Element is selected: Assertion is failed");
            Assert.fail();
        } else if (!check) {
            log.info("Element is not Selected: Assertion is passed");
            Assert.assertTrue(true);
        }
    }

    public void elementClickable(String object, String objectType) {
        if (ExpectedConditions.elementToBeClickable(getElement(object, objectType)) != null) {
            log.info("Element is clickable: Assertion is passed");
            Assert.assertTrue(true);
        } else if (ExpectedConditions.elementToBeClickable(getElement(object, objectType)) == null) {
            log.info("Element is not clickable: Assertion is failed");
            Assert.fail();
        }
    }

    public void elementNotClickable(String object, String objectType) {
        if (ExpectedConditions.elementToBeClickable(getElement(object, objectType)) != null) {
            log.info("Element is clickable: Assertion is failed");
            Assert.fail();
        } else if (ExpectedConditions.elementToBeClickable(getElement(object, objectType)) == null) {
            log.info("Element is not clickable: Assertion is passed");
            Assert.assertTrue(true);
        }
    }

    public void forward() {
        log.info("Navigating forward");
        driver.navigate().forward();
    }

    public void elementContainsText(String object, String objectType, String inputData) {
        String elementText = driver.findElement(getElementType(object, objectType)).getText();
        if (elementText.equals(inputData)) {
            log.info("Element contains the given text");
            Assert.assertTrue(true);
        } else {
            log.info("Element doesn't contains the given text. Actual Value is: " + elementText);
            new Exception().printStackTrace();
            Assert.fail();
        }
    }

    public void navigate(String inputData) {
        log.info("Navigating to the URL: " + inputData);
        driver.navigate().to(inputData);
    }

    public void refresh() {
        log.info("Refreshing the current page");
        driver.navigate().refresh();
    }

    public void pause(String inputData) {
        try {
            log.info("Pausing the script for " + inputData + " seconds");
            Thread.sleep(Long.parseLong(inputData) * 1000);
        } catch (Exception e) {
            log.info("Incorrect format in test data coloum.");
        }
    }

    public void selectDropDownByValue(String object, String objectType, String inputData) {
        try {
            Select select = new Select(getElement(object, objectType));
            log.info("Selecting dropdown value: " + inputData);
            select.selectByValue(inputData);
        } catch (Exception e) {
            log.info("Unable to perform the action. Please check the element or dropdown values");
        }
    }

    public void selectDropDownByIndex(String object, String objectType, String inputData) {
        try {
            Select select = new Select(getElement(object, objectType));
            log.info("Selecting dropdown value: " + inputData);
            select.selectByIndex(Integer.parseInt(inputData));
        } catch (Exception e) {
            log.info("Unable to perform the action. Please check the element or dropdown values");
        }
    }

    public void selectDropDownByText(String object, String objectType, String inputData) {
        try {
            Select selectTest = new Select(getElement(object, objectType));
            log.info("Selecting dropdown value: " + inputData);
            selectTest.selectByVisibleText(inputData);
            System.out.println(inputData);
        } catch (Exception e) {
            log.info("Unable to perform the action. Please check the element or dropdown values");
        }
    }

    public void switchWindowWithTitle(String inputData) {
        log.info("Switching to window with title: " + inputData);
        driver.switchTo().window(inputData);
    }

    public void tabPress() {
        try {
            action.sendKeys(Keys.TAB).build().perform();
            log.info("Pressing Tab Key");
        } catch (Exception e) {
            log.info("Unable to perform the action");
            e.printStackTrace();
        }

    }

    public String screenShot() {
        try {
            Date d = new Date();
            String fileName = d.toString().replaceAll(" ", "_").replaceAll(":", "_") + ".jpg";
            File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenShot,
                    new File(System.getProperty("user.dir") + "//recordings//screenshots//" + fileName));
            log.info("Screenshot Captured");
            return System.getProperty("user.dir") + "//recordings//screenshots//" + fileName;
        } catch (Exception e) {
            log.info("Unable to complete the screenshot process");
            return null;
        }

    }

    public void verifyAlertText(String inputData) {
        if (inputData.equals(driver.switchTo().alert().getText())) {
            log.info("Assertion Passed. Alert field contains " + inputData);
            Assert.assertTrue(true);
        } else {
            log.info("Assertion Failed. Alert field doesn't contain the given text");
            Assert.fail();
        }

    }

    public void maximizeScreen() {
        driver.manage().window().maximize();
    }

    public void mouseHover(String object, String objectType) {
        try {
            log.info("Performing mouse hover to provided element");
            action.moveToElement(getElement(object, objectType));
            Assert.assertTrue(true);
        } catch (Exception e) {
            log.info("Unable to perform the mouse hover");
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void mouseHoverByOffset(String object, String objectType, String testData) {
        try {
            String[] data = testData.split("|");
            int xOffset = Integer.parseInt(data[0]);
            int yOffset = Integer.parseInt(data[1]);
            log.info("Performing mouse hover to provided element with given offset");
            action.moveToElement(getElement(object, objectType), xOffset, yOffset);
            Assert.assertTrue(true);
        } catch (Exception e) {
            log.info("Unable to perform the mouse hover");
            Assert.fail();
        }
    }

    public void rightClickOpen(String object, String objectType) {
        try {
            log.info("Performing right click and opening in new tab");
            getElement(object, objectType).sendKeys(Keys.chord(Keys.COMMAND, Keys.ENTER));
            Assert.assertTrue(true);
        } catch (Exception e) {
            log.info("Unable to perform the right click action");
            Assert.fail();
        }
    }

    public void newTabSwitch() {
        try {
            jsExecute.executeScript("window.open('about:blank','_blank');");
            switchWindow();
            Assert.assertTrue(true);
        } catch (Exception e) {
            log.info("Unable to open a new tab");
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void addCookie(String inputData) {
        try {
            String[] cookiePair = inputData.split("|");
            log.info("Adding following cookie with name: " + cookiePair[0] + "and value: " + cookiePair[1]);
            driver.manage().addCookie(new Cookie(cookiePair[0], cookiePair[1]));
            Assert.assertTrue(true);
        } catch (Exception e) {
            log.error("Unable to add cookie");
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void doubleClick(String object, String objectType) {
        try {
            log.info("Performing double click on the element");
            action.doubleClick(getElement(object, objectType));
            Assert.assertTrue(true);
        } catch (Exception e) {
            log.error("Unable to peform double click on given element");
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void clearCookieByName(String inputData) {
        try {
            log.info("Deleting cookie by name: " + inputData);
            driver.manage().deleteCookieNamed(inputData);
            Assert.assertTrue(true);
        } catch (Exception e) {
            log.error("Unable to delete cookie");
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void clearAllCookies() {
        try {
            log.info("Deleting all cookies in current browser");
            driver.manage().deleteAllCookies();
            Assert.assertTrue(true);
        } catch (Exception e) {
            log.error("Unable to delete all the cookies");
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void moveHorizontal(String object, String objectType, String inputData) {
        try {
            log.info("Sliding the element horizontally: " + inputData);
            action.moveToElement(getElement(object, objectType), Integer.parseInt(inputData), 0);
            Assert.assertTrue(true);
        } catch (Exception e) {
            log.error("Unable to perform the action on given element");
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void verifyUrlContainsText(String inputData) {
        if (driver.getCurrentUrl().contains(inputData)) {
            log.info("Current URL: " + driver.getCurrentUrl() + " contains:" + inputData);
            Assert.assertTrue(true);
        } else {
            log.error("Current URL doesn't contains the given text");
            Assert.fail();
        }
    }

    public WebElement getElement(String object, String objectType) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(getElementType(object, objectType)));
    }

    public By getElementType(String object, String objectType) {
        if (object.equalsIgnoreCase("xpath")) {
            return By.xpath(objectType);
        } else if (object.equalsIgnoreCase("css")) {
            return By.cssSelector(objectType);
        } else if (object.equalsIgnoreCase("id")) {
            return By.id(objectType);
        } else if (object.equalsIgnoreCase("name")) {
            return By.name(objectType);
        } else if (object.equalsIgnoreCase("classname")) {
            return By.className(objectType);
        } else if (object.equalsIgnoreCase("linktext")) {
            return By.linkText(objectType);
        } else if (object.equalsIgnoreCase("partiallinktext")) {
            return By.partialLinkText(objectType);
        } else if (object.equalsIgnoreCase("tag")) {
            return By.tagName(objectType);
        } else {
            log.error("Incorrect Object Type");
            return null;
        }
    }
}
