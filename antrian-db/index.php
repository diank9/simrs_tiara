<?php
/**
 * File: index.php
 * Fungsi: Halaman utama untuk mengambil nomor antrian
 */

require_once 'config.php';

// Ambil data setting untuk header
$setting = getSettings();

// Konfigurasi poliklinik yang akan ditampilkan (maksimal 21)
// Format: ['kd_poli' => 'kode', 'warna' => 'hex color']
$poliklinik_config = [
    ['kd_poli' => 'FMS', 'warna' => '#34495e'],
    ['kd_poli' => 'FRM', 'warna' => '#34495e'],
    ['kd_poli' => 'LAB', 'warna' => '#34495e'],
    ['kd_poli' => 'RAD', 'warna' => '#34495e'],
    ['kd_poli' => 'BED', 'warna' => '#34495e'], 
    ['kd_poli' => 'JTG', 'warna' => '#34495e'],
    ['kd_poli' => 'GIG', 'warna' => '#34495e'],
    ['kd_poli' => 'MTA', 'warna' => '#34495e'],
    ['kd_poli' => 'JWA', 'warna' => '#34495e'],
    ['kd_poli' => 'URO', 'warna' => '#34495e'],
    ['kd_poli' => 'OPD', 'warna' => '#34495e'], 
    ['kd_poli' => 'INT', 'warna' => '#34495e'],
    ['kd_poli' => 'THT', 'warna' => '#34495e'],
    ['kd_poli' => 'PAR', 'warna' => '#34495e'],
    ['kd_poli' => 'BMU', 'warna' => '#34495e'],
    ['kd_poli' => 'SAR', 'warna' => '#34495e'],
    ['kd_poli' => 'BSY', 'warna' => '#34495e'],
];

