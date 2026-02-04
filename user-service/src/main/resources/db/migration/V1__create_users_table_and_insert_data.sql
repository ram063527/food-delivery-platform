
CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 50;

CREATE TABLE users (
                       id BIGINT DEFAULT nextval('users_id_seq') PRIMARY KEY,  -- Use sequence to generate user IDs
                       email VARCHAR(255) NOT NULL UNIQUE,    -- email, unique to each user
                       password VARCHAR(255) NOT NULL,        -- hashed password
                       first_name VARCHAR(100) NOT NULL,      -- user's first name
                       last_name VARCHAR(100) NOT NULL,       -- user's last name
                       phone VARCHAR(20),                     -- phone number, optional
                       role VARCHAR(50) CHECK (role IN ('CUSTOMER', 'RESTAURANT_OWNER', 'DELIVERY_DRIVER','ADMIN')) NOT NULL,  -- user role with constraint
                       status VARCHAR(50) CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED')) NOT NULL,  -- user status with constraint
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- creation timestamp
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- last update timestamp
 );



INSERT INTO users (email, password, first_name, last_name, phone, role, status, created_at, updated_at)
VALUES
    ('admin@admin.com', 'admin1234', 'Admin', 'Pal', '555-1234', 'ADMIN', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('john.doe@example.com', 'hashedpassword123', 'John', 'Doe', '555-1234', 'CUSTOMER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('jane.smith@example.com', 'hashedpassword456', 'Jane', 'Smith', '555-5678', 'RESTAURANT_OWNER', 'INACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('robert.jones@example.com', 'hashedpassword789', 'Robert', 'Jones', '555-9101', 'DELIVERY_DRIVER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('emily.brown@example.com', 'hashedpassword112', 'Emily', 'Brown', '555-1122', 'CUSTOMER', 'SUSPENDED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('michael.davis@example.com', 'hashedpassword334', 'Michael', 'Davis', '555-3344', 'RESTAURANT_OWNER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




