package com.bw.tests.base;

public class APIBaseTest extends BaseTest {

    public String getConstructedMessage(String responseStatusCode, String responseBody) {
         String message = "{"
                .concat("'Status Code':'").concat(responseStatusCode).concat("',")
                .concat("'Response':").concat(responseBody).concat("}");
         return message;
    }
}
