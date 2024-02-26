/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utmClases;

import com.fazecast.jSerialComm.SerialPort;
import java.awt.Dimension;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author 
 */
public class ReadingThread extends Thread {
    SerialPort serialPort;
    
    float mmRev;
    float stepsRev;
    boolean exit;
    String inBuffer = "";
    String testData = "";
    
    JPanel pnlGraph;
    JTextArea txtA;
    
    XYSeries series = new XYSeries("Grafica",false,false);
    XYSeriesCollection seriesCollection = new XYSeriesCollection();
    JFreeChart grafica = ChartFactory.createXYLineChart("Carga - Deformacion", "Deformacion [mm]", "Carga [g]", seriesCollection);
    ChartPanel chartP = new ChartPanel(grafica);

    public ReadingThread(SerialPort serialPort, JPanel pnlGraph, JTextArea txtA, float mmRev, float stepsRev) {
        this.serialPort = serialPort;
        this.pnlGraph = pnlGraph;
        this.txtA = txtA;
        this.exit = false;
        this.mmRev = mmRev;
        this.stepsRev = stepsRev;
        
        seriesCollection.addSeries(series);
        grafica.removeLegend();
        
        Dimension dimension = this.pnlGraph.getSize();
        chartP.setBounds(2,2,dimension.width,dimension.height);
        this.pnlGraph.removeAll();
        this.pnlGraph.add(chartP);
        chartP.setVisible(true);
        this.pnlGraph.repaint();
                
        this.start();
    }
    
    public synchronized void endthread(){
        exit = true;
    }
    
    private synchronized boolean readTestData(){
        String data = "";
        byte bytes[] = new byte[1];
        char character;
        
        if(serialPort.bytesAvailable() > 0){
            serialPort.readBytes(bytes, 1);
            character = (char)bytes[0];
            
            if(character == '<'){
                
                while(character != '>' && !exit){
                    if(serialPort.bytesAvailable() > 0){
                        serialPort.readBytes(bytes, 1);
                        character = (char)bytes[0];
                        
                        if(character != '>'){
                            data += character;
                        }
                    }
                }
                txtA.append(data + "\n");
                
                //testData += data + "\n";
                //System.out.println(data);
                
                if(data.startsWith("TEST")){
                    System.out.println("Ended");
                    return false;
                }
                else{
                    String tokens[] = data.split(",");
                    if(tokens.length == 2){
                        float steps = Float.parseFloat(tokens[0]);
                        float loadCellreading = Float.parseFloat(tokens[1]);
                        float elong = steps * (1/stepsRev) * mmRev;
                        
                        String linea = String.valueOf(elong) + "," + String.valueOf(loadCellreading) + "\n";
                        testData += linea;
                        
                        this.series.add(elong,loadCellreading);
                    }
                }
                //return true;
                
            }
        }
        return true;
    }

    @Override
    public void run() {
        this.series.clear();
        testData = "";
        
        boolean running = true;
        while(running && !exit){
            running = readTestData();
        }
        
        File folder = new File("Tests_csv");
        if(!folder.exists()) folder.mkdir();
        
        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now();
        //System.out.println(time.toString());
        //System.out.println(date);
        
        String name = "Test_"+ date.getDayOfMonth() + "_" + date.getMonthValue() + "_" + date.getYear() + "_" + time.getHour() + "_" + time.getMinute() + "_" + time.getSecond() + ".csv";
        
        File file = new File("Tests_csv/" + name);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ReadingThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(testData);
            
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(ReadingThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        System.out.println("Thread ended");
    }
    
}
