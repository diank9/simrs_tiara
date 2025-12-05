<?php
/**
 * File: api.php
 * Fungsi: Handler untuk semua operasi antrian
 */

require_once 'config.php';

// Set timezone ke Asia/Jakarta (WIB)
date_default_timezone_set('Asia/Jakarta');

// Set header JSON
header('Content-Type: application/json');

// Ambil action dari parameter GET
$action = $_GET['action'] ?? '';

// Ambil data dari request body untuk POST
$input = json_decode(file_get_contents('php://input'), true);

/**
 * Response helper
 */
function response($success, $message, $data = []) {
    echo json_encode(array_merge([
        'success' => $success,
        'message' => $message
    ], $data));
    exit;
}

/**
 * Fungsi untuk cek dan auto-reset data jika sudah ganti hari atau jam 00:00:01
 * @return bool True jika data direset
 */
function autoResetIfNeeded($conn) {
    // Ambil tanggal terakhir dari data antrian
    $query = "SELECT DATE(w_cetak) as last_date, 
              TIME(w_cetak) as last_time 
              FROM antrian_db 
              ORDER BY w_cetak DESC LIMIT 1";
    
    $result = $conn->query($query);
    
    if ($result && $result->num_rows > 0) {
        $row = $result->fetch_assoc();
        $lastDate = $row['last_date'];
        $today = date('Y-m-d');
        $currentTime = date('H:i:s');
        
        // Reset jika tanggal berbeda ATAU jam menunjukkan 00:00:01-00:01:00
        if ($lastDate != $today || ($currentTime >= '00:00:01' && $currentTime <= '00:01:00')) {
            // Truncate tabel antrian_db
            $conn->query("TRUNCATE TABLE antrian_db");
            return true;
        }
    }
    
    return false;
}

/**
 * Generate nomor antrian baru per kode poli
 * Format: 001, 002, 003, dst (terpisah per poli)
 */
function generateNomorAntrian($conn, $kd_poli) {
    // Ambil nomor terakhir hari ini untuk kode poli tertentu
    // Menggunakan ORDER BY id DESC untuk memastikan urutan berdasarkan input terakhir
    $today = date('Y-m-d');
    $query = "SELECT CAST(nomor AS UNSIGNED) as nomor_int 
              FROM antrian_db 
              WHERE kd_poli = ? 
              AND DATE(w_cetak) = ? 
              ORDER BY CAST(nomor AS UNSIGNED) DESC 
              LIMIT 1";
    
    $stmt = $conn->prepare($query);
    $stmt->bind_param("ss", $kd_poli, $today);
    $stmt->execute();
    $result = $stmt->get_result();
    
    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        // Ambil nomor terakhir dan tambah 1
        $newNumber = intval($row['nomor_int']) + 1;
    } else {
        // Jika belum ada antrian untuk poli ini hari ini, mulai dari 1
        $newNumber = 1;
    }
    
    // Format nomor dengan 3 digit (001, 002, 003, dst)
    return str_pad($newNumber, 3, '0', STR_PAD_LEFT);
}

