/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GraphSelection.java
 *
 * Created on 16 août 2010, 18:48:32
 */

package net.atomique.ksar.UI;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import net.atomique.ksar.GlobalOptions;


/**
 *
 * @author alex
 */
public class GraphSelection extends javax.swing.JDialog {

    /** Creates new form GraphSelection */
    public GraphSelection(java.awt.Frame parent, boolean modal, DataView myview) {
        super(parent, modal);
        this.myview = myview;
        initComponents();
        setLocationRelativeTo(GlobalOptions.getUI());
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
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        selectallButton = new javax.swing.JButton();
        unselectallButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(300, 400));
        jScrollPane1.setRequestFocusEnabled(false);

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane1.setViewportView(jPanel3);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        selectallButton.setText("Select All");
        selectallButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectallButtonActionPerformed(evt);
            }
        });
        jPanel2.add(selectallButton);

        unselectallButton.setText("Unselect All");
        unselectallButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unselectallButtonActionPerformed(evt);
            }
        });
        jPanel2.add(unselectallButton);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator2);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        jPanel2.add(cancelButton);

        okButton.setText("Ok");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        jPanel2.add(okButton);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        this.dispose();
        OkforExport=true;
    }//GEN-LAST:event_okButtonActionPerformed

    private void selectallButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectallButtonActionPerformed
        toggle_checkbox(jPanel3,true);        
    }//GEN-LAST:event_selectallButtonActionPerformed

    private void unselectallButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unselectallButtonActionPerformed
        toggle_checkbox(jPanel3,false);
    }//GEN-LAST:event_unselectallButtonActionPerformed

    public void toggle_checkbox(JPanel panel, boolean checked) {
        Component [] list = panel.getComponents();        
        for (int i= 0; i < list.length ; i++) {
            Component tmp = (Component)list[i];
            if ( tmp instanceof JPanel) {
                JPanel obj = (JPanel) tmp;
                toggle_checkbox(obj, checked);
            }
            if ( tmp instanceof JCheckBox) {
                JCheckBox obj = (JCheckBox) tmp;
                obj.setSelected(checked);
            }
        }
    }
    
    public void addPrintCheckBox(JCheckBox tmp) {
        jPanel3.add(tmp);
        jPanel1.validate();
    }
    public void addPrintCheckBox(JPanel tmp) {
        jPanel3.add(tmp);
        jPanel1.validate();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton okButton;
    private javax.swing.JButton selectallButton;
    private javax.swing.JButton unselectallButton;
    // End of variables declaration//GEN-END:variables
    private DataView myview =null;
    public boolean OkforExport = false;
}
