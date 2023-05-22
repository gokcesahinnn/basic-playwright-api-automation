package com.qa.api.tests;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class DeleteApiCall {
    Playwright playwright;
    APIRequestContext request;

    @BeforeTest
    public void setup() {
        // Request
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");

        playwright = Playwright.create();
        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://petstore.swagger.io")
                .setExtraHTTPHeaders(headers));
    }

    @Test
    public void deletePet() {
        System.out.println("********* Delete Pet *********");

        APIResponse apiResponse = request.delete("/v2/pet/9223372036854775440");

        // Response Status Code
        int statusCode = apiResponse.status();
        System.out.println("Status Code: " + statusCode);
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(apiResponse.ok(), true);

        // Response Status Text
        String statusResText = apiResponse.statusText();
        System.out.println("Status Response Text: " + statusResText);

        // Api Response Plain Text
        System.out.println("Api Response: " + apiResponse.text());
    }
}