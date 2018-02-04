package com.plb.test.searchengine;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Profile("default")
public class SearchEngineRestClient implements SearchEngineClient {
    @Override
    public void addDocument(String key, String value) {
        //TODO
    }

    @Override
    public String getDocument(String key) {
        //TODO
        return null;
    }

    @Override
    public Collection<String> findDocuments(String query) {
        //TODO
        return null;
    }
}
