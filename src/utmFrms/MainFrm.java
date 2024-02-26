/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package utmFrms;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import utmClases.ReadingThread;

/**
 *
 * @author 
 */
public class MainFrm extends javax.swing.JFrame {
    
    SerialPort serialPort;
    OutputStream outputStream;
    
    String inBuffer = "";
    
    ReadingThread rThread;

    /**
     * Creates new form MainFrm
     */
    public MainFrm() {
        initComponents();
        initPortTab();
        setEnableCommands(false);
        loadConfig();
    }
    
    private void initPortTab(){
        this.cBoxBaudrate.setSelectedItem("9600");
        this.btlCConnection.setEnabled(false);
        this.btnSend.setEnabled(false);
    }
    
    private void setEnableCommands(boolean state){
        Component components[] = this.pnlCommands.getComponents();
        for(Component c : components){
            c.setEnabled(state);
        }
        this.btnChargeConfigArduino.setEnabled(state);
    }
    
    private void loadConfig(){
        File configFile = new File("MachineConfig.txt");
        
        if(configFile.exists()){
            try{
                Scanner scan = new Scanner(configFile);
                while(scan.hasNext()){
                    String line = scan.nextLine();
                    String tokens[] = line.split(":");
                    
                    if(line.startsWith("stepsRev")){
                        this.txtStepsRev.setText(tokens[1]);
                    }
                    else if(line.startsWith("mmRev")){
                        this.txtmmRev.setText(tokens[1]);
                    }
                    else if(line.startsWith("stepsShortMov")){
                        this.txtStpsSMov.setText(tokens[1]);
                    }
                    else if(line.startsWith("stepsLargeMov")){
                        this.txtStpsLMov.setText(tokens[1]);
                    }
                    else if(line.startsWith("movSpeed")){
                        this.txtMovSpeed.setText(tokens[1]);
                    }
                    else if(line.startsWith("loadCellOffset")){
                        this.txtLCOffset.setText(tokens[1]);
                    }
                    else if(line.startsWith("loadCellScale")){
                        this.txtLCScale.setText(tokens[1]);
                    }
                    else if(line.startsWith("tensileTestSpeed")){
                        this.txtTestSpeed.setText(tokens[1]);
                    }
                }

            }
            catch(IOException e){
                System.out.println("Error Reading File");
            }
        }
        else{
            this.txtStepsRev.setText("200");
            this.txtmmRev.setText("5");
            this.txtStpsLMov.setText("200");
            this.txtStpsSMov.setText("20");
            this.txtMovSpeed.setText("120");
            this.txtLCOffset.setText("0");
            this.txtLCScale.setText("-674.56872558");
            this.txtTestSpeed.setText("60");
        }
    }
    
