-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 17, 2020 at 11:18 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.2.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `project`
--

-- --------------------------------------------------------

--
-- Table structure for table `apply`
--

CREATE TABLE `apply` (
  `companyName` char(10) NOT NULL,
  `position` char(40) NOT NULL,
  `sid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `apply`
--

INSERT INTO `apply` (`companyName`, `position`, `sid`) VALUES
('YouTube', 'developer', 987),
('YouTube', 'developer', 9988),
('YouTube', 'programmer', 987);

-- --------------------------------------------------------

--
-- Table structure for table `attendInterview`
--

CREATE TABLE `attendInterview` (
  `SID` int(11) DEFAULT NULL,
  `interviewId` int(11) NOT NULL,
  `date` char(15) DEFAULT NULL,
  `location` char(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `attendInterview`
--

INSERT INTO `attendInterview` (`SID`, `interviewId`, `date`, `location`) VALUES
(11111, 11111, 'Jan 15 2020', '1234 East Mall'),
(22222, 22222, 'Jan 16 2020', '1234 East Mall'),
(33333, 33333, 'Jan 17 2020', '1234 East Mall'),
(11111, 98765, 'Feb 15 2020', '1234 West Mall');

-- --------------------------------------------------------

--
-- Table structure for table `conductInterview`
--

CREATE TABLE `conductInterview` (
  `companyName` char(30) DEFAULT NULL,
  `interviewID` int(11) NOT NULL,
  `date` char(15) DEFAULT NULL,
  `location` char(40) DEFAULT NULL,
  `SID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `conductInterview`
--

INSERT INTO `conductInterview` (`companyName`, `interviewID`, `date`, `location`, `SID`) VALUES
('YouTube', 1123, '20200807', 'UBC', NULL),
('YouTube', 11111, 'Jan 15 2020', '1234 East Mall', 9988),
('YouTube', 22222, 'Jan 16 2020', '1234 East Mall', 22222),
('YouTube', 33333, 'Jan 17 2020', '1234 East Mall', 33333),
('UBC', 98765, 'Feb 15 2020', '1234 West Mall', 22222);

-- --------------------------------------------------------

--
-- Table structure for table `Course`
--

CREATE TABLE `Course` (
  `Department` char(10) NOT NULL,
  `CourseNum` int(11) NOT NULL,
  `CourseDescription` char(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Course`
--

INSERT INTO `Course` (`Department`, `CourseNum`, `CourseDescription`) VALUES
('CPSC', 110, 'Intro to Programming'),
('CPSC', 210, 'systematic programming'),
('CPSC', 304, 'database'),
('MATH', 200, 'calculus III'),
('MATH', 221, 'Matrix Algebra');

-- --------------------------------------------------------

--
-- Table structure for table `Employer`
--

CREATE TABLE `Employer` (
  `CompanyName` char(30) NOT NULL,
  `Description` char(100) DEFAULT NULL,
  `username` char(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Employer`
--

INSERT INTO `Employer` (`CompanyName`, `Description`, `username`) VALUES
('e2', NULL, 'e2'),
('McDonalds', 'fastfood company', NULL),
('Starship inc', 'startup company', NULL),
('tik tok', 'short video app', NULL),
('UBC', 'University of British Columbia', NULL),
('Uber Eat', NULL, 'e3'),
('YouTube', NULL, 'Youtube123');

-- --------------------------------------------------------

--
-- Table structure for table `JobPost`
--

CREATE TABLE `JobPost` (
  `companyName` char(10) NOT NULL,
  `postDate` char(10) NOT NULL,
  `position` char(40) NOT NULL,
  `requirement` char(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `JobPost`
--

INSERT INTO `JobPost` (`companyName`, `postDate`, `position`, `requirement`) VALUES
('e2', '20200612', 'Programmer', 'It\'s ok that have no experience'),
('YouTube', '2020-01-08', 'developer', '1 year experience'),
('YouTube', '2020-02-21', 'debugger', 'taken CPSC 310'),
('YouTube', '2020-03-08', 'agile developer', '2 year experience'),
('YouTube', '2020-05-08', 'web developer', 'know html'),
('YouTube', '2020-06-14', 'programmer', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Profile`
--

CREATE TABLE `Profile` (
  `Username` char(10) NOT NULL,
  `Email` char(20) DEFAULT NULL,
  `Address` char(20) DEFAULT NULL,
  `Name` char(10) DEFAULT NULL,
  `PhoneNumber` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Profile`
--

INSERT INTO `Profile` (`Username`, `Email`, `Address`, `Name`, `PhoneNumber`) VALUES
('student1', 'student1@gmail.com', '123 random ave', 'Joseph', 77888777),
('student2', 'student2@gmail.com', '122 random ave', 'Josephine', 77855688),
('student3', 'student3@gmail.com', '121 random ave', 'Sam', 77855677),
('student4', 'student4@gmail.com', '120 random ave', 'Albert', 60441201),
('student5', 'student5@gmail.com', '128 random ave', 'Christine', 6045599);

-- --------------------------------------------------------

--
-- Table structure for table `Student`
--

CREATE TABLE `Student` (
  `SID` int(11) NOT NULL,
  `SIN` int(11) DEFAULT NULL,
  `name` char(20) DEFAULT NULL,
  `school` char(20) DEFAULT NULL,
  `workExperience` char(40) DEFAULT NULL,
  `personalProject` char(40) DEFAULT NULL,
  `birthday` char(15) DEFAULT NULL,
  `phoneNumber` int(11) DEFAULT NULL,
  `sex` char(10) DEFAULT NULL,
  `username` char(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Student`
--

INSERT INTO `Student` (`SID`, `SIN`, `name`, `school`, `workExperience`, `personalProject`, `birthday`, `phoneNumber`, `sex`, `username`) VALUES
(987, 987, 's2', 'UT', 'WORK123', NULL, NULL, 0, NULL, 's2'),
(9988, 7777, 'taylor', 'U of A', NULL, NULL, NULL, 0, NULL, 'taylor1'),
(11111, 11111, 'John', 'UBC', 'None', 'None', NULL, NULL, NULL, NULL),
(22222, 22222, 'Wang', 'UBC', 'None', 'Library Management System', '', NULL, NULL, 'student2'),
(33333, 33333, 'Jack', 'UBC', '1 year exp', 'Simulated University Database', '1998-03-04', 778000000, 'Male', 'student3'),
(44444, 44444, 'Grill', 'McGill', 'None', 'None', NULL, NULL, NULL, 'student4'),
(55555, 55555, NULL, 'UT', '3 year exp', '3d graphics film', NULL, NULL, NULL, 'student5');

-- --------------------------------------------------------

--
-- Table structure for table `studyProgress`
--

CREATE TABLE `studyProgress` (
  `sid` int(11) NOT NULL,
  `major` char(10) DEFAULT NULL,
  `takenCourse` char(100) DEFAULT NULL,
  `graduateYear` char(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `studyProgress`
--

INSERT INTO `studyProgress` (`sid`, `major`, `takenCourse`, `graduateYear`) VALUES
(11111, 'CPSC', 'CPSC110', '2025'),
(22222, 'CPSC', 'CPSC121', '2023'),
(33333, 'BIOL', 'BIOL121', '2023'),
(44444, 'BIOL', 'WRDS150', '2020'),
(55555, 'BIOL', 'SCIE113', '2022');

-- --------------------------------------------------------

--
-- Table structure for table `theEvent`
--

CREATE TABLE `theEvent` (
  `eventDate` char(15) DEFAULT NULL,
  `location` char(20) DEFAULT NULL,
  `eventid` int(11) NOT NULL,
  `companyName` char(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `theEvent`
--

INSERT INTO `theEvent` (`eventDate`, `location`, `eventid`, `companyName`) VALUES
('2020-08-10', 'ICICS', 1234, 'YouTube'),
('30300304', 'MATH', 3445, 'e2'),
('Jan 1 2019', '1234 Main Mall', 11111, 'YouTube'),
('Jan 15 2019', '1234 Main Mall', 22222, 'tik tok'),
('March 1 2019', '1234 East Mall', 33333, 'tik tok'),
('May 2 2019', '1234 East Mall', 44444, 'UBC'),
('Jan 1 2019', '1234 Robson Ave', 55555, 'UBC');

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `Username` char(10) NOT NULL,
  `Password` char(10) DEFAULT NULL,
  `type` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`Username`, `Password`, `type`) VALUES
('e2', 'e2', 'e'),
('e3', 'e3', 'e'),
('s2', 's2', 's'),
('student1', '12345', 's'),
('student2', '12345', 's'),
('student3', '54321', 's'),
('student4', 'sdfsf8234', 's'),
('student5', 'md1m377.', 's'),
('taylor1', 'taylor1', 's'),
('Youtube123', '1111', 'e');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `apply`
--
ALTER TABLE `apply`
  ADD PRIMARY KEY (`companyName`,`position`,`sid`) USING BTREE,
  ADD KEY `sid` (`sid`);

--
-- Indexes for table `attendInterview`
--
ALTER TABLE `attendInterview`
  ADD PRIMARY KEY (`interviewId`),
  ADD KEY `SID` (`SID`);

--
-- Indexes for table `conductInterview`
--
ALTER TABLE `conductInterview`
  ADD PRIMARY KEY (`interviewID`),
  ADD KEY `companyName` (`companyName`),
  ADD KEY `SID` (`SID`);

--
-- Indexes for table `Course`
--
ALTER TABLE `Course`
  ADD PRIMARY KEY (`Department`,`CourseNum`);

--
-- Indexes for table `Employer`
--
ALTER TABLE `Employer`
  ADD PRIMARY KEY (`CompanyName`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `username_2` (`username`);

--
-- Indexes for table `JobPost`
--
ALTER TABLE `JobPost`
  ADD PRIMARY KEY (`companyName`,`postDate`,`position`),
  ADD UNIQUE KEY `companyName` (`companyName`,`position`);

--
-- Indexes for table `Profile`
--
ALTER TABLE `Profile`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `Student`
--
ALTER TABLE `Student`
  ADD PRIMARY KEY (`SID`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `studyProgress`
--
ALTER TABLE `studyProgress`
  ADD PRIMARY KEY (`sid`);

--
-- Indexes for table `theEvent`
--
ALTER TABLE `theEvent`
  ADD PRIMARY KEY (`eventid`),
  ADD KEY `fk_companyName` (`companyName`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`Username`,`type`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `apply`
--
ALTER TABLE `apply`
  ADD CONSTRAINT `apply_ibfk_1` FOREIGN KEY (`companyName`) REFERENCES `Employer` (`CompanyName`) ON DELETE CASCADE,
  ADD CONSTRAINT `apply_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `Student` (`SID`) ON DELETE CASCADE,
  ADD CONSTRAINT `apply_ibfk_3` FOREIGN KEY (`companyName`) REFERENCES `Employer` (`CompanyName`) ON DELETE CASCADE,
  ADD CONSTRAINT `apply_ibfk_4` FOREIGN KEY (`companyName`) REFERENCES `Employer` (`CompanyName`) ON DELETE CASCADE,
  ADD CONSTRAINT `apply_ibfk_5` FOREIGN KEY (`sid`) REFERENCES `Student` (`SID`);

--
-- Constraints for table `attendInterview`
--
ALTER TABLE `attendInterview`
  ADD CONSTRAINT `attendinterview_ibfk_1` FOREIGN KEY (`SID`) REFERENCES `Student` (`SID`);

--
-- Constraints for table `conductInterview`
--
ALTER TABLE `conductInterview`
  ADD CONSTRAINT `conductinterview_ibfk_1` FOREIGN KEY (`companyName`) REFERENCES `Employer` (`CompanyName`),
  ADD CONSTRAINT `conductinterview_ibfk_2` FOREIGN KEY (`SID`) REFERENCES `Student` (`SID`);

--
-- Constraints for table `Employer`
--
ALTER TABLE `Employer`
  ADD CONSTRAINT `employer_ibfk_1` FOREIGN KEY (`username`) REFERENCES `User` (`Username`) ON DELETE CASCADE;

--
-- Constraints for table `JobPost`
--
ALTER TABLE `JobPost`
  ADD CONSTRAINT `jobpost_ibfk_1` FOREIGN KEY (`companyName`) REFERENCES `Employer` (`CompanyName`) ON DELETE CASCADE;

--
-- Constraints for table `Profile`
--
ALTER TABLE `Profile`
  ADD CONSTRAINT `profile_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `User` (`Username`);

--
-- Constraints for table `Student`
--
ALTER TABLE `Student`
  ADD CONSTRAINT `fk_username` FOREIGN KEY (`username`) REFERENCES `User` (`Username`) ON DELETE CASCADE;

--
-- Constraints for table `studyProgress`
--
ALTER TABLE `studyProgress`
  ADD CONSTRAINT `FK_sid` FOREIGN KEY (`sid`) REFERENCES `Student` (`SID`) ON DELETE CASCADE,
  ADD CONSTRAINT `studyprogress_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `Student` (`SID`);

--
-- Constraints for table `theEvent`
--
ALTER TABLE `theEvent`
  ADD CONSTRAINT `fk_companyName` FOREIGN KEY (`companyName`) REFERENCES `Employer` (`CompanyName`) ON DELETE CASCADE,
  ADD CONSTRAINT `theevent_ibfk_1` FOREIGN KEY (`companyName`) REFERENCES `Employer` (`CompanyName`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
