

/*
 * Desktop.java
 *
 * Created on 29 juil. 2010, 16:54:23
 */
package net.atomique.ksar.UI;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import net.atomique.ksar.kSar;

/**
 *
 * @author Max
 */
public class Desktop extends javax.swing.JFrame {

    /** Creates new form Desktop */
    public Desktop() {
        int wmargins = 90;
        int hmargins = 60;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        initComponents();
        setBounds(wmargins, hmargins, screenSize.width - (wmargins * 2), screenSize.height - (hmargins * 2));
        
        DesktopPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        setVisible(true);
    }

    

    private static JInternalFrame[] filterFrame(JInternalFrame[] frames) {
        int n = 0;
        for (int i = 0; i < frames.length; i++) {
            if (frames[i].isVisible() && !frames[i].isIcon()) {
                n++;
            }
        }

        JInternalFrame[] newfs = new JInternalFrame[n];
        for (int i = 0, j = 0; i < frames.length; i++) {
            if (frames[i].isVisible() && !frames[i].isIcon()) {
                newfs[j++] = frames[i];
            }
        }
        return newfs;
    }

    public static void iconify(JDesktopPane desktopPane) {
        JInternalFrame[] frames = filterFrame(desktopPane.getAllFrames());
        if (frames.length == 0) {
            return;
        }
        for (int i = 0; i < frames.length; i++) {
            if (frames[i].isVisible() && !frames[i].isIcon()) {
                try {
                    frames[i].setIcon(true);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(Desktop.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void maxall() {
        maximize(DesktopPane);
    }
    
    private static void maximize(JDesktopPane desktopPane) {
        JInternalFrame[] frames = filterFrame(desktopPane.getAllFrames());
        if (frames.length == 0) {
            return;
        }
        for (int i = 0; i < frames.length; i++) {
            if (frames[i].isVisible() && !frames[i].isIcon()) {
                try {
                    frames[i].setMaximum(true);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(Desktop.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void tile(JDesktopPane desktopPane) {
        JInternalFrame[] frames = filterFrame(desktopPane.getAllFrames());
        if (frames.length == 0) {
            return;
        }

        tile(frames, desktopPane.getBounds());
    }

    private static void tile(JInternalFrame[] frames, Rectangle dBounds) {
        int cols = (int) Math.sqrt(frames.length);
        int rows = (int) (Math.ceil(((double) frames.length) / cols));
        int lastRow = frames.length - cols * (rows - 1);
        int width, height;

        if (lastRow == 0) {
            rows--;
            height = dBounds.height / rows;
        } else {
            height = dBounds.height / rows;
            if (lastRow < cols) {
                rows--;
                width = dBounds.width / lastRow;
                for (int i = 0; i < lastRow; i++) {
                    frames[cols * rows + i].setBounds(i * width, rows * height, width, height);
                }
            }
        }

        width = dBounds.width / cols;
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                frames[i + j * cols].setBounds(i * width, j * height, width, height);
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

        DesktopPane = new javax.swing.JDesktopPane();
        MenuBar = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        NewMenu = new javax.swing.JMenuItem();
        QuitMenu = new javax.swing.JMenuItem();
        OptionsMenu = new javax.swing.JMenu();
        SysprefMenu = new javax.swing.JMenuItem();
        WindowMenu = new javax.swing.JMenu();
        TileMenu = new javax.swing.JMenuItem();
        IconifyMenu = new javax.swing.JMenuItem();
        MaximizeMenu = new javax.swing.JMenuItem();
        HelpMenu = new javax.swing.JMenu();
        AboutMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("kSar : a sar grapher");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().add(DesktopPane, java.awt.BorderLayout.CENTER);

        FileMenu.setText("File"); // NOI18N

        NewMenu.setText("New window");
        NewMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewMenuActionPerformed(evt);
            }
        });
        FileMenu.add(NewMenu);

        QuitMenu.setText("Quit");
        QuitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuitMenuActionPerformed(evt);
            }
        });
        FileMenu.add(QuitMenu);

        MenuBar.add(FileMenu);

        OptionsMenu.setText("Options"); // NOI18N

        SysprefMenu.setText("System Preferences");
        SysprefMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SysprefMenuActionPerformed(evt);
            }
        });
        OptionsMenu.add(SysprefMenu);

        MenuBar.add(OptionsMenu);

        WindowMenu.setText("Window");

        TileMenu.setText("Tile");
        TileMenu.setActionCommand("TileMenu");
        TileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TileMenuActionPerformed(evt);
            }
        });
        WindowMenu.add(TileMenu);

        IconifyMenu.setText("Iconify");
        IconifyMenu.setActionCommand("IconifyMenu");
        IconifyMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IconifyMenuActionPerformed(evt);
            }
        });
        WindowMenu.add(IconifyMenu);

        MaximizeMenu.setText("Maximize");
        MaximizeMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaximizeMenuActionPerformed(evt);
            }
        });
        WindowMenu.add(MaximizeMenu);

        MenuBar.add(WindowMenu);

        HelpMenu.setText("?");

        AboutMenu.setText("About");
        AboutMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutMenuActionPerformed(evt);
            }
        });
        HelpMenu.add(AboutMenu);

        MenuBar.add(HelpMenu);

        setJMenuBar(MenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        tryToQuit();
    }//GEN-LAST:event_formWindowClosing

    private void QuitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QuitMenuActionPerformed
        tryToQuit();
    }//GEN-LAST:event_QuitMenuActionPerformed

    private void AboutMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutMenuActionPerformed
        AboutBox myAboutBox = new AboutBox(this);
    }//GEN-LAST:event_AboutMenuActionPerformed

    private void SysprefMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SysprefMenuActionPerformed
        Preferences myPreferences = new Preferences(this);
    }//GEN-LAST:event_SysprefMenuActionPerformed

    private void NewMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewMenuActionPerformed
        add_window();
    }//GEN-LAST:event_NewMenuActionPerformed

    private void TileMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TileMenuActionPerformed
        tile(DesktopPane);
    }//GEN-LAST:event_TileMenuActionPerformed

    private void IconifyMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IconifyMenuActionPerformed
        iconify(DesktopPane);
    }//GEN-LAST:event_IconifyMenuActionPerformed

    private void MaximizeMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaximizeMenuActionPerformed
        maximize(DesktopPane);
    }//GEN-LAST:event_MaximizeMenuActionPerformed

    public void add_window() {
        kSar mysar = new kSar(DesktopPane);
    }

    public JDesktopPane getDesktopPane() {
        return DesktopPane;
    }


    private void tryToQuit() {
        int i = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit kSar ?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (i == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutMenu;
    private javax.swing.JDesktopPane DesktopPane;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenu HelpMenu;
    private javax.swing.JMenuItem IconifyMenu;
    private javax.swing.JMenuItem MaximizeMenu;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenuItem NewMenu;
    private javax.swing.JMenu OptionsMenu;
    private javax.swing.JMenuItem QuitMenu;
    private javax.swing.JMenuItem SysprefMenu;
    private javax.swing.JMenuItem TileMenu;
    private javax.swing.JMenu WindowMenu;
    // End of variables declaration//GEN-END:variables
}
