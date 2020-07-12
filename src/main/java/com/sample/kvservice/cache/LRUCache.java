package com.sample.kvservice.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Least Recently Used cache that stores top k recent KeyValuePairs.
 */
@Component
public class LRUCache {

    private static class KeyValueNode {
        String key;
        String value;
        KeyValueNode left, right;
    }

    Map<String, KeyValueNode> map;
    KeyValueNode start, end;
    int LRU_SIZE;

    public LRUCache(){
        map = new HashMap<String, KeyValueNode>();
        this.LRU_SIZE = 50;
    }

    public LRUCache(int lru_size) {
        map = new HashMap<String, KeyValueNode>();
        this.LRU_SIZE = lru_size;
    }

    public String getEntry(String key) {

        if (map.containsKey(key)) {
            KeyValueNode keyValueNode = map.get(key);
            removeKeyValueNode(keyValueNode);
             moveToTop(keyValueNode);
            return keyValueNode.value;
        }

        return null;
    }


    public void putEntry(String key, String value) {

        if (map.containsKey(key)) {
            KeyValueNode keyValueNode = map.get(key);
            keyValueNode.value = value;
            removeKeyValueNode(keyValueNode);
            moveToTop(keyValueNode);

        } else {
            // New kv record
            KeyValueNode kvNode = new KeyValueNode();
            kvNode.key = key;
            kvNode.value = value;
            kvNode.left = null;
            kvNode.right = null;

            // Check the capacity
            if (map.size() >= LRU_SIZE) {
                map.remove(end.key);
                removeKeyValueNode(end);
            }

            moveToTop(kvNode);
            map.put(key, kvNode);
        }
    }


    public void removeEntry(String key) {
        if(map.containsKey(key)){
            KeyValueNode keyValueNode = map.get(key);
            removeKeyValueNode(keyValueNode);
            map.remove(key);
        }
    }

    public void removeAll(){
        map.clear();
    }

     private void moveToTop(KeyValueNode node) {

        node.right = start;
        node.left = null;

        if(start != null){
            start.left = node;
        }

        start = node;
        if(end == null){
            end = start;
        }
    }

     private void removeKeyValueNode(KeyValueNode node){
        if(node.left != null){
            node.left.right = node.right;
        }else {
            start = node.right;
        }

        if(node.right != null){
            node.right.left = node.left;
        }else {
            end = node.left;
        }
    }

}
