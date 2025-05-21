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
INSERT INTO `address_category` (`id`, `type`) VALUES
	(1, 'Home'),
	(2, 'Work'),
	(3, 'Billing'),
	(4, 'Shipping'),
	(5, 'Temporary'),
	(6, 'Primary Residence'),
	(7, 'Secondary Residence'),
	(8, 'Vacation Home'),
	(9, 'Office'),
	(10, 'Branch Office'),
	(11, 'Warehouse'),
	(12, 'Retail Store'),
	(13, 'Factory'),
	(14, 'Remote Location'),
	(15, 'Guest House'),
	(16, 'Dormitory'),
	(17, 'Apartment'),
	(18, 'Condo'),
	(19, 'Farm'),
	(20, 'Ranch'),
	(21, 'Studio'),
	(22, 'Mobile Home'),
	(23, 'Boat Dock'),
	(24, 'Post Office Box'),
	(25, 'Delivery Point');

-- Dumping structure for table 123.attendance_type
CREATE TABLE IF NOT EXISTS `attendance_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.attendance_type: ~0 rows (approximately)
INSERT INTO `attendance_type` (`id`, `name`) VALUES
	(1, 'Present'),
	(2, 'Absent'),
	(3, 'Late'),
	(4, 'Early Leave'),
	(5, 'Half Day'),
	(6, 'On Leave - Sick'),
	(7, 'On Leave - Casual'),
	(8, 'On Leave - Annual'),
	(9, 'Remote Work'),
	(10, 'Field Visit'),
	(11, 'Training'),
	(12, 'Meeting Off-site'),
	(13, 'Holiday'),
	(14, 'Weekend'),
	(15, 'Public Holiday'),
	(16, 'Maternity Leave'),
	(17, 'Paternity Leave'),
	(18, 'Bereavement Leave'),
	(19, 'Study Leave'),
	(20, 'Scheduled Off'),
	(21, 'Unscheduled Absent'),
	(22, 'Work From Home'),
	(23, 'Flexi-time'),
	(24, 'Overtime'),
	(25, 'Compensatory Off');

-- Dumping structure for table 123.branch
CREATE TABLE IF NOT EXISTS `branch` (
  `id` int NOT NULL AUTO_INCREMENT,
  `branch_id` varchar(10) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `branch_id` (`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.branch: ~0 rows (approximately)
INSERT INTO `branch` (`id`, `branch_id`, `name`) VALUES
	(1, 'BR001', 'Colombo Main Branch'),
	(2, 'BR002', 'Kandy Central'),
	(3, 'BR003', 'Galle Fort Branch'),
	(4, 'BR004', 'Jaffna City'),
	(5, 'BR005', 'Batticaloa East'),
	(6, 'BR006', 'Negombo Beach'),
	(7, 'BR007', 'Anuradhapura Heritage'),
	(8, 'BR008', 'Polonnaruwa Ancient'),
	(9, 'BR009', 'Ratnapura Gem'),
	(10, 'BR010', 'Badulla Hilltop'),
	(11, 'BR011', 'Trincomalee Harbor'),
	(12, 'BR012', 'Matara Coastal'),
	(13, 'BR013', 'Kurunegala Lake'),
	(14, 'BR014', 'Nuwara Eliya Cold'),
	(15, 'BR015', 'Kegalle Highlands'),
	(16, 'BR016', 'Pannipitiya Suburban'),
	(17, 'BR017', 'Maharagama Metro'),
	(18, 'BR018', 'Moratuwa Lagoon'),
	(19, 'BR019', 'Dehiwala Beachside'),
	(20, 'BR020', 'Mount Lavinia Ocean'),
	(21, 'BR021', 'Katunayake Airport'),
	(22, 'BR022', 'Homagama Tech'),
	(23, 'BR023', 'Bandarawela Green'),
	(24, 'BR024', 'Ampara Ricebowl'),
	(25, 'BR025', 'Mannar Island');

