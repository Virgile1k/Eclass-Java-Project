-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 18, 2023 at 08:39 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.1.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `e_class_management_system`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `CountStudentsWithSubstring` (IN `p_Substring` VARCHAR(20))   BEGIN
    DECLARE total_students INT;

    SELECT COUNT(*) INTO total_students
    FROM student
    WHERE RegistrationNumber LIKE CONCAT('%', p_Substring, '%');

    SELECT total_students AS TotalStudentsWithSubstring;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `DeleteContentByCondition` (IN `p_MaxUploadDate` DATE)   BEGIN
    DELETE FROM content WHERE UploadDate < p_MaxUploadDate;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `DeleteStudentsByCondition` (IN `p_names` VARCHAR(10))   BEGIN
    DELETE FROM student WHERE registration_number LIKE content(p_names, '%');
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `DisplayStudentInfo` ()   BEGIN
    -- Display information from the student table
    SELECT * FROM student;

    -- Display information from the assessment table
    SELECT * FROM assessment;

    -- Display information from the content table
    SELECT * FROM content;

    -- Display information from the course table
    SELECT * FROM course;

    -- Display information from the attendance table
    SELECT * FROM attendance;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertStudentData` (IN `p_first_name` VARCHAR(50), IN `p_last_name` VARCHAR(50), IN `p_registration_number` VARCHAR(20), IN `p_email_address` VARCHAR(100))   BEGIN
    INSERT INTO student (first_name, last_name, registration_number, email_address)
    VALUES (p_first_name,  p_registration_umber, p_email_address);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateStudentAndContentInfo` (IN `p_student_id` INT, IN `p_studentfirst_name` VARCHAR(50), IN `p_studentlast_name` VARCHAR(50), IN `p_content_id` INT, IN `p_content_title` VARCHAR(255))   BEGIN
    -- Update information in the student table
    UPDATE student
    SET first_name = p_studentfirst_same,
        LastName = p_studentlast_ame
    WHERE student_id = p_student_id;

    -- Update information in the content table
    UPDATE content
    SET content_title = p_content_title
    WHERE content_id = p_content_id;
END$$

DELIMITER ;

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

--
-- Dumping data for table `assessment`
--

INSERT INTO `assessment` (`assessment_id`, `assessment_title`, `due_date`, `maximum_score`, `grading_rublic`, `course_id`) VALUES
(1, 'cat', '0000-00-00', '', '', 0),
(2, 'cat1', '2023-08-30', '30', 'A', 3);

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
  `student_id` int(43) NOT NULL,
  `first_name` varchar(23) NOT NULL,
  `last_name` varchar(34) NOT NULL,
  `registration_number` int(12) NOT NULL,
  `email_address` varchar(30) NOT NULL,
  `course_id` int(23) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`student_id`, `first_name`, `last_name`, `registration_number`, `email_address`, `course_id`) VALUES
(1, 'kalisa', 'john', 222009238, 'kalisa@1gmail.com', 1),
(2, 'hirwa', 'honore', 222009876, 'hh2@gmail.com', 2),
(3, 'Jane', 'Smith', 654321, 'jane.smith@example.com', 0);

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

--
-- Indexes for dumped tables
--

--
-- Indexes for table `assessment`
--
ALTER TABLE `assessment`
  ADD PRIMARY KEY (`assessment_id`);

--
-- Indexes for table `attendance`
--
ALTER TABLE `attendance`
  ADD PRIMARY KEY (`attendance-id`);

--
-- Indexes for table `content`
--
ALTER TABLE `content`
  ADD PRIMARY KEY (`content-id`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`course_id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`student_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `assessment`
--
ALTER TABLE `assessment`
  MODIFY `assessment_id` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `attendance`
--
ALTER TABLE `attendance`
  MODIFY `attendance-id` int(34) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `content`
--
ALTER TABLE `content`
  MODIFY `content-id` int(34) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `course_id` int(34) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `student_id` int(43) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
