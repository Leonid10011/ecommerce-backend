-- MySQL dump 10.13  Distrib 8.0.35, for Linux (x86_64)
--
-- Host: localhost    Database: ecommerce
-- ------------------------------------------------------
-- Server version	8.0.35-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Address`
--

DROP TABLE IF EXISTS `Address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Address` (
  `id` bigint NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `zipCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Address`
--

LOCK TABLES `Address` WRITE;
/*!40000 ALTER TABLE `Address` DISABLE KEYS */;
INSERT INTO `Address` VALUES (1,'t','Country',NULL,'TIP'),(51,'t','Country',NULL,'TIP'),(101,'t','Country',NULL,'TIP'),(151,'t','Country','Street','TIP'),(201,'t','Country','Street','TIP'),(251,'t','Country','Street','TIP'),(301,'t','Country','Street','TIP'),(302,'t','Country','Street','TIP'),(303,'t','Country','Street','TIP'),(304,'123','123','123','123'),(305,'m31231','m3132','m31231','m312313'),(306,'123','123','123','123'),(307,'456','456','4','564'),(308,'456','456','4','56'),(351,'123','456','123','456'),(352,'düren','engliand','am be','1234');
/*!40000 ALTER TABLE `Address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Address_SEQ`
--

DROP TABLE IF EXISTS `Address_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Address_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Address_SEQ`
--

LOCK TABLES `Address_SEQ` WRITE;
/*!40000 ALTER TABLE `Address_SEQ` DISABLE KEYS */;
INSERT INTO `Address_SEQ` VALUES (1401);
/*!40000 ALTER TABLE `Address_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BuyOrder`
--

DROP TABLE IF EXISTS `BuyOrder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BuyOrder` (
  `id` bigint NOT NULL,
  `Status` varchar(255) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `userId` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BuyOrder`
--