-- Dumping structure for table 123.category
CREATE TABLE IF NOT EXISTS `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.category: ~2 rows (approximately)
INSERT INTO `category` (`id`, `name`) VALUES
	(1, 'Foods'),
	(2, 'Drinks'),
	(3, 'Desserts'),
	(4, 'Snacks'),
	(5, 'Breakfast'),
	(6, 'Lunch'),
	(7, 'Dinner'),
	(8, 'Appetizers'),
	(9, 'Main Courses'),
	(10, 'Soups'),
	(11, 'Salads'),
	(12, 'Beverages'),
	(13, 'Baked Goods'),
	(14, 'Confectionery'),
	(15, 'Dairy'),
	(16, 'Meat'),
	(17, 'Seafood'),
	(18, 'Vegetables'),
	(19, 'Fruits'),
	(20, 'Spices'),
	(21, 'Condiments'),
	(22, 'Frozen Foods'),
	(23, 'Canned Goods'),
	(24, 'Organic Products'),
	(25, 'Gluten-Free');

-- Dumping structure for table 123.company
CREATE TABLE IF NOT EXISTS `company` (
  `id` int NOT NULL AUTO_INCREMENT,
  `company_id` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `company_id` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.company: ~0 rows (approximately)
INSERT INTO `company` (`id`, `company_id`, `name`) VALUES
	(1, 'COMP001', 'Lanka Grocers PLC'),
	(2, 'COMP002', 'Cinnamon Spice Co.'),
	(3, 'COMP003', 'Oceanic Fisheries Ltd.'),
	(4, 'COMP004', 'Highland Dairy Farms'),
	(5, 'COMP005', 'Sunrise Beverages'),
	(6, 'COMP006', 'Green Leaf Produce'),
	(7, 'COMP007', 'Island Foods Corp.'),
	(8, 'COMP008', 'Heritage Bakers'),
	(9, 'COMP009', 'AgriHarvest Solutions'),
	(10, 'COMP010', 'Coastal Logistics Inc.'),
	(11, 'COMP011', 'Peak Tea Estates'),
	(12, 'COMP012', 'Pure Water Holdings'),
	(13, 'COMP013', 'Global Spice Exports'),
	(14, 'COMP014', 'Urban Catering Supplies'),
	(15, 'COMP015', 'Fresh Foods Distributors'),
	(16, 'COMP016', 'Royal Sweets & Confectionery'),
	(17, 'COMP017', 'Valley Vegetables Co.'),
	(18, 'COMP018', 'Palm Oil Producers'),
	(19, 'COMP019', 'Rice Mills of Ceylon'),
	(20, 'COMP020', 'Coconut Products PLC'),
	(21, 'COMP021', 'Spice Route Trading'),
	(22, 'COMP022', 'Island Delights Bakery'),
	(23, 'COMP023', 'Fruit Bowl Imports'),
	(24, 'COMP024', 'Ceylon Coffee House'),
	(25, 'COMP025', 'Organic Foods SL');

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
INSERT INTO `customer` (`id`, `customer_id`, `first_name`, `last_name`, `email`, `mobile`, `registered_date`, `gender_id`, `employee_id`) VALUES
	(1, 'CUST001', 'Alice', 'Wonder', 'alice.w@email.com', '0781112233', '2023-01-15 10:00:00', 2, 1),
	(2, 'CUST002', 'Bob', 'Builder', 'bob.b@email.com', '0762223344', '2023-01-20 11:30:00', 1, 2),
	(3, 'CUST003', 'Charlie', 'Chaplin', 'charlie.c@email.com', '0773334455', '2023-02-01 12:00:00', 1, 3),
	(4, 'CUST004', 'Diana', 'Prince', 'diana.p@email.com', '0754445566', '2023-02-10 14:00:00', 2, 4),
	(5, 'CUST005', 'Eve', 'Adams', 'eve.a@email.com', '0715556677', '2023-02-15 16:00:00', 2, 5),
	(6, 'CUST006', 'Frank', 'Sinatra', 'frank.s@email.com', '0706667788', '2023-03-01 09:00:00', 1, 6),
	(7, 'CUST007', 'Grace', 'Kelly', 'grace.k@email.com', '0787778899', '2023-03-05 10:30:00', 2, 7),
	(8, 'CUST008', 'Henry', 'Ford', 'henry.f@email.com', '0768889900', '2023-03-10 11:00:00', 1, 8),
	(9, 'CUST009', 'Ivy', 'Queen', 'ivy.q@email.com', '0779990011', '2023-03-15 13:00:00', 2, 9),
	(10, 'CUST010', 'Jack', 'Frost', 'jack.f@email.com', '0750001122', '2023-03-20 15:00:00', 1, 10),
	(11, 'CUST011', 'Karen', 'Carpenter', 'karen.c@email.com', '0711112234', '2023-04-01 09:30:00', 2, 11),
	(12, 'CUST012', 'Leo', 'Tolstoy', 'leo.t@email.com', '0702223345', '2023-04-05 10:00:00', 1, 12),
	(13, 'CUST013', 'Mona', 'Lisa', 'mona.l@email.com', '0783334456', '2023-04-10 12:00:00', 2, 13),
	(14, 'CUST014', 'Ned', 'Stark', 'ned.s@email.com', '0764445567', '2023-04-15 14:00:00', 1, 14),
	(15, 'CUST015', 'Oprah', 'Winfrey', 'oprah.w@email.com', '0775556678', '2023-04-20 16:00:00', 2, 15),
	(16, 'CUST016', 'Paul', 'McCartney', 'paul.m@email.com', '0766667789', '2023-05-01 09:00:00', 1, 16),
	(17, 'CUST017', 'Quinn', 'Fabray', 'quinn.f@email.com', '0717778890', '2023-05-05 10:30:00', 2, 17),
	(18, 'CUST018', 'Roger', 'Federer', 'roger.f@email.com', '0708889901', '2023-05-10 11:00:00', 1, 18),
	(19, 'CUST019', 'Sara', 'Conner', 'sara.c@email.com', '0789990012', '2023-05-15 13:00:00', 2, 19),
	(20, 'CUST020', 'Tom', 'Hanks', 'tom.h@email.com', '0760001123', '2023-05-20 15:00:00', 1, 20),
	(21, 'CUST021', 'Uma', 'Thurman', 'uma.t@email.com', '0771112235', '2023-06-01 09:30:00', 2, 21),
	(22, 'CUST022', 'Victor', 'Hugo', 'victor.h@email.com', '0752223346', '2023-06-05 10:00:00', 1, 22),
	(23, 'CUST023', 'Wendy', 'Darling', 'wendy.d@email.com', '0713334457', '2023-06-10 12:00:00', 2, 23),
	(24, 'CUST024', 'Xavier', 'Musk', 'xavier.m@email.com', '0704445568', '2023-06-15 14:00:00', 1, 24),
	(25, 'CUST025', 'Yara', 'Greyjoy', 'yara.g@email.com', '0785556679', '2023-06-20 16:00:00', 2, 25);

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
INSERT INTO `customer_address` (`id`, `address`, `customer_id`, `district_id`, `address_category_id`) VALUES
	(1, '15 Ocean View Rd, Colombo 03', 1, 1, 1),
	(2, '25 Lake Cir, Kandy', 2, 2, 2),
	(3, '35 Hilltop Drive, Galle', 3, 3, 3),
	(4, '45 Fort Lane, Jaffna', 4, 4, 4),
	(5, '55 Coastal Blvd, Batticaloa', 5, 5, 5),
	(6, '65 Sunrise Ave, Negombo', 6, 6, 6),
	(7, '75 Ancient Path, Anuradhapura', 7, 7, 7),
	(8, '85 Royal Garden, Polonnaruwa', 8, 8, 8),
	(9, '95 Gemstone St, Ratnapura', 9, 9, 9),
	(10, '105 Tea Estate Rd, Badulla', 10, 10, 10),
	(11, '115 Harbor Front, Trincomalee', 11, 11, 11),
	(12, '125 Golden Beach Rd, Matara', 12, 12, 12),
	(13, '135 Green Valley, Kurunegala', 13, 13, 13),
	(14, '145 Mist Covered St, Nuwara Eliya', 14, 14, 14),
	(15, '155 Mountain View, Kegalle', 15, 15, 15),
	(16, '165 Palm Grove, Puttalam', 16, 16, 16),
	(17, '175 Riverbend, Kalutara', 17, 17, 17),
	(18, '185 Suburban Bliss, Gampaha', 18, 18, 18),
	(19, '195 Elephant Rock, Hambantota', 19, 19, 19),
	(20, '205 Dry Zone St, Monaragala', 20, 20, 20),
	(21, '215 Rice Field Ln, Ampara', 21, 21, 21),
	(22, '225 Sand Dune Rd, Mannar', 22, 22, 22),
	(23, '235 Northern Star, Vavuniya', 23, 23, 23),
	(24, '245 Jungle Path, Mullaitivu', 24, 24, 24),
	(25, '255 Mango Tree Ave, Kilinochchi', 25, 25, 25);

-- Dumping structure for table 123.district
CREATE TABLE IF NOT EXISTS `district` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.district: ~0 rows (approximately)
INSERT INTO `district` (`id`, `name`) VALUES
	(1, 'Colombo'),
	(2, 'Kandy'),
	(3, 'Galle'),
	(4, 'Jaffna'),
	(5, 'Batticaloa'),
	(6, 'Negombo'),
	(7, 'Anuradhapura'),
	(8, 'Polonnaruwa'),
	(9, 'Ratnapura'),
	(10, 'Badulla'),
	(11, 'Trincomalee'),
	(12, 'Matara'),
	(13, 'Kurunegala'),
	(14, 'Nuwara Eliya'),
	(15, 'Kegalle'),
	(16, 'Puttalam'),
	(17, 'Kalutara'),
	(18, 'Gampaha'),
	(19, 'Hambantota'),
	(20, 'Monaragala'),
	(21, 'Ampara'),
	(22, 'Mannar'),
	(23, 'Vavuniya'),
	(24, 'Mullaitivu'),
	(25, 'Kilinochchi');

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
INSERT INTO `employee` (`id`, `emp_id`, `first_name`, `last_name`, `mobile`, `email`, `password`, `branch_id`, `gender_id`, `employee_role_id`, `selected_salary`) VALUES
	(1, 'EMP001', 'John', 'Doe', '0771234567', 'john.doe@example.com', 'pass123', 1, 1, 1, 60000),
	(2, 'EMP002', 'Jane', 'Smith', '0712345678', 'jane.smith@example.com', 'pass123', 2, 2, 2, 55000),
	(3, 'EMP003', 'Peter', 'Jones', '0703456789', 'peter.j@example.com', 'pass123', 3, 1, 3, 35000),
	(4, 'EMP004', 'Mary', 'Brown', '0764567890', 'mary.b@example.com', 'pass123', 4, 2, 4, 45000),
	(5, 'EMP005', 'David', 'Green', '0755678901', 'david.g@example.com', 'pass123', 5, 1, 5, 40000),
	(6, 'EMP006', 'Sarah', 'White', '0776789012', 'sarah.w@example.com', 'pass123', 6, 2, 6, 30000),
	(7, 'EMP007', 'Michael', 'Black', '0717890123', 'michael.b@example.com', 'pass123', 7, 1, 7, 32000),
	(8, 'EMP008', 'Emily', 'Gray', '0708901234', 'emily.g@example.com', 'pass123', 8, 2, 8, 28000),
	(9, 'EMP009', 'Chris', 'King', '0769012345', 'chris.k@example.com', 'pass123', 9, 1, 9, 50000),
	(10, 'EMP010', 'Anna', 'Lee', '0750123456', 'anna.l@example.com', 'pass123', 10, 2, 10, 38000),
	(11, 'EMP011', 'Daniel', 'Miller', '0771234560', 'daniel.m@example.com', 'pass123', 11, 1, 11, 48000),
	(12, 'EMP012', 'Olivia', 'Wilson', '0712345601', 'olivia.w@example.com', 'pass123', 12, 2, 12, 37000),
	(13, 'EMP013', 'James', 'Moore', '0703456012', 'james.m@example.com', 'pass123', 13, 1, 13, 42000),
	(14, 'EMP014', 'Sophia', 'Taylor', '0764560123', 'sophia.t@example.com', 'pass123', 14, 2, 14, 46000),
	(15, 'EMP015', 'William', 'Hall', '0755601234', 'william.h@example.com', 'pass123', 15, 1, 15, 31000),
	(16, 'EMP016', 'Isabella', 'Adams', '0776012345', 'isabella.a@example.com', 'pass123', 16, 2, 16, 39000),
	(17, 'EMP017', 'Mia', 'Baker', '0717012345', 'mia.b@example.com', 'pass123', 17, 2, 17, 33000),
	(18, 'EMP018', 'Alexander', 'Carter', '0708012345', 'alex.c@example.com', 'pass123', 18, 1, 18, 52000),
	(19, 'EMP019', 'Charlotte', 'Davis', '0769012346', 'char.d@example.com', 'pass123', 19, 2, 19, 44000),
	(20, 'EMP020', 'Ethan', 'Evans', '0750123467', 'ethan.e@example.com', 'pass123', 20, 1, 20, 30000),
	(21, 'EMP021', 'Liam', 'Fisher', '0771234678', 'liam.f@example.com', 'pass123', 21, 1, 21, 47000),
	(22, 'EMP022', 'Ava', 'Garcia', '0712346789', 'ava.g@example.com', 'pass123', 22, 2, 22, 34000),
	(23, 'EMP023', 'Noah', 'Harris', '0703467890', 'noah.h@example.com', 'pass123', 23, 1, 23, 29000),
	(24, 'EMP024', 'Chloe', 'Jackson', '0764567891', 'chloe.j@example.com', 'pass123', 24, 2, 24, 41000),
	(25, 'EMP025', 'Lucas', 'Kennedy', '0755678912', 'lucas.k@example.com', 'pass123', 25, 1, 25, 36000);

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
INSERT INTO `employee_address` (`id`, `address`, `district_id`, `employee_id`) VALUES
	(1, '123 Main St, Apt 1A', 1, 1),
	(2, '45 High Rd, Block B', 2, 2),
	(3, '789 Oak Ave, Unit 3', 3, 3),
	(4, '101 Pine Ln, House 5', 4, 4),
	(5, '202 Maple Dr, Flat 12', 5, 5),
	(6, '303 Elm Ct, Studio 7', 6, 6),
	(7, '404 Birch Way, Bldg 9', 7, 7),
	(8, '505 Cedar Blvd, Floor 2', 8, 8),
	(9, '606 Spruce Pkwy, Apt 10', 9, 9),
	(10, '707 Willow St, Block C', 10, 10),
	(11, '808 Poplar Rd, Unit 15', 11, 11),
	(12, '909 Aspen Ave, House 20', 12, 12),
	(13, '111 Cherry Ln, Flat 8', 13, 13),
	(14, '222 Walnut Dr, Studio 1', 14, 14),
	(15, '333 Chestnut Ct, Bldg 4', 15, 15),
	(16, '444 Fir Way, Floor 6', 16, 16),
	(17, '555 Redwood Blvd, Apt 11', 17, 17),
	(18, '666 Juniper Pkwy, Block A', 18, 18),
	(19, '777 Olive St, Unit 2', 19, 19),
	(20, '888 Peach Rd, House 3', 20, 20),
	(21, '999 Plum Ave, Flat 14', 21, 21),
	(22, '1010 Lemon Ln, Studio 6', 22, 22),
	(23, '1111 Apple Dr, Bldg 1', 23, 23),
	(24, '1212 Berry Ct, Floor 5', 24, 24),
	(25, '1313 Grape Way, Apt 16', 25, 25);

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
INSERT INTO `employee_attendance` (`id`, `checkin_time`, `checkout_time`, `date`, `employee_id`, `attendance_type_id`) VALUES
	(1, '08:00:00', '17:00:00', '2024-05-01', 1, 1),
	(2, '08:30:00', '17:30:00', '2024-05-01', 2, 2),
	(3, '09:00:00', '18:00:00', '2024-05-01', 3, 3),
	(4, '08:00:00', '17:00:00', '2024-05-02', 4, 4),
	(5, '08:15:00', '17:15:00', '2024-05-02', 5, 5),
	(6, '08:45:00', '17:45:00', '2024-05-02', 6, 6),
	(7, '08:00:00', '17:00:00', '2024-05-03', 7, 7),
	(8, '09:00:00', '18:00:00', '2024-05-03', 8, 8),
	(9, '08:00:00', '17:00:00', '2024-05-04', 9, 9),
	(10, '08:30:00', '17:30:00', '2024-05-04', 10, 10),
	(11, '08:00:00', '17:00:00', '2024-05-05', 11, 11),
	(12, '08:15:00', '17:15:00', '2024-05-05', 12, 12),
	(13, '08:45:00', '17:45:00', '2024-05-05', 13, 13),
	(14, '08:00:00', '17:00:00', '2024-05-06', 14, 14),
	(15, '09:00:00', '18:00:00', '2024-05-06', 15, 15),
	(16, '08:00:00', '17:00:00', '2024-05-07', 16, 16),
	(17, '08:30:00', '17:30:00', '2024-05-07', 17, 17),
	(18, '08:00:00', '17:00:00', '2024-05-08', 18, 18),
	(19, '08:15:00', '17:15:00', '2024-05-08', 19, 19),
	(20, '08:45:00', '17:45:00', '2024-05-08', 20, 20),
	(21, '08:00:00', '17:00:00', '2024-05-09', 21, 21),
	(22, '09:00:00', '18:00:00', '2024-05-09', 22, 22),
	(23, '08:00:00', '17:00:00', '2024-05-10', 23, 23),
	(24, '08:30:00', '17:30:00', '2024-05-10', 24, 24),
	(25, '08:00:00', '17:00:00', '2024-05-11', 25, 25);

-- Dumping structure for table 123.employee_role
CREATE TABLE IF NOT EXISTS `employee_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.employee_role: ~0 rows (approximately)
INSERT INTO `employee_role` (`id`, `type`) VALUES
	(1, 'Manager'),
	(2, 'Assistant Manager'),
	(3, 'Cashier'),
	(4, 'Chef'),
	(5, 'Cook'),
	(6, 'Waiter'),
	(7, 'Barista'),
	(8, 'Cleaner'),
	(9, 'Security Guard'),
	(10, 'Driver'),
	(11, 'HR Assistant'),
	(12, 'Accountant'),
	(13, 'Marketing Executive'),
	(14, 'IT Support'),
	(15, 'Store Keeper'),
	(16, 'Delivery Driver'),
	(17, 'Customer Service Rep'),
	(18, 'Team Lead'),
	(19, 'Supervisor'),
	(20, 'Trainee'),
	(21, 'Senior Chef'),
	(22, 'Junior Cook'),
	(23, 'Dishwasher'),
	(24, 'Procurement Officer'),
	(25, 'Admin Assistant');

