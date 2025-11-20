<?php
// Aktifkan error reporting untuk debugging
error_reporting(E_ALL);
ini_set('display_errors', 1);

// Log semua request untuk debugging
file_put_contents('upload_log.txt', date('Y-m-d H:i:s') . " - Request received\n", FILE_APPEND);
file_put_contents('upload_log.txt', print_r($_FILES, true), FILE_APPEND);
file_put_contents('upload_log.txt', print_r($_GET, true), FILE_APPEND);

if (isset($_FILES['file']['name']) && !empty($_FILES['file']['name'])) {
    $name = $_FILES['file']['name'];
    $size = $_FILES['file']['size'];
    $type = $_FILES['file']['type'];
    $tmp_name = $_FILES['file']['tmp_name'];
    $error = $_FILES['file']['error'];
    $maxsize = 99999999999999;
    
    // Ambil parameter doc dari GET
    $location = isset($_GET['doc']) ? $_GET['doc'] : '';
    
    // Log info file
    file_put_contents('upload_log.txt', "File: $name, Size: $size, Location: $location\n", FILE_APPEND);
    
    // Validasi error upload
    if ($error !== UPLOAD_ERR_OK) {
        $errorMsg = "Upload error code: $error";
        file_put_contents('upload_log.txt', "$errorMsg\n", FILE_APPEND);
        echo json_encode(['status' => 'error', 'message' => $errorMsg]);
        exit;
    }
    
    // Validasi ukuran
    if ($size > $maxsize) {
        $errorMsg = "File terlalu besar";
        file_put_contents('upload_log.txt', "$errorMsg\n", FILE_APPEND);
        echo json_encode(['status' => 'error', 'message' => $errorMsg]);
        exit;
    }
    
    // Buat folder jika belum ada
    if (!empty($location) && !is_dir($location)) {
        mkdir($location, 0755, true);
        file_put_contents('upload_log.txt', "Folder created: $location\n", FILE_APPEND);
    }
    
    // Proses upload
    $destination = $location . $name;
    if (move_uploaded_file($tmp_name, $destination)) {
        $successMsg = "File berhasil diupload ke: $destination";
        file_put_contents('upload_log.txt', "$successMsg\n", FILE_APPEND);
        echo json_encode(['status' => 'success', 'message' => $successMsg, 'file' => $destination]);
    } else {
        $errorMsg = "Gagal memindahkan file ke: $destination";
        file_put_contents('upload_log.txt', "$errorMsg\n", FILE_APPEND);
        echo json_encode(['status' => 'error', 'message' => $errorMsg]);
    }
} else {
    $errorMsg = "Tidak ada file yang diupload";
    file_put_contents('upload_log.txt', "$errorMsg\n", FILE_APPEND);
    echo json_encode(['status' => 'error', 'message' => $errorMsg]);
}
?>
