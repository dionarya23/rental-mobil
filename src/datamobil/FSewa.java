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
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author dionarya
 */
public class FSewa extends javax.swing.JFrame {
    Connection conn;
    DefaultTableModel tabModel;

    
    /**
     * Creates new form FSewa
     */
    public FSewa() {
        initComponents();
        conn = koneksi.getConnection(); 
        tampilComboMobil();
        setJTable();
    }
    
     private void setJTable(){
        String [] JudulKolom={"No","Nama Penyewa", "No KTP", "Tanggal Pembelian", "Nama Mobil", "Harga Total"};
         tabModel = new DefaultTableModel(null, JudulKolom){
                  boolean[] canEdit = new boolean [] { false, false, false, false};
                  @Override
                  public boolean isCellEditable(int rowIndex, int columnIndex) {
                   return canEdit [columnIndex];
                  }
              };
        TSewa.setModel(tabModel);
        TSewa.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TSewa.getColumnModel().getColumn(0).setPreferredWidth(30);
        TSewa.getColumnModel().getColumn(1).setPreferredWidth(100);
        TSewa.getColumnModel().getColumn(2).setPreferredWidth(100);
        TSewa.getColumnModel().getColumn(3).setPreferredWidth(100);
        TSewa.getColumnModel().getColumn(4).setPreferredWidth(100);
        TSewa.getColumnModel().getColumn(5).setPreferredWidth(100);



        getData();
    }
     
      private void getData(){  
        try{   
              // Membuat perintah sql 
            String sql="select transaksi.nama_pembeli, "
                    + "transaksi.no_ktp, "
                    + "transaksi.tgl_pembelian, "
                    + "data_mobil.type_mobil, "
                    + "data_mobil.merk_mobil, "
                    + "data_mobil.harga_jual from transaksi inner join data_mobil on "
                    + "transaksi.id_mobil=data_mobil.id_mobil";
            PreparedStatement st=conn.prepareStatement(sql);  // import java.sql.PreparedStatement
              //Membuat Variabel Bertipe ResulSet
             //Kelas Resultset Berfungsi Menyimpan Dataset(Sekumpulan Data) hasil prepareStatement Query
            ResultSet rs=st.executeQuery();   // import java.sql.ResultSet;
            // Menampilkan ke JTable  melalui tabModel
            String Nama_penyewa, No_Ktp, tgl_pembelian, mobil, harga_jual ;
            int no=0;
            while(rs.next()){
                no=no+1;
                Nama_penyewa=rs.getString("nama_pembeli");
                No_Ktp=rs.getString("no_ktp");
                tgl_pembelian= rs.getString("tgl_pembelian");
                mobil= rs.getString("type_mobil") + " " + rs.getString("merk_mobil");
                harga_jual = rs.getString("harga_jual");
                Object Data[]={no, Nama_penyewa, No_Ktp, tgl_pembelian, mobil, harga_jual};
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
    
    
     public void tampilComboMobil()
    {
       try{   
              // Membuat perintah sql 
            String sql="Select * from data_mobil where status_sewa='Tersedia'";
            PreparedStatement st=conn.prepareStatement(sql);  // import java.sql.PreparedStatement
              //Membuat Variabel Bertipe ResulSet
             //Kelas Resultset Berfungsi Menyimpan Dataset(Sekumpulan Data) hasil prepareStatement Query
            ResultSet rs=st.executeQuery();   // import java.sql.ResultSet;
            // Menampilkan ke JTable  melalui tabModel
            while(rs.next()){
                comboMobil.addItem(rs.getString("id_mobil") + "-" +rs.getString("type_mobil")+ "-" + rs.getString("merk_mobil"));
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtPenyewa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtKtp = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_tanggalMulai = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        comboMobil = new javax.swing.JComboBox<>();
        BTambah = new javax.swing.JButton();
        BClose = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TSewa = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        BtnLaporan = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nama Pembeli : ");

        jLabel2.setText("No Ktp : ");

        jLabel3.setText("Tanggal Pembeliani :");

        jLabel4.setText("Mobil Yang Disewa :");

        BTambah.setText("Tambah");
        BTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTambahActionPerformed(evt);
            }
        });

        BClose.setText("Close");
        BClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCloseActionPerformed(evt);
            }
        });

