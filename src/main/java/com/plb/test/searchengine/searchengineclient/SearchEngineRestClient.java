package com.plb.test.searchengine.searchengineclient;

import com.plb.test.searchengine.exceptions.DocumentAddingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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

    @Autowired
    RestTemplate restTemplate;

    //TODO
    @Value("http://localhost:8080")
    String searchEngineURL;

    private URI buildDocumentUrl(String key) {
        return fromUriString(searchEngineURL)
                .path("/documents/")
                .path(key)
                .build().encode().toUri();
    }

    @Override
    public void addDocument(String key, String value) throws DocumentAddingException {
        URI documentUrl = buildDocumentUrl(key);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(value, headers);

        ResponseEntity<String> response = restTemplate.exchange(documentUrl, PUT, entity, String.class);
        //ResponseEntity<String> response = restTemplate.getForEntity(documentUrl, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new DocumentAddingException(response.getStatusCode().getReasonPhrase());
        }
    }

    @Override
    public String getDocument(String key) {
        URI documentUrl = buildDocumentUrl(key);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(TEXT_PLAIN));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(documentUrl, GET, entity, String.class);
        //ResponseEntity<String> response = restTemplate.getForEntity(documentUrl, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
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

        ResponseEntity<String[]> response = restTemplate.exchange(queryUrl, GET, entity, String[].class);
        //ResponseEntity<String> response = restTemplate.getForEntity(documentUrl, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return null;
        }

        return Arrays.asList(response.getBody());
    }
}
