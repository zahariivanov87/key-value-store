package com.sample.kvservice.dao;

import com.sample.kvservice.entity.Node;

import java.util.List;

public interface NodeDao {

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
