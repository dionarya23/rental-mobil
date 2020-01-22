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
 * @author Kiki
 */
public class FAnggota extends javax.swing.JFrame {
    DefaultTableModel tabModel;
    Connection conn;
    /**
     * Creates new form FAnggota
     */
    public FAnggota() {
        initComponents();
        conn=koneksi.getConnection();
        setJTable();
    }
    
    private void setJTable(){
        String [] JudulKolom={"No","ID Penyedia", "Username", "Nama Lengkap"};
        tabModel = new DefaultTableModel(null, JudulKolom){
                  boolean[] canEdit = new boolean [] { false, false, false, false};
                  @Override
                  public boolean isCellEditable(int rowIndex, int columnIndex) {
                   return canEdit [columnIndex];
                  }
              };
        TAnggota.setModel(tabModel);
        TAnggota.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TAnggota.getColumnModel().getColumn(0).setPreferredWidth(30);
        TAnggota.getColumnModel().getColumn(1).setPreferredWidth(100);
        TAnggota.getColumnModel().getColumn(2).setPreferredWidth(100);
        TAnggota.getColumnModel().getColumn(3).setPreferredWidth(150);

        getData();
    }
    
    private void getData(){  
        try{   
              // Membuat perintah sql 
            String sql="Select * from Penyedia";
            PreparedStatement st=conn.prepareStatement(sql);  // import java.sql.PreparedStatement
              //Membuat Variabel Bertipe ResulSet
             //Kelas Resultset Berfungsi Menyimpan Dataset(Sekumpulan Data) hasil prepareStatement Query
            ResultSet rs=st.executeQuery();   // import java.sql.ResultSet;
            // Menampilkan ke JTable  melalui tabModel
            String IDAnggota, Username, Nama;
            int no=0;
            while(rs.next()){
                no=no+1;
                IDAnggota=rs.getString("id_penyedia");
                Username=rs.getString("username");
                Nama=rs.getString("nama_lengkap");

                Object Data[]={no,IDAnggota,Username,Nama};
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
            String sql="Insert into Penyedia values(?,?,?,?)";
            PreparedStatement st=conn.prepareStatement(sql);
                st.setString(1, txtIdPenyedia.getText());
                st.setString(2, txtUsername.getText());
                st.setString(3, txtPassword.getText());
                st.setString(4, txtNamaLengkap.getText());
            int rs=st.executeUpdate();

            if(rs>0){
                JOptionPane.showMessageDialog(this,"Input Berhasil");
                setJTable();
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
        int row = TAnggota.getSelectedRow();

        // Mengambil data yang dipilih pada JTable
        String IdPenyedia = tabModel.getValueAt(row, 1).toString();
        String Username = tabModel.getValueAt(row, 2).toString();
        String NamaLengkap = tabModel.getValueAt(row, 3).toString();

        txtIdPenyedia.setText(IdPenyedia);
        txtUsername.setText(Username);
        txtNamaLengkap.setText(NamaLengkap);
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
            String sql="Select * from Penyedia";
            PreparedStatement st=conn.prepareStatement(sql);
            ResultSet rs=st.executeQuery();
            String IdPenyedia,Username,NamaLengkap;
            int no=0;
            while(rs.next()){
             no=no+1;
             IdPenyedia=rs.getString("id_penyedia");
             Username=rs.getString("username");
             NamaLengkap=rs.getString("nama_lengkap");
             Object Data[]={no,IdPenyedia,Username,NamaLengkap};
             tabModel.addRow(Data);
          }
      }
        catch (Exception e) { }  // Isi informasi eksepsi
    }
    
    public void updateData() {
        // Konfirmasi sebelum melakukan perubahan data
        int ok = JOptionPane.showConfirmDialog(this,
            "Anda Yakin Ingin Mengubah Data\n Dengan Id = '" + txtIdPenyedia.getText() +
            "'", "Konfirmasi ",JOptionPane.YES_NO_OPTION);
        // Apabila tombol Yes ditekan
        if (ok == 0) {
          try {
            String Password = txtPassword.getText();
            
            if (Password.length() < 1) {
                String sql ="UPDATE Penyedia SET username = ?, nama_lengkap= ? WHERE id_penyedia = ?";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, txtUsername.getText());
                st.setString(2, txtNamaLengkap.getText());
                st.setString(3, txtIdPenyedia.getText());
                int rs=st.executeUpdate();
                
                if(rs>0){
                    JOptionPane.showMessageDialog(this,"Edit Data Berhasil");
                    tampilDataKeJTable();
                  }  
            } else {
                String sql ="UPDATE Penyedia SET username = ?, password= ? , nama_lengkap= ? WHERE id_penyedia = ?";
                PreparedStatement st = conn.prepareStatement(sql);
                System.out.println("yessss");
                st.setString(1, txtUsername.getText());
                st.setString(2, txtPassword.getText());
                st.setString(3, txtNamaLengkap.getText());
                st.setString(4, txtIdPenyedia.getText());
                int rs=st.executeUpdate();
                
                if(rs>0){
                    JOptionPane.showMessageDialog(this,"Edit Data Berhasil");
                    tampilDataKeJTable();
                  }  
            }

                     
              
              txtIdPenyedia.setText("");
              txtUsername.setText("");
              txtPassword.setText("");
              txtNamaLengkap.setText("");             
          }catch (SQLException se) {}  // Silahkan tambahkan Sendiri informasi Eksepsi
        }

      }
    
    public void hapusData() {
        // Konfirmasi sebelum melakukan penghapusan data
        ambilData_dari_JTable();
        int ok = JOptionPane.showConfirmDialog(this,
            "Anda Yakin Ingin Menghapus Data\nDengan Id = '" + txtIdPenyedia.getText() +
            "'", "Konfirmasi Menghapus Data",JOptionPane.YES_NO_OPTION);
        if (ok == 0) {     // Apabila tombol OK ditekan
          try {       
            String sql = "DELETE FROM penyedia WHERE id_penyedia = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, txtIdPenyedia.getText());
            int rs=st.executeUpdate();
            if(rs>0){
            tampilDataKeJTable();
            JOptionPane.showMessageDialog(this,"Data Berhasil dihapus");
            }
            txtIdPenyedia.setText("");
            txtUsername.setText("");
            txtPassword.setText("");
            txtNamaLengkap.setText(""); 
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

<<<<<<< HEAD
=======
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        MenuHome = new javax.swing.JLabel();
        MenuDataMobil = new javax.swing.JLabel();
        MenuTransaksi = new javax.swing.JLabel();
        MenuUser = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        MenuLogout = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
>>>>>>> c7bbe8eaaa239df640a2bb3543c52fd88c466097
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        txtNamaLengkap = new javax.swing.JTextField();
        BSimpan = new javax.swing.JButton();
        bClose = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TAnggota = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtIdPenyedia = new javax.swing.JTextField();
        BTambah = new javax.swing.JButton();
        BEdit = new javax.swing.JButton();
        BHapus = new javax.swing.JButton();
<<<<<<< HEAD

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

=======
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
        MenuDataMobil.setForeground(new java.awt.Color(204, 204, 204));
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
        MenuUser.setForeground(new java.awt.Color(255, 255, 255));
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
                        .addGap(0, 10, Short.MAX_VALUE)))
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
                .addContainerGap(539, Short.MAX_VALUE))
        );

>>>>>>> c7bbe8eaaa239df640a2bb3543c52fd88c466097
        jLabel2.setText("Username");

