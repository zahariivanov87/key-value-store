package com.sample.kvservice.dao;

import com.sample.kvservice.entity.KeyValuePair;

import java.util.List;


/**
 * KeyValueDao defines a contract for Data Access Layer of KeyValue service.
 */
public interface KeyValueDao  {

    /**
     * Save KeyValuePair
     * @param keyValuePair - pair of key and value
     * @return stored KeyValuePair
     */
    KeyValuePair insertKeyValuePair(KeyValuePair keyValuePair);

    /**
     * Update KeyValuePair
     * @param keyValuePair - pair of key and value
     * @return updated KeyValuePair
     */
    KeyValuePair updateKeyValuePair(KeyValuePair keyValuePair);

    /**
     * Retrieves KeyValuePair by provided key
     * @param key - key of the KeyValuePair
     * @return KeyValuePair that matches provided key
     */
    KeyValuePair getKeyValuePair(String key);

    /**
     * Fetch all KeyValuePairs
     * @param offset - offset (from where to start) of records (handle pagination)
     * @param limit - limit (handle pagination)
     * @return - List of KeyValuePairs
     */
    List<KeyValuePair> getAll(int offset, int limit);

    /**
     * Fetch all keys of KeyValuePairs
     * @param offset - offset (from where to start) of records (handle pagination)
     * @param limit - limit (handle pagination)
     * @return List of KeyValuePairs - only keys will present
     */
    List<KeyValuePair> getAllKeys(int offset, int limit);

    /**
     * Fetch all values of KeyValuePairs
     * @param offset - offset (from where to start) of records (handle pagination)
     * @param limit - limit (handle pagination)
     * @return List of KeyValuePairs - only values will present
     */
    List<KeyValuePair> getAllValues(int offset, int limit);

    /**
     * Removes KeyValuePair by provided key
     * @param key - key of KeyValuePair
     */
    void removeKeyValuePair(String key);

    /**
     * Removes all KeyValuePairs
     */
    void removeAllKeyValuePairs();

    /**
     * Check if KeyValuePair exists
     * @param key - key of KeyValuePair
     */
    void checkExistence(String key);

}
