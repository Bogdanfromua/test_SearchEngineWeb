package com.plb.test.searchengine;


import java.util.Collection;

public interface SearchEngineClient {
    void addDocument(String key, String value);
    String getDocument(String key);
    Collection<String> findDocuments(String query);
}
