/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cek;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @author hurricane
 */
public class StatistikController implements Initializable {

    @FXML
    private JFXButton btn_Data;
    @FXML
    private JFXButton btn_lihatData;
    @FXML
    private JFXButton btn_statistik;
    @FXML
    private JFXButton btn_laporan;
    @FXML
    private JFXButton btn_detail;
    @FXML
    private Label label;
    @FXML
    private Label lbl_rataBerat;
    @FXML
    private Label lbl_medianBerat;
    @FXML
    private Label lbl_rataHarga;
    @FXML
    private Label lbl_medianHarga;
    @FXML
    private Label lbl_modusNama;
    @FXML 
    private LineChart lineLaundry;
    @FXML 
    private CategoryAxis sumbuX;
    @FXML 
    private NumberAxis sumbuY;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showOkLinechart();
            btn_detail();
        } catch (Exception ex) {
            Logger.getLogger(StatistikController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void btn_Data(ActionEvent event) {
        Parent root;
        try
        {
            root = FXMLLoader.load(getClass().getResource("Beranda.fxml"));
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

    @FXML
    private void btn_detail() throws Exception{
        ObservableList listLaundry = CatatData.read();
        lbl_rataBerat.setText(String.valueOf(Statistik.HitungRataBerat(listLaundry)));
        lbl_rataHarga.setText(String.valueOf(Statistik.HitungRataHarga(listLaundry)));
        lbl_medianBerat.setText(String.valueOf(Statistik.medianBerat(listLaundry)));
        lbl_medianHarga.setText(String.valueOf(Statistik.medianHarga(listLaundry)));
        
    }
    
    public void showOkLinechart()
    {
        try {
            XYChart.Series series = new XYChart.Series();
            List<DataSource> list = new ArrayList<>(CatatData.read());
            for (DataSource dataSource : list) {
                 series.getData().add(new XYChart.Data(dataSource.gettglAsli(), dataSource.getBeratAsli()));
            }           
            lineLaundry.getData().addAll(series);
        } catch (Exception ex) {
            Logger.getLogger(StatistikController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showOkPiechart() throws Exception{
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
            data.add(new PieChart.Data("Ojan", 100));
            
    }
}
