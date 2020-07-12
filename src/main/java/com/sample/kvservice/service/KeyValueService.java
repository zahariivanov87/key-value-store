package com.sample.kvservice.service;

import com.sample.kvservice.entity.KeyValuePair;

import java.util.List;

/**
 *  KeyValueService - is a service that invokes KeyValue repository for CRUD operations.
 */
public interface KeyValueService {

    /**
     * Save KeyValuePair
     *
     * @param keyValuePair - pair of key and value
     * @return stored KeyValuePair
     */
    KeyValuePair insertKeyValuePair(KeyValuePair keyValuePair);

    /**
     * Update KeyValuePair
     *
     * @param keyValuePair - pair of key and value
     * @return updated KeyValuePair
     */
    KeyValuePair updateKeyValuePair(KeyValuePair keyValuePair);

    /**
     * Retrieves KeyValuePair by provided key
     *
     * @param key - key of the KeyValuePair
     * @return KeyValuePair that matches provided key
     */
    KeyValuePair getKeyValuePair(String key);

    /**
     * Removes KeyValuePair by provided key
     *
     * @param key - key of KeyValuePair
     */
    void removeKeyValuePair(String key);

    /**
     * Removes all KeyValuePairs
     */
    void removeAllKeyValuePairs();

    /**
     * Check if KeyValuePair exists
     *
     * @param key - key of KeyValuePair
     */
    void checkExistence(String key);

    /**
     * Fetch all KeyValuePairs
     *
     * @param offset - offset (from where to start) of records (handle pagination)
     * @param limit  - limit (handle pagination)
     * @return - List of KeyValuePairs
     */
    List<KeyValuePair> getAllKeyValuePairs(Integer offset, Integer limit);

    /**
     * Fetch all keys of KeyValuePairs
     *
     * @param offset - offset (from where to start) of records (handle pagination)
     * @param limit  - limit (handle pagination)
     * @return List of KeyValuePairs - only keys will present
     */
    List<KeyValuePair> getAllKeys(Integer offset, Integer limit);

    /**
     * Fetch all values of KeyValuePairs
     *
     * @param offset - offset (from where to start) of records (handle pagination)
     * @param limit  - limit (handle pagination)
     * @return List of KeyValuePairs - only values will present
     */
    List<KeyValuePair> getAllValues(Integer offset, Integer limit);

}
