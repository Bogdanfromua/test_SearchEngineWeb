package com.plb.test.searchengine.web;


import com.plb.test.searchengine.exceptions.DocumentAddingException;
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

    @Autowired
    SearchEngineClient searchEngineClient;

    @RequestMapping(path="controlPanel", method = RequestMethod.GET)
    public String controlPanel() {
        return "controlPanel";
    }

    @ResponseBody
    @RequestMapping(path="addDocument", method = RequestMethod.POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public void addDocument(String key, String value) throws DocumentAddingException {
        searchEngineClient.addDocument(key, value);
    }

    @ResponseBody
    @RequestMapping(value = "getDocument", method = RequestMethod.GET, produces = TEXT_PLAIN_VALUE)
    public String getDocument(String key, HttpServletResponse response) {
        String document = searchEngineClient.getDocument(key);
        //TODO
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
