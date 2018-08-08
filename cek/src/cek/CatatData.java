/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cek;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CatatData {
    private ObservableList<DataSource> list;
    private String extFile;
    
    public int banyakData() throws Exception{
        List<DataSource> listLaundry = CatatData.read();
        int x = listLaundry.size();
        return x;
    }
    public float totalBerat(){
        float total = 0;
        for (DataSource dc : list){
            total += dc.getBeratAsli();
        }
        return total;
    }
    public float totalHarga(){
        float total = 0;
        for (DataSource dc : list){
            total += dc.getHargaAsli();
        }
        return total;
    }
    
    public CatatData(){
        list = FXCollections.observableArrayList();
        extFile = "dataLaundry.xml";
    }
    
    public ObservableList<DataSource> getListLaundry(){
        return list;
    }
    
    public int index(DataSource laundry){
        int j = 0;
        for (int i = 0 ;i<list.size();i++){
            if (list.get(i).getNama() == laundry.getNama()){
                j = i;
            }
        }
        return j;
    }
    
    public void hapus(int i){
        list.remove(i);
    }
    public static void InputFileBaruLaundry(DataSource hasil) throws TransformerException, ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        org.w3c.dom.Document doc = docBuilder.newDocument();
        org.w3c.dom.Element rootElement = doc.createElement("Pencatatan");
        doc.appendChild(rootElement);

        org.w3c.dom.Element laundry = doc.createElement("Laundry");
        rootElement.appendChild(laundry);

        org.w3c.dom.Element nama = doc.createElement("Nama");
        nama.appendChild(doc.createTextNode(hasil.getNamaAsli()));
        laundry.appendChild(nama);

        org.w3c.dom.Element berat = doc.createElement("Berat");
        berat.appendChild(doc.createTextNode(String.valueOf(hasil.getBerat())));
        laundry.appendChild(berat);
        
        org.w3c.dom.Element harga = doc.createElement("Harga");
        harga.appendChild(doc.createTextNode(String.valueOf(hasil.getHarga())));
        laundry.appendChild(harga);
        
        org.w3c.dom.Element tanggal = doc.createElement("Tanggal");
        tanggal.appendChild(doc.createTextNode(String.valueOf(hasil.gettglAsli())));
        laundry.appendChild(tanggal);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("dataLaundry.xml"));

        transformer.transform(source, result);
    }
    
    public static void InputFile(DataSource hasil) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        org.w3c.dom.Document doc = docBuilder.parse("dataLaundry.xml");
        org.w3c.dom.Element root = doc.getDocumentElement();

        List<DataSource> dataList = new ArrayList<>();
        dataList.add(hasil);
        try
        {
            for (DataSource catatan: dataList)
            {
                org.w3c.dom.Element rootElement = doc.createElement("Pencatatan");

                org.w3c.dom.Element laundry = doc.createElement("Laundry");
                rootElement.appendChild(laundry);

                org.w3c.dom.Element nama = doc.createElement("Nama");
                nama.appendChild(doc.createTextNode(catatan.getNamaAsli()));
                laundry.appendChild(nama);

                org.w3c.dom.Element berat = doc.createElement("Berat");
                berat.appendChild(doc.createTextNode(catatan.getBeratString()));
                laundry.appendChild(berat);

                org.w3c.dom.Element harga = doc.createElement("Harga");
                harga.appendChild(doc.createTextNode(catatan.getHargaString()));
                laundry.appendChild(harga);

                org.w3c.dom.Element tanggal = doc.createElement("Tanggal");
                tanggal.appendChild(doc.createTextNode(catatan.gettglAsli()));
                laundry.appendChild(tanggal);

                root.appendChild(laundry);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("dataLaundry.xml"));

            transformer.transform(source, result);

        } catch (TransformerConfigurationException exx) {
            System.out.println(exx.getMessage());
        } catch (TransformerException exxx) {
            System.out.println(exxx.getMessage());
        }
    }
    
    public static ObservableList<DataSource> read() throws Exception{
        ObservableList<DataSource> listLaundry =FXCollections.observableArrayList();
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        org.w3c.dom.Document doc = docBuilder.parse("dataLaundry.xml");
        NodeList laundry = doc.getElementsByTagName("Laundry");
        for (int i =0; i <laundry.getLength(); i++)
        {
            DataSource dhp = new DataSource();
            Node node = laundry.item(i);
            org.w3c.dom.Element elemen = (org.w3c.dom.Element) node;
            dhp.setNama(elemen.getElementsByTagName("Nama").item(0).getTextContent());
            dhp.setBerat(elemen.getElementsByTagName("Berat").item(0).getTextContent());
            dhp.setHarga(elemen.getElementsByTagName("Harga").item(0).getTextContent());
            //Date tglLaundry = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(elemen.getElementsByTagName("Tanggal").item(0).getTextContent());            
            dhp.settglMasuk(elemen.getElementsByTagName("Tanggal").item(0).getTextContent());
            listLaundry.add(dhp);
        }
        return listLaundry;
    }
    
    public  void write()throws Exception {
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            doc.setXmlStandalone(true);
            Element rootElement = doc.createElement("Pencatatan");
            doc.appendChild(rootElement);
            for(int i=0; i<list.size();i++){

                Element laundry = doc.createElement("Laundry");
                rootElement.appendChild(laundry);

                Element fieldNama = doc.createElement("Nama");
                fieldNama.setTextContent(String.valueOf(list.get(i).getNama().get()));
                laundry.appendChild(fieldNama);
                
                Element fieldStock = doc.createElement("Berat");
                fieldStock.setTextContent(String.valueOf(list.get(i).getBerat().get()));
                laundry.appendChild(fieldStock);

                Element fieldTarget_penjualan = doc.createElement("Harga");
                fieldTarget_penjualan.setTextContent(String.valueOf(list.get(i).getHarga().get()));
                laundry.appendChild(fieldTarget_penjualan);
                
                Element fieldTerjual = doc.createElement("Tanggal");
                fieldTerjual.setTextContent(String.valueOf(list.get(i).gettglMasuk().get()));
                laundry.appendChild(fieldTerjual);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource dom = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("dataLaundry.xml"));
            transformer.transform(dom, result);
        }
        catch(Exception e){
            System.out.println("ERROR : "+e.getMessage());
        }
    }
    
    public static void updateData(DataSource dhp, int index) throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        org.w3c.dom.Document doc = null;
        try {
            doc = docBuilder.parse("dataLaundry.xml");
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NodeList listProduksi = doc.getElementsByTagName("Laundry");
        org.w3c.dom.Element el = null;
        el = (org.w3c.dom.Element) listProduksi.item(index);

        Node _nodeMesin = el.getElementsByTagName("Nama").item(0).getFirstChild();
        _nodeMesin.setNodeValue(dhp.getNamaAsli());

        Node _nodeTarget = el.getElementsByTagName("Berat").item(0).getFirstChild();
        _nodeTarget.setNodeValue(dhp.getBeratString());

        Node _nodeShift = el.getElementsByTagName("Harga").item(0).getFirstChild();
        _nodeShift.setNodeValue(dhp.getHargaString());

        Node _nodeHasilOK = el.getElementsByTagName("Tanggal").item(0).getFirstChild();
        _nodeHasilOK.setNodeValue(dhp.gettglAsli());


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("dataLaundry.xml"));

        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
    
     public static void deleteData(int index)
    {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        org.w3c.dom.Document doc = null;
        try {
            doc = docBuilder.parse("dataLaundry.xml");
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        org.w3c.dom.Element el = (org.w3c.dom.Element) doc.getElementsByTagName("Laundry").item(index);
        Node parent = el.getParentNode();
        parent.removeChild(el);
        parent.normalize();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("dataLaundry.xml"));

        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
