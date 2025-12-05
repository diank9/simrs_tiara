<?php
/**
 * File: dashboard.php
 * Fungsi: Dashboard untuk menampilkan antrian yang sedang dipanggil
 */

require_once 'config.php';

// Ambil data setting untuk header
$setting = getSettings();

// Konfigurasi poliklinik - hanya kd_poli dan warna (maksimal 21)
// nm_poli akan diambil otomatis dari tabel poliklinik
$poliklinik_config = [
    ['kd_poli' => 'FMS', 'warna' => '#34495e'],
    ['kd_poli' => 'FRM', 'warna' => '#34495e'],
    ['kd_poli' => 'LAB', 'warna' => '#34495e'],
    ['kd_poli' => 'RAD', 'warna' => '#34495e'],
    ['kd_poli' => 'BED', 'warna' => '#34495e'], 
    // ['kd_poli' => 'JTG', 'warna' => '#34495e'],
    // ['kd_poli' => 'GIG', 'warna' => '#34495e'],
    // ['kd_poli' => 'MTA', 'warna' => '#34495e'],
    // ['kd_poli' => 'JWA', 'warna' => '#34495e'],
    // ['kd_poli' => 'URO', 'warna' => '#34495e'],
    // ['kd_poli' => 'OPD', 'warna' => '#34495e'], 
    // ['kd_poli' => 'INT', 'warna' => '#34495e'],
    // ['kd_poli' => 'THT', 'warna' => '#34495e'],
    // ['kd_poli' => 'PAR', 'warna' => '#34495e'],
    // ['kd_poli' => 'BMU', 'warna' => '#34495e'],
    // ['kd_poli' => 'SAR', 'warna' => '#34495e'],
    // ['kd_poli' => 'BSY', 'warna' => '#34495e'],
];

// Ambil data lengkap poliklinik dari database
$poliklinik = getPoliklinikData($poliklinik_config);
?>

<!DOCTYPE html>
<html lang="id">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Antrian - <?php echo $setting['nama_instansi'] ?? 'RS'; ?></title>
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
        padding-bottom: 60px;
        display: flex;
        flex-direction: column;
    }

    /* Header Fixed */
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

    /* Main Content - Selalu di tengah */
    .main-content {
        max-width: 1800px;
        width: 100%;
        margin: auto;
        padding: 20px;
        flex: 1;
        display: flex;
        flex-direction: column;
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

    .title {
        text-align: center;
        color: white;
        font-size: 32px;
        font-weight: 600;
        margin-bottom: 40px;
        text-transform: uppercase;
        letter-spacing: 2px;
    }

    /* Dashboard Grid - Menggunakan flex untuk auto-center */
    .dashboard-grid {
        display: flex;
        flex-wrap: wrap;
        gap: 25px;
        margin-bottom: 30px;
        justify-content: center;
        align-items: stretch;
        width: 100%;
        max-width: 100%;
    }

    /* Card Antrian - Width mengikuti container (min 3, max 21 poli) */
    .card-antrian {
        background: #546e7a;
        border-radius: 15px;
        padding: 25px 20px;
        text-align: center;
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
        transition: all 0.3s ease;
        min-height: 260px;
        flex: 1 1 auto;
        min-width: 200px;
        max-width: 350px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
    }

    /* Responsive card - adjust untuk 3-21 poli */
    @media (min-width: 1800px) {
        .card-antrian {
            flex: 1 1 calc((100% - (6 * 25px)) / 7);
            /* Max 7 kolom */
            max-width: calc((100% - (6 * 25px)) / 7);
        }
    }

    @media (min-width: 1400px) and (max-width: 1799px) {
        .card-antrian {
            flex: 1 1 calc((100% - (4 * 25px)) / 5);
            /* 5 kolom */
            max-width: calc((100% - (4 * 25px)) / 5);
        }
    }

    @media (min-width: 1024px) and (max-width: 1399px) {
        .card-antrian {
            flex: 1 1 calc((100% - (3 * 25px)) / 4);
            /* 4 kolom */
            max-width: calc((100% - (3 * 25px)) / 4);
        }
    }

    @media (min-width: 768px) and (max-width: 1023px) {
        .card-antrian {
            flex: 1 1 calc((100% - (2 * 25px)) / 3);
            /* Min 3 kolom */
            max-width: calc((100% - (2 * 25px)) / 3);
        }
    }

    @media (min-width: 576px) and (max-width: 767px) {
        .card-antrian {
            flex: 1 1 calc((100% - 25px) / 2);
            /* 2 kolom */
            max-width: calc((100% - 25px) / 2);
        }
    }

    @media (max-width: 575px) {
        .card-antrian {
            flex: 1 1 100%;
            /* 1 kolom full width */
            max-width: 100%;
        }
    }

    .card-antrian:hover {
        transform: translateY(-5px);
        box-shadow: 0 12px 24px rgba(0, 0, 0, 0.4);
    }

    .card-nama-poli {
        font-size: 22px;
        font-weight: bold;
        color: white;
        margin-bottom: 15px;
        text-transform: uppercase;
        min-height: 52px;
        /* Fixed height untuk 2 baris (22px * 2 + line-height) */
        display: flex;
        align-items: center;
        justify-content: center;
        line-height: 1.2;
    }

    .card-nomor-antrian {
        font-size: 70px;
        font-weight: bold;
        color: #fff;
        margin: 15px 0;
        text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
        line-height: 1;
    }

    .card-waiting {
        color: rgba(255, 255, 255, 0.7);
        font-size: 12px;
        margin-top: 10px;
        margin-bottom: 5px;
        font-weight: 600;
        letter-spacing: 1px;
    }

    .card-waiting-numbers {
        background: rgba(0, 0, 0, 0.2);
        border-radius: 8px;
        padding: 10px;
        color: white;
        font-size: 15px;
        font-weight: 600;
        min-height: 40px;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-wrap: wrap;
        gap: 8px;
    }

    .waiting-number {
        background: rgba(255, 255, 255, 0.2);
        padding: 4px 10px;
        border-radius: 5px;
        font-weight: bold;
    }

    @media (max-width: 767px) {
        .card-nama-poli {
            font-size: 18px;
            min-height: 44px;
            /* Adjust untuk font lebih kecil */
        }

        .card-nomor-antrian {
            font-size: 55px;
        }

        .card-waiting-numbers {
            font-size: 13px;
        }
    }

    /* Sound Control */
    .sound-control {
        text-align: center;
        margin-top: 20px;
    }

    .btn-sound {
        padding: 15px 40px;
        font-size: 18px;
        font-weight: bold;
        border: none;
        border-radius: 10px;
        cursor: pointer;
        transition: all 0.3s;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    }

    .btn-sound.active {
        background: #2ecc71;
        color: white;
    }

    .btn-sound.inactive {
        background: #e74c3c;
        color: white;
    }

    .btn-sound:hover {
        transform: translateY(-2px);
        box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3);
    }

    /* Footer */
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

    /* Responsive adjustments */
    @media (max-width: 768px) {
        .header {
            flex-direction: column;
            gap: 15px;
            padding: 15px 20px;
        }

        .header-right {
            width: 100%;
            text-align: center;
        }

        .card-nama-poli {
            font-size: 22px;
        }

        .card-nomor-antrian {
            font-size: 60px;
        }

        body {
            padding-top: 180px;
        }
    }
    </style>
