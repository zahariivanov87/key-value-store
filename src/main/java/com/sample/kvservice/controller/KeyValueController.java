package com.sample.kvservice.controller;

import com.sample.kvservice.cache.LRUCache;
import com.sample.kvservice.service.KeyValueService;
import com.sample.kvservice.entity.KeyValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Controller that handles CRUD operations for Key-Value store.
 */
@RestController
@RequestMapping("/api")
public class KeyValueController {

    @Autowired
    LRUCache cache;

    @Resource
    KeyValueService keyValueService;

    @PostMapping(value = "/set")
    public KeyValuePair saveKeyValuePair(@RequestParam String k, @RequestParam String v) {
        validateSetRequest(k, v);
        KeyValuePair keyValuePair = keyValueService.insertKeyValuePair(new KeyValuePair(k, v));
        cache.putEntry(keyValuePair.getKey(), keyValuePair.getValue());
        return keyValuePair;
    }

    @PutMapping(value = "/set")
    public KeyValuePair updateKeyValuePair(@RequestParam String k, @RequestParam String v) {
        validateSetRequest(k, v);
        KeyValuePair keyValuePair = keyValueService.updateKeyValuePair(new KeyValuePair(k, v));
        cache.putEntry(keyValuePair.getKey(), keyValuePair.getValue());
        return keyValuePair;
    }

    @GetMapping(value = "/get")
    public KeyValuePair getKeyValuePair(@RequestParam String k) {
        // First look up in the cache
        String cachedValue = cache.getEntry(k);

        if (cachedValue != null) {
            // Move on top of the cache
            cache.putEntry(k, cachedValue);
            return new KeyValuePair(k, cachedValue);
        }

        KeyValuePair keyValuePair = keyValueService.getKeyValuePair(k);
        // Store it in the cache
        cache.putEntry(keyValuePair.getKey(), keyValuePair.getValue());

        return keyValuePair;
    }

    @GetMapping(value = "/is")
    public void checkExistence(@RequestParam String k) {
        keyValueService.checkExistence(k);
    }

    @DeleteMapping(value = "/rm")
    public void removeKeyValuePair(@RequestParam String k) {
        if (cache.getEntry(k) != null) {
            cache.removeEntry(k);
        }
        keyValueService.removeKeyValuePair(k);
    }

    @DeleteMapping(value = "/clear")
    public void removeAllKeyValuePairs() {
        cache.removeAll();
        keyValueService.removeAllKeyValuePairs();
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<KeyValuePair>> getAllKeyValuePairs(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "50") Integer limit) {
        List<KeyValuePair> list = keyValueService.getAllKeyValuePairs(offset, limit);

        return new ResponseEntity<List<KeyValuePair>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/getKeys")
    public ResponseEntity<List<KeyValuePair>> getAllKeys(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "50") Integer limit) {
        List<KeyValuePair> list = keyValueService.getAllKeys(offset, limit);

        return new ResponseEntity<List<KeyValuePair>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/getValues")
    public ResponseEntity<List<KeyValuePair>> getAllValues(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "50") Integer limit) {
        List<KeyValuePair> list = keyValueService.getAllValues(offset, limit);

        return new ResponseEntity<List<KeyValuePair>>(list, new HttpHeaders(), HttpStatus.OK);
    }


    private void validateSetRequest(String k, String v) {
//        k is a string not bigger than 64 chars
//        v is a string not bigger than 256 chars
        if (k != null && k.length() > 64) {
            throw new IllegalArgumentException("Invalid length of the key. Max allowed is: 64");
        }

        if (v != null && v.length() > 256) {
            throw new IllegalArgumentException("Invalid length of the value. Max allowed is: 256");
        }

        //TODO - add more - for example check for illegal characters.
    }

}
