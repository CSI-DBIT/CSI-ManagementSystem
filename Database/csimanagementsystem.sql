-- MySQL dump 10.13  Distrib 5.7.25, for Linux (x86_64)
--
-- Host: localhost    Database: CsiManagementSystem
-- ------------------------------------------------------
-- Server version	5.7.25-0ubuntu0.16.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `minute`
--

DROP TABLE IF EXISTS `minute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `minute` (
  `id` varchar(10) NOT NULL,
  `agenda` varchar(25) NOT NULL,
  `da_te` date NOT NULL,
  `ti_me` time NOT NULL,
  `creator` varchar(25) NOT NULL,
  `minute` blob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `minute`
--

LOCK TABLES `minute` WRITE;
/*!40000 ALTER TABLE `minute` DISABLE KEYS */;
INSERT INTO `minute` VALUES ('2017134979','GOC','2019-02-24','21:15:15','saurabh yadav',_binary 'GOC will be conducted on 6th october 2019'),('2017134980','csi','2019-02-24','21:14:23','mushira shaikh',_binary 'CSI meeting would be held on 8th jan 2019'),('2017134979','Some meeting','2019-02-24','22:15:08','saurabh yadav',_binary 'Some points\nSome other points');
/*!40000 ALTER TABLE `minute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile` (
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
INSERT INTO `profile` VALUES ('2017134979','250998','student','saurabh yadav','survir44@gmail.com','9640278397','SE','IT',63,'D',1),('2017134980','m1u2s3h4i5','technical head','mushira shaikh','mushira.shaikh1999@gmail.com','8976099903','SE','IT',54,'c',1),('2017134986','qwerty','technical head','sanket deshmukh','meetsanket24@gmail.com','9702717188','SE','IT',7,'A',1),('2017135040','asdfg','chairperson','viraj tandel','virajtandel72@gmail.com','8976853951','SE','IT',61,'D',1);
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-24 22:28:15
