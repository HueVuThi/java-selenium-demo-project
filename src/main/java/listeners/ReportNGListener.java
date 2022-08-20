package listeners;

import commons.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReportNGListener extends BaseTest implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        logger.info("---------- " + context.getName() + "---------------- STARTED test ----------");
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("---------- " + context.getName() + "---------------- FINISHED test ----------");
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("---------- " + result.getName() + "---------------- STARTED test ----------");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("---------- " + result.getName() + "---------------- SUCCESS test ----------");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("---------- " + result.getName() + " FAILED test ----------");
        System.setProperty("org.uncommons.reportng.escape-output", "false");

    }

    public String captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenPath = System.getProperty("user.dir") + File.separator + "ReportNGScreenShots"+
                    File.separator+ screenshotName + "_" + formater.format(calendar.getTime()) + ".png";
            FileUtils.copyFile(source, new File(screenPath));
            return screenPath;
        } catch (IOException e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
            return e.getMessage();
        }
    }



    @Override
    public void onTestSkipped(ITestResult result) {
        logger.info("---------- " + result.getName() + "---------------- SKIPPED test ----------");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.info("---------- " + result.getName() + "---------------- FAILED WITH SUCCESS PERCENTAGE test ----------");
    }

}