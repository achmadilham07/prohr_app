-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 03 Apr 2018 pada 05.12
-- Versi Server: 10.1.10-MariaDB
-- PHP Version: 5.5.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `urbancar_test`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `absensi`
--

CREATE TABLE `absensi` (
  `no` int(4) NOT NULL,
  `id_beacon` varchar(10) NOT NULL,
  `year` smallint(4) NOT NULL,
  `month` varchar(12) NOT NULL,
  `date` tinyint(2) NOT NULL,
  `in_time` time NOT NULL,
  `out_time` time NOT NULL,
  `status` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `absensi`
--

INSERT INTO `absensi` (`no`, `id_beacon`, `year`, `month`, `date`, `in_time`, `out_time`, `status`) VALUES
(1, 'BC9801', 2017, 'January', 14, '07:32:43', '16:24:45', ''),
(2, 'BC9801', 2017, 'January', 15, '08:32:43', '17:24:45', ''),
(3, 'BC9801', 2017, 'January', 16, '07:32:43', '16:24:45', ''),
(4, 'BC9801', 2017, 'January', 17, '08:32:43', '17:24:45', ''),
(5, 'BC9801', 2016, 'December', 14, '07:32:43', '16:24:45', ''),
(6, 'BC9801', 2016, 'December', 15, '08:32:43', '17:24:45', ''),
(7, 'BC9801', 2016, 'December', 16, '07:32:43', '16:24:45', ''),
(8, 'BC9801', 2016, 'December', 17, '08:32:43', '17:24:45', ''),
(9, 'BC9859', 2015, 'December', 17, '08:32:43', '17:24:45', ''),
(10, 'BC9801', 2017, 'February', 17, '08:32:43', '17:24:45', '');

-- --------------------------------------------------------

--
-- Struktur dari tabel `beacon`
--

CREATE TABLE `beacon` (
  `id_beacon` varchar(10) NOT NULL,
  `minor` varchar(10) NOT NULL,
  `major` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `beacon`
--

INSERT INTO `beacon` (`id_beacon`, `minor`, `major`) VALUES
('1', '122', '122'),
('111', '1111', '111'),
('122', '122', '122'),
('123', '444', '123'),
('132', '122', '122'),
('2', '122', '122'),
('3', '122', '122'),
('4', '122', '122'),
('5', '122', '122'),
('6', '122', '122'),
('BC9801', '632', '111'),
('BC9802', '690', '453'),
('BC9822', '1822', '28391'),
('BC9838', '21433', '4314'),
('BC9841', '9329', '31990'),
('BC9856', '1822', '28391'),
('BC9859', '456', '909'),
('BC9876', '456', '909'),
('BC9891', '9329', '31990');

-- --------------------------------------------------------

--
-- Struktur dari tabel `company`
--

CREATE TABLE `company` (
  `id` int(11) NOT NULL,
  `uid` varchar(20) NOT NULL,
  `name_company` varchar(50) NOT NULL,
  `email_company` varchar(50) NOT NULL,
  `notepl_company` bigint(10) NOT NULL,
  `address_company` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `company`
--

INSERT INTO `company` (`id`, `uid`, `name_company`, `email_company`, `notepl_company`, `address_company`) VALUES
(1, 'indpharma1', 'PT. EAGLE INDO PHARMA ', 'eagle@indopharma.com', 318975644, 'Jl. Raya Cendrawasih no. 27 Gresik'),
(2, 'belajarubic', 'PT. Belajarubic', 'belajarubic@gmail.com', 3817865533, 'Jl. Wisma Agung XII no. 3 Mulyosari, Surabaya'),
(3, 'udacity', 'PT. Udacity', 'cs@udacity.com', 298564344, 'East Dustchie 21, Washington DC');

-- --------------------------------------------------------

--
-- Struktur dari tabel `employee_leave`
--

CREATE TABLE `employee_leave` (
  `id` int(5) NOT NULL,
  `id_beacon` varchar(10) NOT NULL,
  `category_id` varchar(100) NOT NULL,
  `date_begin` date NOT NULL,
  `date_end` date NOT NULL,
  `notes` text NOT NULL,
  `is_approved` int(1) NOT NULL,
  `date_created` datetime NOT NULL,
  `date_modified` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktur dari tabel `employee_overtime`
--

CREATE TABLE `employee_overtime` (
  `id` int(5) NOT NULL,
  `id_beacon` varchar(10) NOT NULL,
  `date` date NOT NULL,
  `time_begin` time NOT NULL,
  `time_end` time NOT NULL,
  `notes` text NOT NULL,
  `is_approved` int(1) NOT NULL,
  `date_created` datetime NOT NULL,
  `date_modified` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktur dari tabel `employee_permit`
--

CREATE TABLE `employee_permit` (
  `id` int(11) NOT NULL,
  `id_beacon` varchar(10) NOT NULL,
  `entry` varchar(15) NOT NULL,
  `category_id` varchar(100) NOT NULL,
  `date_begin` date NOT NULL,
  `date_end` date NOT NULL,
  `notes` text NOT NULL,
  `is_approved` int(1) NOT NULL,
  `date_created` datetime NOT NULL,
  `date_modified` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `employee_permit`
--

INSERT INTO `employee_permit` (`id`, `id_beacon`, `entry`, `category_id`, `date_begin`, `date_end`, `notes`, `is_approved`, `date_created`, `date_modified`) VALUES
(1, 'BC9801', 'permit', 'Datang Terlambat', '0000-00-00', '2018-03-19', '-', 0, '2018-03-19 05:20:31', '2018-03-19 05:20:31');

-- --------------------------------------------------------

--
-- Struktur dari tabel `status`
--

CREATE TABLE `status` (
  `id` int(11) NOT NULL,
  `id_beacon` varchar(10) NOT NULL,
  `title` varchar(15) NOT NULL,
  `message` text NOT NULL,
  `is_approved` int(1) NOT NULL,
  `date_modified` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `status`
--

INSERT INTO `status` (`id`, `id_beacon`, `title`, `message`, `is_approved`, `date_modified`) VALUES
(1, 'BC9801', 'permit', 'Anda telah mengajukan IZIN dengan keterangan Datang Terlambat - untuk tanggal 18-3-2018 sampai 2018-3-19 pada 2018-03-19 05:20:31', 0, '2018-03-19 05:20:31');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users_compa`
--

CREATE TABLE `users_compa` (
  `id_beacon` varchar(10) NOT NULL,
  `fullname` varchar(100) NOT NULL,
  `uname` varchar(20) NOT NULL,
  `uid` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(10) NOT NULL,
  `phoneNum` varchar(20) NOT NULL,
  `birthday_date` date NOT NULL,
  `birthday_place` varchar(30) NOT NULL,
  `position` varchar(20) NOT NULL,
  `division` varchar(20) NOT NULL,
  `address` text NOT NULL,
  `city` varchar(50) NOT NULL,
  `state` varchar(30) NOT NULL,
  `country` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `users_compa`
--

INSERT INTO `users_compa` (`id_beacon`, `fullname`, `uname`, `uid`, `email`, `password`, `phoneNum`, `birthday_date`, `birthday_place`, `position`, `division`, `address`, `city`, `state`, `country`) VALUES
('1', 'asd', 'asd', 'indpharma1', 'asd', 'asd', 'asd', '0000-00-00', 'asd', '123', 'asd', 'asd', 'asd', 'asd', 'asd'),
('111', 'asd', 'asd', 'indpharma1', 'asd', 'asd', 'asd', '0000-00-00', 'asd', '123', 'asd', 'asd', 'asd', 'asd', 'asd'),
('123', 'asd', 'asd', 'indpharma1', 'asd', 'asd', 'asd', '0000-00-00', 'asd', '123', 'asd', 'asd', 'asd', 'asd', 'asd'),
('2', 'asd', 'asd', 'indpharma1', 'asd', 'asd', 'asd', '0000-00-00', 'asd', '123', 'asd', 'asd', 'asd', 'asd', 'asd'),
('3', 'asd', 'asd', 'indpharma1', 'asd', 'asd', 'asd', '0000-00-00', 'asd', '123', 'asd', 'asd', 'asd', 'asd', 'asd'),
('4', 'asd', 'asd', 'indpharma1', 'asd', 'asd', 'asd', '0000-00-00', 'asd', '123', 'asd', 'asd', 'asd', 'asd', 'asd'),
('5', 'asd', 'asd', 'indpharma1', 'asd', 'asd', 'asd', '0000-00-00', 'asd', '123', 'asd', 'asd', 'asd', 'asd', 'asd'),
('6', 'asd', 'asd', 'indpharma1', 'asd', 'asd', 'asd', '0000-00-00', 'asd', '123', 'asd', 'asd', 'asd', 'asd', 'asd'),
('BC9801', 'Diana Saraswati Delinda', 'nana', 'indpharma1', 'nana123@gmail.com', 'na123', '08979738841', '1997-08-21', 'Malang', 'Akuntan', 'Office', 'Banyu Urip 23', 'Surabaya', 'Jawa Timur', 'Indonesia'),
('BC9822', 'Bruce Banner', 'brucebanner', 'indpharma1', 'banner@gmail.com', 'Banner123', '087388264527', '1990-09-18', 'Ohio', 'Nuclear Physicist', 'Physicist', '111-119 Sunbridge Rd, Bradford BD1 2NJ, UK', 'New York City', 'Bradford', 'United State'),
('BC9838', 'Clark Kent', 'clarkkent', 'indpharma1', 'clarkkent@gmail.com', 'Kent123', '087388264527', '1990-05-09', 'Smallville', 'Reporter', 'Communication', '111-119 Sunbridge Rd, Bradford BD1 2NJ, UK', 'New York City', 'Bradford', 'United State'),
('BC9841', 'Peter Benjamin Parker', 'peterparker', 'indpharma1', 'pete@gmail.com', 'Parker123', '086755334257', '1990-09-22', 'New York', 'Photographer', 'Communication', '111-119 Sunbridge Rd, Bradford BD1 2NJ, UK', 'New York', 'Bradford', 'United State'),
('BC9856', 'Barry James Allen', 'barryallen', 'indpharma1', 'barry@gmail.com', 'Barry123', '086755334257', '1990-02-24', 'Central City', 'Forensic Scientist', 'CSI Police', '111-119 Sunbridge Rd, Bradford BD1 2NJ, UK', 'Central City', 'Bradford', 'United Kingdom'),
('BC9859', 'Achmad Ilham Imanuddin', 'belajarubic', 'belajarubic', 'achmad@gmail.com', 'achmad', '085648819007', '1996-07-04', 'Surabaya', 'Engineer', '2D', 'Garuda 9 no. 29, Rewwin, Waru', 'Sidoarjo', 'Jawa Timur', 'Indonesia'),
('BC9876', 'Natasha Romanoff', 'natasha', 'indpharma1', 'natasha@gmail.com', 'Natasha123', '087388264527', '1990-05-09', 'Stalingrad', 'Police', 'Chief Inspector', '111-119 Sunbridge Rd, Bradford BD1 2NJ, UK', 'Stalingrad', 'Bradford', 'United Kingdom'),
('BC9891', 'Bruce Wayne', 'brucewayne', 'indpharma1', 'bruce@gmail.com', 'Bruce123', '087388264527', '1990-05-08', 'GothamCity', 'Police', 'Chief Inspector', '111-119 Sunbridge Rd, Bradford BD1 2NJ, UK', 'Gotham City', 'Bradford', 'United Kingdom');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `absensi`
--
ALTER TABLE `absensi`
  ADD PRIMARY KEY (`no`),
  ADD KEY `id_beacon` (`id_beacon`) USING BTREE;

--
-- Indexes for table `beacon`
--
ALTER TABLE `beacon`
  ADD PRIMARY KEY (`id_beacon`);

--
-- Indexes for table `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uid` (`uid`);

--
-- Indexes for table `employee_leave`
--
ALTER TABLE `employee_leave`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_beacon` (`id_beacon`);

--
-- Indexes for table `employee_overtime`
--
ALTER TABLE `employee_overtime`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_beacon` (`id_beacon`);

--
-- Indexes for table `employee_permit`
--
ALTER TABLE `employee_permit`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_beacon` (`id_beacon`);

--
-- Indexes for table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_beacon` (`id_beacon`) USING BTREE;

--
-- Indexes for table `users_compa`
--
ALTER TABLE `users_compa`
  ADD PRIMARY KEY (`id_beacon`),
  ADD KEY `uid` (`uid`) USING BTREE;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `absensi`
--
ALTER TABLE `absensi`
  MODIFY `no` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `company`
--
ALTER TABLE `company`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `employee_leave`
--
ALTER TABLE `employee_leave`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `employee_overtime`
--
ALTER TABLE `employee_overtime`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `employee_permit`
--
ALTER TABLE `employee_permit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `status`
--
ALTER TABLE `status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `absensi`
--
ALTER TABLE `absensi`
  ADD CONSTRAINT `absensi_ibfk_1` FOREIGN KEY (`id_beacon`) REFERENCES `beacon` (`id_beacon`) ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `employee_leave`
--
ALTER TABLE `employee_leave`
  ADD CONSTRAINT `employee_leave_ibfk_1` FOREIGN KEY (`id_beacon`) REFERENCES `users_compa` (`id_beacon`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `employee_overtime`
--
ALTER TABLE `employee_overtime`
  ADD CONSTRAINT `employee_overtime_ibfk_1` FOREIGN KEY (`id_beacon`) REFERENCES `users_compa` (`id_beacon`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `employee_permit`
--
ALTER TABLE `employee_permit`
  ADD CONSTRAINT `employee_permit_ibfk_1` FOREIGN KEY (`id_beacon`) REFERENCES `users_compa` (`id_beacon`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `status`
--
ALTER TABLE `status`
  ADD CONSTRAINT `status_ibfk_1` FOREIGN KEY (`id_beacon`) REFERENCES `users_compa` (`id_beacon`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `users_compa`
--
ALTER TABLE `users_compa`
  ADD CONSTRAINT `users_compa_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `company` (`uid`) ON UPDATE CASCADE,
  ADD CONSTRAINT `users_compa_ibfk_3` FOREIGN KEY (`id_beacon`) REFERENCES `beacon` (`id_beacon`) ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
