package rekammedis;

import simrskhanza.*;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import laporan.DlgBerkasRawat;
import rekammedis.MasterCariTemplateLaporanOperasi;
import java.util.Date;

public class RMLaporanOperasi1 extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Jurnal jur = new Jurnal();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement psrekening;
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private ResultSet rsrekening, rs, rs2;
    private int pilihan = 0;
    private boolean sukses = true;
    private double ttljmdokter = 0, ttljmpetugas = 0, ttlpendapatan = 0, ttlbhp = 0;
    private String Suspen_Piutang_Operasi_Ranap = "", Operasi_Ranap = "", Beban_Jasa_Medik_Dokter_Operasi_Ranap = "", Utang_Jasa_Medik_Dokter_Operasi_Ranap = "",
            Beban_Jasa_Medik_Paramedis_Operasi_Ranap = "", Utang_Jasa_Medik_Paramedis_Operasi_Ranap = "", HPP_Obat_Operasi_Ranap = "", Persediaan_Obat_Kamar_Operasi_Ranap = "",
            Suspen_Piutang_Operasi_Ralan = "", Operasi_Ralan = "", Beban_Jasa_Medik_Dokter_Operasi_Ralan = "", Utang_Jasa_Medik_Dokter_Operasi_Ralan = "",
            Beban_Jasa_Medik_Paramedis_Operasi_Ralan = "", Utang_Jasa_Medik_Paramedis_Operasi_Ralan = "", HPP_Obat_Operasi_Ralan = "", Persediaan_Obat_Kamar_Operasi_Ralan = "",
            status = "", tanggal = "", mem = "", norawat = "", sql = "", diagnosa_preop = "", diagnosa_postop = "", jaringan_dieksekusi = "", selesaioperasi = "", permintaan_pa = "", laporan_operasi = "", kode_paket = "",
            finger = "", kodeoperator = "";

    /**
     * Creates new form DlgProgramStudi
     *
     * @param parent
     * @param modal
     */
    public RMLaporanOperasi1(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode = new DefaultTableModel(null, new Object[]{
            "Tgl.Operasi", "No.Rawat", "Pasien", "Jns.Ans", "Nama Tindakan", "Operator 1", "Operator 2", "Operator 3", "Asisten Operator 1", "Asisten Operator 2", "Asisten Operator 3", "Instrumen", "Dokter Anak",
            "Perawat Resusitas", "Dokter Anestesi", "Asisten Anestesi 1", "Asisten Anestesi 2", "Bidan 1", "Bidan 2", "Bidan 3", "Perawat Luar", "Onloop 1",
            "Onloop 2", "Onloop 3", "Onloop 4", "Onloop 5", "Sewa OK/VK", "Alat", "Akomodasi", "N.M.S.", "Sarpras", "Dokter PJ Anak", "Dokter Umum", "Biaya Perawatan",
            "Mulai Operasi", "Diagnosa Pre-operatif", "Diagnosa Post-operatif", "Jaringan Yang di-Eksisi/-Insisi", "Kirim PA", "Selesai Operasi",
            "Laporan Operasi", "Kode Tindakan", "Kategori"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 43; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setWidth(0);
                column.setPreferredWidth(0);
            } else if (i == 1) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setWidth(0);
                column.setPreferredWidth(0);
            } else if (i == 2) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setWidth(0);
                column.setPreferredWidth(0);
            } else if (i == 3) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setWidth(0);
                column.setPreferredWidth(0);
            } else if (i == 4) {
                column.setPreferredWidth(200);
            } else if (i == 26) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setWidth(0);
                column.setPreferredWidth(0);
            } else if (i == 27) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setWidth(0);
                column.setPreferredWidth(0);
            } else if (i == 28) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setWidth(0);
                column.setPreferredWidth(0);
            } else if (i == 29) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setWidth(0);
                column.setPreferredWidth(0);
            } else if (i == 30) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setWidth(0);
                column.setPreferredWidth(0);
            } else if (i == 33) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setWidth(0);
                column.setPreferredWidth(0);
            } else if (i == 34) {
                column.setPreferredWidth(120);
            } else if (i == 35) {
                column.setPreferredWidth(150);
            } else if (i == 36) {
                column.setPreferredWidth(150);
            } else if (i == 37) {
                column.setPreferredWidth(170);
            } else if (i == 38) {
                column.setPreferredWidth(50);
            } else if (i == 39) {
                column.setPreferredWidth(120);
            } else if (i == 40) {
                column.setPreferredWidth(400);
            } else if (i == 41) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setWidth(0);
                column.setPreferredWidth(0);
            } else if (i == 42) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setWidth(0);
                column.setPreferredWidth(0);
            } else {
                column.setPreferredWidth(130);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        kdmem.setDocument(new batasInput((byte) 10).getKata(kdmem));
        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        PreOp.setDocument(new batasInput((int) 100).getKata(PreOp));
        PostOp.setDocument(new batasInput((int) 100).getKata(PostOp));
        Jaringan.setDocument(new batasInput((int) 100).getKata(Jaringan));
        Laporan.setDocument(new batasInput((int) 8000).getKata(Laporan));
        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
            });
        }

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        try {
            psrekening = koneksi.prepareStatement(
                    "select set_akun_ralan.Suspen_Piutang_Operasi_Ralan,set_akun_ralan.Operasi_Ralan,"
                    + "set_akun_ralan.Beban_Jasa_Medik_Dokter_Operasi_Ralan,set_akun_ralan.Utang_Jasa_Medik_Dokter_Operasi_Ralan,"
                    + "set_akun_ralan.Beban_Jasa_Medik_Paramedis_Operasi_Ralan,set_akun_ralan.Utang_Jasa_Medik_Paramedis_Operasi_Ralan,"
                    + "set_akun_ralan.HPP_Obat_Operasi_Ralan,set_akun_ralan.Persediaan_Obat_Kamar_Operasi_Ralan from set_akun_ralan");
            try {
                rsrekening = psrekening.executeQuery();
                while (rsrekening.next()) {
                    Suspen_Piutang_Operasi_Ralan = rsrekening.getString("Suspen_Piutang_Operasi_Ralan");
                    Operasi_Ralan = rsrekening.getString("Operasi_Ralan");
                    Beban_Jasa_Medik_Dokter_Operasi_Ralan = rsrekening.getString("Beban_Jasa_Medik_Dokter_Operasi_Ralan");
                    Utang_Jasa_Medik_Dokter_Operasi_Ralan = rsrekening.getString("Utang_Jasa_Medik_Dokter_Operasi_Ralan");
                    Beban_Jasa_Medik_Paramedis_Operasi_Ralan = rsrekening.getString("Beban_Jasa_Medik_Paramedis_Operasi_Ralan");
                    Utang_Jasa_Medik_Paramedis_Operasi_Ralan = rsrekening.getString("Utang_Jasa_Medik_Paramedis_Operasi_Ralan");
                    HPP_Obat_Operasi_Ralan = rsrekening.getString("HPP_Obat_Operasi_Ralan");
                    Persediaan_Obat_Kamar_Operasi_Ralan = rsrekening.getString("Persediaan_Obat_Kamar_Operasi_Ralan");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : " + e);
            } finally {
                if (rsrekening != null) {
                    rsrekening.close();
                }
                if (psrekening != null) {
                    psrekening.close();
                }
            }

            psrekening = koneksi.prepareStatement(
                    "select set_akun_ranap.Suspen_Piutang_Operasi_Ranap,set_akun_ranap.Operasi_Ranap,"
                    + "set_akun_ranap.Beban_Jasa_Medik_Dokter_Operasi_Ranap,set_akun_ranap.Utang_Jasa_Medik_Dokter_Operasi_Ranap,"
                    + "set_akun_ranap.Beban_Jasa_Medik_Paramedis_Operasi_Ranap,set_akun_ranap.Utang_Jasa_Medik_Paramedis_Operasi_Ranap,"
                    + "set_akun_ranap.HPP_Obat_Operasi_Ranap from set_akun_ranap");
            try {
                rsrekening = psrekening.executeQuery();
                while (rsrekening.next()) {
                    Suspen_Piutang_Operasi_Ranap = rsrekening.getString("Suspen_Piutang_Operasi_Ranap");
                    Operasi_Ranap = rsrekening.getString("Operasi_Ranap");
                    Beban_Jasa_Medik_Dokter_Operasi_Ranap = rsrekening.getString("Beban_Jasa_Medik_Dokter_Operasi_Ranap");
                    Utang_Jasa_Medik_Dokter_Operasi_Ranap = rsrekening.getString("Utang_Jasa_Medik_Dokter_Operasi_Ranap");
                    Beban_Jasa_Medik_Paramedis_Operasi_Ranap = rsrekening.getString("Beban_Jasa_Medik_Paramedis_Operasi_Ranap");
                    Utang_Jasa_Medik_Paramedis_Operasi_Ranap = rsrekening.getString("Utang_Jasa_Medik_Paramedis_Operasi_Ranap");
                    HPP_Obat_Operasi_Ranap = rsrekening.getString("HPP_Obat_Operasi_Ranap");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : " + e);
            } finally {
                if (rsrekening != null) {
                    rsrekening.close();
                }
                if (psrekening != null) {
                    psrekening.close();
                }
            }

            psrekening = koneksi.prepareStatement("select set_akun_ranap2.Persediaan_Obat_Kamar_Operasi_Ranap from set_akun_ranap2");
            try {
                rsrekening = psrekening.executeQuery();
                while (rsrekening.next()) {
                    Persediaan_Obat_Kamar_Operasi_Ranap = rsrekening.getString("Persediaan_Obat_Kamar_Operasi_Ranap");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : " + e);
            } finally {
                if (rsrekening != null) {
                    rsrekening.close();
                }
                if (psrekening != null) {
                    psrekening.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private double total = 0;
    private int no = 0;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnHapusLaporanOperasi = new javax.swing.JMenuItem();
        MnPrintLaporanOperasi = new javax.swing.JMenuItem();
        MnLaporanOperasi = new javax.swing.JMenuItem();
        ppBerkasDigital = new javax.swing.JMenuItem();
        WindowLaporan = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        panelGlass6 = new widget.panelisi();
        btnAmbilPhoto1 = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnPrint1 = new widget.Button();
        BtnCloseIn5 = new widget.Button();
        Scroll3 = new widget.ScrollPane();
        Laporan = new widget.TextArea();
        panelGlass7 = new widget.panelisi();
        label12 = new widget.Label();
        tgl2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        PreOp = new widget.TextBox();
        PostOp = new widget.TextBox();
        jLabel7 = new widget.Label();
        jLabel8 = new widget.Label();
        Jaringan = new widget.TextBox();
        DikirimPA = new widget.ComboBox();
        jLabel9 = new widget.Label();
        KdTindakan = new widget.Label();
        jLabel27 = new widget.Label();
        Tindakan = new widget.TextBox();
        label16 = new widget.Label();
        tgl3 = new widget.Tanggal();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label13 = new widget.Label();
        kdmem = new widget.TextBox();
        nmmem = new widget.TextBox();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label15 = new widget.Label();
        NoRawat = new widget.TextBox();
        jLabel5 = new widget.Label();
        Kategori = new widget.ComboBox();
        jLabel4 = new widget.Label();
        Jenis = new widget.TextBox();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnHapusLaporanOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusLaporanOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusLaporanOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusLaporanOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusLaporanOperasi.setText("Hapus Laporan Operasi");
        MnHapusLaporanOperasi.setName("MnHapusLaporanOperasi"); // NOI18N
        MnHapusLaporanOperasi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusLaporanOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusLaporanOperasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnHapusLaporanOperasi);

        MnPrintLaporanOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnPrintLaporanOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPrintLaporanOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnPrintLaporanOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPrintLaporanOperasi.setText("Cetak Laporan Operasi Pasien");
        MnPrintLaporanOperasi.setName("MnPrintLaporanOperasi"); // NOI18N
        MnPrintLaporanOperasi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPrintLaporanOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPrintLaporanOperasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPrintLaporanOperasi);

        MnLaporanOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanOperasi.setText("Laporan Operasi Pasien");
        MnLaporanOperasi.setName("MnLaporanOperasi"); // NOI18N
        MnLaporanOperasi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnLaporanOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanOperasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLaporanOperasi);

        ppBerkasDigital.setBackground(new java.awt.Color(255, 255, 254));
        ppBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkasDigital.setForeground(new java.awt.Color(50, 50, 50));
        ppBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkasDigital.setText("Berkas Digital Perawatan");
        ppBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkasDigital.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkasDigital.setName("ppBerkasDigital"); // NOI18N
        ppBerkasDigital.setPreferredSize(new java.awt.Dimension(220, 26));
        ppBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasDigitalBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBerkasDigital);

        WindowLaporan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowLaporan.setName("WindowLaporan"); // NOI18N
        WindowLaporan.setUndecorated(true);
        WindowLaporan.setResizable(false);
        WindowLaporan.getContentPane().setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Laporan Operasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        btnAmbilPhoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnAmbilPhoto1.setMnemonic('U');
        btnAmbilPhoto1.setText("Template");
        btnAmbilPhoto1.setToolTipText("Alt+U");
        btnAmbilPhoto1.setName("btnAmbilPhoto1"); // NOI18N
        btnAmbilPhoto1.setPreferredSize(new java.awt.Dimension(105, 30));
        btnAmbilPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilPhoto1ActionPerformed(evt);
            }
        });
        panelGlass6.add(btnAmbilPhoto1);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('U');
        BtnSimpan.setText("Update");
        BtnSimpan.setToolTipText("Alt+U");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass6.add(BtnSimpan);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setText("Cetak");
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        BtnPrint1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrint1KeyPressed(evt);
            }
        });
        panelGlass6.add(BtnPrint1);

        BtnCloseIn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn5.setMnemonic('U');
        BtnCloseIn5.setText("Tutup");
        BtnCloseIn5.setToolTipText("Alt+U");
        BtnCloseIn5.setName("BtnCloseIn5"); // NOI18N
        BtnCloseIn5.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnCloseIn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn5ActionPerformed(evt);
            }
        });
        panelGlass6.add(BtnCloseIn5);

        internalFrame6.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        Scroll3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Laporan :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        Scroll3.setName("Scroll3"); // NOI18N

        Laporan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        Laporan.setColumns(20);
        Laporan.setRows(5);
        Laporan.setName("Laporan"); // NOI18N
        Scroll3.setViewportView(Laporan);

        internalFrame6.add(Scroll3, java.awt.BorderLayout.CENTER);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(300, 55));
        panelGlass7.setLayout(null);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label12.setText("Selesai Operasi :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass7.add(label12);
        label12.setBounds(10, 100, 200, 23);

        tgl2.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        tgl2.setName("tgl2"); // NOI18N
        panelGlass7.add(tgl2);
        tgl2.setBounds(30, 120, 150, 23);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Diagnosis Pre-operatif :");
        jLabel6.setName("jLabel6"); // NOI18N
        panelGlass7.add(jLabel6);
        jLabel6.setBounds(10, 150, 200, 23);

        PreOp.setHighlighter(null);
        PreOp.setName("PreOp"); // NOI18N
        PreOp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PreOpKeyPressed(evt);
            }
        });
        panelGlass7.add(PreOp);
        PreOp.setBounds(30, 170, 256, 23);

        PostOp.setHighlighter(null);
        PostOp.setName("PostOp"); // NOI18N
        PostOp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PostOpKeyPressed(evt);
            }
        });
        panelGlass7.add(PostOp);
        PostOp.setBounds(30, 220, 256, 23);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Diagnosis Post-operatif :");
        jLabel7.setName("jLabel7"); // NOI18N
        panelGlass7.add(jLabel7);
        jLabel7.setBounds(10, 200, 200, 23);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Jaringan di-Eksisi / -Insisi :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelGlass7.add(jLabel8);
        jLabel8.setBounds(10, 250, 200, 23);

        Jaringan.setHighlighter(null);
        Jaringan.setName("Jaringan"); // NOI18N
        Jaringan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JaringanKeyPressed(evt);
            }
        });
        panelGlass7.add(Jaringan);
        Jaringan.setBounds(30, 270, 256, 23);

        DikirimPA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        DikirimPA.setName("DikirimPA"); // NOI18N
        DikirimPA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DikirimPAKeyPressed(evt);
            }
        });
        panelGlass7.add(DikirimPA);
        DikirimPA.setBounds(30, 320, 130, 23);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Dikirim Pemeriksaan PA :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelGlass7.add(jLabel9);
        jLabel9.setBounds(10, 300, 200, 23);

        KdTindakan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        KdTindakan.setText(".");
        KdTindakan.setName("KdTindakan"); // NOI18N
        panelGlass7.add(KdTindakan);
        KdTindakan.setBounds(70, 0, 210, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Tindakan :");
        jLabel27.setName("jLabel27"); // NOI18N
        panelGlass7.add(jLabel27);
        jLabel27.setBounds(10, 0, 80, 23);

        Tindakan.setEnabled(false);
        Tindakan.setHighlighter(null);
        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
        panelGlass7.add(Tindakan);
        Tindakan.setBounds(30, 20, 256, 23);

        label16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label16.setText("Mulai Operasi :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass7.add(label16);
        label16.setBounds(10, 50, 200, 23);

        tgl3.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        tgl3.setName("tgl3"); // NOI18N
        panelGlass7.add(tgl3);
        tgl3.setBounds(30, 70, 150, 23);

        internalFrame6.add(panelGlass7, java.awt.BorderLayout.WEST);

        WindowLaporan.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Laporan Operasi/VK ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 70, 23);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl1);
        Tgl1.setBounds(74, 40, 100, 23);

        label13.setText("Pasien :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(385, 10, 60, 23);

        kdmem.setEnabled(false);
        kdmem.setName("kdmem"); // NOI18N
        kdmem.setPreferredSize(new java.awt.Dimension(80, 23));
        kdmem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdmemKeyPressed(evt);
            }
        });
        panelisi3.add(kdmem);
        kdmem.setBounds(449, 10, 80, 23);

        nmmem.setEditable(false);
        nmmem.setEnabled(false);
        nmmem.setName("nmmem"); // NOI18N
        nmmem.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmmem);
        nmmem.setBounds(531, 10, 240, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label18);
        label18.setBounds(173, 40, 30, 23);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl2);
        Tgl2.setBounds(200, 40, 100, 23);

        label15.setText("No.Rawat :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 70, 23);

        NoRawat.setName("NoRawat"); // NOI18N
        NoRawat.setPreferredSize(new java.awt.Dimension(207, 23));
        NoRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRawatKeyPressed(evt);
            }
        });
        panelisi3.add(NoRawat);
        NoRawat.setBounds(74, 10, 226, 23);

        jLabel5.setText("Kategori :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelisi3.add(jLabel5);
        jLabel5.setBounds(385, 40, 60, 23);

        Kategori.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Khusus", "Besar", "Sedang", "Kecil", "Elektive", "Emergency" }));
        Kategori.setEnabled(false);
        Kategori.setName("Kategori"); // NOI18N
        Kategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KategoriKeyPressed(evt);
            }
        });
        panelisi3.add(Kategori);
        Kategori.setBounds(450, 40, 122, 23);

        jLabel4.setText("Jenis Anasthesi :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelisi3.add(jLabel4);
        jLabel4.setBounds(580, 40, 90, 23);

        Jenis.setEnabled(false);
        Jenis.setHighlighter(null);
        Jenis.setName("Jenis"); // NOI18N
        Jenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisKeyPressed(evt);
            }
        });
        panelisi3.add(Jenis);
        Jenis.setBounds(678, 40, 92, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelisi1.add(BtnCari);

        label9.setText("Record :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(55, 30));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(155, 30));
        panelisi1.add(LTotal);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelisi1.add(BtnAll);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelisi1.add(BtnPrint);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelisi1.add(BtnKeluar);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, kdmem, Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void kdmemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdmemKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=?", nmmem, kdmem.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=?", nmmem, kdmem.getText());
            Tgl2.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=?", nmmem, kdmem.getText());
            TCari.requestFocus();
        }
    }//GEN-LAST:event_kdmemKeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1, kdmem);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private String safe(Object o) {
        // kalau mau satu spasi ganti "" jadi " "
        return (o == null) ? "" : o.toString().trim();
    }

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            BtnCariActionPerformed(evt);
            for (int i = 0; i < tbDokter.getRowCount(); i++) {
                Object cekLaporan = tbDokter.getValueAt(i, 34);
                String nilaiLaporan = (cekLaporan == null) ? "" : cekLaporan.toString().trim();
                if (nilaiLaporan.isEmpty()) {
                    JOptionPane.showMessageDialog(rootPane,
                            "Masih ada laporan operasi yang belum terisi ..! ");
                    return;
                }
            }

            if (tabMode.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            } else if (tabMode.getRowCount() != 0) {
                Sequel.queryu("delete from temporary where temp37='" + akses.getalamatip() + "'");
                int row = tabMode.getRowCount();
                for (int i = 0; i < row; i++) {
                    Sequel.menyimpan("temporary", "'" + i + "','"
                            + tabMode.getValueAt(i, 0).toString() + "','"
                            + tabMode.getValueAt(i, 1).toString() + "','"
                            + tabMode.getValueAt(i, 2).toString() + "','"
                            + tabMode.getValueAt(i, 3).toString() + "','"
                            + tabMode.getValueAt(i, 4).toString() + "','"
                            + tabMode.getValueAt(i, 33).toString() + "','"
                            + tabMode.getValueAt(i, 34).toString() + "','"
                            + tabMode.getValueAt(i, 35).toString() + "','"
                            + tabMode.getValueAt(i, 36).toString() + "','"
                            + tabMode.getValueAt(i, 37).toString() + "','"
                            + tabMode.getValueAt(i, 38).toString() + "','"
                            + tabMode.getValueAt(i, 39).toString() + "','"
                            + tabMode.getValueAt(i, 40).toString() + "','"
                            + " ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' " 
                            + akses.getalamatip() + "'", "Transaksi operasi");
                }

                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                Valid.MyReportqry("rptOperasi1.jasper", "report", "::[ Transaksi Operasi ]::", "select * from temporary where temporary.temp37='" + akses.getalamatip() + "' order by temporary.no", param);
            }
        } finally {
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnAll, BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
        dokter.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnPrint, Tgl1);
        }
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDokterKeyPressed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDokterMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void NoRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRawatKeyPressed
        Valid.pindah(evt, BtnKeluar, kdmem);
    }//GEN-LAST:event_NoRawatKeyPressed

    private void MnLaporanOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanOperasiActionPerformed
        if (tbDokter.getSelectedRow() > -1) {
            Object kodepaket = tbDokter.getValueAt(tbDokter.getSelectedRow(), 41);

            if (kodepaket != null && !kodepaket.toString().trim().isEmpty()) {
                try {
                    rs2 = koneksi.prepareStatement(
                            "select operasi.kode_paket,operasi.tgl_operasi,paket_operasi.nm_perawatan,laporan_operasi.tanggal,laporan_operasi.diagnosa_preop,laporan_operasi.diagnosa_postop,laporan_operasi.jaringan_dieksekusi,laporan_operasi.selesaioperasi,laporan_operasi.permintaan_pa,"
                            + "laporan_operasi.laporan_operasi from operasi inner join paket_operasi on paket_operasi.kode_paket=operasi.kode_paket left join laporan_operasi on operasi.kode_paket=laporan_operasi.kode_paket "
                            + "where operasi.no_rawat='" + NoRawat.getText() + "' and operasi.kode_paket='" + kodepaket.toString() + "'").executeQuery();
                    if (rs2.next()) {
                        if (rs2.getString("laporan_operasi") != null) {
                            tgl3.setDate(rs2.getTimestamp("tanggal"));
                            PreOp.setText(rs2.getString("diagnosa_preop"));
                            PostOp.setText(rs2.getString("diagnosa_postop"));
                            Jaringan.setText(rs2.getString("jaringan_dieksekusi"));
                            tgl2.setDate(rs2.getTimestamp("selesaioperasi"));
                            DikirimPA.setSelectedItem(rs2.getString("permintaan_pa"));
                            Laporan.setText(rs2.getString("laporan_operasi"));
                            KdTindakan.setText(rs2.getString("kode_paket"));
                            Tindakan.setText(rs2.getString("nm_perawatan"));

                            BtnSimpan.setText("Update");
                        } else {
                            emptTeks();
                            tgl3.setDate(rs2.getTimestamp("tgl_operasi"));
                            KdTindakan.setText(rs2.getString("kode_paket"));
                            Tindakan.setText(rs2.getString("nm_perawatan"));

                            Timestamp tsNow = new Timestamp(System.currentTimeMillis());
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                            String nowStr = sdf.format(tsNow);
                            tgl2.setSelectedItem(nowStr);

                            BtnSimpan.setText("Simpan");
                        }
                    }

                    if (rs2 != null) {
                        rs2.close();
                    }

                    WindowLaporan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    WindowLaporan.setLocationRelativeTo(internalFrame1);
                    WindowLaporan.setVisible(true);

                } catch (Exception e) {
                    System.out.println("Notif : " + e);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data dengan Kode Paket yang valid!");
            }
        }
    }//GEN-LAST:event_MnLaporanOperasiActionPerformed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (!Laporan.getText().trim().equals("")) {
            try {
                PreparedStatement cek = koneksi.prepareStatement(
                        "select no_rawat from laporan_operasi where no_rawat=? and kode_paket=?");
                cek.setString(1, NoRawat.getText());
                cek.setString(2, KdTindakan.getText());
                ResultSet rs = cek.executeQuery();

                if (rs.next()) {
                    Sequel.queryu(
                            "update laporan_operasi set no_rawat='" + NoRawat.getText() + "',"
                            + "tanggal='" + Valid.SetTgl(tgl3.getSelectedItem() + "") + " " + tgl3.getSelectedItem().toString().substring(11, 19) + "',"
                            + "diagnosa_preop='" + PreOp.getText() + "',"
                            + "diagnosa_postop='" + PostOp.getText() + "',"
                            + "jaringan_dieksekusi='" + Jaringan.getText() + "',"
                            + "selesaioperasi='" + Valid.SetTgl(tgl2.getSelectedItem() + "") + " " + tgl2.getSelectedItem().toString().substring(11, 19) + "',"
                            + "permintaan_pa='" + DikirimPA.getSelectedItem().toString() + "',"
                            + "laporan_operasi='" + Laporan.getText() + "' "
                            + "where no_rawat='" + NoRawat.getText() + "' and kode_paket='" + KdTindakan.getText() + "' ");
                    JOptionPane.showMessageDialog(rootPane, "Laporan operasi berhasil diupdate!");
                } else {
                    Sequel.menyimpan("laporan_operasi", "?,?,?,?,?,?,?,?,?", "laporan operasi", 9, new String[]{
                        NoRawat.getText(),
                        Valid.SetTgl(tgl3.getSelectedItem() + "") + " " + tgl3.getSelectedItem().toString().substring(11, 19),
                        PreOp.getText(),
                        PostOp.getText(),
                        Jaringan.getText(),
                        Valid.SetTgl(tgl2.getSelectedItem() + "") + " " + tgl2.getSelectedItem().toString().substring(11, 19),
                        DikirimPA.getSelectedItem().toString(),
                        Laporan.getText(),
                        KdTindakan.getText()});
                    JOptionPane.showMessageDialog(rootPane, "Laporan operasi berhasil disimpan!");
                }

                rs.close();
                cek.close();
                WindowLaporan.dispose();
                tampil();

            } catch (Exception e) {
                System.out.println("Notif Simpan: " + e);
                JOptionPane.showMessageDialog(rootPane, "Gagal menyimpan data: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Laporan operasi tidak boleh kosong!");
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        MnLaporanOperasiActionPerformed(evt);
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void BtnPrint1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnKeluar, BtnCari);
        }
    }//GEN-LAST:event_BtnPrint1KeyPressed

    private void BtnCloseIn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn5ActionPerformed
        WindowLaporan.dispose();
