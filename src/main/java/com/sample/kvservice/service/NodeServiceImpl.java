package com.sample.kvservice.service;

import com.sample.kvservice.dao.NodeDao;
import com.sample.kvservice.entity.Node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NodeServiceImpl implements NodeService {

    @Autowired
    NodeDao nodeDao;

    @Override
    public List<Node> getAllNodes() {
        return nodeDao.getAllNodes();
    }

    @Override
    public Node saveNode(Node node) {
        return nodeDao.saveNode(node);
    }
}