// Route berdasarkan action
switch ($action) {
    
    /**
     * Action: check_reset
     * Fungsi: Cek dan reset otomatis jika perlu
     */
    case 'check_reset':
        $wasReset = autoResetIfNeeded($conn);
        response(true, $wasReset ? 'Data direset' : 'Tidak perlu reset', [
            'reset' => $wasReset
        ]);
        break;
    
    /**
     * Action: ambil
     * Fungsi: Mengambil nomor antrian baru
     * Parameter: kd_poli (FRM, LAB, RAD, dll)
     */
    case 'ambil':
        // Cek dan reset otomatis jika diperlukan
        autoResetIfNeeded($conn);
        
        $kd_poli = $input['kd_poli'] ?? '';
        
        if (empty($kd_poli)) {
            response(false, 'Kode poliklinik harus diisi');
        }
        
        // Generate nomor antrian
        $nomor = generateNomorAntrian($conn, $kd_poli);
        $w_cetak = date('Y-m-d H:i:s');
        
        // Insert ke database
        // loket = NULL karena untuk penunjang (farmasi, lab, radiologi)
        // status = 0 (belum dipanggil)
        $query = "INSERT INTO antrian_db (nomor, kd_poli, loket, status, w_cetak) 
                  VALUES (?, ?, NULL, '0', ?)";
        
        $stmt = $conn->prepare($query);
        $stmt->bind_param("sss", $nomor, $kd_poli, $w_cetak);
        
        if ($stmt->execute()) {
            response(true, 'Nomor antrian berhasil dibuat', [
                'nomor' => $nomor,
                'kd_poli' => $kd_poli,
                'w_cetak' => $w_cetak
            ]);
        } else {
            response(false, 'Gagal membuat antrian: ' . $conn->error);
        }
        break;
    
    /**
     * Action: panggil
     * Fungsi: Memanggil antrian berikutnya
     * Parameter: kd_poli (opsional, jika tidak ada ambil semua)
     */
    case 'panggil':
        $kd_poli = $input['kd_poli'] ?? '';
        
        // Ambil antrian dengan status 0 (belum dipanggil) yang paling awal
        if (!empty($kd_poli)) {
            $query = "SELECT * FROM antrian_db 
                      WHERE kd_poli = ? AND status = '0' 
                      ORDER BY w_cetak ASC LIMIT 1";
            $stmt = $conn->prepare($query);
            $stmt->bind_param("s", $kd_poli);
        } else {
            $query = "SELECT * FROM antrian_db 
                      WHERE status = '0' 
                      ORDER BY w_cetak ASC LIMIT 1";
            $stmt = $conn->prepare($query);
        }
        
        $stmt->execute();
        $result = $stmt->get_result();
        
        if ($result->num_rows > 0) {
            $row = $result->fetch_assoc();
            $id = $row['id'];
            $w_panggil = date('Y-m-d H:i:s');
            
            // Update status menjadi 1 (dipanggil) dan set waktu panggil
            $updateQuery = "UPDATE antrian_db 
                           SET status = '1', w_panggil = ? 
                           WHERE id = ?";
            $updateStmt = $conn->prepare($updateQuery);
            $updateStmt->bind_param("si", $w_panggil, $id);
            
            if ($updateStmt->execute()) {
                response(true, 'Antrian berhasil dipanggil', [
                    'id' => $id,
                    'nomor' => $row['nomor'],
                    'kd_poli' => $row['kd_poli'],
                    'w_panggil' => $w_panggil
                ]);
            } else {
                response(false, 'Gagal memanggil antrian: ' . $conn->error);
            }
        } else {
            response(false, 'Tidak ada antrian yang menunggu');
        }
        break;
    
    /**
     * Action: selesai
     * Fungsi: Menyelesaikan antrian yang sedang dipanggil
     * Parameter: kd_poli (opsional)
     */
    case 'selesai':
        $kd_poli = $input['kd_poli'] ?? '';
        
        // Ambil antrian dengan status 1 (sedang dipanggil)
        if (!empty($kd_poli)) {
            $query = "SELECT * FROM antrian_db 
                      WHERE kd_poli = ? AND status = '1' 
                      ORDER BY w_panggil DESC LIMIT 1";
            $stmt = $conn->prepare($query);
            $stmt->bind_param("s", $kd_poli);
        } else {
            $query = "SELECT * FROM antrian_db 
                      WHERE status = '1' 
                      ORDER BY w_panggil DESC LIMIT 1";
            $stmt = $conn->prepare($query);
        }
        
        $stmt->execute();
        $result = $stmt->get_result();
        
        if ($result->num_rows > 0) {
            $row = $result->fetch_assoc();
            $id = $row['id'];
            $w_selesai = date('Y-m-d H:i:s');
            
            // Update status menjadi 2 (selesai) dan set waktu selesai
            $updateQuery = "UPDATE antrian_db 
                           SET status = '2', w_selesai = ? 
                           WHERE id = ?";
            $updateStmt = $conn->prepare($updateQuery);
            $updateStmt->bind_param("si", $w_selesai, $id);
            
            if ($updateStmt->execute()) {
                response(true, 'Antrian berhasil diselesaikan', [
                    'id' => $id,
                    'nomor' => $row['nomor'],
                    'w_selesai' => $w_selesai
                ]);
            } else {
                response(false, 'Gagal menyelesaikan antrian: ' . $conn->error);
            }
        } else {
            response(false, 'Tidak ada antrian yang sedang dipanggil');
        }
        break;
    
    /**
     * Action: batal
     * Fungsi: Membatalkan antrian yang sedang dipanggil
     * Parameter: kd_poli (opsional)
     */
    case 'batal':
        $kd_poli = $input['kd_poli'] ?? '';
        
        // Ambil antrian dengan status 1 (sedang dipanggil)
        if (!empty($kd_poli)) {
            $query = "SELECT * FROM antrian_db 
                      WHERE kd_poli = ? AND status = '1' 
                      ORDER BY w_panggil DESC LIMIT 1";
            $stmt = $conn->prepare($query);
            $stmt->bind_param("s", $kd_poli);
        } else {
            $query = "SELECT * FROM antrian_db 
                      WHERE status = '1' 
                      ORDER BY w_panggil DESC LIMIT 1";
            $stmt = $conn->prepare($query);
        }
        
        $stmt->execute();
        $result = $stmt->get_result();
        
        if ($result->num_rows > 0) {
            $row = $result->fetch_assoc();
            $id = $row['id'];
            
            // Update status menjadi 3 (batal)
            $updateQuery = "UPDATE antrian_db 
                           SET status = '3' 
                           WHERE id = ?";
            $updateStmt = $conn->prepare($updateQuery);
            $updateStmt->bind_param("i", $id);
            
            if ($updateStmt->execute()) {
                response(true, 'Antrian berhasil dibatalkan', [
                    'id' => $id,
                    'nomor' => $row['nomor']
                ]);
            } else {
                response(false, 'Gagal membatalkan antrian: ' . $conn->error);
            }
        } else {
            response(false, 'Tidak ada antrian yang sedang dipanggil');
        }
        break;
    
    /**
     * Action: current
     * Fungsi: Mengambil antrian yang sedang dipanggil
     * Parameter: kd_poli (opsional)
     */
    case 'current':
        $kd_poli = $_GET['kd_poli'] ?? '';
        
        // Ambil antrian dengan status 1 (sedang dipanggil)
        if (!empty($kd_poli)) {
            $query = "SELECT * FROM antrian_db 
                      WHERE kd_poli = ? AND status = '1' 
                      ORDER BY w_panggil DESC LIMIT 1";
            $stmt = $conn->prepare($query);
            $stmt->bind_param("s", $kd_poli);
        } else {
            $query = "SELECT * FROM antrian_db 
                      WHERE status = '1' 
                      ORDER BY w_panggil DESC LIMIT 1";
            $stmt = $conn->prepare($query);
        }
        
        $stmt->execute();
        $result = $stmt->get_result();
        
        if ($result->num_rows > 0) {
            $row = $result->fetch_assoc();
            response(true, 'Data antrian ditemukan', [
                'id' => $row['id'],
                'nomor' => $row['nomor'],
                'kd_poli' => $row['kd_poli'],
                'loket' => $row['loket'],
                'w_panggil' => $row['w_panggil']
            ]);
        } else {
            response(true, 'Tidak ada antrian yang sedang dipanggil', [
                'nomor' => null
            ]);
        }
        break;
    
    /**
     * Action: list
     * Fungsi: Mengambil daftar antrian
     * Parameter: kd_poli (opsional), status (opsional)
     */
    case 'list':
        $kd_poli = $_GET['kd_poli'] ?? '';
        $status = $_GET['status'] ?? '';
        $today = date('Y-m-d');
        
        // Build query dinamis
        $conditions = ["DATE(w_cetak) = ?"];
        $params = [$today];
        $types = "s";
        
        if (!empty($kd_poli)) {
            $conditions[] = "kd_poli = ?";
            $params[] = $kd_poli;
            $types .= "s";
        }
        
        if ($status !== '') {
            $conditions[] = "status = ?";
            $params[] = $status;
            $types .= "s";
        }
        
        $query = "SELECT * FROM antrian_db 
                  WHERE " . implode(" AND ", $conditions) . " 
                  ORDER BY w_cetak ASC";
        
        $stmt = $conn->prepare($query);
        $stmt->bind_param($types, ...$params);
        $stmt->execute();
        $result = $stmt->get_result();
        
        $data = [];
        while ($row = $result->fetch_assoc()) {
            $data[] = $row;
        }
        
        response(true, 'Data antrian berhasil diambil', [
            'data' => $data,
            'total' => count($data)
        ]);
        break;
    
    /**
     * Action: reset
     * Fungsi: Reset/hapus semua antrian hari ini
     */
    case 'reset':
        $today = date('Y-m-d');
        
        // Hapus antrian hari ini
        $query = "DELETE FROM antrian_db WHERE DATE(w_cetak) = ?";
        $stmt = $conn->prepare($query);
        $stmt->bind_param("s", $today);
        
        if ($stmt->execute()) {
            response(true, 'Semua antrian hari ini berhasil direset', [
                'deleted' => $stmt->affected_rows
            ]);
        } else {
            response(false, 'Gagal reset antrian: ' . $conn->error);
        }
        break;
    
    /**
     * Action: stats
     * Fungsi: Statistik antrian hari ini
     */
    case 'stats':
        $today = date('Y-m-d');
        
        // Hitung total per status
        $query = "SELECT 
                    kd_poli,
                    COUNT(CASE WHEN status = '0' THEN 1 END) as menunggu,
                    COUNT(CASE WHEN status = '1' THEN 1 END) as dipanggil,
                    COUNT(CASE WHEN status = '2' THEN 1 END) as selesai,
                    COUNT(CASE WHEN status = '3' THEN 1 END) as batal,
                    COUNT(*) as total
                  FROM antrian_db 
                  WHERE DATE(w_cetak) = ?
                  GROUP BY kd_poli";
        
        $stmt = $conn->prepare($query);
        $stmt->bind_param("s", $today);
        $stmt->execute();
        $result = $stmt->get_result();
        
        $stats = [];
        while ($row = $result->fetch_assoc()) {
            $stats[] = $row;
        }
        
        response(true, 'Statistik antrian berhasil diambil', [
            'stats' => $stats
        ]);
        break;
    
    /**
     * Action: dashboard
     * Fungsi: Mengambil data untuk dashboard antrian
     * Return: Data antrian yang sedang dipanggil dan menunggu per poli
     */
    case 'dashboard':
        $today = date('Y-m-d');
        
        // Ambil data antrian untuk setiap poliklinik
        $query = "SELECT kd_poli, nomor, status 
                  FROM antrian_db 
                  WHERE DATE(w_cetak) = ? 
                  ORDER BY kd_poli, CAST(nomor AS UNSIGNED) ASC";
        
        $stmt = $conn->prepare($query);
        $stmt->bind_param("s", $today);
        $stmt->execute();
        $result = $stmt->get_result();
        
        // Organize data per poliklinik
        $dashboardData = [];
        
        while ($row = $result->fetch_assoc()) {
            $kd_poli = $row['kd_poli'];
            
            if (!isset($dashboardData[$kd_poli])) {
                $dashboardData[$kd_poli] = [
                    'current' => '---',
                    'waiting' => []
                ];
            }
            
            // Status 1 = sedang dipanggil (current)
            if ($row['status'] == '1') {
                $dashboardData[$kd_poli]['current'] = $row['nomor'];
            }
            // Status 0 = menunggu (waiting)
            elseif ($row['status'] == '0') {
                $dashboardData[$kd_poli]['waiting'][] = $row['nomor'];
            }
        }
        
        response(true, 'Data dashboard berhasil diambil', [
            'data' => $dashboardData
        ]);
        break;
    
    default:
        response(false, 'Action tidak valid');
}

$conn->close();
?>