//        emptTeks();
    }//GEN-LAST:event_BtnCloseIn5ActionPerformed

    private void PreOpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PreOpKeyPressed
        Valid.pindah(evt, tgl2, PostOp);
    }//GEN-LAST:event_PreOpKeyPressed

    private void PostOpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PostOpKeyPressed
        Valid.pindah(evt, PreOp, Jaringan);
    }//GEN-LAST:event_PostOpKeyPressed

    private void JaringanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JaringanKeyPressed
        Valid.pindah(evt, PostOp, DikirimPA);
    }//GEN-LAST:event_JaringanKeyPressed

    private void DikirimPAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DikirimPAKeyPressed
        Valid.pindah(evt, Jaringan, Laporan);
    }//GEN-LAST:event_DikirimPAKeyPressed

    private void ppBerkasDigitalBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasDigitalBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else {
            if (tbDokter.getSelectedRow() > -1) {
                if (!tbDokter.getValueAt(tbDokter.getSelectedRow(), 1).toString().equals("")) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgBerkasRawat berkas = new DlgBerkasRawat(null, true);
                    berkas.setJudul("::[ Berkas Digital Perawatan ]::", "berkasrawat/pages");
                    try {
                        if (akses.gethapus_berkas_digital_perawatan() == true) {
                            berkas.loadURL("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/" + "berkasrawat/login2.php?act=login&usere=" + koneksiDB.USERHYBRIDWEB() + "&passwordte=" + koneksiDB.PASHYBRIDWEB() + "&no_rawat=" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 1).toString());
                        } else {
                            berkas.loadURL("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/" + "berkasrawat/login2nonhapus.php?act=login&usere=" + koneksiDB.USERHYBRIDWEB() + "&passwordte=" + koneksiDB.PASHYBRIDWEB() + "&no_rawat=" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 1).toString());
                        }
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : " + ex);
                    }

                    berkas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    berkas.setLocationRelativeTo(internalFrame1);
                    berkas.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppBerkasDigitalBtnPrintActionPerformed

    private void btnAmbilPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilPhoto1ActionPerformed
        MasterCariTemplateLaporanOperasi template = new MasterCariTemplateLaporanOperasi(null, false);
        template.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (template.getTable().getSelectedRow() != -1) {
                    PreOp.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(), 2).toString());
                    PostOp.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(), 3).toString());
                    Jaringan.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(), 4).toString());
                    DikirimPA.setSelectedItem(template.getTable().getValueAt(template.getTable().getSelectedRow(), 5).toString());
                    Laporan.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(), 6).toString());
                    Laporan.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        template.emptTeks();
        template.isCek();
        template.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        template.setLocationRelativeTo(internalFrame1);
        template.setVisible(true);
    }//GEN-LAST:event_btnAmbilPhoto1ActionPerformed

    private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKeyPressed
        //        Valid.pindah(evt,SNCN,btnDokterBedah);
    }//GEN-LAST:event_TindakanKeyPressed

    private void MnPrintLaporanOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPrintLaporanOperasiActionPerformed
        if (tbDokter.getSelectedRow() > -1) {
            if (!tbDokter.getValueAt(tbDokter.getSelectedRow(), 41).toString().equals("")) {
                Object cekLaporan = tbDokter.getValueAt(tbDokter.getSelectedRow(), 34);
                if (cekLaporan == null || cekLaporan.toString().trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Laporan operasi belum terisi ..!!");
                    return;
                }

                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                param.put("norawat", NoRawat.getText());
                param.put("tanggaloperasi", tbDokter.getValueAt(tbDokter.getSelectedRow(), 34).toString());
                kodeoperator = Sequel.cariIsi("select operasi.operator1 from operasi inner join laporan_operasi on operasi.kode_paket=laporan_operasi.kode_paket where operasi.no_rawat='" + NoRawat.getText() + "' ");
                finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", kodeoperator);
                param.put("finger", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + tbDokter.getValueAt(tbDokter.getSelectedRow(), 5).toString() + "\nID " + (finger.equals("") ? kodeoperator : finger) + "\n" + Valid.SetTgl3(tbDokter.getValueAt(tbDokter.getSelectedRow(), 34).toString()));
                String tindakan = "";
                String ruangan = "";

                try {
                    try {
                        rs = koneksi.prepareStatement(
                                "select paket_operasi.nm_perawatan from laporan_operasi inner join paket_operasi on paket_operasi.kode_paket=laporan_operasi.kode_paket where "
                                + "laporan_operasi.no_rawat='" + NoRawat.getText() + "' and paket_operasi.kode_paket='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 41).toString() + "' ").executeQuery();
                        while (rs.next()) {
                            tindakan = rs.getString("nm_perawatan");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : " + e);
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif : " + e);
                }
                param.put("tindakan", tindakan);

                try {
                    try {
                        rs = koneksi.prepareStatement(
                                "select ruang_ok.nm_ruang_ok from booking_operasi "
                                + "inner join ruang_ok on booking_operasi.kd_ruang_ok=ruang_ok.kd_ruang_ok where "
                                + "booking_operasi.no_rawat='" + NoRawat.getText() + "' "
                        ).executeQuery();
                        while (rs.next()) {
                            ruangan = rs.getString("nm_ruang_ok");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : " + e);
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif : " + e);
                }
                param.put("ruangan", ruangan);

                if (Sequel.cariIsi("select reg_periksa.status_lanjut from reg_periksa where reg_periksa.no_rawat=?", NoRawat.getText()).equals("Ralan")) {
                    try {
                        try {
                            rs = koneksi.prepareStatement(
                                    "select pemeriksaan_ralan.no_rawat,pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.suhu_tubuh,"
                                    + "pemeriksaan_ralan.tensi,pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,pemeriksaan_ralan.tinggi,pemeriksaan_ralan.berat,"
                                    + "pemeriksaan_ralan.gcs,pemeriksaan_ralan.keluhan,pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi,pemeriksaan_ralan.rtl,"
                                    + "pemeriksaan_ralan.penilaian from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat='" + NoRawat.getText() + "' "
                                    + "and concat(pemeriksaan_ralan.tgl_perawatan,' ',pemeriksaan_ralan.jam_rawat) <= '" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 34).toString() + "' "
                                    + "order by pemeriksaan_ralan.tgl_perawatan desc,pemeriksaan_ralan.jam_rawat desc limit 1").executeQuery();
                            if (rs.next()) {
                                param.put("tgl_perawatan", rs.getDate("tgl_perawatan"));
                                param.put("jam_rawat", rs.getString("jam_rawat"));
                                param.put("alergi", rs.getString("alergi"));
                                param.put("keluhan", rs.getString("keluhan"));
                                param.put("pemeriksaan", rs.getString("pemeriksaan"));
                                param.put("penilaian", rs.getString("penilaian"));
                                param.put("rtl", rs.getString("rtl"));
                                param.put("ruang", Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat=?", rs.getString("no_rawat")));
                                param.put("suhu_tubuh", rs.getString("suhu_tubuh"));
                                param.put("tensi", rs.getString("tensi"));
                                param.put("tinggi", rs.getString("tinggi"));
                                param.put("berat", rs.getString("berat"));
                                param.put("nadi", rs.getString("nadi"));
                                param.put("respirasi", rs.getString("respirasi"));
                                param.put("gcs", rs.getString("gcs"));
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : " + e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : " + e);
                    }
                } else {
                    try {
                        try {
                            rs = koneksi.prepareStatement(
                                    "select pemeriksaan_ranap.no_rawat,pemeriksaan_ranap.tgl_perawatan,pemeriksaan_ranap.jam_rawat,pemeriksaan_ranap.suhu_tubuh,"
                                    + "pemeriksaan_ranap.tensi,pemeriksaan_ranap.nadi,pemeriksaan_ranap.respirasi,pemeriksaan_ranap.tinggi,pemeriksaan_ranap.berat,"
                                    + "pemeriksaan_ranap.gcs,pemeriksaan_ranap.keluhan,pemeriksaan_ranap.pemeriksaan,pemeriksaan_ranap.alergi,pemeriksaan_ranap.rtl,"
                                    + "pemeriksaan_ranap.penilaian from pemeriksaan_ranap where pemeriksaan_ranap.no_rawat='" + NoRawat.getText() + "' "
                                    + "and concat(pemeriksaan_ranap.tgl_perawatan,' ',pemeriksaan_ranap.jam_rawat) <= '" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 34).toString() + "' "
                                    + "order by pemeriksaan_ranap.tgl_perawatan desc,pemeriksaan_ranap.jam_rawat desc limit 1").executeQuery();
                            if (rs.next()) {
                                param.put("tgl_perawatan", rs.getDate("tgl_perawatan"));
                                param.put("jam_rawat", rs.getString("jam_rawat"));
                                param.put("alergi", rs.getString("alergi"));
                                param.put("keluhan", rs.getString("keluhan"));
                                param.put("pemeriksaan", rs.getString("pemeriksaan"));
                                param.put("penilaian", rs.getString("penilaian"));
                                param.put("rtl", rs.getString("rtl"));
                                param.put("ruang", Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal and kamar_inap.kd_kamar=kamar.kd_kamar where no_rawat=? order by tgl_masuk desc limit 1 ", rs.getString("no_rawat")));
                                param.put("suhu_tubuh", rs.getString("suhu_tubuh"));
                                param.put("tensi", rs.getString("tensi"));
                                param.put("tinggi", rs.getString("tinggi"));
                                param.put("berat", rs.getString("berat"));
                                param.put("nadi", rs.getString("nadi"));
                                param.put("respirasi", rs.getString("respirasi"));
                                param.put("gcs", rs.getString("gcs"));
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : " + e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : " + e);
                    }
                }
                Valid.MyReport("rptLaporanOperasi1.jasper", "report", "::[ Laporan Operasi ]::", param);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data, klik pada Nama Pemeriksaan ..!!");
            }
        }
    }//GEN-LAST:event_MnPrintLaporanOperasiActionPerformed

    private void MnHapusLaporanOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusLaporanOperasiActionPerformed
        if (tbDokter.getSelectedRow() > -1) {
            try {
                Sequel.queryu(
                        "delete from laporan_operasi "
                        + "where no_rawat='" + NoRawat.getText() + "' "
                        + "and kode_paket='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 41).toString() + "' ");
                JOptionPane.showMessageDialog(rootPane, "Laporan operasi berhasil dihapus!");
                tampil();
            } catch (Exception e) {
                System.out.println("Notif Hapus : " + e);
                JOptionPane.showMessageDialog(rootPane, "Gagal menghapus data: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang akan dihapus!");
        }
    }//GEN-LAST:event_MnHapusLaporanOperasiActionPerformed

    private void KategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KategoriKeyPressed

    }//GEN-LAST:event_KategoriKeyPressed

    private void JenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisKeyPressed

    }//GEN-LAST:event_JenisKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMLaporanOperasi1 dialog = new RMLaporanOperasi1(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnCloseIn5;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnSimpan;
    private widget.ComboBox DikirimPA;
    private widget.TextBox Jaringan;
    private widget.TextBox Jenis;
    private widget.ComboBox Kategori;
    private widget.TextBox Kd2;
    private widget.Label KdTindakan;
    private widget.Label LTotal;
    private widget.TextArea Laporan;
    private javax.swing.JMenuItem MnHapusLaporanOperasi;
    private javax.swing.JMenuItem MnLaporanOperasi;
    private javax.swing.JMenuItem MnPrintLaporanOperasi;
    private widget.TextBox NoRawat;
    private widget.TextBox PostOp;
    private widget.TextBox PreOp;
    private widget.ScrollPane Scroll3;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.TextBox Tindakan;
    private javax.swing.JDialog WindowLaporan;
    private widget.Button btnAmbilPhoto1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame6;
    private widget.Label jLabel27;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdmem;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmmem;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBerkasDigital;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    private widget.Tanggal tgl2;
    private widget.Tanggal tgl3;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        tanggal = "  operasi.tgl_operasi between '" + Valid.SetTgl(Tgl1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(Tgl2.getSelectedItem() + "") + " 23:59:59' ";
        mem = "";
        if (!kdmem.getText().equals("")) {
            mem = " and pasien.no_rkm_medis='" + kdmem.getText() + "' ";
        }
        norawat = "";
        if (!NoRawat.getText().equals("")) {
            norawat = " and operasi.no_rawat='" + NoRawat.getText() + "' ";
        }

        if (TCari.getText().trim().equals("") && kdmem.getText().trim().equals("") && NoRawat.getText().trim().equals("")) {
            sql = "select operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,operasi.jenis_anasthesi,operasi.kategori,"
                    + "operasi.tgl_operasi from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "
                    + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + " where " + tanggal + " group by operasi.no_rawat,operasi.tgl_operasi order by operasi.tgl_operasi,operasi.no_rawat ";
        } else {
            sql = "select operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,operasi.jenis_anasthesi,operasi.kategori,"
                    + "operasi.tgl_operasi from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "
                    + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + " where " + tanggal + mem + norawat + " and operasi.no_rawat like '%" + TCari.getText() + "%' or "
                    + tanggal + mem + norawat + " and reg_periksa.no_rkm_medis like '%" + TCari.getText() + "%' or "
                    + tanggal + mem + norawat + " and pasien.nm_pasien like '%" + TCari.getText() + "%' or "
                    + tanggal + mem + norawat + " and operasi.tgl_operasi like '%" + TCari.getText() + "%' or "
                    + tanggal + mem + norawat + " and operasi.jenis_anasthesi like '%" + TCari.getText() + "%'  "
                    + " group by operasi.no_rawat,operasi.tgl_operasi order by operasi.tgl_operasi,operasi.no_rawat ";
        }

        prosesCari(sql);
    }

    private void prosesCari(String sql) {
        Valid.tabelKosong(tabMode);
        try {
            rs = koneksi.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                total = 0;
                tanggal = "";
                diagnosa_preop = "";
                diagnosa_postop = "";
                jaringan_dieksekusi = "";
                selesaioperasi = "";
                permintaan_pa = "";
                laporan_operasi = "";
                kode_paket = "";
                rs2 = koneksi.prepareStatement(
                        "select operasi.operator1, operasi.operator2, operasi.operator3, operasi.asisten_operator1,"
                        + "operasi.asisten_operator2,operasi.asisten_operator3, operasi.instrumen, operasi.dokter_anak, operasi.perawaat_resusitas, "
                        + "operasi.dokter_anestesi, operasi.asisten_anestesi,operasi.asisten_anestesi2, operasi.bidan, operasi.bidan2, operasi.bidan3, operasi.perawat_luar, "
                        + "operasi.omloop,operasi.omloop2,operasi.omloop3,operasi.omloop4,operasi.omloop5,operasi.dokter_pjanak,operasi.dokter_umum,"
                        + "operasi.kode_paket,paket_operasi.nm_perawatan, operasi.biayaoperator1, operasi.biayaoperator2, operasi.biayaoperator3, "
                        + "operasi.biayaasisten_operator1, operasi.biayaasisten_operator2,operasi.biayaasisten_operator3, operasi.biayainstrumen, "
                        + "operasi.biayadokter_anak, operasi.biayaperawaat_resusitas, operasi.biayadokter_anestesi, "
                        + "operasi.biayaasisten_anestesi,operasi.biayaasisten_anestesi2, operasi.biayabidan,operasi.biayabidan2,operasi.biayabidan3, operasi.biayaperawat_luar, operasi.biayaalat,"
                        + "operasi.biayasewaok,operasi.akomodasi,operasi.bagian_rs,operasi.biaya_omloop,operasi.biaya_omloop2,"
                        + "operasi.biaya_omloop3,operasi.biaya_omloop4,operasi.biaya_omloop5,operasi.biayasarpras,operasi.biaya_dokter_pjanak,operasi.biaya_dokter_umum,"
                        + "(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+"
                        + "operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayaasisten_operator3+operasi.biayainstrumen+"
                        + "operasi.biayadokter_anak+operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+"
                        + "operasi.biayaasisten_anestesi+operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+"
                        + "operasi.biayaperawat_luar+operasi.biayaalat+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum+"
                        + "operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biaya_omloop4+operasi.biaya_omloop5+operasi.biayasarpras) as total,"
                        + "laporan_operasi.tanggal,laporan_operasi.diagnosa_preop,laporan_operasi.diagnosa_postop,laporan_operasi.jaringan_dieksekusi,laporan_operasi.selesaioperasi,laporan_operasi.permintaan_pa,laporan_operasi.laporan_operasi "
                        + "from operasi "
                        + "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "
                        + "left join laporan_operasi on operasi.kode_paket=laporan_operasi.kode_paket and operasi.no_rawat=laporan_operasi.no_rawat "
                        + "where operasi.no_rawat='" + rs.getString("no_rawat") + "' and operasi.tgl_operasi='" + rs.getString("tgl_operasi") + "'").executeQuery();
                no = 1;
                while (rs2.next()) {
                    tabMode.addRow(new Object[]{rs.getString("tgl_operasi"),
                        rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis") + ", " + rs.getString("nm_pasien"),
                        rs.getString("jenis_anasthesi"),
                        no + ". " + rs2.getString("nm_perawatan"),
                        dokter.tampil3(rs2.getString("operator1")),
                        dokter.tampil3(rs2.getString("operator2")),
                        dokter.tampil3(rs2.getString("operator3")),
                        petugas.tampil3(rs2.getString("asisten_operator1")),
                        petugas.tampil3(rs2.getString("asisten_operator2")),
                        petugas.tampil3(rs2.getString("asisten_operator3")),
                        petugas.tampil3(rs2.getString("instrumen")),
                        dokter.tampil3(rs2.getString("dokter_anak")),
                        petugas.tampil3(rs2.getString("perawaat_resusitas")),
                        dokter.tampil3(rs2.getString("dokter_anestesi")),
                        petugas.tampil3(rs2.getString("asisten_anestesi")),
                        petugas.tampil3(rs2.getString("asisten_anestesi2")),
                        petugas.tampil3(rs2.getString("bidan")),
                        petugas.tampil3(rs2.getString("bidan2")),
                        petugas.tampil3(rs2.getString("bidan3")),
                        petugas.tampil3(rs2.getString("perawat_luar")),
                        petugas.tampil3(rs2.getString("omloop")),
                        petugas.tampil3(rs2.getString("omloop2")),
                        petugas.tampil3(rs2.getString("omloop3")),
                        petugas.tampil3(rs2.getString("omloop4")),
                        petugas.tampil3(rs2.getString("omloop5")),
                        "",
                        "",
                        "",
                        "",
                        "",
                        dokter.tampil3(rs2.getString("dokter_pjanak")),
                        dokter.tampil3(rs2.getString("dokter_umum")),
                        "",
                        tanggal = rs2.getString("tanggal"),
                        diagnosa_preop = rs2.getString("diagnosa_preop"),
                        diagnosa_postop = rs2.getString("diagnosa_postop"),
                        jaringan_dieksekusi = rs2.getString("jaringan_dieksekusi"),
                        permintaan_pa = rs2.getString("permintaan_pa"),
                        selesaioperasi = rs2.getString("selesaioperasi"),
                        laporan_operasi = rs2.getString("laporan_operasi"),
                        kode_paket = rs2.getString("kode_paket"),
                        rs.getString("kategori")
                    }
                    );
                    no++;
                }
                if (rs2 != null) {
                    rs2.close();
                }
            }
            rs.last();
            LTotal.setText("" + rs.getRow());
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }

    }

    public void isCek() {
        MnHapusLaporanOperasi.setEnabled(akses.getoperasi());
        MnPrintLaporanOperasi.setEnabled(akses.getoperasi());
        MnLaporanOperasi.setEnabled(akses.getoperasi());
        ppBerkasDigital.setEnabled(akses.getberkas_digital_perawatan());
        BtnPrint.setEnabled(akses.getoperasi());

    }

    private void getData() {
        int row = tbDokter.getSelectedRow();
        if (row != -1) {
            Kd2.setText(tabMode.getValueAt(row, 0).toString());
            Jenis.setText(tabMode.getValueAt(row, 3).toString());
            Kategori.setSelectedItem(tabMode.getValueAt(row, 42).toString());

        }
    }

    public JTextField getTextField() {
        return Kd2;
    }

    public JButton getButton() {
        return BtnKeluar;
    }

    public void setPasien(String pasien) {
        NoRawat.setText(pasien);
    }

    public void setPasien(String pasien, Date tanggal) {
        NoRawat.setText(pasien);
        Tgl1.setDate(tanggal);
    }

    public void setDataPasien(String pasien, String namaPasien, String noRM) {
        NoRawat.setText(pasien);
        nmmem.setText(namaPasien);
        kdmem.setText(noRM);
    }

    public void emptTeks() {
        KdTindakan.setText("");
        Tindakan.setText("");
        PreOp.setText("");
        PostOp.setText("");
        Jaringan.setText("");
        Laporan.setText("");
        DikirimPA.setSelectedIndex(0);
    }

}