// Ambil data lengkap poliklinik dari database (termasuk nama)
$poliklinik = getPoliklinikData($poliklinik_config);
?>
<!DOCTYPE html>
<html lang="id">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistem Antrian - <?php echo $setting['nama_instansi'] ?? 'RS'; ?></title>
    <style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background: #f0f0f0;
        min-height: 100vh;
        margin: 0;
        padding-top: 140px;
        /* Ruang untuk header fixed */
        padding-bottom: 60px;
        /* Ruang untuk footer fixed */
        display: flex;
        flex-direction: column;
    }

    /* Header dengan informasi RS - Fixed di atas */
    .header {
        background: linear-gradient(135deg, #3d5a6c 0%, #4a6b7c 100%);
        color: white;
        padding: 20px 40px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        z-index: 1000;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
    }

    .header-left {
        display: flex;
        align-items: center;
        gap: 20px;
    }

    .logo {
        width: 100px;
        height: 100px;
        border-radius: 50%;
        background: white;
        padding: 5px;
        object-fit: contain;
    }

    .header-info h1 {
        font-size: 26px;
        margin-bottom: 8px;
        font-weight: 600;
    }

    .header-info p {
        font-size: 14px;
        opacity: 0.95;
        line-height: 1.5;
    }

    .header-right {
        text-align: right;
        background: rgba(52, 152, 219, 0.9);
        padding: 15px 30px;
        border-radius: 10px;
    }

    .header-right .date {
        font-size: 18px;
        margin-bottom: 5px;
    }

    .header-right .time {
        font-size: 36px;
        font-weight: bold;
    }

    /* Main Content - Selalu di tengah vertikal */
    .main-content {
        max-width: 1600px;
        width: 100%;
        margin: auto;
        padding: 20px;
        flex: 1;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .content-box {
        background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
        border-radius: 20px;
        padding: 50px 40px;
        box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
        width: 100%;
    }

    @media (max-width: 768px) {
        .content-box {
            padding: 30px 20px;
        }

        .title {
            font-size: 24px !important;
            margin-bottom: 40px !important;
        }
    }

    .title {
        text-align: center;
        color: white;
        font-size: 32px;
        font-weight: 600;
        margin-bottom: 60px;
        text-transform: uppercase;
        letter-spacing: 2px;
    }

    /* Button Container - Menggunakan flex untuk auto-center */
    .button-container {
        display: flex;
        flex-wrap: wrap;
        gap: 25px;
        margin-bottom: 40px;
        justify-content: center;
        /* Selalu center horizontal */
        align-items: center;
        width: 100%;
    }

    .btn-antrian {
        background: #546e7a;
        color: white;
        padding: 20px 15px;
        font-size: 20px;
        font-weight: 600;
        border: none;
        border-radius: 12px;
        cursor: pointer;
        transition: all 0.3s ease;
        box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3);
        text-align: center;
        height: 130px;
        flex: 0 0 auto;
        /* Tidak grow atau shrink */
        width: 240px;
        /* Fixed width untuk konsistensi */
        display: flex;
        align-items: center;
        justify-content: center;
        word-wrap: break-word;
        line-height: 1.3;
        overflow: hidden;
    }

    /* Responsif button width */
    @media (min-width: 1600px) {
        .btn-antrian {
            width: 220px;
        }
    }

    @media (min-width: 1200px) and (max-width: 1599px) {
        .btn-antrian {
            width: 230px;
        }
    }

    @media (min-width: 992px) and (max-width: 1199px) {
        .btn-antrian {
            width: 220px;
        }
    }

    @media (min-width: 768px) and (max-width: 991px) {
        .btn-antrian {
            width: 230px;
        }
    }

    @media (min-width: 576px) and (max-width: 767px) {
        .btn-antrian {
            width: 240px;
        }
    }

    @media (max-width: 575px) {
        .btn-antrian {
            width: 100%;
            max-width: 300px;
            font-size: 18px;
            padding: 20px 15px;
            height: 120px;
        }
    }

    .btn-antrian:hover {
        transform: translateY(-5px);
        box-shadow: 0 12px 24px rgba(0, 0, 0, 0.4);
        filter: brightness(1.1);
    }

    .btn-antrian:active {
        transform: translateY(-2px);
    }

    /* Info Text */
    .info-text {
        text-align: center;
        color: white;
        margin-top: 30px;
    }

    .info-text p {
        font-size: 16px;
        margin: 8px 0;
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 10px;
    }

    .info-text .icon {
        font-size: 20px;
    }

    /* Footer - Fixed di bawah */
    .footer {
        background: #3d5a6c;
        color: white;
        padding: 15px 20px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        position: fixed;
        bottom: 0;
        left: 0;
        right: 0;
        z-index: 1000;
        box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.3);
        overflow: hidden;
    }

    .footer-marquee {
        flex: 1;
        overflow: hidden;
        margin-right: 20px;
    }

    .marquee-content {
        display: inline-block;
        white-space: nowrap;
        animation: marquee 30s linear infinite;
        font-size: 14px;
    }

    @keyframes marquee {
        0% {
            transform: translateX(100%);
        }

        100% {
            transform: translateX(-100%);
        }
    }

    .footer-copyright {
        white-space: nowrap;
        font-size: 14px;
        font-weight: 500;
    }

    /* Status Message - Popup style */
    .status-message {
        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        padding: 30px 50px;
        border-radius: 15px;
        font-weight: bold;
        font-size: 18px;
        display: none;
        z-index: 2000;
        box-shadow: 0 10px 40px rgba(0, 0, 0, 0.5);
        min-width: 400px;
        text-align: center;
    }

    .status-success {
        background: #2ecc71;
        color: white;
    }

    .status-error {
        background: #e74c3c;
        color: white;
    }

    .status-info {
        background: #3498db;
        color: white;
    }

    /* Overlay untuk popup */
    .overlay {
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: rgba(0, 0, 0, 0.7);
        display: none;
        z-index: 1999;
    }

    /* Print Popup - Hidden, hanya untuk print */
    .print-popup {
        display: none;
        position: absolute;
        left: -9999px;
    }

    @media print {

        /* Sembunyikan semua kecuali print content */
        body>*:not(.print-popup) {
            display: none !important;
        }

        body {
            margin: 0;
            padding: 0;
        }

        /* Setup untuk thermal printer 80mm x 80mm */
        @page {
            size: 80mm 80mm;
            margin: 5mm;
        }

        .print-popup {
            display: block !important;
            position: static !important;
            left: auto !important;
            width: 100% !important;
            height: 100% !important;
            background: white !important;
            padding: 0 !important;
        }

        .print-content {
            text-align: center;
            padding: 5mm 0;
        }

        .print-content h2 {
            color: #000;
            margin: 0 0 2mm 0;
            font-size: 12px;
            font-weight: bold;
        }

        .print-content .poli-name {
            font-size: 14px;
            color: #000;
            margin: 1mm 0;
            font-weight: bold;
        }

        .print-content .nomor-besar {
            font-size: 36px;
            font-weight: bold;
            color: #000;
            margin: 3mm 0;
            line-height: 1;
        }

        .print-content .waktu {
            font-size: 8px;
            color: #333;
            margin: 1mm 0 2mm 0;
        }

        .print-content hr {
            margin: 2mm 0;
            border: 0;
            border-top: 1px solid #333;
            width: 100%;
        }

        .print-content .info-rs {
            font-size: 9px;
            color: #000;
            font-weight: bold;
            margin: 1mm 0;
        }

        .print-content .alamat-rs {
            font-size: 7px;
            color: #333;
            margin: 0.5mm 0;
        }
    }
    </style>
