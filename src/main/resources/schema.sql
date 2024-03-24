CREATE TABLE IF NOT EXISTS profile (
    profile_id SERIAL PRIMARY KEY,
    profile_name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS product (
    product_id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(200) NOT NULL,
    is_sold boolean DEFAULT FALSE NOT NULL,
    owner_id INT NOT NULL,
    CONSTRAINT fk_profile_owner FOREIGN KEY (owner_id) REFERENCES profile(profile_id)
    );
