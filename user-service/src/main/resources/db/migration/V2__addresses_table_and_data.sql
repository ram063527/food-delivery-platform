-- V2__migration_script.sql

-- Create the sequence for addresses ID
CREATE SEQUENCE addresses_id_seq
    START WITH 1
    INCREMENT BY 50;

-- Create the addresses table
CREATE TABLE addresses (
                           id BIGINT DEFAULT nextval('addresses_id_seq') PRIMARY KEY,  -- Use sequence for address IDs
                           user_id BIGINT NOT NULL,                                    -- Foreign key to users table
                           street_address VARCHAR(255) NOT NULL,                       -- Street address
                           city VARCHAR(100) NOT NULL,                                 -- City
                           state VARCHAR(100),                                         -- State (optional)
                           postal_code VARCHAR(20),                                    -- Postal/Zip code
                           country VARCHAR(100) NOT NULL,                              -- Country
                           address_type VARCHAR(50) CHECK (address_type IN ('HOME', 'WORK', 'OTHER')) NOT NULL,  -- Address type
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,             -- Creation timestamp
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,             -- Last update timestamp
                           CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE -- Foreign key constraint
);

-- Insert example addresses for users
INSERT INTO addresses (user_id, street_address, city, state, postal_code, country, address_type, created_at, updated_at)
VALUES
    (1, '123 Elm St', 'Springfield', 'IL', '62701', 'USA', 'HOME', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (51, '456 Oak Ave', 'Springfield', 'IL', '62702', 'USA', 'WORK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (101, '789 Maple Dr', 'Chicago', 'IL', '60601', 'USA', 'HOME', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);