package com.qa.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class GetApiCall {

    Playwright playwright;
    APIRequest request;
    APIRequestContext requestContext;

    @BeforeTest
    public void setup() {
        // Request
        playwright = Playwright.create();
        request = playwright.request();
        requestContext = request.newContext();
    }


    @Test
    public void getSpecificUserApiTest() {
        System.out.println("********* Get Specific User *********");
        APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setQueryParam("id", 879284)
                        .setQueryParam("status", "active")
        );

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

    @Test
    public void getUsersApiTest() throws IOException {
        System.out.println("********* Get All User *********");
        APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users");

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

        // Api Response Pretty Text
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
        String jsonPrettyResponse = jsonResponse.toPrettyString();
        System.out.println("Response: " + jsonPrettyResponse);

        // Response Url
        System.out.println("Response URL: " + apiResponse.url());

        // Response Headers
        Map<String, String> headersMap = apiResponse.headers();
        System.out.println("Response Headers: " + headersMap);
        Assert.assertEquals(headersMap.get("content-type"), "application/json; charset=utf-8");
    }

    @AfterTest
    public void tearDown() {
        playwright.close();
    }

}
