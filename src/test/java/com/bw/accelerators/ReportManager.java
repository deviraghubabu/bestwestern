package com.bw.accelerators;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.bw.utilities.Log;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportManager {

    private static ExtentReports extentReports = null;
    private static ThreadLocal<ExtentTest> currentTest = new ThreadLocal<ExtentTest>();

    public static synchronized ExtentReports createReport(String suiteName) {
        if (extentReports == null) {
            String userDirectory = System.getProperty("user.dir");
            String currentTime = new SimpleDateFormat("MMM_dd_yyyy_z_HH_mm_ss").format(new Date());
            String filePath = userDirectory + File.separator + "Results" + File.separator + suiteName + "_" + currentTime + ".html";
            extentReports = new ExtentReports();
            ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter(filePath);
            extentReports.attachReporter(extentHtmlReporter);
        }
        return extentReports;
    }

    public static synchronized ExtentReports getExtentReports() {
        return extentReports;
    }

    public static synchronized ExtentTest createTest(String methodName) {
        ExtentTest test = extentReports.createTest(methodName);
        currentTest.set(test);
        return getTest();
    }

    public static synchronized ExtentTest getTest() {
        return currentTest.get();
    }

    public static synchronized void pass(String message) {
        ReportManager.pass(message, false);
    }

    public static synchronized void pass(String message, boolean createLabel) {
    	Log.info(message);
        if (createLabel) {
            Markup markupMessage = MarkupHelper.createLabel(message, ExtentColor.GREEN);
            currentTest.get().pass(markupMessage);
        } else {
            currentTest.get().pass(message);
        }
        extentReports.flush();
    }

    public static synchronized void pass(String message, String base64Screenshot) {
       pass(message, base64Screenshot, false);
    }

    public static synchronized void pass(String message, String base64Screenshot, boolean withMarkup) {
    	Log.info(message);
        String markupScreenshot = getMarkUpScreenshot(base64Screenshot);
        if (withMarkup) {
            message = getMarkUpFor(message, ExtentColor.GREEN);
        }
        message = message.concat(markupScreenshot);
        currentTest.get().pass(message);
        extentReports.flush();
    }

    public static synchronized void fail(String message) {
        fail(message, false);
    }

    public static synchronized void fail(String message, boolean createLabel) {
    	Log.fatal(message);
        if (createLabel) {
            Markup markupMessage = MarkupHelper.createLabel(message, ExtentColor.GREEN);
            currentTest.get().fail(markupMessage);
        }else{
            currentTest.get().fail(message);
        }
        extentReports.flush();
    }

    public static synchronized void fail(String message, String base64Screenshot) throws Exception {
        ReportManager.fail(message, base64Screenshot, false);
        
    }

    public static synchronized void fail(String message, String base64Screenshot, boolean withMarkup) throws Exception {
    	Log.fatal(message);
        if (withMarkup)
            message = getMarkUpFor(message, ExtentColor.RED);
        String imageMarkup = getMarkUpScreenshot(base64Screenshot);
        message = message.concat(imageMarkup);
        currentTest.get().fail(message);
        extentReports.flush();
    }

    public static synchronized void fail(Exception exception, String base64Screenshot) throws Exception {
        String message = getMarkUpStacktrace(exception);
        message = message.concat(getMarkUpScreenshot(base64Screenshot));
        currentTest.get().fail(message);
        extentReports.flush();
    }


    public static synchronized void info(String message) {
    	Log.info(message);
        currentTest.get().info(message);
        extentReports.flush();
    }

    public static synchronized void warn(String message) {
    	Log.warn(message);
        currentTest.get().info(message);
        extentReports.flush();
    }

    public static synchronized void warn(String message, String base64Screenshot) {
    	Log.warn(message);
        message = getMarkUpFor(message, ExtentColor.YELLOW);
        message = message.concat(getMarkUpScreenshot(base64Screenshot));
        currentTest.get().info(message);
        extentReports.flush();
    }

    public static synchronized void info(String message, String base64Screenshot){
    	Log.info(message);
        message = message.concat(getMarkUpScreenshot(base64Screenshot));
        currentTest.get().info(message);
    }

    public static synchronized void info(String message, boolean withMarkup) {
    	Log.info(message);
        if (withMarkup) {
            Markup markupMessage = MarkupHelper.createCodeBlock(message, CodeLanguage.JSON);
            currentTest.get().info(markupMessage);
        } else
            currentTest.get().info(message);
        extentReports.flush();
    }

    public static synchronized void publishReport() {
        extentReports.flush();
    }


    private static synchronized String getMarkUpScreenshot(String base64Screenshot) {
        return "<a href=\"data:image/png;base64," +
                base64Screenshot +
                "\" data-featherlight=\"image\"><span class=\"label grey badge white-text text-white\">view image</span></a>";
    }

    private static synchronized String getMarkUpStacktrace(Exception t) {
        return "<textarea disabled class=\"code-block maxxed\">" +
                ExceptionUtils.getStackTrace(t) +
                "<br></textarea>";
    }

    private static synchronized String getMarkUpFor(String message, ExtentColor extentColor) {
        return "<span class='badge white-text "
                .concat(extentColor.toString().toLowerCase())
                .concat("'>")
                .concat(message)
                .concat("</span><br>");
    }


}
