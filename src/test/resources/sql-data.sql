INSERT INTO `customers` (`first_name`, `last_name`) VALUES ('jordan', 'harrison');
INSERT INTO `products` (`title`, `price`) VALUES ('Halo Infinite', 54.99);
INSERT INTO `orders` (`fk_customer_id`) VALUES (1);
INSERT INTO `order_items` (`fk_order_id`, `fk_product_id`, `fk_customer_id`, `order_quantity`) VALUES (1, 1, 1, 1); 