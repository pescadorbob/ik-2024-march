package com.brent.systemdesign;

public interface UrlRepository {
    void add(String number, String url);

    String get(String encodedUrl);
}
