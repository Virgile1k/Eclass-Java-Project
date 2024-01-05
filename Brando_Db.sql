-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 03, 2024 at 06:26 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Brando_Db`
--

-- --------------------------------------------------------

--
-- Table structure for table `assessment`
--

CREATE TABLE `assessment` (
  `assessment_id` int(12) NOT NULL,
  `assessment_title` varchar(23) NOT NULL,
  `due_date` date NOT NULL,
  `maximum_score` varchar(12) NOT NULL,
  `grading_rublic` varchar(6) NOT NULL,
  `course_id` int(23) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `attendance`
--

CREATE TABLE `attendance` (
  `attendance-id` int(34) NOT NULL,
  `student_id` int(23) NOT NULL,
  `course_id` int(23) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `attendance`
--

INSERT INTO `attendance` (`attendance-id`, `student_id`, `course_id`, `date`) VALUES
(1, 1, 2, '2023-04-03');

-- --------------------------------------------------------

--
-- Table structure for table `content`
--

CREATE TABLE `content` (
  `content-id` int(34) NOT NULL,
  `content-title` varchar(23) NOT NULL,
  `upload_date` date NOT NULL,
  `author` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `content`
--

INSERT INTO `content` (`content-id`, `content-title`, `upload_date`, `author`) VALUES
(1, 'software development', '2023-02-12', 'mwiza');

--
-- Triggers `content`
--
DELIMITER $$
CREATE TRIGGER `ContentAfterDelete` AFTER DELETE ON `content` FOR EACH ROW BEGIN
    INSERT INTO content_delete_log (Content_id, DeletedTimestamp)
    VALUES (Content_id, N);
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `ContentAfterInsert` AFTER INSERT ON `content` FOR EACH ROW BEGIN
    INSERT INTO content_insert_log (ContentID, InsertedTimestamp)
    VALUES (content_id, NOW());
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `course_id` int(34) NOT NULL,
  `course_title` varchar(23) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `enrolled_student` varchar(12) NOT NULL,
  `course_materials` varchar(23) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`course_id`, `course_title`, `start_date`, `end_date`, `enrolled_student`, `course_materials`) VALUES
(1, 'software engeneering', '2022-02-07', '2023-12-07', 'bsc', 'computer');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `student_id` int(11) NOT NULL,
  `first_name` varchar(23) NOT NULL,
  `last_name` varchar(34) NOT NULL,
  `registration_number` int(12) NOT NULL,
  `email_address` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`student_id`, `first_name`, `last_name`, `registration_number`, `email_address`) VALUES
(1, 'kalisa', 'john', 222009238, 'kalisa@1gmail.com'),
(2, 'hirwa', 'honore', 222009876, 'hh2@gmail.com'),
(3, 'Jane', 'Smith', 654321, 'jane.smith@example.com');

--
-- Triggers `student`
--
DELIMITER $$
CREATE TRIGGER `StudentAfterDelete` AFTER DELETE ON `student` FOR EACH ROW BEGIN
    INSERT INTO student_delete_log (student_id, DeletedTimestamp)
    VALUES (Student_id, NOW());
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `StudentAfterInsert` AFTER INSERT ON `student` FOR EACH ROW BEGIN
    INSERT INTO student_insert_log (student_id, InsertedTimestamp)
    VALUES (student_id, NOW());
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(10) NOT NULL,
  `username` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `password` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `email`, `phone`, `address`, `password`) VALUES
(1, 'Brandice', 'brandice@gmail.com', '+250782689127', 'Huye', '12345678'),
(2, 'vg', 'vg@gmail.com', '+250782689127', NULL, '12345678'),
(3, 'Bonehur', 'ndayambajevg16@gmail.com', '0722189351', NULL, 'NDAYAMBAJE@vg2022'),
(4, 'vplanet', 'virgilendayambaje@gmail.com', '+250722189351', NULL, 'Test@12345');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`student_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `student_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
