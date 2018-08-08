/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import static java.util.Collections.list;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author hurricane
 */
public class DataSource {
//    public SimpleStringProperty nama = new SimpleStringProperty(); 
//    public SimpleStringProperty tglMasuk = new SimpleStringProperty();   
//    public SimpleFloatProperty berat = new SimpleFloatProperty();
//    public SimpleFloatProperty harga = new SimpleFloatProperty();
    public SimpleStringProperty nama ; 
    public SimpleStringProperty tglMasuk ;  
    public SimpleStringProperty berat ;
    public SimpleStringProperty harga ;
    

    
    public DataSource(){
        this("","","","");
    }

    public DataSource(String nama, String berat, String harga, String tglMasuk){
//        setHarga(harga);
//        setBerat(berat);
//        setNama(nama);
//        settglMasuk(tglMasuk);
        this.nama = new SimpleStringProperty(nama);
        this.berat = new SimpleStringProperty(berat);
        this.harga = new SimpleStringProperty(harga);
        this.tglMasuk = new SimpleStringProperty(tglMasuk);
    }
    
    //Nama
    public SimpleStringProperty getNama(){
        return nama;
    } 

    public void setNama(String nm){
        nama.set(nm);
    }
    
    public String getNamaAsli(){
        return nama.get();
        
    }
    
    //Tanggal
    public SimpleStringProperty gettglMasuk(){
        return tglMasuk;
    }

    public void settglMasuk(String tgl){
        tglMasuk.set(tgl);
    }
    public String gettglAsli(){
//        String a = String.valueOf(tglMasuk);
//        return a;
        return tglMasuk.get();
    }
    public Date getTTG() {
        Date x = null;
        try {
            x = new SimpleDateFormat("dd - MMMM -yyyy").parse(tglMasuk.get());
        } catch (ParseException ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x;
    }

    
    
    //BERAT
    public SimpleStringProperty getBerat(){
        return berat;
    }
    
    public int getBB(){
        int a = Integer.parseInt(berat.get());
        return a;
    }
    
    public float getBeratAsli(){
        float a = Float.parseFloat(berat.get());
        return a;
    }

    public void setBerat(String brt){
        berat.set(brt);
    }
    
    public String getBeratString(){
//        String a = String.valueOf(berat);
//        return a;
        return berat.get();
    }
    
    //HARGA
    public SimpleStringProperty getHarga(){
        return harga;
    }
    
    public float getHargaAsli(){
        float a = Float.parseFloat(harga.get());
        return a;
    }
    
    public void setHarga(String hrg){
        harga.set(hrg);
    }
    public String getHargaString(){
//        String a = String.valueOf(harga);
//        return a;
        return harga.get();
    }

}
