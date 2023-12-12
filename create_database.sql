CREATE TABLE `category` (
  `category_id` bigint PRIMARY KEY,
  `name` varchar(255)
);

CREATE TABLE `product` (
  `product_id` bigint PRIMARY KEY,
  `name` varchar(255),
  `description` varchar(255),
  `price` decimal
);

CREATE TABLE `product_category` (
  `product_id` bigint,
  `category_id` bigint,
  PRIMARY KEY (`product_id`, `category_id`),
  FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
);

CREATE TABLE `image` (
  `image_id` bigint PRIMARY KEY,
  `url` varchar(255),
  `product_id` bigint,
  FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
);

CREATE TABLE `size` (
  `size_id` bigint PRIMARY KEY,
  `name` varchar(255)
);

CREATE TABLE `product_size` (
  `size_id` bigint,
  `product_id` bigint
  PRIMARY KEY (`size_id`, `product_id`),
  FOREIGN KEY (`size_id`) REFERENCES `size` (`size_id`),
  FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
);

CREATE TABLE `inventory` (
  `inventory_id` bigint PRIMARY KEY,
  `product_id` bigint,
  `size_id` bigint,
  `quantity` int,
  `location` varchar(255),
  FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  FOREIGN KEY (`size_id`) REFERENCES `size` (`size_id`)
);

CREATE TABLE `user` (
  `user_id` bigint PRIMARY KEY,
  `username` varchar(255),
  `email` varchar(255),
  `password` varchar(255)
);

CREATE TABLE `address` (
  `address_id` bigint PRIMARY KEY,
  `street` varchar(255),
  `city` varchar(255),
  `country` varchar(255),
  `zip_code` varchar(255)
);

CREATE TABLE `user_address` (
  `user_id` bigint,
  `address_id` bigint,
  FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`)
);

CREATE TABLE `userprofile` (
  `userprofile_id` bigint PRIMARY KEY,
  `first_name` varchar(255),
  `last_name` varchar(255),
  `gender` varchar(255),
  `user_id` bigint
);

CREATE TABLE `order` (
  `order_id` bigint PRIMARY KEY,
  `creation_date` date,
  `status` varchar(255),
  `user_id` bigint
);

CREATE TABLE `order_item` (
  `order_id` bigint,
  `product_id` bigint,
  `size_name` varchar(255),
  `quantity` int,
  `subtotal` decimal,
  FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`),
  FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
);

CREATE TABLE `discount` (
  `discount_id` bigint PRIMARY KEY,
  `code` varchar(255),
  `description` varchar(255),
  `discount_type` varchar(255),
  `discount_value` decimal,
  `start_date` date,
  `end_date` date,
  `conditions` text
);

CREATE TABLE `user_coupon` (
  `user_coupon_id` bigint PRIMARY KEY,
  `user_id` bigint,
  `discount_id` bigint,
  FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  FOREIGN KEY (`discount`) REFERENCES `discount` (`discount_id`)
);

CREATE TABLE `rating` (
  `product_id` bigint,
  `user_id` bigint,
  `rating_value` decimal,
  `rating_text` varchar(255),
  `creation_date` date,
  PRIMARY KEY (`product_id`, `user_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
);

CREATE TABLE `product_discount` (
  `product_discount_id` bigint PRIMARY KEY,
  `product_id` bigint,
  `discount_id` bigint,
  FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  FOREIGN KEY (`discount_id`) REFERENCES `discount` (`discount_id`)
);