-- Dumping structure for table 123.gender
CREATE TABLE IF NOT EXISTS `gender` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.gender: ~0 rows (approximately)
INSERT INTO `gender` (`id`, `type`) VALUES
	(1, 'Male'),
	(2, 'Female'),
	(3, 'Non-binary'),
	(4, 'Genderqueer'),
	(5, 'Agender'),
	(6, 'Bigender'),
	(7, 'Pangender'),
	(8, 'Transgender Male'),
	(9, 'Transgender Female'),
	(10, 'Two-Spirit'),
	(11, 'Neutrois'),
	(12, 'Androgyne'),
	(13, 'Demigirl'),
	(14, 'Demiboy'),
	(15, 'Polygender'),
	(16, 'Cisgender Male'),
	(17, 'Cisgender Female'),
	(18, 'Questioning'),
	(19, 'Third Gender'),
	(20, 'Intersex'),
	(21, 'Afab'),
	(22, 'Amab'),
	(23, 'Genderfluid'),
	(24, 'Unspecified'),
	(25, 'Other');

-- Dumping structure for table 123.grn
CREATE TABLE IF NOT EXISTS `grn` (
  `id` int NOT NULL AUTO_INCREMENT,
  `grn_id` varchar(10) NOT NULL,
  `paid_amount` double NOT NULL,
  `date` datetime NOT NULL,
  `branch_id` int NOT NULL,
  `employee_id` int NOT NULL,
  `purchase_order_id` int NOT NULL,
  `supplier_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `grn_id` (`grn_id`),
  KEY `fk_grn_branch1_idx` (`branch_id`),
  KEY `fk_grn_employee1_idx` (`employee_id`),
  KEY `fk_grn_purchase_order1_idx` (`purchase_order_id`),
  KEY `fk_grn_supplier1_idx` (`supplier_id`),
  CONSTRAINT `fk_grn_branch1` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`),
  CONSTRAINT `fk_grn_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_grn_purchase_order1` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchase_order` (`id`),
  CONSTRAINT `fk_grn_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.grn: ~0 rows (approximately)
