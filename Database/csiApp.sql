-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 13, 2020 at 01:17 AM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `csiApp`
--

-- --------------------------------------------------------

--
-- Table structure for table `creative`
--

CREATE TABLE `creative` (
  `eid` varchar(5) NOT NULL,
  `poster_link` varchar(100) DEFAULT NULL,
  `video_link` varchar(100) DEFAULT NULL,
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `creative`
--

INSERT INTO `creative` (`eid`, `poster_link`, `video_link`, `status`) VALUES
('LLyTE', 'http://tayyabali.in:9091/images/IMG-20200610-WA0012.jpg', 'http://tayyabali.in:9091/images/IMG-20200610-WA0012.jpg', 3);

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

CREATE TABLE `events` (
  `eid` varchar(5) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `theme` varchar(100) DEFAULT NULL,
  `description` blob DEFAULT NULL,
  `event_date` date DEFAULT NULL,
  `speaker` varchar(100) DEFAULT NULL,
  `M_agenda` varchar(100) DEFAULT NULL,
  `M_date` date DEFAULT NULL,
  `creative_budget` int(11) DEFAULT NULL,
  `publicity_budget` int(11) DEFAULT NULL,
  `guest_budget` int(11) DEFAULT NULL,
  `others_budget` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `total_budget` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT 0,
  `p_date` date NOT NULL,
  `comment` blob DEFAULT NULL,
  `venue` varchar(20) DEFAULT NULL,
  `reg_fee_c` int(11) DEFAULT NULL,
  `prize` varchar(50) DEFAULT NULL,
  `reg_fee_nc` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`eid`, `name`, `theme`, `description`, `event_date`, `speaker`, `M_agenda`, `M_date`, `creative_budget`, `publicity_budget`, `guest_budget`, `others_budget`, `total_budget`, `status`, `p_date`, `comment`, `venue`, `reg_fee_c`, `prize`, `reg_fee_nc`) VALUES
('BQOqj', 'Coding premier League', 'Coding', 0x41207465616d20626173656420636f64696e6720636f6d7065746974696f6e2e, '2020-06-30', 'Saurabh, Prabodh, Sanket, Mushira', 'PhotoBattle', '2019-10-21', 200, 200, 456, '{}', NULL, 1, '2020-06-08', 0x56657273696f6e20312e3020697320526561647921212121, 'DBIT', NULL, '1500', NULL),
('HK5bk', 'Mumbai Hackathon', 'Hackathon', 0x4d756d626169204861636b6174686f6e20697320416e6e75616c204f70656e20536f75726365204861636b6174686f6e206f7267616e697a656420627920464f535320556e6974656420616e6420446f6e20426f73636f20496e73746974757465206f6620546563686e6f6c6f6779202844424954292e20457665727920796561722c2077652077656c636f6d652073747564656e74732c20646576656c6f7065727320616e642064657369676e6572732066726f6d206163726f73732074686520636f756e74727920746f2063726561746520696e6372656469626c65206f70656e20736f757263652070726f6a65637473206174204d756d6261692773206c617267657374204f70656e20536f75726365204861636b6174686f6e2e, '2020-07-26', 'ERP-NEXT', 'PhotoBattle', '2019-10-21', 2000, 4000, 1000, '{}', NULL, 0, '2020-06-08', NULL, 'DBIT', NULL, '75000', NULL),
('LLyTE', 'Codechef Walkthrough', 'Presentation', 0x546f20496e74726f64756365205345277320776974682074686520636f64696e6720706c6174666f726d7320436f64656368656620616e64204861636b657265617274682e20546f206272696566207468656d2061626f75742074686520696d706f7274616e6365206f6620746865736520636f64696e6720706c6174666f726d732066726f6d20696e64757374727920706f696e74206f6620766965772e, '2019-08-01', 'Saurabh Yadav, Ishmeet Kaur, Sanket Deshmukh, Mushira Shaikh', 'kuch to hai!!!', '2019-06-13', 0, 100, 0, '{\"tea\":\"1000\"}', NULL, 2, '2020-06-13', NULL, 'SE IT Class', 0, '0', 0),
('WWncp', 'Game of Codes', 'Coding', 0x54686520436f6d7065746974696f6e2061696d7320746f20636865636b2070726f6772616d6d696e67c2a070726f626c656d20736f6c76696e67c2a026c2a06c6f676963616c207468696e6b696e67206361706162696c697479c2a06f66207468652073747564656e742e0a0a54686520436f6d7065746974696f6e2067697665732073747564656e747320746865206f70706f7274756e69747920746f206d656173757265206361706162696c697469657320616e6420746fc2a0636f6d70617265c2a07468656972c2a0636f64696e6720736b696c6c73c2a077697468206f74686572207465616d732066726f6d20766172696f757320696e737469747574696f6e732e0a0a54686520636f6d7065746974696f6e2077696c6c207465737420746865c2a0646562756767696e6720616e6420636f64696e67c2a06162696c697479206f6620636f6e74657374616e742e, '2020-08-15', 'NA', 'PhotoBattle', '2019-10-21', 2000, 4000, 0, '{\"lunch\":\"5000\"}', NULL, 0, '2020-06-08', NULL, 'At Home', NULL, '15000', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `final_list`
--

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
  `year` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `final_list`
--

INSERT INTO `final_list` (`RID`, `stud_id`, `Name`, `date`, `s1`, `s2`, `s3`, `s4`, `s5`, `s6`, `s7`, `reason`, `year`) VALUES
('05ek5oyagnx8', '2017134986', 'Sanket Deshmukh', '2019-07-28', 0, 0, 0, 1, 1, 1, 0, 'Reason', NULL),
('10m9x4zj1tu', '2017134986', 'Sanket Deshmukh', '2020-06-01', 1, 0, 1, 1, 0, 1, 0, 'sjshbs', 'SE'),
('12ocxuz5q2ro', '2017134986', 'Sanket Deshmukh', '2020-05-31', 1, 1, 0, 0, 0, 0, 0, 'testing 1', 'SE'),
('1a0l64h8f9d', '2017134986', 'Sanket Deshmukh', '2019-08-25', 1, 1, 1, 0, 0, 0, 0, 'publicity', NULL),
('2g9r92wcn4n', '2017134986', 'Sanket Deshmukh', '2019-07-01', 1, 1, 0, 0, 1, 1, 1, 'just doing timepass', NULL),
('3umeevfxfw', '2017134986', 'Sanket Deshmukh', '2020-06-04', 0, 0, 0, 1, 1, 1, 1, 'Nisarga', 'SE'),
('6dv2f1khpht', '2017134986', 'Sanket Deshmukh', '2020-06-02', 1, 0, 1, 0, 0, 0, 0, 'corona ka darr', 'SE'),
('6juk8db1dn3', '2017134986', 'Sanket Deshmukh', '2020-06-03', 0, 0, 0, 0, 1, 1, 1, 'Hey Sanky!!!!', 'SE'),
('7tga2a0q004', '2017135040', 'viraj tandel', '2019-10-05', 1, 1, 1, 1, 1, 1, 1, 'tandel', 'SE'),
('8evfmh5uo54', '2017134986', 'Sanket Deshmukh', '2020-05-01', 1, 0, 1, 1, 1, 0, 0, 'Maharashtra day', 'SE'),
('8j5eps4kp3p', '2017134979', 'Saurabh Yadav', '2019-10-08', 1, 1, 1, 1, 1, 1, 1, 'maza aare', 'TE'),
('8uu1pax89mb', '2017134986', 'Sanket Deshmukh', '2019-10-02', 0, 0, 1, 0, 1, 0, 0, 'vjcvkv', 'SE'),
('9j13hqat8uo', '2017135040', 'viraj tandel', '2019-10-15', 1, 1, 1, 0, 0, 0, 0, 'sss', 'SE'),
('9oomugm0vat', '2017134986', 'Sanket Deshmukh', '2020-06-02', 1, 1, 0, 1, 0, 0, 0, 'Cyclone', 'SE'),
('9u4mqvv2wpc', '2017134986', 'Sanket Deshmukh', '2019-07-22', 0, 0, 0, 0, 0, 1, 0, 'Enter Reason', NULL),
('alzucpyynno', '2017135040', 'viraj tandel', '2019-09-12', 0, 0, 1, 0, 0, 1, 0, 'ccihicf7icic', 'SE'),
('cau5gsqfh3a', '2017134986', 'Sanket Deshmukh', '2019-10-01', 1, 0, 0, 1, 0, 0, 1, 'ucuccv', NULL),
('d8g2ua4a6lb', '2017134980', 'Mushira shaikh', '2019-10-02', 0, 0, 1, 0, 1, 1, 0, 'jccick', 'TE'),
('f7g87ha1wgl', '2017135040', 'viraj tandel', '2019-09-01', 1, 0, 0, 0, 0, 0, 1, 'Just Checking', NULL),
('fpz8zv403sv', '2017135040', 'viraj tandel', '2019-09-04', 1, 0, 0, 1, 0, 1, 1, '9govkv', 'SE'),
('i5aixq8c6w', '2017134986', 'Sanket Deshmukh', '2019-11-08', 1, 0, 1, 0, 1, 0, 0, 'publicity of GOC ', 'SE'),
('i9rez57solk', '999', 'Admin', '2020-03-18', 0, 0, 0, 1, 1, 0, 0, 'Pub', NULL),
('kef3go5qps', '2017134980', 'Mushira shaikh', '2019-10-01', 0, 0, 0, 0, 1, 0, 1, '8ggi', 'TE'),
('mudu9efszep', '2017134986', 'Sanket Deshmukh', '2019-10-05', 0, 1, 1, 1, 1, 0, 0, 'icciciv', 'SE'),
('nurvmeb4g', '2017134986', 'Sanket Deshmukh', '2020-06-01', 0, 0, 0, 0, 0, 0, 1, 'Making 40', 'SE'),
('p0hl0ujv84', '2017134986', 'Sanket Deshmukh', '2020-05-30', 1, 0, 0, 0, 0, 0, 1, 'widbxbx', 'SE'),
('ph4gy5yazxe', '2017134986', 'Sanket Deshmukh', '2020-06-03', 0, 1, 1, 0, 1, 0, 0, 'testing 4', 'SE'),
('pzumtmq696', '2017135040', 'Viraj Tandel', '2020-06-04', 1, 1, 1, 1, 0, 0, 0, 'Monsoon ', 'BE'),
('qtmti4ly1ah', '2017134980', 'Mushira shaikh', '2019-08-15', 1, 1, 1, 1, 1, 1, 1, 'okay', NULL),
('smfbh95lna', '2017134986', 'Sanket Deshmukh', '2020-06-02', 0, 1, 0, 1, 0, 1, 0, 'testing 3', 'SE'),
('sqnd11c48qa', '2017135040', 'Viraj Tandel', '2020-06-03', 1, 1, 1, 0, 0, 0, 0, 'Hey Viraj!!!!!!', 'BE'),
('sum84plg6kl', '2017135040', 'Viraj Tandel', '2020-05-13', 1, 1, 1, 0, 0, 0, 0, 'Sanky birthday celebration', 'BE'),
('sx6i5gouvcb', '2017134986', 'Sanket Deshmukh', '2019-07-03', 1, 1, 0, 0, 0, 1, 1, 'nothing', NULL),
('tvzj4409f9', '2017134986', 'Sanket Deshmukh', '2020-06-01', 1, 1, 1, 0, 0, 0, 0, 'Cyclone Nisarga', 'SE'),
('vaqo5dl5cim', '2017135040', 'Viraj Tandel', '2020-06-03', 1, 1, 0, 0, 0, 0, 0, 'Testing', 'BE');

-- --------------------------------------------------------

--
-- Table structure for table `minute`
--

CREATE TABLE `minute` (
  `id` varchar(10) NOT NULL,
  `agenda` varchar(25) NOT NULL,
  `da_te` date NOT NULL,
  `ti_me` time NOT NULL,
  `creator` varchar(25) NOT NULL,
  `minute` blob DEFAULT NULL,
  `work` blob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `minute`
--

INSERT INTO `minute` (`id`, `agenda`, `da_te`, `ti_me`, `creator`, `minute`, `work`) VALUES
('2017134986', 'Coding Walkthrough', '2019-10-20', '15:16:42', 'Sanket Deshmukh', 0x44697363757373696f6e206f66204974696e6572617279, 0x7b226d696e75746573223a5b7b227461736b223a22436f646563686566222c22706572736f6e223a2253617572616268205961646176227d2c7b227461736b223a224861636b65726561727468222c22706572736f6e223a224973686d656574204b617572227d5d7d),
('2017134986', 'Joomla', '2019-10-20', '15:30:23', 'Sanket Deshmukh', 0x312e20426173696320496e74726f206f66204a6f6f6d6c610a322e204f76657276696577206f66204a6f6f6d6c6120636f6d706f6e656e74732c206d6f64756c65732c20657874656e73696f6e7320616e6420706c7567696e730a332e2048656c702073747564656e747320746f206372656174652042617369632054656d706c617465207573696e67206d616a6f7220636f6d706f6e656e7473206f66206a6f6f6d6c610a342e2032207765656b732074696d6520706572696f6420666f722073747564656e747320746f207375626d69742070726f746f74797065206f662044424954207765627369746528746f2067657420696e7465726e7368697029, 0x7b226d696e75746573223a5b7b227461736b223a22496e74726f64756374696f6e222c22706572736f6e223a224d75736869726120536861696b68227d2c7b227461736b223a2242617369632054656d706c617465222c22706572736f6e223a2253616e6b657420446573686d756b68227d5d7d),
('2017134980', 'GOC', '2019-10-20', '16:39:07', 'Mushira shaikh', 0x5461736b20446973747269627574696f6e, 0x7b226d696e75746573223a5b7b227461736b223a22466c6578222c22706572736f6e223a224166696620536861696b68227d2c7b227461736b223a22706f7374657273222c22706572736f6e223a2253616e696b612042686167776174227d2c7b227461736b223a224f757420636f6c6c656765207075626c6963697479222c22706572736f6e223a22476175726176204a61696e227d2c7b227461736b223a224f757420636f6c6c656765207075626c6963697479222c22706572736f6e223a224a6576696e61205665726768657365227d2c7b227461736b223a22496e20636f6c6c656765205075626c6963697479222c22706572736f6e223a224d75736869726120536861696b68227d5d7d),
('2017134980', 'Connectivity ', '2019-10-20', '16:46:50', 'Mushira shaikh', 0x546869732077696c6c2068656c702073747564656e747320746f2063726561746520426173696320416e64726f6964206170702f7765627369746520616e64204261636b656e6420415049207573696e67204e6f64656a732026204d7973716c2e20416c736f2073747564656e74732077696c6c206c6561726e20686f7720746f2073656e642064617461206f76657220696e7465726e657420746f207365727665722e, 0x7b226d696e75746573223a5b7b227461736b223a22416e64726f6964222c22706572736f6e223a2253616e6b657420446573686d756b68227d2c7b227461736b223a22576562222c22706572736f6e223a22566972616a2054616e64656c227d2c7b227461736b223a224e6f64656a73222c22706572736f6e223a2253617572616268205961646176227d5d7d),
('2017134986', 'Code Combat', '2019-10-20', '16:53:42', 'Sanket Deshmukh', 0x5468697320697320496e746572636f6c6c65676520436f64696e6720436f6d7065746974696f6e207769746820646966666572656e7420736574206f662072756c65732e20546869732077696c6c2068656c7020757365727320746f20636f6465207769746820756e6b6e6f776e20636f6465727320696e207465616d2e, 0x7b226d696e75746573223a5b7b227461736b223a225175657374696f6e2053657474696e67222c22706572736f6e223a22566972616a2054616e64656c227d2c7b227461736b223a224c616220417272616e67656d656e74222c22706572736f6e223a2253616e6b657420446573686d756b68227d2c7b227461736b223a227075626c6963697479222c22706572736f6e223a22476175726176204a61696e227d2c7b227461736b223a22706f73746572222c22706572736f6e223a2253616e696b612042686167776174227d5d7d),
('2017134980', 'Mumbai Hackathon', '2019-10-20', '16:56:46', 'Mushira shaikh', '', 0x7b226d696e75746573223a5b7b227461736b223a22566f6c756e74656572222c22706572736f6e223a224973686d656574204b617572227d2c7b227461736b223a22566f6c756e74656572222c22706572736f6e223a22566972616a2054616e64656c227d2c7b227461736b223a224c616220617272616e67656d656e74222c22706572736f6e223a2253616e6b657420446573686d756b68227d2c7b227461736b223a227075626c6963697479222c22706572736f6e223a224a6576696e61205665726768657365227d2c7b227461736b223a22766f6c756e74656572222c22706572736f6e223a224d75736869726120536861696b68227d5d7d),
('2017134980', 'Coderank', '2019-10-20', '16:59:25', 'Mushira shaikh', 0x436f646572616e6b2077696c6c2068656c7020636f6c6c65676520746f206b65657020747261636b206f6620706572666f726d616e6365206f662053747564656e74732e20416c736f207468652073747564656e74732077696e6e696e6720616e642070617274696369706174696e6720636f6e73697374656e746c7920696e206d616e79206576656e74732077696c6c20676574204578636974696e67207072697a65732e, 0x7b226d696e75746573223a5b7b227461736b223a225265636f7264206b6565706572222c22706572736f6e223a224973686d656574204b617572227d5d7d),
('2017134980', 'Easycode 1', '2019-10-20', '17:01:23', 'Mushira shaikh', 0x45617379636f64652069732074686520436f64696e6720436f6d7065746974696f6e20666f7220534520495420746f20747261636b2074686520696d70726f76656d656e74206f6620706572666f726d616e6365206f662073747564656e7420696e20436f6d706574697469766520636f64696e672e, 0x7b226d696e75746573223a5b7b227461736b223a225175657374696f6e2053657474696e67222c22706572736f6e223a2253616e6b657420446573686d756b68227d2c7b227461736b223a225175657374696f6e2053657474696e67222c22706572736f6e223a2253617572616268205961646176227d2c7b227461736b223a225175657374696f6e2053657474696e67222c22706572736f6e223a224d75736869726120536861696b68227d5d7d),
('2017134986', 'CSI Trivia', '2019-10-20', '17:04:37', 'Sanket Deshmukh', 0x496e2074686973207765656b6c7920636f64696e67207175657374696f6e2077696c6c20626520707574207570206f6e206e6f7469636520626f617264732e20546865206e616d65206f662066697273742073747564656e7420746f20676976652074686520636f727265637420616e737765722077696c6c20626520616e6e6f756e6365642061732077696e6e65722e, 0x7b226d696e75746573223a5b7b227461736b223a225175657374696f6e2053657474696e67222c22706572736f6e223a22566972616a2054616e64656c227d5d7d),
('2018330014', 'PhotoBattle', '2019-10-21', '14:27:05', 'Afif Shaikh', 0x412042617369632057616c6b7468726f756768206f662050686f746f73686f7020616e6420506f73746572206d616b696e6720636f6d7065746974696f6e, 0x7b226d696e75746573223a5b7b227461736b223a22566f6c756e74656572222c22706572736f6e223a224d75736869726120536861696b68227d5d7d),
('2017134986', 'testing', '2020-06-02', '12:09:30', 'Sanket Deshmukh', 0x687576626a6e, 0x7b226d696e75746573223a5b7b227461736b223a2279786a76626b222c22706572736f6e223a2253616e6b657420446573686d756b68227d5d7d),
('2018330014', 'hello', '2020-06-03', '12:49:37', 'Afif Shaikh', 0x7468697320697320666f722074657374696e67, 0x7b226d696e75746573223a5b7b227461736b223a22746573742031222c22706572736f6e223a22476175726176204a61696e227d2c7b227461736b223a22746573742032222c22706572736f6e223a2253616e6b657420446573686d756b68227d5d7d),
('2017134986', '', '2020-06-03', '14:51:40', 'Sanket Deshmukh', '', 0x7b226d696e75746573223a5b5d7d),
('2018330014', '', '2020-06-03', '16:37:34', 'Afif Shaikh', '', 0x7b226d696e75746573223a5b5d7d),
('2017134979', '', '2020-06-03', '17:52:07', 'Saurabh Yadav', '', 0x7b226d696e75746573223a5b5d7d),
('2017134979', '', '2020-06-03', '17:52:11', 'Saurabh Yadav', '', 0x7b226d696e75746573223a5b5d7d);

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

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
  `dp` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`id`, `password`, `role`, `name`, `email`, `phone`, `year`, `branch`, `rollno`, `batch`, `membership_left`, `dp`) VALUES
('2017134979', '250998', 'PR Head', 'Saurabh Yadav', 'survir44@gmail.com', '9640278397', 'TE', 'IT', 74, 'D', 1, 'http://tayyabali.in:9091/images/saurabh.png'),
('2017134980', 'abcdef', 'Chairperson', 'Mushira shaikh', 'mushira.shaikh1999@gmail.com', '8976099903', 'TE', 'IT', 54, 'C', 1, 'http://tayyabali.in:9091/images/mushira.png'),
('2017134986', 'qwerty', 'Technical Head', 'Sanket Deshmukh', 'meetsanket24@gmail.com', '9702717188', 'SE', 'IT', 7, 'B', 1, 'http://tayyabali.in:9091/images/sanket.png'),
('2017135030', 'ishmeet', 'SBC', 'Ishmeet Dua', 'ishmeet245@gmail.com', '9967937648', 'TE', 'IT', 14, 'D', 1, NULL),
('2017135040', 'asdfg', 'HOD', 'Viraj Tandel', 'virajtandel72@gmail.com', '8976853951', 'BE', 'IT', 69, 'D', 1, 'http://tayyabali.in:9091/images/viraj.png'),
('2018330014', '12345', 'Creative Head', 'Afif Shaikh', 'shaikhafif48@gmail.com', '9769320992', 'TE', 'IT', 79, 'A', 1, 'http://tayyabali.in:9091/images/csi_afif.png'),
('999', '0000', 'Admin', 'Admin', NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `publicity`
--

CREATE TABLE `publicity` (
  `eid` varchar(5) NOT NULL,
  `collected` int(11) DEFAULT NULL,
  `spent` int(11) DEFAULT NULL,
  `desk` int(11) DEFAULT NULL,
  `in_class` int(11) DEFAULT NULL,
  `target` varchar(50) DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `publicity`
--

INSERT INTO `publicity` (`eid`, `collected`, `spent`, `desk`, `in_class`, `target`, `comment`, `status`) VALUES
('LLyTE', 0, 20, 2, 1, '40', 'Chal Na bhidu!!!! lol!!', 3);

-- --------------------------------------------------------

--
-- Table structure for table `request`
--

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
  `year` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `technical`
--

CREATE TABLE `technical` (
  `eid` varchar(5) NOT NULL,
  `qs_set` int(11) DEFAULT NULL,
  `internet` int(11) DEFAULT NULL,
  `software_install` int(11) DEFAULT NULL,
  `comment` varchar(250) DEFAULT NULL,
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `technical`
--

INSERT INTO `technical` (`eid`, `qs_set`, `internet`, `software_install`, `comment`, `status`) VALUES
('LLyTE', 1, 1, 1, 'Technical API completed', 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `creative`
--
ALTER TABLE `creative`
  ADD PRIMARY KEY (`eid`);

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`eid`);

--
-- Indexes for table `final_list`
--
ALTER TABLE `final_list`
  ADD PRIMARY KEY (`RID`),
  ADD KEY `stud_id` (`stud_id`);

--
-- Indexes for table `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `publicity`
--
ALTER TABLE `publicity`
  ADD PRIMARY KEY (`eid`);

--
-- Indexes for table `request`
--
ALTER TABLE `request`
  ADD PRIMARY KEY (`RID`),
  ADD KEY `fk1` (`stud_id`);

--
-- Indexes for table `technical`
--
ALTER TABLE `technical`
  ADD PRIMARY KEY (`eid`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `creative`
--
ALTER TABLE `creative`
  ADD CONSTRAINT `creative_ibfk_1` FOREIGN KEY (`eid`) REFERENCES `events` (`eid`);

--
-- Constraints for table `final_list`
--
ALTER TABLE `final_list`
  ADD CONSTRAINT `final_list_ibfk_1` FOREIGN KEY (`stud_id`) REFERENCES `profile` (`id`);

--
-- Constraints for table `publicity`
--
ALTER TABLE `publicity`
  ADD CONSTRAINT `publicity_ibfk_1` FOREIGN KEY (`eid`) REFERENCES `events` (`eid`);

--
-- Constraints for table `request`
--
ALTER TABLE `request`
  ADD CONSTRAINT `fk1` FOREIGN KEY (`stud_id`) REFERENCES `profile` (`id`);

--
-- Constraints for table `technical`
--
ALTER TABLE `technical`
  ADD CONSTRAINT `technical_ibfk_1` FOREIGN KEY (`eid`) REFERENCES `events` (`eid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
