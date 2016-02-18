drop table if EXISTS users;

CREATE TABLE users (
  id   INTEGER PRIMARY KEY auto_increment,
  name VARCHAR(30)
);

INSERT into users VALUES (1, 'Michal');