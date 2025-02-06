package com.brent.systemdesign;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository implements UrlRepository {

    Map<String,String> urls;

    public InMemoryRepository() {
        this.urls = new HashMap<>();
    }

    @Override
    public void add(String number, String url) {
        urls.put(number,url);
    }

    @Override
    public String get(String encodedUrl) {
        return urls.get(encodedUrl);
    }
}
