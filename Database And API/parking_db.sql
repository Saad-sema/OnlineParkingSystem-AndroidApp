-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: Nov 19, 2025 at 01:04 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `parking_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking_history`
--

CREATE TABLE `booking_history` (
  `b_history_id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `bookingId` int(11) NOT NULL,
  `paymentId` int(11) NOT NULL,
  `User_Name` varchar(25) NOT NULL,
  `Phone_No` varchar(15) NOT NULL,
  `Vehicle_No` varchar(15) NOT NULL,
  `StartTime` datetime NOT NULL,
  `EndTime` datetime NOT NULL,
  `SlotName` varchar(25) NOT NULL,
  `Payment_Method` varchar(30) NOT NULL,
  `Payment_Amount` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `booking_history`
--

INSERT INTO `booking_history` (`b_history_id`, `userId`, `bookingId`, `paymentId`, `User_Name`, `Phone_No`, `Vehicle_No`, `StartTime`, `EndTime`, `SlotName`, `Payment_Method`, `Payment_Amount`) VALUES
(8, 1, 28, 26, 'saadsema', '2147483647', 'GJ14AS3241', '2025-08-20 12:59:00', '2025-08-23 23:38:00', 'S3', 'Pay via UPI', 4065.00),
(9, 1, 28, 26, 'saadsema', '2147483647', 'GJ14AS3241', '2025-08-20 12:59:00', '2025-08-23 23:38:00', 'S3', 'Pay via UPI', 4065.00),
(10, 5, 28, 26, 'saadsema', '2147483647', 'GJ14AS3241', '2025-08-20 12:59:00', '2025-08-23 23:38:00', 'S3', 'Pay via UPI', 4065.00),
(11, 1, 28, 26, 'saadsema', '2147483647', 'GJ14AS3241', '2025-08-20 12:59:00', '2025-08-23 23:38:00', 'S3', 'Pay via UPI', 4065.00),
(12, 1, 28, 26, 'saadsema', '2147483647', 'GJ14AS3241', '2025-08-20 12:59:00', '2025-08-23 23:38:00', 'S3', 'Pay via UPI', 4065.00),
(13, 1, 28, 26, 'saadsema', '2147483647', 'GJ14AS3241', '2025-08-20 12:59:00', '2025-08-23 23:38:00', 'S3', 'Pay via UPI', 4065.00),
(14, 1, 28, 26, 'saadsema', '2147483647', 'GJ14AS3241', '2025-08-20 12:59:00', '2025-08-23 23:38:00', 'S3', 'Pay via UPI', 4065.00),
(15, 1, 28, 26, 'saadsema', '2147483647', 'GJ14AS3241', '2025-08-20 12:59:00', '2025-08-23 23:38:00', 'S3', 'Pay via UPI', 4065.00);

-- --------------------------------------------------------

--
-- Table structure for table `booking_table`
--

CREATE TABLE `booking_table` (
  `BookingID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `SlotID` int(11) NOT NULL,
  `VehicleNo` varchar(15) DEFAULT NULL,
  `StartTime` datetime NOT NULL,
  `EndTime` datetime NOT NULL,
  `P_Status` enum('Pending','Confirmed','Cancelled','Completed') DEFAULT 'Pending',
  `CreatedAt` datetime DEFAULT NULL,
  `PaymentId` int(11) DEFAULT NULL,
  `UserName` varchar(25) DEFAULT NULL,
  `SlotName` varchar(25) DEFAULT NULL,
  `OTP` int(6) DEFAULT NULL,
  `PhoneNo` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `booking_table`
--

INSERT INTO `booking_table` (`BookingID`, `UserID`, `SlotID`, `VehicleNo`, `StartTime`, `EndTime`, `P_Status`, `CreatedAt`, `PaymentId`, `UserName`, `SlotName`, `OTP`, `PhoneNo`) VALUES
(48, 5, 5, 'GJ15AJ1827', '2025-09-20 10:00:00', '2025-09-20 12:00:00', 'Pending', '2025-09-19 10:00:17', 46, 'saad sema', 'S5', 330572, '+919601253045'),
(49, 5, 17, 'HH14AF3564', '2025-09-20 15:53:00', '2025-09-20 17:53:00', 'Pending', '2025-09-19 15:53:21', 47, 'saad sema', 'S1', 674855, '+919601253045'),
(50, 5, 5, '9637282272', '2025-09-29 08:21:00', '2025-09-20 13:16:00', 'Confirmed', '2025-09-20 11:22:05', 48, 'saad sema', 'S5', 270740, '+919601253045'),
(51, 7, 8, '123456', '2025-11-06 04:35:00', '2025-11-06 06:35:00', 'Pending', '2025-11-05 16:39:34', 49, 'Zeenat Shaikh', 'S8', 256985, '+919142154554'),
(52, 5, 18, 'GH15AJ3627', '2025-11-07 15:06:00', '2025-11-07 17:06:00', 'Pending', '2025-11-07 13:07:44', 50, 'saad sema', 'S2', 755711, '+911234567890'),
(53, 5, 18, 'GH15AJ3627', '2025-11-07 15:06:00', '2025-11-07 17:06:00', 'Pending', '2025-11-07 13:07:45', 51, 'saad sema', 'S2', 755711, '+911234567890');

-- --------------------------------------------------------

--
-- Table structure for table `parking_slot`
--

CREATE TABLE `parking_slot` (
  `SlotID` int(11) NOT NULL,
  `Location` varchar(100) NOT NULL,
  `SlotNumber` varchar(20) NOT NULL,
  `RatePerHour` decimal(6,2) NOT NULL,
  `ExtraCharge` int(15) DEFAULT 50
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `parking_slot`
--

INSERT INTO `parking_slot` (`SlotID`, `Location`, `SlotNumber`, `RatePerHour`, `ExtraCharge`) VALUES
(5, 'Block B', 'S5', 50.00, 60),
(8, 'Block C', 'S8', 50.00, 60),
(17, 'Block A', 'S1', 50.00, 60),
(18, 'Block A', 'S2', 50.00, 60),
(19, 'Block A', 'S3', 50.00, 80),
(20, 'Block A', 'S4', 50.00, 100),
(21, 'Block B', 'S6', 50.00, 70),
(22, 'Block B', 'S7', 50.00, 80),
(23, 'Block C', 'S9', 50.00, 70),
(24, 'Block C', 'S10', 50.00, 80),
(25, 'Block D', 'S11', 50.00, 60),
(26, 'Block D', 'S12', 50.00, 90),
(27, 'Block D', 'S13', 50.00, 100),
(28, 'Block E', 'S14', 50.00, 60),
(29, 'Block E', 'S15', 50.00, 70),
(30, 'Block E', 'S16', 50.00, 80),
(31, 'Block F', 'S17', 50.00, 100),
(32, 'Block F', 'S18', 50.00, 110),
(33, 'Block F', 'S19', 50.00, 120),
(34, 'Block G', 'S20', 50.00, 60),
(35, 'Block G', 'S21', 50.00, 70),
(36, 'Block G', 'S22', 50.00, 90),
(37, 'Block H', 'S23', 50.00, 100),
(38, 'Block H', 'S24', 50.00, 110),
(39, 'Block H', 'S25', 50.00, 120),
(40, 'Block I', 'S26', 50.00, 60),
(41, 'Block I', 'S27', 50.00, 80),
(42, 'Block I', 'S28', 50.00, 90),
(43, 'Block J', 'S29', 50.00, 100),
(44, 'Block J', 'S30', 50.00, 110);

-- --------------------------------------------------------

--
-- Table structure for table `payment_table`
--

CREATE TABLE `payment_table` (
  `PaymentID` int(11) NOT NULL,
  `BookingID` int(11) DEFAULT NULL,
  `UserID` int(11) NOT NULL,
  `Amount` decimal(8,2) NOT NULL,
  `PaymentTime` datetime DEFAULT NULL,
  `PaymentMethod` varchar(30) DEFAULT NULL,
  `Status` enum('Success','Failed','Pending') DEFAULT 'Success',
  `ExtraCharge` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payment_table`
--

INSERT INTO `payment_table` (`PaymentID`, `BookingID`, `UserID`, `Amount`, `PaymentTime`, `PaymentMethod`, `Status`, `ExtraCharge`) VALUES
(18, 20, 1, 20.00, '2025-08-13 13:53:15', 'Pay via UPI', 'Success', 0),
(19, 21, 1, 25.00, '2025-08-13 14:49:26', 'Pay via UPI', 'Success', 0),
(20, 22, 1, 20.00, '2025-08-13 15:29:44', 'Pay via UPI', 'Success', 0),
(21, 23, 1, 60.00, '2025-08-13 15:33:48', 'Pay via UPI', 'Success', 0),
(22, 24, 1, 15.00, '2025-08-16 10:54:04', 'Pay via UPI', 'Success', 0),
(23, 25, 1, 15.00, '2025-08-16 11:48:30', 'Pay via UPI', 'Success', 0),
(24, 26, 1, 15.00, '2025-08-16 12:23:41', 'Pay via UPI', 'Success', NULL),
(25, 27, 1, 10375.00, '2025-08-16 12:39:25', 'Pay via UPI', 'Success', 450),
(26, 28, 1, 4065.00, '2025-08-19 15:37:54', 'Pay via UPI', 'Pending', 100),
(27, 29, 1, 25.00, '2025-08-23 21:37:18', 'Pay via UPI', 'Success', 0),
(28, 30, 1, 30.00, '2025-08-28 11:37:39', 'Pay via UPI', 'Success', NULL),
(29, 31, 1, 30.00, '2025-08-30 11:39:57', 'Pay via UPI', 'Success', NULL),
(30, 32, 1, 30.00, '2025-08-30 13:12:34', 'Pay via UPI', 'Success', NULL),
(31, 33, 5, 90.00, '2025-09-03 12:59:30', 'Pay via UPI', 'Pending', NULL),
(32, 34, 5, 30.00, '2025-09-14 10:59:39', 'Pay via UPI', 'Pending', 60),
(33, 35, 5, 90.00, '2025-09-15 21:58:12', 'Pay via UPI', 'Success', NULL),
(34, 36, 5, 90.00, '2025-09-15 21:58:12', 'Pay via UPI', 'Success', NULL),
(35, 38, 5, 90.00, '2025-09-15 21:58:12', 'Pay via UPI', 'Success', NULL),
(36, 37, 5, 90.00, '2025-09-15 21:58:12', 'Pay via UPI', 'Success', NULL),
(37, 39, 5, 90.00, '2025-09-15 21:58:13', 'Pay via UPI', 'Success', NULL),
(38, 40, 5, 90.00, '2025-09-15 21:58:13', 'Pay via UPI', 'Success', NULL),
(39, 41, 5, 90.00, '2025-09-15 21:58:13', 'Pay via UPI', 'Success', NULL),
(40, 42, 5, 90.00, '2025-09-15 21:58:13', 'Pay via UPI', 'Success', NULL),
(41, 43, 5, 90.00, '2025-09-15 21:58:14', 'Pay via UPI', 'Success', NULL),
(42, 44, 5, 90.00, '2025-09-15 21:58:14', 'Pay via UPI', 'Success', NULL),
(43, 45, 5, 30.00, '2025-09-15 21:59:20', 'Pay via UPI', 'Success', NULL),
(44, 46, 6, 210.00, '2025-09-16 11:22:01', 'Pay via UPI', 'Success', NULL),
(45, 47, 6, 60.00, '2025-09-16 14:52:29', 'Pay via UPI', 'Success', NULL),
(46, 48, 5, 50.00, '2025-09-19 10:00:17', 'Pay via UPI', 'Pending', NULL),
(47, 49, 5, 50.00, '2025-09-19 15:53:21', 'Pay via UPI', 'Pending', 60),
(48, 50, 5, 50.00, '2025-09-20 11:22:05', 'Pay via UPI', 'Pending', 60),
(49, 51, 7, 110.00, '2025-11-05 16:39:34', 'Pay via UPI', 'Success', NULL),
(50, 52, 5, 100.00, '2025-11-07 13:07:44', 'Pay via UPI', 'Success', NULL),
(51, 53, 5, 100.00, '2025-11-07 13:07:45', 'Pay via UPI', 'Success', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_table`
--

CREATE TABLE `user_table` (
  `UserID` int(11) NOT NULL,
  `Role` enum('User','Manager') NOT NULL DEFAULT 'User',
  `Name` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Password` varchar(30) NOT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  `Status` enum('Active','Blocked') DEFAULT 'Active',
  `RegistrationDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_table`
--

INSERT INTO `user_table` (`UserID`, `Role`, `Name`, `Email`, `Password`, `Phone`, `Status`, `RegistrationDate`) VALUES
(1, 'User', 'John Doe', 'john@example.com', '', '1234567890', 'Blocked', '2025-08-04 15:34:47'),
(2, 'Manager', 'manager01', 'manager01@gmail.com', '1245268', '9601234045', 'Active', '0000-00-00 00:00:00'),
(4, 'Manager', 'manager02', 'emailManager02@gmail.com', '6582155', '9601235045', 'Active', '2025-09-01 12:22:40'),
(5, 'User', 'saad sema', 'saad@gmail.com', 'Saad1', '9601235045', 'Active', '2025-09-03 12:04:32'),
(6, 'User', 'Khushi Memon', 'khushi@gmail.com', 'khushi', '8140617860', 'Active', '2025-09-16 11:18:49'),
(7, 'User', 'Zeenat Shaikh', 'zeenat@gmail.com', '12345', '9142154554', 'Active', '2025-11-05 16:37:30');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking_history`
--
ALTER TABLE `booking_history`
  ADD PRIMARY KEY (`b_history_id`);

--
-- Indexes for table `booking_table`
--
ALTER TABLE `booking_table`
  ADD PRIMARY KEY (`BookingID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `SlotID` (`SlotID`),
  ADD KEY `booking_table_ibfk_3` (`PaymentId`);

--
-- Indexes for table `parking_slot`
--
ALTER TABLE `parking_slot`
  ADD PRIMARY KEY (`SlotID`);

--
-- Indexes for table `payment_table`
--
ALTER TABLE `payment_table`
  ADD PRIMARY KEY (`PaymentID`),
  ADD KEY `BookingID` (`BookingID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `user_table`
--
ALTER TABLE `user_table`
  ADD PRIMARY KEY (`UserID`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `booking_history`
--
ALTER TABLE `booking_history`
  MODIFY `b_history_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `booking_table`
--
ALTER TABLE `booking_table`
  MODIFY `BookingID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT for table `parking_slot`
--
ALTER TABLE `parking_slot`
  MODIFY `SlotID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `payment_table`
--
ALTER TABLE `payment_table`
  MODIFY `PaymentID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT for table `user_table`
--
ALTER TABLE `user_table`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking_table`
--
ALTER TABLE `booking_table`
  ADD CONSTRAINT `booking_table_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user_table` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `booking_table_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `user_table` (`UserID`),
  ADD CONSTRAINT `booking_table_ibfk_3` FOREIGN KEY (`PaymentId`) REFERENCES `payment_table` (`PaymentID`),
  ADD CONSTRAINT `booking_table_ibfk_4` FOREIGN KEY (`SlotID`) REFERENCES `parking_slot` (`SlotID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
