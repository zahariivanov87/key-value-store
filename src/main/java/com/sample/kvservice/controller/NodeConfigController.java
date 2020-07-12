package com.sample.kvservice.controller;

import com.sample.kvservice.entity.Node;
import com.sample.kvservice.service.NodeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Controller that handles CRUD operations for Nodes in cluster.
 */
@RestController
@RequestMapping("/api/cluster")
public class NodeConfigController {

    @Resource
    NodeService nodeService;

    @PostMapping(value = "")
    public Node saveNode(@RequestBody Node node) {
        //TODO - add validation of the body (do not propagate to db in case of invalid args)
        return nodeService.saveNode(node);
    }

    // Note! Please, keep in mind that pagination is not implemented for Nodes
    @GetMapping(value = "")
    public List<Node> getAllNodes() {
        return nodeService.getAllNodes();
    }
}
