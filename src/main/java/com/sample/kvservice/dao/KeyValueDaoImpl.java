package com.sample.kvservice.dao;

import com.sample.kvservice.exception.KeyNotFoundException;
import com.sample.kvservice.entity.KeyValuePair;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class KeyValueDaoImpl implements KeyValueDao {

    public KeyValueDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    NamedParameterJdbcTemplate template;

    @Override
    public KeyValuePair insertKeyValuePair(KeyValuePair keyValuePair) {

        final String sql = "insert into key_value_store(k, v) values(:key,:value)";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("key", keyValuePair.getKey())
                .addValue("value", keyValuePair.getValue());

        template.update(sql, params);

        return getKeyValuePair(keyValuePair.getKey());
    }

    @Override
    public KeyValuePair updateKeyValuePair(KeyValuePair keyValuePair) {

        final String sql = "update key_value_store set v=:value where k=:key";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("key", keyValuePair.getKey())
                .addValue("value", keyValuePair.getValue());

        template.update(sql, params);

        return getKeyValuePair(keyValuePair.getKey());
    }

    @Override
    public KeyValuePair getKeyValuePair(String key) {

        Map<String, String> params = new HashMap<>();
        params.put("key", key);

        String sql = "select k,v from key_value_store where k=:key";

        try {
            return template.queryForObject(sql, params, (result, i) ->
                    new KeyValuePair(result.getString("k"), result.getString("v")));
        }catch (EmptyResultDataAccessException e){
            throw new KeyNotFoundException(String.format("No such key: %s", key));
        }
    }

    @Override
    public List<KeyValuePair> getAll(int offset, int limit) {

        String sql = "select k,v from key_value_store order by k asc";

        if(offset > 0){
            //append offset
            sql += String.format(" offset %s", offset);
        }

        if(limit > 0){
            sql += String.format(" limit %s", limit);
        }

        return template.getJdbcTemplate().query(sql, (resultSet, i) ->
                new KeyValuePair(resultSet.getString("k"), resultSet.getString("v")));
    }

    @Override
    public List<KeyValuePair> getAllKeys(int offset, int limit) {
        String sql = "select k from key_value_store order by k asc";

        if(offset > 0){
            //append offset
            sql += String.format(" offset %s", offset);
        }

        if(limit > 0){
            sql += String.format(" limit %s", limit);
        }
        return template.getJdbcTemplate().query(sql, (resultSet, i) ->
                // Deserialize just keys as only keys will be fetched from query
                new KeyValuePair(resultSet.getString("k"), null));
    }

    @Override
    public List<KeyValuePair> getAllValues(int offset, int limit) {
        String sql = "select v from key_value_store order by v asc";

        if(offset > 0){
            //append offset
            sql += String.format(" offset %s", offset);
        }

        if(limit > 0){
            sql += String.format(" limit %s", limit);
        }
        return template.getJdbcTemplate().query(sql, (resultSet, i) ->
                // Deserialize just values as only values will be fetched from query
                new KeyValuePair(null, resultSet.getString("v")));
    }

    @Override
    public void removeKeyValuePair(String key) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key);

        String sql = "delete from key_value_store where k=:key";

        int update = template.update(sql, params);
        if(update ==0){
            // No rows have been affected
            throw new KeyNotFoundException(String.format("No such key: %s", key));
        }

    }

    @Override
    public void removeAllKeyValuePairs() {
        // Params are not needed hence do not create unnecessary Object to fill the memory
        template.getJdbcTemplate().execute("delete from key_value_store");
    }

    @Override
    public void checkExistence(String key) {
        // If there is not such key-value pair getKeyValuePair(key) will throw 404 - not found exception.
        // Otherwise just proceed and return 200
        getKeyValuePair(key);
    }

}
