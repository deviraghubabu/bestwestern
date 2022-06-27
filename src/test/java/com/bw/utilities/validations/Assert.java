package com.bw.utilities.validations;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import com.bw.accelerators.ReportManager;

public class Assert {
    private Assert() {
    }

    public static void that(Validation validation) {
        ValidationStep step = validation.getStep();
        Object target = validation.getTarget();
        String failedValidation = ". Failed validation: [" + validation.getName() + "]";
        String failureMessage = validation.getMessage();

        try {
            if(validation.getTimeout() > 0L) {
                waitFor(validation);
            } else {
                step.perform();
            }

            ReportManager.pass("Assertion succeeded for [" + validation + "]");
        } catch (AssertionError var6) {
            assertionExceptions(target, failedValidation, failureMessage, var6);
        } catch (NoSuchElementException var7) {
            noSuchEleExceptions(target, failedValidation, failureMessage, var7);
        } catch (StaleElementReferenceException var8) {
            staleEleExceptions(target, failedValidation, failureMessage, var8);
        } catch (Exception var9) {
            otherExceptions(target, failedValidation, failureMessage, var9);
        }

    }

    private static void assertionExceptions(Object target, String failedValidation, String failureMessage, Throwable e) {
        String targetAsString;
        if(target instanceof By) {
            targetAsString = target.toString();
        } else if(target instanceof WebElement) {
            targetAsString = target.toString();
        } else {
            targetAsString = "unknown";
        }
        throw new AssertionError(failureMessage + failedValidation + ". Targeting [" + targetAsString + "]. Caused by inner assertion error", e);
    }

    private static void noSuchEleExceptions(Object target, String failedValidation, String failureMessage, Throwable e) {
        if(target instanceof By) {
            throw new AssertionError(failureMessage + failedValidation + ". Locator used: [" + target + "]", e);
        } else if(target instanceof WebElement) {
            throw new AssertionError(failureMessage + failedValidation + ". WebElement targeted: [" + target + "]", e);
        } else {
            throw new AssertionError(failureMessage + failedValidation, e);
        }
    }

    private static void staleEleExceptions(Object target, String failedValidation, String failureMessage, Throwable e) {
        if(target instanceof By) {
            throw new AssertionError(failureMessage + failedValidation + ". Assertion performed on a stale element reference. Locator used: [" + target + "]", e);
        } else if(target instanceof WebElement) {
            throw new AssertionError(failureMessage + failedValidation + ". Assertion performed on a stale element reference. WebElement targeted: [" + target + "]", e);
        } else {
            throw new AssertionError(failureMessage + failedValidation + ". Assertion performed on a stale element reference", e);
        }
    }

    private static void otherExceptions(Object target, String failedValidation, String failureMessage, Throwable e) {
    	if(target instanceof By) {
            throw new AssertionError(failureMessage + failedValidation + ". Assertion failure was caused by an unexpected exception targeting a By locator [" + target + "]", e);
        } else if(target instanceof WebElement) {
            throw new AssertionError(failureMessage + failedValidation + ". Assertion failure was caused by an unexpected exception targeting a WebElement [" + target + "]", e);
        } else {
            throw new AssertionError(failureMessage + failedValidation + ". Assertion failure was caused by an unknown exception", e);
        }
    }

    private static void waitFor(Validation validation) {
        long t = System.currentTimeMillis();
        long end = t + validation.getTimeout();

        while(System.currentTimeMillis() < end) {
            try {
                validation.getStep().perform();
                break;
            } catch (Error | Exception var6) {
                sleep(100L);
            }
        }

    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException var3) {
            ;
        }

    }
}
