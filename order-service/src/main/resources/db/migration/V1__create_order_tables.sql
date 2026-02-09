/*
-- V1__create_order_tables.sql

Table :
orders
- id (primary key, auto-increment)
- orderNumber (unique)
- customerId (foreign key to customers table)
- restaurantId (foreign key to restaurants table)
- deliveryAddress Long (references user service address )
- orderStatus  (enum: CREATED, PAYMENT_PENDING, PAYMENT_COMPLETED, CONFIRMED, PREPARING, READY, OUT_FOR_DELIVERY, DELIVERED, CANCELLED, REFUNDED)
- totalAmount (BigDecimal)
- deliveryFee (BigDecimal - We will take it from Restaurant service)
- tax (BigDecimal - We will calculate it based on the total amount and some fixed percentage)
- discount (BigDecimal - We will calculate it based on the total amount and some fixed percentage )
- specialInstructions (TEXT)
- createdAt (timestamp)
- updatedAt (timestamp)
- confirmedDeliveredAt (timestamp)
- deliveredAt (timestamp)
- cancelledAt (timestamp)




OrderItems
- id (primary key, auto-increment)
- orderId (foreign key to orders table)
- menuItemId (foreign key to menu_items table in restaurant service)
- quantity (integer)
- unitPrice (BigDecimal - We will take it from Restaurant service)
- subTotal (BigDecimal - quantity * unitPrice)
- itemSpecialInstructions (TEXT)



OrderStatusHistory
- id (primary key, auto-increment)
- orderId (foreign key to orders table)
- fromStatus
- toStatus
- changedAt (timestamp)
- changedBy (userId or system)
- notes (TEXT - optional notes about the status change, e.g. reason for cancellation)




 */

CREATE SEQUENCE order_id_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE order_item_id_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE order_status_history_id_seq START WITH 1 INCREMENT BY 50;


CREATE TABLE orders (
    id BIGINT PRIMARY KEY DEFAULT nextval('order_id_seq'),
    order_number VARCHAR(255) UNIQUE NOT NULL,
    customer_id BIGINT NOT NULL,
    restaurant_id BIGINT NOT NULL,
    delivery_address_id BIGINT NOT NULL,
    order_status VARCHAR(50) NOT NULL DEFAULT 'CREATED',
    total_amount DECIMAL(10, 2) NOT NULL,
    delivery_fee DECIMAL(10, 2) NOT NULL,
    tax DECIMAL(10, 2) NOT NULL,
    discount DECIMAL(10, 2) NOT NULL,
    special_instructions TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    confirmed_at TIMESTAMP,
    delivered_at TIMESTAMP,
    cancelled_at TIMESTAMP
);

INSERT INTO orders (
    order_number,
    customer_id,
    restaurant_id,
    delivery_address_id,
    order_status,
    total_amount,
    delivery_fee,
    tax,
    discount,
    special_instructions,
    created_at,
    updated_at
) VALUES
      (
          'ORD-2026-001', 1, 1, 1,
          'DELIVERED', 31.50, 5.00, 2.50, 0.00,
          'Leave at the front door',
          CURRENT_TIMESTAMP - INTERVAL '1 day',
          CURRENT_TIMESTAMP - INTERVAL '1 day'
      ),
      (
          'ORD-2026-002', 51, 51, 51,
          'CONFIRMED', 25.00, 3.00, 2.00, 5.00,
          'Call upon arrival',
          CURRENT_TIMESTAMP - INTERVAL '2 hours',
          CURRENT_TIMESTAMP
      );



CREATE TABLE order_items (
    id BIGINT PRIMARY KEY DEFAULT nextval('order_item_id_seq'),
    order_id BIGINT NOT NULL,
    menu_item_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    sub_total DECIMAL(10, 2) NOT NULL,
    item_special_instructions TEXT,
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

INSERT INTO order_items (
    order_id,
    menu_item_id,
    quantity,
    unit_price,
    sub_total,
    item_special_instructions
) VALUES
-- Items for Order 1
(1, 1, 2, 12.50, 25.00, 'Extra spicy'),
(1, 101, 1, 3.50, 3.50, NULL),

-- Items for Order 2
(51, 151, 1, 9.75, 9.75, NULL);


CREATE TABLE order_status_history (
    id BIGINT PRIMARY KEY DEFAULT nextval('order_status_history_id_seq'),
    order_id BIGINT NOT NULL,
    from_status VARCHAR(50) NOT NULL,
    to_status VARCHAR(50) NOT NULL,
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    changed_by VARCHAR(255) NOT NULL,
    notes TEXT,
    CONSTRAINT fk_order_status_history_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

INSERT INTO order_status_history (
    order_id,
    from_status,
    to_status,
    changed_at,
    changed_by,
    notes
) VALUES
      (1, 'CREATED', 'CONFIRMED', CURRENT_TIMESTAMP - INTERVAL '23 hours', 'SYSTEM', 'Payment verified'),
      (1, 'CONFIRMED', 'DELIVERED', CURRENT_TIMESTAMP - INTERVAL '22 hours', 'DRIVER_007', 'Handed to customer'),
      (51, 'CREATED', 'CONFIRMED', CURRENT_TIMESTAMP - INTERVAL '1 hour', 'RESTAURANT_MANAGER', 'Order accepted');