        TSewa.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(TSewa);

        jLabel6.setText("tahun-bulan-tanggal");

        BtnLaporan.setText("Cetak Laporan");
        BtnLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLaporanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(BTambah)
                        .addGap(26, 26, 26)
                        .addComponent(BClose, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(BtnLaporan)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPenyewa)
                                    .addComponent(txtKtp)
                                    .addComponent(txt_tanggalMulai)
                                    .addComponent(comboMobil, 0, 179, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)))))
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKtp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tanggalMulai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboMobil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BClose)
                    .addComponent(BTambah)
                    .addComponent(BtnLaporan))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_BCloseActionPerformed

    private void BTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTambahActionPerformed
        // TODO add your handling code here:
        //tambah data
        tambah();
    }//GEN-LAST:event_BTambahActionPerformed

    private void BtnLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLaporanActionPerformed
        // TODO add your handling code here:
        buat_laporan();
    }//GEN-LAST:event_BtnLaporanActionPerformed
    
    private void buat_laporan() {
        String reportSource;
        String reportDest;
            try{
                       reportSource=System.getProperty("user.dir")+"/report1.jrxml";
                       reportDest=System.getProperty("user.dir")+"/report1.html";

                       JasperReport jasperReport=JasperCompileManager.compileReport(reportSource);
                       JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,null,conn);
                       JasperExportManager.exportReportToHtmlFile(jasperPrint,reportDest);
                       JasperViewer.viewReport(jasperPrint,false);

            }catch(Exception e){
                   System.out.println(e);
            }
    }                           
    
    private void tambah() {
         try{            
        String mobilDipilih = comboMobil.getItemAt(comboMobil.getSelectedIndex());
        String[] arrOfStr = mobilDipilih.split("-", 5);
        String querySql = "Select * from data_mobil where id_mobil="+arrOfStr[0];
        PreparedStatement skuy = conn.prepareStatement(querySql);
        ResultSet result=skuy.executeQuery();
        int id = 0;
             while(result.next()) {
                id = result.getInt("id_mobil");
             }             
         String sql ="UPDATE data_mobil SET status_sewa=? where id_mobil=?";
         PreparedStatement santuy = conn.prepareStatement(sql);
        
          santuy.setString(1, "Tidak Tersedia");
          santuy.setInt(2, Integer.parseInt(arrOfStr[0]));

          int rs=santuy.executeUpdate();
          
            LocalDate tanggal_mulai = LocalDate.parse(txt_tanggalMulai.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
            
            int id_mobil = Integer.parseInt(arrOfStr[0]);
          
            String sqlkuy="INSERT INTO transaksi values(?,?,?,?,?)";
            PreparedStatement state=conn.prepareStatement(sqlkuy);
            state.setString(1, null);
            state.setString(2, txtPenyewa.getText());
            state.setString(3, txtKtp.getText());
            state.setString(4, txt_tanggalMulai.getText());
            state.setInt(5, id_mobil);
                
            int res=state.executeUpdate();
            if(res>0){
                JOptionPane.showMessageDialog(this,"Input Berhasil");
                setJTable();
            }

        }
         
        catch (SQLException sqle) {
            JOptionPane.showMessageDialog(this,"Input Gagal = " + sqle.getMessage());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,"Koneksi Gagal " +e.getMessage());
        }
    }
    
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
            java.util.logging.Logger.getLogger(FSewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FSewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FSewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FSewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FSewa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BClose;
    private javax.swing.JButton BTambah;
    private javax.swing.JButton BtnLaporan;
    private javax.swing.JTable TSewa;
    private javax.swing.JComboBox<String> comboMobil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtKtp;
    private javax.swing.JTextField txtPenyewa;
    private javax.swing.JTextField txt_tanggalMulai;
    // End of variables declaration//GEN-END:variables
}