INSERT INTO `grn` (`id`, `grn_id`, `paid_amount`, `date`, `branch_id`, `employee_id`, `purchase_order_id`, `supplier_id`) VALUES
	(1, 'GRN001', 15000, '2024-01-03 11:00:00', 1, 1, 1, 1),
	(2, 'GRN002', 8000, '2024-01-07 12:00:00', 2, 2, 2, 2),
	(3, 'GRN003', 9000, '2024-01-12 13:00:00', 3, 3, 3, 3),
	(4, 'GRN004', 25000, '2024-01-17 14:00:00', 4, 4, 4, 4),
	(5, 'GRN005', 3000, '2024-01-22 15:00:00', 5, 5, 5, 5),
	(6, 'GRN006', 50000, '2024-01-27 16:00:00', 6, 6, 6, 6),
	(7, 'GRN007', 60000, '2024-02-03 17:00:00', 7, 7, 7, 7),
	(8, 'GRN008', 40000, '2024-02-07 10:30:00', 8, 8, 8, 8),
	(9, 'GRN009', 2500, '2024-02-12 11:30:00', 9, 9, 9, 9),
	(10, 'GRN010', 70000, '2024-02-17 12:30:00', 10, 10, 10, 10),
	(11, 'GRN011', 55000, '2024-02-22 13:30:00', 11, 11, 11, 11),
	(12, 'GRN012', 4000, '2024-02-27 14:30:00', 12, 12, 12, 12),
	(13, 'GRN013', 3500, '2024-03-03 15:30:00', 13, 13, 13, 13),
	(14, 'GRN014', 6000, '2024-03-07 16:30:00', 14, 14, 14, 14),
	(15, 'GRN015', 5500, '2024-03-12 17:30:00', 15, 15, 15, 15),
	(16, 'GRN016', 12000, '2024-03-17 10:45:00', 16, 16, 16, 16),
	(17, 'GRN017', 15000, '2024-03-22 11:45:00', 17, 17, 17, 17),
	(18, 'GRN018', 20000, '2024-03-27 12:45:00', 18, 18, 18, 18),
	(19, 'GRN019', 9500, '2024-04-03 13:45:00', 19, 19, 19, 19),
	(20, 'GRN020', 18000, '2024-04-07 14:45:00', 20, 20, 20, 20),
	(21, 'GRN021', 20000, '2024-04-12 15:45:00', 21, 21, 21, 21),
	(22, 'GRN022', 22000, '2024-04-17 16:45:00', 22, 22, 22, 22),
	(23, 'GRN023', 25000, '2024-04-22 17:45:00', 23, 23, 23, 23),
	(24, 'GRN024', 28000, '2024-04-27 08:00:00', 24, 24, 24, 24),
	(25, 'GRN025', 30000, '2024-05-03 09:00:00', 25, 25, 25, 25);

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
INSERT INTO `grn_item` (`id`, `quantity`, `price`, `grn_id`, `stock_id`) VALUES
	(1, '500', 150, 1, 1),
	(2, '100', 80, 2, 2),
	(3, '200', 90, 3, 3),
	(4, '150', 250, 4, 4),
	(5, '300', 30, 5, 5),
	(6, '50', 500, 6, 6),
	(7, '70', 600, 7, 7),
	(8, '120', 400, 8, 8),
	(9, '250', 25, 9, 9),
	(10, '80', 700, 10, 10),
	(11, '90', 550, 11, 11),
	(12, '400', 40, 12, 12),
	(13, '350', 35, 13, 13),
	(14, '280', 60, 14, 14),
	(15, '220', 55, 15, 15),
	(16, '180', 120, 16, 16),
	(17, '160', 150, 17, 17),
	(18, '140', 200, 18, 18),
	(19, '110', 95, 19, 19),
	(20, '90', 180, 20, 20),
	(21, '70', 200, 21, 21),
	(22, '60', 220, 22, 22),
	(23, '50', 250, 23, 23),
	(24, '40', 280, 24, 24),
	(25, '30', 300, 25, 25);

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
INSERT INTO `invoice` (`id`, `invoice_id`, `date`, `payment`, `discount`, `customer_id`, `payment_method_id`, `orders_id`, `employee_id`) VALUES
	(1, 'INV001', '2024-05-01', 300, 0, 1, 1, 1, 1),
	(2, 'INV002', '2024-05-01', 200, 10, 2, 2, 2, 2),
	(3, 'INV003', '2024-05-01', 750, 0, 3, 3, 3, 3),
	(4, 'INV004', '2024-05-01', 170, 5, 4, 4, 4, 4),
	(5, 'INV005', '2024-05-01', 240, 0, 5, 5, 5, 5),
	(6, 'INV006', '2024-05-01', 70, 0, 6, 6, 6, 6),
	(7, 'INV007', '2024-05-01', 1000, 50, 7, 7, 7, 7),
	(8, 'INV008', '2024-05-01', 360, 0, 8, 8, 8, 8),
	(9, 'INV009', '2024-05-01', 180, 0, 9, 9, 9, 9),
	(10, 'INV010', '2024-05-01', 750, 25, 10, 10, 10, 10),
	(11, 'INV011', '2024-05-02', 200, 0, 11, 11, 11, 11),
	(12, 'INV012', '2024-05-02', 400, 20, 12, 12, 12, 12),
	(13, 'INV013', '2024-05-02', 260, 0, 13, 13, 13, 13),
	(14, 'INV014', '2024-05-02', 800, 40, 14, 14, 14, 14),
	(15, 'INV015', '2024-05-02', 500, 0, 15, 15, 15, 15),
	(16, 'INV016', '2024-05-02', 150, 0, 16, 16, 16, 16),
	(17, 'INV017', '2024-05-02', 300, 15, 17, 17, 17, 17),
	(18, 'INV018', '2024-05-02', 200, 0, 18, 18, 18, 18),
	(19, 'INV019', '2024-05-02', 600, 30, 19, 19, 19, 19),
	(20, 'INV020', '2024-05-02', 400, 0, 20, 20, 20, 20),
	(21, 'INV021', '2024-05-03', 200, 10, 21, 21, 21, 21),
	(22, 'INV022', '2024-05-03', 400, 0, 22, 22, 22, 22),
	(23, 'INV023', '2024-05-03', 200, 0, 23, 23, 23, 23),
	(24, 'INV024', '2024-05-03', 600, 30, 24, 24, 24, 24),
	(25, 'INV025', '2024-05-03', 400, 0, 25, 25, 25, 25);

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
INSERT INTO `orders` (`id`, `order_id`, `order_on`, `order_status_id`, `order_no`, `order_type_id`) VALUES
	(1, 'ORD001', '2024-05-01 10:00:00', 1, 1001, 1),
	(2, 'ORD002', '2024-05-01 10:15:00', 2, 1002, 2),
	(3, 'ORD003', '2024-05-01 10:30:00', 3, 1003, 3),
	(4, 'ORD004', '2024-05-01 10:45:00', 4, 1004, 4),
	(5, 'ORD005', '2024-05-01 11:00:00', 5, 1005, 5),
	(6, 'ORD006', '2024-05-01 11:15:00', 6, 1006, 6),
	(7, 'ORD007', '2024-05-01 11:30:00', 7, 1007, 7),
	(8, 'ORD008', '2024-05-01 11:45:00', 8, 1008, 8),
	(9, 'ORD009', '2024-05-01 12:00:00', 9, 1009, 9),
	(10, 'ORD010', '2024-05-01 12:15:00', 10, 1010, 10),
	(11, 'ORD011', '2024-05-02 10:00:00', 11, 1011, 11),
	(12, 'ORD012', '2024-05-02 10:15:00', 12, 1012, 12),
	(13, 'ORD013', '2024-05-02 10:30:00', 13, 1013, 13),
	(14, 'ORD014', '2024-05-02 10:45:00', 14, 1014, 14),
	(15, 'ORD015', '2024-05-02 11:00:00', 15, 1015, 15),
	(16, 'ORD016', '2024-05-02 11:15:00', 16, 1016, 16),
	(17, 'ORD017', '2024-05-02 11:30:00', 17, 1017, 17),
	(18, 'ORD018', '2024-05-02 11:45:00', 18, 1018, 18),
	(19, 'ORD019', '2024-05-02 12:00:00', 19, 1019, 19),
	(20, 'ORD020', '2024-05-02 12:15:00', 20, 1020, 20),
	(21, 'ORD021', '2024-05-03 10:00:00', 21, 1021, 21),
	(22, 'ORD022', '2024-05-03 10:15:00', 22, 1022, 22),
	(23, 'ORD023', '2024-05-03 10:30:00', 23, 1023, 23),
	(24, 'ORD024', '2024-05-03 10:45:00', 24, 1024, 24),
	(25, 'ORD025', '2024-05-03 11:00:00', 25, 1025, 25);

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
INSERT INTO `order_item` (`id`, `qty`, `orders_id`, `product_has_size_id`) VALUES
	(1, 2, 1, 1),
	(2, 1, 2, 2),
	(3, 3, 3, 3),
	(4, 1, 4, 4),
	(5, 2, 5, 5),
	(6, 1, 6, 6),
	(7, 4, 7, 7),
	(8, 2, 8, 8),
	(9, 1, 9, 9),
	(10, 3, 10, 10),
	(11, 1, 11, 11),
	(12, 2, 12, 12),
	(13, 1, 13, 13),
	(14, 3, 14, 14),
	(15, 2, 15, 15),
	(16, 1, 16, 16),
	(17, 2, 17, 17),
	(18, 1, 18, 18),
	(19, 3, 19, 19),
	(20, 2, 20, 20),
	(21, 1, 21, 21),
	(22, 2, 22, 22),
	(23, 1, 23, 23),
	(24, 3, 24, 24),
	(25, 2, 25, 25);

