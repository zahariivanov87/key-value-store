package com.sample.kvservice.service;

import com.sample.kvservice.entity.Node;

import java.util.List;

/**
 * Handles CRUD operations for Nodes in the cluster
 */
public interface NodeService {

    /**
     * Retrieves all nodes in the cluster
     * @return - List of Nodes
     */
    List<Node> getAllNodes();

    /**
     * Store Node
     * @param node - node to be stored
     * @return - stored node
     */
    Node saveNode(Node node);
}
