/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cek;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @author hurricane
 */
public class UbahdataController implements Initializable {

    @FXML
    private JFXButton btn_Data;
    @FXML
    private JFXButton btn_lihatData;
    @FXML
    private JFXButton btn_statistik;
    @FXML
    private JFXButton btn_laporan;
    @FXML
    private JFXButton btn_total;
    @FXML
    private Label label;
    @FXML
    private Label label_mean;
    @FXML
    private Label label_modus;
    @FXML
    private Label label_median;
    @FXML
    private Label totalData;
    @FXML
    private Label totalBerat;
    @FXML
    private Label totalUang;
    @FXML
    private TableView<DataSource> tableData;
    @FXML
    private TableColumn<DataSource, String> nama_column;
    @FXML
    private TableColumn<DataSource, String> tanggal_column;
    @FXML
    private TableColumn<DataSource, String> berat_column;
    @FXML
    private TableColumn<DataSource, String> harga_column;
    @FXML
    private TableView<DataInterval> tableInterval;
//    @FXML
//    private TableColumn<DataInterval, Float> batasAwal;
//    @FXML
//    private TableColumn<DataInterval, Float> batasAkhir;
//    @FXML
//    private TableColumn<DataInterval, Float> frekuensi;
    @FXML
    private JFXTextField field_ubahHarga;
    @FXML
    private JFXTextField field_ubahBerat;
    @FXML
    private JFXTextField field_ubahNama;
    @FXML
    private JFXDatePicker field_ubahTanggal;
    @FXML
    private JFXButton btn_ubahData;
    @FXML
    private JFXButton btn_tmbData;
    @FXML
    private JFXButton btn_simpanData;
    
    private List<DataInterval> listInterval;
    private double xiXfi = 0;
    private int totalFrekuensi = 0;
    private int _interval;
    private CatatData listLaundry;
    private DataSource addLaundry;
    private int index = 0;
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
    