-- Dumping structure for table 123.order_status
CREATE TABLE IF NOT EXISTS `order_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.order_status: ~0 rows (approximately)
INSERT INTO `order_status` (`id`, `type`) VALUES
	(1, 'Pending'),
	(2, 'Confirmed'),
	(3, 'Preparing'),
	(4, 'Ready for Pickup'),
	(5, 'Out for Delivery'),
	(6, 'Delivered'),
	(7, 'Completed'),
	(8, 'Canceled'),
	(9, 'Refunded'),
	(10, 'On Hold'),
	(11, 'Rejected'),
	(12, 'Partially Delivered'),
	(13, 'Scheduled'),
	(14, 'Failed Delivery'),
	(15, 'Returned'),
	(16, 'Awaiting Payment'),
	(17, 'Payment Received'),
	(18, 'Processing'),
	(19, 'Dispatched'),
	(20, 'Awaiting Confirmation'),
	(21, 'Delayed'),
	(22, 'Rescheduled'),
	(23, 'Picked Up'),
	(24, 'Shipped'),
	(25, 'Archived');

-- Dumping structure for table 123.order_type
CREATE TABLE IF NOT EXISTS `order_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.order_type: ~0 rows (approximately)
INSERT INTO `order_type` (`id`, `name`) VALUES
	(1, 'Dine-in'),
	(2, 'Takeaway'),
	(3, 'Delivery'),
	(4, 'Online Order'),
	(5, 'Phone Order'),
	(6, 'Catering'),
	(7, 'Pre-order'),
	(8, 'Subscription'),
	(9, 'Walk-in'),
	(10, 'Drive-Thru'),
	(11, 'Curbside Pickup'),
	(12, 'Group Order'),
	(13, 'Corporate Order'),
	(14, 'Event Order'),
	(15, 'Bulk Order'),
	(16, 'App Order'),
	(17, 'Website Order'),
	(18, 'Third-Party App'),
	(19, 'Express Delivery'),
	(20, 'Standard Delivery'),
	(21, 'Pickup At Store'),
	(22, 'Scheduled Delivery'),
	(23, 'Immediate Pickup'),
	(24, 'Rush Order'),
	(25, 'Custom Order');

