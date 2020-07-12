package com.sample.kvservice.service;

import com.sample.kvservice.cache.LRUCache;
import com.sample.kvservice.controller.KeyValueController;
import com.sample.kvservice.entity.KeyValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(KeyValueController.class)
public class KeyValueServiceMockTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private KeyValueService service;

    @MockBean
    private LRUCache cache;

    @Test
    public void testGetKeyValuePair() throws Exception {

        KeyValuePair pair = new KeyValuePair("key", "value");

        given(service.getKeyValuePair("key")).willReturn(pair);

        mvc.perform(get("/api/get?k=key")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key", is(pair.getKey())));
    }

    @Test
    public void testGetAllKeys() throws Exception {

        KeyValuePair pair = new KeyValuePair("key", "value");

        List<KeyValuePair> keyValues = Arrays.asList(pair);

        given(service.getAllKeys(0, 50)).willReturn(keyValues);

        mvc.perform(get("/api/getKeys")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].key", is(pair.getKey())));
    }

    @Test
    public void testGetAllValues() throws Exception {

        KeyValuePair pair = new KeyValuePair("key", "value");

        List<KeyValuePair> keyValues = Arrays.asList(pair);

        given(service.getAllValues(0, 50)).willReturn(keyValues);

        mvc.perform(get("/api/getValues")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].key", is(pair.getKey())));
    }


    @Test
    public void testGetAll() throws Exception {

        List<KeyValuePair> keyValues = new ArrayList<>();
        for(int i=0; i< 20; i++){
            keyValues.add(new KeyValuePair("key"+i, "value"+i));
        }

        given(service.getAllKeyValuePairs(0, 50)).willReturn(keyValues);

        mvc.perform(get("/api/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(20)));
    }

}