        jLabel3.setText("Password");

        jLabel4.setText("Nama Lengkap");

        txtUsername.setEnabled(false);

        txtPassword.setEnabled(false);

        txtNamaLengkap.setEnabled(false);

        BSimpan.setText("Simpan");
        BSimpan.setEnabled(false);
        BSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BSimpanActionPerformed(evt);
            }
        });

<<<<<<< HEAD
=======
        bClose.setBackground(new java.awt.Color(204, 204, 204));
>>>>>>> c7bbe8eaaa239df640a2bb3543c52fd88c466097
        bClose.setText("Close");
        bClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCloseActionPerformed(evt);
            }
        });

        TAnggota.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(TAnggota);

<<<<<<< HEAD
        jLabel1.setText("Id Penyedia");

        txtIdPenyedia.setEnabled(false);

=======
        jLabel1.setText("Id User");

        txtIdPenyedia.setEnabled(false);

        BTambah.setBackground(new java.awt.Color(51, 153, 255));
        BTambah.setForeground(new java.awt.Color(255, 255, 255));
>>>>>>> c7bbe8eaaa239df640a2bb3543c52fd88c466097
        BTambah.setText("Tambah");
        BTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTambahActionPerformed(evt);
            }
        });

<<<<<<< HEAD
=======
        BEdit.setBackground(new java.awt.Color(255, 153, 51));
        BEdit.setForeground(new java.awt.Color(255, 255, 255));
>>>>>>> c7bbe8eaaa239df640a2bb3543c52fd88c466097
        BEdit.setText("Edit");
        BEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditActionPerformed(evt);
            }
        });

<<<<<<< HEAD
=======
        BHapus.setBackground(new java.awt.Color(204, 0, 51));
        BHapus.setForeground(new java.awt.Color(255, 255, 255));
>>>>>>> c7bbe8eaaa239df640a2bb3543c52fd88c466097
        BHapus.setText("Hapus");
        BHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BHapusActionPerformed(evt);
            }
        });

