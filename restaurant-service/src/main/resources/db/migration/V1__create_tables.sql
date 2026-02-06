CREATE SEQUENCE restaurant_id_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE restaurant_address_id_seq START WITH 1 INCREMENT BY 50;
CREATE sequence menu_id_seq start with 1 increment by 50;
CREATE SEQUENCE menu_item_id_seq START WITH 1 INCREMENT BY 50;

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
                       name TEXT NOT NULL, -- e.g., 'Main Menu', 'Breakfast', 'Drinks'
                       description TEXT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT fk_menu_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE
);



INSERT INTO menus (restaurant_id, name, description, created_at, updated_at) VALUES
                                                                                 (1, 'Main Menu', 'The core selection for Spice Hub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                 (1, 'Breakfast', 'Morning offerings', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                 (1, 'Drinks', 'Beverages and mixers', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                 (51, 'Main Menu', 'Vegan-friendly staples', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                 (51, 'Brunch', 'Late morning comfort foods', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                 (101, 'Main Menu', 'Pasta, pizza, and more', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                 (151, 'Main Menu', 'Burger-focused menu', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                 (201, 'Dessert Menu', 'Sweet treats and desserts', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 5. Create Menu Items Table (The Dishes)
CREATE TABLE menu_items (
                            id BIGINT DEFAULT nextval('menu_item_id_seq') PRIMARY KEY,
                            menu_id BIGINT NOT NULL,
                            name TEXT NOT NULL,
                            description TEXT,
                            price NUMERIC NOT NULL,
                            category TEXT NOT NULL CHECK (category IN ('APPETIZER', 'MAIN_COURSE', 'DESSERT', 'BEVERAGE', 'BREAKFAST')),
                            availability BOOLEAN NOT NULL,
                            image_url TEXT,
                            dietary_info TEXT NOT NULL CHECK (dietary_info IN ('VEGAN', 'VEGETARIAN', 'GLUTEN_FREE', 'DAIRY_FREE', 'NUT_FREE', 'HALAL', 'KOSHER', 'NONE')),
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            CONSTRAINT fk_menu_item_menu FOREIGN KEY (menu_id) REFERENCES menus(id) ON DELETE CASCADE
);

INSERT INTO menu_items (menu_id, name, description, price, category, availability, image_url, dietary_info, created_at, updated_at) VALUES
-- Menu 1: Spice Hub Main Menu
(1, 'Butter Chicken', 'Creamy tomato gravy with tender chicken', 12.50, 'MAIN_COURSE', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Paneer Tikka', 'Grilled paneer with spices', 11.00, 'MAIN_COURSE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Garlic Naan', 'Buttery flatbread', 3.50, 'APPETIZER', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Chana Masala', 'Chickpea curry', 9.50, 'MAIN_COURSE', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Mango Lassi', 'Mango yogurt drink', 4.50, 'BEVERAGE', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Gulab Jamun', 'Warm syrup-soaked dumplings', 6.00, 'DESSERT', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Samosa', 'Spiced potato pastries', 4.00, 'APPETIZER', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Dal Tadka', 'Lentil curry with tempering', 8.50, 'MAIN_COURSE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Raita', 'Yogurt with cucumber', 2.75, 'DESSERT', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Tandoori Chicken', 'Spiced grilled chicken', 13.50, 'MAIN_COURSE', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Nimbu Pani', 'Lemonade douche', 3.00, 'BEVERAGE', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Chicken Biryani', 'Fragrant basmati rice with spiced chicken', 14.50, 'MAIN_COURSE', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Vegetable Biryani', 'Basmati rice with mixed vegetables', 12.00, 'MAIN_COURSE', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Palak Paneer', 'Spinach curry with paneer cubes', 11.75, 'MAIN_COURSE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Chole Bhature', 'Spiced chickpeas with fried bread', 10.50, 'MAIN_COURSE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Aloo Gobi', 'Potato and cauliflower curry', 9.25, 'MAIN_COURSE', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Onion Pakora', 'Fried onion fritters', 4.75, 'APPETIZER', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Masala Papad', 'Roasted papad with spices', 3.25, 'APPETIZER', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Kheer', 'Rice pudding with cardamom', 5.75, 'DESSERT', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Lemon Soda', 'Sparkling lemon drink', 3.50, 'BEVERAGE', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Masala Chaas', 'Spiced buttermilk', 3.00, 'BEVERAGE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Menu 2: Spice Hub Breakfast
(51, 'Masala Omelette', 'Eggs with spices', 7.50, 'MAIN_COURSE', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(51, 'Paratha with Aloo', 'Stuffed flatbread with potato', 6.50, 'MAIN_COURSE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(51, 'Masala Chai', 'Spiced tea', 2.80, 'BEVERAGE', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(51, 'Idli Sambhar', 'Steamed rice cakes with lentil soup', 5.50, 'MAIN_COURSE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(51, 'Poha', 'Flattened rice with spices', 5.25, 'MAIN_COURSE', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Menu 3: Spice Hub Drinks
(101, 'Masala Soda', 'Sparkling spiced soda', 3.75, 'BEVERAGE', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101, 'Lassi', 'Sweet yogurt drink', 4.25, 'BEVERAGE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101, 'Rose Milk', 'Milk with rose flavor', 3.95, 'BEVERAGE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Menu 4: Green Bowl Main Menu
(151, 'Quinoa Bowl', 'Quinoa with veggies and tahini', 9.75, 'MAIN_COURSE', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(151, 'Tofu Stir-Fry', 'Tofu with mixed vegetables', 11.25, 'MAIN_COURSE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(151, 'Kale Salad', 'Kale, quinoa, citrus dressing', 8.50, 'APPETIZER', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(151, 'Green Smoothie', 'Spinach, banana, almond milk', 5.50, 'BEVERAGE', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Menu 5: Green Bowl Breakfast
(201, 'Overnight Oats', 'Oats with chia and berries', 4.75, 'BREAKFAST', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(201, 'Avocado Toast', 'Whole grain toast with avocado', 6.00, 'BREAKFAST', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(201, 'Green Juice', 'Spinach and apple juice', 4.50, 'BEVERAGE', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Menu 6: Pasta Palace Main Menu
(251, 'Spaghetti Carbonara', 'Creamy egg and cheese sauce', 12.00, 'MAIN_COURSE', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(251, 'Margherita Pizza', 'Tomato, mozzarella, basil', 10.50, 'MAIN_COURSE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(251, 'Tiramisu', 'Coffee-flavored Italian dessert', 6.75, 'DESSERT', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(251, 'Bruschetta', 'Grilled bread with tomato topping', 6.25, 'APPETIZER', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(251, 'Fettuccine Alfredo', 'Creamy fettuccine with Parmesan', 11.50, 'MAIN_COURSE', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(251, 'Penne Arrabbiata', 'Penne with spicy tomato sauce', 10.75, 'MAIN_COURSE', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(251, 'Caprese Salad', 'Tomato, mozzarella, basil', 7.50, 'APPETIZER', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(251, 'Caesar Salad', 'Romaine with Caesar dressing', 7.25, 'APPETIZER', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(251, 'Garlic Bread', 'Toasted bread with garlic butter', 4.50, 'APPETIZER', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(251, 'Lemon Soda', 'Sparkling lemon drink', 3.25, 'BEVERAGE', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(251, 'Iced Tea', 'Sweetened iced tea', 3.00, 'BEVERAGE', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(251, 'Lemon Tart', 'Tangy lemon dessert', 5.25, 'DESSERT', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(251, 'Panna Cotta', 'Creamy Italian custard', 5.75, 'DESSERT', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(251, 'Cannoli', 'Crispy pastry filled with sweet ricotta', 4.75, 'DESSERT', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Menu 7: Burger Barn Main Menu
(301, 'Classic Burger', 'Beef patty with lettuce and tomato', 9.99, 'MAIN_COURSE', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(301, 'Cheese Fries', 'Fries with melted cheese', 4.99, 'APPETIZER', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(301, 'Milkshake', 'Vanilla or chocolate', 3.99, 'BEVERAGE', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(301, 'Veggie Burger', 'Grilled veggie patty', 9.49, 'MAIN_COURSE', TRUE, NULL, 'VEGAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Menu 8: Sweet Treats Dessert Menu
(351, 'Chocolate Cake', 'Rich dark chocolate cake', 5.50, 'DESSERT', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(351, 'Ice Cream Sundae', 'Vanilla ice cream with toppings', 4.75, 'DESSERT', TRUE, NULL, 'NONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(351, 'Baklava', 'Crispy pastry with nuts', 4.25, 'DESSERT', TRUE, NULL, 'NUT_FREE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(351, 'Custard Pudding', 'Silky custard dessert', 4.00, 'DESSERT', TRUE, NULL, 'VEGETARIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

