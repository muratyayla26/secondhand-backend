CREATE TABLE IF NOT EXISTS account (
    account_id SERIAL PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(120) NOT NULL,
    created_at TIMESTAMP(0) NOT NULL,
    updated_at TIMESTAMP(0) DEFAULT NULL,
    UNIQUE (username),
    UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS account_role (
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(20) NOT NULL,
    created_at TIMESTAMP(0) NOT NULL,
    updated_at TIMESTAMP(0) DEFAULT NULL,
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
    created_at TIMESTAMP(0) NOT NULL,
    updated_at TIMESTAMP(0) DEFAULT NULL,
    CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES account(account_id)
);

CREATE TABLE IF NOT EXISTS product (
    product_id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(200) NOT NULL,
    owner_id INT NOT NULL,
    product_type SMALLINT DEFAULT 0,
    is_sold boolean DEFAULT FALSE NOT NULL,
    created_at TIMESTAMP(0) NOT NULL,
    updated_at TIMESTAMP(0) DEFAULT NULL,
    CONSTRAINT fk_account_id FOREIGN KEY (owner_id) REFERENCES account(account_id)
    );

CREATE TABLE IF NOT EXISTS profile (
    profile_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    mobile_number VARCHAR(20),
    address VARCHAR(255),
    city_id INT,
    district_id INT,
    gender_type SMALLINT,
    account_id BIGINT NOT NULL,
    created_at TIMESTAMP(0) NOT NULL,
    updated_at TIMESTAMP(0) DEFAULT NULL,
    UNIQUE (account_id),
    CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES account(account_id),
    CONSTRAINT fk_city_id FOREIGN KEY (city_id) REFERENCES city(city_id),
    CONSTRAINT fk_district_id FOREIGN KEY (district_id) REFERENCES district(district_id)
);

CREATE TABLE IF NOT EXISTS comment (
  comment_id SERIAL PRIMARY KEY,
  content VARCHAR(255) NOT NULL,
  owner_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  created_at TIMESTAMP(0) NOT NULL,
  updated_at TIMESTAMP(0) DEFAULT NULL,
  CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES account(account_id),
  CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE IF NOT EXISTS comment_answer (
  comment_answer_id SERIAL PRIMARY KEY,
    content VARCHAR(255) NOT NULL,
    owner_id BIGINT NOT NULL,
    comment_id BIGINT NOT NULL,
    created_at TIMESTAMP(0) NOT NULL,
    updated_at TIMESTAMP(0) DEFAULT NULL,
    CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES account(account_id),
    CONSTRAINT fk_comment_id FOREIGN KEY (comment_id) REFERENCES comment(comment_id)
);

CREATE VIEW profile_plain_view AS
    SELECT profile_id, account_id, first_name, last_name from profile;
