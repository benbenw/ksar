
package net.atomique.ksar;

import java.util.HashMap;
import java.util.Map;

import net.atomique.ksar.XML.OSConfig;

/**
 *
 * @author Max
 */
public abstract class OSParser extends AllParser {

    public OSParser () {
    }

    public OSParser(kSar hissar, String header) {
        init(hissar, header);
    }

    public void init (kSar hissar, String header) {
        String[] s = header.split("\\s+");
        this.mysar = hissar;
        this.parserName = s[0];
        this.myosconfig = GlobalOptions.getOSinfo(this.parserName);
        parse_header(header);
    }
    
    public OSConfig get_OSConfig() {
        return myosconfig;
    }

    public void setHostname(String s) {
        Hostname = s;
    }

    public void setOSversion(String s) {
        OSversion = s;
    }

    public void setKernel(String s) {
        Kernel = s;
    }

    public void setCpuType(String s) {
        CpuType = s;
    }

    public void setMacAddress(String s) {
        MacAddress = s;
    }

    public void setMemory(String s) {
        Memory = s;
    }

    public void setNBDisk(String s) {
        NBDisk = s;
    }

    public void setNBCpu(String s) {
        NBCpu = s;
    }

    public void setENT(String s) {
        ENT = s;
    }

    

    public String getInfo() {
        StringBuilder tmpstr = new StringBuilder();
        tmpstr.append("OS Type: ").append(ostype);
        if (OSversion != null) {
           tmpstr.append("OS Version: ").append(OSversion).append("\n");
        }
        if (Kernel != null) {
            tmpstr.append("Kernel Release: ").append(Kernel).append("\n");
        }
        if (CpuType != null) {
            tmpstr.append("CPU Type: ").append(CpuType).append("\n");
        }
        if (Hostname != null) {
            tmpstr.append("Hostname: ").append(Hostname).append("\n");
        }
        if (MacAddress != null) {
            tmpstr.append("Mac Address: ").append(MacAddress).append("\n");
        }
        if (Memory != null) {
            tmpstr.append("Memory: ").append(Memory).append("\n");
        }
        if (NBDisk != null) {
            tmpstr.append("Number of disks: ").append(NBDisk).append("\n");
        }
        if (NBCpu != null) {
            tmpstr.append("Number of CPU: ").append(NBCpu).append("\n");
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

    public String getOriginal_line() {
        return originalLine;
    }

    public void setOriginal_line(String original_line) {
        this.originalLine = original_line;
    }

    public String gethostName() {
        return Hostname;
    }

    public String getOstype() {
        return ostype;
    }

    public void setOstype(String ostype) {
        this.ostype = ostype;
    }

     public void updateUITitle() {
        if ( mysar.getDataView() != null) {
            mysar.getDataView().setTitle(Hostname + " from "+ startofgraph + " to " + endofgraph);
        }
    }
    
    protected Map<String, Object> listofGraph = new HashMap<String, Object>();
    
    protected String lastStat = null;
    protected Object currentStatObj = null;
    
    protected String ostype = null;
    protected String Hostname = null;
    protected String OSversion = null;
    protected String Kernel = null;
    protected String CpuType = null;
    protected String MacAddress = null;
    protected String Memory = null;
    protected String NBDisk = null;
    protected String NBCpu = null;
    protected String ENT = null;
    protected String Detect = null;
    protected String originalLine=null;
    
}
