CREATE SEQUENCE restaurant_id_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE restaurant_address_id_seq START WITH 1 INCREMENT BY 50;
CREATE sequence menu_id_seq start with 1 increment by 50;

CREATE TABLE restaurants (
                             id BIGINT DEFAULT nextval('restaurant_id_seq') PRIMARY KEY,
                             name TEXT NOT NULL,
                             description TEXT,
                             phone TEXT NOT NULL,
                             email TEXT NOT NULL UNIQUE,
                             owner_id BIGINT NOT NULL,
                             cuisine TEXT,
                             rating NUMERIC,
                             status TEXT NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE', 'PENDING_APPROVAL')),
                             opening_time TIME,
                             closing_time TIME,
                             delivery_fee NUMERIC NOT NULL,
                             minimum_order_amount NUMERIC NOT NULL,
                             estimated_delivery_time INTEGER,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO restaurants(
    name, description, phone, email, owner_id, cuisine, rating, status,
    opening_time, closing_time, delivery_fee, minimum_order_amount, estimated_delivery_time,
    created_at, updated_at
)
VALUES
    ('Spice Hub', 'Authentic Indian cuisine', '9999990001', 'spicehub@mail.com', 1, 'INDIAN', 4.5, 'ACTIVE', '10:00', '23:00', 30, 200, 40, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Green Bowl', 'Healthy vegan meals', '9999990002', 'greenbowl@mail.com', 2, 'VEGAN', 4.7, 'ACTIVE', '09:00', '22:00', 20, 150, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Pasta Palace', 'Italian pastas and pizzas', '9999990003', 'pastapalace@mail.com', 3, 'ITALIAN', 4.3, 'ACTIVE', '11:00', '23:30', 25, 250, 45, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Burger Barn', 'Juicy burgers and fries', '9999990004', 'burgerbarn@mail.com', 4, 'AMERICAN', 4.1, 'ACTIVE', '10:30', '22:30', 15, 120, 25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Sweet Treats', 'Desserts and beverages', '9999990005', 'sweettreats@mail.com', 5, 'DESSERTS', 4.6, 'ACTIVE', '08:00', '21:00', 10, 100, 20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


CREATE TABLE restaurant_addresses (
                                      id BIGINT DEFAULT nextval('restaurant_address_id_seq') PRIMARY KEY,
                                      restaurant_id BIGINT NOT NULL,
                                      street_address VARCHAR(255) NOT NULL,
                                      city VARCHAR(100) NOT NULL,
                                      state VARCHAR(100) NOT NULL,
                                      postal_code VARCHAR(20) NOT NULL,
                                      country VARCHAR(100) NOT NULL,
                                      latitude DECIMAL(10,7),
                                      longitude DECIMAL(10,7),
                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      CONSTRAINT fk_restaurant_address
                                          FOREIGN KEY (restaurant_id)
                                              REFERENCES restaurants(id)
                                              ON DELETE CASCADE
);
INSERT INTO restaurant_addresses(
    restaurant_id, street_address, city, state, postal_code, country, latitude, longitude, created_at, updated_at
)
VALUES
    (1, '12 MG Road', 'Bangalore', 'Karnataka', '560001', 'India', 12.971599, 77.594566, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (51, '45 Green Avenue', 'Pune', 'Maharashtra', '411001', 'India', 18.520430, 73.856743, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (101, '78 Roma Street', 'Mumbai', 'Maharashtra', '400001', 'India', 19.076090, 72.877426, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (151, '9 Burger Lane', 'Delhi', 'Delhi', '110001', 'India', 28.613939, 77.209023, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (201, '22 Sugar Street', 'Hyderabad', 'Telangana', '500001', 'India', 17.385044, 78.486671, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



CREATE TABLE menus (
                        id BIGINT DEFAULT nextval('menu_id_seq') PRIMARY KEY,
                        restaurant_id BIGINT NOT NULL,
                        name TEXT NOT NULL,
                        description TEXT,
                        price NUMERIC NOT NULL,
                        category TEXT NOT NULL CHECK (category IN ('APPETIZER', 'MAIN_COURSE', 'DESSERT', 'BEVERAGE')),
                        availability BOOLEAN NOT NULL,
                        image_url TEXT,
                        dietary_info TEXT NOT NULL CHECK (dietary_info IN (  'VEGAN',
                                                                             'VEGETARIAN',
                                                                             'GLUTEN_FREE',
                                                                             'DAIRY_FREE',
                                                                             'NUT_FREE',
                                                                             'HALAL',
                                                                             'KOSHER',
                                                                             'NONE')),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_menu_restaurant
                            FOREIGN KEY (restaurant_id)
                                REFERENCES restaurants(id)
                                ON DELETE CASCADE
);
-- --- Restaurant 1 ---
INSERT INTO menus(restaurant_id, name, description, price, category, availability, image_url, dietary_info, created_at, updated_at)
VALUES
    (1, 'Paneer Tikka', 'Grilled cottage cheese', 250, 'APPETIZER', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 'Chicken Biryani', 'Spiced basmati rice with chicken', 350, 'MAIN_COURSE', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 'Dal Makhani', 'Creamy lentil curry', 220, 'MAIN_COURSE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 'Butter Naan', 'Soft butter naan', 40, 'APPETIZER', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 'Gulab Jamun', 'Sweet milk dumplings', 120, 'DESSERT', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --- Restaurant 2 ---
INSERT INTO menus(restaurant_id, name, description, price, category, availability, image_url, dietary_info, created_at, updated_at)
VALUES
    (51, 'Vegan Buddha Bowl', 'Quinoa and veggies', 280, 'MAIN_COURSE', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (51, 'Avocado Toast', 'Whole grain toast with avocado', 180, 'APPETIZER', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (51, 'Vegan Burger', 'Plant-based patty', 260, 'MAIN_COURSE', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (51, 'Fruit Salad', 'Seasonal fruits', 150, 'DESSERT', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (51, 'Green Smoothie', 'Spinach and apple smoothie', 130, 'BEVERAGE', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --- Restaurant 3 ---
INSERT INTO menus(restaurant_id, name, description, price, category, availability, image_url, dietary_info, created_at, updated_at)
VALUES
    (101, 'Bruschetta', 'Toasted bread with tomatoes', 190, 'APPETIZER', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (101, 'Margherita Pizza', 'Classic cheese pizza', 420, 'MAIN_COURSE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (101, 'Penne Alfredo', 'Creamy white sauce pasta', 390, 'MAIN_COURSE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (101, 'Tiramisu', 'Coffee-flavored dessert', 240, 'DESSERT', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (101, 'Lemon Iced Tea', 'Refreshing beverage', 120, 'BEVERAGE', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --- Restaurant 4 ---
INSERT INTO menus(restaurant_id, name, description, price, category, availability, image_url, dietary_info, created_at, updated_at)
VALUES
    (151, 'Classic Veg Burger', 'Veg patty burger', 180, 'MAIN_COURSE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (151, 'Chicken Burger', 'Grilled chicken burger', 220, 'MAIN_COURSE', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (151, 'French Fries', 'Crispy fries', 120, 'APPETIZER', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (151, 'Cheese Fries', 'Fries with cheese', 150, 'APPETIZER', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (151, 'Chocolate Shake', 'Milkshake with chocolate', 160, 'BEVERAGE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --- Restaurant 5 ---
INSERT INTO menus(restaurant_id, name, description, price, category, availability, image_url, dietary_info, created_at, updated_at)
VALUES
    (201, 'Chocolate Lava Cake', 'Molten chocolate cake', 250, 'DESSERT', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (201, 'Vanilla Milkshake', 'Milkshake with vanilla ice cream', 130, 'BEVERAGE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (201, 'Coffee', 'Hot brewed coffee', 90, 'BEVERAGE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (201, 'Fruit Tart', 'Fruit-filled pastry', 180, 'DESSERT', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (201, 'Carrot Cake', 'Moist carrot cake', 220, 'DESSERT', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
