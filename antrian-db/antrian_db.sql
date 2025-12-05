-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Waktu pembuatan: 05 Des 2025 pada 09.35
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sik_design`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `antrian_db`
--

CREATE TABLE `antrian_db` (
  `id` int(11) NOT NULL,
  `nomor` varchar(4) NOT NULL COMMENT 'dibuat beda per poliklinik',
  `kd_poli` char(5) NOT NULL COMMENT 'datanya diambil langusng dari tabel poliklinik',
  `loket` varchar(5) DEFAULT NULL COMMENT 'pemilihan loket hanya ada di pendaftaran, kalau untuk penunjang (farmasi, lab, radiologi) set default null',
  `status` enum('0','1','2','3') NOT NULL COMMENT '0=belum dipanggil\r\n1=dipanggil\r\n2=selesai\r\n3=batal',
  `w_cetak` datetime NOT NULL COMMENT 'waktu cetak antrian',
  `w_panggil` datetime DEFAULT NULL COMMENT 'waktu panggil',
  `w_selesai` datetime DEFAULT NULL COMMENT 'wakt selesai'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `antrian_db`
--
ALTER TABLE `antrian_db`
  ADD PRIMARY KEY (`id`),
  ADD KEY `kd_poli` (`kd_poli`) USING BTREE;

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `antrian_db`
--
ALTER TABLE `antrian_db`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `antrian_db`
--
ALTER TABLE `antrian_db`
  ADD CONSTRAINT `antrian_db_constraint1` FOREIGN KEY (`kd_poli`) REFERENCES `poliklinik` (`kd_poli`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
