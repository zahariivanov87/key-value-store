
CREATE TABLE IF NOT EXISTS key_value_store
(
    k varchar(64) DEFAULT NULL,
    v varchar(256) DEFAULT NULL,
    PRIMARY KEY (k)
);

CREATE INDEX IF NOT EXISTS idx_key
ON key_value_store(k);

CREATE TABLE IF NOT EXISTS node_config
(
  id serial,
  hostname varchar(100) DEFAULT NULL UNIQUE,
  db_capacity integer,
  load_factor integer,
  healthy BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (id)
);