    @Override
    public void initialize(URL arg0, ResourceBundle resources){
            initTable();
        try {
            PopulateItemToTable();
            total();
            tab();
        } catch (Exception ex) {
            Logger.getLogger(UbahdataController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    public void tab(){
        try {
            listLaundry = new CatatData();
            addLaundry = new DataSource();
            
            listLaundry.read();
            tableData.setItems(CatatData.read());
            
            nama_column.setCellValueFactory(cellData -> cellData.getValue().getNama());
            tanggal_column.setCellValueFactory(cellData -> cellData.getValue().gettglMasuk());
            harga_column.setCellValueFactory(cellData -> cellData.getValue().getHarga());
            berat_column.setCellValueFactory(cellData -> cellData.getValue().getBerat());
        } catch (Exception ex) {
            Logger.getLogger(UbahdataController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
    @FXML
    private void tambahData(ActionEvent event) throws IOException{
        try {
            try {
                boolean statusa = true;
                boolean statusb = true;
                int beratx = 0;
                int hargax = 0;
                Calendar cal = new GregorianCalendar();
                int tgl = field_ubahTanggal.getValue().getDayOfMonth();
                int bulan = field_ubahTanggal.getValue().getMonthValue()-1;
                int tahun = field_ubahTanggal.getValue().getYear();
                cal.set(tahun, bulan, tgl);
                SimpleDateFormat sdf = new SimpleDateFormat("dd - MMMM -yyyy");
                String tanggal = sdf.format(cal.getTime());
                
                try {
                beratx = Integer.parseInt(field_ubahBerat.getText());                      
                }
                catch (RuntimeException x) {
                    statusa = false;
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Perhatian");
                    alert.setHeaderText("Tipe data isian salah");
                    alert.setContentText("Berat haruslah bilangan");
                    alert.showAndWait();
                }
                try {
                    hargax = Integer.parseInt(field_ubahHarga.getText());                      
                }
                catch (RuntimeException x) {
                    statusb = false;
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Perhatian");
                    alert.setHeaderText("Tipe data isian salah");
                    alert.setContentText("Harga haruslah bilangan");
                    alert.showAndWait();
                }
                if(statusa == true & statusb == true){
                    DataSource dataSC = new DataSource(field_ubahNama.getText(), String.valueOf(beratx), String.valueOf(hargax), tanggal);
                    try {
                        CatatData.InputFile(dataSC);
                    } catch (SAXException ex) {
                        Logger.getLogger(UbahdataController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(UbahdataController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
            PopulateItemToTable();
            total();
            initTable();
            tab();
        } catch (Exception ex) {
            Logger.getLogger(UbahdataController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void showLaundryEdit(DataSource laundry){
        //DataSource addlaundry = new DataSource();
        if (laundry != null){
            field_ubahNama.setText(laundry.getNama().get());
            field_ubahBerat.setText(laundry.getBerat().get());
            field_ubahHarga.setText(laundry.getHarga().get());
            LocalDate date = laundry.getTTG().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            field_ubahTanggal.setValue(date);

            
        }else {
            field_ubahNama.setText("");
            field_ubahBerat.setText("");
            field_ubahHarga.setText("");
            field_ubahTanggal.setValue(null);
        }
    }
    
    @FXML
    private void edit(ActionEvent e) {
        
        addLaundry = tableData.getSelectionModel().getSelectedItem();
        
        boolean okClicked = true;
        if (addLaundry != null){
           
            if (okClicked){
                  showLaundryEdit(addLaundry);       
            }
        }else {
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Item Selected");
            alert.setContentText("Please selected a item in the table.");
            
            alert.showAndWait();
        }
    }
    
    @FXML
    private void simpan(ActionEvent event) throws Exception {
       DataSource dhp = (DataSource) tableData.getSelectionModel().getSelectedItem();
       index = tableData.getSelectionModel().getFocusedIndex();
       dhp.setNama(field_ubahNama.getText());
       dhp.setBerat(field_ubahBerat.getText());
       dhp.setHarga(field_ubahHarga.getText());
       
       Calendar cal = new GregorianCalendar();
            int tgl = field_ubahTanggal.getValue().getDayOfMonth();
            int bulan = field_ubahTanggal.getValue().getMonthValue()-1;
            int tahun = field_ubahTanggal.getValue().getYear();
            cal.set(tahun, bulan, tgl);
       SimpleDateFormat sdf = new SimpleDateFormat("dd - MMMM -yyyy");
       
       String tanggal = sdf.format(cal.getTime());
       dhp.settglMasuk(tanggal);
       CatatData.updateData(dhp, index);
       
    }

    @FXML
    private void hapus(ActionEvent event) throws Exception {
       DataSource dhp = (DataSource) tableData.getSelectionModel().getSelectedItem();
       index = tableData.getSelectionModel().getFocusedIndex();
       CatatData.deleteData(index);
        tab();
        PopulateItemToTable();
    }

    
    
    
    public void initTable()
    {
        TableColumn<DataInterval, Float> batasBawah = new TableColumn<>("Batas Bawah");
        batasBawah.setMinWidth(55);
        batasBawah.setCellValueFactory(new PropertyValueFactory<>("batasBawah"));

        TableColumn<DataInterval, Float> batasAtas = new TableColumn<>("Batas Atas");
        batasAtas.setMinWidth(70);
        batasAtas.setCellValueFactory(new PropertyValueFactory<>("batasAtas"));

        TableColumn<DataInterval, Float> frekuensi = new TableColumn<>("Frekuensi");
        frekuensi.setMinWidth(79);
        frekuensi.setCellValueFactory(new PropertyValueFactory<>("frekuensi"));

        tableInterval.getColumns().addAll(batasBawah, batasAtas, frekuensi);
    }

    public void PopulateItemToTable() throws Exception
    {
        ObservableList<Float> dummy = FXCollections.observableArrayList();
        try {
            for (DataSource dhp: CatatData.read())
            {
                dummy.add(dhp.getBeratAsli());
            }

            Collections.sort(dummy);
            float rentang = (dummy.get(dummy.size()-1) - dummy.get(0));
            System.out.println(rentang);
            double nKelas = 1 + (3.3*Math.log10(dummy.size()));
            int banyakKelas = nKelas %1 == 0? (int)nKelas : (int)Math.floor(nKelas) +1;
            System.out.println(banyakKelas);
            double x = rentang / banyakKelas;
            float interval = x % 1 == 0? (int) x : (int) Math.floor(x) + 1;
            System.out.println(interval);
            ObservableList<DataInterval> listDi = FXCollections.observableArrayList();

            for(int i =0; i< banyakKelas; i++)
            {
                float batasBawah;
                DataInterval di = new DataInterval();
                if(listDi.size() == 0)
                {
                    batasBawah = dummy.get(0);
                    di.setBatasBawah(batasBawah);
                    di.setBatasAtas((batasBawah + interval));
                }
                else
                {
                    batasBawah = listDi.get(listDi.size()-1).getBatasAtas() + 1;
                    di.setBatasBawah(batasBawah);
                    di.setBatasAtas(batasBawah + interval);
                }
                listDi.add(di);
            }

            for (DataInterval di :listDi)
            {
                for (Float angka: dummy)
                {
                    if(angka >= di.getBatasBawah() && angka <= di.getBatasAtas())
                        di.setFrekuensi(1);
                }
            }
            listInterval = listDi;
            tableInterval.setItems(listDi);

        } catch (ParserConfigurationException | IOException | ParseException | SAXException e) {
            e.printStackTrace();
        }
    }
    public void total() throws Exception {
        ObservableList listLaundry = CatatData.read();
        totalData.setText(String.valueOf(listLaundry.size()));
        label_mean.setText(String.valueOf(hitungMean()));
        label_median.setText(String.valueOf(hitungMedian()));
        label_modus.setText(String.valueOf(hitungModus()));
    }
    private Object getScene() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    private float hitungMean()
    {
        float rata2;
        for (DataInterval dhp: listInterval)
        {
            this.xiXfi += ((dhp.getBatasAtas()+dhp.getBatasBawah())/2) * dhp.getFrekuensi();
            totalFrekuensi += dhp.getFrekuensi();
        }
        rata2 = (float) (xiXfi / totalFrekuensi);

        return rata2;
    }

    public double hitungMedian()
    {
        double L0 = 0;
        double fm = 0;
        double c =0;
        int cek = (int) listInterval.get(0).getFrekuensi();
        int index = 0;

        int n = 0;
        for(int i =0; i< listInterval.size(); i++)
        {
            n += listInterval.get(i).getFrekuensi();
        }
        n = n/2;
        while (n> cek)
        {
            index++;
            cek = (int) (cek +listInterval.get(index).getFrekuensi());
        }

        L0 = listInterval.get(index).getBatasBawah() - 0.5;
        c =listInterval.get(index+1).getBatasBawah() - listInterval.get(index).getBatasAtas();
        cek = (int) (cek - listInterval.get(index).getFrekuensi());
        fm = listInterval.get(index).getFrekuensi();

        double median = L0 + (c * (n-cek)/fm);
        return median;
    }

    public double hitungModus()
    {
        double modus =0;
        double b, b1,b2;
        int index = 0;
        double terbesar = 0;
        for(int i =0; i<listInterval.size(); i++)
        {
           if(listInterval.get(i).getFrekuensi() > terbesar)
            {
                index = i;
                terbesar = listInterval.get(i).getFrekuensi();
            }
        }
        double interval = listInterval.get(index+1).getBatasAtas() - listInterval.get(index).getBatasAtas();
        System.out.println("Interval "+interval);
        b1 = index !=0? (listInterval.get(index).getFrekuensi() - listInterval.get(index-1).getFrekuensi()) : listInterval.get(index).getFrekuensi();
        System.out.println("b1 " + b1);
        b2 = (listInterval.get(index).getFrekuensi() - listInterval.get(index+1).getFrekuensi());
        System.out.println("b2 "+b2);
        b = listInterval.get(index).getBatasBawah() - 0.5;
        System.out.println("b "+b);
        modus = b + ((b1/(b1+b2)) *(interval));
        return modus;
    }

    
}
