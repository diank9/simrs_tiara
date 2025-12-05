<?php
/**
 * File: test_poliklinik.php
 * Fungsi: Test apakah tabel poliklinik sudah ada dan berisi data
 */

require_once 'config.php';

echo "<h2>Test Koneksi Database & Tabel Poliklinik</h2>";

// Test 1: Cek koneksi database
echo "<h3>1. Test Koneksi Database</h3>";
if ($conn->connect_error) {
    echo "<p style='color: red;'>❌ Koneksi GAGAL: " . $conn->connect_error . "</p>";
} else {
    echo "<p style='color: green;'>✓ Koneksi BERHASIL ke database: " . DB_NAME . "</p>";
}

// Test 2: Cek apakah tabel poliklinik ada
echo "<h3>2. Cek Tabel Poliklinik</h3>";
$checkTable = $conn->query("SHOW TABLES LIKE 'poliklinik'");
if ($checkTable && $checkTable->num_rows > 0) {
    echo "<p style='color: green;'>✓ Tabel 'poliklinik' DITEMUKAN</p>";
    
    // Test 3: Cek isi tabel
    echo "<h3>3. Isi Tabel Poliklinik</h3>";
    $result = $conn->query("SELECT * FROM poliklinik ORDER BY kd_poli");
    
    if ($result && $result->num_rows > 0) {
        echo "<p style='color: green;'>✓ Ditemukan " . $result->num_rows . " data poliklinik</p>";
        echo "<table border='1' cellpadding='10' style='border-collapse: collapse;'>";
        echo "<tr><th>Kode Poli</th><th>Nama Poli</th></tr>";
        
        while ($row = $result->fetch_assoc()) {
            echo "<tr>";
            echo "<td>" . htmlspecialchars($row['kd_poli']) . "</td>";
            echo "<td>" . htmlspecialchars($row['nm_poli']) . "</td>";
            echo "</tr>";
        }
        echo "</table>";
    } else {
        echo "<p style='color: orange;'>⚠ Tabel 'poliklinik' KOSONG (tidak ada data)</p>";
        echo "<p>Silakan jalankan SQL berikut:</p>";
        echo "<pre style='background: #f5f5f5; padding: 10px; border: 1px solid #ddd;'>";
        echo "INSERT INTO poliklinik (kd_poli, nm_poli) VALUES
('FRM', 'Farmasi'),
('LAB', 'Laboratorium'),
('RAD', 'Radiologi'),
('UMUM', 'Poli Umum'),
('GIGI', 'Poli Gigi'),
('MATA', 'Poli Mata');";
        echo "</pre>";
    }
} else {
    echo "<p style='color: red;'>❌ Tabel 'poliklinik' TIDAK DITEMUKAN</p>";
    echo "<p>Silakan buat tabel dengan SQL berikut:</p>";
    echo "<pre style='background: #f5f5f5; padding: 10px; border: 1px solid #ddd;'>";
    echo "CREATE TABLE poliklinik (
  kd_poli CHAR(5) NOT NULL,
  nm_poli VARCHAR(50) NOT NULL,
  PRIMARY KEY (kd_poli)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO poliklinik (kd_poli, nm_poli) VALUES
('FRM', 'Farmasi'),
('LAB', 'Laboratorium'),
('RAD', 'Radiologi'),
('UMUM', 'Poli Umum'),
('GIGI', 'Poli Gigi'),
('MATA', 'Poli Mata');";
    echo "</pre>";
}

// Test 4: Test fungsi getPoliklinikData
echo "<h3>4. Test Fungsi getPoliklinikData()</h3>";
$testConfig = [
    ['kd_poli' => 'FRM', 'warna' => '#2ecc71'],
    ['kd_poli' => 'LAB', 'warna' => '#3498db'],
    ['kd_poli' => 'RAD', 'warna' => '#e74c3c'],
];

$testResult = getPoliklinikData($testConfig);

if (!empty($testResult)) {
    echo "<p style='color: green;'>✓ Fungsi berhasil mengembalikan " . count($testResult) . " data</p>";
    echo "<pre>";
    print_r($testResult);
    echo "</pre>";
} else {
    echo "<p style='color: red;'>❌ Fungsi mengembalikan array kosong</p>";
}

echo "<hr>";
echo "<p><a href='index.php'>← Kembali ke Halaman Utama</a></p>";

$conn->close();
?>