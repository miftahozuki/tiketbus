/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bagus ika putranto
 */
package com.miftahozuki;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
//import javax.swing.JOptionPane;

public class koneksi {
    private Connection koneksi;
    public Connection getKoneksi(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
//            
//            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//            myConfig = DriverManager.getConnection("jdbc:mysql://localhost/java","root","");
            
//            return Connector;
        }catch(ClassNotFoundException ex){
//            JOptionPane.showMessageDialog(null, e);
//            System.err.println("koneksi gagal "+e.getMessage());
//            return null;
        }
        try{
            koneksi = DriverManager.getConnection("jdbc:mysql://localhost/tiketbus", "root", "");
            if(koneksi != null){
                System.out.println("Berhasil Koneksi");
            }
        }catch(SQLException ex){
            System.out.println("Koneksi Gagal");
        }
        return koneksi;
//        return myConfig;
    }
}
