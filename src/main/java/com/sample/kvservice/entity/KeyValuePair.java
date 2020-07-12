package com.sample.kvservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "key_value_store")
public class KeyValuePair {

    /**
     * Key of KeyValue entry
     */
    @Column(name="k")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Id
    private String key;

    /**
     * Value of KeyValue entry
     */
    @Column(name="v")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String value;

    public KeyValuePair(){};

    public KeyValuePair(String key, String value){
        this.key = key;
        this.value = value;
    }

    // Getters & Setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
