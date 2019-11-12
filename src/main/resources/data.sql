CREATE TABLE users (
    username varchar(50) NOT NULL,
    password varchar(50) NOT NULL,
    enabled tinyint(1) NOT NULL,

    PRIMARY KEY (username)
);

INSERT INTO users (username, password, enabled)
VALUES
('john', '{noop}test123', 1),
('mary', '{noop}test123', 1),
('susan', '{noop}test123', 1);

CREATE TABLE authorities (
    username varchar(50) NOT NULL,
    authority varchar(50) NOT NULL,

    UNIQUE KEY authorities_idx_1 (username, authority),

    CONSTRAINT authorities_ibfk_1
    FOREIGN KEY (username)
    REFERENCES users (username)
);

INSERT INTO authorities (username, authority)
VALUES
('john', 'ROLE_EMPLOYEE'),
('mary', 'ROLE_EMPLOYEE'),
('mary', 'ROLE_MANAGER'),
('susan', 'ROLE_EMPLOYEE'),
('susan', 'ROLE_ADMIN');
