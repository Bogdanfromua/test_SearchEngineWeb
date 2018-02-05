package com.plb.test.searchengine.searchengineclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@Component
@Profile("default")
public class SearchEngineRestClient implements SearchEngineClient {

    private final RestTemplate restTemplate;
    private final String searchEngineURL;

    @Autowired
    public SearchEngineRestClient(
            RestTemplate restTemplate,
            @Value("#{systemProperties['searchEngineURL'] ?: \"http://localhost:80\"}")
            String searchEngineURL
    ) {
        this.restTemplate = restTemplate;
        this.searchEngineURL = searchEngineURL;
    }

    private URI buildDocumentUrl(String key) {
        return fromUriString(searchEngineURL)
                .path("/documents/")
                .path(key)
                .build().encode().toUri();
    }

    @Override
    public void addDocument(String key, String value) {
        URI documentUrl = buildDocumentUrl(key);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(value, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(documentUrl, PUT, entity, String.class);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getDocument(String key) {
        URI documentUrl = buildDocumentUrl(key);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(TEXT_PLAIN));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(documentUrl, GET, entity, String.class);
        } catch (RestClientException e) {
            return null;
        }

        return response.getBody();
    }

    @Override
    public Collection<String> findDocuments(String query) {
        URI queryUrl = fromUriString(searchEngineURL)
                .path("/findDocuments")
                .queryParam("query", query)
                .build().encode().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String[]> response;
        try {
            response = restTemplate.exchange(queryUrl, GET, entity, String[].class);
        } catch (RestClientException e) {
            return null;
        }

        return Arrays.asList(response.getBody());
    }
}
