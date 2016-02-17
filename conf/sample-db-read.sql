drop table if EXISTS users;

CREATE TABLE users (
  id   INTEGER PRIMARY KEY,
  name VARCHAR(30)
);

INSERT into users VALUES (1, 'Michal');