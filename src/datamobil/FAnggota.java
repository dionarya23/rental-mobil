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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jLabel1.setText("Id Penyedia");

        txtIdPenyedia.setEnabled(false);

        BTambah.setText("Tambah");
        BTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTambahActionPerformed(evt);
            }
        });

        BEdit.setText("Edit");
        BEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditActionPerformed(evt);
            }
        });

        BHapus.setText("Hapus");
        BHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BTambah)
                        .addGap(14, 14, 14)
                        .addComponent(BSimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BEdit)
                        .addGap(18, 18, 18)
                        .addComponent(BHapus)
                        .addGap(18, 18, 18)
                        .addComponent(bClose))
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
                    .addComponent(BSimpan)
                    .addComponent(bClose)
                    .addComponent(BTambah)
                    .addComponent(BEdit)
                    .addComponent(BHapus))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                .addContainerGap())
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
    private javax.swing.JTable TAnggota;
    private javax.swing.JButton bClose;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtIdPenyedia;
    private javax.swing.JTextField txtNamaLengkap;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
