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


-- Dumping database structure for platepal_db
CREATE DATABASE IF NOT EXISTS `platepal_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `platepal_db`;

-- Dumping structure for table platepal_db.address_category
CREATE TABLE IF NOT EXISTS `address_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.address_category: ~0 rows (approximately)

-- Dumping structure for table platepal_db.attendance_type
CREATE TABLE IF NOT EXISTS `attendance_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.attendance_type: ~0 rows (approximately)

-- Dumping structure for table platepal_db.branch
CREATE TABLE IF NOT EXISTS `branch` (
  `id` int NOT NULL,
  `branch_id` varchar(10) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.branch: ~1 rows (approximately)
INSERT INTO `branch` (`id`, `branch_id`, `name`) VALUES
	(1, '001', 'Kottawa');

-- Dumping structure for table platepal_db.category
CREATE TABLE IF NOT EXISTS `category` (
  `id` int NOT NULL,
  `category_id` varchar(10) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.category: ~0 rows (approximately)

-- Dumping structure for table platepal_db.company
CREATE TABLE IF NOT EXISTS `company` (
  `id` int NOT NULL,
  `company_id` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.company: ~0 rows (approximately)

-- Dumping structure for table platepal_db.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int NOT NULL,
  `customer_id` varchar(10) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mobile` varchar(10) NOT NULL,
  `registered_date` datetime NOT NULL,
  `gender_id` int NOT NULL,
  `employee_emp_id` varchar(10) NOT NULL,
  PRIMARY KEY (`customer_id`),
  KEY `fk_customer_gender1_idx` (`gender_id`),
  KEY `fk_customer_employee1_idx` (`employee_emp_id`),
  CONSTRAINT `fk_customer_employee1` FOREIGN KEY (`employee_emp_id`) REFERENCES `employee` (`emp_id`),
  CONSTRAINT `fk_customer_gender1` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.customer: ~0 rows (approximately)

