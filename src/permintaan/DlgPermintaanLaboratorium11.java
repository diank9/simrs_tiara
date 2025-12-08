/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DlgPerawatan.java
 *
 * Created on May 23, 2010, 6:36:30 PM
 */
package permintaan;

import kepegawaian.DlgCariDokter;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgPermintaanLaboratorium11 extends javax.swing.JDialog {

    private final DefaultTableModel tabMode, tabMode2, tabMode3, tabModeMB, tabModeDetailMB;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement pstindakan, pstampil,
            psset_tarif;
    private ResultSet rstindakan, rstampil, rsset_tarif;
    private boolean[] pilih, pilih2;
    private String[] kode, nama, pemeriksaan2, satuan2, nilai_rujukan2, idtemplate2;
    private int jml = 0, i = 0, index = 0, jml2 = 0, jml3 = 0, i2 = 0, index2 = 0, jmlparsial = 0;
    private String aktifkanparsial = "no", norawatibu = "", kelas = "", kamar, namakamar, cara_bayar_lab = "Yes", kelas_lab = "Yes", status = "", la = "", ld = "", pa = "", pd = "", finger = "", jenisRawat = "";
    private boolean sukses = true;

    /**
     * Creates new form DlgPerawatan
     *
     * @param parent
     * @param modal
     */
    public DlgPermintaanLaboratorium11(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8, 1);
        setSize(885, 674);

        Object[] row = {
            "P", "Pemeriksaan", "Satuan", "Nilai Rujukan", "id_template", "Kode Jenis"};
        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        tbDetailPK.setModel(tabMode);
        //tampilPr();

        tbDetailPK.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbDetailPK.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbDetailPK.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(356);
            } else if (i == 2) {
                column.setPreferredWidth(50);
            } else if (i == 3) {
                column.setPreferredWidth(345);
            } else if (i == 4) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 5) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }

        tbDetailPK.setDefaultRenderer(Object.class, new WarnaTable());

        Object[] row2 = {"P", "Kode Periksa", "Nama Pemeriksaan"};
        tabMode2 = new DefaultTableModel(null, row2) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbTarifPK.setModel(tabMode2);

        tbTarifPK.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbTarifPK.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbTarifPK.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(100);
            } else if (i == 2) {
                column.setPreferredWidth(520);
            }
        }
        tbTarifPK.setDefaultRenderer(Object.class, new WarnaTable());

        Object[] row3 = {"P", "Kode Periksa", "Nama Pemeriksaan"};
        tabMode3 = new DefaultTableModel(null, row3) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbTarifPA.setModel(tabMode3);

        tbTarifPA.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbTarifPA.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbTarifPA.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(100);
            } else if (i == 2) {
                column.setPreferredWidth(652);
            }
        }
        tbTarifPA.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeDetailMB = new DefaultTableModel(null, new Object[]{"P", "Pemeriksaan", "Satuan", "Nilai Rujukan", "id_template", "Kode Jenis"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        tbDetailMB.setModel(tabModeDetailMB);
        //tampilPr();

        tbDetailMB.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbDetailMB.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbDetailMB.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(356);
            } else if (i == 2) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 3) {
                column.setPreferredWidth(345);
            } else if (i == 4) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 5) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbDetailMB.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeMB = new DefaultTableModel(null, new Object[]{"P", "Kode Periksa", "Nama Pemeriksaan"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbTarifMB.setModel(tabModeMB);

        tbTarifMB.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbTarifMB.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbTarifMB.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(100);
            } else if (i == 2) {
                column.setPreferredWidth(520);
            }
        }
        tbTarifMB.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        KodePerujuk.setDocument(new batasInput((byte) 20).getKata(KodePerujuk));
        Pemeriksaan.setDocument(new batasInput((int) 100).getKata(Pemeriksaan));
        PemeriksaanPA.setDocument(new batasInput((int) 100).getKata(PemeriksaanPA));
        PemeriksaanMB.setDocument(new batasInput((int) 100).getKata(PemeriksaanMB));
        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        TCariMB.setDocument(new batasInput((byte) 100).getKata(TCariMB));
        TNoPermintaanPK.setDocument(new batasInput((byte) 15).getKata(TNoPermintaanPK));
        TNoPermintaanPA.setDocument(new batasInput((int) 15).getKata(TNoPermintaanPA));
        TNoPermintaanMB.setDocument(new batasInput((int) 15).getKata(TNoPermintaanPA));
        InformasiTambahan.setDocument(new batasInput((int) 60).getKata(InformasiTambahan));
        DiagnosisKlinis.setDocument(new batasInput((int) 80).getKata(DiagnosisKlinis));
        DiperolehDengan.setDocument(new batasInput((int) 40).getKata(DiperolehDengan));
        LokasiPengambilan.setDocument(new batasInput((int) 40).getKata(LokasiPengambilan));
        Diawetkan.setDocument(new batasInput((int) 40).getKata(Diawetkan));
        DilakukanPA.setDocument(new batasInput((int) 100).getKata(DilakukanPA));
        NomorPA.setDocument(new batasInput((int) 20).getKata(NomorPA));
        DiagnosaPA.setDocument(new batasInput((int) 100).getKata(DiagnosaPA));

        if (koneksiDB.CARICEPAT().equals("aktif")) {
            Pemeriksaan.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (Pemeriksaan.getText().length() > 2) {
                        tampiltarif();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (Pemeriksaan.getText().length() > 2) {
                        tampiltarif();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (Pemeriksaan.getText().length() > 2) {
                        tampiltarif();
                    }
                }
            });
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
        ChkJln.setSelected(true);
        jam();

        try {
            psset_tarif = koneksi.prepareStatement("select set_tarif.cara_bayar_lab,set_tarif.kelas_lab from set_tarif");
            try {
                rsset_tarif = psset_tarif.executeQuery();
                if (rsset_tarif.next()) {
                    cara_bayar_lab = rsset_tarif.getString("cara_bayar_lab");
                    kelas_lab = rsset_tarif.getString("kelas_lab");
                } else {
                    cara_bayar_lab = "Yes";
                    kelas_lab = "Yes";
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (rsset_tarif != null) {
                    rsset_tarif.close();
                }
                if (psset_tarif != null) {
                    psset_tarif.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            aktifkanparsial = koneksiDB.AKTIFKANBILLINGPARSIAL();
        } catch (Exception ex) {
            aktifkanparsial = "no";
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Penjab = new widget.TextBox();
        buttonGroup1 = new javax.swing.ButtonGroup();
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppSemua = new javax.swing.JMenuItem();
        Jk = new widget.TextBox();
        Umur = new widget.TextBox();
        Alamat = new widget.TextBox();
        PopupMB = new javax.swing.JPopupMenu();
        ppBersihkan1 = new javax.swing.JMenuItem();
        ppSemua1 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        internalFrame2 = new widget.InternalFrame();
        FormInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        PanelInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel9 = new widget.Label();
        Tanggal = new widget.Tanggal();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        btnDokter = new widget.Button();
        jLabel15 = new widget.Label();
        KodePerujuk = new widget.TextBox();
        NmPerujuk = new widget.TextBox();
        jLabel5 = new widget.Label();
        InformasiTambahan = new widget.TextBox();
        DiagnosisKlinis = new widget.TextBox();
        jLabel7 = new widget.Label();
        TabRawat = new javax.swing.JTabbedPane();
        PanelCariUtama = new javax.swing.JPanel();
        Scroll = new widget.ScrollPane();
        tbDetailPK = new widget.Table();
        panelGlass11 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari2 = new widget.Button();
        jLabel11 = new widget.Label();
        Pemeriksaan = new widget.TextBox();
        Scroll1 = new widget.ScrollPane();
        tbTarifPK = new widget.Table();
        BtnCari1 = new widget.Button();
        jLabel4 = new widget.Label();
        TNoPermintaanPK = new widget.TextBox();
        PanelCariUtama1 = new javax.swing.JPanel();
        panelGlass12 = new widget.panelisi();
        jLabel8 = new widget.Label();
        PemeriksaanPA = new widget.TextBox();
        BtnCari3 = new widget.Button();
        jLabel13 = new widget.Label();
        TNoPermintaanPA = new widget.TextBox();
        jLabel12 = new widget.Label();
        DiperolehDengan = new widget.TextBox();
        jLabel14 = new widget.Label();
        LokasiPengambilan = new widget.TextBox();
        jLabel16 = new widget.Label();
        Diawetkan = new widget.TextBox();
        jLabel17 = new widget.Label();
        TanggalPA = new widget.Tanggal();
        jLabel18 = new widget.Label();
        DilakukanPA = new widget.TextBox();
        jLabel19 = new widget.Label();
        DiagnosaPA = new widget.TextBox();
        TanggalBahan = new widget.Tanggal();
        jLabel20 = new widget.Label();
        NomorPA = new widget.TextBox();
        jLabel21 = new widget.Label();
        Scroll2 = new widget.ScrollPane();
        tbTarifPA = new widget.Table();
        PanelCariUtama2 = new javax.swing.JPanel();
        Scroll3 = new widget.ScrollPane();
        tbDetailMB = new widget.Table();
        panelGlass13 = new widget.panelisi();
        jLabel22 = new widget.Label();
        TCariMB = new widget.TextBox();
        BtnCariMB = new widget.Button();
        jLabel23 = new widget.Label();
        PemeriksaanMB = new widget.TextBox();
        Scroll4 = new widget.ScrollPane();
        tbTarifMB = new widget.Table();
        BtnCari5 = new widget.Button();
        jLabel24 = new widget.Label();
        TNoPermintaanMB = new widget.TextBox();
        panelGlass8 = new widget.panelisi();
        BtnUpdated = new widget.Button();
        jLabel10 = new widget.Label();
        BtnKeluar = new widget.Button();

        Penjab.setEditable(false);
        Penjab.setFocusTraversalPolicyProvider(true);
        Penjab.setName("Penjab"); // NOI18N
        Penjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenjabKeyPressed(evt);
            }
        });

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemua.setForeground(new java.awt.Color(50, 50, 50));
        ppSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemua.setText("Pilih Semua");
        ppSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemua.setName("ppSemua"); // NOI18N
        ppSemua.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaActionPerformed(evt);
            }
        });
        Popup.add(ppSemua);

        Jk.setEditable(false);
        Jk.setFocusTraversalPolicyProvider(true);
        Jk.setName("Jk"); // NOI18N

        Umur.setEditable(false);
        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        Alamat.setEditable(false);
        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N

        PopupMB.setName("PopupMB"); // NOI18N

        ppBersihkan1.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan1.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan1.setText("Bersihkan Pilihan");
        ppBersihkan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan1.setName("ppBersihkan1"); // NOI18N
        ppBersihkan1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkan1ActionPerformed(evt);
            }
        });
        PopupMB.add(ppBersihkan1);

        ppSemua1.setBackground(new java.awt.Color(255, 255, 254));
        ppSemua1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemua1.setForeground(new java.awt.Color(50, 50, 50));
        ppSemua1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemua1.setText("Pilih Semua");
        ppSemua1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemua1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemua1.setName("ppSemua1"); // NOI18N
        ppSemua1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSemua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemua1ActionPerformed(evt);
            }
        });
        PopupMB.add(ppSemua1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Validasi Data Permintaan Laboratorium ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setOpaque(false);
        FormInput.setPreferredSize(new java.awt.Dimension(560, 126));
        FormInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setSelected(true);
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        FormInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setPreferredSize(new java.awt.Dimension(360, 168));
        PanelInput.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        PanelInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 92, 23);

        TNoRw.setEditable(false);
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        PanelInput.add(TNoRw);
        TNoRw.setBounds(95, 10, 128, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        PanelInput.add(TNoRM);
        TNoRM.setBounds(225, 10, 105, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        PanelInput.add(TPasien);
        TPasien.setBounds(332, 10, 440, 23);

        jLabel9.setText("Dokter Perujuk :");
        jLabel9.setName("jLabel9"); // NOI18N
        PanelInput.add(jLabel9);
        jLabel9.setBounds(0, 40, 92, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-12-2025" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalItemStateChanged(evt);
            }
        });
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        PanelInput.add(Tanggal);
        Tanggal.setBounds(460, 40, 90, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        PanelInput.add(CmbJam);
        CmbJam.setBounds(554, 40, 62, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        PanelInput.add(CmbMenit);
        CmbMenit.setBounds(619, 40, 62, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        PanelInput.add(CmbDetik);
        CmbDetik.setBounds(684, 40, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setEnabled(false);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        PanelInput.add(ChkJln);
        ChkJln.setBounds(749, 40, 23, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('4');
        btnDokter.setToolTipText("ALt+4");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        PanelInput.add(btnDokter);
        btnDokter.setBounds(359, 40, 28, 23);

        jLabel15.setText("Tanggal :");
        jLabel15.setName("jLabel15"); // NOI18N
        PanelInput.add(jLabel15);
        jLabel15.setBounds(390, 40, 67, 23);

        KodePerujuk.setName("KodePerujuk"); // NOI18N
        KodePerujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePerujukKeyPressed(evt);
            }
        });
        PanelInput.add(KodePerujuk);
        KodePerujuk.setBounds(95, 40, 80, 23);

        NmPerujuk.setEditable(false);
        NmPerujuk.setHighlighter(null);
        NmPerujuk.setName("NmPerujuk"); // NOI18N
        PanelInput.add(NmPerujuk);
        NmPerujuk.setBounds(177, 40, 180, 23);

        jLabel5.setText("Informasi Tambahan :");
        jLabel5.setName("jLabel5"); // NOI18N
        PanelInput.add(jLabel5);
        jLabel5.setBounds(400, 70, 130, 23);

        InformasiTambahan.setHighlighter(null);
        InformasiTambahan.setName("InformasiTambahan"); // NOI18N
        InformasiTambahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InformasiTambahanKeyPressed(evt);
            }
        });
        PanelInput.add(InformasiTambahan);
        InformasiTambahan.setBounds(533, 70, 239, 23);

        DiagnosisKlinis.setHighlighter(null);
        DiagnosisKlinis.setName("DiagnosisKlinis"); // NOI18N
        DiagnosisKlinis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKlinisKeyPressed(evt);
            }
        });
        PanelInput.add(DiagnosisKlinis);
        DiagnosisKlinis.setBounds(95, 70, 292, 23);

        jLabel7.setText("Indikasi/Klinis :");
        jLabel7.setName("jLabel7"); // NOI18N
        PanelInput.add(jLabel7);
        jLabel7.setBounds(0, 70, 92, 23);

        FormInput.add(PanelInput, java.awt.BorderLayout.CENTER);

        internalFrame2.add(FormInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        PanelCariUtama.setName("PanelCariUtama"); // NOI18N
        PanelCariUtama.setOpaque(false);
        PanelCariUtama.setPreferredSize(new java.awt.Dimension(100, 143));
        PanelCariUtama.setLayout(new java.awt.BorderLayout());

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbDetailPK.setComponentPopupMenu(Popup);
        tbDetailPK.setName("tbDetailPK"); // NOI18N
        Scroll.setViewportView(tbDetailPK);

        PanelCariUtama.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 157));
        panelGlass11.setLayout(null);

        jLabel6.setText("Detail Pemeriksaan :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(110, 23));
        panelGlass11.add(jLabel6);
        jLabel6.setBounds(4, 130, 110, 23);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(625, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass11.add(TCari);
        TCari.setBounds(117, 130, 623, 23);

        BtnCari2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari2.setMnemonic('4');
        BtnCari2.setToolTipText("Alt+4");
        BtnCari2.setName("BtnCari2"); // NOI18N
        BtnCari2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari2ActionPerformed(evt);
            }
        });
        BtnCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari2KeyPressed(evt);
            }
        });
        panelGlass11.add(BtnCari2);
        BtnCari2.setBounds(744, 130, 28, 23);

        jLabel11.setText("Pemeriksaan :");
        jLabel11.setName("jLabel11"); // NOI18N
        panelGlass11.add(jLabel11);
        jLabel11.setBounds(0, 10, 82, 23);

        Pemeriksaan.setHighlighter(null);
        Pemeriksaan.setName("Pemeriksaan"); // NOI18N
        Pemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanKeyPressed(evt);
            }
        });
        panelGlass11.add(Pemeriksaan);
        Pemeriksaan.setBounds(85, 10, 420, 23);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll1.setName("Scroll1"); // NOI18N

        tbTarifPK.setName("tbTarifPK"); // NOI18N
        tbTarifPK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTarifPKMouseClicked(evt);
            }
        });
        Scroll1.setViewportView(tbTarifPK);

        panelGlass11.add(Scroll1);
        Scroll1.setBounds(85, 35, 687, 90);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('1');
        BtnCari1.setToolTipText("Alt+1");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        panelGlass11.add(BtnCari1);
        BtnCari1.setBounds(507, 10, 28, 23);

        jLabel4.setText("No.Permintaan :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass11.add(jLabel4);
        jLabel4.setBounds(541, 10, 98, 23);

        TNoPermintaanPK.setHighlighter(null);
        TNoPermintaanPK.setName("TNoPermintaanPK"); // NOI18N
        TNoPermintaanPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoPermintaanPKKeyPressed(evt);
            }
        });
        panelGlass11.add(TNoPermintaanPK);
        TNoPermintaanPK.setBounds(642, 10, 130, 23);

        PanelCariUtama.add(panelGlass11, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Patologi Klinis", PanelCariUtama);

        PanelCariUtama1.setName("PanelCariUtama1"); // NOI18N
        PanelCariUtama1.setOpaque(false);
        PanelCariUtama1.setPreferredSize(new java.awt.Dimension(100, 143));
        PanelCariUtama1.setLayout(new java.awt.BorderLayout());

        panelGlass12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        panelGlass12.setName("panelGlass12"); // NOI18N
        panelGlass12.setPreferredSize(new java.awt.Dimension(44, 157));
        panelGlass12.setLayout(null);

        jLabel8.setText("Permintaan Pemeriksaan :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(110, 23));
        panelGlass12.add(jLabel8);
        jLabel8.setBounds(0, 130, 145, 23);

        PemeriksaanPA.setName("PemeriksaanPA"); // NOI18N
        PemeriksaanPA.setPreferredSize(new java.awt.Dimension(625, 23));
        PemeriksaanPA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanPAKeyPressed(evt);
            }
        });
        panelGlass12.add(PemeriksaanPA);
        PemeriksaanPA.setBounds(148, 130, 592, 23);

        BtnCari3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari3.setMnemonic('4');
        BtnCari3.setToolTipText("Alt+4");
        BtnCari3.setName("BtnCari3"); // NOI18N
        BtnCari3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari3ActionPerformed(evt);
            }
        });
        BtnCari3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari3KeyPressed(evt);
            }
        });
        panelGlass12.add(BtnCari3);
        BtnCari3.setBounds(744, 130, 28, 23);

        jLabel13.setText("Dengan Diagnosa PA :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelGlass12.add(jLabel13);
        jLabel13.setBounds(309, 100, 120, 23);

        TNoPermintaanPA.setHighlighter(null);
        TNoPermintaanPA.setName("TNoPermintaanPA"); // NOI18N
        TNoPermintaanPA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoPermintaanPAKeyPressed(evt);
            }
        });
        panelGlass12.add(TNoPermintaanPA);
        TNoPermintaanPA.setBounds(101, 10, 130, 23);

        jLabel12.setText("Diperoleh Dengan :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelGlass12.add(jLabel12);
        jLabel12.setBounds(450, 10, 110, 23);

        DiperolehDengan.setHighlighter(null);
        DiperolehDengan.setName("DiperolehDengan"); // NOI18N
        DiperolehDengan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiperolehDenganKeyPressed(evt);
            }
        });
        panelGlass12.add(DiperolehDengan);
        DiperolehDengan.setBounds(563, 10, 209, 23);

        jLabel14.setText("Lokasi Pengambilan Jaringan :");
        jLabel14.setName("jLabel14"); // NOI18N
        panelGlass12.add(jLabel14);
        jLabel14.setBounds(0, 40, 165, 23);

        LokasiPengambilan.setHighlighter(null);
        LokasiPengambilan.setName("LokasiPengambilan"); // NOI18N
        LokasiPengambilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiPengambilanKeyPressed(evt);
            }
        });
        panelGlass12.add(LokasiPengambilan);
        LokasiPengambilan.setBounds(168, 40, 225, 23);

        jLabel16.setText("Diawetkan/Direndam Dengan :");
        jLabel16.setName("jLabel16"); // NOI18N
        panelGlass12.add(jLabel16);
        jLabel16.setBounds(400, 40, 160, 23);

        Diawetkan.setHighlighter(null);
        Diawetkan.setName("Diawetkan"); // NOI18N
        Diawetkan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiawetkanKeyPressed(evt);
            }
        });
        panelGlass12.add(Diawetkan);
        Diawetkan.setBounds(563, 40, 209, 23);

        jLabel17.setText("Pengambilan Bahan :");
        jLabel17.setName("jLabel17"); // NOI18N
        panelGlass12.add(jLabel17);
        jLabel17.setBounds(235, 10, 120, 23);

        TanggalPA.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-12-2025" }));
        TanggalPA.setDisplayFormat("dd-MM-yyyy");
        TanggalPA.setName("TanggalPA"); // NOI18N
        TanggalPA.setOpaque(false);
        TanggalPA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPAKeyPressed(evt);
            }
        });
        panelGlass12.add(TanggalPA);
        TanggalPA.setBounds(682, 70, 90, 23);

        jLabel18.setText("Pernah Dilakukan PA Di :");
        jLabel18.setName("jLabel18"); // NOI18N
        panelGlass12.add(jLabel18);
        jLabel18.setBounds(0, 70, 165, 23);

        DilakukanPA.setHighlighter(null);
        DilakukanPA.setName("DilakukanPA"); // NOI18N
        DilakukanPA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DilakukanPAKeyPressed(evt);
            }
        });
        panelGlass12.add(DilakukanPA);
        DilakukanPA.setBounds(168, 70, 400, 23);

        jLabel19.setText("Pada Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        panelGlass12.add(jLabel19);
        jLabel19.setBounds(589, 70, 90, 23);

        DiagnosaPA.setHighlighter(null);
        DiagnosaPA.setName("DiagnosaPA"); // NOI18N
        DiagnosaPA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaPAKeyPressed(evt);
            }
        });
        panelGlass12.add(DiagnosaPA);
        DiagnosaPA.setBounds(432, 100, 340, 23);

        TanggalBahan.setForeground(new java.awt.Color(50, 70, 50));
        TanggalBahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-12-2025" }));
        TanggalBahan.setDisplayFormat("dd-MM-yyyy");
        TanggalBahan.setName("TanggalBahan"); // NOI18N
        TanggalBahan.setOpaque(false);
        TanggalBahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalBahanKeyPressed(evt);
            }
        });
        panelGlass12.add(TanggalBahan);
        TanggalBahan.setBounds(358, 10, 90, 23);

        jLabel20.setText("No.Permintaan :");
        jLabel20.setName("jLabel20"); // NOI18N
        panelGlass12.add(jLabel20);
        jLabel20.setBounds(0, 10, 98, 23);

        NomorPA.setHighlighter(null);
        NomorPA.setName("NomorPA"); // NOI18N
        NomorPA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NomorPAKeyPressed(evt);
            }
        });
        panelGlass12.add(NomorPA);
        NomorPA.setBounds(168, 100, 130, 23);

        jLabel21.setText("Dengan Nomor PA :");
        jLabel21.setName("jLabel21"); // NOI18N
        panelGlass12.add(jLabel21);
        jLabel21.setBounds(25, 100, 140, 23);

        PanelCariUtama1.add(panelGlass12, java.awt.BorderLayout.PAGE_START);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setComponentPopupMenu(Popup);
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbTarifPA.setComponentPopupMenu(Popup);
        tbTarifPA.setName("tbTarifPA"); // NOI18N
        Scroll2.setViewportView(tbTarifPA);

        PanelCariUtama1.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Patologi Anatomi", PanelCariUtama1);

        PanelCariUtama2.setName("PanelCariUtama2"); // NOI18N
        PanelCariUtama2.setOpaque(false);
        PanelCariUtama2.setPreferredSize(new java.awt.Dimension(100, 143));
        PanelCariUtama2.setLayout(new java.awt.BorderLayout());

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll3.setComponentPopupMenu(PopupMB);
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbDetailMB.setComponentPopupMenu(PopupMB);
        tbDetailMB.setName("tbDetailMB"); // NOI18N
        Scroll3.setViewportView(tbDetailMB);

        PanelCariUtama2.add(Scroll3, java.awt.BorderLayout.CENTER);

        panelGlass13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        panelGlass13.setName("panelGlass13"); // NOI18N
        panelGlass13.setPreferredSize(new java.awt.Dimension(44, 157));
        panelGlass13.setLayout(null);

        jLabel22.setText("Detail Pemeriksaan :");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(110, 23));
        panelGlass13.add(jLabel22);
        jLabel22.setBounds(4, 130, 110, 23);

        TCariMB.setName("TCariMB"); // NOI18N
        TCariMB.setPreferredSize(new java.awt.Dimension(625, 23));
        TCariMB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMBKeyPressed(evt);
            }
        });
        panelGlass13.add(TCariMB);
        TCariMB.setBounds(117, 130, 623, 23);

        BtnCariMB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariMB.setMnemonic('4');
        BtnCariMB.setToolTipText("Alt+4");
        BtnCariMB.setName("BtnCariMB"); // NOI18N
        BtnCariMB.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariMB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariMBActionPerformed(evt);
            }
        });
        BtnCariMB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariMBKeyPressed(evt);
            }
        });
        panelGlass13.add(BtnCariMB);
        BtnCariMB.setBounds(744, 130, 28, 23);

        jLabel23.setText("Pemeriksaan :");
        jLabel23.setName("jLabel23"); // NOI18N
        panelGlass13.add(jLabel23);
        jLabel23.setBounds(0, 10, 82, 23);

        PemeriksaanMB.setHighlighter(null);
        PemeriksaanMB.setName("PemeriksaanMB"); // NOI18N
        PemeriksaanMB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanMBKeyPressed(evt);
            }
        });
        panelGlass13.add(PemeriksaanMB);
        PemeriksaanMB.setBounds(85, 10, 420, 23);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll4.setName("Scroll4"); // NOI18N

        tbTarifMB.setName("tbTarifMB"); // NOI18N
        tbTarifMB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTarifMBMouseClicked(evt);
            }
        });
        Scroll4.setViewportView(tbTarifMB);

        panelGlass13.add(Scroll4);
        Scroll4.setBounds(85, 35, 687, 90);

        BtnCari5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari5.setMnemonic('1');
        BtnCari5.setToolTipText("Alt+1");
        BtnCari5.setName("BtnCari5"); // NOI18N
        BtnCari5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari5ActionPerformed(evt);
            }
        });
        panelGlass13.add(BtnCari5);
        BtnCari5.setBounds(507, 10, 28, 23);

        jLabel24.setText("No.Permintaan :");
        jLabel24.setName("jLabel24"); // NOI18N
        panelGlass13.add(jLabel24);
        jLabel24.setBounds(541, 10, 98, 23);

        TNoPermintaanMB.setHighlighter(null);
        TNoPermintaanMB.setName("TNoPermintaanMB"); // NOI18N
        TNoPermintaanMB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoPermintaanMBKeyPressed(evt);
            }
        });
        panelGlass13.add(TNoPermintaanMB);
        TNoPermintaanMB.setBounds(642, 10, 130, 23);

        PanelCariUtama2.add(panelGlass13, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Mikrobiologi & Bio Molekuler", PanelCariUtama2);

        internalFrame2.add(TabRawat, java.awt.BorderLayout.CENTER);

        internalFrame1.add(internalFrame2, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnUpdated.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnUpdated.setMnemonic('S');
        BtnUpdated.setText("Update");
        BtnUpdated.setToolTipText("Alt+S");
        BtnUpdated.setName("BtnUpdated"); // NOI18N
        BtnUpdated.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnUpdated.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUpdatedActionPerformed(evt);
            }
        });
        BtnUpdated.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnUpdatedKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnUpdated);

        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(540, 30));
        panelGlass8.add(jLabel10);

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
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnUpdatedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUpdatedActionPerformed
        // Hitung jumlah pemeriksaan yang dipilih per kategori
        jml = 0;
        for (i = 0; i < tbTarifPK.getRowCount(); i++) {
            if (tbTarifPK.getValueAt(i, 0).toString().equals("true")) {
                jml++;
            }
        }
        for (i = 0; i < tbDetailPK.getRowCount(); i++) {
            if (tbDetailPK.getValueAt(i, 0).toString().equals("true")) {
                jml++;
            }
        }

        jml2 = 0;
        for (i = 0; i < tbTarifPA.getRowCount(); i++) {
            if (tbTarifPA.getValueAt(i, 0).toString().equals("true")) {
                jml2++;
            }
        }

        jml3 = 0;
        for (i = 0; i < tbTarifMB.getRowCount(); i++) {
            if (tbTarifMB.getValueAt(i, 0).toString().equals("true")) {
                jml3++;
            }
        }
        for (i = 0; i < tbDetailMB.getRowCount(); i++) {
            if (tbDetailMB.getValueAt(i, 0).toString().equals("true")) {
                jml3++;
            }
        }

        // Validasi input dasar
        if (TNoRw.getText().equals("") || TNoRM.getText().equals("") || TPasien.getText().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (KodePerujuk.getText().equals("") || NmPerujuk.getText().equals("")) {
            Valid.textKosong(KodePerujuk, "Dokter Perujuk");
        } else if (InformasiTambahan.getText().equals("")) {
            Valid.textKosong(InformasiTambahan, "Informasi Tambahan");
        } else if (DiagnosisKlinis.getText().equals("")) {
            Valid.textKosong(DiagnosisKlinis, "Indikasi/Diagnosis Klinis");
        } else if ((jml + jml2 + jml3) == 0) {
            Valid.textKosong(DiagnosisKlinis, "Data Permintaan");
        } else {
            // Validasi kategori permintaan
            String kategoriAsli = "";
            String noPermintaanAsli = "";

            // Tentukan kategori asli berdasarkan NoPermintaan yang terisi
            if (!TNoPermintaanPK.getText().trim().equals("")) {
                kategoriAsli = "PK";
                noPermintaanAsli = TNoPermintaanPK.getText().trim();
            } else if (!TNoPermintaanPA.getText().trim().equals("")) {
                kategoriAsli = "PA";
                noPermintaanAsli = TNoPermintaanPA.getText().trim();
            } else if (!TNoPermintaanMB.getText().trim().equals("")) {
                kategoriAsli = "MB";
                noPermintaanAsli = TNoPermintaanMB.getText().trim();
            }

            // Cek apakah kategori yang diupdate sama dengan kategori asli
            boolean kategoriValid = false;
            String pesanError = "";

            if (kategoriAsli.equals("PK")) {
                if (jml > 0 && jml2 == 0 && jml3 == 0) {
                    kategoriValid = true;
                } else {
                    pesanError = "No. permintaan " + noPermintaanAsli + " adalah kategori PK (Patologi Klinik).\n"
                            + "Update hanya bisa dilakukan pada kategori yang sama!";
                }
            } else if (kategoriAsli.equals("PA")) {
                if (jml2 > 0 && jml == 0 && jml3 == 0) {
                    kategoriValid = true;
                } else {
                    pesanError = "No. permintaan " + noPermintaanAsli + " adalah kategori PA (Patologi Anatomi).\n"
                            + "Update hanya bisa dilakukan pada kategori yang sama!";
                }
            } else if (kategoriAsli.equals("MB")) {
                if (jml3 > 0 && jml == 0 && jml2 == 0) {
                    kategoriValid = true;
                } else {
                    pesanError = "No. permintaan " + noPermintaanAsli + " adalah kategori MB (Mikrobiologi).\n"
                            + "Update hanya bisa dilakukan pada kategori yang sama!";
                }
            }

            if (!kategoriValid) {
                JOptionPane.showMessageDialog(rootPane, pesanError, "Peringatan Perubahan Kategori", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Lanjutkan proses update jika kategori valid
            jmlparsial = 0;
            if (aktifkanparsial.equals("yes")) {
                jmlparsial = Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?", Penjab.getText());
            }

            if (jmlparsial > 0) {
                update();
            } else {
                if (Sequel.cariRegistrasi(TNoRw.getText()) > 0) {
                    JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                    Pemeriksaan.requestFocus();
                } else {
                    update();
                }
            }
        }
}//GEN-LAST:event_BtnUpdatedActionPerformed

    private void BtnUpdatedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnUpdatedKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnUpdatedActionPerformed(null);
        } else {
            Valid.pindah(evt, Pemeriksaan, BtnKeluar);
        }
}//GEN-LAST:event_BtnUpdatedKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, TCari, Pemeriksaan);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

