-- phpMyAdmin SQL Dump
-- version 5.2.3deb1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 14, 2026 at 05:16 PM
-- Server version: 8.4.10-0ubuntu0.26.04.1
-- PHP Version: 8.5.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `eagle_auth`
--
CREATE DATABASE IF NOT EXISTS `eagle_auth` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `eagle_auth`;

-- --------------------------------------------------------

--
-- Table structure for table `isl_progress`
--

CREATE TABLE `isl_progress` (
  `user_id` int NOT NULL,
  `xp` int DEFAULT '0',
  `learned` int DEFAULT '0',
  `quiz_correct` int DEFAULT '0',
  `streak_days` int DEFAULT '0',
  `last_active` date DEFAULT NULL,
  `ui_lang` varchar(40) DEFAULT 'English'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `isl_progress`
--

INSERT INTO `isl_progress` (`user_id`, `xp`, `learned`, `quiz_correct`, `streak_days`, `last_active`, `ui_lang`) VALUES
(22, 0, 0, 0, 0, NULL, 'English'),
(23, 5, 1, 0, 1, '2026-07-13', 'English');

-- --------------------------------------------------------

--
-- Table structure for table `isl_seen`
--

CREATE TABLE `isl_seen` (
  `user_id` int NOT NULL,
  `sign_id` int NOT NULL,
  `seen_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `isl_seen`
--

INSERT INTO `isl_seen` (`user_id`, `sign_id`, `seen_at`) VALUES
(23, 1, '2026-07-13 14:39:06');

-- --------------------------------------------------------

--
-- Table structure for table `isl_signs`
--

CREATE TABLE `isl_signs` (
  `id` int NOT NULL,
  `slug` varchar(60) NOT NULL,
  `word_en` varchar(80) NOT NULL,
  `emoji` varchar(16) DEFAULT '',
  `category` varchar(40) NOT NULL,
  `difficulty` tinyint DEFAULT '1',
  `yt_id` varchar(20) DEFAULT '',
  `yt_start` int DEFAULT '0',
  `yt_credit` varchar(120) DEFAULT '',
  `yt_channel_url` varchar(200) DEFAULT '',
  `sort_order` int DEFAULT '100'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `isl_signs`
--

INSERT INTO `isl_signs` (`id`, `slug`, `word_en`, `emoji`, `category`, `difficulty`, `yt_id`, `yt_start`, `yt_credit`, `yt_channel_url`, `sort_order`) VALUES
(1, 'hello', 'Hello', '👋', 'greetings', 1, '', 0, '', '', 0),
(2, 'thank-you', 'Thank You', '🙏', 'greetings', 1, '', 0, '', '', 1),
(3, 'sorry', 'Sorry', '😔', 'greetings', 1, '', 0, '', '', 2),
(4, 'please', 'Please', '🤲', 'greetings', 1, '', 0, '', '', 3),
(5, 'yes', 'Yes', '✅', 'greetings', 1, '', 0, '', '', 4),
(6, 'no', 'No', '❌', 'greetings', 1, '', 0, '', '', 5),
(7, 'good-morning', 'Good Morning', '🌅', 'greetings', 2, '', 0, '', '', 6),
(8, 'how-are-you', 'How Are You', '🤔', 'greetings', 2, '', 0, '', '', 7),
(9, 'mother', 'Mother', '👩', 'family', 1, '', 0, '', '', 8),
(10, 'father', 'Father', '👨', 'family', 1, '', 0, '', '', 9),
(11, 'sister', 'Sister', '👧', 'family', 1, '', 0, '', '', 10),
(12, 'brother', 'Brother', '👦', 'family', 1, '', 0, '', '', 11),
(13, 'friend', 'Friend', '🤝', 'family', 1, '', 0, '', '', 12),
(14, 'baby', 'Baby', '👶', 'family', 1, '', 0, '', '', 13),
(15, 'family', 'Family', '👨‍👩‍👧', 'family', 2, '', 0, '', '', 14),
(16, 'teacher', 'Teacher', '👩‍🏫', 'family', 2, '', 0, '', '', 15),
(17, 'water', 'Water', '💧', 'food', 1, '', 0, '', '', 16),
(18, 'food', 'Food', '🍽️', 'food', 1, '', 0, '', '', 17),
(19, 'milk', 'Milk', '🥛', 'food', 1, '', 0, '', '', 18),
(20, 'rice', 'Rice', '🍚', 'food', 1, '', 0, '', '', 19),
(21, 'apple', 'Apple', '🍎', 'food', 1, '', 0, '', '', 20),
(22, 'tea', 'Tea', '☕', 'food', 2, '', 0, '', '', 21),
(23, 'hungry', 'Hungry', '😋', 'food', 2, '', 0, '', '', 22),
(24, 'eat', 'Eat', '🍴', 'food', 1, '', 0, '', '', 23),
(25, 'one', 'One', '1️⃣', 'numbers', 1, '', 0, '', '', 24),
(26, 'two', 'Two', '2️⃣', 'numbers', 1, '', 0, '', '', 25),
(27, 'three', 'Three', '3️⃣', 'numbers', 1, '', 0, '', '', 26),
(28, 'four', 'Four', '4️⃣', 'numbers', 1, '', 0, '', '', 27),
(29, 'five', 'Five', '5️⃣', 'numbers', 1, '', 0, '', '', 28),
(30, 'ten', 'Ten', '🔟', 'numbers', 1, '', 0, '', '', 29),
(31, 'hundred', 'Hundred', '💯', 'numbers', 2, '', 0, '', '', 30),
(32, 'zero', 'Zero', '0️⃣', 'numbers', 1, '', 0, '', '', 31),
(33, 'letter-a', 'Letter A', '🅰️', 'alphabet', 1, '', 0, '', '', 32),
(34, 'letter-b', 'Letter B', '🅱️', 'alphabet', 1, '', 0, '', '', 33),
(35, 'letter-c', 'Letter C', '©️', 'alphabet', 1, '', 0, '', '', 34),
(36, 'letter-d', 'Letter D', '🇩', 'alphabet', 1, '', 0, '', '', 35),
(37, 'letter-e', 'Letter E', '📧', 'alphabet', 1, '', 0, '', '', 36),
(38, 'fingerspelling', 'Fingerspelling A-Z', '🔤', 'alphabet', 2, '', 0, '', '', 37),
(39, 'happy', 'Happy', '😊', 'emotions', 1, '', 0, '', '', 38),
(40, 'sad', 'Sad', '😢', 'emotions', 1, '', 0, '', '', 39),
(41, 'angry', 'Angry', '😠', 'emotions', 2, '', 0, '', '', 40),
(42, 'love', 'Love', '❤️', 'emotions', 1, '', 0, '', '', 41),
(43, 'tired', 'Tired', '😴', 'emotions', 2, '', 0, '', '', 42),
(44, 'afraid', 'Afraid', '😨', 'emotions', 2, '', 0, '', '', 43),
(45, 'today', 'Today', '📅', 'time', 1, '', 0, '', '', 44),
(46, 'tomorrow', 'Tomorrow', '⏭️', 'time', 2, '', 0, '', '', 45),
(47, 'yesterday', 'Yesterday', '⏮️', 'time', 2, '', 0, '', '', 46),
(48, 'now', 'Now', '⏰', 'time', 1, '', 0, '', '', 47),
(49, 'morning', 'Morning', '🌞', 'time', 1, '', 0, '', '', 48),
(50, 'night', 'Night', '🌙', 'time', 1, '', 0, '', '', 49),
(51, 'home', 'Home', '🏠', 'places', 1, '', 0, '', '', 50),
(52, 'school', 'School', '🏫', 'places', 1, '', 0, '', '', 51),
(53, 'hospital', 'Hospital', '🏥', 'places', 2, '', 0, '', '', 52),
(54, 'shop', 'Shop', '🏪', 'places', 2, '', 0, '', '', 53),
(55, 'toilet', 'Toilet', '🚻', 'places', 1, '', 0, '', '', 54),
(56, 'road', 'Road', '🛣️', 'places', 2, '', 0, '', '', 55),
(57, 'go', 'Go', '🚶', 'verbs', 1, '', 0, '', '', 56),
(58, 'come', 'Come', '👉', 'verbs', 1, '', 0, '', '', 57),
(59, 'sit', 'Sit', '🪑', 'verbs', 1, '', 0, '', '', 58),
(60, 'sleep', 'Sleep', '😴', 'verbs', 1, '', 0, '', '', 59),
(61, 'work', 'Work', '💼', 'verbs', 2, '', 0, '', '', 60),
(62, 'play', 'Play', '⚽', 'verbs', 1, '', 0, '', '', 61),
(63, 'read', 'Read', '📖', 'verbs', 2, '', 0, '', '', 62),
(64, 'what', 'What', '❓', 'questions', 1, '', 0, '', '', 63),
(65, 'who', 'Who', '🙋', 'questions', 1, '', 0, '', '', 64),
(66, 'where', 'Where', '📍', 'questions', 1, '', 0, '', '', 65),
(67, 'when', 'When', '🕐', 'questions', 2, '', 0, '', '', 66),
(68, 'why', 'Why', '🤷', 'questions', 2, '', 0, '', '', 67),
(69, 'how', 'How', '🔧', 'questions', 2, '', 0, '', '', 68),
(70, 'help', 'Help', '🆘', 'emergency', 1, '', 0, '', '', 69),
(71, 'doctor', 'Doctor', '👨‍⚕️', 'emergency', 1, '', 0, '', '', 70),
(72, 'police', 'Police', '👮', 'emergency', 2, '', 0, '', '', 71),
(73, 'pain', 'Pain', '🤕', 'emergency', 2, '', 0, '', '', 72),
(74, 'fire', 'Fire', '🔥', 'emergency', 2, '', 0, '', '', 73),
(75, 'danger', 'Danger', '⚠️', 'emergency', 2, '', 0, '', '', 74),
(76, 'red', 'Red', '🔴', 'colors', 1, '', 0, '', '', 75),
(77, 'blue', 'Blue', '🔵', 'colors', 1, '', 0, '', '', 76),
(78, 'green', 'Green', '🟢', 'colors', 1, '', 0, '', '', 77),
(79, 'black', 'Black', '⚫', 'colors', 1, '', 0, '', '', 78),
(80, 'white', 'White', '⚪', 'colors', 1, '', 0, '', '', 79);

-- --------------------------------------------------------

--
-- Table structure for table `isl_tr`
--

CREATE TABLE `isl_tr` (
  `id` int NOT NULL,
  `sign_id` int NOT NULL,
  `lang` varchar(40) NOT NULL,
  `roman` varchar(120) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `lp_blocks`
--

CREATE TABLE `lp_blocks` (
  `user_id` int NOT NULL,
  `blocked_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `lp_friends`
--

CREATE TABLE `lp_friends` (
  `id` int NOT NULL,
  `from_id` int NOT NULL,
  `to_id` int NOT NULL,
  `status` enum('pending','accepted') COLLATE utf8mb4_unicode_ci DEFAULT 'pending',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `lp_friends`
--

INSERT INTO `lp_friends` (`id`, `from_id`, `to_id`, `status`, `created_at`) VALUES
(1, 3, 17, 'accepted', '2026-07-11 08:40:56'),
(2, 15, 18, 'accepted', '2026-07-12 09:10:59'),
(3, 15, 25, 'accepted', '2026-07-14 06:53:18'),
(4, 25, 3, 'pending', '2026-07-14 07:06:35'),
(5, 15, 3, 'pending', '2026-07-14 07:07:24'),
(7, 15, 17, 'pending', '2026-07-14 11:57:58'),
(8, 15, 26, 'accepted', '2026-07-14 14:42:58');

-- --------------------------------------------------------

--
-- Table structure for table `lp_links`
--

CREATE TABLE `lp_links` (
  `user_id` int NOT NULL,
  `telegram_chat_id` bigint NOT NULL,
  `telegram_username` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `linked_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `daily_reminder_time` time DEFAULT '19:00:00',
  `last_daily` date DEFAULT NULL,
  `notif_on` tinyint DEFAULT '1',
  `last_streak_warn` date DEFAULT NULL,
  `last_weekly` date DEFAULT NULL,
  `last_winback` date DEFAULT NULL,
  `notified_xp` int DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `lp_links`
--

INSERT INTO `lp_links` (`user_id`, `telegram_chat_id`, `telegram_username`, `linked_at`, `daily_reminder_time`, `last_daily`, `notif_on`, `last_streak_warn`, `last_weekly`, `last_winback`, `notified_xp`) VALUES
(5, 6446675842, '', '2026-07-14 05:12:46', '19:00:00', '2026-07-14', 1, NULL, NULL, NULL, 250),
(6, 7321246402, 'rrroosh', '2026-07-10 16:49:17', '19:00:00', '2026-07-14', 1, NULL, '2026-07-12', NULL, 100),
(17, 7975505663, 'aarush999x', '2026-07-11 08:38:48', '19:00:00', '2026-07-14', 1, NULL, NULL, NULL, 0),
(21, 8740033234, '', '2026-07-13 04:31:47', '19:00:00', NULL, 0, NULL, NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `lp_link_codes`
--

CREATE TABLE `lp_link_codes` (
  `code` char(6) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int NOT NULL,
  `expires_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `lp_link_codes`
--

INSERT INTO `lp_link_codes` (`code`, `user_id`, `expires_at`) VALUES
('775949', 26, '2026-07-14 16:45:03');

-- --------------------------------------------------------

--
-- Table structure for table `lp_messages`
--

CREATE TABLE `lp_messages` (
  `id` bigint NOT NULL,
  `from_id` int NOT NULL,
  `to_id` int NOT NULL,
  `original_text` text COLLATE utf8mb4_unicode_ci,
  `original_lang` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `translated_text` text COLLATE utf8mb4_unicode_ci,
  `translated_roman` text COLLATE utf8mb4_unicode_ci,
  `english_text` text COLLATE utf8mb4_unicode_ci,
  `flagged` tinyint DEFAULT '0',
  `seen` tinyint DEFAULT '0',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `lp_messages`
--

INSERT INTO `lp_messages` (`id`, `from_id`, `to_id`, `original_text`, `original_lang`, `translated_text`, `translated_roman`, `english_text`, `flagged`, `seen`, `created_at`) VALUES
(1, 17, 3, 'Kya Haal hain', 'Hindi', 'क्या हाल हैं', 'Kya Haal hain', 'How are you', 0, 1, '2026-07-11 08:41:29'),
(2, 3, 17, 'Do will play football with me', 'Hindi', 'क्या तुम मेरे साथ फुटबॉल खेलोगे', 'Kya tum mere saath football kheloge', 'Will you play football with me', 0, 1, '2026-07-11 08:42:00'),
(3, 17, 3, 'Or bhai kyaa hall hai', 'Hindi', 'और भाई क्या हाल है', 'aur bhai kyaa haal hai', 'And brother, how are you', 0, 1, '2026-07-11 08:43:01'),
(4, 3, 17, 'You are fool', 'Hindi', 'तुम मूर्ख हो', 'tum moorkh ho', 'You are a fool', 1, 1, '2026-07-11 08:43:28'),
(5, 3, 17, 'You are fool', 'Hindi', 'तुम मूर्ख हो', 'tum moorkh ho', 'You are a fool', 1, 1, '2026-07-11 08:43:42'),
(6, 3, 17, 'Hello', 'Hindi', 'नमस्ते', 'Namaste', 'Hello', 0, 1, '2026-07-11 08:44:09'),
(7, 17, 3, 'Hlo beta kya kr rha h', 'Hindi', 'ह्लो बेटा क्या कर रहा है', 'Hlo beta kya kar raha hai', 'Hello child, what are you doing', 0, 1, '2026-07-11 08:44:25'),
(8, 17, 3, 'Bhen ki chut❤️', 'Hindi', 'बहेन की चुत', 'bhen ki chut', 'sister\'s vagina', 1, 1, '2026-07-11 08:44:40'),
(9, 18, 15, 'Hi', 'Hindi', 'Hi', 'Hi', 'Hello', 0, 1, '2026-07-12 09:11:18'),
(10, 18, 15, 'Beta', 'Hindi', 'Child', 'Beta', 'Child or son/daughter', 0, 1, '2026-07-12 09:11:29'),
(11, 15, 18, 'i want to tak beer', 'English', 'मैं बियर पीना चाहता हूँ', 'main beer peena chahta hoon', 'I want to drink beer', 0, 1, '2026-07-12 09:11:54'),
(12, 18, 15, 'हाँ सिर बिल्कुल पी लेते है बताओ कौनसी पियो गे', 'Hindi', 'Yes, of course, we will definitely have a drink, tell me which one to have', 'haan sir bilkul pi lete hai batavo kaunsi piyo ge', 'Yes, of course, we will definitely have a drink, tell me which one to have', 0, 1, '2026-07-12 09:12:33'),
(13, 15, 18, 'i want fuck', 'English', 'मैं चोदना चाहता हूँ', 'main chodna chahta hoon', 'I want to fuck', 1, 1, '2026-07-12 09:13:11'),
(14, 15, 18, 'i want a beautiful girl', 'English', 'मैं एक सुंदर लड़की चाहता हूँ', 'main ek sundar ladki chahta hoon', 'I want a beautiful girl', 1, 1, '2026-07-12 09:13:58'),
(15, 15, 18, 'i want to play football', 'English', 'मैं फुटबॉल खेलना चाहता हूँ', 'main football khelna chahta hoon', 'I want to play football', 0, 1, '2026-07-12 09:14:11'),
(16, 18, 15, 'हा सिर खेल लेते है', 'Hindi', 'Ha sir khel lete hain', 'Ha sir khel lete hain', 'Yes sir, I am ready to play', 0, 1, '2026-07-12 09:14:34'),
(17, 25, 15, 'namste si kaise ho', 'Hindi', 'Namaste, how are you?', 'Namaste, si kaise ho', 'Hello, how are you?', 0, 1, '2026-07-14 06:53:50'),
(18, 15, 25, 'You will play game with me language.', 'English', 'तुम मेरे साथ भाषा का खेल खेलोगे', 'Tum mere saath bhaasha ka khel kheloge', 'You will play a language game with me', 0, 1, '2026-07-14 06:54:31'),
(19, 15, 25, 'Hi sir how are you', 'English', 'नमस्ते सर कैसे हैं', 'Namaste sar kaise hain', 'Hello sir, how are you', 0, 1, '2026-07-14 06:55:39'),
(20, 25, 15, 'aaj mere pet mein gas ban rahi hai', 'Hindi', 'Today I have gas in my stomach', 'Aaj mere pet mein gas ban rahi hai', 'I have stomach gas today', 0, 1, '2026-07-14 06:56:02'),
(21, 15, 25, 'Then u should take a medicine !!', 'English', 'तो तुम्हें दवाई लेनी चाहिए !!', 'To tumhe davaai leni chahiye !!', 'Then you should take a medicine !!', 0, 1, '2026-07-14 06:56:31'),
(22, 15, 25, 'Hello what’s upp', 'English', 'नमस्ते क्या हो रहा है', 'Namaste kya ho raha hai', 'Hello what\'s up', 0, 1, '2026-07-14 06:58:52'),
(23, 15, 25, 'Hello how are you', 'English', 'नमस्ते कैसे हो', 'Namaste kaise ho', 'Hello, how are you', 0, 1, '2026-07-14 06:59:42'),
(24, 15, 25, 'Hello , how are u?', 'English', 'नमस्ते, आप कैसे हैं?', 'Namaste, aap kaise hain?', 'Hello, how are you?', 0, 1, '2026-07-14 07:00:55'),
(25, 15, 25, 'Hi how are you', 'English', 'नमस्ते कैसे हो', 'Namaste kaise ho', 'Hello, how are you', 0, 1, '2026-07-14 07:01:50'),
(26, 15, 25, 'Hello', 'English', 'नमस्ते', 'Namaste', 'Hello', 0, 1, '2026-07-14 07:03:40'),
(27, 15, 25, 'How are you', 'English', 'आप कैसे हैं', 'aap kaise hain', 'How are you', 0, 1, '2026-07-14 07:03:54'),
(28, 15, 25, 'Hi', 'English', 'नमस्ते', 'Namaste', 'Hello', 0, 0, '2026-07-14 11:58:08'),
(29, 26, 15, 'Hi', 'English', 'नमस्ते', 'Namaste', 'Hello', 0, 1, '2026-07-14 14:43:19'),
(30, 15, 26, 'tum kaise ho', 'Hindi', 'How are you', 'Tum kaise ho', 'How are you', 0, 1, '2026-07-14 14:43:40'),
(31, 15, 26, 'mein bhaut dukhi hun', 'Hindi', 'मैं बहुत दुखी हूँ', 'Main bahut dukhi hoon', 'I am very sad', 0, 1, '2026-07-14 14:44:01'),
(32, 26, 15, 'Why are you sad', 'English', 'आप क्यों उदास हैं', 'aap kyon udaas hain', 'Why are you sad', 0, 1, '2026-07-14 14:44:30'),
(33, 15, 26, 'i have no money', 'Hindi', 'मेरे पास पैसे नहीं हैं', 'Mere paas paise nahi hain', 'I have no money', 0, 1, '2026-07-14 14:44:54'),
(34, 26, 15, 'No problem I will give you money', 'English', 'कोई समस्या नहीं मैं आपको पैसे दूंगा', 'Koi samasya nahin main aapko paise doonga', 'No problem I will give you money', 0, 1, '2026-07-14 14:45:37');

-- --------------------------------------------------------

--
-- Table structure for table `lp_profiles`
--

CREATE TABLE `lp_profiles` (
  `user_id` int NOT NULL,
  `display_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `mobile_hash` char(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `my_language` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT 'English',
  `learning_language` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT 'English',
  `birth_date` date NOT NULL,
  `is_adult` tinyint DEFAULT '0',
  `searchable_by_mobile` tinyint DEFAULT '0',
  `searchable_by_name` tinyint DEFAULT '1',
  `is_banned` tinyint DEFAULT '0',
  `last_seen` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `lp_profiles`
--

INSERT INTO `lp_profiles` (`user_id`, `display_name`, `username`, `mobile_hash`, `my_language`, `learning_language`, `birth_date`, `is_adult`, `searchable_by_mobile`, `searchable_by_name`, `is_banned`, `last_seen`) VALUES
(3, 'Pankaj', 'pankajiec', '4778882a65db8ea249e3b95262f0bc45c0f33a6ee5e0082b35bc916d15480445', 'Hindi', 'Telugu', '1982-11-12', 1, 1, 1, 0, '2026-07-11 08:51:40'),
(4, 'RUDRA TYAGI', 'rudratyagi21', NULL, 'Slovak', 'Slovak', '2003-03-21', 1, 0, 1, 0, '2026-07-10 17:10:00'),
(9, 'Nimit Arora', 'nimmii17', NULL, 'English', 'English', '2006-04-17', 1, 1, 1, 0, '2026-07-13 07:53:22'),
(12, 'pankaj', 'pankajiec1', NULL, 'Hindi', 'English', '2026-07-10', 0, 1, 1, 0, '2026-07-12 04:40:29'),
(13, 'ABHSIHEK', '_1408', '5e04d4cd850edd90c6f3fb1fb7abd32a7532b967c00b25d7123a8a00b3156cfc', 'English', 'English', '2005-02-01', 1, 1, 1, 0, '2026-07-10 16:00:42'),
(15, 'pankaj kashyap', 'pank', '4778882a65db8ea249e3b95262f0bc45c0f33a6ee5e0082b35bc916d15480445', 'Hindi', 'English', '2004-06-19', 1, 1, 1, 0, '2026-07-14 15:06:44'),
(17, 'Aarush', 'examica', '0d0f79bdf4c3b01980976971ae05b50340f2d64947a57a6bafdb74bf7cf5d569', 'Hindi', 'English', '1981-07-11', 1, 1, 1, 0, '2026-07-11 08:45:14'),
(18, 'Manik Mattoo', 'matto', '500797deca4ff65262140a64194a89d572634594cb03ff8b890dc8ef43286fcc', 'Hindi', 'English', '2000-07-12', 1, 1, 1, 0, '2026-07-12 13:08:33'),
(20, 'Test User', 'testuser123', '7619ee8cea49187f309616e30ecf54be072259b43760f1f550a644945d5572f2', 'English', 'Hindi', '0101-09-19', 1, 0, 1, 0, '2026-07-12 10:29:44'),
(21, 'Sania Saxena', 'ania04', 'a20454961b325cfcfe756379ad235df05081e66d88d965f388e7923d7db9bc30', 'English', 'English', '2006-07-04', 1, 1, 1, 0, '2026-07-13 04:57:17'),
(25, 'PosterWall', 'posterwall', 'cb660ccb88d04593e651575a65b3993889e446ebd7cc666dbd9b65c3bee7687e', 'Hindi', 'English', '2000-02-01', 1, 1, 1, 0, '2026-07-14 07:08:05'),
(26, 'Mahakumbrix Innovation', 'aha', '2a672648304524e685d2b1fe80796ab5d7fa14c62e9a260587dbb430c9679315', 'English', 'Hindi', '2002-07-14', 1, 0, 1, 0, '2026-07-14 16:42:46');

-- --------------------------------------------------------

--
-- Table structure for table `lp_progress`
--

CREATE TABLE `lp_progress` (
  `user_id` int NOT NULL,
  `xp` int DEFAULT '0',
  `streak_days` int DEFAULT '0',
  `last_active` date DEFAULT NULL,
  `target_lang` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `lp_progress`
--

INSERT INTO `lp_progress` (`user_id`, `xp`, `streak_days`, `last_active`, `target_lang`) VALUES
(3, 321, 3, '2026-07-12', 'Telugu'),
(4, 37, 1, '2026-07-10', 'Estonian'),
(5, 480, 5, '2026-07-14', 'Hindi'),
(6, 231, 1, '2026-07-13', 'English'),
(7, 70, 1, '2026-07-10', 'Japanese'),
(8, 45, 1, '2026-07-10', 'English'),
(9, 225, 1, '2026-07-13', 'German'),
(10, 45, 1, '2026-07-10', 'Tamil'),
(11, 20, 1, '2026-07-10', 'Japanese'),
(12, 55, 1, '2026-07-14', 'Hindi'),
(13, 10, 1, '2026-07-10', 'English'),
(14, 250, 1, '2026-07-10', 'Hindi'),
(15, 315, 1, '2026-07-14', 'Bodo'),
(19, 5, 1, '2026-07-12', 'Hindi'),
(20, 25, 1, '2026-07-12', 'Hindi'),
(21, 10, 1, '2026-07-13', 'English'),
(24, 10, 1, '2026-07-13', 'Tamil'),
(25, 45, 1, '2026-07-14', 'Hindi'),
(26, 225, 1, '2026-07-14', 'French');

-- --------------------------------------------------------

--
-- Table structure for table `lp_reminders`
--

CREATE TABLE `lp_reminders` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `title` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `due_date` date NOT NULL,
  `note` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `notify_7d` tinyint DEFAULT '0',
  `notify_1d` tinyint DEFAULT '0',
  `notify_0d` tinyint DEFAULT '0',
  `created_via` enum('chat','bot') COLLATE utf8mb4_unicode_ci DEFAULT 'bot',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `lp_reminders`
--

INSERT INTO `lp_reminders` (`id`, `user_id`, `title`, `due_date`, `note`, `notify_7d`, `notify_1d`, `notify_0d`, `created_via`, `created_at`) VALUES
(1, 3, 'insurance renewal', '2026-07-11', '', 0, 1, 0, 'chat', '2026-07-10 10:28:17'),
(2, 3, 'son\'s birthday', '2026-10-01', '', 0, 0, 0, 'chat', '2026-07-10 10:28:43'),
(3, 3, 'daughter\'s birthday', '2026-07-15', '', 0, 0, 0, 'chat', '2026-07-10 10:30:09'),
(4, 3, 'Birthday', '2026-07-15', '', 0, 0, 0, 'chat', '2026-07-10 10:32:46'),
(5, 3, 'Football game', '2026-07-11', '', 0, 1, 0, 'chat', '2026-07-10 11:50:22'),
(9, 12, 'Birthday', '2026-07-15', '', 0, 1, 0, 'chat', '2026-07-11 08:36:44'),
(10, 5, 'Birthday', '2026-07-15', '', 0, 1, 0, 'chat', '2026-07-14 05:13:41'),
(11, 5, 'son\'s birthday', '2026-10-29', '', 0, 0, 0, 'chat', '2026-07-14 05:14:23');

-- --------------------------------------------------------

--
-- Table structure for table `lp_reports`
--

CREATE TABLE `lp_reports` (
  `id` int NOT NULL,
  `reporter_id` int DEFAULT NULL,
  `reported_id` int DEFAULT NULL,
  `message_id` bigint DEFAULT NULL,
  `reason` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `lp_users`
--

CREATE TABLE `lp_users` (
  `id` int NOT NULL,
  `user_key` char(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `totp_secret` char(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `google_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(120) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `picture` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `lp_users`
--

INSERT INTO `lp_users` (`id`, `user_key`, `totp_secret`, `created_at`, `google_id`, `email`, `picture`) VALUES
(1, '8c0626370dcd0ad594dd537cef496069', 'W7JBHBCIFKRMXSE5', '2026-07-10 10:14:31', NULL, NULL, NULL),
(2, '1c40a07d4b25965c7433fcfe490b4ba6', 'QFHHIZWIJLQNNJFE', '2026-07-10 10:14:32', NULL, NULL, NULL),
(3, '6af0daef8cf64d572ce67ac2de1cf37a', '3ZMMTKUPDLRUWYGU', '2026-07-10 10:14:32', NULL, NULL, NULL),
(4, '974222c49c7b494cd527f309e3f5e81f', 'HGCWYFCFPUUFIGU2', '2026-07-10 12:26:33', NULL, NULL, NULL),
(5, 'c317c0c90fb39098a21a271fb36ea374', '4RB7H67TPORJKTLX', '2026-07-10 12:31:02', NULL, NULL, NULL),
(6, '257755b6a6279932858958064af6bbf4', 'KUGJARD7W5UHXMKF', '2026-07-10 12:57:17', NULL, NULL, NULL),
(7, '84fdc28c7e2997882f77eae15b421aad', 'W5FTY7G3E3U777CZ', '2026-07-10 13:00:41', NULL, NULL, NULL),
(8, '211e6c2d4c236f519dc01d4bb445de4e', 'U52GZZ5SQS5NFZPQ', '2026-07-10 14:33:38', NULL, NULL, NULL),
(9, 'd4ed722402fdaf0ed3b15f2f09b9d1dd', 'WZVXIJ3MVNREIUGF', '2026-07-10 14:41:13', NULL, NULL, NULL),
(10, '103baa98d3c5a7e4039b8b0267324b67', 'JQZOZU5F4TEYJ76Z', '2026-07-10 14:45:02', NULL, NULL, NULL),
(11, 'f6b72239e66f8922dcd6972ef1a39ca7', 'F6LILVY7V42PY5SO', '2026-07-10 15:52:52', NULL, NULL, NULL),
(12, 'b5e19a5245bb6e1fa66d39ccfbc71190', 'EVZQI4GRYVP4B4US', '2026-07-10 15:53:02', NULL, NULL, NULL),
(13, 'de1f859ea85e2e3ae36f88a79e9d0299', 'WIM7DKFHZAWCW2SN', '2026-07-10 15:56:26', NULL, NULL, NULL),
(14, 'd5c056e216b612ef5bb150b6dc764f9e', 'IC5A37KU4BLJYJ3R', '2026-07-10 16:27:25', NULL, NULL, NULL),
(15, 'fcf68f966c183e093380495178a80356', 'BAW5ZIEF5XDMNNWW', '2026-07-10 16:41:48', '100856079255209285266', 'pankajiec@gmail.com', 'https://lh3.googleusercontent.com/a/ACg8ocJhU3_u6d89r9S_Vy3EXvN33G9gDW-oHucWso9TSu22nMtbMGLJ=s96-c'),
(16, '2abc183452958d3d39776221ba44e6b8', 'GRTZSXWGDB5JO2YS', '2026-07-11 07:26:00', NULL, NULL, NULL),
(17, 'b500d86dffe1866df1d4c890b7a3de19', 'IDIOXVPKFFPENC66', '2026-07-11 08:38:19', NULL, NULL, NULL),
(18, '737aeb7892582d1abecff3ed7af6aca9', 'BSOGPQIKEXMFK3VX', '2026-07-12 09:08:45', '104051519497015353793', 'mattoomanik9@gmail.com', 'https://lh3.googleusercontent.com/a/ACg8ocKF07aX54bjX1MjYiyrQKglNmISXz8xzWiJMxBT4j9_SK1UrzY=s96-c'),
(19, 'e238fccbb84e7814d7bad1af89c8a7ed', 'GSC5EFVER7DAT3IP', '2026-07-12 10:04:15', NULL, NULL, NULL),
(20, '0c735dc5622a5f1cb3d6aff8d86ba66b', 'VYPRZMQCCY5MYU4D', '2026-07-12 10:28:47', NULL, NULL, NULL),
(21, 'f84c9bf3ae1680b81431d0211bca8c8e', 'XM4UNCITLGDHITJJ', '2026-07-13 04:29:47', '117834384994087413443', 'saxenasania0235@gmail.com', 'https://lh3.googleusercontent.com/a/ACg8ocLVPyGflDTFJ9XHNw_NqUva4z_KHtc12NvUf9u3etnmQIyDBQ=s96-c'),
(22, '5ebb9052bc7e01c820461d8ea7e5106b', '6ZDZLQ53UL7TTGHA', '2026-07-13 14:25:38', NULL, NULL, NULL),
(23, '910ded0edfa75913e1c4988390c09a06', 'HQT8V0COVYZJMC72', '2026-07-13 14:38:57', NULL, NULL, NULL),
(24, 'fdf2cf5dbd5a997d916abe538000f90a', 'W645AWT5EMJV4WYW', '2026-07-13 16:11:27', NULL, NULL, NULL),
(25, '33bfb2a0281deea852252bdde5b67dbb', 'CRGVQDOMSTM2LLPT', '2026-07-14 06:50:41', '115059355890422644735', 'posterwall.in@gmail.com', 'https://lh3.googleusercontent.com/a/ACg8ocJNPMOAZwYkvTuVmxa0pbEXpLX4F2VxFOLEHjyrVY87186H4_o=s96-c'),
(26, 'ff957032d1d3c48622255a6a2e38517e', 'ECZTAIOOIC7HO5XG', '2026-07-14 14:20:51', '112276026188187107561', 'digimahakumbh@gmail.com', 'https://lh3.googleusercontent.com/a/ACg8ocLpuaKfX6HN-MayPEnoEENoxJME7Jy25DMuhkIs5nCPMTc54LY=s96-c'),
(27, 'ed2cee95b502d09f2f768b8e6cd398db', '4RWM6IZ773XPWZYJ', '2026-07-14 14:41:38', NULL, NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `isl_progress`
--
ALTER TABLE `isl_progress`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `isl_seen`
--
ALTER TABLE `isl_seen`
  ADD PRIMARY KEY (`user_id`,`sign_id`);

--
-- Indexes for table `isl_signs`
--
ALTER TABLE `isl_signs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `slug` (`slug`),
  ADD KEY `idx_cat` (`category`),
  ADD KEY `idx_yt` (`yt_id`);

--
-- Indexes for table `isl_tr`
--
ALTER TABLE `isl_tr`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_sign_lang` (`sign_id`,`lang`),
  ADD KEY `idx_lang` (`lang`);

--
-- Indexes for table `lp_blocks`
--
ALTER TABLE `lp_blocks`
  ADD PRIMARY KEY (`user_id`,`blocked_id`);

--
-- Indexes for table `lp_friends`
--
ALTER TABLE `lp_friends`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq` (`from_id`,`to_id`),
  ADD KEY `idx_to` (`to_id`,`status`);

--
-- Indexes for table `lp_links`
--
ALTER TABLE `lp_links`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `telegram_chat_id` (`telegram_chat_id`);

--
-- Indexes for table `lp_link_codes`
--
ALTER TABLE `lp_link_codes`
  ADD PRIMARY KEY (`code`),
  ADD KEY `idx_exp` (`expires_at`);

--
-- Indexes for table `lp_messages`
--
ALTER TABLE `lp_messages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_in` (`to_id`,`seen`),
  ADD KEY `idx_pair` (`from_id`,`to_id`,`id`);

--
-- Indexes for table `lp_profiles`
--
ALTER TABLE `lp_profiles`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `idx_name` (`display_name`),
  ADD KEY `idx_mob` (`mobile_hash`);

--
-- Indexes for table `lp_progress`
--
ALTER TABLE `lp_progress`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `lp_reminders`
--
ALTER TABLE `lp_reminders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_due` (`due_date`),
  ADD KEY `idx_user` (`user_id`);

--
-- Indexes for table `lp_reports`
--
ALTER TABLE `lp_reports`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `lp_users`
--
ALTER TABLE `lp_users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_key` (`user_key`),
  ADD UNIQUE KEY `google_id` (`google_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `isl_signs`
--
ALTER TABLE `isl_signs`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- AUTO_INCREMENT for table `isl_tr`
--
ALTER TABLE `isl_tr`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `lp_friends`
--
ALTER TABLE `lp_friends`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `lp_messages`
--
ALTER TABLE `lp_messages`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `lp_reminders`
--
ALTER TABLE `lp_reminders`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `lp_reports`
--
ALTER TABLE `lp_reports`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `lp_users`
--
ALTER TABLE `lp_users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;
--
-- Database: `eagle_translate`
--
CREATE DATABASE IF NOT EXISTS `eagle_translate` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `eagle_translate`;

-- --------------------------------------------------------

--
-- Table structure for table `ai_cache`
--

CREATE TABLE `ai_cache` (
  `id` int NOT NULL,
  `cache_key` varchar(64) NOT NULL,
  `feature` varchar(50) NOT NULL,
  `input_text` text NOT NULL,
  `output_text` text NOT NULL,
  `lang_pair` varchar(20) DEFAULT NULL,
  `hit_count` int DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `last_used` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `ai_cache`
--

INSERT INTO `ai_cache` (`id`, `cache_key`, `feature`, `input_text`, `output_text`, `lang_pair`, `hit_count`, `created_at`, `last_used`) VALUES
(1, '50c63e73db41223af321ed59b8e24baa3eb043f81a8342c2817cff725a5a2fbc', 'translate', 'Hi', '6281003911', 'hi-en', 2, '2026-07-03 06:40:15', '2026-07-03 06:44:48'),
(2, '11c3d85cf613e9640275829cff6e0b05468dd7633904a8a85f96251f1957a184', 'translate', 'Mein happy hun', 'Mein happy hun', 'hi-en', 1, '2026-07-03 06:41:26', '2026-07-03 06:41:26'),
(3, 'e10a1b74577bb08ae482c8e4962271b7cdfffbc2a0195ea2bae41acb484c2335', 'translate', 'tum kaise ho', 'group discussion', 'hi-en', 1, '2026-07-03 06:47:03', '2026-07-03 06:47:03'),
(4, '826e134df9d5ab6c19eade99c378093eace42941c854f07f19932144aac059f4', 'translate', 'hello', '', 'hi-en', 1, '2026-07-03 06:48:07', '2026-07-03 06:48:07'),
(5, '8b80ce85cd0a7b7660f9e5db454ff3a3afc49849455507dbf1be89f538f29016', 'translate', 'namaste', 'Hello friends.', 'hi-en', 1, '2026-07-03 06:53:01', '2026-07-03 06:53:01'),
(6, '850dbf34251cf5b492550994d71b7362d447b0a1121725fe5c310ffde9846bf7', 'translate', 'kya haal hain', 'kya haal hain', 'hi-en', 3, '2026-07-03 06:53:27', '2026-07-03 08:08:26'),
(7, 'b6c2a0c82e79fc08b8e9722b3ce73248ad00175afeadea9460ee60f074412b0a', 'translate', 'kya karu ab mein', 'kya karu ab mein', 'hi-en', 1, '2026-07-03 06:53:42', '2026-07-03 06:53:42'),
(8, 'f22d14f40577709ac9b2d748cb767703134c40891e09ae23f724a01ab9861c12', 'translate', 'alvida', 'Alvida', 'hi-en', 1, '2026-07-03 06:54:32', '2026-07-03 06:54:32'),
(9, '13f58f37d05af58b42134af53abbad14c1f0e2c2be8038a2f494ca5f8a635095', 'translate', 'hi how ar you', 'ஹாய் நீங்கள் எப்படி இருக்கிறீர்கள்', 'en-ta', 1, '2026-07-03 06:57:33', '2026-07-03 06:57:33'),
(10, 'a8840dfeffd5c4a47a64bc96c8f69b56a251ac0d7178abb9f6e23d47c2b62680', 'translate', 'hello', 'ஒரு ஒத்திசைவான ஒருங்கிணைந்த வாக்கியத்தை உருவாக்குங்கள்', 'en-ta', 4, '2026-07-03 06:59:53', '2026-07-03 08:41:21'),
(11, '41a34eab917a91a1a186c4bbb2eab4944f7d4cb1d9ed960fbfc6e580b595a9a3', 'translate', 'what are you doing', 'ena panra', 'en-ta', 1, '2026-07-03 07:00:10', '2026-07-03 07:00:10'),
(12, 'b3fafe0e4cf85639834b781c52eaf2996b43c5c664f7df6268fe4909e729d459', 'translate', 'i am fine', 'நல்ல சுஹம்', 'en-ta', 1, '2026-07-03 07:00:36', '2026-07-03 07:00:36'),
(13, 'dc80f26af11db423bd2d87fd12c5ae12126dd26bcfb979a0dcdcc566d244f0c1', 'translate', 'kya haal hain', 'க்யா ஹால் ஹைன்', 'en-ta', 1, '2026-07-03 07:24:22', '2026-07-03 07:24:22'),
(14, 'ec7e62f40a216326884bc3e0af791341f3b6e769e6a3a408b335b94dc3ad0816', 'romanize', 'க்யா ஹால் ஹைன்', 'க்யா ஹால் ஹைன்', 'ta', 1, '2026-07-03 07:24:23', '2026-07-03 07:24:23'),
(15, 'b65755bb778310d756a697df6005255ac76aa2a143955ab43b075bfca1db0980', 'translate', 'kya haal hai bhai', 'kya haal hai bhaiyghgc', 'hi-en', 1, '2026-07-03 07:24:50', '2026-07-03 07:24:50'),
(16, 'ffd4f58aef1c3ccd63a2fece58b06df0e68d517db1f3bc7858d8dd9b1a26a99a', 'translate', 'i want water', 'Ich will Wasser.', 'en-de', 1, '2026-07-03 07:25:38', '2026-07-03 07:25:38'),
(17, '286b19dea326ec3a6457eacfbeb6675c6f4170a597af50dd64b804edd0963915', 'romanize', 'Ich will Wasser.', 'Ich will Wasser.', 'de', 1, '2026-07-03 07:25:39', '2026-07-03 07:25:39'),
(18, '6e4d4f3329c4ec9ad5f8dc478f335eb551321d87bbcc0b7a176b49da5be2f061', 'romanize', 'ஒரு ஒத்திசைவான ஒருங்கிணைந்த வாக்கியத்தை உருவாக்குங்கள்', 'ஒரு ஒத்திசைவான ஒருங்கிணைந்த வாக்கியத்தை உருவாக்குங்கள்', 'ta', 3, '2026-07-03 08:08:55', '2026-07-03 08:41:21'),
(19, '55e9e5dacc443ca14915191627536ea3f8f2b662fdfae328241795c2af960874', 'translate', 'hello', 'INVALID EMAIL PROVIDED', 'en-ru', 2, '2026-07-03 08:09:31', '2026-07-03 08:09:59'),
(20, 'c3baee3f55e992d117ae1abbafa1d3ee828ed961080b1b8869b1eef8f7572c71', 'romanize', 'INVALID EMAIL PROVIDED', 'INVALID EMAIL PROVIDED', 'ru', 2, '2026-07-03 08:09:32', '2026-07-03 08:09:59'),
(21, '5f58c8b0f17df8cd03a21efb18bc7cba0211bea6ccfb8c1f8cfa5b572279a8a6', 'translate', 'Hi', 'வணக்கம்', 'en-ta', 1, '2026-07-03 08:10:55', '2026-07-03 08:10:55'),
(22, '6a90929a42ddd1ad68afe2069e8d8d205870ecaf567929cc2771247336e21436', 'romanize', 'வணக்கம்', 'வணக்கம்', 'ta', 1, '2026-07-03 08:10:55', '2026-07-03 08:10:55'),
(23, '95abcc00d12527b3cf1540794c8a6926ffdbca904d21afb23e165ff5c303e8b3', 'translate', 'What are you doing', 'நன்றிய பெரியப்பா', 'en-ta', 1, '2026-07-03 08:11:15', '2026-07-03 08:11:15'),
(24, '7eec34438ce913e602e8f392829ca0516bb67deed7a310fef9ad4eb4c85d4b95', 'romanize', 'நன்றிய பெரியப்பா', 'நன்றிய பெரியப்பா', 'ta', 1, '2026-07-03 08:11:16', '2026-07-03 08:11:16'),
(25, '8c8a9100533e851d7712bd082733b7f2ffd7d6d0d6fde0d9881750c72d18556a', 'translate', 'Hello how are you', 'QUERY LENGTH LIMIT EXCEDEED. MAX ALLOWED QUERY : 500 CHARS', 'en-ta', 1, '2026-07-03 08:12:09', '2026-07-03 08:12:09'),
(26, '16b46835aa7983929beb70078e813ad3eccad9af67ebc0f44a8fc584f7361f1c', 'romanize', 'QUERY LENGTH LIMIT EXCEDEED. MAX ALLOWED QUERY : 500 CHARS', 'QUERY LENGTH LIMIT EXCEDEED. MAX ALLOWED QUERY : 500 CHARS', 'ta', 1, '2026-07-03 08:12:09', '2026-07-03 08:12:09'),
(27, '96f585d6aae423b7f51180a236fe5c58e171d7e8213d484f3081ad41540f32f4', 'translate', 'How are you', 'enna solare', 'en-ta', 1, '2026-07-03 08:12:35', '2026-07-03 08:12:35'),
(28, '11435d71115e50369a468c4ef8c017586ae4221c6adf5dc6b558155bb5f7e126', 'romanize', 'enna solare', 'enna solare', 'ta', 1, '2026-07-03 08:12:35', '2026-07-03 08:12:35'),
(29, 'b366e080b4dd6d1deba98d3f60d4f3deff4a9680d0e045a1eb003017b843dd71', 'translate', 'I am fine', '', 'en-ta', 1, '2026-07-03 08:12:52', '2026-07-03 08:12:52'),
(30, '2bc218049f6c790b1bda4f16cd086e583a5689e40f13e9a86cb9f9e8c153e275', 'translate', 'hi', 'INVALID EMAIL PROVIDED', 'en-ta', 3, '2026-07-03 08:23:03', '2026-07-03 08:40:11'),
(31, 'e5be5578e662f3da2d17102db7971780c8ad016a40879a368de9150bee6e26dd', 'romanize', 'INVALID EMAIL PROVIDED', 'INVALID EMAIL PROVIDED', 'ta', 3, '2026-07-03 08:23:04', '2026-07-03 08:40:11'),
(32, 'b9b6da388d690fc4e91e8781605f884a966327d1a516da298bad2b38bd5ca805', 'translate', 'good', 'வேட்பேடு', 'en-ta', 1, '2026-07-03 08:40:32', '2026-07-03 08:40:32'),
(33, '620de7223d3888dc80b538b725d0eb83ad7d321a007d9a36568e9d1145cff576', 'romanize', 'வேட்பேடு', 'வேட்பேடு', 'ta', 1, '2026-07-03 08:40:33', '2026-07-03 08:40:33'),
(34, '3a95baabcc0ddaa44329735e2e124a58254e9120920a4df468cc2776e393381d', 'translate', 'hi', 'hi', 'en-hi', 1, '2026-07-03 08:41:52', '2026-07-03 08:41:52'),
(35, '9f3acb5fe7a0f26a62bfdcfa2fc4625918194fd1ef04fc0406774c1b707050d3', 'romanize', 'hi', 'hi', 'hi', 1, '2026-07-03 08:41:52', '2026-07-03 08:41:52'),
(36, '9e90f4a04de159e1de1f2505eb187c2a8f5b7b70e355c79eaa2d97196782caaa', 'translate', 'kaise ho', 'kaise ho', 'hi-en', 1, '2026-07-03 08:42:08', '2026-07-03 08:42:08'),
(37, 'bfe6b0dfba80483a44f24b431bcbf50c738aca204827017c8b5aeb999d5578c8', 'translate', 'kaise ho', 'కైస్ హో', 'en-te', 1, '2026-07-03 08:42:23', '2026-07-03 08:42:23'),
(38, '7e0cce3692fec98433c2d385289f34965bb611fee8796edac6179dc7e2a3a646', 'romanize', 'కైస్ హో', 'కైస్ హో', 'te', 1, '2026-07-03 08:42:24', '2026-07-03 08:42:24'),
(39, 'bec53f8ce9f75b932ce0e8271f89610f89d2d98812eb2027b263c1447100e7ad', 'translate', 'hello', 'హలో', 'en-te', 1, '2026-07-03 08:44:14', '2026-07-03 08:44:14'),
(40, 'b72e645e71dfeea3792e6dcb192672b2692cbbf3b5fe417f73d956ba62f9b938', 'romanize', 'హలో', 'హలో', 'te', 1, '2026-07-03 08:44:14', '2026-07-03 08:44:14'),
(41, '86b5b5086428770ce28269e16b422f12621391f430b23a7e53a8ce8dcaa5ae1c', 'translate', 'milk', '', 'en-te', 2, '2026-07-03 08:44:38', '2026-07-03 08:45:10'),
(42, '2386d5dbd50e7a1874711d0e39100aeafed09d4a3c8d0d48ac0f12f3ef65b87b', 'translate', 'water', 'chittappa', 'en-te', 1, '2026-07-03 08:44:51', '2026-07-03 08:44:51'),
(43, '38c7a3fb85bba81b1a23a9b45bb3f35025987056c96c98ecf950c80328befc28', 'romanize', 'chittappa', 'chittappa', 'te', 1, '2026-07-03 08:44:51', '2026-07-03 08:44:51'),
(44, 'fa66ef009716654f96e151855d4c1189b12fd93ab436952b24d639150c5ae7d1', 'translate', 'I am fine', 'I am fine', 'en-bn', 1, '2026-07-03 19:46:31', '2026-07-03 19:46:31'),
(45, 'f743f090a1fb281421cdea88aafc41a44f11c4f20d62331f352a29a9bb6dab51', 'romanize', 'I am fine', 'I am fine', 'bn', 1, '2026-07-03 19:46:31', '2026-07-03 19:46:31'),
(46, 'e8587bbcd7c5b283c7ad74abc753e80fdf395b1a9116e5e43926126f86a4f061', 'translate', 'Hello', 'হ্যালো', 'en-bn', 1, '2026-07-03 19:46:42', '2026-07-03 19:46:42'),
(47, 'aee99c16183c638985d907a5e2e6ddca6b750c1373a18109f54201edd753f091', 'romanize', 'হ্যালো', 'হ্যালো', 'bn', 1, '2026-07-03 19:46:42', '2026-07-03 19:46:42'),
(48, 'e2a8cf600cbe5a09e4777228e620a4d7778da7d0e8c8b3cc065db3818637183b', 'translate', 'Water', 'পানি', 'en-bn', 1, '2026-07-03 19:46:55', '2026-07-03 19:46:55'),
(49, 'ed0c4ae6fc389c375b8a4dae646d4452dbf340678f8e5a5f843584a61c8dc692', 'romanize', 'পানি', 'পানি', 'bn', 1, '2026-07-03 19:46:55', '2026-07-03 19:46:55'),
(50, 'd6e66bbba68eac0c7fc5ad9ddb742063d13109467b14acb354e50d0e95089dbe', 'translate', 'Milk', 'দুধরং', 'en-bn', 1, '2026-07-03 19:47:12', '2026-07-03 19:47:12'),
(51, 'f1a37a8f6fe3a168cc2c5feed50c6f42ba4bbe483f80a41974984bc23b02317d', 'romanize', 'দুধরং', 'দুধরং', 'bn', 1, '2026-07-03 19:47:12', '2026-07-03 19:47:12'),
(52, 'ce1a3b13637d17b11059141d5bc6a8895049fdd177eb0ef3e78d735b851c7585', 'translate', 'https://meet.google.com/sgd-fqsh-aun', 'https://meet.google.com/sgd-fqsh-aun', 'en-bn', 1, '2026-07-04 09:34:14', '2026-07-04 09:34:14'),
(53, '0c1f8af7699a2e180b4e3832bf9e9a0d089b8b814c8d37494b0482d35bae33c5', 'romanize', 'https://meet.google.com/sgd-fqsh-aun', 'https://meet.google.com/sgd-fqsh-aun', 'bn', 1, '2026-07-04 09:34:15', '2026-07-04 09:34:15');

-- --------------------------------------------------------

--
-- Table structure for table `auth_users`
--

CREATE TABLE `auth_users` (
  `id` int NOT NULL,
  `telegram_user_id` bigint DEFAULT NULL,
  `mobile` varchar(15) NOT NULL,
  `secret_seed` varchar(64) NOT NULL,
  `verified` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `auth_users`
--

INSERT INTO `auth_users` (`id`, `telegram_user_id`, `mobile`, `secret_seed`, `verified`, `created_at`) VALUES
(1, 6446675842, '9540739137', 'OYG6LJTAWM2AV3FOKLOCESUXQTXVBC4T', 1, '2026-07-03 06:40:00'),
(2, 6213295870, '9311728189', '4QJ647DDSANGCJCQED45K2LZ6KO6M3WR', 1, '2026-07-04 02:17:08');

-- --------------------------------------------------------

--
-- Table structure for table `badges`
--

CREATE TABLE `badges` (
  `id` int NOT NULL,
  `code` varchar(50) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `icon` varchar(20) DEFAULT 0xF09F8F85
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `badges`
--

INSERT INTO `badges` (`id`, `code`, `title`, `description`, `icon`) VALUES
(1, 'first_translate', 'First Flight', 'Completed your first translation', '🦅'),
(2, 'streak_7', 'Week Warrior', '7-day streak', '🔥'),
(3, 'streak_30', 'Month Master', '30-day streak', '⚡'),
(4, 'translations_100', 'Century Club', '100 translations done', '💯'),
(5, 'night_owl', 'Night Owl', 'Translated after midnight', '🦉');

-- --------------------------------------------------------

--
-- Table structure for table `translations`
--

CREATE TABLE `translations` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `input_type` enum('text','voice','image') DEFAULT 'text',
  `source_text` text NOT NULL,
  `translated_text` text NOT NULL,
  `source_lang` varchar(10) DEFAULT NULL,
  `target_lang` varchar(10) DEFAULT NULL,
  `confidence_note` varchar(50) DEFAULT NULL,
  `was_cached` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `translations`
--

INSERT INTO `translations` (`id`, `user_id`, `input_type`, `source_text`, `translated_text`, `source_lang`, `target_lang`, `confidence_note`, `was_cached`, `created_at`) VALUES
(1, 1, 'text', 'Hi', '6281003911', 'hi', 'en', NULL, 0, '2026-07-03 06:40:15'),
(2, 1, 'text', 'Mein happy hun', 'Mein happy hun', 'hi', 'en', NULL, 0, '2026-07-03 06:41:26'),
(3, 1, 'text', 'Hi', '6281003911', 'hi', 'en', NULL, 1, '2026-07-03 06:44:48'),
(4, 1, 'text', 'tum kaise ho', 'group discussion', 'hi', 'en', NULL, 0, '2026-07-03 06:47:03'),
(5, 1, 'text', 'hello', '', 'hi', 'en', NULL, 0, '2026-07-03 06:48:07'),
(6, 1, 'text', 'namaste', 'Hello friends.', 'hi', 'en', NULL, 0, '2026-07-03 06:53:01'),
(7, 1, 'text', 'kya haal hain', 'kya haal hain', 'hi', 'en', NULL, 0, '2026-07-03 06:53:27'),
(8, 1, 'text', 'kya karu ab mein', 'kya karu ab mein', 'hi', 'en', NULL, 0, '2026-07-03 06:53:42'),
(9, 1, 'text', 'kya haal hain', 'kya haal hain', 'hi', 'en', NULL, 1, '2026-07-03 06:54:15'),
(10, 1, 'text', 'alvida', 'Alvida', 'hi', 'en', NULL, 0, '2026-07-03 06:54:32'),
(11, 1, 'text', 'hi how ar you', 'ஹாய் நீங்கள் எப்படி இருக்கிறீர்கள்', 'en', 'ta', NULL, 0, '2026-07-03 06:57:33'),
(12, 1, 'text', 'hello', 'ஒரு ஒத்திசைவான ஒருங்கிணைந்த வாக்கியத்தை உருவாக்குங்கள்', 'en', 'ta', NULL, 0, '2026-07-03 06:59:53'),
(13, 1, 'text', 'what are you doing', 'ena panra', 'en', 'ta', NULL, 0, '2026-07-03 07:00:10'),
(14, 1, 'text', 'i am fine', 'நல்ல சுஹம்', 'en', 'ta', NULL, 0, '2026-07-03 07:00:36'),
(15, 1, 'text', 'kya haal hain', 'க்யா ஹால் ஹைன்', 'en', 'ta', NULL, 0, '2026-07-03 07:24:22'),
(16, 1, 'text', 'kya haal hai bhai', 'kya haal hai bhaiyghgc', 'hi', 'en', NULL, 0, '2026-07-03 07:24:50'),
(17, 1, 'text', 'i want water', 'Ich will Wasser.', 'en', 'de', NULL, 0, '2026-07-03 07:25:38'),
(18, 1, 'text', 'kya haal hain', 'kya haal hain', 'hi', 'en', NULL, 1, '2026-07-03 08:08:26'),
(19, 1, 'text', 'hello', 'ஒரு ஒத்திசைவான ஒருங்கிணைந்த வாக்கியத்தை உருவாக்குங்கள்', 'en', 'ta', NULL, 1, '2026-07-03 08:08:55'),
(20, 1, 'text', 'hello', 'INVALID EMAIL PROVIDED', 'en', 'ru', NULL, 0, '2026-07-03 08:09:31'),
(21, 1, 'text', 'hello', 'INVALID EMAIL PROVIDED', 'en', 'ru', NULL, 1, '2026-07-03 08:09:59'),
(22, 1, 'text', 'Hi', 'வணக்கம்', 'en', 'ta', NULL, 0, '2026-07-03 08:10:55'),
(23, 1, 'text', 'What are you doing', 'நன்றிய பெரியப்பா', 'en', 'ta', NULL, 0, '2026-07-03 08:11:15'),
(24, 1, 'text', 'Hello how are you', 'QUERY LENGTH LIMIT EXCEDEED. MAX ALLOWED QUERY : 500 CHARS', 'en', 'ta', NULL, 0, '2026-07-03 08:12:09'),
(25, 1, 'text', 'How are you', 'enna solare', 'en', 'ta', NULL, 0, '2026-07-03 08:12:35'),
(26, 1, 'text', 'I am fine', '', 'en', 'ta', NULL, 0, '2026-07-03 08:12:52'),
(27, 1, 'text', 'hi', 'INVALID EMAIL PROVIDED', 'en', 'ta', NULL, 0, '2026-07-03 08:23:03'),
(28, 1, 'text', 'hi', 'INVALID EMAIL PROVIDED', 'en', 'ta', NULL, 1, '2026-07-03 08:23:42'),
(29, 1, 'text', 'hi', 'INVALID EMAIL PROVIDED', 'en', 'ta', NULL, 1, '2026-07-03 08:40:11'),
(30, 1, 'text', 'good', 'வேட்பேடு', 'en', 'ta', NULL, 0, '2026-07-03 08:40:32'),
(31, 1, 'text', 'hello', 'ஒரு ஒத்திசைவான ஒருங்கிணைந்த வாக்கியத்தை உருவாக்குங்கள்', 'en', 'ta', NULL, 1, '2026-07-03 08:41:11'),
(32, 1, 'text', 'hello', 'ஒரு ஒத்திசைவான ஒருங்கிணைந்த வாக்கியத்தை உருவாக்குங்கள்', 'en', 'ta', NULL, 1, '2026-07-03 08:41:21'),
(33, 1, 'text', 'hi', 'hi', 'en', 'hi', NULL, 0, '2026-07-03 08:41:52'),
(34, 1, 'text', 'kaise ho', 'kaise ho', 'hi', 'en', NULL, 0, '2026-07-03 08:42:08'),
(35, 1, 'text', 'kaise ho', 'కైస్ హో', 'en', 'te', NULL, 0, '2026-07-03 08:42:23'),
(36, 1, 'text', 'hello', 'హలో', 'en', 'te', NULL, 0, '2026-07-03 08:44:14'),
(37, 1, 'text', 'milk', '', 'en', 'te', NULL, 0, '2026-07-03 08:44:38'),
(38, 1, 'text', 'water', 'chittappa', 'en', 'te', NULL, 0, '2026-07-03 08:44:51'),
(39, 1, 'text', 'milk', '', 'en', 'te', NULL, 1, '2026-07-03 08:45:10'),
(40, 1, 'text', 'I am fine', 'I am fine', 'en', 'bn', NULL, 0, '2026-07-03 19:46:31'),
(41, 1, 'text', 'Hello', 'হ্যালো', 'en', 'bn', NULL, 0, '2026-07-03 19:46:42'),
(42, 1, 'text', 'Water', 'পানি', 'en', 'bn', NULL, 0, '2026-07-03 19:46:55'),
(43, 1, 'text', 'Milk', 'দুধরং', 'en', 'bn', NULL, 0, '2026-07-03 19:47:12'),
(44, 1, 'text', 'https://meet.google.com/sgd-fqsh-aun', 'https://meet.google.com/sgd-fqsh-aun', 'en', 'bn', NULL, 0, '2026-07-04 09:34:14');

-- --------------------------------------------------------

--
-- Table structure for table `user_badges`
--

CREATE TABLE `user_badges` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `badge_id` int NOT NULL,
  `earned_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user_badges`
--

INSERT INTO `user_badges` (`id`, `user_id`, `badge_id`, `earned_at`) VALUES
(1, 1, 1, '2026-07-03 06:40:15');

-- --------------------------------------------------------

--
-- Table structure for table `user_lang_prefs`
--

CREATE TABLE `user_lang_prefs` (
  `user_id` int NOT NULL,
  `native_lang` varchar(10) NOT NULL DEFAULT 'hi',
  `destination_lang` varchar(10) NOT NULL DEFAULT 'en',
  `notifications_enabled` tinyint(1) DEFAULT '1',
  `notif_streak` tinyint(1) DEFAULT '1',
  `notif_nudges` tinyint(1) DEFAULT '1',
  `notif_achievements` tinyint(1) DEFAULT '1',
  `notif_digest` tinyint(1) DEFAULT '1',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user_lang_prefs`
--

INSERT INTO `user_lang_prefs` (`user_id`, `native_lang`, `destination_lang`, `notifications_enabled`, `notif_streak`, `notif_nudges`, `notif_achievements`, `notif_digest`, `updated_at`) VALUES
(1, 'en', 'bn', 1, 1, 1, 1, 1, '2026-07-03 19:46:11'),
(2, 'hi', 'en', 1, 1, 1, 1, 1, '2026-07-04 02:17:08');

-- --------------------------------------------------------

--
-- Table structure for table `user_stats`
--

CREATE TABLE `user_stats` (
  `user_id` int NOT NULL,
  `xp` int DEFAULT '0',
  `current_streak` int DEFAULT '0',
  `longest_streak` int DEFAULT '0',
  `last_active_date` date DEFAULT NULL,
  `daily_quota_used` int DEFAULT '0',
  `daily_quota_limit` int DEFAULT '200',
  `quota_reset_date` date DEFAULT NULL,
  `fluency_score` decimal(5,2) DEFAULT '0.00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user_stats`
--

INSERT INTO `user_stats` (`user_id`, `xp`, `current_streak`, `longest_streak`, `last_active_date`, `daily_quota_used`, `daily_quota_limit`, `quota_reset_date`, `fluency_score`) VALUES
(1, 440, 2, 2, '2026-07-04', 1, 200, '2026-07-04', 0.00),
(2, 0, 0, 0, NULL, 0, 200, '2026-07-04', 0.00);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ai_cache`
--
ALTER TABLE `ai_cache`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cache_key` (`cache_key`),
  ADD KEY `idx_feature` (`feature`),
  ADD KEY `idx_lang` (`lang_pair`);

--
-- Indexes for table `auth_users`
--
ALTER TABLE `auth_users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `mobile` (`mobile`),
  ADD UNIQUE KEY `telegram_user_id` (`telegram_user_id`);

--
-- Indexes for table `badges`
--
ALTER TABLE `badges`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code` (`code`);

--
-- Indexes for table `translations`
--
ALTER TABLE `translations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_user_time` (`user_id`,`created_at`);

--
-- Indexes for table `user_badges`
--
ALTER TABLE `user_badges`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_user_badge` (`user_id`,`badge_id`),
  ADD KEY `badge_id` (`badge_id`);

--
-- Indexes for table `user_lang_prefs`
--
ALTER TABLE `user_lang_prefs`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `user_stats`
--
ALTER TABLE `user_stats`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ai_cache`
--
ALTER TABLE `ai_cache`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT for table `auth_users`
--
ALTER TABLE `auth_users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `badges`
--
ALTER TABLE `badges`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `translations`
--
ALTER TABLE `translations`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `user_badges`
--
ALTER TABLE `user_badges`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `translations`
--
ALTER TABLE `translations`
  ADD CONSTRAINT `translations_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `auth_users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `user_badges`
--
ALTER TABLE `user_badges`
  ADD CONSTRAINT `user_badges_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `auth_users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_badges_ibfk_2` FOREIGN KEY (`badge_id`) REFERENCES `badges` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `user_lang_prefs`
--
ALTER TABLE `user_lang_prefs`
  ADD CONSTRAINT `user_lang_prefs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `auth_users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `user_stats`
--
ALTER TABLE `user_stats`
  ADD CONSTRAINT `user_stats_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `auth_users` (`id`) ON DELETE CASCADE;
--
-- Database: `phpmyadmin`
--
CREATE DATABASE IF NOT EXISTS `phpmyadmin` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `phpmyadmin`;

-- --------------------------------------------------------

--
-- Table structure for table `pma__bookmark`
--

CREATE TABLE `pma__bookmark` (
  `id` int UNSIGNED NOT NULL,
  `dbase` varchar(255) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user` varchar(255) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `label` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '',
  `query` text COLLATE utf8mb3_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Bookmarks';

-- --------------------------------------------------------

--
-- Table structure for table `pma__central_columns`
--

CREATE TABLE `pma__central_columns` (
  `db_name` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `col_name` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `col_type` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `col_length` text COLLATE utf8mb3_bin,
  `col_collation` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `col_isNull` tinyint(1) NOT NULL,
  `col_extra` varchar(255) COLLATE utf8mb3_bin DEFAULT '',
  `col_default` text COLLATE utf8mb3_bin
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Central list of columns';

-- --------------------------------------------------------

--
-- Table structure for table `pma__column_info`
--

CREATE TABLE `pma__column_info` (
  `id` int UNSIGNED NOT NULL,
  `db_name` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `table_name` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `column_name` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `comment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '',
  `mimetype` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '',
  `transformation` varchar(255) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `transformation_options` varchar(255) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `input_transformation` varchar(255) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `input_transformation_options` varchar(255) COLLATE utf8mb3_bin NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Column information for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__designer_settings`
--

CREATE TABLE `pma__designer_settings` (
  `username` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `settings_data` text COLLATE utf8mb3_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Settings related to Designer';

-- --------------------------------------------------------

--
-- Table structure for table `pma__export_templates`
--

CREATE TABLE `pma__export_templates` (
  `id` int UNSIGNED NOT NULL,
  `username` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `export_type` varchar(10) COLLATE utf8mb3_bin NOT NULL,
  `template_name` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `template_data` text COLLATE utf8mb3_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Saved export templates';

-- --------------------------------------------------------

--
-- Table structure for table `pma__favorite`
--

CREATE TABLE `pma__favorite` (
  `username` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `tables` text COLLATE utf8mb3_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Favorite tables';

-- --------------------------------------------------------

--
-- Table structure for table `pma__history`
--

CREATE TABLE `pma__history` (
  `id` bigint UNSIGNED NOT NULL,
  `username` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `db` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `table` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `timevalue` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sqlquery` text COLLATE utf8mb3_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='SQL history for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__navigationhiding`
--

CREATE TABLE `pma__navigationhiding` (
  `username` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `item_name` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `item_type` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `db_name` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `table_name` varchar(64) COLLATE utf8mb3_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Hidden items of navigation tree';

-- --------------------------------------------------------

--
-- Table structure for table `pma__pdf_pages`
--

CREATE TABLE `pma__pdf_pages` (
  `db_name` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `page_nr` int UNSIGNED NOT NULL,
  `page_descr` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='PDF relation pages for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__recent`
--

CREATE TABLE `pma__recent` (
  `username` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `tables` text COLLATE utf8mb3_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Recently accessed tables';

-- --------------------------------------------------------

--
-- Table structure for table `pma__relation`
--

CREATE TABLE `pma__relation` (
  `master_db` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `master_table` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `master_field` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `foreign_db` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `foreign_table` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `foreign_field` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Relation table';

-- --------------------------------------------------------

--
-- Table structure for table `pma__savedsearches`
--

CREATE TABLE `pma__savedsearches` (
  `id` int UNSIGNED NOT NULL,
  `username` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `db_name` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `search_name` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `search_data` text COLLATE utf8mb3_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Saved searches';

-- --------------------------------------------------------

--
-- Table structure for table `pma__table_coords`
--

CREATE TABLE `pma__table_coords` (
  `db_name` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `table_name` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `pdf_page_number` int NOT NULL DEFAULT '0',
  `x` float UNSIGNED NOT NULL DEFAULT '0',
  `y` float UNSIGNED NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table coordinates for phpMyAdmin PDF output';

-- --------------------------------------------------------

--
-- Table structure for table `pma__table_info`
--

CREATE TABLE `pma__table_info` (
  `db_name` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `table_name` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `display_field` varchar(64) COLLATE utf8mb3_bin NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Table information for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__table_uiprefs`
--

CREATE TABLE `pma__table_uiprefs` (
  `username` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `db_name` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `table_name` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `prefs` text COLLATE utf8mb3_bin NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Tables'' UI preferences';

-- --------------------------------------------------------

--
-- Table structure for table `pma__tracking`
--

CREATE TABLE `pma__tracking` (
  `db_name` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `table_name` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `version` int UNSIGNED NOT NULL,
  `date_created` datetime NOT NULL,
  `date_updated` datetime NOT NULL,
  `schema_snapshot` text COLLATE utf8mb3_bin NOT NULL,
  `schema_sql` text COLLATE utf8mb3_bin,
  `data_sql` longtext COLLATE utf8mb3_bin,
  `tracking` set('UPDATE','REPLACE','INSERT','DELETE','TRUNCATE','CREATE DATABASE','ALTER DATABASE','DROP DATABASE','CREATE TABLE','ALTER TABLE','RENAME TABLE','DROP TABLE','CREATE INDEX','DROP INDEX','CREATE VIEW','ALTER VIEW','DROP VIEW') COLLATE utf8mb3_bin DEFAULT NULL,
  `tracking_active` int UNSIGNED NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Database changes tracking for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__userconfig`
--

CREATE TABLE `pma__userconfig` (
  `username` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `timevalue` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `config_data` text COLLATE utf8mb3_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='User preferences storage for phpMyAdmin';

--
-- Dumping data for table `pma__userconfig`
--

INSERT INTO `pma__userconfig` (`username`, `timevalue`, `config_data`) VALUES
('admin', '2026-07-14 17:16:44', '{\"Console\\/Mode\":\"collapse\"}');

-- --------------------------------------------------------

--
-- Table structure for table `pma__usergroups`
--

CREATE TABLE `pma__usergroups` (
  `usergroup` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `tab` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `allowed` enum('Y','N') COLLATE utf8mb3_bin NOT NULL DEFAULT 'N'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='User groups with configured menu items';

-- --------------------------------------------------------

--
-- Table structure for table `pma__users`
--

CREATE TABLE `pma__users` (
  `username` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `usergroup` varchar(64) COLLATE utf8mb3_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='Users and their assignments to user groups';

--
-- Indexes for dumped tables
--

--
-- Indexes for table `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pma__central_columns`
--
ALTER TABLE `pma__central_columns`
  ADD PRIMARY KEY (`db_name`,`col_name`);

--
-- Indexes for table `pma__column_info`
--
ALTER TABLE `pma__column_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `db_name` (`db_name`,`table_name`,`column_name`);

--
-- Indexes for table `pma__designer_settings`
--
ALTER TABLE `pma__designer_settings`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_user_type_template` (`username`,`export_type`,`template_name`);

--
-- Indexes for table `pma__favorite`
--
ALTER TABLE `pma__favorite`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__history`
--
ALTER TABLE `pma__history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username` (`username`,`db`,`table`,`timevalue`);

--
-- Indexes for table `pma__navigationhiding`
--
ALTER TABLE `pma__navigationhiding`
  ADD PRIMARY KEY (`username`,`item_name`,`item_type`,`db_name`,`table_name`);

--
-- Indexes for table `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  ADD PRIMARY KEY (`page_nr`),
  ADD KEY `db_name` (`db_name`);

--
-- Indexes for table `pma__recent`
--
ALTER TABLE `pma__recent`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__relation`
--
ALTER TABLE `pma__relation`
  ADD PRIMARY KEY (`master_db`,`master_table`,`master_field`),
  ADD KEY `foreign_field` (`foreign_db`,`foreign_table`);

--
-- Indexes for table `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_savedsearches_username_dbname` (`username`,`db_name`,`search_name`);

--
-- Indexes for table `pma__table_coords`
--
ALTER TABLE `pma__table_coords`
  ADD PRIMARY KEY (`db_name`,`table_name`,`pdf_page_number`);

--
-- Indexes for table `pma__table_info`
--
ALTER TABLE `pma__table_info`
  ADD PRIMARY KEY (`db_name`,`table_name`);

--
-- Indexes for table `pma__table_uiprefs`
--
ALTER TABLE `pma__table_uiprefs`
  ADD PRIMARY KEY (`username`,`db_name`,`table_name`);

--
-- Indexes for table `pma__tracking`
--
ALTER TABLE `pma__tracking`
  ADD PRIMARY KEY (`db_name`,`table_name`,`version`);

--
-- Indexes for table `pma__userconfig`
--
ALTER TABLE `pma__userconfig`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__usergroups`
--
ALTER TABLE `pma__usergroups`
  ADD PRIMARY KEY (`usergroup`,`tab`,`allowed`);

--
-- Indexes for table `pma__users`
--
ALTER TABLE `pma__users`
  ADD PRIMARY KEY (`username`,`usergroup`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__column_info`
--
ALTER TABLE `pma__column_info`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__history`
--
ALTER TABLE `pma__history`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  MODIFY `page_nr` int UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
