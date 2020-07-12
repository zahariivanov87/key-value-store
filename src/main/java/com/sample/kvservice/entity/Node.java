package com.sample.kvservice.entity;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "node_config")
public class Node {

    @Id
    @Generated(GenerationTime.ALWAYS)
    @Column(name="id")
    private Integer id;

    /**
     * Name of the node
     */
    @Column(name="hostname")
    private String hostname;

    /**
     * Capacity for db according with the current db size
     */
    @Column(name="db_capacity")
    private Integer dbCapacity;

    /**
     * Load factor for the node - from 0 to 100
     */
    @Column(name="load_factor")
    private Integer loadFactor;

    /**
     * Boolean flag that indicates whether the node is healthy
     */
    @Column(name="healthy")
    private Boolean healthy;

    //TODO add some other metadata for the node

    public Node(){

    }

    public Node(Integer id, String hostname, Integer dbCapacity, Integer loadFactor, Boolean healthy){
        this.id = id;
        this.hostname = hostname;
        this.dbCapacity = dbCapacity;
        this.loadFactor = loadFactor;
        this.healthy = healthy;
    }

    public Integer getId() {
        return id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getDbCapacity() {
        return dbCapacity;
    }

    public void setDbCapacity(Integer dbCapacity) {
        this.dbCapacity = dbCapacity;
    }

    public Integer getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(Integer loadFactor) {
        this.loadFactor = loadFactor;
    }

    public Boolean getHealthy() {
        return healthy;
    }

    public void setHealthy(Boolean healthy) {
        this.healthy = healthy;
    }

}
