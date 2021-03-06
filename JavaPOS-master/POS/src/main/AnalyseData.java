
package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;


public class AnalyseData extends javax.swing.JDialog {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    Statement stm = null;
    /**
     * Creates new form AnalyseData
     */
    public AnalyseData(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Graph();
        Graph1();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        chartpanel = new javax.swing.JPanel();
        panelAmount = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        javax.swing.GroupLayout chartpanelLayout = new javax.swing.GroupLayout(chartpanel);
        chartpanel.setLayout(chartpanelLayout);
        chartpanelLayout.setHorizontalGroup(
            chartpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1040, Short.MAX_VALUE)
        );
        chartpanelLayout.setVerticalGroup(
            chartpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Quantity Sold", chartpanel);

        javax.swing.GroupLayout panelAmountLayout = new javax.swing.GroupLayout(panelAmount);
        panelAmount.setLayout(panelAmountLayout);
        panelAmountLayout.setHorizontalGroup(
            panelAmountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1040, Short.MAX_VALUE)
        );
        panelAmountLayout.setVerticalGroup(
            panelAmountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 464, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Amount Sold", panelAmount);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(1081, 553));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
        Graph();
        Graph1();
    }//GEN-LAST:event_formWindowOpened
    private void Graph()
    {       
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        //dynamic info
        DatabaseConnection.DbConnection Connect = new DatabaseConnection.DbConnection();
        conn = Connect.Connectdb();
        try
        {
            String sql = "SELECT ProductId, Date, QuantitySold FROM item_sales GROUP BY Date , ProductId";
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            
            while (rs.next())
            {              
                int qs = rs.getInt("QuantitySold");
                String day = rs.getString("Date");
                String product = rs.getString("ProductId");      
                dataset.setValue(qs, product, day);
            }       
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        //and loop
       
        JFreeChart chart = ChartFactory.createBarChart("Daily Sales Analysis", "Product ID", "Total Quantity Sold", dataset);
        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.BLACK);
        //add to panel
        ChartPanel barpnel = new ChartPanel(chart);
        barpnel.setSize(1100, 600);
        barpnel.setSize(chartpanel.getSize());
        chartpanel.removeAll();
        chartpanel.add(barpnel, BorderLayout.CENTER);
        chartpanel.validate();
       
        try{
            final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            final File file1 = new File("exam.PNG");
            ChartUtilities.saveChartAsPNG(file1, chart, 600, 400, info);
        }catch(Exception e)
        {
         e.printStackTrace();
        }
    }
    private void Graph1()
    {       
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        //dynamic info
        DatabaseConnection.DbConnection Connect = new DatabaseConnection.DbConnection();
        conn = Connect.Connectdb();
        try
        {
            String sql = "SELECT SUM(SellingPrice * QuantitySold) As Amount, ProductId, Date , QuantitySold FROM item_sales GROUP BY Date , ProductId";
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            
            while (rs.next())
            {              
                int qs = rs.getInt("Amount");
                String day = rs.getString("Date");
                String product = rs.getString("ProductId");      
                dataset.setValue(qs, product, day);
            }       
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        //and loop
       
        JFreeChart chart = ChartFactory.createBarChart("Daily Sales Analysis", "Product ID", "Total Amount Sold", dataset);
        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.BLACK);
        //add to panel
        ChartPanel barpnel = new ChartPanel(chart);
        barpnel.setSize(1100, 600);
        barpnel.setSize(panelAmount.getSize());
        panelAmount.removeAll();
        panelAmount.add(barpnel, BorderLayout.CENTER);
        panelAmount.validate();
       
        try{
            final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            final File file1 = new File("amount.PNG");
            ChartUtilities.saveChartAsPNG(file1, chart, 600, 400, info);
        }catch(Exception e)
        {
         e.printStackTrace();
        }
    }
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
            java.util.logging.Logger.getLogger(AnalyseData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnalyseData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnalyseData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnalyseData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AnalyseData dialog = new AnalyseData(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chartpanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panelAmount;
    // End of variables declaration//GEN-END:variables
}