LOCK TABLES `BuyOrder` WRITE;
/*!40000 ALTER TABLE `BuyOrder` DISABLE KEYS */;
/*!40000 ALTER TABLE `BuyOrder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BuyOrder_SEQ`
--

DROP TABLE IF EXISTS `BuyOrder_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BuyOrder_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BuyOrder_SEQ`
--

LOCK TABLES `BuyOrder_SEQ` WRITE;
/*!40000 ALTER TABLE `BuyOrder_SEQ` DISABLE KEYS */;
INSERT INTO `BuyOrder_SEQ` VALUES (1);
/*!40000 ALTER TABLE `BuyOrder_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CartItem`
--

DROP TABLE IF EXISTS `CartItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CartItem` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint DEFAULT NULL,
  `productId` bigint DEFAULT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=752 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CartItem`
--

LOCK TABLES `CartItem` WRITE;
/*!40000 ALTER TABLE `CartItem` DISABLE KEYS */;
/*!40000 ALTER TABLE `CartItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CartItem_SEQ`
--

DROP TABLE IF EXISTS `CartItem_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CartItem_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CartItem_SEQ`
--

LOCK TABLES `CartItem_SEQ` WRITE;
/*!40000 ALTER TABLE `CartItem_SEQ` DISABLE KEYS */;
INSERT INTO `CartItem_SEQ` VALUES (801);
/*!40000 ALTER TABLE `CartItem_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Category`
--

DROP TABLE IF EXISTS `Category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1952 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Category`
--

LOCK TABLES `Category` WRITE;
/*!40000 ALTER TABLE `Category` DISABLE KEYS */;
INSERT INTO `Category` VALUES (1,'T-Shirt'),(2,'Hose'),(3,'Pullover'),(1003,'Pullover');
/*!40000 ALTER TABLE `Category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Category_SEQ`
--

DROP TABLE IF EXISTS `Category_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Category_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Category_SEQ`
--

LOCK TABLES `Category_SEQ` WRITE;
/*!40000 ALTER TABLE `Category_SEQ` DISABLE KEYS */;
INSERT INTO `Category_SEQ` VALUES (2001);
/*!40000 ALTER TABLE `Category_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Discount`
--

DROP TABLE IF EXISTS `Discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Discount` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `description` text,
  `discountType` varchar(20) NOT NULL,
  `value` double NOT NULL,
  `startDate` datetime(6) DEFAULT NULL,
  `endDate` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=252 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Discount`
--

LOCK TABLES `Discount` WRITE;
/*!40000 ALTER TABLE `Discount` DISABLE KEYS */;
INSERT INTO `Discount` VALUES (1,'CODE','DESCRIPTION','TYPE',1,'2023-10-16 17:49:16.242000','2023-10-16 19:49:16.242000'),(51,'CODE2','DESCRIPTION','TYPE',1,'2023-10-16 18:09:28.300000','2023-10-16 18:09:28.300000'),(101,'CODE3','DESCRIPTION','TYPE',1,'2023-10-16 18:10:01.698000','2023-10-16 18:10:01.698000'),(151,'CODE4','DESCRIPTION','TYPE',1,'2023-10-16 18:10:34.895000','2023-10-16 18:10:34.895000');
/*!40000 ALTER TABLE `Discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Discount_SEQ`
--

DROP TABLE IF EXISTS `Discount_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Discount_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Discount_SEQ`
--

LOCK TABLES `Discount_SEQ` WRITE;
/*!40000 ALTER TABLE `Discount_SEQ` DISABLE KEYS */;
INSERT INTO `Discount_SEQ` VALUES (301);
/*!40000 ALTER TABLE `Discount_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FavoriteProduct`
--

DROP TABLE IF EXISTS `FavoriteProduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FavoriteProduct` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint DEFAULT NULL,
  `productId` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `productId` (`productId`),
  CONSTRAINT `FavoriteProduct_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`id`),
  CONSTRAINT `FavoriteProduct_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `Product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1217 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FavoriteProduct`
--

LOCK TABLES `FavoriteProduct` WRITE;
/*!40000 ALTER TABLE `FavoriteProduct` DISABLE KEYS */;
INSERT INTO `FavoriteProduct` VALUES (101,3251,3903),(102,3251,3904),(151,3301,3953),(152,3301,3954),(201,3351,4003),(202,3351,4004),(251,3401,4053),(751,3702,4355),(752,3702,4356),(853,1801,2370),(854,1801,2368),(857,1801,2376),(1201,1801,2363),(1202,1801,2363),(1203,1801,2363),(1204,1801,2363),(1212,1801,2365),(1213,1801,2362),(1214,1801,2361),(1215,1801,3903),(1216,1801,4356);
/*!40000 ALTER TABLE `FavoriteProduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FavoriteProduct_SEQ`
--

DROP TABLE IF EXISTS `FavoriteProduct_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FavoriteProduct_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FavoriteProduct_SEQ`
--

LOCK TABLES `FavoriteProduct_SEQ` WRITE;
/*!40000 ALTER TABLE `FavoriteProduct_SEQ` DISABLE KEYS */;
INSERT INTO `FavoriteProduct_SEQ` VALUES (1251);
/*!40000 ALTER TABLE `FavoriteProduct_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Inventory`
--

DROP TABLE IF EXISTS `Inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Inventory` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `productID` bigint DEFAULT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `productID` (`productID`),
  CONSTRAINT `Inventory_ibfk_1` FOREIGN KEY (`productID`) REFERENCES `Product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3507 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Inventory`
--

LOCK TABLES `Inventory` WRITE;
/*!40000 ALTER TABLE `Inventory` DISABLE KEYS */;
INSERT INTO `Inventory` VALUES (1152,2361,90),(1153,2362,98),(1154,2363,83),(1155,2364,95),(1156,2365,72),(1157,2366,68),(1158,2367,100),(1159,2368,100),(1160,2369,100),(1161,2370,100),(1162,2371,96),(1163,2372,100),(1164,2373,94),(1165,2374,100),(1166,2375,100),(1167,2376,100),(1168,2377,100),(1169,2378,100),(1401,NULL,1222),(1451,NULL,100),(1501,NULL,100),(1551,NULL,100),(1601,NULL,100),(1651,NULL,100),(1652,NULL,100),(1851,NULL,10),(1901,NULL,100),(1902,NULL,100),(1951,NULL,10),(1952,NULL,100),(1953,NULL,100),(2001,NULL,10),(2002,NULL,100),(2003,NULL,100),(2051,NULL,10),(2101,NULL,10),(2151,NULL,10),(2201,NULL,10),(2251,NULL,10),(2301,NULL,10),(2351,NULL,10),(2401,NULL,10),(2451,NULL,10),(2452,NULL,10),(2453,NULL,100),(2454,NULL,100),(2501,NULL,100),(2502,NULL,100),(2503,NULL,100),(2551,NULL,100),(2552,NULL,100),(2553,NULL,100),(2601,NULL,100),(2602,NULL,100),(2603,NULL,100),(2604,NULL,100),(2651,NULL,100),(2652,NULL,100),(2653,NULL,100),(2654,NULL,100),(2701,NULL,100),(2702,NULL,100),(2703,NULL,100),(2704,NULL,100),(2751,NULL,100),(2752,NULL,100),(2753,NULL,100),(2754,NULL,100),(2801,NULL,100),(2802,NULL,100),(2803,NULL,100),(2804,NULL,100),(2851,NULL,100),(2852,NULL,100),(2853,NULL,100),(2854,NULL,100),(2901,NULL,100),(2902,NULL,100),(2903,NULL,100),(2904,NULL,100),(2951,NULL,100),(2952,NULL,100),(2953,NULL,100),(2954,NULL,100),(3001,NULL,10),(3002,NULL,10),(3003,NULL,100),(3004,NULL,100),(3005,NULL,100),(3006,NULL,100),(3051,NULL,10),(3052,NULL,10),(3053,NULL,100),(3054,NULL,100),(3055,NULL,100),(3056,NULL,100),(3101,NULL,10),(3102,NULL,10),(3103,NULL,100),(3104,NULL,100),(3105,NULL,100),(3106,NULL,100),(3151,NULL,10),(3152,NULL,10),(3153,NULL,100),(3154,NULL,100),(3155,NULL,100),(3156,NULL,100),(3201,NULL,10),(3202,NULL,10),(3203,NULL,100),(3204,NULL,100),(3205,NULL,100),(3206,NULL,100),(3251,NULL,10),(3252,NULL,10),(3253,NULL,100),(3254,NULL,100),(3255,NULL,100),(3256,NULL,100),(3301,NULL,10),(3302,NULL,10),(3303,NULL,100),(3304,NULL,100),(3305,NULL,100),(3306,NULL,100),(3351,NULL,10),(3352,NULL,10),(3353,NULL,100),(3354,NULL,100),(3355,NULL,100),(3356,NULL,100),(3401,NULL,10),(3402,NULL,10),(3403,NULL,100),(3404,NULL,100),(3405,NULL,100),(3406,NULL,100),(3451,NULL,10),(3452,NULL,10),(3453,NULL,100),(3454,NULL,100),(3455,NULL,100),(3456,NULL,100),(3501,NULL,10),(3502,NULL,10),(3503,NULL,100),(3504,NULL,100),(3505,NULL,100),(3506,NULL,100);
/*!40000 ALTER TABLE `Inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Inventory_SEQ`
--

DROP TABLE IF EXISTS `Inventory_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Inventory_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Inventory_SEQ`
--

LOCK TABLES `Inventory_SEQ` WRITE;
/*!40000 ALTER TABLE `Inventory_SEQ` DISABLE KEYS */;
INSERT INTO `Inventory_SEQ` VALUES (3551);
/*!40000 ALTER TABLE `Inventory_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrderHistory`
--

DROP TABLE IF EXISTS `OrderHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OrderHistory` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userID` bigint DEFAULT NULL,
  `orderID` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userID` (`userID`),
  KEY `orderID` (`orderID`),
  CONSTRAINT `OrderHistory_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `User` (`id`),
  CONSTRAINT `OrderHistory_ibfk_2` FOREIGN KEY (`orderID`) REFERENCES `Orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderHistory`
--

LOCK TABLES `OrderHistory` WRITE;
/*!40000 ALTER TABLE `OrderHistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `OrderHistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrderHistory_SEQ`
--

DROP TABLE IF EXISTS `OrderHistory_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OrderHistory_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderHistory_SEQ`
--

LOCK TABLES `OrderHistory_SEQ` WRITE;
/*!40000 ALTER TABLE `OrderHistory_SEQ` DISABLE KEYS */;
INSERT INTO `OrderHistory_SEQ` VALUES (1);
/*!40000 ALTER TABLE `OrderHistory_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrderItem`
--

DROP TABLE IF EXISTS `OrderItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OrderItem` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `orderId` bigint DEFAULT NULL,
  `productId` bigint DEFAULT NULL,
  `quantity` int NOT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl8avcrunmvqdcldoec2duhdiq` (`productId`),
  CONSTRAINT `FKl8avcrunmvqdcldoec2duhdiq` FOREIGN KEY (`productId`) REFERENCES `Product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1455 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderItem`
--

LOCK TABLES `OrderItem` WRITE;
/*!40000 ALTER TABLE `OrderItem` DISABLE KEYS */;
INSERT INTO `OrderItem` VALUES (451,1,2368,1,88.15),(901,1,2366,6,22),(952,1,2365,4,27.66),(957,1,2367,1,41.08),(958,1,2369,4,43.02),(1451,1851,2361,2,28.62),(1452,1851,2362,3,22.01),(1453,1851,2363,3,27.43),(1454,1851,2364,6,37.38);
/*!40000 ALTER TABLE `OrderItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrderItem_SEQ`
--

DROP TABLE IF EXISTS `OrderItem_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OrderItem_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderItem_SEQ`
--

LOCK TABLES `OrderItem_SEQ` WRITE;
/*!40000 ALTER TABLE `OrderItem_SEQ` DISABLE KEYS */;
INSERT INTO `OrderItem_SEQ` VALUES (1501);
/*!40000 ALTER TABLE `OrderItem_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Order_SEQ`
--

DROP TABLE IF EXISTS `Order_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Order_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Order_SEQ`
--

LOCK TABLES `Order_SEQ` WRITE;
/*!40000 ALTER TABLE `Order_SEQ` DISABLE KEYS */;
INSERT INTO `Order_SEQ` VALUES (1);
/*!40000 ALTER TABLE `Order_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Orders`
--

DROP TABLE IF EXISTS `Orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint DEFAULT NULL,
  `status` varchar(50) NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `only_one_order_per_user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=1857 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Orders`
--

LOCK TABLES `Orders` WRITE;
/*!40000 ALTER TABLE `Orders` DISABLE KEYS */;
INSERT INTO `Orders` VALUES (1,1801,'Test',NULL),(1801,4120,'open','2023-11-04 19:24:45.325000'),(1802,4121,'open','2023-11-04 19:25:22.370000'),(1851,4151,'open','2023-11-04 20:25:08.058000'),(1852,4152,'open','2023-11-04 20:49:22.794000'),(1853,4157,'open','2023-11-04 20:59:58.395000'),(1854,NULL,'open','2023-11-04 21:02:18.238000'),(1855,NULL,'open','2023-11-04 21:03:22.538000'),(1856,NULL,'open','2023-11-04 21:03:51.425000');
/*!40000 ALTER TABLE `Orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Orders_SEQ`
--

DROP TABLE IF EXISTS `Orders_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Orders_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Orders_SEQ`
--

LOCK TABLES `Orders_SEQ` WRITE;
/*!40000 ALTER TABLE `Orders_SEQ` DISABLE KEYS */;
INSERT INTO `Orders_SEQ` VALUES (1901);
/*!40000 ALTER TABLE `Orders_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PaymentTransaction`
--

DROP TABLE IF EXISTS `PaymentTransaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PaymentTransaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `orderID` bigint DEFAULT NULL,
  `transactionDate` date NOT NULL,
  `paymentMethod` varchar(50) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `orderID` (`orderID`),
  CONSTRAINT `PaymentTransaction_ibfk_1` FOREIGN KEY (`orderID`) REFERENCES `Orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PaymentTransaction`
--

LOCK TABLES `PaymentTransaction` WRITE;
/*!40000 ALTER TABLE `PaymentTransaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `PaymentTransaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Product`
--

DROP TABLE IF EXISTS `Product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text,
  `price` double DEFAULT NULL,
  `categoryID` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `categoryID` (`categoryID`),
  KEY `idx_product_name` (`name`),
  CONSTRAINT `Product_ibfk_1` FOREIGN KEY (`categoryID`) REFERENCES `Category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4707 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Product`
--

LOCK TABLES `Product` WRITE;
/*!40000 ALTER TABLE `Product` DISABLE KEYS */;
INSERT INTO `Product` VALUES (2361,'Waterflow 2','Hohe Qualität und Stil vereint in einem Produkt.',28.62,2),(2362,'Lionfur 3','Das perfekte Kleidungsstück für jede Gelegenheit.',22.01,1),(2363,'PaperWhite 2','Ein großartiges Produkt für Ihren Kleiderschrank.',27.43,1),(2364,'Lionfur 1','Ein großartiges Produkt für Ihren Kleiderschrank.',37.38,2),(2365,'LeafGreen 1','Hohe Qualität und Stil vereint in einem Produkt.',27.66,2),(2366,'Treespark 1','Ein großartiges Produkt für Ihren Kleiderschrank.',22,2),(2367,'Waterflow 3','Ein großartiges Produkt für Ihren Kleiderschrank.',41.08,3),(2368,'AsoluteWarmth 1','Hohe Qualität und Stil vereint in einem Produkt.',88.15,1),(2369,'Treespark 3','Das perfekte Kleidungsstück für jede Gelegenheit.',43.02,1),(2370,'Waterflow 1','Das perfekte Kleidungsstück für jede Gelegenheit.',66.58,1),(2371,'LeafGreen 2','Das perfekte Kleidungsstück für jede Gelegenheit.',15.91,3),(2372,'Treespark 2','Hohe Qualität und Stil vereint in einem Produkt.',55.15,2),(2373,'LeafGreen 3','Das perfekte Kleidungsstück für jede Gelegenheit.',21.41,1),(2374,'PaperWhite 1','Hohe Qualität und Stil vereint in einem Produkt.',32.07,1),(2375,'PaperWhite 3','Das perfekte Kleidungsstück für jede Gelegenheit.',53.68,3),(2376,'Lionfur 2','Ein großartiges Produkt für Ihren Kleiderschrank.',18.44,2),(2377,'AsoluteWarmth 3','Hohe Qualität und Stil vereint in einem Produkt.',91.39,2),(2378,'AsoluteWarmth 2','Das perfekte Kleidungsstück für jede Gelegenheit.',31.46,1),(2601,'Postman13532','',9999999999.99,1),(3703,'TestProduct0','For testing purposes',99.99,1),(3753,'TestProduct0123','For testing purposes',99.99,1),(3803,'TestProduct01323','For testing purposes',99.99,1),(3804,'TestProduct11233','For testing purposes',99.99,1),(3853,'TestProduct012323','For testing purposes',99.99,1),(3854,'TestProduct112233','For testing purposes',99.99,1),(3903,'TestProduct0132323','For testing purposes',99.99,1),(3904,'TestProduct1132233','For testing purposes',99.99,1),(3953,'TestProduct03132323','For testing purposes',99.99,1),(3954,'TestProduct11323233','For testing purposes',99.99,1),(4003,'TESTP123','For testing purposes',99.99,1),(4004,'TESTP12','For testing purposes',99.99,1),(4053,'TESTP13123','For testing purposes',99.99,1),(4054,'TESTP1122','For testing purposes',99.99,1),(4355,'TESTP1312312313','For testing purposes',99.99,1),(4356,'TESTP11331122','For testing purposes',99.99,1);
/*!40000 ALTER TABLE `Product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProductImage`
--

DROP TABLE IF EXISTS `ProductImage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ProductImage` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `productID` bigint DEFAULT NULL,
  `imageURL` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `productID` (`productID`),
  CONSTRAINT `ProductImage_ibfk_1` FOREIGN KEY (`productID`) REFERENCES `Product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=452 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProductImage`
--

LOCK TABLES `ProductImage` WRITE;
/*!40000 ALTER TABLE `ProductImage` DISABLE KEYS */;
INSERT INTO `ProductImage` VALUES (52,2361,'test2.jpeg'),(301,NULL,'test22.jpeg'),(351,NULL,'image url'),(401,NULL,'No URL yet'),(451,NULL,'http://test.de');
/*!40000 ALTER TABLE `ProductImage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProductImage_SEQ`
--

DROP TABLE IF EXISTS `ProductImage_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ProductImage_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProductImage_SEQ`
--

LOCK TABLES `ProductImage_SEQ` WRITE;
/*!40000 ALTER TABLE `ProductImage_SEQ` DISABLE KEYS */;
INSERT INTO `ProductImage_SEQ` VALUES (501);
/*!40000 ALTER TABLE `ProductImage_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Product_SEQ`
--

DROP TABLE IF EXISTS `Product_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Product_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Product_SEQ`
--

LOCK TABLES `Product_SEQ` WRITE;
/*!40000 ALTER TABLE `Product_SEQ` DISABLE KEYS */;
INSERT INTO `Product_SEQ` VALUES (4751);
/*!40000 ALTER TABLE `Product_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Rating`
--

DROP TABLE IF EXISTS `Rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Rating` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `rating` int NOT NULL,
  `text` text,
  `date` datetime(6) DEFAULT NULL,
  `userId` bigint DEFAULT NULL,
  `productId` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userID` (`userId`),
  KEY `productID` (`productId`),
  CONSTRAINT `Rating_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`id`),
  CONSTRAINT `Rating_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `Product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=452 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Rating`
--

LOCK TABLES `Rating` WRITE;
/*!40000 ALTER TABLE `Rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `Rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Rating_SEQ`
--

DROP TABLE IF EXISTS `Rating_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Rating_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Rating_SEQ`
--

LOCK TABLES `Rating_SEQ` WRITE;
/*!40000 ALTER TABLE `Rating_SEQ` DISABLE KEYS */;
INSERT INTO `Rating_SEQ` VALUES (501);
/*!40000 ALTER TABLE `Rating_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Review`
--

DROP TABLE IF EXISTS `Review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `rating` int NOT NULL,
  `text` text,
  `date` date NOT NULL,
  `userID` bigint DEFAULT NULL,
  `productID` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userID` (`userID`),
  KEY `productID` (`productID`),
  CONSTRAINT `Review_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `User` (`id`),
  CONSTRAINT `Review_ibfk_2` FOREIGN KEY (`productID`) REFERENCES `Product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Review`
--

LOCK TABLES `Review` WRITE;
/*!40000 ALTER TABLE `Review` DISABLE KEYS */;
/*!40000 ALTER TABLE `Review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Role`
--

DROP TABLE IF EXISTS `Role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Role`
--

LOCK TABLES `Role` WRITE;
/*!40000 ALTER TABLE `Role` DISABLE KEYS */;
INSERT INTO `Role` VALUES (1,'User');
/*!40000 ALTER TABLE `Role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Role_SEQ`
--

DROP TABLE IF EXISTS `Role_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Role_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Role_SEQ`
--

LOCK TABLES `Role_SEQ` WRITE;
/*!40000 ALTER TABLE `Role_SEQ` DISABLE KEYS */;
INSERT INTO `Role_SEQ` VALUES (1);
/*!40000 ALTER TABLE `Role_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Transaction`
--

DROP TABLE IF EXISTS `Transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `orderId` bigint DEFAULT NULL,
  `transactionDate` datetime(6) DEFAULT NULL,
  `paymentMethod` varchar(50) NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Transaction`
--

LOCK TABLES `Transaction` WRITE;
/*!40000 ALTER TABLE `Transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `Transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Transaction_SEQ`
--

DROP TABLE IF EXISTS `Transaction_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Transaction_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Transaction_SEQ`
--

LOCK TABLES `Transaction_SEQ` WRITE;
/*!40000 ALTER TABLE `Transaction_SEQ` DISABLE KEYS */;
INSERT INTO `Transaction_SEQ` VALUES (1);
/*!40000 ALTER TABLE `Transaction_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `forename` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `address` bigint DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `roleId` bigint DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4158 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1551,'Test','User','Mock',101,'123456','l@g.com',1,'$2a$10$XyqH6FdazQc0bQZ2BYPysOyQCLqXnhXP8nFCHDhfHxR1zZh0FIyaG'),(1601,'Test2','User','Mock',151,'123456','l@g.com',1,'$2a$10$/.PeL1Z5Ur20UTURwQHyue6cGEwBpbYYDbIFTsjdoeE5rI.Ss8FZq'),(1751,'Test32','User','Mock',301,'123456','l@g.com',1,'$2a$10$6u7e4.2T0a9ylkusAXgtyefGeN4wmvcV7IIiIBHBLD9V9MEzX8viG'),(1752,'Test132','User','Mock',302,'123456','l@g.com',1,'$2a$10$2ZKU8sOaokXpt3zjfx6o6.FAWK691OW8zue38ksu8fBkPoA7Ed6zq'),(1753,'Test32as','User','Mock',303,'123456','l@g.com',1,'$2a$10$h5/48HRfnb411vaXLupS1ekwW5TIjMyrndqTkSIbGAFoOao875qNW'),(1754,'m123as','123','123',304,'123','123',1,'$2a$10$x4Cmz2T4Smjsgo3Na.BthuuN2boCKyhO32AN.kzrHDPI13fRcnJle'),(1755,'m12weas','m312314','m123',305,'m1321','m12@3.de',1,'$2a$10$uz/E4GmXf1WMEUa2.4VEAum/NI4HZqmS0J.WHiBBxVArdWx9M.jSq'),(1756,'123asdfa','123','123',306,'123','123',1,'$2a$10$eQdf.0irWXkih10Dt3Tqi.oeAqtYfOBw8bS0AylGyvS1BShzDPL1K'),(1757,'4s5a4d654','456','46',307,'56','456',1,'$2a$10$zc0Yarwz0v/qQN1QC8dRXOUM6XojmP/3o0TSOHMFpeK9.zdYWbPYq'),(1758,'1231231','esd','456',308,'456','456',1,'$2a$10$Cn7y5ceVFR8H5KBcet9VFOiTH1AI4cpmiZqJOnKr6b2xPH/LrUh02'),(1801,'L','123','456',351,'123','456',1,'$2a$10$GeTJsUq8y5CuhEvxOvH66.NE01ahLC3babsg6hBppHxDg2gouQlEK'),(1802,'Leon','l','b',352,'1293283','l@g.bom',1,'$2a$10$JSu78DbgqByOWeZsiEOzHeeJwQ7T0dTuItvfjBo.vEpCc66m4575i'),(2552,'Ldsdf',NULL,NULL,NULL,NULL,'fsdf',1,'$2a$10$VMH5xCT2h2KB.ozQW8mixelPmoCOsuGU6IU77KsG4IvQ0fnapjLfC'),(2553,'3',NULL,NULL,NULL,NULL,'123',1,'$2a$10$Nk7/sMY2y1FgacB3UzHIou2IzJ8iGJOH0mJ3hvoqX0sBrBj/axyfm'),(2554,'L3',NULL,NULL,NULL,NULL,'123',1,'$2a$10$0lCIwcPasKmk/vnGk3Y4HO5MNuNFOXssncLme7rMaJsrv31Z93j5S'),(2555,'L31',NULL,NULL,NULL,NULL,'123',1,'$2a$10$B17vFRtb1MFs0mrW7uedguxg..NOQcnHQSeAhJWgHlTbAhBGgG0q.'),(2601,'L123',NULL,NULL,NULL,NULL,'31',1,'$2a$10$LFbDwz/BceAZD3JzoiWRCOZVJH5qpaT/BGzChbZYcPEcLTcRciUm2'),(3151,'Leonidasss456ssa',NULL,NULL,NULL,NULL,'L@Gmail.com',1,'$2a$10$.HeNLDz67Pf2e1ujEpa95euS2KWzQ53O8RYP2SeipdfmGczJ/EaKy'),(3201,'Leonidasss2456ssa',NULL,NULL,NULL,NULL,'L@Gmail.com',1,'$2a$10$GmNxrorUMRc848Dh9EfCaOdfOgEvn6DTPVA5a13BsfyB0il5MrSfS'),(3251,'Leonidasss22456ssa',NULL,NULL,NULL,NULL,'L@Gmail.com',1,'$2a$10$5cKzJmu32fdC9k..CqKWku8oJL2SEll.JqBmk1.iFlKqJgnX664pe'),(3301,'Leonidasss232456ssa',NULL,NULL,NULL,NULL,'L@Gmail.com',1,'$2a$10$dmYxDBxQD9.3G/1LPPhDPeP2eqhmwWmMuPWhhprXtca6fjmu0v9uu'),(3351,'TestNOW',NULL,NULL,NULL,NULL,'L@Gmail.com',1,'$2a$10$JsubKM2L4lDAba8YQv0AyOelLA/2XDP5ca3nhbWkEsOX3kOWR6F3W'),(3401,'TestNOW12',NULL,NULL,NULL,NULL,'L@Gmail.com',1,'$2a$10$BFZ23l10ylPIQnxbzd1j9OK4M4aSyzzQWwuTS8DnasYZW8QU/iSQi'),(3702,'TestNOW31123212',NULL,NULL,NULL,NULL,'L@Gmail.com',1,'$2a$10$ylQs7hDF4aJuj4QL4/lKpeNlwPy9x6fgrkxgsDokDvt63/KE3hqVy'),(4101,'Leonard',NULL,NULL,NULL,NULL,'l@gmail.com',1,'$2a$10$hWcpzj4DsrKTDuieHjVYMO.dzkD5lcEMQpCH2sH9FA5RnS7uuifuG'),(4102,'1sadsjkl',NULL,NULL,NULL,NULL,'l@gmail.com',1,'$2a$10$gdF/irxwOuOTVN4yxKX8F.8IpajdiyOtBU084AQ/g1hWQjAPJ8QPS'),(4103,'123kdas',NULL,NULL,NULL,NULL,'l@ð',1,'$2a$10$3cngSdyxCNiCPg9EdhoBwuZjdWj1u6KEMqCwO.6G0NFbvBHtsqGQS'),(4104,'leom12311',NULL,NULL,NULL,NULL,'l@gmail.com',1,'$2a$10$r0ARuuRPr/L5q/2iTxsMdOT6TZszQXmNE9EaekXZzCfq5nkLwEMIC'),(4105,'231sadd',NULL,NULL,NULL,NULL,'qweq',1,'$2a$10$SKjM.BrAMzYbUwb6XYuCU.j9sfEFm2OQbddc2oDO.68lBL/lnIoLC'),(4106,'1312',NULL,NULL,NULL,NULL,'123',1,'$2a$10$885QZeFx3ZoQC9nnmTmrD.Ls2JMi154tJYAM6uH7.BNRHIQuPeaBK'),(4107,'1231',NULL,NULL,NULL,NULL,'123121231',1,'$2a$10$MPLA1PJ8SbcuS14cEOcHMuNtquHWnE0lLYSaqWwNGKb0HL8CLazaS'),(4108,'123qeeq',NULL,NULL,NULL,NULL,'l@gm.com',1,'$2a$10$Qx2MMkpMUHwWjXIeGjSar.DMdrE8V5jhNRZw2SmupgIlnZO3L08Zq'),(4109,'asda',NULL,NULL,NULL,NULL,'12',1,'$2a$10$muToeUTYqBmAbUdbA8pFoeCVS5J1owZAhyCtQDb1gVsQhDo0ZcqNi'),(4110,'newqe1',NULL,NULL,NULL,NULL,'asda',1,'$2a$10$mMmoszJ93dqPH1UgZvpRpOuLy93tXD3GRHGM.PFC9Aldj8upEIIaS'),(4111,'qwenqwe',NULL,NULL,NULL,NULL,'123',1,'$2a$10$ezQMapFHpEbrwBcezYMT3.nqxbzvE/4a40NEgVXqbKcyeIqD13N0q'),(4112,'leo123',NULL,NULL,NULL,NULL,'123',1,'$2a$10$ZxafAb/xTMaHykc9yN4Kce/ZFyCJhQ8sbYHHcGuKS4qr0zApoytW6'),(4113,'leo1231312',NULL,NULL,NULL,NULL,'123123',1,'$2a$10$oFqUaOBdgvVkl.8LFKuKV.JXbWacTslZKgwdGXdsbCNYLCl8iOGf2'),(4114,'lqweqwejk',NULL,NULL,NULL,NULL,'123',1,'$2a$10$OJPhhjNTsK1476voWulpMewrUOwUfRsP2HzrVbpSCJ84Ub6NEVYy.'),(4115,'1231wasda',NULL,NULL,NULL,NULL,'l@gmia.cm',1,'$2a$10$w0Q3/YtuNrO4cb6WzJI3c.lRFIw3nH/XvKdQhJwzSxSWAj94AnjDy'),(4116,'leondsda',NULL,NULL,NULL,NULL,'123',1,'$2a$10$uLXzVXQcP7cQGbaIsgbf0.vGuKZJWZxzpi4RXoFrUcpFv6v0CPsWu'),(4117,'1231sdafa',NULL,NULL,NULL,NULL,'123',1,'$2a$10$7vXhh.9AoEc3Cymb/ccXzuRhRXMTpZmOGWAubJqkGGEfNyxY11xZK'),(4118,'1231sdadadvxc',NULL,NULL,NULL,NULL,'123',1,'$2a$10$JfRMoo.SuqyR30AsnS1aoOuVPr.J9N8NB/tHO8j1YK8lMqIhEPJZC'),(4119,'regsit',NULL,NULL,NULL,NULL,'45d6as',1,'$2a$10$3kJfVZGkSfD/HH6ZhwEV6eqrJoCTzdigiqh0PUNUA.Yb3lQa4sYja'),(4120,'newISgn',NULL,NULL,NULL,NULL,'123',1,'$2a$10$fRDRUI8ZlFH88bNsssC9dOEQw4ZcLBQiQL2aKFjre7aWAKsifp/46'),(4121,'jaksdjalk13',NULL,NULL,NULL,NULL,'123',1,'$2a$10$vAB8rRoKA4sozPnJddY3VO8zpWZkm8gA1mouqloMRxt3hAel5yJrm'),(4151,'Neuers',NULL,NULL,NULL,NULL,'l@gmail.com',1,'$2a$10$ZsCMqJrDAPm70//NtafAzOS8g9wQE/jVzDGWBK83.l4ozGVLSRuey'),(4152,'1',NULL,NULL,NULL,NULL,'1',1,'$2a$10$iX5g1JLAgGQII6XaK8wKYeyCBl33zwyoDDhh.Aa9H8IJwCTCRrM36'),(4153,'123fcwer',NULL,NULL,NULL,NULL,'13',1,'$2a$10$kBrmhzIRVHuZ3QlB7nybQewFhApM6U4jLoRn7MB.awHlR47OByZIO'),(4154,'123',NULL,NULL,NULL,NULL,'123',1,'$2a$10$KmxUZNehyNWX.5j9saL1zOlQAwifgxJV8C.zn9sJcBLIjkajLsbUS'),(4155,'sdfadsf21sdafa',NULL,NULL,NULL,NULL,'11',1,'$2a$10$eTq/ZpmX8emz6qJvvXO2UeNPrJwp9kVgOQq262b.2YLSgAgFmyNvG'),(4156,'1231ada',NULL,NULL,NULL,NULL,'123',1,'$2a$10$7GJCwbsAQSlFAKhiProbz..5HUPkn2nOtfqgnDm1KT.MC.yFYqDyq'),(4157,'12sadad',NULL,NULL,NULL,NULL,'1',1,'$2a$10$OVeC6FvQyfTt364ybh7yQOrBt90Gf8ju4QDfT9lbaypV1gHerm27a');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserCoupon`
--

DROP TABLE IF EXISTS `UserCoupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UserCoupon` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint DEFAULT NULL,
  `discountId` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userID` (`userId`),
  KEY `discountID` (`discountId`),
  CONSTRAINT `UserCoupon_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`id`),
  CONSTRAINT `UserCoupon_ibfk_2` FOREIGN KEY (`discountId`) REFERENCES `Discount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserCoupon`
--

LOCK TABLES `UserCoupon` WRITE;
/*!40000 ALTER TABLE `UserCoupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserCoupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserCoupon_SEQ`
--

DROP TABLE IF EXISTS `UserCoupon_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UserCoupon_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserCoupon_SEQ`
--

LOCK TABLES `UserCoupon_SEQ` WRITE;
/*!40000 ALTER TABLE `UserCoupon_SEQ` DISABLE KEYS */;
INSERT INTO `UserCoupon_SEQ` VALUES (1);
/*!40000 ALTER TABLE `UserCoupon_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserProfile`
--

DROP TABLE IF EXISTS `UserProfile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UserProfile` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint DEFAULT NULL,
  `addressId` bigint DEFAULT NULL,
  `forename` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `addressId` (`addressId`),
  CONSTRAINT `UserProfile_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`id`),
  CONSTRAINT `UserProfile_ibfk_2` FOREIGN KEY (`addressId`) REFERENCES `Address` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=702 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserProfile`
--

LOCK TABLES `UserProfile` WRITE;
/*!40000 ALTER TABLE `UserProfile` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserProfile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserProfile_SEQ`
--

DROP TABLE IF EXISTS `UserProfile_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UserProfile_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserProfile_SEQ`
--

LOCK TABLES `UserProfile_SEQ` WRITE;
/*!40000 ALTER TABLE `UserProfile_SEQ` DISABLE KEYS */;
INSERT INTO `UserProfile_SEQ` VALUES (751);
/*!40000 ALTER TABLE `UserProfile_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User_SEQ`
--

DROP TABLE IF EXISTS `User_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User_SEQ`
--

LOCK TABLES `User_SEQ` WRITE;
/*!40000 ALTER TABLE `User_SEQ` DISABLE KEYS */;
INSERT INTO `User_SEQ` VALUES (4201);
/*!40000 ALTER TABLE `User_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quantity`
--

DROP TABLE IF EXISTS `quantity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quantity` (
  `id` bigint NOT NULL,
  `productID` bigint DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quantity`
--

LOCK TABLES `quantity` WRITE;
/*!40000 ALTER TABLE `quantity` DISABLE KEYS */;
INSERT INTO `quantity` VALUES (1,1051,0),(51,1101,0),(101,1151,0),(151,1201,0),(201,1251,0),(251,1301,0),(301,1351,0),(351,1401,20),(401,1451,20);
/*!40000 ALTER TABLE `quantity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quantity_SEQ`
--

DROP TABLE IF EXISTS `quantity_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quantity_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quantity_SEQ`
--

LOCK TABLES `quantity_SEQ` WRITE;
/*!40000 ALTER TABLE `quantity_SEQ` DISABLE KEYS */;
INSERT INTO `quantity_SEQ` VALUES (451);
/*!40000 ALTER TABLE `quantity_SEQ` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-05 11:23:16
