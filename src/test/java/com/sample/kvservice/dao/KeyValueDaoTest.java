package com.sample.kvservice.dao;

import com.sample.kvservice.PostgressApplication;
import com.sample.kvservice.entity.KeyValuePair;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PostgressApplication.class)
@ActiveProfiles("test")
public class KeyValueDaoTest {

    @Autowired
    private KeyValueDao keyValueRepository;

    @Before
    public void before() {
        // Clean h2 in-memory database per each test case
        keyValueRepository.removeAllKeyValuePairs();
    }

    @Test
    public void testSaveAndGetKeyValue() {

        KeyValuePair genericEntity = keyValueRepository.insertKeyValuePair(new KeyValuePair("key", "val"));
        // According to REST standards the object that is stored is returned as response
        assertNotNull(genericEntity);

        KeyValuePair foundEntity = keyValueRepository.getKeyValuePair(genericEntity.getKey());
        assertNotNull(foundEntity);
        assertEquals(genericEntity.getKey(), foundEntity.getKey());
        assertEquals(genericEntity.getValue(), foundEntity.getValue());

    }

    @Test
    public void testUpdateKeyValue() {

        KeyValuePair genericEntity = keyValueRepository.insertKeyValuePair(new KeyValuePair("key", "val"));
        // According to REST standards the object that is stored is returned as response
        assertNotNull(genericEntity);

        KeyValuePair updatedEntity = keyValueRepository.updateKeyValuePair(new KeyValuePair("key", "val1"));
        assertNotNull(updatedEntity);

        KeyValuePair foundEntity = keyValueRepository.getKeyValuePair(genericEntity.getKey());
        assertNotNull(foundEntity);
        assertEquals(updatedEntity.getKey(), foundEntity.getKey());
        assertEquals(updatedEntity.getValue(), foundEntity.getValue());

    }

    @Test
    public void testRemoveAll() {

        //Store 100 key-value entries
        for (int i = 0; i < 100; i++) {
            keyValueRepository.insertKeyValuePair(new KeyValuePair(String.valueOf(i), String.valueOf(i)));
        }

        List<KeyValuePair> page = keyValueRepository.getAll(0, 100);

        assertNotNull(page);
        assertEquals(100, page.size());

        keyValueRepository.removeAllKeyValuePairs();

        page = keyValueRepository.getAll(0, 100);
        assertNotNull(page);
        assertEquals(0, page.size());
    }

    @Test
    public void testGetAllKeys() {

        List<KeyValuePair> allKeys = keyValueRepository.getAllKeys(0, 100);
        assertNotNull(allKeys);
        assertEquals(0, allKeys.size());


        //Store 50 key-value entries
        for (int i = 97; i < 123; i++) {
            char c = (char) i;
            keyValueRepository.insertKeyValuePair(new KeyValuePair(String.valueOf(c), String.valueOf(i)));
        }

        allKeys = keyValueRepository.getAllKeys(0, 100);

        assertNotNull(allKeys);
        assertEquals(26, allKeys.size()); // alphabetic chars

        int indexOfKeys = 0;
        //Assert that keys are fetched in ascending order
        for (int i = 97; i < 123; i++) {
            char c = (char) i;
            assertEquals(String.valueOf(c), allKeys.get(indexOfKeys).getKey());
            assertNull(allKeys.get(indexOfKeys).getValue());
            indexOfKeys++;
        }

    }

    @Test
    public void testGetAllValues() {

        List<KeyValuePair> allValues = keyValueRepository.getAllValues(0, 100);
        assertNotNull(allValues);
        assertEquals(0, allValues.size());

        //Store 50 key-value entries
        for (int i = 97; i < 123; i++) {
            char c = (char) i;
            keyValueRepository.insertKeyValuePair(new KeyValuePair(String.valueOf(i), String.valueOf(c)));
        }

        allValues = keyValueRepository.getAllValues(0, 100);

        assertNotNull(allValues);
        assertEquals(26, allValues.size()); // alphabetic chars

        int indexOfValues = 0;
        //Assert that values are fetched in ascending order
        for (int i = 97; i < 123; i++) {
            char c = (char) i;
            assertEquals(String.valueOf(c), allValues.get(indexOfValues).getValue());
            assertNull(allValues.get(indexOfValues).getKey());
            indexOfValues++;
        }
    }

    @Test
    public void testCheckExistence() {
        List<KeyValuePair> allValues = keyValueRepository.getAllValues(0, 100);
        assertNotNull(allValues);
        assertEquals(0, allValues.size());

        // Insert one key-value pair
        keyValueRepository.insertKeyValuePair(new KeyValuePair("key", "value"));

        // First try to look up for invalid key - exception expected
        Throwable t = null;
        try {
            keyValueRepository.checkExistence("invalid-key");
        } catch (Throwable e) {
            t = e;
        }

        assertNotNull(t);
        assertEquals("No such key: invalid-key", t.getMessage());

        // Now check existence of valid key
        keyValueRepository.checkExistence("key");
        // Exception is not expected, test should pass :)
    }

    @Test
    public void testRemoveKeyValue() {
        // insert one key-value pair
        keyValueRepository.insertKeyValuePair(new KeyValuePair("key", "value"));

        KeyValuePair keyValuePair = keyValueRepository.getKeyValuePair("key");
        assertNotNull(keyValuePair);
        assertEquals("key", keyValuePair.getKey());

        //Now remove this key-value
        keyValueRepository.removeKeyValuePair("key");

        //Try to fetch it -> 404 not found expected
        Throwable t = null;
        try {
            keyValueRepository.getKeyValuePair("key");
        } catch (Throwable e) {
            t = e;
        }
        assertNotNull(t);
        assertEquals("No such key: key", t.getMessage());
    }

    @Test
    public void testPagination(){
        List<KeyValuePair> allKeys = keyValueRepository.getAllKeys(0, 100);
        assertNotNull(allKeys);
        assertEquals(0, allKeys.size());

        //Store 151 key-value entries
        for (int i = 0; i < 151; i++) {
            keyValueRepository.insertKeyValuePair(new KeyValuePair(UUID.randomUUID().toString(), String.valueOf(i)));
        }

        // Fetch first 50 results
        allKeys = keyValueRepository.getAllKeys(0, 50);
        assertNotNull(allKeys);
        assertEquals(50, allKeys.size());

        // Now go to the second page to fetch results from 50 to 100
        allKeys = keyValueRepository.getAllKeys(50, 50);
        // Again 50 results MUST be fetched
        assertNotNull(allKeys);
        assertEquals(50, allKeys.size());

        //And one more time to make it 150 -> move the offset
        allKeys = keyValueRepository.getAllKeys(100, 50);
        assertNotNull(allKeys);
        assertEquals(50, allKeys.size());

        //Ok now only one result remains for the last page
        allKeys = keyValueRepository.getAllKeys(150, 50);
        assertNotNull(allKeys);
        assertEquals(1, allKeys.size());
    }

}