</head>

<body>
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
            <h2 class="title">DASHBOARD ANTRIAN</h2>

            <div class="dashboard-grid" id="dashboardGrid">
                <!-- Card akan diisi oleh JavaScript -->
            </div>

            <div class="sound-control">
                <button class="btn-sound inactive" id="btnSound" onclick="toggleSound()">
                    🔇 VOICE DISABLED
                </button>
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
    // Konfigurasi poliklinik dari PHP
    const poliklinikConfig = <?php echo json_encode($poliklinik); ?>;

    // State untuk tracking antrian yang sudah dipanggil
    let previousData = {};
    let soundEnabled = false;

    // Queue untuk panggilan suara
    let speechQueue = [];
    let isSpeaking = false;

    /**
     * Fungsi untuk update tanggal dan waktu real-time
     */
    function updateDateTime() {
        const now = new Date();

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

        const hours = String(now.getHours()).padStart(2, '0');
        const minutes = String(now.getMinutes()).padStart(2, '0');
        const seconds = String(now.getSeconds()).padStart(2, '0');

        document.getElementById('currentTime').textContent =
            `${hours}:${minutes}:${seconds}`;
    }

    /**
     * Fungsi untuk toggle sound on/off
     */
    function toggleSound() {
        soundEnabled = !soundEnabled;
        const btn = document.getElementById('btnSound');

        if (soundEnabled) {
            btn.className = 'btn-sound active';
            btn.innerHTML = '🔊 VOICE ENABLED';

            // Ucapkan konfirmasi bahwa suara sudah aktif
            speakConfirmation('Sistem panggilan suara sudah diaktifkan');
        } else {
            btn.className = 'btn-sound inactive';
            btn.innerHTML = '🔇 VOICE DISABLED';

            // Hentikan semua speech yang sedang berjalan
            if ('speechSynthesis' in window) {
                window.speechSynthesis.cancel();
            }
        }
    }

    /**
     * Fungsi untuk mengucapkan konfirmasi
     * @param {string} text - Text yang akan diucapkan
     */
    function speakConfirmation(text) {
        if ('speechSynthesis' in window) {
            window.speechSynthesis.cancel();

            const utterance = new SpeechSynthesisUtterance();
            utterance.text = text;
            utterance.lang = 'id-ID';
            utterance.rate = 0.9;
            utterance.pitch = 1;
            utterance.volume = 1;

            window.speechSynthesis.speak(utterance);
        }
    }

    /**
     * Fungsi untuk memproses queue panggilan suara
     */
    function processSpeechQueue() {
        if (isSpeaking || speechQueue.length === 0 || !soundEnabled) {
            return;
        }

        isSpeaking = true;
        const call = speechQueue.shift();

        if ('speechSynthesis' in window) {
            const utterance = new SpeechSynthesisUtterance();

            // Pisahkan digit nomor untuk dibaca satu per satu
            const digits = call.nomor.split('').join(' ');

            // Text yang akan diucapkan
            utterance.text = `Nomor antrian ${digits}, ${call.nama}`;
            utterance.lang = 'id-ID';
            utterance.rate = 0.9;
            utterance.pitch = 1;
            utterance.volume = 1;

            // Event ketika selesai bicara
            utterance.onend = function() {
                isSpeaking = false;
                // Jeda 1 detik sebelum panggilan berikutnya
                setTimeout(() => {
                    processSpeechQueue();
                }, 1000);
            };

            // Event jika ada error
            utterance.onerror = function() {
                isSpeaking = false;
                processSpeechQueue();
            };

            window.speechSynthesis.speak(utterance);
        } else {
            isSpeaking = false;
        }
    }

    /**
     * Fungsi untuk menambahkan panggilan ke queue
     * @param {string} nomor - Nomor antrian
     * @param {string} namaPoli - Nama poliklinik
     */
    function speakAntrian(nomor, namaPoli) {
        if (!soundEnabled) return;

        // Tambahkan ke queue
        speechQueue.push({
            nomor: nomor,
            nama: namaPoli
        });

        // Proses queue
        processSpeechQueue();
    }

    /**
     * Fungsi untuk mengambil data antrian dari API
     */
    function fetchAntrianData() {
        fetch('api.php?action=dashboard')
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    updateDashboard(data.data);
                }
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    }

    /**
     * Fungsi untuk update tampilan dashboard
     * @param {object} data - Data antrian per poliklinik
     */
    function updateDashboard(data) {
        const grid = document.getElementById('dashboardGrid');
        grid.innerHTML = '';

        poliklinikConfig.forEach(poli => {
            const kd_poli = poli.kd_poli;
            const poliData = data[kd_poli] || {
                current: '---',
                waiting: []
            };

            // Cek apakah ada perubahan status menjadi 1 (dipanggil)
            if (previousData[kd_poli]) {
                // Jika nomor current berbeda dan bukan '---', berarti ada panggilan baru
                if (previousData[kd_poli].current !== poliData.current &&
                    poliData.current !== '---') {
                    speakAntrian(poliData.current, poli.nama);
                }
            } else if (poliData.current !== '---') {
                // Jika belum ada data sebelumnya tapi ada current, panggil juga
                speakAntrian(poliData.current, poli.nama);
            }

            // Simpan data saat ini untuk perbandingan berikutnya
            previousData[kd_poli] = poliData;

            // Buat card
            const card = document.createElement('div');
            card.className = 'card-antrian';
            card.style.background = poli.warna;

            // Ambil maksimal 5 nomor antrian yang menunggu
            const waitingDisplay = poliData.waiting.slice(0, 5);

            let waitingHTML = '';
            if (waitingDisplay.length > 0) {
                waitingHTML = waitingDisplay.map(num =>
                    `<span class="waiting-number">${num}</span>`
                ).join('');

                // Tambahkan indikator jika ada lebih dari 5
                if (poliData.waiting.length > 5) {
                    waitingHTML += `<span class="waiting-number">+${poliData.waiting.length - 5}</span>`;
                }
            } else {
                waitingHTML = '<span style="opacity: 0.6;">Tidak ada antrian</span>';
            }

            card.innerHTML = `
                    <div class="card-nama-poli">${poli.nama}</div>
                    <div class="card-nomor-antrian">${poliData.current}</div>
                    <div class="card-waiting">MENUNGGU ANTRIAN</div>
                    <div class="card-waiting-numbers">${waitingHTML}</div>
                `;

            grid.appendChild(card);
        });
    }

    // Update waktu setiap detik
    updateDateTime();
    setInterval(updateDateTime, 1000);

    // Fetch data antrian pertama kali
    fetchAntrianData();

    // Update data antrian setiap 1 detik untuk mengurangi delay
    setInterval(fetchAntrianData, 1000);
    </script>
</body>

</html>