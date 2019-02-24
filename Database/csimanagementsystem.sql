-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Feb 24, 2019 at 12:56 PM
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
('2017134979', 'csi', '2019-02-23', '20:50:39', 'saurabh', 0x6d656574696e67);

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
CREATE TABLE IF NOT EXISTS `profile` (
  `id` varchar(10) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(35) DEFAULT NULL,
  `phone` int(11) DEFAULT NULL,
  `year` varchar(10) DEFAULT NULL,
  `branch` varchar(10) DEFAULT NULL,
  `rollno` int(11) DEFAULT NULL,
  `batch` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`id`, `name`, `email`, `phone`, `year`, `branch`, `rollno`, `batch`) VALUES
('2017134979', 'saurabh', 'survir44@gmail.com', 12345, 'SE', 'IT', 63, 'D');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` varchar(10) NOT NULL,
  `name` varchar(25) NOT NULL,
  `role` varchar(15) NOT NULL,
  `password` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `role`, `password`) VALUES
('2017134979', 'saurabh', 'student', 'abcd');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
