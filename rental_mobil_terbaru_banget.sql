-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 26, 2020 at 03:45 PM
-- Server version: 10.4.10-MariaDB
-- PHP Version: 7.1.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rental_mobil`
--

-- --------------------------------------------------------

--
-- Table structure for table `data_mobil`
--

CREATE TABLE `data_mobil` (
  `id_mobil` int(11) NOT NULL,
  `type_mobil` varchar(255) NOT NULL,
  `merk_mobil` varchar(255) NOT NULL,
  `harga_jual` varchar(255) NOT NULL,
  `stok` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_mobil`
--

INSERT INTO `data_mobil` (`id_mobil`, `type_mobil`, `merk_mobil`, `harga_jual`, `stok`) VALUES
(1, 'Toyota', 'Avanza', '100000', 0),
(2, 'Angola', 'Avanza', '120000', 0),
(4, 'Avanza', 'Jaguar', '10000', 10);

-- --------------------------------------------------------

--
-- Table structure for table `penyedia`
--

CREATE TABLE `penyedia` (
  `id_penyedia` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama_lengkap` varchar(35) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `penyedia`
--

INSERT INTO `penyedia` (`id_penyedia`, `username`, `password`, `nama_lengkap`) VALUES
(1, 'admin', 'admin', 'Admin Yang Tahu Segala');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` int(11) NOT NULL,
  `nama_pembeli` varchar(255) NOT NULL,
  `no_ktp` varchar(255) NOT NULL,
  `tgl_pembelian` varchar(255) NOT NULL,
  `id_mobil` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `nama_pembeli`, `no_ktp`, `tgl_pembelian`, `id_mobil`) VALUES
(2, 'Dion', '20139021', '2019-10-12', 1),
(3, 'Arya', '8123912', '2019-10-11', 2),
(4, 'Pamungkas', '302930', '2020-01-10', 4),
(5, 'Naufal', '302930', '2020-01-10', 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `data_mobil`
--
ALTER TABLE `data_mobil`
  ADD PRIMARY KEY (`id_mobil`);

--
-- Indexes for table `penyedia`
--
ALTER TABLE `penyedia`
  ADD PRIMARY KEY (`id_penyedia`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `id_mobil` (`id_mobil`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `data_mobil`
--
ALTER TABLE `data_mobil`
  MODIFY `id_mobil` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `penyedia`
--
ALTER TABLE `penyedia`
  MODIFY `id_penyedia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`id_mobil`) REFERENCES `data_mobil` (`id_mobil`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
