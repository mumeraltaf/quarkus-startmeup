DROP TABLE IF EXISTS fruit CASCADE;
CREATE TABLE IF NOT EXISTS fruit(
    id text PRIMARY KEY,
    name text,
    description text
);