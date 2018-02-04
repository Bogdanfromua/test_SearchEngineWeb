package com.plb.test.searchengine;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WebControllerITest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DirtiesContext
    public void addDocument() throws Exception {
        mvc.perform(
                post("/addDocument")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(asList(
                                new BasicNameValuePair("key", "key1"),
                                new BasicNameValuePair("value", "value1")
                        )))));
    }

    @Test
    public void getDocument_nonExist() throws Exception {
        mvc.perform(
                get("/getDocument")
                        .param("key", "key1")
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext
    public void addAndGetDocument() throws Exception {
        String value = "testValue";
        String key = "test";

        mvc.perform(
                post("/addDocument")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(asList(
                                new BasicNameValuePair("key", key),
                                new BasicNameValuePair("value", value)
                        )))));

        mvc.perform(
                get("/getDocument")
                        .param("key", key)
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(value));
    }

    @Test
    public void findDocuments_emptySet() throws Exception {
        mvc.perform(
                get("/findDocuments")
                        .param("query", "search query")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    public void findDocuments() throws Exception {
        String value = "testValue";
        String key = "test";

        mvc.perform(
                post("/addDocument")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(asList(
                                new BasicNameValuePair("key", key),
                                new BasicNameValuePair("value", value)
                        )))));
        mvc.perform(
                get("/findDocuments")
                        .param("query", value)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(result -> assertEquals(
                        "["+key+"]",
                        result.getResponse().getContentAsString(),
                        false));
    }
}