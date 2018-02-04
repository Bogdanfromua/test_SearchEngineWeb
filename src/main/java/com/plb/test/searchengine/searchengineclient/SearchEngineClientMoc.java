package com.plb.test.searchengine.searchengineclient;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Profile("test")
public class SearchEngineClientMoc implements SearchEngineClient {
    private Map<String, String> documentsTp = new HashMap<>();

    public void addDocument(String key, String value) {
        documentsTp.put(key, value);
    }

    public String getDocument(String key) {
        return documentsTp.get(key);
    }

    public Collection<String> findDocuments(String query) {
        //TODO
        return documentsTp.keySet();
    }
}
