
/*
 * DataView.java
 *
 * Created on 30 juil. 2010, 15:38:01
 */
package net.atomique.ksar.UI;

import java.io.File;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.atomique.ksar.Config;
import net.atomique.ksar.GlobalOptions;
import net.atomique.ksar.kSar;
import net.atomique.ksar.Export.FileCSV;
import net.atomique.ksar.Export.FilePDF;
import net.atomique.ksar.Graph.Graph;
import net.atomique.ksar.Graph.GraphList;

/**
 *
 * @author Max
 */
public class DataView extends javax.swing.JInternalFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataView.class);
    
    /** Creates new form DataView */
    public DataView(kSar sar) {
        initComponents();
        mysar = sar;
        jTree1.setModel(new DefaultTreeModel(mysar.graphtree));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jButton1 = new javax.swing.JButton();
        displayPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        dataMenu = new javax.swing.JMenu();
        LoadFile = new javax.swing.JMenuItem();
        LoadCommand = new javax.swing.JMenuItem();
        LoadSSH = new javax.swing.JMenuItem();
        GraphMenu = new javax.swing.JMenu();
        addgraphMenu = new javax.swing.JMenuItem();
        exportMenu = new javax.swing.JMenu();
        PDFMenu = new javax.swing.JMenuItem();
        CSVMenu = new javax.swing.JMenuItem();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setMinimumSize(new java.awt.Dimension(150, 46));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setLeftComponent(jPanel2);

        displayPanel.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setRightComponent(displayPanel);

        jPanel1.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        dataMenu.setText("Data");

        LoadFile.setText("Load from a file...");
        LoadFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadFileActionPerformed(evt);
            }
        });
        dataMenu.add(LoadFile);

        LoadCommand.setText("Load from a local Command...");
        LoadCommand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadCommandActionPerformed(evt);
            }
        });
        dataMenu.add(LoadCommand);

        LoadSSH.setText("Load from a SSH Command...");
        LoadSSH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadSSHActionPerformed(evt);
            }
        });
        dataMenu.add(LoadSSH);

        jMenuBar1.add(dataMenu);

        GraphMenu.setText("Graph");
        GraphMenu.setEnabled(false);

        addgraphMenu.setText("Add a Graph");
        addgraphMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addgraphMenuActionPerformed(evt);
            }
        });
        GraphMenu.add(addgraphMenu);

        jMenuBar1.add(GraphMenu);

        exportMenu.setText("Export");
        exportMenu.setEnabled(false);

        PDFMenu.setText("Export to PDF...");
        PDFMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PDFMenuActionPerformed(evt);
            }
        });
        exportMenu.add(PDFMenu);

        CSVMenu.setText("Export to CSV...");
        CSVMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CSVMenuActionPerformed(evt);
            }
        });
        exportMenu.add(CSVMenu);

        jMenuBar1.add(exportMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        save_data();
        dispose();
    }//GEN-LAST:event_formInternalFrameClosing

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        TreePath treepath = evt.getPath();
        Object obj = treepath.getLastPathComponent();
        if (obj != null) {
            LOGGER.debug("mem:" + Runtime.getRuntime().totalMemory());
            LOGGER.debug(" free:" + Runtime.getRuntime().freeMemory());

            SortedTreeNode treenode = (SortedTreeNode) obj;
            if ( treenode.getRoot() == treenode) {
                if (current_panel != null) {
                    displayPanel.removeAll();
                    current_panel=null;
                }
                displayPanel.repaint();
            }
            Object obj1 = treenode.getUserObject();
            if (obj1 instanceof TreeNodeInfo) {
                TreeNodeInfo tmpnode = (TreeNodeInfo) obj1;
                Graph nodeobj = tmpnode.getNode_object();
                if (current_panel != null) {
                    displayPanel.removeAll();
                    current_panel=null;
                }
                mygraphview.setGraph(nodeobj);
                current_panel = mygraphview;
                displayPanel.add(mygraphview);
                displayPanel.validate();
                displayPanel.repaint();

            }
            if (obj1 instanceof ParentNodeInfo) {
                ParentNodeInfo tmpnode = (ParentNodeInfo) obj1;
                GraphList nodeobj = tmpnode.getNode_object();
                if (current_panel != null) {
                    displayPanel.removeAll();
                    current_panel=null;
                }
                current_panel = nodeobj.run();
                displayPanel.add(current_panel);
                displayPanel.validate();
                displayPanel.repaint();
            }
        }

    }//GEN-LAST:event_jTree1ValueChanged

    private void LoadCommandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadCommandActionPerformed
        mysar.do_localcommand(null);
}//GEN-LAST:event_LoadCommandActionPerformed

    private void LoadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadFileActionPerformed
        mysar.do_fileread(null);
        
}//GEN-LAST:event_LoadFileActionPerformed

    private void LoadSSHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadSSHActionPerformed
        mysar.do_sshread(null);
    }//GEN-LAST:event_LoadSSHActionPerformed

    private void PDFMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PDFMenuActionPerformed
        GraphSelection tmp  = new GraphSelection( GlobalOptions.getUI() ,true,this);
        ask_treenode(mysar.graphtree,tmp);
        tmp.setVisible(true);
        if ( ! tmp.OkforExport ) {
            tmp=null;
            return ;            
        }
        tmp=null;
        String filename = askSaveFilename("Export PDF", Config.getLastExportDirectory());
        if (filename == null) {
            return;
        }
        Config.setLastExportDirectory(filename);
        if (!Config.getLastExportDirectory().isDirectory()) {
            Config.setLastExportDirectory(Config.getLastExportDirectory().getParentFile());
            Config.save();
        }
        doExportPDF(filename);
    }//GEN-LAST:event_PDFMenuActionPerformed

    private void CSVMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CSVMenuActionPerformed
        GraphSelection tmp  = new GraphSelection( GlobalOptions.getUI() ,true,this);
        ask_treenode(mysar.graphtree,tmp);
        tmp.setVisible(true);
        if ( ! tmp.OkforExport ) {
            return ;

        }
        String filename = askSaveFilename("Export CSV", Config.getLastExportDirectory());
        if (filename == null) {
            return;
        }
        Config.setLastExportDirectory(filename);
        if (!Config.getLastExportDirectory().isDirectory()) {
            Config.setLastExportDirectory(Config.getLastExportDirectory().getParentFile());
            Config.save();
        }
        doExportCSV(filename);
    }//GEN-LAST:event_CSVMenuActionPerformed

    private void addgraphMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addgraphMenuActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_addgraphMenuActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        mysar.interrupt_parsing();
        jButton1.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

   private String askSaveFilename(String title, File chdirto) {
        String filename = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(title);
        if (chdirto != null) {
            chooser.setCurrentDirectory(chdirto);
        }
        int returnVal = chooser.showSaveDialog(GlobalOptions.getUI());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            filename = chooser.getSelectedFile().getAbsolutePath();
        }

        if (filename == null) {
            return null;
        }

        if (new File(filename).exists()) {
            String[] choix = {"Yes", "No"};
            int resultat = JOptionPane.showOptionDialog(null, "Overwrite " + filename + " ?", "File Exist", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[1]);
            if (resultat != 0) {
                return null;
            }
        }
        return filename;
    }

   public void doExportPDF(String filename) {
        int pages = 0;
        pages= mysar.get_page_to_print();
        JPanel panel0 = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JProgressBar pbar = new JProgressBar();
        pbar.setMinimum(0);
        pbar.setMaximum(pages);
        pbar.setStringPainted(true);
        JLabel mytitre = new JLabel("Exporting: ");
        panel1.add(mytitre);
        panel2.add(pbar);
        panel0.add(panel1);
        panel0.add(panel2);
        JDialog mydial = new JDialog();
        mydial.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mydial.setContentPane(panel0);
        mydial.setSize(250, 80);
        mydial.pack();
        mydial.setLocationRelativeTo(GlobalOptions.getUI());
        mydial.setVisible(true);

        Runnable t = new FilePDF(filename, mysar, pbar, mydial);
        Thread th = new Thread(t);
        th.start();
    }
   
    public void doExportCSV(String filename) {
        int pages = 0;
        pages= mysar.myparser.getDateSamples().size();
        JPanel panel0 = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JProgressBar pbar = new JProgressBar();
        pbar.setMinimum(0);
        pbar.setMaximum(pages);
        pbar.setStringPainted(true);
        JLabel mytitre = new JLabel("Exporting: ");
        panel1.add(mytitre);
        panel2.add(pbar);
        panel0.add(panel1);
        panel0.add(panel2);
        JDialog mydial = new JDialog();
        mydial.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mydial.setContentPane(panel0);
        mydial.setSize(250, 80);
        mydial.pack();
        mydial.setLocationRelativeTo(GlobalOptions.getUI());
        mydial.setVisible(true);
        
        Runnable t = new FileCSV(filename, mysar, pbar, mydial);
        Thread th = new Thread(t);
        th.start();
        
    }

    
    public void ask_treenode(SortedTreeNode node, GraphSelection graphselection) {
        int num = node.getChildCount();
        
        if (num > 0) {
            Object obj1 = node.getUserObject();
            if (obj1 instanceof ParentNodeInfo) {                
                ParentNodeInfo tmpnode = (ParentNodeInfo) obj1;
                GraphList nodeobj = tmpnode.getNode_object();
                askparentPanel= nodeobj.getprintform();
                graphselection.addPrintCheckBox(askparentPanel);
            }
            for (int i = 0; i < num; i++) {
                SortedTreeNode l = (SortedTreeNode) node.getChildAt(i);
                ask_treenode(l, graphselection);
            }
            askparentPanel=null;
        } else {            
            Object obj1 = node.getUserObject();
            if (obj1 instanceof TreeNodeInfo) {
                TreeNodeInfo tmpnode = (TreeNodeInfo) obj1;
                Graph nodeobj = tmpnode.getNode_object();
                JCheckBox tmp = nodeobj.getprintform();
                if ( askparentPanel == null) {
                    graphselection.addPrintCheckBox(tmp);
                } else {
                    askparentPanel.add(tmp);
                }
            }
        }
    }

    public void add2tree(SortedTreeNode parent, SortedTreeNode newNode) {
        DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
        model.insertNodeInto(newNode, parent, parent.getChildCount());
    }

    public void treehome() {
        jTree1.setSelectionRow(0);
        jTree1.expandRow(0);
        jTree1.repaint();
    }

    public void notifyrun(boolean t) {
        exportMenu.setEnabled(!t);
        dataMenu.setEnabled(!t);
        if ( t ) {
            jButton1.setText("Stop");
            jButton1.setEnabled(true);
        } else {
            jButton1.setText("");
            jButton1.setEnabled(false);
        }
    }
    
    public void setHasData(boolean actif) {
        hasFreshData=actif;
        exportMenu.setEnabled(actif);
        if (actif) {
            LoadFile.setText("Append from a file...");
            LoadCommand.setText("Append from a local Command...");
            LoadSSH.setText("Append from a SSH Command...");
        } else {
            LoadFile.setText("Load from a file...");
            LoadCommand.setText("Load from a local Command...");
            LoadSSH.setText("Load from a SSH Command...");
        }
    }
    
    private void save_data() {
        if (hasFreshData) {
            System.out.println("need backup");
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem CSVMenu;
    private javax.swing.JMenu GraphMenu;
    private javax.swing.JMenuItem LoadCommand;
    private javax.swing.JMenuItem LoadFile;
    private javax.swing.JMenuItem LoadSSH;
    private javax.swing.JMenuItem PDFMenu;
    private javax.swing.JMenuItem addgraphMenu;
    private javax.swing.JMenu dataMenu;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JMenu exportMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
    private boolean hasFreshData = false;
    private kSar mysar = null;
    private JPanel current_panel = null;
    private GraphView mygraphview = new GraphView();
    private JPanel askparentPanel = null;
}
