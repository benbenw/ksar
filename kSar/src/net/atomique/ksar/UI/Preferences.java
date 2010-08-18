/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Preferences.java
 *
 * Created on 30 juil. 2010, 13:55:43
 */
package net.atomique.ksar.UI;

import java.awt.Dimension;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import net.atomique.ksar.Config;
import net.atomique.ksar.GlobalOptions;

/**
 *
 * @author Max
 */
public class Preferences extends javax.swing.JDialog {

    /** Creates new form Preferences */
    public Preferences(java.awt.Frame parent) {
        super(parent);
        initComponents();
        load_landf();
        Dimension d1 = getSize();
        Dimension d2 = parent.getSize();
        int x = (d2.width - (d1.width / 2)) / 2;
        int y = (d2.height - (d1.height / 2)) / 2;
        setBounds(x, y, d1.width, d1.height);
        setModal(true);
        setVisible(true);
    }

    private void load_landf() {
        UIManager.LookAndFeelInfo looks[] = UIManager.getInstalledLookAndFeels();
        for (int i = 0, n = looks.length; i < n; i++) {
            String tmp = looks[i].getName();
            UI_lanf_model.addElement(tmp);
            if (Config.getLandf().equals(tmp)) {
                jComboBox1.setSelectedItem(tmp);
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        OkButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(430, 430));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setText("Look and Feel: ");
        jPanel5.add(jLabel1);

        jComboBox1.setModel(UI_lanf_model);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox1);

        jPanel4.add(jPanel5);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 2));

        jLabel2.setText("Language: ");
        jPanel6.add(jLabel2);

        jPanel4.add(jPanel6);

        jScrollPane1.setViewportView(jPanel4);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        OkButton.setText("Ok");
        OkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OkButtonActionPerformed(evt);
            }
        });
        jPanel2.add(OkButton);

        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });
        jPanel2.add(CancelButton);

        jPanel3.add(jPanel2);

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        Object source = evt.getSource();
        String lafClassName = null;
        if (source == jComboBox1) {
            JComboBox comboBox = (JComboBox) source;
            lafClassName = (String) comboBox.getSelectedItem();
            if (lafClassName != null) {
                String finalLafClassName = lafClassName;
                for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
                    if (lafClassName.equals(laf.getName())) {
                        try {
                            UIManager.setLookAndFeel(laf.getClassName());
                            SwingUtilities.updateComponentTreeUI(GlobalOptions.getUI());
                        } catch (Exception e) {
                        }

                    }
                }
            }
        }

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void OkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OkButtonActionPerformed
        Config.setLandf(jComboBox1.getSelectedItem().toString());
        Config.save();
        dispose();
    }//GEN-LAST:event_OkButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JButton OkButton;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    DefaultComboBoxModel UI_lanf_model = new DefaultComboBoxModel();
}
