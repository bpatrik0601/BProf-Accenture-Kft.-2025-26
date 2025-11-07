package com.bprof.playwright.clients;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;

import java.util.Map;

public class APIClient {
    private final APIRequestContext request;

    public APIClient(Playwright playwright, Map<String, String> defaultHeaders) {
        this.request = playwright.request().newContext(
            new APIRequest.NewContextOptions()
                .setExtraHTTPHeaders(defaultHeaders)
        );
    }


    public APIClient(Playwright playwright) {
        this(playwright, Map.of()); // Clear header map constructor
    }

    public APIResponse get(String url) {
        return request.get(url);
    }

    public APIResponse post(String url, RequestOptions options) {
        return request.post(url, options);
    }

    public APIResponse put(String url, RequestOptions options) {
        return request.put(url, options);
    }

    public APIResponse delete(String url) {
        return request.delete(url);
    }

    public void close() {
        request.dispose();
    }
}