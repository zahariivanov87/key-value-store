package com.sample.kvservice.dao;

import com.sample.kvservice.entity.Node;
import com.sample.kvservice.exception.NodeNotFoundException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class NodeDaoImpl implements NodeDao {

    NamedParameterJdbcTemplate template;

    public NodeDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Node> getAllNodes() {

        String sql = "select * from node_config order by hostname asc";

        return template.getJdbcTemplate().query(sql, (result, i) ->
                new Node(result.getInt("id"), result.getString("hostname"),
                        result.getInt("db_capacity"), result.getInt("load_factor"),
                        result.getBoolean("healthy")));

    }

    @Override
    public Node saveNode(Node node) {

        final String sql = "insert into node_config(hostname, db_capacity,load_factor,healthy) values(:hostname,:db_capacity,:load_factor,:healthy)";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("hostname", node.getHostname())
                .addValue("db_capacity", node.getDbCapacity())
                .addValue("load_factor", node.getLoadFactor())
                .addValue("healthy", node.getHealthy());

        template.update(sql, params);

        return getNodeByHostname(node.getHostname());
    }

    public Node getNodeByHostname(String hostname ) {

        Map<String, String> params = new HashMap<>();
        params.put("hostname", hostname);

        String sql = "select * from node_config where hostname=:hostname";

        try {
            return template.queryForObject(sql, params, (result, i) ->
                    new Node(result.getInt("id"), result.getString("hostname"),
                            result.getInt("db_capacity"), result.getInt("load_factor"),
                            result.getBoolean("healthy")));
        }catch (EmptyResultDataAccessException e){
            throw new NodeNotFoundException(String.format("No such node: %s", hostname));
        }
    }
}
