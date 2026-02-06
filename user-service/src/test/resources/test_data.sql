

truncate table users cascade ;
alter sequence users_id_seq restart with 1;
alter sequence addresses_id_seq restart with 1;


-- Insert test users
INSERT INTO users (email, password, first_name, last_name, phone, role, status)
VALUES
    ('john.doe@example.com', 'hashed_password_123', 'John', 'Doe', '1234567890', 'CUSTOMER', 'ACTIVE'),
    ('jane.smith@example.com', 'hashed_password_456', 'Jane', 'Smith', '0987654321', 'RESTAURANT_OWNER', 'ACTIVE'),
    ('bob.jones@example.com', 'hashed_password_789', 'Bob', 'Jones', '1122334455', 'DELIVERY_DRIVER', 'INACTIVE'),
    ('admin@example.com', 'hashed_password_101', 'Admin', 'User', '2233445566', 'ADMIN', 'ACTIVE'),
    ('alice.white@example.com', 'hashed_password_102', 'Alice', 'White', '6677889900', 'CUSTOMER', 'SUSPENDED'),
    ('ram.white@example.com', 'hashed_password_102', 'Ram', 'Lakhan', '6677889900', 'CUSTOMER', 'SUSPENDED');




-- Insert test addresses (ensure consistency with user_id)
INSERT INTO addresses (user_id, street_address, city, state, postal_code, country)
VALUES
    (1, '123 Elm Street', 'New York', 'NY', '10001', 'USA'),
    (51, '456 Oak Avenue', 'Los Angeles', 'CA', '90001', 'USA'),
    (101, '789 Pine Road', 'Chicago', 'IL', '60001', 'USA'),
    (151, '101 Maple Lane', 'San Francisco', 'CA', '94101', 'USA'),
    (201, '202 Birch Boulevard', 'Miami', 'FL', '33101', 'USA');
