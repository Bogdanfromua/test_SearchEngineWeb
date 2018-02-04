package com.plb.test.searchengine.searchengineclient;

import com.plb.test.searchengine.exceptions.DocumentAddingException;

import java.util.Collection;

public interface SearchEngineClient {
    void addDocument(String key, String value) throws DocumentAddingException;
    String getDocument(String key);
    Collection<String> findDocuments(String query);
}
