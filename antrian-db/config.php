<?php
/**
 * File: config.php
 * Fungsi: Konfigurasi koneksi database
 */

// Set timezone ke Asia/Jakarta (WIB)
date_default_timezone_set('Asia/Jakarta');

// Konfigurasi database
define('DB_HOST', 'localhost');
define('DB_USER', 'root');
define('DB_PASS', '');
define('DB_NAME', 'sik_design');

// Membuat koneksi ke database
$conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);

// Cek koneksi
if ($conn->connect_error) {
    die("Koneksi gagal: " . $conn->connect_error);
}

// Set charset ke utf8
$conn->set_charset("utf8");

/**
 * Fungsi untuk mengambil data setting aplikasi
 * @return array|null Data setting atau null jika gagal
 */
function getSettings() {
    global $conn;
    
    $query = "SELECT * FROM setting LIMIT 1";
    $result = $conn->query($query);
    
    if ($result && $result->num_rows > 0) {
        return $result->fetch_assoc();
    }
    
    return null;
}

/**
 * Fungsi untuk konversi blob logo ke base64
 * @param blob $blob Data blob dari database
 * @return string Base64 encoded image
 */
function blobToBase64($blob) {
    if (!empty($blob)) {
        return 'data:image/png;base64,' . base64_encode($blob);
    }
    return '';
}

/**
 * Fungsi untuk mengambil data poliklinik berdasarkan config
 * @param array $config Array konfigurasi [kd_poli, warna]
 * @return array Daftar poliklinik dengan data lengkap dari database
 */
function getPoliklinikData($config) {
    global $conn;
    
    $result = [];
    
    foreach ($config as $item) {
        $kd_poli = $item['kd_poli'];
        $warna = $item['warna'];
        
        // Ambil nm_poli dari database
        $query = "SELECT nm_poli FROM poliklinik WHERE kd_poli = ? LIMIT 1";
        $stmt = $conn->prepare($query);
        
        if ($stmt) {
            $stmt->bind_param("s", $kd_poli);
            $stmt->execute();
            $dbResult = $stmt->get_result();
            
            if ($dbResult && $dbResult->num_rows > 0) {
                $row = $dbResult->fetch_assoc();
                $result[] = [
                    'kd_poli' => $kd_poli,
                    'nama' => $row['nm_poli'], // Ambil dari database
                    'warna' => $warna
                ];
            } else {
                // Fallback: gunakan kd_poli sebagai nama jika tidak ada di database
                $result[] = [
                    'kd_poli' => $kd_poli,
                    'nama' => strtoupper($kd_poli), // Uppercase kd_poli sebagai fallback
                    'warna' => $warna
                ];
            }
            $stmt->close();
        } else {
            // Jika prepare gagal, tetap tambahkan dengan fallback
            $result[] = [
                'kd_poli' => $kd_poli,
                'nama' => strtoupper($kd_poli),
                'warna' => $warna
            ];
        }
    }
    
    return $result;
}
?>