</head>

<body>
    <!-- Overlay untuk popup message -->
    <div class="overlay" id="overlay" onclick="closeMessage()"></div>

    <!-- Status Message -->
    <div id="statusMessage" class="status-message"></div>

    <!-- Print Popup (Hidden, hanya muncul saat print) -->
    <div class="print-popup" id="printPopup">
        <div class="print-content">
            <h2>NOMOR ANTRIAN</h2>
            <div class="poli-name" id="printPoli"></div>
            <div class="nomor-besar" id="printNomor"></div>
            <div class="waktu" id="printWaktu"></div>
            <hr>
            <p class="info-rs"><?php echo $setting['nama_instansi'] ?? 'RS SIMRS KHANZA DEVELOPMENT'; ?></p>
            <p class="alamat-rs"><?php echo $setting['alamat_instansi'] ?? 'GUWOSARI'; ?></p>
        </div>
    </div>

    <!-- Header -->
    <div class="header">
        <div class="header-left">
            <?php if (!empty($setting['logo'])): ?>
            <img src="<?php echo blobToBase64($setting['logo']); ?>" alt="Logo" class="logo">
            <?php endif; ?>
            <div class="header-info">
                <h1><?php echo $setting['nama_instansi'] ?? 'RS SIMRS KHANZA DEVELOPMENT'; ?></h1>
                <p><?php echo $setting['alamat_instansi'] ?? 'GUWOSARI - Pajangan, Bantul'; ?></p>
                <p>Telp: Hp: <?php echo $setting['kontak'] ?? '085626751039, 085296559963'; ?> | Email:
                    <?php echo $setting['email'] ?? 'khanzasoftmedia@gmail.com'; ?></p>
            </div>
        </div>
        <div class="header-right">
            <div class="date" id="currentDate">Kamis, 4 Desember 2025</div>
            <div class="time" id="currentTime">10:13:55</div>
        </div>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <div class="content-box">
            <h2 class="title">SILAHKAN AMBIL NOMOR ANTRIAN</h2>

            <div class="button-container">
                <?php 
                // Loop untuk menampilkan tombol poliklinik dengan data lengkap dari database
                if (!empty($poliklinik)) {
                    foreach ($poliklinik as $poli) {
                        $kd_poli = htmlspecialchars($poli['kd_poli']);
                        $nm_poli = strtoupper(htmlspecialchars($poli['nama'])); // Uppercase untuk nama
                        $warna = htmlspecialchars($poli['warna']);
                        
                        echo "<button class='btn-antrian' 
                                      style='background: {$warna};' 
                                      onclick=\"ambilAntrian('{$kd_poli}', '{$nm_poli}')\">
                                {$nm_poli}
                              </button>";
                    }
                } else {
                    echo "<p style='color: white; text-align: center;'>Tidak ada poliklinik yang tersedia</p>";
                }
                ?>
            </div>

            <div class="info-text">
                <p>
                    <span class="icon">📋</span>
                    <span>Tekan tombol untuk mengambil nomor antrian</span>
                </p>
                <p>
                    <span class="icon">🖨️</span>
                    <span>Tiket akan dicetak secara otomatis</span>
                </p>
            </div>
        </div>
    </div>

    <!-- Footer dengan Marquee -->
    <div class="footer">
        <div class="footer-marquee">
            <div class="marquee-content">
                Selamat Datang Di <?php echo $setting['nama_instansi'] ?? 'RS SIMRS KHANZA DEVELOPMENT'; ?> | Di balik
                setiap pasien yang sembuh, ada tenaga medis yang tak kenal lelah.
            </div>
        </div>
        <div class="footer-copyright">
            🇮🇩 <?php echo $setting['nama_instansi'] ?? 'RS SIMRS KHANZA DEVELOPMENT'; ?> © 2025
        </div>
    </div>

    <script>
    /**
     * Fungsi untuk update tanggal dan waktu real-time
     */
    function updateDateTime() {
        const now = new Date();

        // Format tanggal
        const days = ['Minggu', 'Senin', 'Selasa', 'Rabu', 'Kamis', 'Jumat', 'Sabtu'];
        const months = ['Januari', 'Februari', 'Maret', 'April', 'Mei', 'Juni',
            'Juli', 'Agustus', 'September', 'Oktober', 'November', 'Desember'
        ];

        const dayName = days[now.getDay()];
        const date = now.getDate();
        const monthName = months[now.getMonth()];
        const year = now.getFullYear();

        document.getElementById('currentDate').textContent =
            `${dayName}, ${date} ${monthName} ${year}`;

        // Format waktu
        const hours = String(now.getHours()).padStart(2, '0');
        const minutes = String(now.getMinutes()).padStart(2, '0');
        const seconds = String(now.getSeconds()).padStart(2, '0');

        document.getElementById('currentTime').textContent =
            `${hours}:${minutes}:${seconds}`;
    }

    /**
     * Fungsi untuk menampilkan pesan status
     * @param {string} message - Pesan yang akan ditampilkan
     * @param {string} type - Tipe pesan (success, error, info)
     */
    function showMessage(message, type) {
        const messageEl = document.getElementById('statusMessage');
        const overlay = document.getElementById('overlay');

        messageEl.textContent = message;
        messageEl.className = 'status-message status-' + type;
        messageEl.style.display = 'block';
        overlay.style.display = 'block';

        // Sembunyikan pesan setelah 1.5 detik (lebih cepat)
        setTimeout(() => {
            closeMessage();
        }, 1500);
    }

    /**
     * Fungsi untuk menutup message
     */
    function closeMessage() {
        document.getElementById('statusMessage').style.display = 'none';
        document.getElementById('overlay').style.display = 'none';
    }

    /**
     * Fungsi untuk set data print dan langsung cetak
     * @param {string} nomor - Nomor antrian
     * @param {string} poli - Nama poliklinik
     */
    function printDirect(nomor, poli) {
        // Set data langsung (synchronous)
        document.getElementById('printNomor').textContent = nomor;
        document.getElementById('printPoli').textContent = poli;

        // Format waktu manual (super cepat)
        const now = new Date();
        const d = String(now.getDate()).padStart(2, '0');
        const m = String(now.getMonth() + 1).padStart(2, '0');
        const y = now.getFullYear();
        const h = String(now.getHours()).padStart(2, '0');
        const i = String(now.getMinutes()).padStart(2, '0');
        const s = String(now.getSeconds()).padStart(2, '0');

        document.getElementById('printWaktu').textContent = `${d}/${m}/${y} ${h}:${i}:${s}`;

        // Print instant tanpa flag blocking
        window.print();
    }

    /**
     * Fungsi untuk mengambil nomor antrian
     * @param {string} kd_poli - Kode poliklinik
     * @param {string} nm_poli - Nama poliklinik (untuk tampilan)
     */
    function ambilAntrian(kd_poli, nm_poli) {
        // Kirim request ke server untuk membuat antrian baru
        fetch('api.php?action=ambil', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    kd_poli: kd_poli
                })
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // Langsung cetak tanpa notifikasi untuk kecepatan maksimal
                    printDirect(data.nomor, nm_poli);
                } else {
                    showMessage(data.message || 'Gagal mengambil antrian', 'error');
                }
            })
            .catch(error => {
                showMessage('Terjadi kesalahan: ' + error.message, 'error');
                console.error('Error:', error);
            });
    }

    /**
     * Fungsi untuk cek dan auto-reset data antrian di tengah malam atau hari berbeda
     */
    function checkAndResetData() {
        fetch('api.php?action=check_reset')
            .then(response => response.json())
            .then(data => {
                if (data.reset) {
                    console.log('Data antrian telah direset otomatis');
                }
            })
            .catch(error => {
                console.error('Error checking reset:', error);
            });
    }

    // Update waktu setiap detik
    updateDateTime();
    setInterval(updateDateTime, 1000);

    // Cek reset data setiap 60 detik
    checkAndResetData();
    setInterval(checkAndResetData, 60000);
    </script>
</body>

</html>