package com.sample.kvservice.service;

import com.sample.kvservice.dao.KeyValueDao;
import com.sample.kvservice.entity.KeyValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KeyValueServiceImpl implements KeyValueService {

    @Autowired
    KeyValueDao keyValueDao;

    @Override
    public KeyValuePair insertKeyValuePair(KeyValuePair keyValuePair) {
        return keyValueDao.insertKeyValuePair(keyValuePair);
    }

    @Override
    public KeyValuePair updateKeyValuePair(KeyValuePair keyValuePair) {
        return keyValueDao.updateKeyValuePair(keyValuePair);
    }

    @Override
    public KeyValuePair getKeyValuePair(String key) {
        return keyValueDao.getKeyValuePair(key);
    }

    @Override
    public void removeKeyValuePair(String key) {
        keyValueDao.removeKeyValuePair(key);
    }

    @Override
    public void removeAllKeyValuePairs() {
        keyValueDao.removeAllKeyValuePairs();
    }

    @Override
    public void checkExistence(String key) {
        keyValueDao.checkExistence(key);
    }

    @Override
    public List<KeyValuePair> getAllKeyValuePairs(Integer offset, Integer limit) {
        //Fetch paginated result
        return keyValueDao.getAll(offset, limit);
    }

    @Override
    public List<KeyValuePair> getAllKeys(Integer offset, Integer limit) {
        //Fetch paginated result
       return keyValueDao.getAllKeys(offset, limit);
    }

    @Override
    public List<KeyValuePair> getAllValues(Integer offset, Integer limit) {
        //Fetch paginated result
        return keyValueDao.getAllValues(offset, limit);
    }
}
