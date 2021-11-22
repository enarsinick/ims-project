drop schema ims;

CREATE SCHEMA IF NOT EXISTS `ims`;

USE `ims` ;

-- customer table
CREATE TABLE IF NOT EXISTS `ims`.`customers` (
	`customer_id` int AUTO_INCREMENT,
    `first_name` varchar(255) NOT NULL,
    `last_name` varchar(255) NOT NULL,
    PRIMARY KEY (`customer_id`)
);

-- products/items table
CREATE TABLE IF NOT EXISTS `ims`.`products` (
	`product_id` int AUTO_INCREMENT,
    `title` varchar(255) NOT NULL,
    `price` decimal(5,2) NOT NULL,
    PRIMARY KEY (`product_id`)
);

-- orders table 
CREATE TABLE IF NOT EXISTS `ims`.`orders` (
	`order_id` int AUTO_INCREMENT, 
    `fk_customer_id` int NOT NULL, 
    PRIMARY KEY (`order_id`),
    FOREIGN KEY (`fk_customer_id`) REFERENCES customers(`customer_id`)
    ON DELETE CASCADE
);

-- order items joining table
CREATE TABLE IF NOT EXISTS `ims`.`order_items` (
	`order_items_id` int AUTO_INCREMENT, 
    `fk_order_id` int NOT NULL, 
    `fk_product_id` int NOT NULL, 
    `order_quantity` int NOT NULL, 
    PRIMARY KEY (`order_items_id`), 
    FOREIGN KEY (`fk_order_id`) REFERENCES orders(`order_id`), 
    FOREIGN KEY (`fk_product_id`) REFERENCES products(`product_id`),
    FOREIGN KEY (fk_customer_id) REFERENCES customers(customer_id)
    ON DELETE CASCADE
);