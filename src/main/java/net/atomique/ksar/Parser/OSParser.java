
package net.atomique.ksar.Parser;

import java.util.HashMap;
import java.util.Map;

import net.atomique.ksar.GlobalOptions;
import net.atomique.ksar.kSar;
import net.atomique.ksar.XML.OSConfig;

/**
 *
 * @author Max
 */
public abstract class OSParser extends BaseParser {

    public OSParser () {
    }

    public OSParser(kSar hissar, String header) {
        init(hissar, header);
    }

    @Override
    public void init (kSar hissar, String header) {
        String[] s = header.split("\\s+");
        this.mysar = hissar;
        this.parserName = s[0];
        this.myosconfig = GlobalOptions.getOSinfo(this.parserName);
        parseHeader(header);
    }
    
    public OSConfig getOSConfig() {
        return myosconfig;
    }

    public void setHostname(String s) {
        hostname = s;
    }

    public void setOSversion(String s) {
        osVersion = s;
    }

    public void setKernel(String s) {
        kernel = s;
    }

    public void setCpuType(String s) {
        cpuType = s;
    }

    public void setMacAddress(String s) {
        macAddress = s;
    }

    public void setMemory(String s) {
        memory = s;
    }

    public void setNBDisk(String s) {
        nbDisk = s;
    }

    public void setNBCpu(String s) {
        nbCpu = s;
    }

    public void setENT(String s) {
        ENT = s;
    }

    

    @Override
    public String getInfo() {
        StringBuilder tmpstr = new StringBuilder();
        tmpstr.append("OS Type: ").append(ostype);
        if (osVersion != null) {
           tmpstr.append("OS Version: ").append(osVersion).append("\n");
        }
        if (kernel != null) {
            tmpstr.append("Kernel Release: ").append(kernel).append("\n");
        }
        if (cpuType != null) {
            tmpstr.append("CPU Type: ").append(cpuType).append("\n");
        }
        if (hostname != null) {
            tmpstr.append("Hostname: ").append(hostname).append("\n");
        }
        if (macAddress != null) {
            tmpstr.append("Mac Address: ").append(macAddress).append("\n");
        }
        if (memory != null) {
            tmpstr.append("Memory: ").append(memory).append("\n");
        }
        if (nbDisk != null) {
            tmpstr.append("Number of disks: ").append(nbDisk).append("\n");
        }
        if (nbCpu != null) {
            tmpstr.append("Number of CPU: ").append(nbCpu).append("\n");
        }
        if (ENT != null) {
            tmpstr.append("Ent: ").append(ENT).append("\n");
        }
        if (sarStartDate != null) {
            tmpstr.append("Start of SAR: ").append(sarStartDate).append("\n");
        }
        if (sarEndDate != null) {
            tmpstr.append("End of SAR: ").append(sarEndDate).append("\n");
        }

        tmpstr.append("\n");

        return tmpstr.toString();
    }


    public String gethostName() {
        return hostname;
    }

    public String getOstype() {
        return ostype;
    }

    public void setOstype(String ostype) {
        this.ostype = ostype;
    }

    
    protected Map<String, Object> listofGraph = new HashMap<String, Object>();
    
    protected String lastStat = null;
    protected Object currentStatObj = null;
    
    protected String ostype = null;
    protected String hostname = null;
    protected String osVersion = null;
    protected String kernel = null;
    protected String cpuType = null;
    protected String macAddress = null;
    protected String memory = null;
    protected String nbDisk = null;
    protected String nbCpu = null;
    protected String ENT = null;
}
