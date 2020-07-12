package com.sample.kvservice.cache;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class LRUCacheTest {

    @Test
    public void testCache(){
        // Store 7 entries in the cache
        LRUCache lrucache = new LRUCache(4);
        lrucache.putEntry("a", "aa");
        lrucache.putEntry("b", "bb");
        lrucache.putEntry("c", "cc");
        lrucache.putEntry("d", "dd");
        lrucache.putEntry("e", "ee");
        lrucache.putEntry("f", "ff");
        lrucache.putEntry("g", "gg");

        // Since capacity is 4, first 3 records must be null
        assertNull(lrucache.getEntry("a"));
        assertNull(lrucache.getEntry("b"));
        assertNull(lrucache.getEntry("c"));

        //Next records must present
        assertNotNull(lrucache.getEntry("d"));
        assertNotNull(lrucache.getEntry("e"));
        assertNotNull(lrucache.getEntry("f"));
        assertNotNull(lrucache.getEntry("g"));

        lrucache.removeEntry("e");
        assertNull(lrucache.getEntry("e"));

        // Clear cache
        lrucache.removeAll();

        assertNull(lrucache.getEntry("a"));
        assertNull(lrucache.getEntry("b"));
        assertNull(lrucache.getEntry("c"));
        assertNull(lrucache.getEntry("d"));
        assertNull(lrucache.getEntry("e"));
        assertNull(lrucache.getEntry("f"));
        assertNull(lrucache.getEntry("g"));

    }
}
