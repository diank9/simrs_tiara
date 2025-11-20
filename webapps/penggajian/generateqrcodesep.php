<?php
    // Include file konfigurasi database dan koneksi
    include '../conf/conf.php';
    
    // Include library PHP QR Code untuk generate QR code
    include '../phpqrcode/qrlib.php'; 
    
    // Ambil parameter no_kartu dari URL GET, replace underscore dengan spasi, lalu validasi
    $no_kartu = validTeks(str_replace("_"," ",$_GET['no_kartu']));
    
    // Cek apakah no_kartu tersedia/valid
    if(isset($no_kartu)){
        // Set direktori temporary untuk menyimpan file QR code PNG
        $PNG_TEMP_DIR   = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
        
        // Set direktori web untuk akses file QR code
        $PNG_WEB_DIR    = 'temp/';
        
        // Cek apakah folder temp sudah ada, jika belum buat folder baru
        if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
        
        // Set nama file output QR code berdasarkan nomor kartu peserta
        $filename = $PNG_TEMP_DIR.$no_kartu.'.png';
        
        // Set level error correction QR code ('L' = Low ~7% koreksi error)
        $errorCorrectionLevel = 'L';
        
        // Set ukuran matrix point QR code (menentukan ukuran pixel per modul)
        $matrixPointSize = 4;
        
        // Query database untuk ambil data nama instansi dan kabupaten dari tabel setting
        $setting = mysqli_fetch_array(bukaquery("select nama_instansi,kabupaten from setting"));
        
        // Generate QR code dengan konten:
        // - Lokasi penerbitan (nama instansi + kabupaten)
        // - Nama peserta yang menandatangani (query dari tabel bridging_sep berdasarkan no_kartu)
        // - ID unik (hash SHA1 dari sidik jari pegawai join dengan no_kartu di bridging_sep, atau fallback ke no_kartu jika sidikjari null)
        // - Tanggal penerbitan dalam format dd-mm-yyyy
        // Simpan sebagai file PNG dengan level error correction dan ukuran yang sudah ditentukan
        QRcode::png(
            "Dikeluarkan di ".$setting["nama_instansi"].", Kabupaten/Kota ".$setting["kabupaten"]."\n".
            "Ditandatangani secara elektronik oleh ".getOne("select peserta from bridging_sep where no_kartu='$no_kartu'")."\n".
            "ID ".getOne3("select ifnull(sha1(sidikjari.sidikjari),'".$no_kartu."') from sidikjari inner join pegawai on pegawai.id=sidikjari.id inner join bridging_sep on bridging_sep.no_kartu=pegawai.nik where bridging_sep.no_kartu='".$no_kartu."'",$no_kartu)."\n".
            date('d-m-Y'), 
            $filename, 
            $errorCorrectionLevel, 
            $matrixPointSize, 
            2 // Margin/quiet zone di sekitar QR code (dalam unit modul)
        );
    }   
?>
