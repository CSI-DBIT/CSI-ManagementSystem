-- MySQL dump 10.13  Distrib 5.7.29, for Linux (x86_64)
--
-- Host: localhost    Database: CsiManagementSystem
-- ------------------------------------------------------
-- Server version	5.7.29-0ubuntu0.16.04.1

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
-- Table structure for table `creative`
--

DROP TABLE IF EXISTS `creative`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creative` (
  `eid` varchar(5) NOT NULL,
  PRIMARY KEY (`eid`),
  CONSTRAINT `eid` FOREIGN KEY (`eid`) REFERENCES `events` (`eid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creative`
--

LOCK TABLES `creative` WRITE;
/*!40000 ALTER TABLE `creative` DISABLE KEYS */;
/*!40000 ALTER TABLE `creative` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `events` (
  `eid` varchar(5) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `theme` varchar(100) DEFAULT NULL,
  `description` blob,
  `event_date` date DEFAULT NULL,
  `speaker` varchar(100) DEFAULT NULL,
  `M_agenda` varchar(100) DEFAULT NULL,
  `M_date` date DEFAULT NULL,
  `creative_budget` int(11) DEFAULT NULL,
  `publicity_budget` int(11) DEFAULT NULL,
  `guest_budget` int(11) DEFAULT NULL,
  `others_budget` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
  `total_budget` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `p_date` date NOT NULL,
  `comment` blob,
  `venue` varchar(20) DEFAULT NULL,
  `reg_fee_c` int(11) DEFAULT NULL,
  `prize` int(11) DEFAULT NULL,
  `reg_fee_nc` int(11) DEFAULT NULL,
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES ('1QH7E','testing','testing',_binary 'test','2019-10-01','test','CSI Trivia','2019-10-20',25,25,25,'{}',NULL,2,'2019-10-21',_binary 'Done','test',NULL,123,NULL),('1WMpK','GOC','coding',_binary 'Hello World','2019-09-25','Venkat Raman','yup','2019-02-24',50,50,25,'{\"message\":\"hello\"}',NULL,2,'2019-10-21',NULL,'IT LAB-2',50,1200,NULL),('Ejjw8','Joomla Workshop','none',_binary 'This workshop will help students to create their own websites using joomla in less amount of time. Also best students from this workshop will get chance of internship from college','2019-08-30','Sarah Solkar, Sanket Deshmukh','Joomla','2019-10-20',20,0,0,'{}',NULL,2,'2019-10-21','','IT Lab 1, IT Lab 4',NULL,0,NULL),('tyJQF','swat the bug','debugging',_binary 'Error Solving competition','2020-03-02','Saurabh','PhotoBattle','2019-10-21',20,20,20,'{}',NULL,2,'2020-03-02',_binary 'Ok','it lab-2',NULL,10000,NULL),('XIRyZ','CPL','Coding',_binary 'CPL(Coding Premier League) is coding Competition. In this competition coder as well as non coders will get chance to show their skills by participating as coder and managers.','2019-09-18','None','CPL','2019-10-20',30,0,0,'{}',NULL,0,'2019-10-21',NULL,'IT Lab 4',NULL,1200,NULL),('XTHIY','data hunt',NULL,_binary 'khelo to sahi','2020-03-01','SE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,-2,'2020-03-02',_binary 'Questions are not proper','DBIT',NULL,10000,NULL);
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

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
  `year` varchar(5) DEFAULT NULL,
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
INSERT INTO `final_list` VALUES ('05ek5oyagnx8','2017134986','Sanket Deshmukh','2019-07-28',0,0,0,1,1,1,0,'Reason',NULL),('1a0l64h8f9d','2017134986','Sanket Deshmukh','2019-08-25',1,1,1,0,0,0,0,'publicity',NULL),('2g9r92wcn4n','2017134986','Sanket Deshmukh','2019-07-01',1,1,0,0,1,1,1,'just doing timepass',NULL),('7tga2a0q004','2017135040','viraj tandel','2019-10-05',1,1,1,1,1,1,1,'tandel','SE'),('8j5eps4kp3p','2017134979','Saurabh Yadav','2019-10-08',1,1,1,1,1,1,1,'maza aare','TE'),('8uu1pax89mb','2017134986','Sanket Deshmukh','2019-10-02',0,0,1,0,1,0,0,'vjcvkv','SE'),('9j13hqat8uo','2017135040','viraj tandel','2019-10-15',1,1,1,0,0,0,0,'sss','SE'),('9u4mqvv2wpc','2017134986','Sanket Deshmukh','2019-07-22',0,0,0,0,0,1,0,'Enter Reason',NULL),('alzucpyynno','2017135040','viraj tandel','2019-09-12',0,0,1,0,0,1,0,'ccihicf7icic','SE'),('cau5gsqfh3a','2017134986','Sanket Deshmukh','2019-10-01',1,0,0,1,0,0,1,'ucuccv',NULL),('d8g2ua4a6lb','2017134980','Mushira shaikh','2019-10-02',0,0,1,0,1,1,0,'jccick','TE'),('f7g87ha1wgl','2017135040','viraj tandel','2019-09-01',1,0,0,0,0,0,1,'Just Checking',NULL),('fpz8zv403sv','2017135040','viraj tandel','2019-09-04',1,0,0,1,0,1,1,'9govkv','SE'),('kef3go5qps','2017134980','Mushira shaikh','2019-10-01',0,0,0,0,1,0,1,'8ggi','TE'),('mudu9efszep','2017134986','Sanket Deshmukh','2019-10-05',0,1,1,1,1,0,0,'icciciv','SE'),('qtmti4ly1ah','2017134980','Mushira shaikh','2019-08-15',1,1,1,1,1,1,1,'okay',NULL),('sx6i5gouvcb','2017134986','Sanket Deshmukh','2019-07-03',1,1,0,0,0,1,1,'nothing',NULL);
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
  `minute` blob,
  `work` blob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `minute`
--

LOCK TABLES `minute` WRITE;
/*!40000 ALTER TABLE `minute` DISABLE KEYS */;
INSERT INTO `minute` VALUES ('2017134986','Coding Walkthrough','2019-10-20','15:16:42','Sanket Deshmukh',_binary 'Discussion of Itinerary',_binary '{\"minutes\":[{\"task\":\"Codechef\",\"person\":\"Saurabh Yadav\"},{\"task\":\"Hackerearth\",\"person\":\"Ishmeet Kaur\"}]}'),('2017134986','Joomla','2019-10-20','15:30:23','Sanket Deshmukh',_binary '1. Basic Intro of Joomla\n2. Overview of Joomla components, modules, extensions and plugins\n3. Help students to create Basic Template using major components of joomla\n4. 2 weeks time period for students to submit prototype of DBIT website(to get internship)',_binary '{\"minutes\":[{\"task\":\"Introduction\",\"person\":\"Mushira Shaikh\"},{\"task\":\"Basic Template\",\"person\":\"Sanket Deshmukh\"}]}'),('2017134979','CPL','2019-10-20','16:06:32','Saurabh Yadav',_binary 'CPL(Coding Premier league) is team based coding competition,wherein team of(3 coders +1Manager) is formed through Auction.The challenge is of 2hr splitted into 30 mins of each round.',_binary '{\"minutes\":[{\"task\":\"Technical Head\",\"person\":\"Sanket Deshmukh\"},{\"task\":\"Question Setting\",\"person\":\"Saurabh Yadav\"},{\"task\":\"Question Uploading\",\"person\":\"Sanket Deshmukh\"},{\"task\":\"volunteer\",\"person\":\"Mushira Shaikh\"},{\"task\":\"Auction\",\"person\":\"Viraj Tandel\"}]}'),('2017134980','GOC','2019-10-20','16:39:07','Mushira shaikh',_binary 'Task Distribution',_binary '{\"minutes\":[{\"task\":\"Flex\",\"person\":\"Afif Shaikh\"},{\"task\":\"posters\",\"person\":\"Sanika Bhagwat\"},{\"task\":\"Out college publicity\",\"person\":\"Gaurav Jain\"},{\"task\":\"Out college publicity\",\"person\":\"Jevina Verghese\"},{\"task\":\"In college Publicity\",\"person\":\"Mushira Shaikh\"}]}'),('2017134980','Connectivity ','2019-10-20','16:46:50','Mushira shaikh',_binary 'This will help students to create Basic Android app/website and Backend API using Nodejs & Mysql. Also students will learn how to send data over internet to server.',_binary '{\"minutes\":[{\"task\":\"Android\",\"person\":\"Sanket Deshmukh\"},{\"task\":\"Web\",\"person\":\"Viraj Tandel\"},{\"task\":\"Nodejs\",\"person\":\"Saurabh Yadav\"}]}'),('2017134986','Code Combat','2019-10-20','16:53:42','Sanket Deshmukh',_binary 'This is Intercollege Coding Competition with different set of rules. This will help users to code with unknown coders in team.',_binary '{\"minutes\":[{\"task\":\"Question Setting\",\"person\":\"Viraj Tandel\"},{\"task\":\"Lab Arrangement\",\"person\":\"Sanket Deshmukh\"},{\"task\":\"publicity\",\"person\":\"Gaurav Jain\"},{\"task\":\"poster\",\"person\":\"Sanika Bhagwat\"}]}'),('2017134980','Mumbai Hackathon','2019-10-20','16:56:46','Mushira shaikh','',_binary '{\"minutes\":[{\"task\":\"Volunteer\",\"person\":\"Ishmeet Kaur\"},{\"task\":\"Volunteer\",\"person\":\"Viraj Tandel\"},{\"task\":\"Lab arrangement\",\"person\":\"Sanket Deshmukh\"},{\"task\":\"publicity\",\"person\":\"Jevina Verghese\"},{\"task\":\"volunteer\",\"person\":\"Mushira Shaikh\"}]}'),('2017134980','Coderank','2019-10-20','16:59:25','Mushira shaikh',_binary 'Coderank will help college to keep track of performance of Students. Also the students winning and participating consistently in many events will get Exciting prizes.',_binary '{\"minutes\":[{\"task\":\"Record keeper\",\"person\":\"Ishmeet Kaur\"}]}'),('2017134980','Easycode 1','2019-10-20','17:01:23','Mushira shaikh',_binary 'Easycode is the Coding Competition for SE IT to track the improvement of performance of student in Competitive coding.',_binary '{\"minutes\":[{\"task\":\"Question Setting\",\"person\":\"Sanket Deshmukh\"},{\"task\":\"Question Setting\",\"person\":\"Saurabh Yadav\"},{\"task\":\"Question Setting\",\"person\":\"Mushira Shaikh\"}]}'),('2017134986','CSI Trivia','2019-10-20','17:04:37','Sanket Deshmukh',_binary 'In this weekly coding question will be put up on notice boards. The name of first student to give the correct answer will be announced as winner.',_binary '{\"minutes\":[{\"task\":\"Question Setting\",\"person\":\"Viraj Tandel\"}]}'),('2018330014','PhotoBattle','2019-10-21','14:27:05','Afif Shaikh',_binary 'A Basic Walkthrough of Photoshop and Poster making competition',_binary '{\"minutes\":[{\"task\":\"Volunteer\",\"person\":\"Mushira Shaikh\"}]}');
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
  `dp` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
INSERT INTO `profile` VALUES ('2016134613','xGXg8IE7Tb','member','Calden Rodrigues','caldenrodrigues1202@gmail.com','9820240340','BE','IT',61,'A',1,'http://159.65.144.246:8082/images/mushira.png'),('2017134979','250998','PR Head','Saurabh Yadav','survir44@gmail.com','9640278397','TE','IT',74,'D',1,'http://159.65.144.246:8090/images/saurabh.png'),('2017134980','abcdef','Chairperson','Mushira shaikh','mushira.shaikh1999@gmail.com','8976099903','TE','IT',54,'C',1,'http://159.65.144.246:8090/images/mushira.png'),('2017134986','qwerty','Technical Head','Sanket Deshmukh','meetsanket24@gmail.com','9702717188','SE','IT',75,'D',1,'http://159.65.144.246:8090/images/sanket.png'),('2017135030','ishmeet','SBC','Ishmeet Dua','ishmeet245@gmail.com','9967937648','TE','IT',14,'D',1,NULL),('2017135040','asdfg','HOD','viraj tandel','virajtandel72@gmail.com','8976853951','SE','IT',61,'D',1,'http://159.65.144.246:8090/images/viraj.png'),('2018330014','12345','Creative Head','Afif Shaikh','afifshaikh48@gmail.com','9769320992','TE','IT',79,'D',1,'http://159.65.144.246:8090/images/csi_afif.png'),('999','0000','Admin','Admin',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL);
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
  `year` varchar(5) DEFAULT NULL,
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
INSERT INTO `request` VALUES ('i5aixq8c6w','2017134986','Sanket Deshmukh','2019-11-08',1,0,1,0,1,0,0,'publicity of GOC ','SE'),('i9rez57solk','999','Admin','2020-03-18',0,0,0,1,1,0,0,'Pub',NULL),('kef3go5qps','2017134980','Mushira shaikh','2019-10-01',0,0,0,0,1,0,1,'8ggi','TE');
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

-- Dump completed on 2020-03-27  7:09:51
