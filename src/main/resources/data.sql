CREATE TABLE users (
    username varchar(50) NOT NULL,
    password varchar(68) NOT NULL,
    enabled tinyint(1) NOT NULL,

    PRIMARY KEY (username)
);

INSERT INTO users (username, password, enabled)
VALUES
('john', '{bcrypt}$2a$10$JbfY3QLDpnQlMtP2Wk9aaum586jPvF9rBQXiHZCmxnPjD4Ol.30VS', 1),
('mary', '{bcrypt}$2a$10$sY82/i3ycggOoKg573KjFe84eNcW/AwnoFxGchiKbSLSxEP4yNCmO', 1),
('susan', '{bcrypt}$2a$10$uAfdRP20mFsdt5g/nQkNSOBfCS6yzNdf6zRnVwzBdCj7BAeUVxS.W', 1);

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
