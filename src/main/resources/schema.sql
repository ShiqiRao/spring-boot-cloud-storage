CREATE TABLE IF NOT EXISTS T_USER (
  user_id BIGINT PRIMARY KEY auto_increment,
  username VARCHAR(20) UNIQUE,
  `password` VARCHAR(20),
  first_name VARCHAR(20),
  last_name VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS T_NOTE (
    note_id BIGINT PRIMARY KEY auto_increment,
    note_title VARCHAR(20),
    note_description VARCHAR (1000),
    user_id BIGINT
);

CREATE TABLE IF NOT EXISTS T_FILE (
    file_id BIGINT PRIMARY KEY auto_increment,
    filename VARCHAR(50),
    object_name VARCHAR(50),
    url  VARCHAR(50),
    file_size BIGINT,
    user_id BIGINT
);