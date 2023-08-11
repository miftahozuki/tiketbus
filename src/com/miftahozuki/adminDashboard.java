/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.miftahozuki;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.PreparedStatement;
/**
 *
 * @author Mift4hus
 */
public class adminDashboard extends javax.swing.JFrame {

    /**
     * Creates new form adminDashboard
     */
    public adminDashboard() {
        
        initComponents();
        getData();
        addListeners();
    }
    
     private void addListeners() {
        // Menambahkan ListSelectionListener untuk JTable
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedData = jTable1.getSelectedRow();
                    // Mendapatkan data dari JTable dan melakukan tindakan yang sesuai
                    String kdB = (String) jTable1.getValueAt(selectedData, 1);
                    String nB = (String) jTable1.getValueAt(selectedData, 2);
                    String tglK = (String) jTable1.getValueAt(selectedData, 3);
                    String est = (String) jTable1.getValueAt(selectedData, 4);
                    String kA = (String) jTable1.getValueAt(selectedData, 5);
                    String kT = (String) jTable1.getValueAt(selectedData, 6);
                    String harga = (String) jTable1.getValueAt(selectedData, 7);
                    System.out.println(nB + tglK + est + kA + kT + harga);
                    
                    kodeBus.setText(kdB);
                    namaBus.setText(nB);
                    tglKeberangkatan.setText(tglK);
                    estimasi.setText(est);
                    kAsal.setText(kA);
                    kTujuan.setText(kT);
                    price.setText(harga);
                    kodeBus.enable(false);
                }
            }
        });
    }
     
    private void Clear() {
        //
        kodeBus.setText("");
        namaBus.setText("");
        tglKeberangkatan.setText("");
        estimasi.setText("");
        kAsal.setText("");
        kTujuan.setText("");
        price.setText("");
        kodeBus.enable(true);
        
        
    }
    
    
        public void deleteData() {
            // TODO add your handling code here:
        int selectedData = jTable1.getSelectedRow();
        String id = (String) jTable1.getValueAt(selectedData, 1);
        System.out.println(id);
        try{
            String query = "DELETE FROM tiket"
                    +" WHERE id_tiket='"+ id + "' ";
            
            Connection connection = koneksiDB.getConnection(); // Mendapatkan koneksi ke database
            Statement statement = connection.createStatement();
            statement.executeUpdate(query); 
            Clear();
            getData();

        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error deleting data: " + e.getMessage());
        }
    
    }

    
    public void saveData() {  
        try{
        String kdB = kodeBus.getText();
        String nb = namaBus.getText();
        String tglK = tglKeberangkatan.getText();
        String est = estimasi.getText();
        String kA = kAsal.getText();
        String kT = kTujuan.getText();
        String harga = price.getText();
        String query =  "INSERT INTO tiket (id_tiket, nama_bus, tgl_keberangkatan, estimasi, asal, tujuan, harga) VALUES (?, ?, ?, ?, ?, ?, ?)";
          PreparedStatement preparedStatement = koneksiDB.getConnection().prepareStatement(query);
         preparedStatement.setString(1, kdB);
         preparedStatement.setString(2, nb);
         preparedStatement.setString(3, tglK);
         preparedStatement.setString(4, est);
         preparedStatement.setString(5, kA);
         preparedStatement.setString(6, kT);
         preparedStatement.setString(7, harga);
         preparedStatement.executeUpdate();
        
         preparedStatement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            // Menampilkan pesan kesalahan
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }
    
    
    public void getData() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
       
                
        tableModel.addColumn ("No");
        tableModel.addColumn ("Kode Bus");
        tableModel.addColumn ("Nama Bus");
        tableModel.addColumn ("Tgl Keberangkatan");
        tableModel.addColumn ("Estimasi");
        tableModel.addColumn ("Kota Asal");
        tableModel.addColumn ("Kota Tujuan");
        tableModel.addColumn ("Harga");

        
        String sql = "SELECT * FROM tiket";
        
        
        //
         try {
            Connection connection = koneksiDB.getConnection(); // Mendapatkan koneksi ke database
             Statement statement = connection.createStatement(
           ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY
            );
            ResultSet resultSet = statement.executeQuery(sql); // Menjalankan kueri
            

 // Mendapatkan metadata kolom
            int columnCount = resultSet.getMetaData().getColumnCount();

            // Mendapatkan jumlah baris hasil kueri
            int rowCount = 0;
            if (resultSet.last()) {
                rowCount = resultSet.getRow();
                resultSet.beforeFirst();
            }
            
            
            // Tambahkan kolom ke model tabel berdasarkan metadata
//            for (int col = 1; col <= columnCount; col++) {
//                tableModel.addColumn(resultSet.getMetaData().getColumnName(col));
//            }

                        // Tambahkan baris ke model tabel dari hasil kueri
            int noUrut = 1; // Nilai awal increment
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount +1];
                rowData[0] = noUrut++; 
                for (int col = 1; col <= columnCount; col++) {
                    rowData[col] = resultSet.getString(col);
                }
                tableModel.addRow(rowData);
            }
           
            

            // Menutup koneksi dan sumber daya lainnya
            resultSet.close();
            statement.close();
            connection.close();
            
            
            
            // Sekarang Anda memiliki data dalam bentuk array data[][]
            // Anda dapat melakukan operasi yang diinginkan dengan data ini
        } catch (SQLException e) {
            e.printStackTrace();
            // Tangani pengecualian, misalnya dengan menampilkan pesan kesalahan
        }
     

    }
    
    public void editData() {
    String nb = namaBus.getText();
        String tglK = tglKeberangkatan.getText();
        String est = estimasi.getText();
        String kA = kAsal.getText();
        String kT = kTujuan.getText();
        String harga = price.getText();
        int selectedData = jTable1.getSelectedRow();
        String id = (String) jTable1.getValueAt(selectedData, 1);
        System.out.println(id);
        try{
            String query = "UPDATE tiket set nama_bus='"+ nb+"',tgl_keberangkatan='"+tglK+"',estimasi='"+est+"',asal='"+kA+"',tujuan='"+kT+"',harga="+harga
                    +" WHERE id_tiket='"+ id + "' ";
            System.out.println(query);
            
            Connection connection = koneksiDB.getConnection(); // Mendapatkan koneksi ke database
            Statement statement = connection.createStatement();
            statement.executeUpdate(query); 
            Clear();
            getData();

        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error updating data: " + e.getMessage());
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        panel1 = new java.awt.Panel();
        label1 = new java.awt.Label();
        tglKeberangkatan = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        label3 = new java.awt.Label();
        namaBus = new javax.swing.JTextField();
        kAsal = new javax.swing.JTextField();
        kTujuan = new javax.swing.JTextField();
        label4 = new java.awt.Label();
        label5 = new java.awt.Label();
        estimasi = new javax.swing.JTextField();
        price = new javax.swing.JTextField();
        label7 = new java.awt.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        label8 = new java.awt.Label();
        label9 = new java.awt.Label();
        editBtn = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        delBtn = new javax.swing.JButton();
        label6 = new java.awt.Label();
        kodeBus = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 153));

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jButton1.setText("Pembelian");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Tiket");

        jButton3.setText("Logout");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel1.setPreferredSize(new java.awt.Dimension(700, 500));

        label1.setText("label1");

        jButton4.setText("[+] Tambah");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        label3.setText("Kota Asal:");

        namaBus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaBusActionPerformed(evt);
            }
        });

        label4.setText("Kota Tujuan:");

        label5.setText("Kode Bus");

        label7.setText("Tanggal Keberangkatan");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        label8.setText("Estimasi:");

        label9.setText("Harga:");

        editBtn.setText("Edit");
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        jButton6.setText("Clear");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        delBtn.setText("Hapus");
        delBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delBtnActionPerformed(evt);
            }
        });

        label6.setText("Nama Bus:");

        kodeBus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodeBusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(delBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editBtn))
                    .addComponent(tglKeberangkatan, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namaBus, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kodeBus, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6))
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kAsal, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(estimasi, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(estimasi, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kodeBus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kAsal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(namaBus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tglKeberangkatan, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(editBtn)
                            .addComponent(jButton6)
                            .addComponent(delBtn)))
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        loginAdmin loginAdmin = new loginAdmin();
        loginAdmin.setVisible(true);
        this.dispose(); 
    }//GEN-LAST:event_jButton3ActionPerformed

    
    private void namaBusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaBusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaBusActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        saveData();
        getData();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        // TODO add your handling code here:
        editData();
        getData();
        
    }//GEN-LAST:event_editBtnActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        Clear();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void delBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delBtnActionPerformed
       
    deleteData();
     getData();
        
    }//GEN-LAST:event_delBtnActionPerformed

    private void kodeBusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodeBusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kodeBusActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Pembelian Pembelian = new Pembelian();
        Pembelian.setVisible(true);
        this.dispose(); 
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(adminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(adminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(adminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(adminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new adminDashboard().setVisible(true);
            }
        });
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton delBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JTextField estimasi;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField kAsal;
    private javax.swing.JTextField kTujuan;
    private javax.swing.JTextField kodeBus;
    private java.awt.Label label1;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.Label label7;
    private java.awt.Label label8;
    private java.awt.Label label9;
    private javax.swing.JTextField namaBus;
    private java.awt.Panel panel1;
    private javax.swing.JTextField price;
    private javax.swing.JTextField tglKeberangkatan;
    // End of variables declaration//GEN-END:variables
}


