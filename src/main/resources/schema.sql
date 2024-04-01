CREATE TABLE IF NOT EXISTS profile (
    profile_id SERIAL PRIMARY KEY,
    profile_name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    created_at TIMESTAMP(0) NOT NULL,
    updated_at TIMESTAMP(0) DEFAULT NULL
    );

CREATE TABLE IF NOT EXISTS product (
    product_id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(200) NOT NULL,
    is_sold boolean DEFAULT FALSE NOT NULL,
    owner_id INT NOT NULL,
    created_at TIMESTAMP(0) NOT NULL,
    updated_at TIMESTAMP(0) DEFAULT NULL,
    CONSTRAINT fk_profile_owner FOREIGN KEY (owner_id) REFERENCES profile(profile_id)
    );

CREATE TABLE IF NOT EXISTS account (
    account_id SERIAL PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(120) NOT NULL,
    UNIQUE (username),
    UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS account_role (
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(20) NOT NULL,
    UNIQUE (role_name)
);

CREATE TABLE IF NOT EXISTS account_account_role (
    account_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (account_id, role_id),
    CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES account(account_id),
    CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES account_role(role_id)
);

INSERT INTO account_role(role_name) VALUES('ROLE_USER');
INSERT INTO account_role(role_name) VALUES('ROLE_MODERATOR');
INSERT INTO account_role(role_name) VALUES('ROLE_ADMIN');

CREATE TABLE IF NOT EXISTS refresh_token (
    refresh_token_id SERIAL PRIMARY KEY,
    token VARCHAR(36) NOT NULL,
    expiry_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    account_id BIGINT NOT NULL,
    CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES account(account_id)
);