private void PemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        tampiltarif();
    } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
        BtnUpdated.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
        Tanggal.requestFocus();
    }
}//GEN-LAST:event_PemeriksaanKeyPressed

private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_ChkJlnActionPerformed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampiltarif();
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void PenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenjabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenjabKeyPressed

    private void tbTarifPKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTarifPKMouseClicked
        if (tabMode2.getRowCount() != 0) {
            try {
                Valid.tabelKosong(tabMode);
                tampil();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbTarifPKMouseClicked

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, KodePerujuk, Pemeriksaan);
    }//GEN-LAST:event_TanggalKeyPressed

    private void KodePerujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePerujukKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnDokterActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, Tanggal);
        }
    }//GEN-LAST:event_KodePerujukKeyPressed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        DlgCariDokter dokter = new DlgCariDokter(null, false);
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter.getTable().getSelectedRow() != -1) {
                    KodePerujuk.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    NmPerujuk.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                    KodePerujuk.requestFocus();
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
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        for (i = 0; i < tbDetailPK.getRowCount(); i++) {
            tbDetailPK.setValueAt(false, i, 0);
        }
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void ppSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaActionPerformed
        for (i = 0; i < tbDetailPK.getRowCount(); i++) {
            tbDetailPK.setValueAt(true, i, 0);
        }
    }//GEN-LAST:event_ppSemuaActionPerformed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCari2ActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCari2ActionPerformed

    private void BtnCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCari2ActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, Pemeriksaan);
        }
    }//GEN-LAST:event_BtnCari2KeyPressed

    private void TNoPermintaanPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoPermintaanPKKeyPressed
        Valid.pindah(evt, Pemeriksaan, TCari);
    }//GEN-LAST:event_TNoPermintaanPKKeyPressed

    private void InformasiTambahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InformasiTambahanKeyPressed
        if (TabRawat.getSelectedIndex() == 0) {
            Valid.pindah(evt, DiagnosisKlinis, Pemeriksaan);
        } else if (TabRawat.getSelectedIndex() == 1) {
            Valid.pindah(evt, DiagnosisKlinis, DiperolehDengan);
        } else if (TabRawat.getSelectedIndex() == 2) {
            Valid.pindah(evt, DiagnosisKlinis, PemeriksaanMB);
        }
    }//GEN-LAST:event_InformasiTambahanKeyPressed

    private void DiagnosisKlinisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKlinisKeyPressed
        Valid.pindah(evt, TCari, InformasiTambahan);
    }//GEN-LAST:event_DiagnosisKlinisKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if (TabRawat.getSelectedIndex() == 0) {