<<<<<<< HEAD
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
=======
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
>>>>>>> c7bbe8eaaa239df640a2bb3543c52fd88c466097
                        .addComponent(BTambah)
                        .addGap(14, 14, 14)
                        .addComponent(BSimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BEdit)
                        .addGap(18, 18, 18)
                        .addComponent(BHapus)
                        .addGap(18, 18, 18)
                        .addComponent(bClose))
<<<<<<< HEAD
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUsername)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(txtIdPenyedia)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNamaLengkap, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(88, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdPenyedia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNamaLengkap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
=======
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNamaLengkap, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                            .addComponent(txtPassword)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdPenyedia, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdPenyedia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNamaLengkap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
>>>>>>> c7bbe8eaaa239df640a2bb3543c52fd88c466097
                    .addComponent(BSimpan)
                    .addComponent(bClose)
                    .addComponent(BTambah)
                    .addComponent(BEdit)
                    .addComponent(BHapus))
                .addGap(18, 18, 18)
<<<<<<< HEAD
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                .addContainerGap())
=======
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addGap(95, 95, 95))
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Sitem Informasi Penjualan Mobil");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("User");

        jSeparator3.setBackground(new java.awt.Color(0, 102, 153));
        jSeparator3.setForeground(new java.awt.Color(0, 102, 153));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 233, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel7)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
>>>>>>> c7bbe8eaaa239df640a2bb3543c52fd88c466097
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BSimpanActionPerformed
        // TODO add your handling code here:
        if(BSimpan.getText().equalsIgnoreCase("Simpan"))
            simpanData();
        else
            updateData();
    }//GEN-LAST:event_BSimpanActionPerformed

    private void bCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_bCloseActionPerformed

    private void BTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTambahActionPerformed
        // TODO add your handling code here:
        txtIdPenyedia.setEnabled(true);
        txtNamaLengkap.setEnabled(true);
        txtUsername.setEnabled(true);
        txtPassword.setEnabled(true);
        BSimpan.setEnabled(true);
        BSimpan.setText("Simpan");
    }//GEN-LAST:event_BTambahActionPerformed

    private void BEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditActionPerformed
        // TODO add your handling code here:
        txtIdPenyedia.setEnabled(false); // txtNoAnggota Tidak Aktif
        txtUsername.setEnabled(true);       // txtNama          Aktif
        txtPassword.setEnabled(true);   
        txtNamaLengkap.setEnabled(true);// txtAlamat Aktif

        BSimpan.setText("Update"); // Merubah Teks Tombol Simpan
        BSimpan.setEnabled(true);

        // Memanggil Method  ambilData_dari_JTable()
        ambilData_dari_JTable();
    }//GEN-LAST:event_BEditActionPerformed

    private void BHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BHapusActionPerformed
        // TODO add your handling code here:
        hapusData();
    }//GEN-LAST:event_BHapusActionPerformed

<<<<<<< HEAD
=======
    private void MenuHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuHomeMouseClicked
        // TODO add your handling code here:
        WelcomePage welcomePage = new WelcomePage();
        FormDataMobil formDataMobil = new FormDataMobil();
        FAnggota formAnggota = new FAnggota();
        FSewa formSewa = new FSewa();
        Login login = new Login();

        this.setVisible(false);
        welcomePage.setVisible(true);
        formDataMobil.setVisible(false);
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

        this.setVisible(false);
        welcomePage.setVisible(false);
        formDataMobil.setVisible(true);
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
        formDataMobil.setVisible(false);
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

        this.setVisible(true);
        welcomePage.setVisible(false);
        formDataMobil.setVisible(false);
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
        formDataMobil.setVisible(false);
        formSewa.setVisible(false);
        login.setVisible(true);
    }//GEN-LAST:event_MenuLogoutMouseClicked

>>>>>>> c7bbe8eaaa239df640a2bb3543c52fd88c466097
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
            java.util.logging.Logger.getLogger(FAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FAnggota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BEdit;
    private javax.swing.JButton BHapus;
    private javax.swing.JButton BSimpan;
    private javax.swing.JButton BTambah;
<<<<<<< HEAD
=======
    private javax.swing.JLabel MenuDataMobil;
    private javax.swing.JLabel MenuHome;
    private javax.swing.JLabel MenuLogout;
    private javax.swing.JLabel MenuTransaksi;
    private javax.swing.JLabel MenuUser;
>>>>>>> c7bbe8eaaa239df640a2bb3543c52fd88c466097
    private javax.swing.JTable TAnggota;
    private javax.swing.JButton bClose;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
<<<<<<< HEAD
    private javax.swing.JScrollPane jScrollPane1;
=======
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
>>>>>>> c7bbe8eaaa239df640a2bb3543c52fd88c466097
    private javax.swing.JTextField txtIdPenyedia;
    private javax.swing.JTextField txtNamaLengkap;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
