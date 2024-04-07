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

ALTER TABLE product DROP CONSTRAINT fk_profile_owner;
ALTER TABLE product ADD CONSTRAINT fk_account_id FOREIGN KEY (owner_id) REFERENCES account(account_id);
ALTER TABLE product ADD COLUMN product_type INT DEFAULT 0;

CREATE TABLE IF NOT EXISTS account (
    account_id SERIAL PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(120) NOT NULL,
    UNIQUE (username),
    UNIQUE (email)
);

ALTER TABLE account DROP CONSTRAINT IF EXISTS account_username_key;
ALTER TABLE account ADD COLUMN created_at TIMESTAMP(0);
ALTER TABLE account ADD COLUMN updated_at TIMESTAMP(0) DEFAULT NULL;
ALTER TABLE account ALTER COLUMN created_at SET NOT NULL;

CREATE TABLE IF NOT EXISTS account_role (
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(20) NOT NULL,
    UNIQUE (role_name)
);

ALTER TABLE account_role ADD COLUMN created_at TIMESTAMP(0);
ALTER TABLE account_role ADD COLUMN updated_at TIMESTAMP(0) DEFAULT NULL;
ALTER TABLE account_role ALTER COLUMN created_at SET NOT NULL;

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

ALTER TABLE refresh_token ADD COLUMN created_at TIMESTAMP(0) NOT NULL;
ALTER TABLE refresh_token ADD COLUMN updated_at TIMESTAMP(0) DEFAULT NULL;