-- Dumping structure for table platepal_db.customer_address
CREATE TABLE IF NOT EXISTS `customer_address` (
  `id` int NOT NULL,
  `customer_address_id` varchar(10) NOT NULL,
  `address` text NOT NULL,
  `customer_customer_id` varchar(10) NOT NULL,
  `district_id` int NOT NULL,
  `address_category_id` int NOT NULL,
  PRIMARY KEY (`customer_address_id`),
  KEY `fk_customer_address_customer1_idx` (`customer_customer_id`),
  KEY `fk_customer_address_district1_idx` (`district_id`),
  KEY `fk_customer_address_address_category1_idx` (`address_category_id`),
  CONSTRAINT `fk_customer_address_address_category1` FOREIGN KEY (`address_category_id`) REFERENCES `address_category` (`id`),
  CONSTRAINT `fk_customer_address_customer1` FOREIGN KEY (`customer_customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `fk_customer_address_district1` FOREIGN KEY (`district_id`) REFERENCES `district` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.customer_address: ~0 rows (approximately)

-- Dumping structure for table platepal_db.district
CREATE TABLE IF NOT EXISTS `district` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.district: ~0 rows (approximately)

-- Dumping structure for table platepal_db.employee
CREATE TABLE IF NOT EXISTS `employee` (
  `id` int NOT NULL,
  `emp_id` varchar(10) NOT NULL,
  `first_name` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `last_name` varchar(40) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(20) NOT NULL,
  `branch_branch_id` varchar(10) NOT NULL,
  `gender_id` int NOT NULL,
  `employee_role_role_id` varchar(10) NOT NULL,
  `selected_salary` double NOT NULL,
  PRIMARY KEY (`emp_id`),
  UNIQUE KEY `emp_id_UNIQUE` (`emp_id`),
  KEY `fk_employee_branch1_idx` (`branch_branch_id`),
  KEY `fk_employee_gender1_idx` (`gender_id`),
  KEY `fk_employee_employee_role1_idx` (`employee_role_role_id`),
  CONSTRAINT `fk_employee_branch1` FOREIGN KEY (`branch_branch_id`) REFERENCES `branch` (`branch_id`),
  CONSTRAINT `fk_employee_employee_role1` FOREIGN KEY (`employee_role_role_id`) REFERENCES `employee_role` (`role_id`),
  CONSTRAINT `fk_employee_gender1` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.employee: ~1 rows (approximately)
INSERT INTO `employee` (`id`, `emp_id`, `first_name`, `last_name`, `email`, `password`, `branch_branch_id`, `gender_id`, `employee_role_role_id`, `selected_salary`) VALUES
	(1, '1', 'Suraj', 'Charuka', 'srjcharuka@gmail.com', '12345678', '001', 1, '2', 40000);

-- Dumping structure for table platepal_db.employee_address
CREATE TABLE IF NOT EXISTS `employee_address` (
  `id` int NOT NULL,
  `employee_address_id` varchar(10) NOT NULL,
  `address` text NOT NULL,
  `district_id` int NOT NULL,
  `employee_emp_id` varchar(10) NOT NULL,
  PRIMARY KEY (`employee_address_id`),
  KEY `fk_employee_address_district1_idx` (`district_id`),
  KEY `fk_employee_address_employee1_idx` (`employee_emp_id`),
  CONSTRAINT `fk_employee_address_district1` FOREIGN KEY (`district_id`) REFERENCES `district` (`id`),
  CONSTRAINT `fk_employee_address_employee1` FOREIGN KEY (`employee_emp_id`) REFERENCES `employee` (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.employee_address: ~0 rows (approximately)

-- Dumping structure for table platepal_db.employee_attendance
CREATE TABLE IF NOT EXISTS `employee_attendance` (
  `id` int NOT NULL,
  `attendance_id` varchar(10) NOT NULL,
  `checkin_time` datetime NOT NULL,
  `checkout_time` datetime DEFAULT NULL,
  `date` date NOT NULL,
  `employee_emp_id` varchar(10) NOT NULL,
  `regular_hours` double DEFAULT NULL,
  `ot_hours` double DEFAULT NULL,
  `attendance_type_id` int NOT NULL,
  PRIMARY KEY (`attendance_id`),
  KEY `fk_employee_attendance_employee1_idx` (`employee_emp_id`),
  KEY `fk_employee_attendance_attendance_type1_idx` (`attendance_type_id`),
  CONSTRAINT `fk_employee_attendance_attendance_type1` FOREIGN KEY (`attendance_type_id`) REFERENCES `attendance_type` (`id`),
  CONSTRAINT `fk_employee_attendance_employee1` FOREIGN KEY (`employee_emp_id`) REFERENCES `employee` (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.employee_attendance: ~0 rows (approximately)

-- Dumping structure for table platepal_db.employee_role
CREATE TABLE IF NOT EXISTS `employee_role` (
  `id` int NOT NULL,
  `role_id` varchar(10) NOT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_id_UNIQUE` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.employee_role: ~4 rows (approximately)
INSERT INTO `employee_role` (`id`, `role_id`, `type`) VALUES
	(1, '1', 'Cashier'),
	(2, '2', 'Reception'),
	(3, '3', 'Financial Manager'),
	(4, '4', 'Stock Management');

-- Dumping structure for table platepal_db.gender
CREATE TABLE IF NOT EXISTS `gender` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.gender: ~2 rows (approximately)
INSERT INTO `gender` (`id`, `type`) VALUES
	(1, 'Male'),
	(2, 'Female');

-- Dumping structure for table platepal_db.grn
CREATE TABLE IF NOT EXISTS `grn` (
  `id` int NOT NULL,
  `grn_id` varchar(10) NOT NULL,
  `paid_amount` double NOT NULL,
  `date` datetime NOT NULL,
  `branch_branch_id` varchar(10) NOT NULL,
  `employee_emp_id` varchar(10) NOT NULL,
  `purchase_order_po_id` varchar(10) NOT NULL,
  `supplier_supplier_id` varchar(10) NOT NULL,
  PRIMARY KEY (`grn_id`),
  KEY `fk_grn_branch1_idx` (`branch_branch_id`),
  KEY `fk_grn_employee1_idx` (`employee_emp_id`),
  KEY `fk_grn_purchase_order1_idx` (`purchase_order_po_id`),
  KEY `fk_grn_supplier1_idx` (`supplier_supplier_id`),
  CONSTRAINT `fk_grn_branch1` FOREIGN KEY (`branch_branch_id`) REFERENCES `branch` (`branch_id`),
  CONSTRAINT `fk_grn_employee1` FOREIGN KEY (`employee_emp_id`) REFERENCES `employee` (`emp_id`),
  CONSTRAINT `fk_grn_purchase_order1` FOREIGN KEY (`purchase_order_po_id`) REFERENCES `purchase_order` (`po_id`),
  CONSTRAINT `fk_grn_supplier1` FOREIGN KEY (`supplier_supplier_id`) REFERENCES `supplier` (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.grn: ~0 rows (approximately)

-- Dumping structure for table platepal_db.grn_item
CREATE TABLE IF NOT EXISTS `grn_item` (
  `id` int NOT NULL,
  `grn_item_id` varchar(10) NOT NULL,
  `quantity` varchar(5) NOT NULL,
  `price` double NOT NULL,
  `grn_grn_id` varchar(10) NOT NULL,
  `stock_stock_id` varchar(10) NOT NULL,
  PRIMARY KEY (`grn_item_id`),
  KEY `fk_grn_item_grn1_idx` (`grn_grn_id`),
  KEY `fk_grn_item_stock1_idx` (`stock_stock_id`),
  CONSTRAINT `fk_grn_item_grn1` FOREIGN KEY (`grn_grn_id`) REFERENCES `grn` (`grn_id`),
  CONSTRAINT `fk_grn_item_stock1` FOREIGN KEY (`stock_stock_id`) REFERENCES `stock` (`stock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.grn_item: ~0 rows (approximately)

-- Dumping structure for table platepal_db.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
  `id` int NOT NULL,
  `invoice_id` varchar(10) NOT NULL,
  `date` date NOT NULL,
  `payment` double NOT NULL,
  `discount` double NOT NULL,
  `customer_customer_id` varchar(10) NOT NULL,
  `payment_method_id` int NOT NULL,
  `orders_order_id` varchar(10) NOT NULL,
  `employee_emp_id` varchar(10) NOT NULL,
  PRIMARY KEY (`invoice_id`),
  KEY `fk_invoice_customer1_idx` (`customer_customer_id`),
  KEY `fk_invoice_payment_method1_idx` (`payment_method_id`),
  KEY `fk_invoice_orders1_idx` (`orders_order_id`),
  KEY `fk_invoice_employee1_idx` (`employee_emp_id`),
  CONSTRAINT `fk_invoice_customer1` FOREIGN KEY (`customer_customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `fk_invoice_employee1` FOREIGN KEY (`employee_emp_id`) REFERENCES `employee` (`emp_id`),
  CONSTRAINT `fk_invoice_orders1` FOREIGN KEY (`orders_order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `fk_invoice_payment_method1` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.invoice: ~0 rows (approximately)

-- Dumping structure for table platepal_db.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int NOT NULL,
  `order_id` varchar(10) NOT NULL,
  `order_on` datetime NOT NULL,
  `order_status_id` int NOT NULL,
  `order_no` int NOT NULL,
  `order_type_id` int NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `fk_orders_order_status1_idx` (`order_status_id`),
  KEY `fk_orders_order_type1_idx` (`order_type_id`),
  CONSTRAINT `fk_orders_order_status1` FOREIGN KEY (`order_status_id`) REFERENCES `order_status` (`id`),
  CONSTRAINT `fk_orders_order_type1` FOREIGN KEY (`order_type_id`) REFERENCES `order_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.orders: ~0 rows (approximately)

-- Dumping structure for table platepal_db.order_item
CREATE TABLE IF NOT EXISTS `order_item` (
  `id` int NOT NULL,
  `order_item_id` varchar(10) NOT NULL,
  `qty` int NOT NULL,
  `orders_order_id` varchar(10) NOT NULL,
  `product_has_size_id` varchar(10) NOT NULL,
  PRIMARY KEY (`order_item_id`),
  KEY `fk_order_item_orders1_idx` (`orders_order_id`),
  KEY `fk_order_item_product_has_size1_idx` (`product_has_size_id`),
  CONSTRAINT `fk_order_item_orders1` FOREIGN KEY (`orders_order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `fk_order_item_product_has_size1` FOREIGN KEY (`product_has_size_id`) REFERENCES `product_has_size` (`product_size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.order_item: ~0 rows (approximately)

-- Dumping structure for table platepal_db.order_status
CREATE TABLE IF NOT EXISTS `order_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.order_status: ~0 rows (approximately)

-- Dumping structure for table platepal_db.order_type
CREATE TABLE IF NOT EXISTS `order_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.order_type: ~0 rows (approximately)

-- Dumping structure for table platepal_db.payment_method
CREATE TABLE IF NOT EXISTS `payment_method` (
  `id` int NOT NULL AUTO_INCREMENT,
  `method` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.payment_method: ~0 rows (approximately)

-- Dumping structure for table platepal_db.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` int NOT NULL,
  `product_id` varchar(10) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` longtext NOT NULL,
  `added_date` datetime NOT NULL,
  `category_category_id` varchar(10) NOT NULL,
  `product_status_id` int NOT NULL,
  PRIMARY KEY (`product_id`),
  KEY `fk_product_category1_idx` (`category_category_id`),
  KEY `fk_product_product_status1_idx` (`product_status_id`),
  CONSTRAINT `fk_product_category1` FOREIGN KEY (`category_category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `fk_product_product_status1` FOREIGN KEY (`product_status_id`) REFERENCES `product_status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.product: ~0 rows (approximately)

-- Dumping structure for table platepal_db.product_has_size
CREATE TABLE IF NOT EXISTS `product_has_size` (
  `id` int NOT NULL,
  `product_size_id` varchar(10) NOT NULL,
  `product_product_id` varchar(10) NOT NULL,
  `size_id` int NOT NULL,
  `price` varchar(45) NOT NULL,
  PRIMARY KEY (`product_size_id`),
  KEY `fk_product_has_size_size1_idx` (`size_id`),
  KEY `fk_product_has_size_product1_idx` (`product_product_id`),
  CONSTRAINT `fk_product_has_size_product1` FOREIGN KEY (`product_product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `fk_product_has_size_size1` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.product_has_size: ~0 rows (approximately)

-- Dumping structure for table platepal_db.product_images
CREATE TABLE IF NOT EXISTS `product_images` (
  `id` int NOT NULL,
  `image_id` varchar(45) NOT NULL,
  `url` text NOT NULL,
  `product_product_id` varchar(10) NOT NULL,
  PRIMARY KEY (`image_id`),
  KEY `fk_product_images_product1_idx` (`product_product_id`),
  CONSTRAINT `fk_product_images_product1` FOREIGN KEY (`product_product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.product_images: ~0 rows (approximately)

-- Dumping structure for table platepal_db.product_status
CREATE TABLE IF NOT EXISTS `product_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.product_status: ~0 rows (approximately)

-- Dumping structure for table platepal_db.purchase_order
CREATE TABLE IF NOT EXISTS `purchase_order` (
  `id` int NOT NULL,
  `po_id` varchar(10) NOT NULL,
  `issued_date` datetime NOT NULL,
  `supplier_supplier_id` varchar(10) NOT NULL,
  `branch_branch_id` varchar(10) NOT NULL,
  PRIMARY KEY (`po_id`),
  KEY `fk_purchase_order_supplier1_idx` (`supplier_supplier_id`),
  KEY `fk_purchase_order_branch1_idx` (`branch_branch_id`),
  CONSTRAINT `fk_purchase_order_branch1` FOREIGN KEY (`branch_branch_id`) REFERENCES `branch` (`branch_id`),
  CONSTRAINT `fk_purchase_order_supplier1` FOREIGN KEY (`supplier_supplier_id`) REFERENCES `supplier` (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.purchase_order: ~0 rows (approximately)

-- Dumping structure for table platepal_db.purchase_order_item
CREATE TABLE IF NOT EXISTS `purchase_order_item` (
  `id` int NOT NULL,
  `po_item_id` varchar(10) NOT NULL,
  `quantity` varchar(5) NOT NULL,
  `price` double NOT NULL,
  `purchase_order_po_id` varchar(10) NOT NULL,
  `stock_product_stock_product_id` varchar(10) NOT NULL,
  PRIMARY KEY (`po_item_id`),
  KEY `fk_purchase_order_item_purchase_order1_idx` (`purchase_order_po_id`),
  KEY `fk_purchase_order_item_stock_product1_idx` (`stock_product_stock_product_id`),
  CONSTRAINT `fk_purchase_order_item_purchase_order1` FOREIGN KEY (`purchase_order_po_id`) REFERENCES `purchase_order` (`po_id`),
  CONSTRAINT `fk_purchase_order_item_stock_product1` FOREIGN KEY (`stock_product_stock_product_id`) REFERENCES `stock_product` (`stock_product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.purchase_order_item: ~0 rows (approximately)

-- Dumping structure for table platepal_db.qty_type
CREATE TABLE IF NOT EXISTS `qty_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.qty_type: ~0 rows (approximately)

-- Dumping structure for table platepal_db.salary_payments
CREATE TABLE IF NOT EXISTS `salary_payments` (
  `id` int NOT NULL,
  `salary_payments_id` varchar(10) NOT NULL,
  `paid_date` date NOT NULL,
  `paid_amount` double NOT NULL,
  `total_regular_hours` double NOT NULL,
  `total_ot_hours` double NOT NULL,
  `employee_emp_id` varchar(10) NOT NULL,
  PRIMARY KEY (`salary_payments_id`),
  KEY `fk_salary_payments_employee1_idx` (`employee_emp_id`),
  CONSTRAINT `fk_salary_payments_employee1` FOREIGN KEY (`employee_emp_id`) REFERENCES `employee` (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.salary_payments: ~0 rows (approximately)

-- Dumping structure for table platepal_db.selling_status
CREATE TABLE IF NOT EXISTS `selling_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.selling_status: ~0 rows (approximately)

-- Dumping structure for table platepal_db.size
CREATE TABLE IF NOT EXISTS `size` (
  `id` int NOT NULL AUTO_INCREMENT,
  `size_type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.size: ~0 rows (approximately)

-- Dumping structure for table platepal_db.stock
CREATE TABLE IF NOT EXISTS `stock` (
  `id` int NOT NULL,
  `stock_id` varchar(10) NOT NULL,
  `added_date` datetime NOT NULL,
  `branch_branch_id` varchar(10) NOT NULL,
  PRIMARY KEY (`stock_id`),
  KEY `fk_stock_branch1_idx` (`branch_branch_id`),
  CONSTRAINT `fk_stock_branch1` FOREIGN KEY (`branch_branch_id`) REFERENCES `branch` (`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.stock: ~0 rows (approximately)

-- Dumping structure for table platepal_db.stock_item
CREATE TABLE IF NOT EXISTS `stock_item` (
  `id` int NOT NULL,
  `stock_item_id` varchar(10) NOT NULL,
  `added_date` datetime NOT NULL,
  `qty` double NOT NULL,
  `price` double NOT NULL,
  `stock_product_stock_product_id` varchar(10) NOT NULL,
  `stock_stock_id` varchar(10) NOT NULL,
  `qty_type_id` int NOT NULL,
  PRIMARY KEY (`stock_item_id`),
  KEY `fk_stock_item_stock_product1_idx` (`stock_product_stock_product_id`),
  KEY `fk_stock_item_stock1_idx` (`stock_stock_id`),
  KEY `fk_stock_item_qty_type1_idx` (`qty_type_id`),
  CONSTRAINT `fk_stock_item_qty_type1` FOREIGN KEY (`qty_type_id`) REFERENCES `qty_type` (`id`),
  CONSTRAINT `fk_stock_item_stock1` FOREIGN KEY (`stock_stock_id`) REFERENCES `stock` (`stock_id`),
  CONSTRAINT `fk_stock_item_stock_product1` FOREIGN KEY (`stock_product_stock_product_id`) REFERENCES `stock_product` (`stock_product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.stock_item: ~0 rows (approximately)

-- Dumping structure for table platepal_db.stock_product
CREATE TABLE IF NOT EXISTS `stock_product` (
  `id` int NOT NULL,
  `stock_product_id` varchar(10) NOT NULL,
  `title` varchar(45) NOT NULL,
  `product_status_id` int NOT NULL,
  `selling_status_id` int NOT NULL,
  PRIMARY KEY (`stock_product_id`),
  KEY `fk_stock_product_product_status1_idx` (`product_status_id`),
  KEY `fk_stock_product_selling_status1_idx` (`selling_status_id`),
  CONSTRAINT `fk_stock_product_product_status1` FOREIGN KEY (`product_status_id`) REFERENCES `product_status` (`id`),
  CONSTRAINT `fk_stock_product_selling_status1` FOREIGN KEY (`selling_status_id`) REFERENCES `selling_status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.stock_product: ~0 rows (approximately)

-- Dumping structure for table platepal_db.supplier
CREATE TABLE IF NOT EXISTS `supplier` (
  `id` int NOT NULL,
  `supplier_id` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `mobile` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `company_company_id` varchar(10) NOT NULL,
  PRIMARY KEY (`supplier_id`),
  KEY `fk_supplier_company1_idx` (`company_company_id`),
  CONSTRAINT `fk_supplier_company1` FOREIGN KEY (`company_company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table platepal_db.supplier: ~0 rows (approximately)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