//            autoNomor();
        } else if (TabRawat.getSelectedIndex() == 1) {
//            autoNomor2();
        } else if (TabRawat.getSelectedIndex() == 1) {
//            autoNomor3();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void PemeriksaanPAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanPAKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tampiltarif2();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnUpdated.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            DiagnosaPA.requestFocus();
        }
    }//GEN-LAST:event_PemeriksaanPAKeyPressed

    private void BtnCari3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari3ActionPerformed
        tampiltarif2();
    }//GEN-LAST:event_BtnCari3ActionPerformed

    private void BtnCari3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari3KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCari3ActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, PemeriksaanPA);
        }
    }//GEN-LAST:event_BtnCari3KeyPressed

    private void TNoPermintaanPAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoPermintaanPAKeyPressed
        Valid.pindah(evt, InformasiTambahan, TanggalBahan);
    }//GEN-LAST:event_TNoPermintaanPAKeyPressed

    private void DiperolehDenganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiperolehDenganKeyPressed
        Valid.pindah(evt, InformasiTambahan, LokasiPengambilan);
    }//GEN-LAST:event_DiperolehDenganKeyPressed

    private void LokasiPengambilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiPengambilanKeyPressed
        Valid.pindah(evt, DiperolehDengan, Diawetkan);
    }//GEN-LAST:event_LokasiPengambilanKeyPressed

    private void DiawetkanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiawetkanKeyPressed
        Valid.pindah(evt, LokasiPengambilan, DilakukanPA);
    }//GEN-LAST:event_DiawetkanKeyPressed

    private void TanggalPAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPAKeyPressed
        Valid.pindah(evt, DilakukanPA, NomorPA);
    }//GEN-LAST:event_TanggalPAKeyPressed

    private void DilakukanPAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DilakukanPAKeyPressed
        Valid.pindah(evt, Diawetkan, TanggalPA);
    }//GEN-LAST:event_DilakukanPAKeyPressed

    private void DiagnosaPAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaPAKeyPressed
        Valid.pindah(evt, NomorPA, PemeriksaanPA);
    }//GEN-LAST:event_DiagnosaPAKeyPressed

    private void TanggalBahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalBahanKeyPressed
        Valid.pindah(evt, TNoPermintaanPA, DiperolehDengan);
    }//GEN-LAST:event_TanggalBahanKeyPressed

    private void NomorPAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomorPAKeyPressed
        Valid.pindah(evt, TanggalBahan, DiagnosaPA);
    }//GEN-LAST:event_NomorPAKeyPressed

    private void TanggalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalItemStateChanged
        try {
//            autoNomor();
//            autoNomor2();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_TanggalItemStateChanged

    private void TCariMBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariMBKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariMBActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariMBKeyPressed

    private void BtnCariMBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariMBActionPerformed
        tampildetailmb();
    }//GEN-LAST:event_BtnCariMBActionPerformed

    private void BtnCariMBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariMBKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariMBActionPerformed(null);
        } else {
            Valid.pindah(evt, TCariMB, PemeriksaanMB);
        }
    }//GEN-LAST:event_BtnCariMBKeyPressed

    private void PemeriksaanMBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanMBKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tampiltarifmb();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnUpdated.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Tanggal.requestFocus();
        }
    }//GEN-LAST:event_PemeriksaanMBKeyPressed

    private void tbTarifMBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTarifMBMouseClicked
        if (tabModeMB.getRowCount() != 0) {
            try {
                Valid.tabelKosong(tabModeDetailMB);
                tampildetailmb();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbTarifMBMouseClicked

    private void BtnCari5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari5ActionPerformed
        tampiltarifmb();
    }//GEN-LAST:event_BtnCari5ActionPerformed

    private void TNoPermintaanMBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoPermintaanMBKeyPressed
        Valid.pindah(evt, PemeriksaanMB, TCariMB);
    }//GEN-LAST:event_TNoPermintaanMBKeyPressed

    private void ppBersihkan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkan1ActionPerformed
        for (i = 0; i < tbDetailMB.getRowCount(); i++) {
            tbDetailMB.setValueAt(false, i, 0);
        }
    }//GEN-LAST:event_ppBersihkan1ActionPerformed

    private void ppSemua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemua1ActionPerformed
        for (i = 0; i < tbDetailMB.getRowCount(); i++) {
            tbDetailMB.setValueAt(true, i, 0);
        }
    }//GEN-LAST:event_ppSemua1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPermintaanLaboratorium11 dialog = new DlgPermintaanLaboratorium11(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alamat;
    private widget.Button BtnCari1;
    private widget.Button BtnCari2;
    private widget.Button BtnCari3;
    private widget.Button BtnCari5;
    private widget.Button BtnCariMB;
    private widget.Button BtnKeluar;
    private widget.Button BtnUpdated;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private widget.TextBox DiagnosaPA;
    private widget.TextBox DiagnosisKlinis;
    private widget.TextBox Diawetkan;
    private widget.TextBox DilakukanPA;
    private widget.TextBox DiperolehDengan;
    private javax.swing.JPanel FormInput;
    private widget.TextBox InformasiTambahan;
    private widget.TextBox Jk;
    private widget.TextBox KodePerujuk;
    private widget.TextBox LokasiPengambilan;
    private widget.TextBox NmPerujuk;
    private widget.TextBox NomorPA;
    private javax.swing.JPanel PanelCariUtama;
    private javax.swing.JPanel PanelCariUtama1;
    private javax.swing.JPanel PanelCariUtama2;
    private widget.PanelBiasa PanelInput;
    private widget.TextBox Pemeriksaan;
    private widget.TextBox PemeriksaanMB;
    private widget.TextBox PemeriksaanPA;
    private widget.TextBox Penjab;
    private javax.swing.JPopupMenu Popup;
    private javax.swing.JPopupMenu PopupMB;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.TextBox TCari;
    private widget.TextBox TCariMB;
    private widget.TextBox TNoPermintaanMB;
    private widget.TextBox TNoPermintaanPA;
    private widget.TextBox TNoPermintaanPK;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TanggalBahan;
    private widget.Tanggal TanggalPA;
    private widget.TextBox Umur;
    private widget.Button btnDokter;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass12;
    private widget.panelisi panelGlass13;
    private widget.panelisi panelGlass8;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppBersihkan1;
    private javax.swing.JMenuItem ppSemua;
    private javax.swing.JMenuItem ppSemua1;
    private widget.Table tbDetailMB;
    private widget.Table tbDetailPK;
    private widget.Table tbTarifMB;
    private widget.Table tbTarifPA;
    private widget.Table tbTarifPK;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        try {
            jml2 = 0;
            for (i2 = 0; i2 < tbDetailPK.getRowCount(); i2++) {
                if (tbDetailPK.getValueAt(i2, 0).toString().equals("true")) {
                    jml2++;
                }
            }

            pilih2 = null;
            pilih2 = new boolean[jml2];
            pemeriksaan2 = null;
            pemeriksaan2 = new String[jml2];
            satuan2 = null;
            satuan2 = new String[jml2];
            nilai_rujukan2 = null;
            nilai_rujukan2 = new String[jml2];
            idtemplate2 = null;
            idtemplate2 = new String[jml2];
            kode = null;
            kode = new String[jml2];

            index2 = 0;
            for (i2 = 0; i2 < tbDetailPK.getRowCount(); i2++) {
                if (tbDetailPK.getValueAt(i2, 0).toString().equals("true")) {
                    pilih2[index2] = true;
                    pemeriksaan2[index2] = tbDetailPK.getValueAt(i2, 1).toString();
                    satuan2[index2] = tbDetailPK.getValueAt(i2, 2).toString();
                    nilai_rujukan2[index2] = tbDetailPK.getValueAt(i2, 3).toString();
                    idtemplate2[index2] = tbDetailPK.getValueAt(i2, 4).toString();
                    kode[index2] = tbDetailPK.getValueAt(i2, 5).toString();
                    index2++;
                }
            }

            Valid.tabelKosong(tabMode);

            for (i2 = 0; i2 < jml2; i2++) {
                tabMode.addRow(new Object[]{
                    pilih2[i2], pemeriksaan2[i2], satuan2[i2], nilai_rujukan2[i2], idtemplate2[i2], kode[i2]
                });
            }

            for (i2 = 0; i2 < tbTarifPK.getRowCount(); i2++) {
                if (tbTarifPK.getValueAt(i2, 0).toString().equals("true")) {
                    tabMode.addRow(new Object[]{false, tbTarifPK.getValueAt(i2, 2).toString(), "", "", ""});
                    pstampil = koneksi.prepareStatement("select template_laboratorium.id_template,template_laboratorium.Pemeriksaan,template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa from template_laboratorium where template_laboratorium.kd_jenis_prw=? and template_laboratorium.Pemeriksaan like ? order by template_laboratorium.urut");
                    try {
                        pstampil.setString(1, tbTarifPK.getValueAt(i2, 1).toString());
                        pstampil.setString(2, "%" + TCari.getText().trim() + "%");
                        rstampil = pstampil.executeQuery();
                        while (rstampil.next()) {
                            la = "";
                            ld = "";
                            pa = "";
                            pd = "";
                            if (!rstampil.getString("nilai_rujukan_ld").equals("")) {
                                ld = "LD : " + rstampil.getString("nilai_rujukan_ld");
                            }
                            if (!rstampil.getString("nilai_rujukan_la").equals("")) {
                                la = ", LA : " + rstampil.getString("nilai_rujukan_la");
                            }
                            if (!rstampil.getString("nilai_rujukan_pa").equals("")) {
                                pd = ", PD : " + rstampil.getString("nilai_rujukan_pd");
                            }
                            if (!rstampil.getString("nilai_rujukan_pd").equals("")) {
                                pa = " PA : " + rstampil.getString("nilai_rujukan_pa");
                            }
                            tabMode.addRow(new Object[]{
                                false, "   " + rstampil.getString("Pemeriksaan"),
                                rstampil.getString("satuan"),
                                ld + la + pd + pa,
                                rstampil.getString("id_template"), tbTarifPK.getValueAt(i2, 1).toString()
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rstampil != null) {
                            rstampil.close();
                        }
                        if (pstampil != null) {
                            pstampil.close();
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("Error Detail : " + e);
        }
    }

    private void tampildetailmb() {
        try {
            jml3 = 0;
            for (i2 = 0; i2 < tbDetailMB.getRowCount(); i2++) {
                if (tbDetailMB.getValueAt(i2, 0).toString().equals("true")) {
                    jml3++;
                }
            }

            pilih2 = null;
            pilih2 = new boolean[jml3];
            pemeriksaan2 = null;
            pemeriksaan2 = new String[jml3];
            satuan2 = null;
            satuan2 = new String[jml3];
            nilai_rujukan2 = null;
            nilai_rujukan2 = new String[jml3];
            idtemplate2 = null;
            idtemplate2 = new String[jml3];
            kode = null;
            kode = new String[jml3];

            index2 = 0;
            for (i2 = 0; i2 < tbDetailMB.getRowCount(); i2++) {
                if (tbDetailMB.getValueAt(i2, 0).toString().equals("true")) {
                    pilih2[index2] = true;
                    pemeriksaan2[index2] = tbDetailMB.getValueAt(i2, 1).toString();
                    satuan2[index2] = tbDetailMB.getValueAt(i2, 2).toString();
                    nilai_rujukan2[index2] = tbDetailMB.getValueAt(i2, 3).toString();
                    idtemplate2[index2] = tbDetailMB.getValueAt(i2, 4).toString();
                    kode[index2] = tbDetailMB.getValueAt(i2, 5).toString();
                    index2++;
                }
            }

            Valid.tabelKosong(tabModeDetailMB);

            for (i2 = 0; i2 < jml3; i2++) {
                tabModeDetailMB.addRow(new Object[]{
                    pilih2[i2], pemeriksaan2[i2], satuan2[i2], nilai_rujukan2[i2], idtemplate2[i2], kode[i2]
                });
            }

            for (i2 = 0; i2 < tbTarifMB.getRowCount(); i2++) {
                if (tbTarifMB.getValueAt(i2, 0).toString().equals("true")) {
                    tabModeDetailMB.addRow(new Object[]{false, tbTarifMB.getValueAt(i2, 2).toString(), "", "", "", ""});
                    pstampil = koneksi.prepareStatement("select template_laboratorium.id_template,template_laboratorium.Pemeriksaan,template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa from template_laboratorium where template_laboratorium.kd_jenis_prw=? and template_laboratorium.Pemeriksaan like ? order by template_laboratorium.urut");
                    try {
                        pstampil.setString(1, tbTarifMB.getValueAt(i2, 1).toString());
                        pstampil.setString(2, "%" + TCariMB.getText().trim() + "%");
                        rstampil = pstampil.executeQuery();
                        while (rstampil.next()) {
                            la = "";
                            ld = "";
                            pa = "";
                            pd = "";
                            if (!rstampil.getString("nilai_rujukan_ld").equals("")) {
                                ld = "LD : " + rstampil.getString("nilai_rujukan_ld");
                            }
                            if (!rstampil.getString("nilai_rujukan_la").equals("")) {
                                la = ", LA : " + rstampil.getString("nilai_rujukan_la");
                            }
                            if (!rstampil.getString("nilai_rujukan_pa").equals("")) {
                                pd = ", PD : " + rstampil.getString("nilai_rujukan_pd");
                            }
                            if (!rstampil.getString("nilai_rujukan_pd").equals("")) {
                                pa = " PA : " + rstampil.getString("nilai_rujukan_pa");
                            }
                            tabModeDetailMB.addRow(new Object[]{
                                false, "   " + rstampil.getString("Pemeriksaan"),
                                rstampil.getString("satuan"),
                                ld + la + pd + pa,
                                rstampil.getString("id_template"), tbTarifMB.getValueAt(i2, 1).toString()
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rstampil != null) {
                            rstampil.close();
                        }
                        if (pstampil != null) {
                            pstampil.close();
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("Error Detail : " + e);
        }
    }

    public void emptTeks() {
        Pemeriksaan.setText("");
        PemeriksaanPA.setText("");
        PemeriksaanMB.setText("");
        TCari.setText("");
        TCariMB.setText("");
        DiperolehDengan.setText("");
        LokasiPengambilan.setText("");
        Diawetkan.setText("");
        DilakukanPA.setText("");
        NomorPA.setText("");
        DiagnosaPA.setText("");
        Tanggal.setDate(new Date());
        TanggalBahan.setDate(new Date());
        TanggalPA.setDate(new Date());
        DiagnosisKlinis.requestFocus();
//        autoNomor();
//        autoNomor2();
//        autoNomor3();
    }

    public void onCari() {
        TCari.requestFocus();
    }

    private void isRawat() {
        if (status.equals("Ranap")) {
            norawatibu = Sequel.cariIsi("select ranap_gabung.no_rawat from ranap_gabung where ranap_gabung.no_rawat2=?", TNoRw.getText());
            if (!norawatibu.equals("")) {
                kamar = Sequel.cariIsi("select ifnull(kamar_inap.kd_kamar,'') from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1", norawatibu);
                kelas = Sequel.cariIsi("select kamar.kelas from kamar inner join kamar_inap on kamar.kd_kamar=kamar_inap.kd_kamar where kamar_inap.no_rawat=? and kamar_inap.stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',kamar_inap.jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1", norawatibu);
            } else {
                kamar = Sequel.cariIsi("select ifnull(kamar_inap.kd_kamar,'') from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1", TNoRw.getText());
                kelas = Sequel.cariIsi("select kamar.kelas from kamar inner join kamar_inap on kamar.kd_kamar=kamar_inap.kd_kamar where kamar_inap.no_rawat=? and kamar_inap.stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',kamar_inap.jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1", TNoRw.getText());
            }
            namakamar = kamar + ", " + Sequel.cariIsi("select bangsal.nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal where kamar.kd_kamar=? ", kamar);
            kamar = "Kamar";
        } else if (status.equals("Ralan")) {
            kelas = "Rawat Jalan";
            kamar = "Poli";
            namakamar = Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli where reg_periksa.no_rawat=?", TNoRw.getText());
        }
    }

    private void isPsien() {
        try {
            pstindakan = koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,reg_periksa.kd_pj,reg_periksa.kd_dokter,dokter.nm_dokter,pasien.nm_pasien,pasien.jk,pasien.umur,"
                    + "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat "
                    + "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "
                    + "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "
                    + "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where no_rawat=?");
            try {
                pstindakan.setString(1, TNoRw.getText());
                rstindakan = pstindakan.executeQuery();
                while (rstindakan.next()) {
                    TNoRM.setText(rstindakan.getString("no_rkm_medis"));
                    Penjab.setText(rstindakan.getString("kd_pj"));
                    KodePerujuk.setText(rstindakan.getString("kd_dokter"));
                    NmPerujuk.setText(rstindakan.getString("nm_dokter"));
                    TPasien.setText(rstindakan.getString("nm_pasien"));
                    Jk.setText(rstindakan.getString("jk"));
                    Umur.setText(rstindakan.getString("umur"));
                    Alamat.setText(rstindakan.getString("alamat"));
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rstindakan != null) {
                    rstindakan.close();
                }
                if (pstindakan != null) {
                    pstindakan.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    private void isReset() {
        jml = tbTarifPK.getRowCount();
        for (i = 0; i < jml; i++) {
            tbTarifPK.setValueAt(false, i, 0);
        }
        Valid.tabelKosong(tabMode);

        jml = tbTarifPA.getRowCount();
        for (i = 0; i < jml; i++) {
            tbTarifPA.setValueAt(false, i, 0);
        }
        Valid.tabelKosong(tabMode3);

        jml3 = tbTarifMB.getRowCount();
        for (i = 0; i < jml3; i++) {
            tbTarifMB.setValueAt(false, i, 0);
        }
        Valid.tabelKosong(tabModeDetailMB);
    }

    private void jam() {
        ActionListener taskPerformer = new ActionListener() {
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;

            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if (ChkJln.isSelected() == true) {
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                } else if (ChkJln.isSelected() == false) {
                    nilai_jam = CmbJam.getSelectedIndex();
                    nilai_menit = CmbMenit.getSelectedIndex();
                    nilai_detik = CmbDetik.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                CmbJam.setSelectedItem(jam);
                CmbMenit.setSelectedItem(menit);
                CmbDetik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    public void setNoRm(String norwt, String posisi) {
        TNoRw.setText(norwt);
        this.status = posisi;
        isRawat();
        isPsien();
        isReset();
    }

    public void setNoRm(String norwt, String posisi, String kddokter, String nmdokter) {
        TNoRw.setText(norwt);
        this.status = posisi;
        isRawat();
        isPsien();
        isReset();
        KodePerujuk.setText(kddokter);
        NmPerujuk.setText(nmdokter);
    }

    public void isCek() {
        BtnUpdated.setEnabled(akses.getperiksa_lab());
//        BtnPrint.setEnabled(akses.getperiksa_lab());
//        BtnHapus.setEnabled(akses.getperiksa_lab());
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            FormInput.setPreferredSize(new Dimension(WIDTH, 126));
            PanelInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            FormInput.setPreferredSize(new Dimension(WIDTH, 20));
            PanelInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    private void tampiltarif() {
        try {
            Valid.tabelKosong(tabMode2);

            // 1. Ambil data pemeriksaan yang sudah dipilih/disimpan sebelumnya
            PreparedStatement psDetail = koneksi.prepareStatement(
                    "SELECT DISTINCT pdpl.kd_jenis_prw, jpl.nm_perawatan "
                    + "FROM permintaan_detail_permintaan_lab pdpl "
                    + "INNER JOIN jns_perawatan_lab jpl ON pdpl.kd_jenis_prw = jpl.kd_jenis_prw "
                    + "WHERE pdpl.noorder = ?"
            );
            psDetail.setString(1, TNoPermintaanPK.getText().trim()); // atau TNoPermintaanPA/TNoPermintaanMB sesuai tab
            ResultSet rsDetail = psDetail.executeQuery();

            // Simpan kode pemeriksaan yang sudah dipilih
            java.util.ArrayList<String> kodeYangSudahDipilih = new java.util.ArrayList<>();
            while (rsDetail.next()) {
                String kdJenisPrw = rsDetail.getString("kd_jenis_prw");
                String nmPerawatan = rsDetail.getString("nm_perawatan");
                tabMode2.addRow(new Object[]{true, kdJenisPrw, nmPerawatan}); // Checkbox TRUE
                kodeYangSudahDipilih.add(kdJenisPrw);
            }
            rsDetail.close();
            psDetail.close();

            // 2. Ambil semua data pemeriksaan yang tersedia
            if (cara_bayar_lab.equals("Yes") && kelas_lab.equals("No")) {
                pstindakan = koneksi.prepareStatement(
                        "SELECT jns_perawatan_lab.kd_jenis_prw, jns_perawatan_lab.nm_perawatan, penjab.png_jawab "
                        + "FROM jns_perawatan_lab INNER JOIN penjab ON penjab.kd_pj = jns_perawatan_lab.kd_pj "
                        + "WHERE jns_perawatan_lab.kategori = 'PK' AND jns_perawatan_lab.status = '1' "
                        + "AND (jns_perawatan_lab.kd_pj = ? OR jns_perawatan_lab.kd_pj = '-') "
                        + "AND (jns_perawatan_lab.kd_jenis_prw LIKE ? OR jns_perawatan_lab.nm_perawatan LIKE ?) "
                        + "ORDER BY jns_perawatan_lab.kd_jenis_prw"
                );
            } else if (cara_bayar_lab.equals("No") && kelas_lab.equals("No")) {
                pstindakan = koneksi.prepareStatement(
                        "SELECT jns_perawatan_lab.kd_jenis_prw, jns_perawatan_lab.nm_perawatan, penjab.png_jawab "
                        + "FROM jns_perawatan_lab INNER JOIN penjab ON penjab.kd_pj = jns_perawatan_lab.kd_pj "
                        + "WHERE jns_perawatan_lab.kategori = 'PK' AND jns_perawatan_lab.status = '1' "
                        + "AND (jns_perawatan_lab.kd_jenis_prw LIKE ? OR jns_perawatan_lab.nm_perawatan LIKE ?) "
                        + "ORDER BY jns_perawatan_lab.kd_jenis_prw"
                );
            } else if (cara_bayar_lab.equals("Yes") && kelas_lab.equals("Yes")) {
                pstindakan = koneksi.prepareStatement(
                        "SELECT jns_perawatan_lab.kd_jenis_prw, jns_perawatan_lab.nm_perawatan, penjab.png_jawab "
                        + "FROM jns_perawatan_lab INNER JOIN penjab ON penjab.kd_pj = jns_perawatan_lab.kd_pj "
                        + "WHERE jns_perawatan_lab.kategori = 'PK' AND jns_perawatan_lab.status = '1' "
                        + "AND (jns_perawatan_lab.kd_pj = ? OR jns_perawatan_lab.kd_pj = '-') "
                        + "AND (jns_perawatan_lab.kelas = ? OR jns_perawatan_lab.kelas = '-') "
                        + "AND (jns_perawatan_lab.kd_jenis_prw LIKE ? OR jns_perawatan_lab.nm_perawatan LIKE ?) "
                        + "ORDER BY jns_perawatan_lab.kd_jenis_prw"
                );
            } else if (cara_bayar_lab.equals("No") && kelas_lab.equals("Yes")) {
                pstindakan = koneksi.prepareStatement(
                        "SELECT jns_perawatan_lab.kd_jenis_prw, jns_perawatan_lab.nm_perawatan, penjab.png_jawab "
                        + "FROM jns_perawatan_lab INNER JOIN penjab ON penjab.kd_pj = jns_perawatan_lab.kd_pj "
                        + "WHERE jns_perawatan_lab.kategori = 'PK' AND jns_perawatan_lab.status = '1' "
                        + "AND (jns_perawatan_lab.kelas = ? OR jns_perawatan_lab.kelas = '-') "
                        + "AND (jns_perawatan_lab.kd_jenis_prw LIKE ? OR jns_perawatan_lab.nm_perawatan LIKE ?) "
                        + "ORDER BY jns_perawatan_lab.kd_jenis_prw"
                );
            }

            try {
                if (cara_bayar_lab.equals("Yes") && kelas_lab.equals("No")) {
                    pstindakan.setString(1, Penjab.getText().trim());
                    pstindakan.setString(2, "%" + Pemeriksaan.getText().trim() + "%");
                    pstindakan.setString(3, "%" + Pemeriksaan.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (cara_bayar_lab.equals("No") && kelas_lab.equals("No")) {
                    pstindakan.setString(1, "%" + Pemeriksaan.getText().trim() + "%");
                    pstindakan.setString(2, "%" + Pemeriksaan.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (cara_bayar_lab.equals("Yes") && kelas_lab.equals("Yes")) {
                    pstindakan.setString(1, Penjab.getText().trim());
                    pstindakan.setString(2, kelas.trim());
                    pstindakan.setString(3, "%" + Pemeriksaan.getText().trim() + "%");
                    pstindakan.setString(4, "%" + Pemeriksaan.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (cara_bayar_lab.equals("No") && kelas_lab.equals("Yes")) {
                    pstindakan.setString(1, kelas.trim());
                    pstindakan.setString(2, "%" + Pemeriksaan.getText().trim() + "%");
                    pstindakan.setString(3, "%" + Pemeriksaan.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                }

                while (rstindakan.next()) {
                    String kdJenisPrw = rstindakan.getString("kd_jenis_prw");

                    // Cek apakah kode ini sudah ada di list yang dipilih
                    if (!kodeYangSudahDipilih.contains(kdJenisPrw)) {
                        tabMode2.addRow(new Object[]{false, kdJenisPrw, rstindakan.getString("nm_perawatan")});
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rstindakan != null) {
                    rstindakan.close();
                }
                if (pstindakan != null) {
                    pstindakan.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public void setDokterPerujuk(String kodeperujuk, String namaperujuk) {
        KodePerujuk.setText(kodeperujuk);
        NmPerujuk.setText(namaperujuk);
    }

    private void update() {
        int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum data yang mau diupdate..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            ChkJln.setSelected(false);
            try {
                koneksi.setAutoCommit(false);
                sukses = true;

                // Tentukan status berdasarkan jenisRawat yang dikirim dari form sebelumnya
                String statusRawat = "";
                if (jenisRawat.equals("Ralan")) {
                    statusRawat = "ralan";
                } else if (jenisRawat.equals("Ranap")) {
                    statusRawat = "ranap";
                }

                // UPDATE KATEGORI PK (tanpa update tgl_permintaan dan jam_permintaan)
                if (jml > 0) {
                    // Hapus detail lama
                    Sequel.queryu2("DELETE FROM permintaan_detail_permintaan_lab WHERE noorder=?", 1, new String[]{TNoPermintaanPK.getText()});
                    Sequel.queryu2("DELETE FROM permintaan_pemeriksaan_lab WHERE noorder=?", 1, new String[]{TNoPermintaanPK.getText()});

                    // Update header permintaan_lab (tanpa tgl_permintaan dan jam_permintaan)
                    if (Sequel.mengedittf2("permintaan_lab", "noorder=?", "dokter_perujuk=?, status=?, informasi_tambahan=?, diagnosa_klinis=?", 5, new String[]{
                        KodePerujuk.getText(),
                        statusRawat,
                        InformasiTambahan.getText(),
                        DiagnosisKlinis.getText(),
                        TNoPermintaanPK.getText()
                    }) == true) {

                        // Insert detail baru dari tbTarifPK
                        for (i = 0; i < tbTarifPK.getRowCount(); i++) {
                            if (tbTarifPK.getValueAt(i, 0).toString().equals("true")) {
                                Sequel.menyimpan2("permintaan_pemeriksaan_lab", "?,?,?", "pemeriksaan lab", 3, new String[]{
                                    TNoPermintaanPK.getText(), tbTarifPK.getValueAt(i, 1).toString(), "Belum"
                                });
                            }
                        }

                        // Insert detail baru dari tbDetailPK
                        for (i = 0; i < tbDetailPK.getRowCount(); i++) {
                            if ((!tbDetailPK.getValueAt(i, 4).toString().equals("")) && tbDetailPK.getValueAt(i, 0).toString().equals("true")) {
                                Sequel.menyimpan2("permintaan_detail_permintaan_lab", "?,?,?,?", "detail pemeriksaan lab", 4, new String[]{
                                    TNoPermintaanPK.getText(), tbDetailPK.getValueAt(i, 5).toString(), tbDetailPK.getValueAt(i, 4).toString(), "Belum"
                                });
                            }
                        }
                    } else {
                        sukses = false;
                    }
                }

                // UPDATE KATEGORI PA (tanpa update tgl_permintaan dan jam_permintaan)
                if (jml2 > 0) {
                    // Hapus detail lama
                    Sequel.queryu2("DELETE FROM permintaan_pemeriksaan_labpa WHERE noorder=?", 1, new String[]{TNoPermintaanPA.getText()});

                    // Update header permintaan_labpa dengan nama kolom yang benar
                    if (Sequel.mengedittf2("permintaan_labpa", "noorder=?",
                            "dokter_perujuk=?, status=?, informasi_tambahan=?, diagnosa_klinis=?, pengambilan_bahan=?, diperoleh_dengan=?, lokasi_jaringan=?, diawetkan_dengan=?, pernah_dilakukan_di=?, tanggal_pa_sebelumnya=?, nomor_pa_sebelumnya=?, diagnosa_pa_sebelumnya=?",
                            13, new String[]{
                                KodePerujuk.getText(),
                                statusRawat,
                                InformasiTambahan.getText(),
                                DiagnosisKlinis.getText(),
                                Valid.SetTgl(TanggalBahan.getSelectedItem() + ""),
                                DiperolehDengan.getText(),
                                LokasiPengambilan.getText(),
                                Diawetkan.getText(),
                                DilakukanPA.getText(),
                                (DilakukanPA.getText().equals("") ? "0000-00-00" : Valid.SetTgl(TanggalPA.getSelectedItem() + "")),
                                NomorPA.getText(),
                                DiagnosaPA.getText(),
                                TNoPermintaanPA.getText()
                            }) == true) {

                        // Insert detail baru
                        for (i = 0; i < tbTarifPA.getRowCount(); i++) {
                            if (tbTarifPA.getValueAt(i, 0).toString().equals("true")) {
                                Sequel.menyimpan2("permintaan_pemeriksaan_labpa", "?,?,?", "pemeriksaan lab PA", 3, new String[]{
                                    TNoPermintaanPA.getText(), tbTarifPA.getValueAt(i, 1).toString(), "Belum"
                                });
                            }
                        }
                    } else {
                        sukses = false;
                    }
                }

                // UPDATE KATEGORI MB (tanpa update tgl_permintaan dan jam_permintaan)
                if (jml3 > 0) {
                    // Hapus detail lama
                    Sequel.queryu2("DELETE FROM permintaan_detail_permintaan_labmb WHERE noorder=?", 1, new String[]{TNoPermintaanMB.getText()});
                    Sequel.queryu2("DELETE FROM permintaan_pemeriksaan_labmb WHERE noorder=?", 1, new String[]{TNoPermintaanMB.getText()});

                    // Update header permintaan_labmb (tanpa tgl_permintaan dan jam_permintaan)
                    if (Sequel.mengedittf2("permintaan_labmb", "noorder=?", "dokter_perujuk=?, status=?, informasi_tambahan=?, diagnosa_klinis=?", 5, new String[]{
                        KodePerujuk.getText(),
                        statusRawat,
                        InformasiTambahan.getText(),
                        DiagnosisKlinis.getText(),
                        TNoPermintaanMB.getText()
                    }) == true) {

                        // Insert detail baru dari tbTarifMB
                        for (i = 0; i < tbTarifMB.getRowCount(); i++) {
                            if (tbTarifMB.getValueAt(i, 0).toString().equals("true")) {
                                Sequel.menyimpan2("permintaan_pemeriksaan_labmb", "?,?,?", "pemeriksaan lab", 3, new String[]{
                                    TNoPermintaanMB.getText(), tbTarifMB.getValueAt(i, 1).toString(), "Belum"
                                });
                            }
                        }

                        // Insert detail baru dari tbDetailMB
                        for (i = 0; i < tbDetailMB.getRowCount(); i++) {
                            if ((!tbDetailMB.getValueAt(i, 4).toString().equals("")) && tbDetailMB.getValueAt(i, 0).toString().equals("true")) {
                                Sequel.menyimpan2("permintaan_detail_permintaan_labmb", "?,?,?,?", "detail pemeriksaan lab", 4, new String[]{
                                    TNoPermintaanMB.getText(), tbDetailMB.getValueAt(i, 5).toString(), tbDetailMB.getValueAt(i, 4).toString(), "Belum"
                                });
                            }
                        }
                    } else {
                        sukses = false;
                    }
                }

                if (sukses == true) {
                    koneksi.commit();
                    JOptionPane.showMessageDialog(null, "Proses update selesai...!");
                    emptTeks();
                    dispose(); // Tutup form setelah update berhasil
                } else {
                    koneksi.rollback();
                    JOptionPane.showMessageDialog(null, "Proses update gagal...!");
                }
                koneksi.setAutoCommit(true);

            } catch (Exception e) {
                System.out.println("Notifikasi Update: " + e);
                try {
                    koneksi.rollback();
                    JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
                } catch (Exception ex) {
                    System.out.println("Notifikasi Rollback: " + ex);
                }
            }
            ChkJln.setSelected(true);
        }
    }

    private void tampiltarif2() {
        try {
            jml = 0;
            for (i = 0; i < tbTarifPA.getRowCount(); i++) {
                if (tbTarifPA.getValueAt(i, 0).toString().equals("true")) {
                    jml++;
                }
            }

            pilih = null;
            pilih = new boolean[jml];
            kode = null;
            kode = new String[jml];
            nama = null;
            nama = new String[jml];
            index = 0;
            for (i = 0; i < tbTarifPA.getRowCount(); i++) {
                if (tbTarifPA.getValueAt(i, 0).toString().equals("true")) {
                    pilih[index] = true;
                    kode[index] = tbTarifPA.getValueAt(i, 1).toString();
                    nama[index] = tbTarifPA.getValueAt(i, 2).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabMode3);

            for (i = 0; i < jml; i++) {
                tabMode3.addRow(new Object[]{pilih[i], kode[i], nama[i]});
            }

            if (cara_bayar_lab.equals("Yes") && kelas_lab.equals("No")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,penjab.png_jawab "
                        + "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "
                        + "jns_perawatan_lab.kategori='PA' and jns_perawatan_lab.status='1' and (jns_perawatan_lab.kd_pj=? or jns_perawatan_lab.kd_pj='-') "
                        + "and (jns_perawatan_lab.kd_jenis_prw like ? or jns_perawatan_lab.nm_perawatan like ?) order by jns_perawatan_lab.kd_jenis_prw");
            } else if (cara_bayar_lab.equals("No") && kelas_lab.equals("No")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,penjab.png_jawab "
                        + "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "
                        + "jns_perawatan_lab.kategori='PA' and jns_perawatan_lab.status='1' "
                        + "and (jns_perawatan_lab.kd_jenis_prw like ? or jns_perawatan_lab.nm_perawatan like ?) order by jns_perawatan_lab.kd_jenis_prw");
            } else if (cara_bayar_lab.equals("Yes") && kelas_lab.equals("Yes")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,penjab.png_jawab "
                        + "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "
                        + "jns_perawatan_lab.kategori='PA' and jns_perawatan_lab.status='1' and (jns_perawatan_lab.kd_pj=? or jns_perawatan_lab.kd_pj='-') and (jns_perawatan_lab.kelas=? or jns_perawatan_lab.kelas='-') "
                        + "and (jns_perawatan_lab.kd_jenis_prw like ? or jns_perawatan_lab.nm_perawatan like ?) order by jns_perawatan_lab.kd_jenis_prw");
            } else if (cara_bayar_lab.equals("No") && kelas_lab.equals("Yes")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,penjab.png_jawab "
                        + "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "
                        + "jns_perawatan_lab.kategori='PA' and jns_perawatan_lab.status='1' and (jns_perawatan_lab.kelas=? or jns_perawatan_lab.kelas='-') "
                        + "and (jns_perawatan_lab.kd_jenis_prw like ? or jns_perawatan_lab.nm_perawatan like ?) order by jns_perawatan_lab.kd_jenis_prw");
            }

            try {
                if (cara_bayar_lab.equals("Yes") && kelas_lab.equals("No")) {
                    pstindakan.setString(1, Penjab.getText().trim());
                    pstindakan.setString(2, "%" + PemeriksaanPA.getText().trim() + "%");
                    pstindakan.setString(3, "%" + PemeriksaanPA.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (cara_bayar_lab.equals("No") && kelas_lab.equals("No")) {
                    pstindakan.setString(1, "%" + PemeriksaanPA.getText().trim() + "%");
                    pstindakan.setString(2, "%" + PemeriksaanPA.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (cara_bayar_lab.equals("Yes") && kelas_lab.equals("Yes")) {
                    pstindakan.setString(1, Penjab.getText().trim());
                    pstindakan.setString(2, kelas.trim());
                    pstindakan.setString(3, "%" + PemeriksaanPA.getText().trim() + "%");
                    pstindakan.setString(4, "%" + PemeriksaanPA.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (cara_bayar_lab.equals("No") && kelas_lab.equals("Yes")) {
                    pstindakan.setString(1, kelas.trim());
                    pstindakan.setString(2, "%" + PemeriksaanPA.getText().trim() + "%");
                    pstindakan.setString(3, "%" + PemeriksaanPA.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                }

                while (rstindakan.next()) {
                    tabMode3.addRow(new Object[]{false, rstindakan.getString(1), rstindakan.getString(2)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rstindakan != null) {
                    rstindakan.close();
                }
                if (pstindakan != null) {
                    pstindakan.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void tampiltarifmb() {
        try {
            jml = 0;
            for (i = 0; i < tbTarifMB.getRowCount(); i++) {
                if (tbTarifMB.getValueAt(i, 0).toString().equals("true")) {
                    jml++;
                }
            }

            pilih = null;
            pilih = new boolean[jml];
            kode = null;
            kode = new String[jml];
            nama = null;
            nama = new String[jml];
            index = 0;
            for (i = 0; i < tbTarifMB.getRowCount(); i++) {
                if (tbTarifMB.getValueAt(i, 0).toString().equals("true")) {
                    pilih[index] = true;
                    kode[index] = tbTarifMB.getValueAt(i, 1).toString();
                    nama[index] = tbTarifMB.getValueAt(i, 2).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeMB);

            for (i = 0; i < jml; i++) {
                tabModeMB.addRow(new Object[]{pilih[i], kode[i], nama[i]});
            }

            if (cara_bayar_lab.equals("Yes") && kelas_lab.equals("No")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,penjab.png_jawab "
                        + "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "
                        + "jns_perawatan_lab.kategori='MB' and jns_perawatan_lab.status='1' and (jns_perawatan_lab.kd_pj=? or jns_perawatan_lab.kd_pj='-') "
                        + "and (jns_perawatan_lab.kd_jenis_prw like ? or jns_perawatan_lab.nm_perawatan like ?) order by jns_perawatan_lab.kd_jenis_prw");
            } else if (cara_bayar_lab.equals("No") && kelas_lab.equals("No")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,penjab.png_jawab "
                        + "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "
                        + "jns_perawatan_lab.kategori='MB' and jns_perawatan_lab.status='1' "
                        + "and (jns_perawatan_lab.kd_jenis_prw like ? or jns_perawatan_lab.nm_perawatan like ?) order by jns_perawatan_lab.kd_jenis_prw");
            } else if (cara_bayar_lab.equals("Yes") && kelas_lab.equals("Yes")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,penjab.png_jawab "
                        + "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "
                        + "jns_perawatan_lab.kategori='MB' and jns_perawatan_lab.status='1' and (jns_perawatan_lab.kd_pj=? or jns_perawatan_lab.kd_pj='-') and (jns_perawatan_lab.kelas=? or jns_perawatan_lab.kelas='-') "
                        + "and (jns_perawatan_lab.kd_jenis_prw like ? or jns_perawatan_lab.nm_perawatan like ?) order by jns_perawatan_lab.kd_jenis_prw");
            } else if (cara_bayar_lab.equals("No") && kelas_lab.equals("Yes")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,penjab.png_jawab "
                        + "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "
                        + "jns_perawatan_lab.kategori='MB' and jns_perawatan_lab.status='1' and (jns_perawatan_lab.kelas=? or jns_perawatan_lab.kelas='-') "
                        + "and (jns_perawatan_lab.kd_jenis_prw like ? or jns_perawatan_lab.nm_perawatan like ?) order by jns_perawatan_lab.kd_jenis_prw");
            }

            try {
                if (cara_bayar_lab.equals("Yes") && kelas_lab.equals("No")) {
                    pstindakan.setString(1, Penjab.getText().trim());
                    pstindakan.setString(2, "%" + PemeriksaanMB.getText().trim() + "%");
                    pstindakan.setString(3, "%" + PemeriksaanMB.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (cara_bayar_lab.equals("No") && kelas_lab.equals("No")) {
                    pstindakan.setString(1, "%" + PemeriksaanMB.getText().trim() + "%");
                    pstindakan.setString(2, "%" + PemeriksaanMB.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (cara_bayar_lab.equals("Yes") && kelas_lab.equals("Yes")) {
                    pstindakan.setString(1, Penjab.getText().trim());
                    pstindakan.setString(2, kelas.trim());
                    pstindakan.setString(3, "%" + PemeriksaanMB.getText().trim() + "%");
                    pstindakan.setString(4, "%" + PemeriksaanMB.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (cara_bayar_lab.equals("No") && kelas_lab.equals("Yes")) {
                    pstindakan.setString(1, kelas.trim());
                    pstindakan.setString(2, "%" + PemeriksaanMB.getText().trim() + "%");
                    pstindakan.setString(3, "%" + PemeriksaanMB.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                }

                while (rstindakan.next()) {
                    tabModeMB.addRow(new Object[]{false, rstindakan.getString(1), rstindakan.getString(2)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rstindakan != null) {
                    rstindakan.close();
                }
                if (pstindakan != null) {
                    pstindakan.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public void setDataLab(String noPermintaan, String noRawat, String noRM, String namaPasien,
            String kodePerujuk, String namaPerujuk, String diagnosisKlinis,
            String infoTambahan, String jenisRawat, String kodePermintaan) {
        // Data Permintaan
        TNoRw.setText(noRawat);
        TNoRM.setText(noRM);
        TPasien.setText(namaPasien);

        // Data Dokter Perujuk
        KodePerujuk.setText(kodePerujuk);
        NmPerujuk.setText(namaPerujuk);

        // Data Klinis
        DiagnosisKlinis.setText(diagnosisKlinis);
        InformasiTambahan.setText(infoTambahan);

        // Jenis Rawat
        this.jenisRawat = jenisRawat;

        // Set NoPermintaan berdasarkan 2 karakter awal
        if (kodePermintaan.equals("PK")) {
            TabRawat.setSelectedIndex(0);
            TNoPermintaanPK.setText(noPermintaan);
        } else if (kodePermintaan.equals("PA")) {
            TabRawat.setSelectedIndex(1);
            TNoPermintaanPA.setText(noPermintaan);

            // Jika PA, ambil data PA dari database
            loadDataPA(noPermintaan);
        } else if (kodePermintaan.equals("MB")) {
            TabRawat.setSelectedIndex(2);
            TNoPermintaanMB.setText(noPermintaan);
        }
    }

    private void loadDataPA(String noPermintaan) {
        try {
            String sqlPA = "SELECT pengambilan_bahan, diperoleh_dengan, lokasi_jaringan, diawetkan_dengan, "
                    + "pernah_dilakukan_di, tanggal_pa_sebelumnya, nomor_pa_sebelumnya, diagnosa_pa_sebelumnya "
                    + "FROM permintaan_labpa WHERE noorder = ?";
            PreparedStatement psPA = koneksi.prepareStatement(sqlPA);
            psPA.setString(1, noPermintaan);
            ResultSet rsPA = psPA.executeQuery();

            if (rsPA.next()) {
                // Set tanggal pengambilan bahan
                String pengambilanBahan = rsPA.getString("pengambilan_bahan");
                if (pengambilanBahan != null && !pengambilanBahan.equals("0000-00-00")) {
                    try {
                        Valid.SetTgl(TanggalBahan, pengambilanBahan);
                    } catch (Exception e) {
                        System.out.println("Error set TanggalBahan: " + e);
                    }
                }

                // Set field text
                DiperolehDengan.setText(rsPA.getString("diperoleh_dengan") != null ? rsPA.getString("diperoleh_dengan") : "");
                LokasiPengambilan.setText(rsPA.getString("lokasi_jaringan") != null ? rsPA.getString("lokasi_jaringan") : "");
                Diawetkan.setText(rsPA.getString("diawetkan_dengan") != null ? rsPA.getString("diawetkan_dengan") : "");
                DilakukanPA.setText(rsPA.getString("pernah_dilakukan_di") != null ? rsPA.getString("pernah_dilakukan_di") : "");
                NomorPA.setText(rsPA.getString("nomor_pa_sebelumnya") != null ? rsPA.getString("nomor_pa_sebelumnya") : "");
                DiagnosaPA.setText(rsPA.getString("diagnosa_pa_sebelumnya") != null ? rsPA.getString("diagnosa_pa_sebelumnya") : "");

                // Set tanggal PA sebelumnya
                String tanggalPaSebelumnya = rsPA.getString("tanggal_pa_sebelumnya");
                if (tanggalPaSebelumnya != null && !tanggalPaSebelumnya.equals("0000-00-00")) {
                    try {
                        Valid.SetTgl(TanggalPA, tanggalPaSebelumnya);
                    } catch (Exception e) {
                        System.out.println("Error set TanggalPA: " + e);
                    }
                }
            }
            rsPA.close();
            psPA.close();

        } catch (Exception e) {
            System.out.println("Notifikasi loadDataPA: " + e);
        }
    }

}
