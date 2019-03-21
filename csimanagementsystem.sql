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
-- Table structure for table `final_list`
--

DROP TABLE IF EXISTS `final_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `final_list` (
  `RID` varchar(20) NOT NULL,
  `stud_id` varchar(10) NOT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `s1` int(11) DEFAULT NULL,
  `s2` int(11) DEFAULT NULL,
  `s3` int(11) DEFAULT NULL,
  `s4` int(11) DEFAULT NULL,
  `s5` int(11) DEFAULT NULL,
  `s6` int(11) DEFAULT NULL,
  `s7` int(11) DEFAULT NULL,
  `reason` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`RID`),
  KEY `stud_id` (`stud_id`),
  CONSTRAINT `final_list_ibfk_1` FOREIGN KEY (`stud_id`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `final_list`
--

LOCK TABLES `final_list` WRITE;
/*!40000 ALTER TABLE `final_list` DISABLE KEYS */;
INSERT INTO `final_list` VALUES ('123dfs234','2017134979','saurabh yadav','2019-03-07',1,0,0,0,0,0,1,NULL),('8u36rg4bcvk','2017134986','sanket deshmukh','2019-03-17',0,1,1,0,0,0,0,NULL),('gpzwxy8tr7v','2017134986','sanket deshmukh','2019-03-13',0,1,1,0,0,0,0,NULL),('ickvq7bsbhb','2017134986','sanket deshmukh','2019-03-07',1,1,0,1,1,0,1,NULL),('mt7dcaqqqde','2017134980','mushira shaikh','2019-03-13',0,1,1,0,0,0,0,NULL),('qrpb0af0it','2017134979','saurabh yadav','2019-07-03',1,1,0,1,1,0,1,'Just wasting time john plz give attendance'),('tlm2a4dqxne','2017135040','viraj tandel','2019-09-06',1,1,1,1,1,1,1,NULL);
/*!40000 ALTER TABLE `final_list` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `profile` VALUES ('2016','calden','member','Calden','c@g.com','49684315','FE','IT',61,'A',2),('2016134613','calden','member','Calden','c@g.com','123456789','TE','IT',61,'B',1),('20168964','SkXIbJdqhE','member','Calden','caldenrodrigues1202@gmail.com','49684315','FE','IT',61,'A',2),('2017134979','250998','student','saurabh yadav','survir44@gmail.com','9640278397','SE','IT',63,'D',1),('2017134980','m1u2s3h4i5','technical head','mushira shaikh','mushira.shaikh1999@gmail.com','8976099903','SE','IT',54,'c',1),('2017134986','qwerty','technical head','sanket deshmukh','meetsanket24@gmail.com','9702717188','SE','COMPS',7,'A',1),('2017135040','asdfg','chairperson','viraj tandel','virajtandel72@gmail.com','8976853951','SE','IT',61,'D',1),('2443','calden','member','fhewok','bh@hb','5641','SE','IT',75,'B',1),('454','calden','member','','','','','',55,'',1),('612','calden','member',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('666','calden','member','fhewok','bh@hb','5641','SE','IT',75,'B',3),('ewlfjh','calden','member','lbfklbrjskbfl','bfjkb@i','85','SE','COMPS',46,'B',2);
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request` (
  `RID` varchar(20) NOT NULL,
  `stud_id` varchar(10) NOT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `s1` int(11) DEFAULT NULL,
  `s2` int(11) DEFAULT NULL,
  `s3` int(11) DEFAULT NULL,
  `s4` int(11) DEFAULT NULL,
  `s5` int(11) DEFAULT NULL,
  `s6` int(11) DEFAULT NULL,
  `s7` int(11) DEFAULT NULL,
  `reason` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`RID`),
  KEY `fk1` (`stud_id`),
  CONSTRAINT `fk1` FOREIGN KEY (`stud_id`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request`
--

LOCK TABLES `request` WRITE;
/*!40000 ALTER TABLE `request` DISABLE KEYS */;
INSERT INTO `request` VALUES ('nvucua4ojss','2017134986','sanket deshmukh','2019-03-07',1,1,0,1,1,0,1,'Just wasting time, john plz give attendance');
/*!40000 ALTER TABLE `request` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-21 18:12:56
