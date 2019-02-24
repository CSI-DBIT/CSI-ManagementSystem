-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Feb 24, 2019 at 04:11 PM
-- Server version: 5.5.62
-- PHP Version: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `csimanagementsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `minute`
--

DROP TABLE IF EXISTS `minute`;
CREATE TABLE IF NOT EXISTS `minute` (
  `id` varchar(10) NOT NULL,
  `agenda` varchar(25) NOT NULL,
  `da_te` date NOT NULL,
  `ti_me` time NOT NULL,
  `creator` varchar(25) NOT NULL,
  `minute` blob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `minute`
--

INSERT INTO `minute` (`id`, `agenda`, `da_te`, `ti_me`, `creator`, `minute`) VALUES
('2017134979', 'GOC', '2019-02-24', '21:15:15', 'saurabh yadav', 0x474f432077696c6c20626520636f6e647563746564206f6e20367468206f63746f6265722032303139),
('2017134980', 'csi', '2019-02-24', '21:14:23', 'mushira shaikh', 0x435349206d656574696e6720776f756c642062652068656c64206f6e20387468206a616e2032303139);

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
CREATE TABLE IF NOT EXISTS `profile` (
  `id` varchar(10) NOT NULL,
  `password` varchar(25) NOT NULL,
  `role` varchar(15) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `email` varchar(35) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `year` varchar(10) DEFAULT NULL,
  `branch` varchar(10) DEFAULT NULL,
  `rollno` int(11) DEFAULT NULL,
  `batch` varchar(5) DEFAULT NULL,
  `membership_left` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`id`, `password`, `role`, `name`, `email`, `phone`, `year`, `branch`, `rollno`, `batch`, `membership_left`) VALUES
('2017134979', '250998', 'student', 'saurabh yadav', 'survir44@gmail.com', '9640278397', 'SE', 'IT', 63, 'D', 1),
('2017134980', 'm1u2s3h4i5', 'technical head', 'mushira shaikh', 'mushira.shaikh1999@gmail.com', '8976099903', 'SE', 'IT', 54, 'c', 1),
('2017134986', 'qwerty', 'technical head', 'sanket deshmukh', 'meetsanket24@gmail.com', '9702717188', 'SE', 'IT', 7, 'A', 1),
('2017135040', 'asdfg', 'chairperson', 'viraj tandel', 'virajtandel72@gmail.com', '8976853951', 'SE', 'IT', 61, 'D', 1);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
