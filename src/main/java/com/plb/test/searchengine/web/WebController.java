package com.plb.test.searchengine.web;


import com.plb.test.searchengine.searchengineclient.SearchEngineClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@Controller
@RequestMapping("/")
public class WebController {

    private final SearchEngineClient searchEngineClient;

    @Autowired
    public WebController(SearchEngineClient searchEngineClient) {
        this.searchEngineClient = searchEngineClient;
    }

    @RequestMapping(path="/", method = RequestMethod.GET)
    public String controlPanel() {
        return "controlPanel";
    }

    @RequestMapping(path="addDocument", method = RequestMethod.POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String addDocument(String key, String value) {
        searchEngineClient.addDocument(key, value);
        return "redirect:/";
    }

    @ResponseBody
    @RequestMapping(value = "getDocument", method = RequestMethod.GET, produces = TEXT_PLAIN_VALUE)
    public String getDocument(String key, HttpServletResponse response) {
        String document = searchEngineClient.getDocument(key);
        if (null == document) {
            response.setStatus(404);
            return "";
        }
        return document;
    }

    @ResponseBody
    @RequestMapping(value = "findDocuments", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public Collection<String> findDocuments(String query) {
        return searchEngineClient.findDocuments(query);
    }
}