    private void serialEventReading(SerialPort port){
        port.addDataListener(new SerialPortDataListener(){
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent spe) {
                byte newData[] = spe.getReceivedData();
                                
                for(int i=0; i<newData.length; i++){
                    inBuffer += (char)newData[i];
                    txtADataIn.setText(inBuffer);
                }
            }
        });
    }
    /*
    private void sendData(){
        outputStream = serialPort.getOutputStream();
        String data = this.txtDataOut.getText();
        
        try{
            outputStream.write(data.getBytes());
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    */
    private void sendCommand(String comm){
        outputStream = serialPort.getOutputStream();
                        
        try{
            outputStream.write(comm.getBytes());
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    private int getCompValue(float speed){
        int compV;
        float stepsRev = Float.parseFloat(this.txtStepsRev.getText());
        float mmRev = Float.parseFloat(this.txtmmRev.getText());
        
        // speed mm/min
        // interrupt frequency (Hz) = (Arduino clock speed 16,000,000Hz) / (2 * prescaler * (compare match register + 1))
        // compare match register = [ 16,000,000Hz/ (2 * prescaler <1024> * desired interrupt frequency) ] - 1
        float mmSeg = speed / 60;
        float revMM = 1 / mmRev;
        float hz = mmSeg * revMM * stepsRev;
        
        compV = (int) (16000000 / (2 * 1024 * hz) ) - 1;
        
        return compV;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMConfig = new javax.swing.JTabbedPane();
        pnlComPort = new javax.swing.JPanel();
        pnlPortS = new javax.swing.JPanel();
        lblComPort = new javax.swing.JLabel();
        cBoxPort = new javax.swing.JComboBox<>();
        lblBaudrate = new javax.swing.JLabel();
        cBoxBaudrate = new javax.swing.JComboBox<>();
        lblConStatus = new javax.swing.JLabel();
        pBarConStatus = new javax.swing.JProgressBar();
        btnConnect = new javax.swing.JButton();
        btlCConnection = new javax.swing.JButton();
        pnlSerialData = new javax.swing.JPanel();
        txtDataOut = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtADataIn = new javax.swing.JTextArea();
        btnClearInData = new javax.swing.JButton();
        pnlMachineConfig = new javax.swing.JPanel();
        pnlMSpecs = new javax.swing.JPanel();
        lblStpsRev = new javax.swing.JLabel();
        txtStepsRev = new javax.swing.JTextField();
        lblmmRev = new javax.swing.JLabel();
        txtmmRev = new javax.swing.JTextField();
        pnlMovConfig = new javax.swing.JPanel();
        lblStpsSMov = new javax.swing.JLabel();
        txtStpsSMov = new javax.swing.JTextField();
        lblStpsLMov = new javax.swing.JLabel();
        txtStpsLMov = new javax.swing.JTextField();
        lvlMovSpeed = new javax.swing.JLabel();
        txtMovSpeed = new javax.swing.JTextField();
        lblmmMin = new javax.swing.JLabel();
        lblInteger = new javax.swing.JLabel();
        lblInteger1 = new javax.swing.JLabel();
        btnSaveConfig = new javax.swing.JButton();
        pnlTestConf = new javax.swing.JPanel();
        lblTestSpeed = new javax.swing.JLabel();
        txtTestSpeed = new javax.swing.JTextField();
        lblTestSpeedUnits = new javax.swing.JLabel();
        pnlLoadCellConf = new javax.swing.JPanel();
        lblLoadCellOffset = new javax.swing.JLabel();
        lblLoadCellScale = new javax.swing.JLabel();
        txtLCOffset = new javax.swing.JTextField();
        txtLCScale = new javax.swing.JTextField();
        btnChargeConfigArduino = new javax.swing.JButton();
        lblInteger2 = new javax.swing.JLabel();
        pnlInterface = new javax.swing.JPanel();
        pnlCommands = new javax.swing.JPanel();
        btnSTest = new javax.swing.JButton();
        btnEnMotor = new javax.swing.JButton();
        btnDisMotor = new javax.swing.JButton();
        tBtnRevM = new javax.swing.JToggleButton();
        pBarRevM = new javax.swing.JProgressBar();
        btnLMovUp = new javax.swing.JButton();
        btnSMovUp = new javax.swing.JButton();
        brnLMovDown = new javax.swing.JButton();
        btnSMovDown = new javax.swing.JButton();
        lblMotorComm = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblTTest = new javax.swing.JLabel();
        btnEStop = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        pnlGraphV = new javax.swing.JPanel();
        pnlGraph = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("UTM");

        pnlPortS.setBorder(javax.swing.BorderFactory.createTitledBorder("Port Settings"));

        lblComPort.setText("COM Port");

        cBoxPort.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cBoxPortPopupMenuWillBecomeVisible(evt);
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        lblBaudrate.setText("Baudrate");

        cBoxBaudrate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4800", "9600", "38400", "57600", "115200" }));

        lblConStatus.setText("Connection Status");

        btnConnect.setText("Connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        btlCConnection.setText("Close Connection");
        btlCConnection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btlCConnectionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPortSLayout = new javax.swing.GroupLayout(pnlPortS);
        pnlPortS.setLayout(pnlPortSLayout);
        pnlPortSLayout.setHorizontalGroup(
            pnlPortSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPortSLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPortSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pBarConStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlPortSLayout.createSequentialGroup()
                        .addGroup(pnlPortSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPortSLayout.createSequentialGroup()
                                .addGroup(pnlPortSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblComPort)
                                    .addComponent(lblBaudrate))
                                .addGap(54, 54, 54)
                                .addGroup(pnlPortSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cBoxPort, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cBoxBaudrate, 0, 144, Short.MAX_VALUE)))
                            .addComponent(lblConStatus))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(pnlPortSLayout.createSequentialGroup()
                .addGroup(pnlPortSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConnect)
                    .addComponent(btlCConnection))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlPortSLayout.setVerticalGroup(
            pnlPortSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPortSLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPortSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblComPort)
                    .addComponent(cBoxPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPortSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBaudrate)
                    .addComponent(cBoxBaudrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnConnect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btlCConnection)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(lblConStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pBarConStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlSerialData.setBorder(javax.swing.BorderFactory.createTitledBorder("Serial "));

        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        txtADataIn.setEditable(false);
        txtADataIn.setColumns(20);
        txtADataIn.setRows(5);
        jScrollPane1.setViewportView(txtADataIn);

        btnClearInData.setText("Clear Data");
        btnClearInData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearInDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSerialDataLayout = new javax.swing.GroupLayout(pnlSerialData);
        pnlSerialData.setLayout(pnlSerialDataLayout);
        pnlSerialDataLayout.setHorizontalGroup(
            pnlSerialDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSerialDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSerialDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE)
                    .addGroup(pnlSerialDataLayout.createSequentialGroup()
                        .addComponent(txtDataOut)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSend))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSerialDataLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnClearInData)))
                .addContainerGap())
        );
        pnlSerialDataLayout.setVerticalGroup(
            pnlSerialDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSerialDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSerialDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSend))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClearInData)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlComPortLayout = new javax.swing.GroupLayout(pnlComPort);
        pnlComPort.setLayout(pnlComPortLayout);
        pnlComPortLayout.setHorizontalGroup(
            pnlComPortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlComPortLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlComPortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlSerialData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPortS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlComPortLayout.setVerticalGroup(
            pnlComPortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlComPortLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlPortS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlSerialData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlMConfig.addTab("COM Port", pnlComPort);

        pnlMSpecs.setBorder(javax.swing.BorderFactory.createTitledBorder("Machine Specifications"));

        lblStpsRev.setText("Steps/rev");

        lblmmRev.setText("mm/rev");

        javax.swing.GroupLayout pnlMSpecsLayout = new javax.swing.GroupLayout(pnlMSpecs);
        pnlMSpecs.setLayout(pnlMSpecsLayout);
        pnlMSpecsLayout.setHorizontalGroup(
            pnlMSpecsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMSpecsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMSpecsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStpsRev)
                    .addComponent(lblmmRev))
                .addGap(45, 45, 45)
                .addGroup(pnlMSpecsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtmmRev, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(txtStepsRev))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMSpecsLayout.setVerticalGroup(
            pnlMSpecsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMSpecsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMSpecsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStpsRev)
                    .addComponent(txtStepsRev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlMSpecsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblmmRev)
                    .addComponent(txtmmRev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlMovConfig.setBorder(javax.swing.BorderFactory.createTitledBorder("Movment Configurations"));

        lblStpsSMov.setText("Steps per Short Movment");

        lblStpsLMov.setText("Steps per Large Movment");

        lvlMovSpeed.setText("Movment Speed");

        lblmmMin.setText("mm/min");

        lblInteger.setText("(integer)");

        lblInteger1.setText("(integer)");

        javax.swing.GroupLayout pnlMovConfigLayout = new javax.swing.GroupLayout(pnlMovConfig);
        pnlMovConfig.setLayout(pnlMovConfigLayout);
        pnlMovConfigLayout.setHorizontalGroup(
            pnlMovConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMovConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMovConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStpsSMov)
                    .addComponent(lblStpsLMov)
                    .addComponent(lvlMovSpeed))
                .addGap(53, 53, 53)
                .addGroup(pnlMovConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtStpsSMov)
                    .addComponent(txtStpsLMov)
                    .addComponent(txtMovSpeed, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMovConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblmmMin)
                    .addComponent(lblInteger)
                    .addComponent(lblInteger1))
                .addContainerGap(556, Short.MAX_VALUE))
        );
        pnlMovConfigLayout.setVerticalGroup(
            pnlMovConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMovConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMovConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStpsSMov)
                    .addComponent(txtStpsSMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInteger))
                .addGap(18, 18, 18)
                .addGroup(pnlMovConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStpsLMov)
                    .addComponent(txtStpsLMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInteger1))
                .addGap(18, 18, 18)
                .addGroup(pnlMovConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lvlMovSpeed)
                    .addComponent(txtMovSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblmmMin))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSaveConfig.setText("Save Config");
        btnSaveConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveConfigActionPerformed(evt);
            }
        });

        pnlTestConf.setBorder(javax.swing.BorderFactory.createTitledBorder("Tensile Test Configurations"));

        lblTestSpeed.setText("Tensile Test Speed");

        lblTestSpeedUnits.setText("mm/min");

        javax.swing.GroupLayout pnlTestConfLayout = new javax.swing.GroupLayout(pnlTestConf);
        pnlTestConf.setLayout(pnlTestConfLayout);
        pnlTestConfLayout.setHorizontalGroup(
            pnlTestConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTestConfLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTestSpeed)
                .addGap(55, 55, 55)
                .addComponent(txtTestSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTestSpeedUnits)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTestConfLayout.setVerticalGroup(
            pnlTestConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTestConfLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTestConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTestSpeed)
                    .addComponent(txtTestSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTestSpeedUnits))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlLoadCellConf.setBorder(javax.swing.BorderFactory.createTitledBorder("Load Cell Configurations"));

        lblLoadCellOffset.setText("Offset");

        lblLoadCellScale.setText("Scale");

        btnChargeConfigArduino.setText("Charge Configuration");
        btnChargeConfigArduino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChargeConfigArduinoActionPerformed(evt);
            }
        });

        lblInteger2.setText("(integer)");

        javax.swing.GroupLayout pnlLoadCellConfLayout = new javax.swing.GroupLayout(pnlLoadCellConf);
        pnlLoadCellConf.setLayout(pnlLoadCellConfLayout);
        pnlLoadCellConfLayout.setHorizontalGroup(
            pnlLoadCellConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoadCellConfLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLoadCellConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLoadCellOffset)
                    .addComponent(lblLoadCellScale))
                .addGap(33, 33, 33)
                .addGroup(pnlLoadCellConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtLCOffset)
                    .addComponent(txtLCScale, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLoadCellConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLoadCellConfLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnChargeConfigArduino))
                    .addGroup(pnlLoadCellConfLayout.createSequentialGroup()
                        .addComponent(lblInteger2)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnlLoadCellConfLayout.setVerticalGroup(
            pnlLoadCellConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoadCellConfLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLoadCellConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLoadCellOffset)
                    .addComponent(txtLCOffset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInteger2))
                .addGap(18, 18, 18)
                .addGroup(pnlLoadCellConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLoadCellScale)
                    .addComponent(txtLCScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChargeConfigArduino))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlMachineConfigLayout = new javax.swing.GroupLayout(pnlMachineConfig);
        pnlMachineConfig.setLayout(pnlMachineConfigLayout);
        pnlMachineConfigLayout.setHorizontalGroup(
            pnlMachineConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMachineConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMachineConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlLoadCellConf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMovConfig, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMSpecs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlMachineConfigLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSaveConfig))
                    .addComponent(pnlTestConf, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlMachineConfigLayout.setVerticalGroup(
            pnlMachineConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMachineConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlMSpecs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlMovConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlLoadCellConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlTestConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSaveConfig)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlMConfig.addTab("Machine Configurations", pnlMachineConfig);

        pnlCommands.setBorder(javax.swing.BorderFactory.createTitledBorder("Commands"));

        btnSTest.setText("Start Tensile Test");
        btnSTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSTestActionPerformed(evt);
            }
        });

        btnEnMotor.setText("Enable Motor");
        btnEnMotor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnMotorActionPerformed(evt);
            }
        });

        btnDisMotor.setText("Disable Motor");
        btnDisMotor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisMotorActionPerformed(evt);
            }
        });

        tBtnRevM.setText("Reversed Motor");
        tBtnRevM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tBtnRevMActionPerformed(evt);
            }
        });

        btnLMovUp.setText("Large UP");
        btnLMovUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLMovUpActionPerformed(evt);
            }
        });

        btnSMovUp.setText("Short UP");
        btnSMovUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSMovUpActionPerformed(evt);
            }
        });

        brnLMovDown.setText("Large DOWN");
        brnLMovDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brnLMovDownActionPerformed(evt);
            }
        });

        btnSMovDown.setText("Short DOWN");
        btnSMovDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSMovDownActionPerformed(evt);
            }
        });

        lblMotorComm.setText("Motor Commands");

        jLabel1.setText("Movment Commands");

        lblTTest.setText("Tensile Test");

        btnEStop.setBackground(new java.awt.Color(255, 0, 0));
        btnEStop.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        btnEStop.setText("Emergency \nSTOP");
        btnEStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEStopActionPerformed(evt);
            }
        });

        btnStop.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnStop.setText("STOP");
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCommandsLayout = new javax.swing.GroupLayout(pnlCommands);
        pnlCommands.setLayout(pnlCommandsLayout);
        pnlCommandsLayout.setHorizontalGroup(
            pnlCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCommandsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMotorComm)
                    .addGroup(pnlCommandsLayout.createSequentialGroup()
                        .addGroup(pnlCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlCommandsLayout.createSequentialGroup()
                                .addComponent(btnLMovUp)
                                .addGap(75, 75, 75)
                                .addComponent(btnSMovUp))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlCommandsLayout.createSequentialGroup()
                                .addComponent(btnEnMotor)
                                .addGap(18, 18, 18)
                                .addComponent(btnDisMotor)))
                        .addGap(18, 18, 18)
                        .addComponent(tBtnRevM)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pBarRevM, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTTest)
                            .addComponent(btnSTest)))
                    .addGroup(pnlCommandsLayout.createSequentialGroup()
                        .addComponent(brnLMovDown)
                        .addGap(52, 52, 52)
                        .addComponent(btnSMovDown)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCommandsLayout.createSequentialGroup()
                        .addComponent(btnEStop)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCommandsLayout.createSequentialGroup()
                        .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        pnlCommandsLayout.setVerticalGroup(
            pnlCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCommandsLayout.createSequentialGroup()
                .addGroup(pnlCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCommandsLayout.createSequentialGroup()
                        .addComponent(lblMotorComm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnEnMotor)
                                .addComponent(btnDisMotor)
                                .addComponent(tBtnRevM))
                            .addComponent(pBarRevM, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblTTest))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLMovUp)
                            .addComponent(btnSMovUp)
                            .addComponent(btnSTest))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(brnLMovDown)
                            .addComponent(btnSMovDown))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCommandsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEStop)))
                .addContainerGap())
        );

        pnlGraphV.setBorder(javax.swing.BorderFactory.createTitledBorder("Graph Visualizer"));

        pnlGraph.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pnlGraphLayout = new javax.swing.GroupLayout(pnlGraph);
        pnlGraph.setLayout(pnlGraphLayout);
        pnlGraphLayout.setHorizontalGroup(
            pnlGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 928, Short.MAX_VALUE)
        );
        pnlGraphLayout.setVerticalGroup(
            pnlGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 306, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlGraphVLayout = new javax.swing.GroupLayout(pnlGraphV);
        pnlGraphV.setLayout(pnlGraphVLayout);
        pnlGraphVLayout.setHorizontalGroup(
            pnlGraphVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGraphVLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlGraphVLayout.setVerticalGroup(
            pnlGraphVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGraphVLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlInterfaceLayout = new javax.swing.GroupLayout(pnlInterface);
        pnlInterface.setLayout(pnlInterfaceLayout);
        pnlInterfaceLayout.setHorizontalGroup(
            pnlInterfaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInterfaceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInterfaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlGraphV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlCommands, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlInterfaceLayout.setVerticalGroup(
            pnlInterfaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInterfaceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlCommands, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlGraphV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlMConfig.addTab("Interface", pnlInterface);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMConfig)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMConfig)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cBoxPortPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cBoxPortPopupMenuWillBecomeVisible
        this.cBoxPort.removeAllItems();
        SerialPort portList[] = SerialPort.getCommPorts();
        
        for(SerialPort p : portList){
            this.cBoxPort.addItem(p.getSystemPortName());
        }
    }//GEN-LAST:event_cBoxPortPopupMenuWillBecomeVisible

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        try{
            SerialPort portList[] = SerialPort.getCommPorts();
            serialPort = portList[this.cBoxPort.getSelectedIndex()];
            serialPort.setBaudRate(Integer.parseInt(this.cBoxBaudrate.getSelectedItem().toString()));
            serialPort.setNumDataBits(8);
            serialPort.setNumStopBits(1);
            serialPort.setParity(0);
            serialPort.openPort();
            
            if(serialPort.isOpen()){
                JOptionPane.showMessageDialog(this, serialPort.getDescriptivePortName() + " -- Connection success");
                this.cBoxPort.setEnabled(false);
                this.pBarConStatus.setValue(100);
                this.btnConnect.setEnabled(false);
                
                this.btlCConnection.setEnabled(true);
                this.btnSend.setEnabled(true);
                
                serialEventReading(serialPort);
                
                setEnableCommands(true);
            }
            else{
                JOptionPane.showMessageDialog(this, serialPort.getDescriptivePortName() + " -- Connection failed");
            }
        }
        catch (ArrayIndexOutOfBoundsException i){
            JOptionPane.showMessageDialog(this, "Choose COM Port", "ERROR", ERROR_MESSAGE);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(this, e, "ERROR", ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnConnectActionPerformed

    private void btlCConnectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btlCConnectionActionPerformed
        if(serialPort.isOpen()){
            serialPort.closePort();
            this.cBoxPort.setEnabled(true);
            this.pBarConStatus.setValue(0);
            this.btnConnect.setEnabled(true);
            this.btlCConnection.setEnabled(false);
            this.btnSend.setEnabled(false);
            serialPort.removeDataListener();
            
            setEnableCommands(false);
            this.tBtnRevM.setSelected(false);
            this.pBarRevM.setValue(0);
        }
    }//GEN-LAST:event_btlCConnectionActionPerformed

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        outputStream = serialPort.getOutputStream();
        String data = this.txtDataOut.getText() + "\n";
        
        try{
            outputStream.write(data.getBytes());
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnSendActionPerformed

    private void btnLMovUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLMovUpActionPerformed
        String steps = this.txtStpsLMov.getText();
        String speed = this.txtMovSpeed.getText();
        int compV = getCompValue(Float.parseFloat(speed));
        
        String comm = "%SCU," + steps + "," + String.valueOf(compV) + ";";
                
        sendCommand(comm);
    }//GEN-LAST:event_btnLMovUpActionPerformed

    private void btnSMovUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSMovUpActionPerformed
        String steps = this.txtStpsSMov.getText();
        String speed = this.txtMovSpeed.getText();
        int compV = getCompValue(Float.parseFloat(speed));
        
        String comm = "%SCU," + steps + "," + String.valueOf(compV) + ";";
                
        sendCommand(comm);
    }//GEN-LAST:event_btnSMovUpActionPerformed

    private void btnSMovDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSMovDownActionPerformed
        String steps = this.txtStpsSMov.getText();
        String speed = this.txtMovSpeed.getText();
        int compV = getCompValue(Float.parseFloat(speed));
        
        String comm = "%SCD," + steps + "," + String.valueOf(compV) + ";";
                
        sendCommand(comm);
    }//GEN-LAST:event_btnSMovDownActionPerformed

    private void brnLMovDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brnLMovDownActionPerformed
        String steps = this.txtStpsLMov.getText();
        String speed = this.txtMovSpeed.getText();
        int compV = getCompValue(Float.parseFloat(speed));
        
        String comm = "%SCD," + steps + "," + String.valueOf(compV) + ";";
                
        sendCommand(comm);
    }//GEN-LAST:event_brnLMovDownActionPerformed

    private void btnEnMotorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnMotorActionPerformed
        String comm = "$EM;";
                
        sendCommand(comm);
    }//GEN-LAST:event_btnEnMotorActionPerformed

    private void btnDisMotorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisMotorActionPerformed
        String comm = "$DM;";
        
        sendCommand(comm);
    }//GEN-LAST:event_btnDisMotorActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        try{
            serialPort.removeDataListener();
            serialEventReading(serialPort);
        }
        catch(Exception e){
        }
        
        String comm = "#s;";
        sendCommand(comm);
        
        rThread.endthread();
    }//GEN-LAST:event_btnStopActionPerformed

    private void btnEStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEStopActionPerformed
        try{
            serialPort.removeDataListener();
            serialEventReading(serialPort);
        }
        catch(Exception e){
        }
        
        String comm = "#e;";
        sendCommand(comm);
        
        rThread.endthread();
    }//GEN-LAST:event_btnEStopActionPerformed

    private void btnSTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSTestActionPerformed
        float stepsRev = Float.parseFloat(this.txtStepsRev.getText());
        float mmRev = Float.parseFloat(this.txtmmRev.getText());
        serialPort.removeDataListener();
        rThread = new ReadingThread(serialPort, pnlGraph, txtADataIn, mmRev, stepsRev);
        
        String speed = this.txtTestSpeed.getText();
        int compV = getCompValue(Float.parseFloat(speed));
        String comm = "#TTS," + String.valueOf(compV) + ";";
        
        
        
        sendCommand(comm);
    }//GEN-LAST:event_btnSTestActionPerformed

    private void tBtnRevMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tBtnRevMActionPerformed
        String comm;
        
        if(this.tBtnRevM.isSelected()){
            comm = "$IR;";
            this.pBarRevM.setValue(100);
        }
        else{
            comm = "$NIR;";
            this.pBarRevM.setValue(0);
        }
        
        sendCommand(comm);
    }//GEN-LAST:event_tBtnRevMActionPerformed

    private void btnSaveConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveConfigActionPerformed
        FileWriter fw = null;
        try {
            File configFile = new File("MachineConfig.txt");
            if(!configFile.exists()){
                configFile.createNewFile();
            }
            
            fw = new FileWriter(configFile);
            String line;
            String field;
            
            
            field = this.txtStepsRev.getText();
            if(field.isEmpty()) field = "0";
            line = "stepsRev:" + field + "\n";
            fw.write(line);
            
            
            field = this.txtmmRev.getText();
            if(field.isEmpty()) field = "0";
            line = "mmRev:" + field + "\n";
            fw.write(line);
            
            
            field = this.txtStpsSMov.getText();
            if(field.isEmpty()) field = "0";
            line = "stepsShortMov:" + field + "\n";
            fw.write(line);
                        
            
            field = this.txtStpsLMov.getText();
            if(field.isEmpty()) field = "0";
            line = "stepsLargeMov:" + field + "\n";
            fw.write(line);
            
            
            field = this.txtMovSpeed.getText();
            if(field.isEmpty()) field = "0";
            line = "movSpeed:" + field + "\n";
            fw.write(line);
            
            
            field = this.txtLCOffset.getText();
            if(field.isEmpty()) field = "0";
            line = "loadCellOffset:" + field + "\n";
            fw.write(line);
            
            
            field = this.txtLCScale.getText();
            if(field.isEmpty()) field = "0";
            line = "loadCellScale:" + field + "\n";
            fw.write(line);
            
            
            field = this.txtTestSpeed.getText();
            if(field.isEmpty()) field = "0";
            line = "tensileTestSpeed:" + field + "\n";
            fw.write(line);
            
        } catch (IOException ex) {
            Logger.getLogger(MainFrm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(MainFrm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_btnSaveConfigActionPerformed

    private void btnChargeConfigArduinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChargeConfigArduinoActionPerformed
        String comm = "@CAL," + this.txtLCOffset.getText() + "," + this.txtLCScale.getText() + ";";
        
        sendCommand(comm);
    }//GEN-LAST:event_btnChargeConfigArduinoActionPerformed

    private void btnClearInDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearInDataActionPerformed
        inBuffer = "";
        this.txtADataIn.setText(inBuffer);
    }//GEN-LAST:event_btnClearInDataActionPerformed

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
            java.util.logging.Logger.getLogger(MainFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton brnLMovDown;
    private javax.swing.JButton btlCConnection;
    private javax.swing.JButton btnChargeConfigArduino;
    private javax.swing.JButton btnClearInData;
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnDisMotor;
    private javax.swing.JButton btnEStop;
    private javax.swing.JButton btnEnMotor;
    private javax.swing.JButton btnLMovUp;
    private javax.swing.JButton btnSMovDown;
    private javax.swing.JButton btnSMovUp;
    private javax.swing.JButton btnSTest;
    private javax.swing.JButton btnSaveConfig;
    private javax.swing.JButton btnSend;
    private javax.swing.JButton btnStop;
    private javax.swing.JComboBox<String> cBoxBaudrate;
    private javax.swing.JComboBox<String> cBoxPort;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBaudrate;
    private javax.swing.JLabel lblComPort;
    private javax.swing.JLabel lblConStatus;
    private javax.swing.JLabel lblInteger;
    private javax.swing.JLabel lblInteger1;
    private javax.swing.JLabel lblInteger2;
    private javax.swing.JLabel lblLoadCellOffset;
    private javax.swing.JLabel lblLoadCellScale;
    private javax.swing.JLabel lblMotorComm;
    private javax.swing.JLabel lblStpsLMov;
    private javax.swing.JLabel lblStpsRev;
    private javax.swing.JLabel lblStpsSMov;
    private javax.swing.JLabel lblTTest;
    private javax.swing.JLabel lblTestSpeed;
    private javax.swing.JLabel lblTestSpeedUnits;
    private javax.swing.JLabel lblmmMin;
    private javax.swing.JLabel lblmmRev;
    private javax.swing.JLabel lvlMovSpeed;
    private javax.swing.JProgressBar pBarConStatus;
    private javax.swing.JProgressBar pBarRevM;
    private javax.swing.JPanel pnlComPort;
    private javax.swing.JPanel pnlCommands;
    private javax.swing.JPanel pnlGraph;
    private javax.swing.JPanel pnlGraphV;
    private javax.swing.JPanel pnlInterface;
    private javax.swing.JPanel pnlLoadCellConf;
    private javax.swing.JTabbedPane pnlMConfig;
    private javax.swing.JPanel pnlMSpecs;
    private javax.swing.JPanel pnlMachineConfig;
    private javax.swing.JPanel pnlMovConfig;
    private javax.swing.JPanel pnlPortS;
    private javax.swing.JPanel pnlSerialData;
    private javax.swing.JPanel pnlTestConf;
    private javax.swing.JToggleButton tBtnRevM;
    private javax.swing.JTextArea txtADataIn;
    private javax.swing.JTextField txtDataOut;
    private javax.swing.JTextField txtLCOffset;
    private javax.swing.JTextField txtLCScale;
    private javax.swing.JTextField txtMovSpeed;
    private javax.swing.JTextField txtStepsRev;
    private javax.swing.JTextField txtStpsLMov;
    private javax.swing.JTextField txtStpsSMov;
    private javax.swing.JTextField txtTestSpeed;
    private javax.swing.JTextField txtmmRev;
    // End of variables declaration//GEN-END:variables
}
