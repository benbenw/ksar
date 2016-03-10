
package net.atomique.ksar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

/**
 * @author Max
 */
public class FileRead extends Thread {

    private static final Logger LOGGER = Logger.getLogger(FileRead.class.getName());
    
    private kSar mysar = null;
    private String sarfilename = null;
    
    public FileRead(kSar hissar) {
        mysar = hissar;
        JFileChooser fc = new JFileChooser();
        if (Config.getLastReadDirectory() != null) {
            fc.setCurrentDirectory(Config.getLastReadDirectory());
        }
        int returnVal = fc.showDialog(null, "Open");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            sarfilename = fc.getSelectedFile().getAbsolutePath();
            if (fc.getSelectedFile().isDirectory()) {
                Config.setLastReadDirectory(fc.getSelectedFile());
            } else {
                Config.setLastReadDirectory(fc.getSelectedFile().getParentFile());
            }
            Config.save();
        }
    }

    public FileRead(kSar hissar, String filename) {
        mysar = hissar;
        sarfilename = filename;
    }

    public String get_action() {
        if ( sarfilename != null ) {
            return "file://" + sarfilename;
        } else {
            return null;
        }
    }

    public void run() {
        if (sarfilename == null) {
            return;
        }
        
        FileReader tmpfile = null;
        BufferedReader myfilereader = null;

        try {
            tmpfile = new FileReader(sarfilename);
            myfilereader = new BufferedReader(tmpfile);
            mysar.parse(myfilereader);
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        finally {
            if (myfilereader != null) {
                try {myfilereader.close();} catch (Exception ex) {/*noop*/}
            }
            if (tmpfile != null) {
                try {tmpfile.close();} catch (Exception ex) {/*noop*/}
            }
        }
    }
    
}
