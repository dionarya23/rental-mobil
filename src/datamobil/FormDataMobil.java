/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamobil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CodeLab240619
 */
public class FormDataMobil extends javax.swing.JFrame {
    DefaultTableModel tabModel;
    Connection conn;
    
    /**
     * Creates new form FormDataMobil
     */
    public FormDataMobil() {
        initComponents();
        conn=datamobil.koneksi.getConnection();
        setJTable();
    }
    
    private void setJTable(){
        String [] JudulKolom={"Id","Tipe Mobil", "Merk Mobil", "Harga Mobil", "Ketersedian"};
        tabModel = new DefaultTableModel(null, JudulKolom){
                  boolean[] canEdit = new boolean [] { false, false, false, false};
                  @Override
                  public boolean isCellEditable(int rowIndex, int columnIndex) {
                   return canEdit [columnIndex];
                  }
              };
        TMobil.setModel(tabModel);
        TMobil.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TMobil.getColumnModel().getColumn(0).setPreferredWidth(100);
        TMobil.getColumnModel().getColumn(1).setPreferredWidth(100);
        TMobil.getColumnModel().getColumn(2).setPreferredWidth(100);
        TMobil.getColumnModel().getColumn(3).setPreferredWidth(100);

        getData();
    }
    
    private void getData(){  
        try{   
              // Membuat perintah sql 
            String sql="Select * from data_mobil";
            PreparedStatement st=conn.prepareStatement(sql);  // import java.sql.PreparedStatement
              //Membuat Variabel Bertipe ResulSet
             //Kelas Resultset Berfungsi Menyimpan Dataset(Sekumpulan Data) hasil prepareStatement Query
            ResultSet rs=st.executeQuery();   // import java.sql.ResultSet;
            // Menampilkan ke JTable  melalui tabModel
            String id_mobil, tipe_mobil, merk_mobil, harga_jual, status_sewa, ketersediaan;
           
            while(rs.next()){
                id_mobil=rs.getString("id_mobil");
                tipe_mobil=rs.getString("type_mobil");
                merk_mobil=rs.getString("merk_mobil");
                harga_jual=rs.getString("harga_jual");
                ketersediaan = rs.getString("status_sewa");
                status_sewa = rs.getString("status_sewa");

                Object Data[]={id_mobil,tipe_mobil,merk_mobil,harga_jual, status_sewa};
                tabModel.addRow(Data);
            }
        }
        catch (SQLException sqle) {                  
            System.out.println("Proses Query Gagal = " + sqle);
            System.exit(0);
        }
        catch(Exception e){
            System.out.println("Koneksi Access Gagal " +e.getMessage());
            System.exit(0);
        }
    }
    
