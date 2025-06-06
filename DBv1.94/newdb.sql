-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.29 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for 123
CREATE DATABASE IF NOT EXISTS `123` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `123`;

-- Dumping structure for table 123.address_category
CREATE TABLE IF NOT EXISTS `address_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.address_category: ~0 rows (approximately)

-- Dumping structure for table 123.attendance_type
CREATE TABLE IF NOT EXISTS `attendance_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.attendance_type: ~0 rows (approximately)

-- Dumping structure for table 123.branch
CREATE TABLE IF NOT EXISTS `branch` (
  `id` int NOT NULL AUTO_INCREMENT,
  `branch_id` varchar(10) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `branch_id` (`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.branch: ~0 rows (approximately)

-- Dumping structure for table 123.category
CREATE TABLE IF NOT EXISTS `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.category: ~3 rows (approximately)
INSERT INTO `category` (`id`, `name`) VALUES
	(0, 'All'),
	(1, 'Foods'),
	(2, 'Drinks');

-- Dumping structure for table 123.company
CREATE TABLE IF NOT EXISTS `company` (
  `id` int NOT NULL AUTO_INCREMENT,
  `company_id` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `company_id` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.company: ~0 rows (approximately)

-- Dumping structure for table 123.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` varchar(10) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mobile` varchar(10) NOT NULL,
  `registered_date` datetime NOT NULL,
  `gender_id` int NOT NULL,
  `employee_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `customer_id` (`customer_id`),
  KEY `fk_customer_gender1_idx` (`gender_id`),
  KEY `fk_customer_employee1_idx` (`employee_id`),
  CONSTRAINT `fk_customer_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_customer_gender1` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.customer: ~0 rows (approximately)

-- Dumping structure for table 123.customer_address
CREATE TABLE IF NOT EXISTS `customer_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` text NOT NULL,
  `customer_id` int NOT NULL,
  `district_id` int NOT NULL,
  `address_category_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_customer_address_customer1_idx` (`customer_id`),
  KEY `fk_customer_address_district1_idx` (`district_id`),
  KEY `fk_customer_address_address_category1_idx` (`address_category_id`),
  CONSTRAINT `fk_customer_address_address_category1` FOREIGN KEY (`address_category_id`) REFERENCES `address_category` (`id`),
  CONSTRAINT `fk_customer_address_customer1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `fk_customer_address_district1` FOREIGN KEY (`district_id`) REFERENCES `district` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.customer_address: ~0 rows (approximately)

-- Dumping structure for table 123.district
CREATE TABLE IF NOT EXISTS `district` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.district: ~0 rows (approximately)

-- Dumping structure for table 123.employee
CREATE TABLE IF NOT EXISTS `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `emp_id` varchar(10) NOT NULL,
  `first_name` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `last_name` varchar(40) NOT NULL,
  `mobile` varchar(10) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(20) NOT NULL,
  `branch_id` int NOT NULL,
  `gender_id` int NOT NULL,
  `employee_role_id` int NOT NULL,
  `selected_salary` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `emp_id` (`emp_id`),
  KEY `fk_employee_branch1_idx` (`branch_id`),
  KEY `fk_employee_gender1_idx` (`gender_id`),
  KEY `fk_employee_employee_role1_idx` (`employee_role_id`),
  CONSTRAINT `fk_employee_branch1` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`),
  CONSTRAINT `fk_employee_employee_role1` FOREIGN KEY (`employee_role_id`) REFERENCES `employee_role` (`id`),
  CONSTRAINT `fk_employee_gender1` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.employee: ~0 rows (approximately)

-- Dumping structure for table 123.employee_address
CREATE TABLE IF NOT EXISTS `employee_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` text NOT NULL,
  `district_id` int NOT NULL,
  `employee_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employee_address_district1_idx` (`district_id`),
  KEY `fk_employee_address_employee1_idx` (`employee_id`),
  CONSTRAINT `fk_employee_address_district1` FOREIGN KEY (`district_id`) REFERENCES `district` (`id`),
  CONSTRAINT `fk_employee_address_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.employee_address: ~0 rows (approximately)

-- Dumping structure for table 123.employee_attendance
CREATE TABLE IF NOT EXISTS `employee_attendance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `checkin_time` time NOT NULL,
  `checkout_time` time DEFAULT NULL,
  `date` date NOT NULL,
  `employee_id` int NOT NULL,
  `attendance_type_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employee_attendance_employee1_idx` (`employee_id`),
  KEY `fk_employee_attendance_attendance_type1_idx` (`attendance_type_id`),
  CONSTRAINT `fk_employee_attendance_attendance_type1` FOREIGN KEY (`attendance_type_id`) REFERENCES `attendance_type` (`id`),
  CONSTRAINT `fk_employee_attendance_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.employee_attendance: ~0 rows (approximately)

-- Dumping structure for table 123.employee_requests
CREATE TABLE IF NOT EXISTS `employee_requests` (
  `id` int NOT NULL AUTO_INCREMENT,
  `request_id` varchar(10) NOT NULL,
  `employee_id` int NOT NULL,
  `request_type` varchar(50) NOT NULL,
  `description` text NOT NULL,
  `date_submitted` datetime NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'Pending',
  `priority` varchar(10) NOT NULL DEFAULT 'Medium',
  `admin_response` text,
  `admin_id` int DEFAULT NULL,
  `response_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `request_id` (`request_id`),
  KEY `fk_employee_requests_employee1_idx` (`employee_id`),
  KEY `fk_employee_requests_admin1_idx` (`admin_id`),
  CONSTRAINT `fk_employee_requests_admin1` FOREIGN KEY (`admin_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_employee_requests_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.employee_requests: ~0 rows (approximately)

-- Dumping structure for table 123.employee_role
CREATE TABLE IF NOT EXISTS `employee_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.employee_role: ~0 rows (approximately)

-- Dumping structure for table 123.gender
CREATE TABLE IF NOT EXISTS `gender` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.gender: ~0 rows (approximately)

-- Dumping structure for table 123.grn
CREATE TABLE IF NOT EXISTS `grn` (
  `id` int NOT NULL AUTO_INCREMENT,
  `grn_id` varchar(10) NOT NULL,
  `paid_amount` double NOT NULL,
  `date` datetime NOT NULL,
  `branch_id` int NOT NULL,
  `employee_id` int NOT NULL,
  `supplier_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `grn_id` (`grn_id`),
  KEY `fk_grn_branch1_idx` (`branch_id`),
  KEY `fk_grn_employee1_idx` (`employee_id`),
  KEY `fk_grn_supplier1_idx` (`supplier_id`),
  CONSTRAINT `fk_grn_branch1` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`),
  CONSTRAINT `fk_grn_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_grn_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.grn: ~0 rows (approximately)

-- Dumping structure for table 123.grn_item
CREATE TABLE IF NOT EXISTS `grn_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` varchar(5) NOT NULL,
  `price` double NOT NULL,
  `grn_id` int NOT NULL,
  `stock_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grn_item_grn1_idx` (`grn_id`),
  KEY `fk_grn_item_stock1_idx` (`stock_id`),
  CONSTRAINT `fk_grn_item_grn1` FOREIGN KEY (`grn_id`) REFERENCES `grn` (`id`),
  CONSTRAINT `fk_grn_item_stock1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.grn_item: ~0 rows (approximately)

-- Dumping structure for table 123.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `invoice_id` varchar(10) NOT NULL,
  `date` date NOT NULL,
  `payment` double NOT NULL,
  `discount` double NOT NULL,
  `customer_id` int NOT NULL,
  `payment_method_id` int NOT NULL,
  `orders_id` int NOT NULL,
  `employee_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `invoice_id` (`invoice_id`),
  KEY `fk_invoice_customer1_idx` (`customer_id`),
  KEY `fk_invoice_payment_method1_idx` (`payment_method_id`),
  KEY `fk_invoice_orders1_idx` (`orders_id`),
  KEY `fk_invoice_employee1_idx` (`employee_id`),
  CONSTRAINT `fk_invoice_customer1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `fk_invoice_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_invoice_orders1` FOREIGN KEY (`orders_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `fk_invoice_payment_method1` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.invoice: ~0 rows (approximately)

-- Dumping structure for table 123.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` varchar(10) NOT NULL,
  `order_on` datetime NOT NULL,
  `order_status_id` int NOT NULL,
  `order_no` int NOT NULL,
  `order_type_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id` (`order_id`),
  KEY `fk_orders_order_status1_idx` (`order_status_id`),
  KEY `fk_orders_order_type1_idx` (`order_type_id`),
  CONSTRAINT `fk_orders_order_status1` FOREIGN KEY (`order_status_id`) REFERENCES `order_status` (`id`),
  CONSTRAINT `fk_orders_order_type1` FOREIGN KEY (`order_type_id`) REFERENCES `order_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.orders: ~0 rows (approximately)

-- Dumping structure for table 123.order_item
CREATE TABLE IF NOT EXISTS `order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `qty` int NOT NULL,
  `orders_id` int NOT NULL,
  `product_has_size_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_item_orders1_idx` (`orders_id`),
  KEY `fk_order_item_product_has_size1_idx` (`product_has_size_id`),
  CONSTRAINT `fk_order_item_orders1` FOREIGN KEY (`orders_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `fk_order_item_product_has_size1` FOREIGN KEY (`product_has_size_id`) REFERENCES `product_has_size` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.order_item: ~0 rows (approximately)

-- Dumping structure for table 123.order_status
CREATE TABLE IF NOT EXISTS `order_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.order_status: ~0 rows (approximately)

-- Dumping structure for table 123.order_type
CREATE TABLE IF NOT EXISTS `order_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.order_type: ~0 rows (approximately)

-- Dumping structure for table 123.payment_method
CREATE TABLE IF NOT EXISTS `payment_method` (
  `id` int NOT NULL AUTO_INCREMENT,
  `method` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.payment_method: ~0 rows (approximately)

-- Dumping structure for table 123.petty_cash
CREATE TABLE IF NOT EXISTS `petty_cash` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `amount` double NOT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `employee_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employee` (`employee_id`),
  CONSTRAINT `fk_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table 123.petty_cash: ~0 rows (approximately)

-- Dumping structure for table 123.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `product_id` varchar(10) NOT NULL,
  `description` longtext CHARACTER SET utf8mb3 COLLATE utf8_general_ci,
  `added_date` datetime NOT NULL,
  `product_status_id` int NOT NULL,
  `sub_category_id` int NOT NULL,
  `sellingCount` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_id` (`product_id`),
  KEY `fk_product_product_status1_idx` (`product_status_id`),
  KEY `fk_product_sub_category1_idx` (`sub_category_id`),
  CONSTRAINT `fk_product_product_status1` FOREIGN KEY (`product_status_id`) REFERENCES `product_status` (`id`),
  CONSTRAINT `fk_product_sub_category1` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.product: ~18 rows (approximately)
INSERT INTO `product` (`id`, `title`, `product_id`, `description`, `added_date`, `product_status_id`, `sub_category_id`, `sellingCount`) VALUES
	(1, 'Vegetable Rice', 'pdct001', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:10:07', 1, 1, 21),
	(2, 'Egg Rice', 'pdct002', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:10:46', 1, 1, 32),
	(3, 'Chicken Rice', 'pdct003', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:11:13', 1, 1, 345),
	(4, 'Pork Rice', 'pdct004', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:11:39', 1, 1, 100),
	(5, 'Fish Rice', 'pdct005', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:12:32', 1, 1, 48),
	(6, 'Sea Food Rice', 'pdct006', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:12:52', 1, 1, 60),
	(7, 'Mixed Rice', 'pdct007', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:13:25', 1, 1, 97),
	(8, 'Cheese Rice', 'pdct008', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:14:28', 1, 1, 22),
	(9, 'Vegetable Kottu', 'pdct009', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:15:51', 1, 2, 44),
	(10, 'Egg Kottu', 'pdct010', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:16:34', 1, 2, 67),
	(11, 'Chicken Kottu', 'pdct011', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:17:05', 1, 2, 600),
	(12, 'Pork Kottu', 'pdct012', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:17:59', 1, 2, 307),
	(13, 'Fish Kottu', 'pdct013', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:18:52', 1, 2, 27),
	(14, 'Sea Food Kottu', 'pdct014', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:19:19', 1, 2, 75),
	(15, 'Mixed Kottu', 'pdct015', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:20:00', 1, 2, 267),
	(16, 'Cheese Kottu', 'pdct016', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:21:32', 1, 2, 127),
	(17, 'Noodles Kottu', 'pdct017', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:21:56', 1, 2, 65),
	(18, 'String Hopper Kottu', 'pdct018', 'The flavors in this are just incredible – a perfect blend that really hits the spot.', '2025-05-21 22:22:42', 1, 2, 45);

-- Dumping structure for table 123.product_has_size
CREATE TABLE IF NOT EXISTS `product_has_size` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `size_id` int NOT NULL,
  `price` varchar(45) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_product_has_size_size1_idx` (`size_id`),
  KEY `fk_product_has_size_product1_idx` (`product_id`),
  CONSTRAINT `fk_product_has_size_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_product_has_size_size1` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.product_has_size: ~9 rows (approximately)
INSERT INTO `product_has_size` (`id`, `product_id`, `size_id`, `price`) VALUES
	(1, 1, 1, '600'),
	(2, 1, 2, '1000'),
	(3, 1, 3, '1400'),
	(4, 2, 1, '800'),
	(5, 2, 2, '1200'),
	(6, 2, 3, '1600'),
	(7, 3, 1, '1000'),
	(8, 3, 2, '1500'),
	(9, 3, 3, '2000');

-- Dumping structure for table 123.product_images
CREATE TABLE IF NOT EXISTS `product_images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `url` text NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_images_product1_idx` (`product_id`),
  CONSTRAINT `fk_product_images_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.product_images: ~18 rows (approximately)
INSERT INTO `product_images` (`id`, `url`, `product_id`) VALUES
	(1, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 1),
	(2, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 2),
	(3, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 3),
	(4, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 4),
	(5, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 5),
	(6, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 6),
	(7, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 7),
	(8, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 8),
	(9, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 9),
	(10, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 10),
	(11, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 11),
	(12, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 12),
	(13, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 13),
	(14, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 14),
	(15, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 15),
	(16, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 16),
	(17, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 17),
	(18, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo.png', 18);

-- Dumping structure for table 123.product_status
CREATE TABLE IF NOT EXISTS `product_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.product_status: ~2 rows (approximately)
INSERT INTO `product_status` (`id`, `name`) VALUES
	(1, 'Active'),
	(2, 'Inactive');

-- Dumping structure for table 123.purchase_order
CREATE TABLE IF NOT EXISTS `purchase_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `po_id` varchar(10) NOT NULL,
  `issued_date` datetime NOT NULL,
  `supplier_id` int NOT NULL,
  `branch_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `po_id` (`po_id`),
  KEY `fk_purchase_order_supplier1_idx` (`supplier_id`),
  KEY `fk_purchase_order_branch1_idx` (`branch_id`),
  CONSTRAINT `fk_purchase_order_branch1` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`),
  CONSTRAINT `fk_purchase_order_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.purchase_order: ~0 rows (approximately)

-- Dumping structure for table 123.purchase_order_item
CREATE TABLE IF NOT EXISTS `purchase_order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` varchar(5) NOT NULL,
  `price` double NOT NULL,
  `purchase_order_id` int NOT NULL,
  `stock_product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_purchase_order_item_purchase_order1_idx` (`purchase_order_id`),
  KEY `fk_purchase_order_item_stock_product1_idx` (`stock_product_id`),
  CONSTRAINT `fk_purchase_order_item_purchase_order1` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchase_order` (`id`),
  CONSTRAINT `fk_purchase_order_item_stock_product1` FOREIGN KEY (`stock_product_id`) REFERENCES `stock_product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.purchase_order_item: ~0 rows (approximately)

-- Dumping structure for table 123.qty_type
CREATE TABLE IF NOT EXISTS `qty_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.qty_type: ~0 rows (approximately)

-- Dumping structure for table 123.request
CREATE TABLE IF NOT EXISTS `request` (
  `id` int NOT NULL AUTO_INCREMENT,
  `message` varchar(250) DEFAULT NULL,
  `dateTime` datetime DEFAULT NULL,
  `from` int NOT NULL,
  `to` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_request_employee1_idx` (`from`),
  KEY `fk_request_employee2_idx` (`to`),
  CONSTRAINT `fk_request_employee1` FOREIGN KEY (`from`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_request_employee2` FOREIGN KEY (`to`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table 123.request: ~0 rows (approximately)

-- Dumping structure for table 123.request_type
CREATE TABLE IF NOT EXISTS `request_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.request_type: ~6 rows (approximately)
INSERT INTO `request_type` (`id`, `type_name`) VALUES
	(1, 'Leave Request'),
	(2, 'Equipment Request'),
	(3, 'Overtime Request'),
	(4, 'Schedule Change'),
	(5, 'Training Request'),
	(6, 'Other');

-- Dumping structure for table 123.salary_payments
CREATE TABLE IF NOT EXISTS `salary_payments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `salary_payments_id` varchar(10) NOT NULL,
  `paid_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `paid_amount` double NOT NULL,
  `total_regular_hours` double NOT NULL,
  `total_ot_hours` double NOT NULL,
  `employee_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `salary_payments_id` (`salary_payments_id`),
  KEY `fk_salary_payments_employee1_idx` (`employee_id`),
  CONSTRAINT `fk_salary_payments_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.salary_payments: ~0 rows (approximately)

-- Dumping structure for table 123.size
CREATE TABLE IF NOT EXISTS `size` (
  `id` int NOT NULL AUTO_INCREMENT,
  `size_type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.size: ~3 rows (approximately)
INSERT INTO `size` (`id`, `size_type`) VALUES
	(1, 'M'),
	(2, 'Lg'),
	(3, 'Xl');

-- Dumping structure for table 123.status
CREATE TABLE IF NOT EXISTS `status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table 123.status: ~2 rows (approximately)
INSERT INTO `status` (`id`, `status`) VALUES
	(1, 'Active'),
	(2, 'Deactive');

-- Dumping structure for table 123.stock
CREATE TABLE IF NOT EXISTS `stock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stock_id` varchar(10) NOT NULL,
  `added_date` datetime NOT NULL,
  `branch_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `stock_id` (`stock_id`),
  KEY `fk_stock_branch1_idx` (`branch_id`),
  CONSTRAINT `fk_stock_branch1` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.stock: ~0 rows (approximately)

-- Dumping structure for table 123.stock_item
CREATE TABLE IF NOT EXISTS `stock_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stock_item_id` varchar(10) NOT NULL,
  `added_date` datetime NOT NULL,
  `qty` double NOT NULL,
  `price` double NOT NULL,
  `stock_product_id` int NOT NULL,
  `stock_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `stock_item_id` (`stock_item_id`),
  KEY `fk_stock_item_stock_product1_idx` (`stock_product_id`),
  KEY `fk_stock_item_stock1_idx` (`stock_id`),
  CONSTRAINT `fk_stock_item_stock1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`),
  CONSTRAINT `fk_stock_item_stock_product1` FOREIGN KEY (`stock_product_id`) REFERENCES `stock_product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.stock_item: ~0 rows (approximately)

-- Dumping structure for table 123.stock_product
CREATE TABLE IF NOT EXISTS `stock_product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stock_product_id` varchar(10) NOT NULL,
  `title` varchar(45) NOT NULL,
  `status_id` int NOT NULL,
  `qty_type_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `stock_product_id` (`stock_product_id`),
  KEY `fk_stock_product_status1_idx` (`status_id`),
  KEY `fk_stock_product_qty_type1_idx` (`qty_type_id`),
  CONSTRAINT `fk_stock_product_qty_type1` FOREIGN KEY (`qty_type_id`) REFERENCES `qty_type` (`id`),
  CONSTRAINT `fk_stock_product_status1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.stock_product: ~0 rows (approximately)

-- Dumping structure for table 123.sub_category
CREATE TABLE IF NOT EXISTS `sub_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `subCatTitle` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `category_id` int NOT NULL,
  `image` varchar(120) NOT NULL DEFAULT '',
  `sellCount` int NOT NULL DEFAULT (0),
  PRIMARY KEY (`id`),
  KEY `fk_sub_category_category1_idx` (`category_id`),
  CONSTRAINT `fk_sub_category_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table 123.sub_category: ~12 rows (approximately)
INSERT INTO `sub_category` (`id`, `subCatTitle`, `category_id`, `image`, `sellCount`) VALUES
	(0, 'All', 0, '', 10000),
	(1, 'Rice', 1, '', 100),
	(2, 'Kottu', 1, '', 200),
	(3, 'Biriyani', 2, '', 0),
	(4, 'Nasiguran', 2, '', 0),
	(5, 'Burger', 1, '', 0),
	(6, 'Pizza', 1, '', 0),
	(7, 'Noodless', 2, '', 0),
	(8, 'Devil', 1, '', 0),
	(9, 'Soup', 1, '', 0),
	(10, 'Shorteats', 2, '', 0),
	(11, 'Drinks', 1, '', 0);

-- Dumping structure for table 123.supplier
CREATE TABLE IF NOT EXISTS `supplier` (
  `id` int NOT NULL AUTO_INCREMENT,
  `supplier_id` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `mobile` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `company_id` int NOT NULL,
  `status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `supplier_id` (`supplier_id`),
  KEY `fk_supplier_company1_idx` (`company_id`),
  KEY `fk_supplier_status1_idx` (`status_id`),
  CONSTRAINT `fk_supplier_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_supplier_status1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.supplier: ~0 rows (approximately)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
