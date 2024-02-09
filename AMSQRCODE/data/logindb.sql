-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 27, 2023 at 03:26 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `logindb`
--

-- --------------------------------------------------------

--
-- Table structure for table `admintable`
--

CREATE TABLE `admintable` (
  `firstname` varchar(250) NOT NULL,
  `lastname` varchar(250) NOT NULL,
  `username` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admintable`
--

INSERT INTO `admintable` (`firstname`, `lastname`, `username`, `password`) VALUES
('Engineer1', 'Dummy', 'Eg1Dum1', 'Dummy1'),
('Engineer1', 'Dummy', 'Eg1Dum2', 'Dummy2'),
('Engineer1', 'Dummy', 'Eg1Dum3', 'Dummy3'),
('Engineer2', 'Dummy', 'Eg2Dum1', 'Dummy1'),
('Engineer2', 'Dummy', 'Eg2Dum2 ', 'Dummy2'),
('Engineer2', 'Dummy', 'Eg2Dum3', 'Dummy3'),
('Engineer3', 'Dummy', 'Eg3Dum1', 'Dummy1'),
('Engineer3', 'Dummy', 'Eg3Dum2', 'Dummy2'),
('Engineer3', 'Dummy', 'Eg3Dum3', 'Dummy3'),
('Engineer4', 'Duimmy', 'Eg4Dum2', 'Dummy2'),
('Engineer4', 'Dummy', 'Eg4Dum1', 'Dummy1'),
('Engineer4', 'Dummy', 'Eg4Dum3', 'Dummy3'),
('Engineer5', 'Dummy', 'Eg5Dum1', 'Dummy1'),
('Engineer5', 'Dummy', 'Eg5Dum2', 'Dummy2'),
('Engineer5', 'Dummy', 'Eg5Dum3', 'Dummy3'),
('Prince Bert', 'Corbeta', 'admin', 'admin123');

-- --------------------------------------------------------

--
-- Table structure for table `attendancetimetable`
--

CREATE TABLE `attendancetimetable` (
  `Num` int(11) NOT NULL,
  `IDNumber` int(250) NOT NULL,
  `LogDate` varchar(250) NOT NULL,
  `Time` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `studenttable`
--

CREATE TABLE `studenttable` (
  `IDNumber` int(50) NOT NULL,
  `FirstName` varchar(250) NOT NULL,
  `LastName` varchar(250) NOT NULL,
  `Course` varchar(250) NOT NULL,
  `Section` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `studenttable`
--

INSERT INTO `studenttable` (`IDNumber`, `FirstName`, `LastName`, `Course`, `Section`) VALUES
(202300001, 'Alyssa', 'Cabahug', 'BSCS', '3A'),
(202300002, 'Jimboy', 'Manliguez', 'BSCS', '3A'),
(202300003, 'Prince', 'Corbeta', 'BSCS', '3A'),
(202300004, 'Reyjewel', 'Tampus', 'BSCS', '3A'),
(202300005, 'Rieliana', 'Villamor', 'BSCS', '3A'),
(202300006, 'Classmate1', 'Classmate1', 'BSCS', '3A'),
(202300007, 'Classmate2', 'Classmate2', 'BSCS', '3A');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `attendancetimetable`
--
ALTER TABLE `attendancetimetable`
  ADD PRIMARY KEY (`Num`);

--
-- Indexes for table `studenttable`
--
ALTER TABLE `studenttable`
  ADD PRIMARY KEY (`IDNumber`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `attendancetimetable`
--
ALTER TABLE `attendancetimetable`
  MODIFY `Num` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `studenttable`
--
ALTER TABLE `studenttable`
  MODIFY `IDNumber` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=202300009;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