-- Dumping structure for table 123.payment_method
CREATE TABLE IF NOT EXISTS `payment_method` (
  `id` int NOT NULL AUTO_INCREMENT,
  `method` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.payment_method: ~0 rows (approximately)
INSERT INTO `payment_method` (`id`, `method`) VALUES
	(1, 'Cash'),
	(2, 'Credit Card'),
	(3, 'Debit Card'),
	(4, 'Online Transfer'),
	(5, 'Mobile Payment'),
	(6, 'Cheque'),
	(7, 'Bank Transfer'),
	(8, 'Gift Card'),
	(9, 'Loyalty Points'),
	(10, 'E-wallet'),
	(11, 'Installment Plan'),
	(12, 'QR Code'),
	(13, 'UPI'),
	(14, 'Crypto'),
	(15, 'Prepaid Card'),
	(16, 'Voucher'),
	(17, 'POS Terminal'),
	(18, 'Direct Debit'),
	(19, 'Cash on Delivery'),
	(20, 'Paypal'),
	(21, 'Stripe'),
	(22, 'Apple Pay'),
	(23, 'Google Pay'),
	(24, 'AliPay'),
	(25, 'WeChat Pay');

-- Dumping structure for table 123.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `product_id` varchar(10) NOT NULL,
  `description` longtext NOT NULL,
  `added_date` datetime NOT NULL,
  `product_status_id` int NOT NULL,
  `sub_category_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_id` (`product_id`),
  KEY `fk_product_product_status1_idx` (`product_status_id`),
  KEY `fk_product_sub_category1_idx` (`sub_category_id`),
  CONSTRAINT `fk_product_product_status1` FOREIGN KEY (`product_status_id`) REFERENCES `product_status` (`id`),
  CONSTRAINT `fk_product_sub_category1` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.product: ~12 rows (approximately)
INSERT INTO `product` (`id`, `title`, `product_id`, `description`, `added_date`, `product_status_id`, `sub_category_id`) VALUES
	(1, 'Kottu', 'pdct001', 'parata kottu', '2025-05-21 02:36:09', 1, 1),
	(2, 'Rice', 'pdct002', 'Baasmathi rice', '2025-05-21 03:06:27', 2, 2),
	(3, 'Biriyani', 'pdct003', 'dawdaw', '2025-05-21 03:07:49', 3, 3),
	(4, 'Nasiguran', 'pdct004', 'fsdfs', '2025-05-21 03:08:27', 4, 4),
	(5, 'Shorteats', 'pdct005', 'fserfweg', '2025-05-21 03:09:47', 5, 5),
	(6, 'Plain Tea', 'pdct006', 'fsef', '2025-05-21 03:10:22', 6, 6),
	(7, 'Water', 'pdct007', 'fsadf', '2025-05-21 03:13:41', 7, 7),
	(8, 'Cocacola', 'pdct008', 'daffse', '2025-05-21 03:14:06', 8, 8),
	(9, 'Faluda', 'pdct009', 'dfgsdf', '2025-05-21 03:14:51', 9, 9),
	(10, 'Sprite', 'pdct010', 'bcxv', '2025-05-21 19:22:37', 10, 10),
	(11, 'Pepsi', 'pdct011', 'csfds', '2025-05-21 19:23:21', 11, 11),
	(12, 'Portelo', 'pdct012', 'sdfsdf', '2025-05-21 19:24:29', 12, 12),
	(13, 'Chicken Biriyani', 'pdct013', 'Aromatic basmati rice with tender chicken.', '2025-05-21 19:30:00', 13, 13),
	(14, 'Egg Hopper', 'pdct014', 'Crispy hopper with a fried egg.', '2025-05-21 19:31:00', 14, 14),
	(15, 'Curd with Treacle', 'pdct015', 'Traditional Sri Lankan dessert.', '2025-05-21 19:32:00', 15, 15),
	(16, 'Vegetable Fried Rice', 'pdct016', 'Wok-tossed rice with fresh veggies.', '2025-05-21 19:33:00', 16, 16),
	(17, 'Cheese Kottu', 'pdct017', 'Shredded rotti with cheese and vegetables.', '2025-05-21 19:34:00', 17, 17),
	(18, 'Iced Coffee', 'pdct018', 'Chilled coffee with milk and sugar.', '2025-05-21 19:35:00', 18, 18),
	(19, 'Orange Juice', 'pdct019', 'Freshly squeezed orange juice.', '2025-05-21 19:36:00', 19, 19),
	(20, 'Chocolate Milkshake', 'pdct020', 'Rich chocolate blend with ice cream.', '2025-05-21 19:37:00', 20, 20),
	(21, 'Hot Chocolate', 'pdct021', 'Warm and comforting chocolate drink.', '2025-05-21 19:38:00', 21, 21),
	(22, 'Chicken Noodle Soup', 'pdct022', 'Hearty chicken broth with noodles.', '2025-05-21 19:39:00', 22, 22),
	(23, 'Prawn Curry', 'pdct023', 'Spicy prawn curry, ideal with rice.', '2025-05-21 19:40:00', 23, 23),
	(24, 'Watalappan', 'pdct024', 'Traditional Sri Lankan jaggery pudding.', '2025-05-21 19:41:00', 24, 24),
	(25, 'Sri Lankan Coffee', 'pdct025', 'Strong local coffee.', '2025-05-21 19:42:00', 25, 25);

-- Dumping structure for table 123.product_has_size
CREATE TABLE IF NOT EXISTS `product_has_size` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `size_id` int NOT NULL,
  `price` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_has_size_size1_idx` (`size_id`),
  KEY `fk_product_has_size_product1_idx` (`product_id`),
  CONSTRAINT `fk_product_has_size_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_product_has_size_size1` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.product_has_size: ~0 rows (approximately)
INSERT INTO `product_has_size` (`id`, `product_id`, `size_id`, `price`) VALUES
	(1, 1, 1, '150.00'),
	(2, 2, 2, '200.00'),
	(3, 3, 3, '250.00'),
	(4, 4, 4, '180.00'),
	(5, 5, 5, '120.00'),
	(6, 6, 6, '50.00'),
	(7, 7, 7, '100.00'),
	(8, 8, 8, '120.00'),
	(9, 9, 9, '180.00'),
	(10, 10, 10, '110.00'),
	(11, 11, 11, '120.00'),
	(12, 12, 12, '130.00'),
	(13, 13, 13, '250.00'),
	(14, 14, 14, '70.00'),
	(15, 15, 15, '150.00'),
	(16, 16, 16, '150.00'),
	(17, 17, 17, '200.00'),
	(18, 18, 18, '180.00'),
	(19, 19, 19, '200.00'),
	(20, 20, 20, '220.00'),
	(21, 21, 21, '190.00'),
	(22, 22, 22, '210.00'),
	(23, 23, 23, '280.00'),
	(24, 24, 24, '160.00'),
	(25, 25, 25, '100.00');

-- Dumping structure for table 123.product_images
CREATE TABLE IF NOT EXISTS `product_images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `url` text NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_images_product1_idx` (`product_id`),
  CONSTRAINT `fk_product_images_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.product_images: ~25 rows (approximately)
INSERT INTO `product_images` (`id`, `url`, `product_id`) VALUES
	(1, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_1.png', 1),
	(2, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_2.png', 2),
	(3, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_3.png', 3),
	(4, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_4.png', 4),
	(5, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_5.png', 5),
	(6, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_6.png', 6),
	(7, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_7.png', 7),
	(8, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_8.png', 8),
	(9, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_9.png', 9),
	(10, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_10.png', 10),
	(11, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_11.png', 11),
	(12, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_12.png', 12),
	(13, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_13.png', 13),
	(14, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_14.png', 14),
	(15, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_15.png', 15),
	(16, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_16.png', 16),
	(17, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_17.png', 17),
	(18, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_18.png', 18),
	(19, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_19.png', 19),
	(20, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_20.png', 20),
	(21, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_21.png', 21),
	(22, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_22.png', 22),
	(23, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_23.png', 23),
	(24, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_24.png', 24),
	(25, 'C:\\Users\\USER\\Documents\\NetBeansProjects\\PlatePal1.3\\src\\resourcess\\150logo_25.png', 25);

-- Dumping structure for table 123.product_status
CREATE TABLE IF NOT EXISTS `product_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.product_status: ~2 rows (approximately)
INSERT INTO `product_status` (`id`, `name`) VALUES
	(1, 'Active'),
	(2, 'Inactive'),
	(3, 'Discontinued'),
	(4, 'Seasonal'),
	(5, 'New Arrival'),
	(6, 'Limited Edition'),
	(7, 'Coming Soon'),
	(8, 'Out of Stock'),
	(9, 'Pre-Order'),
	(10, 'On Sale'),
	(11, 'Clearance'),
	(12, 'Backorder'),
	(13, 'Expired'),
	(14, 'Damaged'),
	(15, 'Refurbished'),
	(16, 'Available for Order'),
	(17, 'Pending Approval'),
	(18, 'Under Review'),
	(19, 'Draft'),
	(20, 'Published'),
	(21, 'Archived'),
	(22, 'Hidden'),
	(23, 'Featured'),
	(24, 'Promotional'),
	(25, 'Banned');

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
INSERT INTO `purchase_order` (`id`, `po_id`, `issued_date`, `supplier_id`, `branch_id`) VALUES
	(1, 'PO001', '2024-01-02 10:00:00', 1, 1),
	(2, 'PO002', '2024-01-06 11:00:00', 2, 2),
	(3, 'PO003', '2024-01-11 12:00:00', 3, 3),
	(4, 'PO004', '2024-01-16 13:00:00', 4, 4),
	(5, 'PO005', '2024-01-21 14:00:00', 5, 5),
	(6, 'PO006', '2024-01-26 15:00:00', 6, 6),
	(7, 'PO007', '2024-02-02 16:00:00', 7, 7),
	(8, 'PO008', '2024-02-06 17:00:00', 8, 8),
	(9, 'PO009', '2024-02-11 10:30:00', 9, 9),
	(10, 'PO010', '2024-02-16 11:30:00', 10, 10),
	(11, 'PO011', '2024-02-21 12:30:00', 11, 11),
	(12, 'PO012', '2024-02-26 13:30:00', 12, 12),
	(13, 'PO013', '2024-03-02 14:30:00', 13, 13),
	(14, 'PO014', '2024-03-06 15:30:00', 14, 14),
	(15, 'PO015', '2024-03-11 16:30:00', 15, 15),
	(16, 'PO016', '2024-03-16 17:30:00', 16, 16),
	(17, 'PO017', '2024-03-21 10:45:00', 17, 17),
	(18, 'PO018', '2024-03-26 11:45:00', 18, 18),
	(19, 'PO019', '2024-04-02 12:45:00', 19, 19),
	(20, 'PO020', '2024-04-06 13:45:00', 20, 20),
	(21, 'PO021', '2024-04-11 14:45:00', 21, 21),
	(22, 'PO022', '2024-04-16 15:45:00', 22, 22),
	(23, 'PO023', '2024-04-21 16:45:00', 23, 23),
	(24, 'PO024', '2024-04-26 17:45:00', 24, 24),
	(25, 'PO025', '2024-05-02 08:00:00', 25, 25);

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
INSERT INTO `purchase_order_item` (`id`, `quantity`, `price`, `purchase_order_id`, `stock_product_id`) VALUES
	(1, '50', 15000, 1, 1),
	(2, '10', 8000, 2, 2),
	(3, '20', 9000, 3, 3),
	(4, '15', 25000, 4, 4),
	(5, '30', 3000, 5, 5),
	(6, '5', 50000, 6, 6),
	(7, '7', 60000, 7, 7),
	(8, '12', 40000, 8, 8),
	(9, '25', 2500, 9, 9),
	(10, '8', 70000, 10, 10),
	(11, '9', 55000, 11, 11),
	(12, '40', 4000, 12, 12),
	(13, '35', 3500, 13, 13),
	(14, '28', 6000, 14, 14),
	(15, '22', 5500, 15, 15),
	(16, '18', 12000, 16, 16),
	(17, '16', 15000, 17, 17),
	(18, '14', 20000, 18, 18),
	(19, '11', 9500, 19, 19),
	(20, '9', 18000, 20, 20),
	(21, '7', 20000, 21, 21),
	(22, '6', 22000, 22, 22),
	(23, '5', 25000, 23, 23),
	(24, '4', 28000, 24, 24),
	(25, '3', 30000, 25, 25);

-- Dumping structure for table 123.qty_type
CREATE TABLE IF NOT EXISTS `qty_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.qty_type: ~0 rows (approximately)
INSERT INTO `qty_type` (`id`, `type`) VALUES
	(1, 'kg'),
	(2, 'g'),
	(3, 'L'),
	(4, 'ml'),
	(5, 'pcs'),
	(6, 'pack'),
	(7, 'bundle'),
	(8, 'box'),
	(9, 'crate'),
	(10, 'dozen'),
	(11, 'sack'),
	(12, 'bottle'),
	(13, 'can'),
	(14, 'cup'),
	(15, 'unit'),
	(16, 'sheet'),
	(17, 'meter'),
	(18, 'cm'),
	(19, 'roll'),
	(20, 'bag'),
	(21, 'bunch'),
	(22, 'drum'),
	(23, 'carton'),
	(24, 'pallet'),
	(25, 'case');

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
INSERT INTO `request` (`id`, `message`, `dateTime`, `from`, `to`) VALUES
	(1, 'Need more stock for Rice.', '2024-05-01 09:00:00', 1, 2),
	(2, 'Approve leave for Jane.', '2024-05-01 10:00:00', 2, 1),
	(3, 'Customer feedback on new menu item.', '2024-05-02 11:00:00', 3, 4),
	(4, 'Report missing inventory from last night.', '2024-05-02 12:00:00', 4, 3),
	(5, 'Requesting budget for marketing campaign.', '2024-05-03 13:00:00', 5, 6),
	(6, 'Confirm delivery schedule for tomorrow.', '2024-05-03 14:00:00', 6, 5),
	(7, 'Issue with POS system in Branch 7.', '2024-05-04 15:00:00', 7, 8),
	(8, 'New employee onboarding materials needed.', '2024-05-04 16:00:00', 8, 7),
	(9, 'Table reservation for 5 on Friday.', '2024-05-05 09:30:00', 9, 10),
	(10, 'Urgent repair for kitchen equipment.', '2024-05-05 10:30:00', 10, 9),
	(11, 'Supplier payment reminder.', '2024-05-06 11:30:00', 11, 12),
	(12, 'Quarterly performance review meeting.', '2024-05-06 12:30:00', 12, 11),
	(13, 'Suggest new uniform design.', '2024-05-07 13:30:00', 13, 14),
	(14, 'Need assistance with weekend shift.', '2024-05-07 14:30:00', 14, 13),
	(15, 'Update on customer loyalty program.', '2024-05-08 15:30:00', 15, 16),
	(16, 'Feedback on new training module.', '2024-05-08 16:30:00', 16, 15),
	(17, 'Damage report from delivery.', '2024-05-09 09:00:00', 17, 18),
	(18, 'Enquiry about career progression.', '2024-05-09 10:00:00', 18, 17),
	(19, 'Proposal for new product launch.', '2024-05-10 11:00:00', 19, 20),
	(20, 'Issue with website online ordering.', '2024-05-10 12:00:00', 20, 19),
	(21, 'Request for extra cleaning supplies.', '2024-05-11 13:00:00', 21, 22),
	(22, 'Question about employee benefits.', '2024-05-11 14:00:00', 22, 21),
	(23, 'Need catering quote for corporate event.', '2024-05-12 15:00:00', 23, 24),
	(24, 'Report on monthly sales targets.', '2024-05-12 16:00:00', 24, 23),
	(25, 'Urgent - inventory count discrepancy.', '2024-05-13 10:00:00', 25, 1);

-- Dumping structure for table 123.salary_payments
CREATE TABLE IF NOT EXISTS `salary_payments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `salary_payments_id` varchar(10) NOT NULL,
  `paid_date` date NOT NULL,
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
INSERT INTO `salary_payments` (`id`, `salary_payments_id`, `paid_date`, `paid_amount`, `total_regular_hours`, `total_ot_hours`, `employee_id`) VALUES
	(1, 'SAL001', '2024-05-20', 60000, 160, 10, 1),
	(2, 'SAL002', '2024-05-20', 55000, 160, 8, 2),
	(3, 'SAL003', '2024-05-20', 35000, 160, 5, 3),
	(4, 'SAL004', '2024-05-20', 45000, 160, 7, 4),
	(5, 'SAL005', '2024-05-20', 40000, 160, 6, 5),
	(6, 'SAL006', '2024-05-20', 30000, 160, 0, 6),
	(7, 'SAL007', '2024-05-20', 32000, 160, 2, 7),
	(8, 'SAL008', '2024-05-20', 28000, 160, 0, 8),
	(9, 'SAL009', '2024-05-20', 50000, 160, 12, 9),
	(10, 'SAL010', '2024-05-20', 38000, 160, 4, 10),
	(11, 'SAL011', '2024-05-20', 48000, 160, 9, 11),
	(12, 'SAL012', '2024-05-20', 37000, 160, 3, 12),
	(13, 'SAL013', '2024-05-20', 42000, 160, 6, 13),
	(14, 'SAL014', '2024-05-20', 46000, 160, 8, 14),
	(15, 'SAL015', '2024-05-20', 31000, 160, 1, 15),
	(16, 'SAL016', '2024-05-20', 39000, 160, 5, 16),
	(17, 'SAL017', '2024-05-20', 33000, 160, 2, 17),
	(18, 'SAL018', '2024-05-20', 52000, 160, 10, 18),
	(19, 'SAL019', '2024-05-20', 44000, 160, 7, 19),
	(20, 'SAL020', '2024-05-20', 30000, 160, 0, 20),
	(21, 'SAL021', '2024-05-20', 47000, 160, 9, 21),
	(22, 'SAL022', '2024-05-20', 34000, 160, 3, 22),
	(23, 'SAL023', '2024-05-20', 29000, 160, 0, 23),
	(24, 'SAL024', '2024-05-20', 41000, 160, 6, 24),
	(25, 'SAL025', '2024-05-20', 36000, 160, 4, 25);

-- Dumping structure for table 123.selling_status
CREATE TABLE IF NOT EXISTS `selling_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.selling_status: ~0 rows (approximately)
INSERT INTO `selling_status` (`id`, `status`) VALUES
	(1, 'Available'),
	(2, 'Out of Stock'),
	(3, 'Limited Stock'),
	(4, 'Pre-order'),
	(5, 'Discontinued'),
	(6, 'Seasonal'),
	(7, 'New Arrival'),
	(8, 'Best Seller'),
	(9, 'Clearance'),
	(10, 'On Sale'),
	(11, 'Backorder'),
	(12, 'Coming Soon'),
	(13, 'Expired'),
	(14, 'Damaged'),
	(15, 'Refurbished'),
	(16, 'Reserved'),
	(17, 'Pending Delivery'),
	(18, 'Dispatched'),
	(19, 'Completed'),
	(20, 'Canceled'),
	(21, 'Returned'),
	(22, 'On Hold'),
	(23, 'Partially Available'),
	(24, 'Exclusive'),
	(25, 'Popular');

-- Dumping structure for table 123.size
CREATE TABLE IF NOT EXISTS `size` (
  `id` int NOT NULL AUTO_INCREMENT,
  `size_type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.size: ~3 rows (approximately)
INSERT INTO `size` (`id`, `size_type`) VALUES
	(1, 'Small'),
	(2, 'Medium'),
	(3, 'Large'),
	(4, 'Extra Large'),
	(5, 'Family'),
	(6, 'Single'),
	(7, 'Double'),
	(8, 'Kilo'),
	(9, 'Gram'),
	(10, 'Liter'),
	(11, 'Milliliter'),
	(12, 'Gallon'),
	(13, 'Cup'),
	(14, 'Plate'),
	(15, 'Bowl'),
	(16, 'Glass'),
	(17, 'Bottle'),
	(18, 'Can'),
	(19, 'Pack'),
	(20, 'Unit'),
	(21, 'Piece'),
	(22, 'Dozen'),
	(23, 'Bundle'),
	(24, 'Box'),
	(25, 'Tray');

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
INSERT INTO `stock` (`id`, `stock_id`, `added_date`, `branch_id`) VALUES
	(1, 'STK001', '2024-01-01 08:00:00', 1),
	(2, 'STK002', '2024-01-05 09:00:00', 2),
	(3, 'STK003', '2024-01-10 10:00:00', 3),
	(4, 'STK004', '2024-01-15 11:00:00', 4),
	(5, 'STK005', '2024-01-20 12:00:00', 5),
	(6, 'STK006', '2024-01-25 13:00:00', 6),
	(7, 'STK007', '2024-02-01 14:00:00', 7),
	(8, 'STK008', '2024-02-05 15:00:00', 8),
	(9, 'STK009', '2024-02-10 16:00:00', 9),
	(10, 'STK010', '2024-02-15 17:00:00', 10),
	(11, 'STK011', '2024-02-20 18:00:00', 11),
	(12, 'STK012', '2024-02-25 08:30:00', 12),
	(13, 'STK013', '2024-03-01 09:30:00', 13),
	(14, 'STK014', '2024-03-05 10:30:00', 14),
	(15, 'STK015', '2024-03-10 11:30:00', 15),
	(16, 'STK016', '2024-03-15 12:30:00', 16),
	(17, 'STK017', '2024-03-20 13:30:00', 17),
	(18, 'STK018', '2024-03-25 14:30:00', 18),
	(19, 'STK019', '2024-04-01 15:30:00', 19),
	(20, 'STK020', '2024-04-05 16:30:00', 20),
	(21, 'STK021', '2024-04-10 17:30:00', 21),
	(22, 'STK022', '2024-04-15 08:45:00', 22),
	(23, 'STK023', '2024-04-20 09:45:00', 23),
	(24, 'STK024', '2024-04-25 10:45:00', 24),
	(25, 'STK025', '2024-05-01 11:45:00', 25);

-- Dumping structure for table 123.stock_item
CREATE TABLE IF NOT EXISTS `stock_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stock_item_id` varchar(10) NOT NULL,
  `added_date` datetime NOT NULL,
  `qty` double NOT NULL,
  `price` double NOT NULL,
  `stock_product_id` int NOT NULL,
  `stock_id` int NOT NULL,
  `qty_type_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `stock_item_id` (`stock_item_id`),
  KEY `fk_stock_item_stock_product1_idx` (`stock_product_id`),
  KEY `fk_stock_item_stock1_idx` (`stock_id`),
  KEY `fk_stock_item_qty_type1_idx` (`qty_type_id`),
  CONSTRAINT `fk_stock_item_qty_type1` FOREIGN KEY (`qty_type_id`) REFERENCES `qty_type` (`id`),
  CONSTRAINT `fk_stock_item_stock1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`),
  CONSTRAINT `fk_stock_item_stock_product1` FOREIGN KEY (`stock_product_id`) REFERENCES `stock_product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.stock_item: ~0 rows (approximately)
INSERT INTO `stock_item` (`id`, `stock_item_id`, `added_date`, `qty`, `price`, `stock_product_id`, `stock_id`, `qty_type_id`) VALUES
	(1, 'SI001', '2024-01-01 08:30:00', 500, 150, 1, 1, 1),
	(2, 'SI002', '2024-01-05 09:30:00', 100, 80, 2, 2, 2),
	(3, 'SI003', '2024-01-10 10:30:00', 200, 90, 3, 3, 3),
	(4, 'SI004', '2024-01-15 11:30:00', 150, 250, 4, 4, 4),
	(5, 'SI005', '2024-01-20 12:30:00', 300, 30, 5, 5, 5),
	(6, 'SI006', '2024-01-25 13:30:00', 50, 500, 6, 6, 6),
	(7, 'SI007', '2024-02-01 14:30:00', 70, 600, 7, 7, 7),
	(8, 'SI008', '2024-02-05 15:30:00', 120, 400, 8, 8, 8),
	(9, 'SI009', '2024-02-10 16:30:00', 250, 25, 9, 9, 9),
	(10, 'SI010', '2024-02-15 17:30:00', 80, 700, 10, 10, 10),
	(11, 'SI011', '2024-02-20 18:30:00', 90, 550, 11, 11, 11),
	(12, 'SI012', '2024-02-25 09:00:00', 400, 40, 12, 12, 12),
	(13, 'SI013', '2024-03-01 10:00:00', 350, 35, 13, 13, 13),
	(14, 'SI014', '2024-03-05 11:00:00', 280, 60, 14, 14, 14),
	(15, 'SI015', '2024-03-10 12:00:00', 220, 55, 15, 15, 15),
	(16, 'SI016', '2024-03-15 13:00:00', 180, 120, 16, 16, 16),
	(17, 'SI017', '2024-03-20 14:00:00', 160, 150, 17, 17, 17),
	(18, 'SI018', '2024-03-25 15:00:00', 140, 200, 18, 18, 18),
	(19, 'SI019', '2024-04-01 16:00:00', 110, 95, 19, 19, 19),
	(20, 'SI020', '2024-04-05 17:00:00', 90, 180, 20, 20, 20),
	(21, 'SI021', '2024-04-10 18:00:00', 70, 200, 21, 21, 21),
	(22, 'SI022', '2024-04-15 09:15:00', 60, 220, 22, 22, 22),
	(23, 'SI023', '2024-04-20 10:15:00', 50, 250, 23, 23, 23),
	(24, 'SI024', '2024-04-25 11:15:00', 40, 280, 24, 24, 24),
	(25, 'SI025', '2024-05-01 12:15:00', 30, 300, 25, 25, 25);

-- Dumping structure for table 123.stock_product
CREATE TABLE IF NOT EXISTS `stock_product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stock_product_id` varchar(10) NOT NULL,
  `title` varchar(45) NOT NULL,
  `product_status_id` int NOT NULL,
  `selling_status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `stock_product_id` (`stock_product_id`),
  KEY `fk_stock_product_product_status1_idx` (`product_status_id`),
  KEY `fk_stock_product_selling_status1_idx` (`selling_status_id`),
  CONSTRAINT `fk_stock_product_product_status1` FOREIGN KEY (`product_status_id`) REFERENCES `product_status` (`id`),
  CONSTRAINT `fk_stock_product_selling_status1` FOREIGN KEY (`selling_status_id`) REFERENCES `selling_status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.stock_product: ~0 rows (approximately)
INSERT INTO `stock_product` (`id`, `stock_product_id`, `title`, `product_status_id`, `selling_status_id`) VALUES
	(1, 'SP001', 'Rice - Basmati', 1, 1),
	(2, 'SP002', 'Flour - Wheat', 2, 2),
	(3, 'SP003', 'Sugar - White', 3, 3),
	(4, 'SP004', 'Cooking Oil - Coconut', 4, 4),
	(5, 'SP005', 'Salt - Iodized', 5, 5),
	(6, 'SP006', 'Tea Leaves - Ceylon', 6, 6),
	(7, 'SP007', 'Coffee Beans - Roasted', 7, 7),
	(8, 'SP008', 'Milk Powder - Full Cream', 8, 8),
	(9, 'SP009', 'Eggs - Brown', 9, 9),
	(10, 'SP010', 'Chicken - Whole', 10, 10),
	(11, 'SP011', 'Fish - Tuna', 11, 11),
	(12, 'SP012', 'Potatoes - Local', 12, 12),
	(13, 'SP013', 'Onions - Red', 13, 13),
	(14, 'SP014', 'Tomatoes - Fresh', 14, 14),
	(15, 'SP015', 'Carrots - Fresh', 15, 15),
	(16, 'SP016', 'Garlic - Cloves', 16, 16),
	(17, 'SP017', 'Ginger - Root', 17, 17),
	(18, 'SP018', 'Chillies - Green', 18, 18),
	(19, 'SP019', 'Lentils - Red', 19, 19),
	(20, 'SP020', 'Coconut - Fresh', 20, 20),
	(21, 'SP021', 'Bread - Sliced', 21, 21),
	(22, 'SP022', 'Butter - Unsalted', 22, 22),
	(23, 'SP023', 'Cheese - Cheddar', 23, 23),
	(24, 'SP024', 'Yogurt - Plain', 24, 24),
	(25, 'SP025', 'Apples - Red', 25, 25);

-- Dumping structure for table 123.sub_category
CREATE TABLE IF NOT EXISTS `sub_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `sub_categorycol` varchar(45) DEFAULT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sub_category_category1_idx` (`category_id`),
  CONSTRAINT `fk_sub_category_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table 123.sub_category: ~0 rows (approximately)
INSERT INTO `sub_category` (`id`, `title`, `sub_categorycol`, `category_id`) VALUES
	(1, 'Rice & Curry', NULL, 1),
	(2, 'Kottu Varieties', NULL, 2),
	(3, 'Noodle Dishes', NULL, 3),
	(4, 'Biriyani Specials', NULL, 4),
	(5, 'Short Eats', NULL, 5),
	(6, 'Desserts', NULL, 6),
	(7, 'Soup & Salads', NULL, 7),
	(8, 'Fresh Juices', NULL, 8),
	(9, 'Hot Beverages', NULL, 9),
	(10, 'Cold Beverages', NULL, 10),
	(11, 'Milkshakes', NULL, 11),
	(12, 'Smoothies', NULL, 12),
	(13, 'Carbonated Drinks', NULL, 13),
	(14, 'Bottled Water', NULL, 14),
	(15, 'Breakfast Items', NULL, 15),
	(16, 'Lunch Combos', NULL, 16),
	(17, 'Dinner Platters', NULL, 17),
	(18, 'Vegetarian Options', NULL, 18),
	(19, 'Seafood Specials', NULL, 19),
	(20, 'Meat Dishes', NULL, 20),
	(21, 'Local Delicacies', NULL, 21),
	(22, 'Foreign Cuisine', NULL, 22),
	(23, 'Sweet Treats', NULL, 23),
	(24, 'Energy Drinks', NULL, 24),
	(25, 'Herbal Drinks', NULL, 25);

-- Dumping structure for table 123.supplier
CREATE TABLE IF NOT EXISTS `supplier` (
  `id` int NOT NULL AUTO_INCREMENT,
  `supplier_id` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `mobile` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `company_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `supplier_id` (`supplier_id`),
  KEY `fk_supplier_company1_idx` (`company_id`),
  CONSTRAINT `fk_supplier_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table 123.supplier: ~0 rows (approximately)
INSERT INTO `supplier` (`id`, `supplier_id`, `name`, `mobile`, `email`, `company_id`) VALUES
	(1, 'SUP001', 'Fresh Produce Distributors', '0711112233', 'fresh@supplier.com', 1),
	(2, 'SUP002', 'Quality Spices Inc.', '0722223344', 'spices@supplier.com', 2),
	(3, 'SUP003', 'Seafood Master', '0733334455', 'seafood@supplier.com', 3),
	(4, 'SUP004', 'Dairy Best', '0744445566', 'dairy@supplier.com', 4),
	(5, 'SUP005', 'Beverage King', '0755556677', 'beverages@supplier.com', 5),
	(6, 'SUP006', 'Organic Veggies', '0766667788', 'organic@supplier.com', 6),
	(7, 'SUP007', 'Island Staples', '0777778899', 'staples@supplier.com', 7),
	(8, 'SUP008', 'The Baker\'s Delight', '0788889900', 'bakers@supplier.com', 8),
	(9, 'SUP009', 'Agri Supply Co.', '0799990011', 'agrisupply@supplier.com', 9),
	(10, 'SUP010', 'Coastal Transport', '0700001122', 'transport@supplier.com', 10),
	(11, 'SUP011', 'Tea Garden Supplies', '0711112234', 'tea@supplier.com', 11),
	(12, 'SUP012', 'Pure Water Bottlers', '0722223345', 'water@supplier.com', 12),
	(13, 'SUP013', 'Spice & Herbs Global', '0733334456', 'herbs@supplier.com', 13),
	(14, 'SUP014', 'Catering Essentials', '0744445567', 'catering@supplier.com', 14),
	(15, 'SUP015', 'Daily Fresh Foods', '0755556678', 'dailyfresh@supplier.com', 15),
	(16, 'SUP016', 'Sweet Indulgence', '0766667789', 'sweets@supplier.com', 16),
	(17, 'SUP017', 'Vegetable Valley', '0777778890', 'vegvalley@supplier.com', 17),
	(18, 'SUP018', 'Coconut Products Intl.', '0788889901', 'coconut@supplier.com', 18),
	(19, 'SUP019', 'Rice Bowl Corp.', '0799990012', 'ricebowl@supplier.com', 19),
	(20, 'SUP020', 'Palm Oil King', '0700001123', 'palmoil@supplier.com', 20),
	(21, 'SUP021', 'Aroma Spices', '0711112236', 'aroma@supplier.com', 21),
	(22, 'SUP022', 'Gold Bake', '0722223347', 'goldbake@supplier.com', 22),
	(23, 'SUP023', 'Fruit & Veg Exporters', '0733334458', 'fruitvex@supplier.com', 23),
	(24, 'SUP024', 'Ceylon Coffee Beans', '0744445569', 'coffeebeans@supplier.com', 24),
	(25, 'SUP025', 'Naturals Food Co.', '0755556680', 'naturals@supplier.com', 25);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