    void simpanData(){    
        try{            
            String sql="Insert into data_mobil values(?,?,?,?,?)";
            PreparedStatement st=conn.prepareStatement(sql);
                st.setString(1, txtIdMobil.getText());           
                st.setString(2, txtTipeMobil.getText());
                st.setString(3, txtMerkMobil.getText());
                st.setString(4, txtHargaSewa.getText());
                st.setString(5, "Tersedia");
            int rs=st.executeUpdate();

            if(rs>0){
                JOptionPane.showMessageDialog(this,"Input Berhasil");
                setJTable();
                txtTipeMobil.setText("");
                txtMerkMobil.setText("");
                txtHargaSewa.setText("");
                txtIdMobil.setText("");
            }
        }
        catch (SQLException sqle) {
            JOptionPane.showMessageDialog(this,"Input  Gagal = " + sqle.getMessage());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,"Koneksi Gagal " +e.getMessage());
        }
    }
    
    void ambilData_dari_JTable() {
        int row = TMobil.getSelectedRow();

        // Mengambil data yang dipilih pada JTable
        String IdMobil = tabModel.getValueAt(row, 0).toString();
        String TipeMobil = tabModel.getValueAt(row, 1).toString();
        String MerkMobil = tabModel.getValueAt(row, 2).toString();
        String HargaSewa = tabModel.getValueAt(row, 3).toString();

        txtIdMobil.setText(IdMobil);
        txtTipeMobil.setText(TipeMobil);
        txtMerkMobil.setText(MerkMobil);
        txtHargaSewa.setText(HargaSewa);
      }
    
    // Method Untuk Menghapus Semua Isi JTable
    public void hapusIsiJTable() {
        int row = tabModel.getRowCount();
        for (int i = 0; i < row; i++) {
          tabModel.removeRow(0);
        }
      }
    
    //  Method Untuk Menampilkan Data dari tabel Anggota Ke JTable
    public void tampilDataKeJTable() {
        hapusIsiJTable();
        try {
           String sql="Select * from data_mobil";
            PreparedStatement st=conn.prepareStatement(sql);  // import java.sql.PreparedStatement
              //Membuat Variabel Bertipe ResulSet
             //Kelas Resultset Berfungsi Menyimpan Dataset(Sekumpulan Data) hasil prepareStatement Query
            ResultSet rs=st.executeQuery();   // import java.sql.ResultSet;
            // Menampilkan ke JTable  melalui tabModel
            String id_mobil, tipe_mobil, merk_mobil, harga_jual, status_sewa, ketersediaan;
           
            while(rs.next()){
                id_mobil=rs.getString("id_mobil");
                tipe_mobil=rs.getString("type_mobil");
                merk_mobil=rs.getString("merk_mobil");
                harga_jual=rs.getString("harga_jual");
                ketersediaan = rs.getString("status_sewa");
                status_sewa = "Tersedia";
                if (ketersediaan == "1") {
                   status_sewa = "Tidak Tersedia";
                }

                Object Data[]={id_mobil, tipe_mobil, merk_mobil, harga_jual, status_sewa};
                tabModel.addRow(Data);
            }
      }
        catch (Exception e) { }  // Isi informasi eksepsi
    }
    
    public void updateData() {
        // Konfirmasi sebelum melakukan perubahan data
        int ok = JOptionPane.showConfirmDialog(this,
            "Anda Yakin Ingin Mengubah Data\n Ini?", "Konfirmasi ",JOptionPane.YES_NO_OPTION);
        // Apabila tombol Yes ditekan
        if (ok == 0) {
          try {
            String sql ="UPDATE data_mobil SET type_mobil = ?, merk_mobil= ?, harga_jual = ? WHERE id_mobil = ?";
            PreparedStatement st = conn.prepareStatement(sql);

              st.setString(1, txtTipeMobil.getText());
              st.setString(2, txtMerkMobil.getText());
              st.setString(3, txtHargaSewa.getText());
              st.setString(4, txtIdMobil.getText());
              int rs=st.executeUpdate();

              if(rs>0){
                JOptionPane.showMessageDialog(this,"Edit Data Berhasil");
                tampilDataKeJTable();
              }         

              txtTipeMobil.setText("");
              txtMerkMobil.setText("");
              txtHargaSewa.setText("");
              txtIdMobil.setText("");
          }catch (SQLException se) {}  // Silahkan tambahkan Sendiri informasi Eksepsi
        }

      }
    
    public void hapusData() {
        // Konfirmasi sebelum melakukan penghapusan data
        ambilData_dari_JTable();
        int ok = JOptionPane.showConfirmDialog(this,
            "Anda Yakin Ingin Menghapus Data\nDengan Id Mobil = '" + txtIdMobil.getText() +
            "'", "Konfirmasi Menghapus Data",JOptionPane.YES_NO_OPTION);
        if (ok == 0) {     // Apabila tombol OK ditekan
          try {       
            String sql = "DELETE FROM data_mobil WHERE id_mobil = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, txtIdMobil.getText());
            int rs=st.executeUpdate();
            if(rs>0){
            tampilDataKeJTable();
            JOptionPane.showMessageDialog(this,"Data Berhasil dihapus");
            }
            txtTipeMobil.setText("");
            txtMerkMobil.setText("");
            txtHargaSewa.setText("");
            txtIdMobil.setText("");
          } catch (Exception se) {  // Silahkan tambahkan catch Exception yang lain
             JOptionPane.showMessageDialog(this,"Gagal Hapus Data.. ");
           }
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTipeMobil = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtMerkMobil = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtHargaSewa = new javax.swing.JTextField();
        BSimpan = new javax.swing.JButton();
        BCloseMobil = new javax.swing.JButton();
        BEdit = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtIdMobil = new javax.swing.JTextField();
        BHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TMobil = new javax.swing.JTable();
        CBCari = new javax.swing.JComboBox();
        txtCari = new javax.swing.JTextField();
        BCari = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        BTambahMobil = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        MenuHome = new javax.swing.JLabel();
        MenuDataMobil = new javax.swing.JLabel();
        MenuTransaksi = new javax.swing.JLabel();
        MenuUser = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        MenuLogout = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Tipe Mobil");

        txtTipeMobil.setEnabled(false);
        txtTipeMobil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipeMobilActionPerformed(evt);
            }
        });

        jLabel2.setText("Merk Mobil");

        txtMerkMobil.setEnabled(false);

        jLabel3.setText("Harga Mobil");

        txtHargaSewa.setEnabled(false);

        BSimpan.setText("Simpan");
        BSimpan.setEnabled(false);
        BSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BSimpanActionPerformed(evt);
            }
        });

        BCloseMobil.setBackground(new java.awt.Color(204, 204, 204));
        BCloseMobil.setText("Close");
        BCloseMobil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCloseMobilActionPerformed(evt);
            }
        });

        BEdit.setBackground(new java.awt.Color(255, 153, 51));
        BEdit.setForeground(new java.awt.Color(255, 255, 255));
        BEdit.setText("Edit");
        BEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditActionPerformed(evt);
            }
        });

        jLabel4.setText("Id Mobil");

        txtIdMobil.setEnabled(false);

        BHapus.setBackground(new java.awt.Color(204, 0, 51));
        BHapus.setForeground(new java.awt.Color(255, 255, 255));
        BHapus.setText("Hapus");
        BHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BHapusActionPerformed(evt);
            }
        });

        TMobil.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TMobil);

        CBCari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Id Mobil", "Tipe Mobil", "Merk Mobil" }));

        BCari.setText("Cari");
        BCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCariActionPerformed(evt);
            }
        });

        jLabel5.setText("Cari Berdasarkan");

        BTambahMobil.setBackground(new java.awt.Color(51, 153, 255));
        BTambahMobil.setForeground(new java.awt.Color(255, 255, 255));
        BTambahMobil.setText("Tambah");
        BTambahMobil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTambahMobilActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(BTambahMobil)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(BSimpan)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(BEdit)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(BHapus)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(BCloseMobil))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(28, 28, 28)
                            .addComponent(CBCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(BCari))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4))
                            .addGap(21, 21, 21)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtTipeMobil)
                                .addComponent(txtMerkMobil)
                                .addComponent(txtHargaSewa)
                                .addComponent(txtIdMobil, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(230, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtIdMobil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTipeMobil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMerkMobil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtHargaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BSimpan)
                    .addComponent(BCloseMobil)
                    .addComponent(BEdit)
                    .addComponent(BHapus)
                    .addComponent(BTambahMobil))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CBCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BCari)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(185, 185, 185))
        );

        jPanel2.setBackground(new java.awt.Color(0, 102, 153));
        jPanel2.setToolTipText("");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Dashboard");

        jSeparator1.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));

        MenuHome.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        MenuHome.setForeground(new java.awt.Color(204, 204, 204));
        MenuHome.setText("Home");
        MenuHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuHomeMouseClicked(evt);
            }
        });

        MenuDataMobil.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        MenuDataMobil.setForeground(new java.awt.Color(255, 255, 255));
        MenuDataMobil.setText("Data Mobil");
        MenuDataMobil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuDataMobilMouseClicked(evt);
            }
        });

        MenuTransaksi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        MenuTransaksi.setForeground(new java.awt.Color(204, 204, 204));
        MenuTransaksi.setText("Transaksi");
        MenuTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuTransaksiMouseClicked(evt);
            }
        });

        MenuUser.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        MenuUser.setForeground(new java.awt.Color(204, 204, 204));
        MenuUser.setText("User");
        MenuUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuUserMouseClicked(evt);
            }
        });

        jSeparator2.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator2.setForeground(new java.awt.Color(204, 204, 204));

        MenuLogout.setBackground(new java.awt.Color(204, 204, 204));
        MenuLogout.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        MenuLogout.setForeground(new java.awt.Color(204, 204, 204));
        MenuLogout.setText("Logout");
        MenuLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuLogoutMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MenuHome)
                            .addComponent(MenuDataMobil)
                            .addComponent(MenuTransaksi)
                            .addComponent(MenuUser)
                            .addComponent(MenuLogout))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(MenuHome)
                .addGap(18, 18, 18)
                .addComponent(MenuDataMobil)
                .addGap(18, 18, 18)
                .addComponent(MenuTransaksi)
                .addGap(18, 18, 18)
                .addComponent(MenuUser)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(MenuLogout)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Sitem Informasi Penjualan Mobil");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Data Mobil");

        jSeparator3.setBackground(new java.awt.Color(0, 102, 153));
        jSeparator3.setForeground(new java.awt.Color(0, 102, 153));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditActionPerformed
        // TODO add your handling code here:
        txtIdMobil.setEnabled(false);
        txtTipeMobil.setEnabled(true);
        txtMerkMobil.setEnabled(true);
        txtHargaSewa.setEnabled(true);

        BSimpan.setText("Update"); // Merubah Teks Tombol Simpan
        BSimpan.setEnabled(true);

        // Memanggil Method  ambilData_dari_JTable()
        ambilData_dari_JTable();
    }//GEN-LAST:event_BEditActionPerformed

    private void BTambahMobilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTambahMobilActionPerformed
        // TODO add your handling code here:
        txtIdMobil.setEnabled(true);
        txtTipeMobil.setEnabled(true);
        txtMerkMobil.setEnabled(true);
        txtHargaSewa.setEnabled(true);
        BSimpan.setEnabled(true);
        BSimpan.setText("Simpan");
    }//GEN-LAST:event_BTambahMobilActionPerformed

    private void BCloseMobilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCloseMobilActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_BCloseMobilActionPerformed

    private void BSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BSimpanActionPerformed
        // TODO add your handling code here:
        if(BSimpan.getText().equalsIgnoreCase("Simpan"))
            simpanData();
        else
            updateData();

    }//GEN-LAST:event_BSimpanActionPerformed

    private void txtTipeMobilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipeMobilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipeMobilActionPerformed

    private void BHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BHapusActionPerformed
        // TODO add your handling code here:
        hapusData();
    }//GEN-LAST:event_BHapusActionPerformed

    private void BCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCariActionPerformed
        // TODO add your handling code here:
        String sql;
        int pilih = CBCari.getSelectedIndex();
        String cari = txtCari.getText();
        try {
            if(pilih==0)
                sql ="Select * from data_mobil WHERE id_mobil ='" +txtCari.getText() + "'";
            else if (pilih==1)
                sql ="Select * from data_mobil WHERE tipe_mobil Like '%" +txtCari.getText() + "%'";
            else
                sql ="Select * from data_mobil WHERE merk_mobil Like '%" +txtCari.getText() + "%'";

            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs =st.executeQuery();

            hapusIsiJTable();
            String id_mobil, tipe_mobil, merk_mobil, harga_jual, status_sewa, ketersediaan;
           
            while(rs.next()){
                id_mobil=rs.getString("id_mobil");
                tipe_mobil=rs.getString("type_mobil");
                merk_mobil=rs.getString("merk_mobil");
                harga_jual=rs.getString("harga_jual");
                ketersediaan = rs.getString("status_sewa");
                status_sewa = "Tersedia";
                if (ketersediaan == "1") {
                   status_sewa = "Tidak Tersedia";
                }

                Object Data[]={id_mobil,tipe_mobil,merk_mobil,harga_jual, status_sewa};
                tabModel.addRow(Data);
            }

             if(tabModel.getRowCount()>0)         
               JOptionPane.showMessageDialog(this,"Data Ditemukan ");        
            else
                JOptionPane.showMessageDialog(this,"Data Tidak Ditemukan.. ");

          }  // Silahkan tambahkan sendiri informasi eksepsi
        catch (SQLException se) {} 
    }//GEN-LAST:event_BCariActionPerformed

    private void MenuHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuHomeMouseClicked
        // TODO add your handling code here:
        WelcomePage welcomePage = new WelcomePage();
        FormDataMobil formDataMobil = new FormDataMobil();
        FAnggota formAnggota = new FAnggota();
        FSewa formSewa = new FSewa();
        Login login = new Login();

        this.setVisible(false);
        welcomePage.setVisible(true);
        formAnggota.setVisible(false);
        formSewa.setVisible(false);
        login.setVisible(false);
    }//GEN-LAST:event_MenuHomeMouseClicked

    private void MenuDataMobilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuDataMobilMouseClicked
        // TODO add your handling code here:
        WelcomePage welcomePage = new WelcomePage();
        FormDataMobil formDataMobil = new FormDataMobil();
        FAnggota formAnggota = new FAnggota();
        FSewa formSewa = new FSewa();
        Login login = new Login();

        this.setVisible(true);
        welcomePage.setVisible(false);
        formAnggota.setVisible(false);
        formSewa.setVisible(false);
        login.setVisible(false);
    }//GEN-LAST:event_MenuDataMobilMouseClicked

    private void MenuTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuTransaksiMouseClicked
        // TODO add your handling code here:
        WelcomePage welcomePage = new WelcomePage();
        FormDataMobil formDataMobil = new FormDataMobil();
        FAnggota formAnggota = new FAnggota();
        FSewa formSewa = new FSewa();
        Login login = new Login();

        this.setVisible(false);
        welcomePage.setVisible(false);
        formAnggota.setVisible(false);
        formSewa.setVisible(true);
        login.setVisible(false);
    }//GEN-LAST:event_MenuTransaksiMouseClicked

    private void MenuUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuUserMouseClicked
        // TODO add your handling code here:
        WelcomePage welcomePage = new WelcomePage();
        FormDataMobil formDataMobil = new FormDataMobil();
        FAnggota formAnggota = new FAnggota();
        FSewa formSewa = new FSewa();
        Login login = new Login();

        this.setVisible(false);
        welcomePage.setVisible(false);
        formAnggota.setVisible(true);
        formSewa.setVisible(false);
        login.setVisible(false);
    }//GEN-LAST:event_MenuUserMouseClicked

    private void MenuLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuLogoutMouseClicked
        // TODO add your handling code here:
        WelcomePage welcomePage = new WelcomePage();
        FormDataMobil formDataMobil = new FormDataMobil();
        FAnggota formAnggota = new FAnggota();
        FSewa formSewa = new FSewa();
        Login login = new Login();

        this.setVisible(false);
        welcomePage.setVisible(false);
        formAnggota.setVisible(false);
        formSewa.setVisible(false);
        login.setVisible(true);
    }//GEN-LAST:event_MenuLogoutMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormDataMobil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormDataMobil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormDataMobil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormDataMobil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormDataMobil().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BCari;
    private javax.swing.JButton BCloseMobil;
    private javax.swing.JButton BEdit;
    private javax.swing.JButton BHapus;
    private javax.swing.JButton BSimpan;
    private javax.swing.JButton BTambahMobil;
    private javax.swing.JComboBox CBCari;
    private javax.swing.JLabel MenuDataMobil;
    private javax.swing.JLabel MenuHome;
    private javax.swing.JLabel MenuLogout;
    private javax.swing.JLabel MenuTransaksi;
    private javax.swing.JLabel MenuUser;
    private javax.swing.JTable TMobil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHargaSewa;
    private javax.swing.JTextField txtIdMobil;
    private javax.swing.JTextField txtMerkMobil;
    private javax.swing.JTextField txtTipeMobil;
    // End of variables declaration//GEN-END:variables
}
