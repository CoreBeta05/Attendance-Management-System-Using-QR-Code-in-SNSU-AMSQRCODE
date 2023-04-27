/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author crew laptop
 */
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;


public class DashBoard extends javax.swing.JFrame implements Runnable,ThreadFactory{
    private WebcamPanel panel=null;
    private Webcam webcam=null;
    private Executor executor = Executors.newSingleThreadExecutor(this);
    public DashBoard() {
        initComponents();
        initWebcam();
        sd();
        st();
        Connect();
        LoadAttendance();
        LoadAttendance2();
        StudentRegistered();
    }
    //Update table when deleted
    private void updateTable(){
        String sql = "SELECT * FROM attendancetimetable";
        try{
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            TimeTable.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception ex){
            }
        }
    }
    
        private void updateTable2(){
        String sql = "SELECT * FROM attendancetimetable";
        try{
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            TimeTable2.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception ex){
                
            }
        }
    }
        
        private void updateTable3(){
        String sql = "SELECT * FROM studenttable";
        try{
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            StudentRegistered.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception ex){
  
            }
        }
    }
    
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    //Connection for mysql
    public void Connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/logindb","root", "");
        } catch(ClassNotFoundException | SQLException ex){
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//Load to attendance time table
    public void LoadAttendance(){
        try {
            int q;
            
            pst = con.prepareStatement("SELECT * FROM attendancetimetable a INNER JOIN studenttable e ON a.IDNumber = e.IDNumber");
            rs = pst.executeQuery();
            
            ResultSetMetaData rss = rs.getMetaData();
            q = rss.getColumnCount();
            
            DefaultTableModel df = (DefaultTableModel)TimeTable.getModel();
            df.setRowCount(0);
            
            while(rs.next()){
                
                Vector v2 = new Vector();
                
                for(int a=1;a<=q;a++){
                    v2.add(rs.getString("Num"));
                    v2.add(rs.getString("IDNumber"));
                    v2.add(rs.getString("FirstName"));
                    v2.add(rs.getString("LastName"));
                    v2.add(rs.getString("LogDate"));
                    v2.add(rs.getString("Time"));
                    v2.add(rs.getString("Course"));
                    v2.add(rs.getString("Section"));
                }
                df.addRow(v2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void LoadAttendance2(){
        try {
            int q;
            
            pst = con.prepareStatement("SELECT * FROM attendancetimetable a INNER JOIN studenttable e ON a.IDNumber = e.IDNumber");
            rs = pst.executeQuery();
            
            ResultSetMetaData rss = rs.getMetaData();
            q = rss.getColumnCount();
            
            DefaultTableModel df = (DefaultTableModel)TimeTable2.getModel();
            df.setRowCount(0);
            
            while(rs.next()){
                
                Vector v2 = new Vector();
                
                for(int a=1;a<=q;a++){
                    v2.add(rs.getString("Num"));
                    v2.add(rs.getString("IDNumber"));
                    v2.add(rs.getString("FirstName"));
                    v2.add(rs.getString("LastName"));
                    v2.add(rs.getString("LogDate"));
                    v2.add(rs.getString("Time"));
                    v2.add(rs.getString("Course"));
                    v2.add(rs.getString("Section"));
                }
                df.addRow(v2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        public void StudentRegistered(){
        try {
            int q;
            pst = con.prepareStatement("SELECT * FROM studenttable");
            rs = pst.executeQuery();
            
            ResultSetMetaData rss = rs.getMetaData();
            q = rss.getColumnCount();
            
            DefaultTableModel df = (DefaultTableModel)StudentRegistered.getModel();
            df.setRowCount(0);
            
            while(rs.next()){
                
                Vector v2 = new Vector();
                
                for(int a=1;a<=q;a++){
                    v2.add(rs.getString("IDNumber"));
                    v2.add(rs.getString("FirstName"));
                    v2.add(rs.getString("LastName"));
                    v2.add(rs.getString("Course"));
                    v2.add(rs.getString("Section"));
                }
                df.addRow(v2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
                
    //Display date and time
    public void sd(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        String dd = sdf.format(d);
        livedate.setText(dd);
    }
    
    public void st(){
        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("hh-mm-ss a");
                String tt = sdf.format(d);
                livetime.setText(tt);
            }
        }).start();
        {
            
        };
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        txtidnumber = new javax.swing.JTextField();
        txtfirstname = new javax.swing.JTextField();
        txtlastname = new javax.swing.JTextField();
        txtcourse = new javax.swing.JTextField();
        txtsection = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        qrcoderesult = new javax.swing.JTextField();
        webcampanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        livetime = new javax.swing.JLabel();
        livedate = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TimeTable = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TimeTable2 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        StudentRegistered = new javax.swing.JTable();
        jButton14 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();

        jLabel3.setText("jLabel3");

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setForeground(new java.awt.Color(255, 0, 0));

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton3.setText("Attendance Time Table");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton7.setText("Scan QR Code");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton8.setText("Back up Data");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 102, 0));
        jPanel2.setForeground(new java.awt.Color(255, 255, 0));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("ADMIN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton6.setText("Register");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 500));

        jTabbedPane1.setBackground(new java.awt.Color(204, 204, 255));

        jPanel7.setBackground(new java.awt.Color(204, 204, 255));
        jPanel7.setForeground(new java.awt.Color(102, 255, 255));

        jButton1.setText("Submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtidnumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidnumberActionPerformed(evt);
            }
        });

        jLabel2.setText("ID Number:");

        jLabel7.setText("First Name:");

        jLabel8.setText("Last Name:");

        jLabel9.setText("Course:");

        jLabel10.setText("Year & Section:");

        jButton5.setText("Clear");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(txtidnumber, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel2)
                    .addComponent(txtcourse, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtlastname, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfirstname, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jButton1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5))
                        .addComponent(txtsection, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(324, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                    .addGap(0, 150, Short.MAX_VALUE)
                    .addComponent(jLabel15)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtidnumber, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfirstname, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtlastname, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcourse, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtsection, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton5))
                .addContainerGap(59, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("Register", jPanel7);

        jPanel8.setBackground(new java.awt.Color(204, 204, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(qrcoderesult, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 190, 210, 30));

        webcampanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        webcampanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(webcampanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 350, 310));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Scan QR Code");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("ID Number:");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 160, -1, -1));

        livetime.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        livetime.setText("0");
        jPanel4.add(livetime, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 150, -1));

        livedate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        livedate.setText("0");
        jPanel4.add(livedate, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, 150, 20));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Date:");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Time:");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 110, -1, -1));

        jPanel8.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 643, 460));

        jTabbedPane1.addTab("Scan QR Code", jPanel8);

        jPanel9.setBackground(new java.awt.Color(204, 204, 255));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TimeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Number:", "ID Number:", "First Name:", "Last Name:", "Log Date:", "Time:", "Course:", "Year & Section:"
            }
        ));
        jScrollPane1.setViewportView(TimeTable);

        jToolBar1.setRollover(true);

        jButton11.setText("jButton11");

        jButton12.setText("Create File");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton13.setText("Delete ");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                    .addContainerGap(202, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(448, Short.MAX_VALUE)))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jButton11)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(275, 275, 275)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(275, Short.MAX_VALUE)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton13)
                .addGap(1087, 1087, 1087)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                    .addContainerGap(677, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(880, Short.MAX_VALUE)))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jButton11)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(742, 742, 742)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(782, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Attendance Time Table", jPanel9);

        jPanel10.setBackground(new java.awt.Color(204, 204, 255));

        jButton9.setText("Create File");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        TimeTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Number:", "ID Number:", "First Name:", "Last Name:", "Log Date:", "Time:", "Course:", "Year & Section:"
            }
        ));
        jScrollPane2.setViewportView(TimeTable2);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Generate Report");

        jLabel6.setText("List of Students");

        jLabel12.setText("Attendance Today");

        StudentRegistered.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Number:", "First Name:", "Last Name:", "Course:", "Year & Section:"
            }
        ));
        jScrollPane3.setViewportView(StudentRegistered);

        jButton14.setText("Unenroll");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton14)
                .addGap(17, 17, 17))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(259, 259, 259)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(244, 244, 244)
                        .addComponent(jLabel5)))
                .addGap(132, 277, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Back up Data", jPanel10);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 650, 500));

        jButton4.setText("Clear");
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(399, 296, -1, -1));

        jButton10.setText("jButton10");
        getContentPane().add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void txtidnumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidnumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidnumberActionPerformed
    //Generate QR Code
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String IDnum = this.txtidnumber.getText();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/logindb","root", "");
            String sql = "INSERT INTO studenttable VALUE(?,?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt( txtidnumber.getText()));
            pstmt.setString(2, txtfirstname.getText());
            pstmt.setString(3, txtlastname.getText());
            pstmt.setString(4, txtcourse.getText());
            pstmt.setString(5, txtsection.getText());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "insertion successful");
            con.close();
            String filepath = "C:\\Users\\crew laptop\\Documents\\NetBeansProjects\\AMSQRCODE\\Pictures\\QRCode\\qr.png";
            String charset = "UTF-8";
            Map <EncodeHintType,ErrorCorrectionLevel> hintMap= new HashMap<EncodeHintType,ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.L);

            BitMatrix matrix= new MultiFormatWriter().encode(new String (IDnum.getBytes(charset),charset),BarcodeFormat.QR_CODE,200,200,hintMap);

            MatrixToImageWriter.writeToFile(matrix, filepath.substring(filepath.lastIndexOf('.')+1),new File(filepath));
            System.out.println("Qr code has been generated at the location "+filepath);

            JFrame frame = new JFrame();
            ImageIcon icon = new ImageIcon("C:\\Users\\crew laptop\\Documents\\NetBeansProjects\\AMSQRCODE\\Pictures\\QRCode\\qr.png");
            JLabel label = new JLabel(icon);
            frame.add(label);
            frame.setDefaultCloseOperation
            (JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            updateTable3();
        }
        catch(Exception ex){
            System.out.println(ex);
    }//GEN-LAST:event_jButton1ActionPerformed
}
    //Clear button
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        txtidnumber.setText(null);
        txtfirstname.setText(null);
        txtlastname.setText(null);
        txtcourse.setText(null);
        txtsection.setText(null);
    }//GEN-LAST:event_jButton5ActionPerformed
        //Safe File to csv
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Attendance Time Table");
        int userSelection = fileChooser.showSaveDialog(this);
        if(userSelection == JFileChooser.APPROVE_OPTION){
             File fileToSave = fileChooser.getSelectedFile();
             //Write to File
            try {
                FileWriter fw = new FileWriter(fileToSave);
                BufferedWriter bw = new BufferedWriter(fw);
                for (int i = 0; i < TimeTable.getRowCount(); i++) {
                    for (int j = 0; j < TimeTable.getColumnCount(); j++) {
                    //Write 
                    bw.write(TimeTable.getValueAt(i, j).toString()+",");
                }
                    bw.newLine();//record per line
            }
                JOptionPane.showMessageDialog(this, "SUCCESSFULLY LOADED","INFORMATION",JOptionPane.INFORMATION_MESSAGE);
                bw.close();
                fw.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "ERROR","ERROR MESSAGE",JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed
//Delete button
    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        int row = TimeTable.getSelectedRow();
        String cell = TimeTable.getModel().getValueAt(row, 0).toString();
        int row2 = TimeTable2.getSelectedRow();
        String cell2 = TimeTable2.getModel().getValueAt(row, 0).toString();
        String sql = "DELETE FROM attendancetimetable where Num="+cell;
        try{
            pst = con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Are you sure you want to delete?");
            updateTable();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }

        
        String sql2 = "DELETE FROM attendancetimetable where Num="+cell;
        try{
            pst = con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Deleted Succesfully");
            updateTable2();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        int row = StudentRegistered.getSelectedRow();
        String cell = StudentRegistered.getModel().getValueAt(row, 0).toString();
        String sql = "DELETE FROM studenttable where IDNumber="+cell;
        try{
            pst = con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Unerolled");
            updateTable3();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
    }//GEN-LAST:event_jButton14ActionPerformed
    }

    /**
     * @param args the command line arguments
     */
    //Scan QR Code
    public static void main(String args[]) {

        }
    private void initWebcam(){
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(size);
        
        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);
        
        webcampanel.add(panel,new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,350,315));
        executor.execute(this);
    }
    @Override
    public void run(){
        do{
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            Result result = null;
            BufferedImage image=null;
            
            if(webcam.isOpen()){
                if((image=webcam.getImage())==null){
                    continue;
                }
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            
            try {
                result = new MultiFormatReader().decode(bitmap);
            } catch (NotFoundException ex) {
                java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            if(result != null){
                try {
                    java.util.Date date = new java.util.Date();
                    java.sql.Date sqldate = new java.sql.Date(date.getTime());
                    java.sql.Timestamp sqltime = new java.sql.Timestamp(date.getTime()); 
                    
                    qrcoderesult.setText(result.getText());
                    
                    pst = con.prepareStatement("SELECT * FROM studenttable WHERE IDNumber = ?");
                    pst.setInt(1, Integer.parseInt( qrcoderesult.getText()));
                    rs = pst.executeQuery();
                    
                    if(rs.next()==true){
                        pst = con.prepareStatement("INSERT INTO attendancetimetable (IDNumber, LogDate, Time) VALUES(?,?,?)");
                        pst.setInt(1, Integer.parseInt( qrcoderesult.getText()));
                        pst.setDate(2, sqldate);
                        pst.setTimestamp(3, sqltime);
                        
                        int q = pst.executeUpdate();
                        
                        if(q==1){
                            JOptionPane.showMessageDialog(this, "Attendance Successfully Inserted!");
                            qrcoderesult.setText("");
                            qrcoderesult.requestFocus();  
                            LoadAttendance();
                            LoadAttendance2();
                        }else{
                            JOptionPane.showMessageDialog(this, "Attendance Error To Insert!");
                        }
                    }else{
                        JOptionPane.showMessageDialog(this, "QR Code Number Is Not Found In Table!");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                   }
                }
        }while(true);
        
    }
    
    
    @Override
    public Thread newThread(Runnable r){
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable StudentRegistered;
    private javax.swing.JTable TimeTable;
    private javax.swing.JTable TimeTable2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel livedate;
    private javax.swing.JLabel livetime;
    private javax.swing.JTextField qrcoderesult;
    private javax.swing.JTextField txtcourse;
    private javax.swing.JTextField txtfirstname;
    private javax.swing.JTextField txtidnumber;
    private javax.swing.JTextField txtlastname;
    private javax.swing.JTextField txtsection;
    private javax.swing.JPanel webcampanel;
    // End of variables declaration//GEN-END:variables

    }
