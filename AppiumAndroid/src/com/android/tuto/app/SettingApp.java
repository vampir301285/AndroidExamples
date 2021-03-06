package com.android.tuto.app;

import io.appium.java_client.AppiumDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SettingApp {
    
    private String url = "http://127.0.0.1:4723/wd/hub";

    private String deviceId = "";

    private String swVersion = "";

    private AppiumDriver driver = null;

    private DesiredCapabilities capabilities;

    /**
     * @return the driver
     */
    public AppiumDriver getDriver() {
        return driver;
    }

    public SettingApp(String deviceId, String swVersion, String url) {
        super();
        this.deviceId = deviceId;
        this.swVersion = swVersion;
        /** Setup */
        capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        //capabilities.setCapability(CapabilityType.PLATFORM, "Android");
        capabilities.setCapability(CapabilityType.VERSION, this.swVersion);
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceType", "Phone");
        capabilities.setCapability("appPackage", "com.android.settings");
        capabilities.setCapability("appActivity", ".Settings");
        // for real device
        capabilities.setCapability("device ID", this.deviceId);
        this.url = url;
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * to open setting application
     * 
     * @throws MalformedURLException
     */
    public void openSettingApp() throws MalformedURLException {
        driver = new AppiumDriver(new URL(url), capabilities);
    }

    /**
     * Precondition: setting application is open.
     * 
     * This function will turn on Bluetooth on the device and make it visible for searching.
     * 
     * @throws MalformedURLException
     * @throws InterruptedException
     */
    public void turnOnBT() throws MalformedURLException, InterruptedException {
        // turn on Bluetooth
        WebElement bluetooth = driver.findElement(By.name("Bluetooth"));
        bluetooth.click();
        WebElement switchOnOff = driver.findElement(By.className("android.widget.Switch"));
        if (!switchOnOff.getText().equalsIgnoreCase("on")) {
            switchOnOff.click();
            // wait for the screen
            waitForVisible(By.xpath("//android.widget.TextView[contains(@text,'visible to') or contains(@text,'Visible to')]"), 20);
        }
        // make the device visible for searching
        List<WebElement> textViews = driver.findElements(By.className("android.widget.TextView"));
        for (WebElement el : textViews) {
            if (el.getText().contains("Only visible to paired devices") || el.getText().contains("Not visible to other")) {
                el.click();
                break;
            }
        }
        Thread.sleep(3000);
    }

    /**
     * Precondition: scontains(@text,'visible to')etting application is opened.
     * 
     * @throws MalformedURLException
     * @throws InterruptedException
     */
    public void enbaleWiFi() throws InterruptedException {
        /** run steps */
        WebElement wifi = driver.findElement(By.name("Wi-Fi"));
        wifi.click();

        WebElement switchOnOff = driver.findElement(By.className("android.widget.Switch"));
        if (!switchOnOff.getText().equalsIgnoreCase("ON")) {
            switchOnOff.click();
            waitForVisible(By.xpath("//android.widget.Switch[contains(@text,'ON')]"), 20);
        }
        Thread.sleep(3000);
    }

    /**
     * precondition: the Bluetooth paring request dialog is on the screen.
     */
    public void acceptBTPairingrequest() {
        WebElement ok = driver.findElement(By.xpath("//android.widget.Button[contains(@text,'OK') or contains(@text,'Pair')]"));
        ok.click();

    }

    /**
     * precondition: the Bluetooth paring request dialog is on the screen.
     */
    public void rejectBTPairingrequest() {
        WebElement ok = driver.findElement(By.xpath("//android.widget.Button[contains(@text,'Cancel')]"));
        ok.click();
    }

    /**
     * wait for the screen till the specific element appears
     * 
     * @param by
     *            the element should be displayed on the screen
     * @param waitTime
     *            the waiting time in second
     */
    public void waitForVisible(final By by, int waitTime) {
        int timeoutInSeconds = 10;
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        for (int attempt = 0; attempt < waitTime; attempt++) {
            try {
                driver.findElement(by);
                break;
            } catch (Exception e) {
                driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            }
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
