package proyecto1.pkg0;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Natalia
 */

//class variables {
//    
//    public static XYSeries foca;
//    public static int t_muestreo = 100;
//    public static float cnt = 176.875f;
//    public static int off_all = 0;
//    public static int pausa = 0;
//    public static Color Fondo1 = new Color(0, 76, 153);
//    public static Color Fondo2 = new Color(128, 255, 0);
//    public static Double señal_analoga;
//    public static Double señal_digital;
//    public static String combo_set_analoga;
//    public static String combo_set_digital;
//    public static String Canal = "Canal 1";
//    public static int Channel_ID = 1;
//    public static double[][] series;
//    public static byte[] txDatos ={0x00,0x00};
//    public static char salidasdigitales = 0x00;
//    public static char analogadigitalflag= 0x00;
//    public static char canal_ID= 0x00;
//    public static int parametro1 =0;
//    public static int parametro2 =0;
//    public static int parametro3 =0;
//    public static int parametro4 =0;
//    public static boolean suitche = true ;
//    public static double[][] arregloGuardar;
//    public static ChartPanel grafica;
//    public static double xs;
//    public static float TimeSample;
//    public static int flagFinal =0;
//    public static float Time;
//    public static int maxIdVarsData;
//    public static int idw;
//    public static float valor;
//    //public static Connection conn = null;
//    
//    
//     
//
//}

public class GUI extends javax.swing.JFrame {
    
    XYSeries oSeries = new XYSeries(""); 
    XYSeries oSeries1 = new XYSeries("");  
    XYSeries oAnalog1 = new XYSeries(""); 
    XYSeries oAnalog2 = new XYSeries(""); 
    XYSeries oAnalog3 = new XYSeries(""); 
    XYSeries oAnalog4 = new XYSeries(""); 
    XYSeries oAnalog5 = new XYSeries(""); 
    XYSeries oAnalog6 = new XYSeries("");  
    XYSeries oAnalog7 = new XYSeries(""); 
    XYSeries oAnalog8 = new XYSeries(""); 
    XYSeries oDigital1 = new XYSeries("");
    XYSeries oDigital2 = new XYSeries("");
    XYSeries oDigital3 = new XYSeries("");
    XYSeries oDigital4 = new XYSeries("");
    
    XYSeriesCollection oDataset;
    JFreeChart  oChart;
    ChartPanel oPanel;
    int sampleTime;
    public static    int z;
    
    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        
        //Add radios to group
        inputRadioGroup.add(analog1);
        inputRadioGroup.add(analog2);
        inputRadioGroup.add(analog3);
        inputRadioGroup.add(analog4);
        inputRadioGroup.add(analog5);
        inputRadioGroup.add(analog6);
        inputRadioGroup.add(analog7);
        inputRadioGroup.add(analog8);
        inputRadioGroup.add(digital1);
        inputRadioGroup.add(digital2);
        inputRadioGroup.add(digital3);
        inputRadioGroup.add(digital4);
        
        analog1.setSelected(true);
        
        //inicializar hilo
        sampleTime = 1;
        generateData();
        
        
        oDataset = new XYSeriesCollection();
        oDataset.addSeries(oAnalog1);
        
        oChart = ChartFactory.createXYLineChart("Señales Analogas", "Tiempo[s]", "Volt[V]", oDataset,PlotOrientation.VERTICAL,true, false,false);
        
