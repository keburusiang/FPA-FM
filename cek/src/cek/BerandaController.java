/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cek;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.IOException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author hurricane
 */
public class BerandaController implements Initializable {
    
    
    
    private Label label;
    
    @FXML
    private JFXButton btn_Data;
    
    @FXML
    private JFXButton btn_lihatData;
    
    @FXML
    private JFXButton btn_statistik;
    
    @FXML
    private JFXButton btn_laporan;
    
    @FXML
    private JFXTextField nama;
    
    @FXML
    private JFXTextField berat;
    
    @FXML
    private JFXTextField harga;
    
    @FXML
    private DatePicker tanggal;
    
    @FXML
    private JFXButton tambahData;
    
    public void btn_statistik(ActionEvent event) {
        Parent root;
        try
        {
            root = FXMLLoader.load(getClass().getResource("Statistik.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Stage stageToClose = (Stage) btn_Data.getScene().getWindow();
            stageToClose.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void btn_lihatData(ActionEvent event) {
        Parent root;
        try
        {
            root = FXMLLoader.load(getClass().getResource("Ubahdata.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Stage stageToClose = (Stage) btn_lihatData.getScene().getWindow();
            stageToClose.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void btnTambah(ActionEvent e) throws IOException{
        try
            {
            Calendar cal = new GregorianCalendar();
            boolean statusa = true;
            boolean statusb = true;
            int beratx = 0;
            int hargax = 0;
            int tgl = tanggal.getValue().getDayOfMonth();
            int bulan = tanggal.getValue().getMonthValue()-1;
            int tahun = tanggal.getValue().getYear();
            cal.set(tahun, bulan, tgl);
            SimpleDateFormat sdf = new SimpleDateFormat("dd - MMMM -yyyy");
            String tanggal = sdf.format(cal.getTime());
            try {
                beratx = Integer.parseInt(berat.getText());                      
            }
            catch (RuntimeException x) {
                statusa = false;
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Perhatian");
                alert.setHeaderText("Tipe data isian salah");
                alert.setContentText("Berat haruslah bilangan");
                alert.showAndWait();
            }
            try {
                hargax = Integer.parseInt(harga.getText());                      
            }
            catch (RuntimeException x) {
                statusb = false;
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Perhatian");
                alert.setHeaderText("Tipe data isian salah");
                alert.setContentText("Harga haruslah bilangan");
                alert.showAndWait();
            }
            if(statusa == true & statusb == true){
                DataSource dataSC = new DataSource(nama.getText(), String.valueOf(beratx), String.valueOf(hargax), tanggal);
                try {
                    CatatData.InputFile(dataSC);
                } catch (SAXException ex) {
                    Logger.getLogger(BerandaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(BerandaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