         XYPlot plot = oChart.getXYPlot();
        
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0,new Color(2, 120,174));
        renderer.setSeriesStroke(0, new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        renderer.setSeriesPaint(1, Color.red);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        
        oChart.setBackgroundPaint(new Color(165, 236, 215));
        plot.setBackgroundPaint(new Color(232, 255, 193));
        plot.getDomainAxis().setAutoRange(true);
        plot.getDomainAxis().setFixedAutoRange(sampleTime*1000);//
        plot.getDomainAxis().setTickLabelFont(new Font("Calibri Bold Caps", Font.BOLD, 12));
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.getRangeAxis().setTickLabelFont(new Font("Calibri Bold Caps", Font.BOLD, 12));
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.getRangeAxis().setAutoRange(true);
//        plot.getDomainAxis().setFixedAutoRange(4000);
        plot.setRenderer(renderer);
        
       
        oPanel= new ChartPanel(oChart);
        chart.setLayout(new java.awt.BorderLayout());
        chart.add(oPanel);
        chart.validate();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public void Escribir_text(String direccion) throws FileNotFoundException {

         int[] myNum = {1, 2};
//        Object nombre_analogas = combo_analogas.getSelectedItem();
//        Object nombre_digitales = combo_digitales.getSelectedItem();
        PrintWriter archivo_datos;
        archivo_datos = new PrintWriter(direccion);
        archivo_datos.println("Señal almacenada");
        
        archivo_datos.println("Time [s] -------    Valor[V] ");
        
        
     
//        double[][] datos = variables.series;
//        double[][] datos = float 1;
//        if (nombre_analogas != "apagado" && nombre_digitales == "apagado") {
//            try {

                
//                for (double[] dato : datos) {
                    archivo_datos.print(Arrays.toString(myNum)+"\n");
                    archivo_datos.println(Arrays.toString(myNum));
//                    archivo_datos.println(Arrays.toString());
//                }
                archivo_datos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        
        
        
//        if (nombre_digitales != "apagado"|| nombre_analogas == "apagado") {
//            try {
//                    archivo_datos = new PrintWriter(direccion);
//
//                
//                for (double[] dato : datos) {
//                    archivo_datos.println(Arrays.toString(dato));
//                }
//                archivo_datos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inputRadioGroup = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        inputs = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        sampleTimeBox = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        analog1 = new javax.swing.JRadioButton();
        analog2 = new javax.swing.JRadioButton();
        analog3 = new javax.swing.JRadioButton();
        analog4 = new javax.swing.JRadioButton();
        analog5 = new javax.swing.JRadioButton();
        analog6 = new javax.swing.JRadioButton();
        analog7 = new javax.swing.JRadioButton();
        analog8 = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        digital3 = new javax.swing.JRadioButton();
        digital4 = new javax.swing.JRadioButton();
        digital1 = new javax.swing.JRadioButton();
        digital2 = new javax.swing.JRadioButton();
        chart = new javax.swing.JPanel();
        export = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        chartTitle = new javax.swing.JLabel();
        analog9 = new javax.swing.JRadioButton();
        outputs = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        lightOutput1 = new javax.swing.JPanel();
        output1 = new javax.swing.JToggleButton();
        jPanel12 = new javax.swing.JPanel();
        lightOutput2 = new javax.swing.JPanel();
        output2 = new javax.swing.JToggleButton();
        jPanel13 = new javax.swing.JPanel();
        lightOutput3 = new javax.swing.JPanel();
        output3 = new javax.swing.JToggleButton();
        jPanel14 = new javax.swing.JPanel();
        lightOutput4 = new javax.swing.JPanel();
        output4 = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Sample time");

        sampleTimeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 ms", "2 ms", "4 ms", "8 ms" }));
        sampleTimeBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sampleTimeBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(sampleTimeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(sampleTimeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Inputs"));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Analog signal"));

        analog1.setSelected(true);
        analog1.setText("Analog 1");
        analog1.setActionCommand("analog1");
        analog1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                analog1StateChanged(evt);
            }
        });
        analog1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analog1ActionPerformed(evt);
            }
        });

        analog2.setText("Analog 2");
        analog2.setActionCommand("analog2");
        analog2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analog2ActionPerformed(evt);
            }
        });

        analog3.setText("Analog 3");
        analog3.setActionCommand("analog3");
        analog3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analog3ActionPerformed(evt);
            }
        });

        analog4.setText("Analog 4");
        analog4.setActionCommand("analog4");
        analog4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analog4ActionPerformed(evt);
            }
        });

        analog5.setText("Analog 5");
        analog5.setActionCommand("analog5");
        analog5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analog5ActionPerformed(evt);
            }
        });

        analog6.setText("Analog 6");
        analog6.setToolTipText("");
        analog6.setActionCommand("analog6");
        analog6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analog6ActionPerformed(evt);
            }
        });

        analog7.setText("Analog 7");
        analog7.setActionCommand("analog7");
        analog7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analog7ActionPerformed(evt);
            }
        });

        analog8.setText("Analog 8");
        analog8.setActionCommand("analog8");
        analog8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analog8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(analog1)
                    .addComponent(analog2)
                    .addComponent(analog3)
                    .addComponent(analog4)
                    .addComponent(analog5)
                    .addComponent(analog6)
                    .addComponent(analog7)
                    .addComponent(analog8))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(analog1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(analog2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(analog3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(analog4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(analog5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(analog6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(analog7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(analog8)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Digital signals"));

        digital3.setText("Digital 3");
        digital3.setActionCommand("Digital3");
        digital3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                digital3ActionPerformed(evt);
            }
        });

        digital4.setText("Digital 4");
        digital4.setActionCommand("Digital4");
        digital4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                digital4ActionPerformed(evt);
            }
        });

        digital1.setText("Digital 1");
        digital1.setActionCommand("Digital1");
        digital1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                digital1ActionPerformed(evt);
            }
        });

        digital2.setText("Digital 2");
        digital2.setActionCommand("Digital2");
        digital2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                digital2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(digital3)
                    .addComponent(digital4)
                    .addComponent(digital2)
                    .addComponent(digital1))
                .addGap(0, 74, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(digital1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(digital2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(digital3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(digital4)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        chart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout chartLayout = new javax.swing.GroupLayout(chart);
        chart.setLayout(chartLayout);
        chartLayout.setHorizontalGroup(
            chartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        chartLayout.setVerticalGroup(
            chartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        export.setText("Export");
        export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportActionPerformed(evt);
            }
        });

        chartTitle.setText("Signal");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chartTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(chartTitle)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        analog9.setText("Analog 8");
        analog9.setActionCommand("analog8");
        analog9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analog9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inputsLayout = new javax.swing.GroupLayout(inputs);
        inputs.setLayout(inputsLayout);
        inputsLayout.setHorizontalGroup(
            inputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputsLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(326, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(export)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputsLayout.createSequentialGroup()
                        .addGroup(inputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(inputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(inputsLayout.createSequentialGroup()
                    .addGap(309, 309, 309)
                    .addComponent(analog9)
                    .addContainerGap(309, Short.MAX_VALUE)))
        );
        inputsLayout.setVerticalGroup(
            inputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputsLayout.createSequentialGroup()
                        .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(export)
                        .addGap(21, 21, 21))
                    .addGroup(inputsLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(inputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(inputsLayout.createSequentialGroup()
                    .addGap(218, 218, 218)
                    .addComponent(analog9)
                    .addContainerGap(219, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("inputs", inputs);

        outputs.setToolTipText("");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Outputs"));

        lightOutput1.setBackground(new java.awt.Color(255, 51, 51));
        lightOutput1.setBorder(new javax.swing.border.MatteBorder(null));
        lightOutput1.setToolTipText("");
        lightOutput1.setMinimumSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout lightOutput1Layout = new javax.swing.GroupLayout(lightOutput1);
        lightOutput1.setLayout(lightOutput1Layout);
        lightOutput1Layout.setHorizontalGroup(
            lightOutput1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        lightOutput1Layout.setVerticalGroup(
            lightOutput1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        output1.setText("Signal 1");
        output1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                output1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(lightOutput1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(output1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(output1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lightOutput1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        lightOutput2.setBackground(new java.awt.Color(255, 51, 51));
        lightOutput2.setBorder(new javax.swing.border.MatteBorder(null));
        lightOutput2.setToolTipText("");
        lightOutput2.setMinimumSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout lightOutput2Layout = new javax.swing.GroupLayout(lightOutput2);
        lightOutput2.setLayout(lightOutput2Layout);
        lightOutput2Layout.setHorizontalGroup(
            lightOutput2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        lightOutput2Layout.setVerticalGroup(
            lightOutput2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        output2.setText("Signal 2");
        output2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                output2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(lightOutput2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(output2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(output2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lightOutput2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        lightOutput3.setBackground(new java.awt.Color(255, 51, 51));
        lightOutput3.setBorder(new javax.swing.border.MatteBorder(null));
        lightOutput3.setToolTipText("");
        lightOutput3.setMinimumSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout lightOutput3Layout = new javax.swing.GroupLayout(lightOutput3);
        lightOutput3.setLayout(lightOutput3Layout);
        lightOutput3Layout.setHorizontalGroup(
            lightOutput3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        lightOutput3Layout.setVerticalGroup(
            lightOutput3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        output3.setText("Signal 3");
        output3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                output3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(lightOutput3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(output3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(output3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lightOutput3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        lightOutput4.setBackground(new java.awt.Color(255, 51, 51));
        lightOutput4.setBorder(new javax.swing.border.MatteBorder(null));
        lightOutput4.setToolTipText("");
        lightOutput4.setMinimumSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout lightOutput4Layout = new javax.swing.GroupLayout(lightOutput4);
        lightOutput4.setLayout(lightOutput4Layout);
        lightOutput4Layout.setHorizontalGroup(
            lightOutput4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        lightOutput4Layout.setVerticalGroup(
            lightOutput4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        output4.setText("Signal 4");
        output4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                output4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(lightOutput4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(output4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(output4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lightOutput4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout outputsLayout = new javax.swing.GroupLayout(outputs);
        outputs.setLayout(outputsLayout);
        outputsLayout.setHorizontalGroup(
            outputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(488, Short.MAX_VALUE))
        );
        outputsLayout.setVerticalGroup(
            outputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputsLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(262, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("outputs", outputs);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sampleTimeBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sampleTimeBoxActionPerformed
        // TODO add your handling code here:
        String sample = sampleTimeBox.getSelectedItem().toString();
        try{
           sampleTime = ((Number)NumberFormat.getInstance().parse(sample)).intValue();
           float tiempo= (float) (sampleTime*Math.pow(10,-3));
            
           
        }
        catch(Exception e){

        }
        
        
    }//GEN-LAST:event_sampleTimeBoxActionPerformed

    private void exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportActionPerformed
        
        String filename = JOptionPane.showInputDialog("Nombre del archivo");
        JFileChooser savefile = new JFileChooser("C:\\Users\\FALCON-X\\Videos");
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("archivos TXT", "txt");
        savefile.setFileFilter(filtro);
        savefile.setSelectedFile(new File(filename+".txt"));
        savefile.showSaveDialog(savefile);
        String direccion = savefile.getSelectedFile().getAbsolutePath();
        
        int sf = savefile.showSaveDialog(null);
        if(sf == JFileChooser.APPROVE_OPTION){
            try {
                Escribir_text(direccion);
                
                //JOptionPane.showMessageDialog(null, "File has been saved","File Saved",JOptionPane.INFORMATION_MESSAGE);
                // true for rewrite, false for override
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(sf == JFileChooser.CANCEL_OPTION){
        }
//        
//        JFileChooser seleccionaArchivo = new JFileChooser();
//        FileNameExtensionFilter filtro = new FileNameExtensionFilter("archivos csv", "csv");
//        seleccionaArchivo.setFileFilter(filtro);
//        
//        int seleccionar = seleccionaArchivo.showOpenDialog(this);
//        
//        if(seleccionar == JFileChooser.APPROVE_OPTION){
//            File archivo = seleccionaArchivo.getSelectedFile();
//            guardarArchivo(archivo);
//            System.out.println("Chao de aquí****************************************");
//        }
    }//GEN-LAST:event_exportActionPerformed

    private void output1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_output1ActionPerformed
        if (output1.isSelected()){
            lightOutput1.setBackground(Color.green);
        }
        else{
            lightOutput1.setBackground(Color.red);
        }
    }//GEN-LAST:event_output1ActionPerformed

    private void output2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_output2ActionPerformed
        if (output2.isSelected()){
            lightOutput2.setBackground(Color.green);
        }
        else{
            lightOutput2.setBackground(Color.red);
        }
    }//GEN-LAST:event_output2ActionPerformed

    private void output3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_output3ActionPerformed
        if (output3.isSelected()){
            lightOutput3.setBackground(Color.green);
        }
        else{
            lightOutput3.setBackground(Color.red);
        }
    }//GEN-LAST:event_output3ActionPerformed

    private void output4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_output4ActionPerformed
        if (output4.isSelected()){
            lightOutput4.setBackground(Color.green);
        }
        else{
            lightOutput4.setBackground(Color.red);
        }
    }//GEN-LAST:event_output4ActionPerformed

    private void analog1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analog1ActionPerformed
        if(analog1.isSelected()){
            oDataset.removeAllSeries();
            oDataset.addSeries(oAnalog1);
            
        }
    }//GEN-LAST:event_analog1ActionPerformed

    private void analog3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analog3ActionPerformed
        if(analog3.isSelected()){
            oDataset.removeAllSeries();
            oDataset.addSeries(oAnalog3);
        }
    }//GEN-LAST:event_analog3ActionPerformed

    private void analog2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analog2ActionPerformed
        // TODO add your handling code here:
        if(analog2.isSelected()){
            oDataset.removeAllSeries();
            oDataset.addSeries(oAnalog2);
//            z=0;
        }
    }//GEN-LAST:event_analog2ActionPerformed

    private void analog4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analog4ActionPerformed
        // TODO add your handling code here:
        if(analog4.isSelected()){
            oDataset.removeAllSeries();
            oDataset.addSeries(oAnalog4);
        }
    }//GEN-LAST:event_analog4ActionPerformed

    private void analog5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analog5ActionPerformed
        // TODO add your handling code here:
        if(analog5.isSelected()){
            oDataset.removeAllSeries();
            oDataset.addSeries(oAnalog5);
        }
    }//GEN-LAST:event_analog5ActionPerformed

    private void analog6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analog6ActionPerformed
        // TODO add your handling code here:
        if(analog6.isSelected()){
            oDataset.removeAllSeries();
            oDataset.addSeries(oAnalog6);
        }
    }//GEN-LAST:event_analog6ActionPerformed

    private void analog7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analog7ActionPerformed
        // TODO add your handling code here:
        if(analog7.isSelected()){
            oDataset.removeAllSeries();
            oDataset.addSeries(oAnalog7);
        }
    }//GEN-LAST:event_analog7ActionPerformed

    private void analog8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analog8ActionPerformed
        // TODO add your handling code here:
        if(analog8.isSelected()){
            oDataset.removeAllSeries();
            oDataset.addSeries(oAnalog8);
        }
    }//GEN-LAST:event_analog8ActionPerformed

    private void digital1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_digital1ActionPerformed
        // TODO add your handling code here:
        if(digital1.isSelected()){
            oDataset.removeAllSeries();
            oDataset.addSeries(oDigital1);
        }
    }//GEN-LAST:event_digital1ActionPerformed

    private void digital2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_digital2ActionPerformed
        // TODO add your handling code here:
        if(digital2.isSelected()){
            oDataset.removeAllSeries();
            oDataset.addSeries(oDigital2);
        }
    }//GEN-LAST:event_digital2ActionPerformed

    private void digital3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_digital3ActionPerformed
        // TODO add your handling code here:
        if(digital3.isSelected()){
            oDataset.removeAllSeries();
            oDataset.addSeries(oDigital3);
        }
    }//GEN-LAST:event_digital3ActionPerformed

    private void digital4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_digital4ActionPerformed
        // TODO add your handling code here:
        if(digital4.isSelected()){
            oDataset.removeAllSeries();
            oDataset.addSeries(oDigital4);
        }
    }//GEN-LAST:event_digital4ActionPerformed

    private void analog9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analog9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_analog9ActionPerformed

    private void analog1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_analog1StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_analog1StateChanged
    /*
    * Este método crea los hilos para actualizar los datos
    */
    public void generateData() {
	Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    
//                  public int z = 0;
                    
                    float t = 0;
                    float x = 0;// hora del pc
                    int  Contador1 = 0;int  Contador2 = 0;int  Contador3 = 0;int  Contador4 = 0;
                    int  flag1 = 0;int  flag2 = 0;int  flag3 = 0;int  flag4 = 0;
//                    oAnalog1.add(x,0);
 
                    ArrayList<Float>tm = new ArrayList<Float>();
                    tm = new ArrayList();                    
                    tm.add(0.0f);

                    while(true){
//                        System.out.println(tm.size());
//                        System.out.println(x);
//                        System.out.println(y);
//                        System.out.println(oSeries.get(x));
                        
                        float tiempo= (float) (sampleTime*Math.pow(10,-3));
                        t=tm.get(tm.size()-1)+tiempo;
//                        y = (float) Math.cos(400*t);
                        tm.add(t);
                        
                        System.out.println(inputRadioGroup.getSelection().getActionCommand());
                        System.out.println(x);
                        System.out.println(z);
                        System.out.println(tiempo);
                        System.out.println(tm.get(tm.size()-1));
                        System.out.println(t);
                        
                        switch(inputRadioGroup.getSelection().getActionCommand()) {                         
                                  
                                case "analog1":
                                    oAnalog1.add(x, Math.sin(1*t));
                                  break;
                                case "analog2":
                                    
                                    oAnalog2.add(x, Math.cos(t));
                                  break;                                
                                case "analog3":
                                    oAnalog3.add(x, Math.cos(0.6f*t));
                                  break;
                                case "analog4":
                                    oAnalog4.add(x, Math.sin(0.8f*t));
                                  break;
                                case "analog5":
                                    oAnalog5.add(x, Math.cos(0.2f*t) + Math.random());
                                  break;
                                case "analog6":
                                    oAnalog6.add(x, Math.sin(0.2f*t) + Math.random()*0.05);
                                  break;
                                case "analog7":
                                    oAnalog7.add(x, Math.cos(0.2f*t) + Math.random()*0.01);
                                  break;
                                case "analog8":
                                    oAnalog8.add(x, Math.sin(0.2f*t) + Math.random()*0.02);
                                  break;
                                case "Digital1":
                                    
                                    if ((Contador1!=10 && flag1 ==0)) {

                                            oDigital1.add(x, 3);

                                    } else {
                                            oDigital1.add(x, 0);
                                    }
                                    if (Contador1==10) {
                                            Contador1=0;
                                              if (flag1==0) {
                                                  flag1=1;}
                                              else{flag1=0;}
                                    } 

                                    Contador1++;
//                                   oDigital1.add(x,);
                                 break;
                                case "Digital2":
                                   if (Contador2!=100 && flag2 ==0) {

                                            oDigital2.add(x, 3);

                                    } else {
                                            oDigital2.add(x, 0);
                                    }
                                     if (Contador2==100) {
                                            Contador2=0;
                                              if (flag2==0) {
                                                  flag2=1;}
                                              else{flag2=0;}
                                    } 
                                    Contador2++;
                                 break;
                                case "Digital3":
                                   if (Contador3!=200 && flag3 ==0) {

                                   
                                            oDigital3.add(x, 3);

                                    } else {
                                            oDigital3.add(x, 0);
                                    }
                                     if (Contador3==200) {
                                            Contador3=0;
                                              if (flag3==0) {
                                                  flag3=1;}
                                              else{flag3=0;}
                                    } 
                                    Contador3++;
                                 break;
                                case "Digital4":
                                   if (Contador4!=500 && flag4 ==0) {

                                    
                                            oDigital4.add(x, 3);

                                    } else {
                                            oDigital4.add(x, 0);
                                    }
                                    
                                    Contador4++; 
                                    if (Contador4==500) {
                                            Contador4=0;
                                              if (flag4==0) {
                                                  flag4=1;}
                                              else{flag4=0;}
                                    } 
                                 break;
//                                
                                default:
                                  // code block
                              }
//                        oDigital1.add(x, Math.random()*10);
//                        oDigital2.add(x, -3 + Math.random()*10);
//                        oDigital3.add(x, Math.random()*2);
//                        oDigital4.add(x, 4 + Math.random()*2);
//                        oSeries.add(x, x*x+5);
//                        oSeries1.add(x, -x);
                        
                        sleep(sampleTime);
                        x++;
                        z++;
                        
                        
                    }                    
      		}
                catch (InterruptedException e) {
                    e.printStackTrace();
		}
            }
        };
	thread.start();
    }
        
//   
   
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
                if ("windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton analog1;
    private javax.swing.JRadioButton analog2;
    private javax.swing.JRadioButton analog3;
    private javax.swing.JRadioButton analog4;
    private javax.swing.JRadioButton analog5;
    private javax.swing.JRadioButton analog6;
    private javax.swing.JRadioButton analog7;
    private javax.swing.JRadioButton analog8;
    private javax.swing.JRadioButton analog9;
    private javax.swing.JPanel chart;
    private javax.swing.JLabel chartTitle;
    private javax.swing.JRadioButton digital1;
    private javax.swing.JRadioButton digital2;
    private javax.swing.JRadioButton digital3;
    private javax.swing.JRadioButton digital4;
    private javax.swing.JButton export;
    private javax.swing.ButtonGroup inputRadioGroup;
    private javax.swing.JPanel inputs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel lightOutput1;
    private javax.swing.JPanel lightOutput2;
    private javax.swing.JPanel lightOutput3;
    private javax.swing.JPanel lightOutput4;
    private javax.swing.JToggleButton output1;
    private javax.swing.JToggleButton output2;
    private javax.swing.JToggleButton output3;
    private javax.swing.JToggleButton output4;
    private javax.swing.JPanel outputs;
    private javax.swing.JComboBox<String> sampleTimeBox;
    // End of variables declaration//GEN-END:variables
}
